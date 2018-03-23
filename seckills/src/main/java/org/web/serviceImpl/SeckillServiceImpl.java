package org.web.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.web.dao.SeckillDao;
import org.web.dao.SuccessKillDao;
import org.web.dao.cache.RedisDao;
import org.web.dto.Exposer;
import org.web.dto.SeckillExecution;
import org.web.entity.Seckill;
import org.web.entity.SuccessKilled;
import org.web.enums.SeckillStatEnum;
import org.web.exception.RepeatKillException;
import org.web.exception.SeckillCloseException;
import org.web.exception.SeckillException;
import org.web.service.SeckillService;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class SeckillServiceImpl implements SeckillService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillDao seckillDao;

	@Autowired
	private SuccessKillDao successKillDao;

	@Autowired
	private RedisDao redisDao;

	// MD5盐值字符串，用于混淆md5
	private final String slat = "dfjksgreg@##$%$#^8979({SDGSG";

	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 5);
	}

	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	/**
	 * 输出秒杀开启时接口地址
	 */
	public Exposer exportSeckillUrl(long seckillId) {
		// 优化点：缓存优化
		// 1:访问Redis
		Seckill seckill = redisDao.getSeckill(seckillId);
		if (seckill == null) {
			// 2:访问数据库
			// 查询单个秒杀记录
			seckill = seckillDao.queryById(seckillId);
			// 如果记录为空，表示秒杀没有开启
			if (seckill == null) {
				return new Exposer(false, seckillId);
			} else {
				// 3：放入Redis里面
				redisDao.putSeckill(seckill);
			}
		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		// 系统当前时间
		Date nowTime = new Date();
		// 如果系统当前时间小于秒杀开始时间，或大于秒杀结束时间，表示秒杀没有开始
		if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
			return new Exposer(false, nowTime.getTime(), startTime.getTime(), endTime.getTime(), seckillId);
		}
		// 否则秒杀已开始
		String md5 = getMD5(seckillId);
		return new Exposer(true, md5, seckillId);
	}

	/**
	 * 生成MD5加密的方法
	 * 
	 * @param seckillId
	 * @return
	 */
	private String getMD5(long seckillId) {
		String base = seckillId + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	/**
	 * 使用注解控制事务方法的优点 1：开发团队达成一致约定，明确标注事务方法的编程风格 2：保证事务方法的执行时间尽可能短
	 * 3：不是所有的方法都需要事务，如只有一条修改操作，或只读操作不需要事务控制
	 */
	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, SeckillCloseException, RepeatKillException {
		// 系统当前时间
		Date nowTime = new Date();
		// 判断用户的MD5
		if (md5 == null || !md5.equals(getMD5(seckillId))) {
			throw new SeckillException("seckill data rewrite");
		}
		try {
			// 记录购买行为
			int total = successKillDao.insertSuccessKilled(seckillId, userPhone);
			if (total <= 0) {
				throw new RepeatKillException("不能重复秒杀");
			} else {
				// 减库存，热点商品竞争
				int count = seckillDao.reduceNumber(seckillId, nowTime);
				// 减库存失败 MySQL------>rollback
				if (count <= 0) {
					throw new SeckillCloseException("秒杀已结束");
				} else {
					// 减库存成功，秒杀成功 MySQL------>commit
					SuccessKilled successKilled = successKillDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
				}
			}
		} catch (SeckillCloseException e1) {
			throw e1;
		} catch (RepeatKillException e2) {
			throw e2;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// 所有编译期异常转化为运行期异常
			throw new SeckillException("sckill inner error" + e.getMessage());
		}
	}

	/**
	 * 调用存储过程
	 */
	public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5) {
		if (md5 == null || !md5.equals(getMD5(seckillId))) {
			// 数据被篡改
			return new SeckillExecution(seckillId, SeckillStatEnum.DATE_REWRITE);
		}
		Date killTime = new Date();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seckillId", seckillId);
		map.put("phone", userPhone);
		map.put("killTime", killTime);
		map.put("result", null);
		try {
			// 执行存储过程，result被赋值
			seckillDao.killByProcedure(map);
			// 获取Result
			int result = MapUtils.getInteger(map, "result", -2);
			// 秒杀成功
			if (result == 1) {
				SuccessKilled sk = successKillDao.queryByIdWithSeckill(seckillId, userPhone);
				return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, sk);
			} else {
				// 秒杀失败
				return new SeckillExecution(seckillId, SeckillStatEnum.stateof(result));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
		}
	}
}

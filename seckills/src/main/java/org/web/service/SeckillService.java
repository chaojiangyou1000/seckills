package org.web.service;

import java.util.List;

import org.web.dto.SeckillExecution;
import org.web.dto.Exposer;
import org.web.entity.Seckill;
import org.web.exception.RepeatKillException;
import org.web.exception.SeckillCloseException;
import org.web.exception.SeckillException;

/**
 * 业务接口，站在使用者的角度设计接口 三个方面：1：方法的设计粒度 2： 方法的参数 3：返回的参数
 * 
 * @author Administrator
 *
 */
public interface SeckillService {

	/**
	 * 查询所有的秒杀记录
	 * 
	 * @return
	 */
	List<Seckill> getSeckillList();

	/**
	 * 查询单个秒杀记录
	 * 
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);

	/**
	 * 输出秒杀开启时接口地址，否则输出系统时间和秒杀时间
	 * 
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);

	/**
	 * 执行秒杀操作
	 * 
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, SeckillCloseException, RepeatKillException;
	
	/**
	 * 执行秒杀操作 --->调用存储过程
	 * 
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5);
}

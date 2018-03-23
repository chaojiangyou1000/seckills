package org.web.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.web.dao.SeckillDao;
import org.web.entity.Seckill;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml" })
public class RedisDaoTest {

	private long seckillId = 1001;

	@Autowired
	private RedisDao redisDao;

	@Autowired
	private SeckillDao seckillDao;

	@Test
	public void testSeckill() throws Exception {
		// 从Redis缓存拿值
		Seckill seckill = redisDao.getSeckill(seckillId);
		if (seckill == null) {
			// 从数据库拿值
			seckill = seckillDao.queryById(seckillId);
			if (seckill != null) {
				// 添加进Redis缓存
				String result = redisDao.putSeckill(seckill);
				System.out.println(result);
				// 从Redis缓存拿值
				seckill = redisDao.getSeckill(seckillId);
				System.out.println(seckill);
			}
		}
	}

	@Test
	public void testPutSeckill() throws Exception {

	}

}

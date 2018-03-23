package org.test.dao;


import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.web.dao.SuccessKillDao;
import org.web.entity.SuccessKilled;
/**
 * 
 * @author Administrator
 *
 */

//配置spring和junit整合，这样junit在启动时就会加载spring容器
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKillDaoTest {

	@Resource
	private SuccessKillDao successKilledDao;
	
	@Test
	public void testInsertSuccessKilled() {
		
		//第一次insertCount=1
		//第二次insertCount=0（入库失败，秒杀失败）
		
		long seckillId = 1001L;
		long userPhone = 13078962345L;
		int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
		System.out.println("insertCount="+insertCount);
	}

	@Test
	public void testQueryByIdWithSeckill() {
		long seckillId = 1001L;
		long userPhone = 13078962345L;
		SuccessKilled successKilled =  successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
		System.out.println(successKilled);
		System.out.println(successKilled.getSeckill());
	}

}

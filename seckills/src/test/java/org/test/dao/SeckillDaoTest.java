package org.test.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.web.dao.SeckillDao;
import org.web.entity.Seckill;

/**
 * 
 * @author Administrator
 *
 */
// 配置spring和junit整合，这样junit在启动时就会加载spring容器
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring的配置文件
@ContextConfiguration({ "classpath:spring/spring-dao.xml" })
public class SeckillDaoTest {

	// 注入Dao实现类依赖
	@Resource
	private SeckillDao seckillDao;

	@Test
	public void queryById() throws Exception {
		// 此时传递一个参数，JAVA语言可以给seckillId赋值，当两个参数时，要采用@Param注解给其赋值
		long seckillId = 1000;
		Seckill seckill = seckillDao.queryById(seckillId);
		System.out.println(seckill.getName());
		System.out.println(seckill);
	}

	@Test
	public void queryAll() throws Exception {
		// 从第一条数据开始查，偏移量是100
		List<Seckill> seckill = seckillDao.queryAll(0, 100);
		for (Seckill seckill2 : seckill) {
			System.out.println(seckill2);
		}
	}

	@Test
	public void reduceNumber() throws Exception {
		Date killTime = new Date();
		// 秒杀成功时减库存
		int update = seckillDao.reduceNumber(1004, killTime);
		System.out.println(update + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	}
}

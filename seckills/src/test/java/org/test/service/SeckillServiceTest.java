package org.test.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.web.dto.Exposer;
import org.web.dto.SeckillExecution;
import org.web.entity.Seckill;
import org.web.exception.RepeatKillException;
import org.web.exception.SeckillCloseException;
import org.web.service.SeckillService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml" })
public class SeckillServiceTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillService seckillService;

	@Test
	public void testGetSeckillList() throws Exception {
		List<Seckill> list = seckillService.getSeckillList();
		logger.info("list={}", list);
	}

	@Test
	public void testGetById() throws Exception {
		long seckillId = 1000;
		Seckill seckill = seckillService.getById(seckillId);
		logger.info("seckill={}", seckill);
	}

	@Test
	public void testExportSeckillUrl() throws Exception {
		long seckillId = 1000;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		logger.info("exposer={}", exposer);
	}

	@Test
	public void testExecuteSeckill() throws Exception {
		long seckillId = 1000;
		long userPhone = 13245678902L;
		String md5 = "8774c9c95686c5dbfb0496f61bf2b123";
		try {
			SeckillExecution excution = seckillService.executeSeckill(seckillId, userPhone, md5);
			logger.info("excution={}", excution);
		} catch (RepeatKillException e) {
			logger.error(e.getMessage());
		} catch (SeckillCloseException e) {
			logger.error(e.getMessage());
		}
	}

	@Test
	public void testExecuteSeckillProcedure() throws Exception {
		long seckillId = 1004;
		long userPhone = 13245678902L;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		if (exposer.isExposed()) {
			String md5 = exposer.getMd5();
			SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId, userPhone, md5);
			logger.info(execution.getStateInfo());
		}
	}
}

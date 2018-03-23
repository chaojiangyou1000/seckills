package org.web.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web.entity.Seckill;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final JedisPool jedisPool;

	private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

	/**
	 * 对jedisPool进行初始化
	 * 
	 * @param ip
	 * @param port
	 */
	public RedisDao(String ip, int port) {
		jedisPool = new JedisPool(ip, port);
	}

	public Seckill getSeckill(long seckillId) {
		// Redis操作逻辑
		try {
			// 相当于拿到连接池
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:" + seckillId;
				// 采用自定义序列化，将对象转化成二进制传给Redis
				byte[] bytes = jedis.get(key.getBytes());
				// 说明缓存了获取到了
				if (!(bytes == null)) {
					Seckill seckill = schema.newMessage();
					ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
					return seckill;
				}
			} finally {
				// 关闭连接池
				jedis.close();
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 当缓存里没有时，去添加
	 * 
	 * @param seckill
	 * @return
	 */
	public String putSeckill(Seckill seckill) {
		try {
			// 相当于拿到连接池
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:" + seckill.getSeckillId();
				byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
						LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				// 缓存超时一个小时
				int timeout = 60 * 60;
				String result = jedis.setex(key.getBytes(), timeout, bytes);
				return result;
			} finally {
				// 关闭连接池
				jedis.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
}

package org.web.exception;

/**
 * 秒杀相关业务异常
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class SeckillException extends RuntimeException {

	public SeckillException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}

	public SeckillException(String message, Throwable cause) {
		// TODO Auto-generated constructor stub
		super(message, cause);
	}

}

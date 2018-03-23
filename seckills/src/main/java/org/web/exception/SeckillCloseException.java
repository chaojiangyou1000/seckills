package org.web.exception;
/**
 * 秒杀关闭异常:如秒杀时间到了，库存数量为0等
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class SeckillCloseException extends SeckillException{

	public SeckillCloseException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}
	
	public SeckillCloseException(String message, Throwable cause) {
		// TODO Auto-generated constructor stub
		super(message,cause);
	}
}

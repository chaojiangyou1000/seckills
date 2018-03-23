package org.web.exception;
/**
 * 重复秒杀异常（运行期）异常
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class RepeatKillException extends SeckillException{

	public RepeatKillException(String message) {
		super(message);
	}
	
	public RepeatKillException(String message, Throwable cause) {
	    super(message, cause);
	}
}

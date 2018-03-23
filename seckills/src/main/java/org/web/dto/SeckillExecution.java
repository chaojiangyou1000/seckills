package org.web.dto;

import org.web.entity.SuccessKilled;
import org.web.enums.SeckillStatEnum;

/***
 * 封装 秒杀执行后的结果
 * 
 * @author Administrator
 *
 */
public class SeckillExecution {

	private long seckillId;

	private int state; // 秒杀执行结果状态

	private String stateInfo; // 状态标识

	private SuccessKilled successKilled; // 秒杀成功后返回对象数据

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccessKilled getSuccessKilled() {
		return successKilled;
	}

	public void setSuccessKilled(SuccessKilled successKilled) {
		this.successKilled = successKilled;
	}

	@Override
	public String toString() {
		return "SeckillExecution [seckillId=" + seckillId + ", state=" + state + ", stateInfo=" + stateInfo
				+ ", successKilled=" + successKilled + "]";
	}

	/**
	 * 秒杀成功后返回数据
	 * 
	 * @param seckillId
	 * @param state
	 * @param stateInfo
	 * @param successKilled
	 */
	public SeckillExecution(long seckillId, SeckillStatEnum statEnum, SuccessKilled successKilled) {
		super();
		this.seckillId = seckillId;
		this.state = statEnum.getState();
		this.stateInfo = statEnum.getStateInfo();
		this.successKilled = successKilled;
	}

	/**
	 * 秒杀失败后返回数据
	 * 
	 * @param seckillId
	 * @param state
	 * @param stateInfo
	 */
	public SeckillExecution(long seckillId, SeckillStatEnum statEnum) {
		super();
		this.seckillId = seckillId;
		this.state = statEnum.getState();
		this.stateInfo = statEnum.getStateInfo();
	}

}

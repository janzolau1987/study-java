package com.yaoyaohao.study.zookeeper.lock;

/**
 * 分布式锁定义
 * 
 * @author liujianzhu
 * @date 2016年10月14日 下午5:01:11
 */
public interface DistributedLock {
	/**
	 * 获取锁，若未成功获取得等待,直到成功获取
	 * @throws Exception
	 */
	public void acquire() throws Exception;


	/**
	 * 锁释放
	 * @throws Exception
	 */
	public void release() throws Exception;
}

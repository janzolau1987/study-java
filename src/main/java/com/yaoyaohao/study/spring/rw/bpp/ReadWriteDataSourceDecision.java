package com.yaoyaohao.study.spring.rw.bpp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.PatternMatchUtils;

/**
 *  读写分离动态数据库选择决策类
 *  通过ThreadLocal绑定DataSourceType来决定是使用读库，还是写库
 *  
 *  参照：http://jinnianshilongnian.iteye.com/blog/1720618
 * 
 */
public class ReadWriteDataSourceDecision {
	private static final ThreadLocal<DataSourceType> holder = new ThreadLocal<ReadWriteDataSourceDecision.DataSourceType>();
	
	//ISSUE:当存在一个事务中"写-读-读"，会出现第二个读的holder值被前一个读最后reset掉
	private static final ThreadLocal<Integer> counter = new ThreadLocal<Integer>(){
		protected Integer initialValue() {
			return 0;
		}
	};
	
	/**
	 * 标注为写库
	 */
	public static void markWrite() {
        holder.set(DataSourceType.WRITE);
    }
    
	/**
	 * 标注为读库
	 */
    public static void markRead() {
        holder.set(DataSourceType.READ);
    }
    
    /**
     * 重置
     */
    public static void reset() {
    	int cnt = counter.get();
    	if(cnt > 0) {
    		cnt --;
    		counter.set(cnt);
    	}
    	//如果当计数结果为0时，将当前绑定的数据类型清空
    	if(cnt == 0) 
    		holder.set(null);
    }
    
    /**
     * 判断是否为空，未设置读写类型
     */
    public static boolean isNone() {
        return null == holder.get(); 
    }
    
    /**
     * 判断是否为写库
     */
    public static boolean isWrite() {
        return DataSourceType.WRITE == holder.get();
    }
    
    /**
     * 判断是否为读库
     */
    public static boolean isRead() {
        return DataSourceType.READ == holder.get();
    }
    
    /**数据源类型*/
    public enum DataSourceType{
		WRITE, /*写库*/
		READ;  /*读库*/
	}
    
    /***********************************************
     * 
     * 
     *
     ***********************************************/
    /**
	 * 保存方法的是否强制读的标识信息
	 */
	private static Map<String, Boolean> readMethodMap = new HashMap<String, Boolean>();
	
	public static void markForceRead(String methodName,boolean isForceRead) {
		readMethodMap.put(methodName, isForceRead);
	}
	
	public static boolean matchMethod(String methodName) {
		//增加计数，用于reset时判断
		counter.set(counter.get() + 1);
		
		String bestNameMatch = null;
        for (String mappedName : readMethodMap.keySet()) {
            if (isMatch(methodName, mappedName)) {
                bestNameMatch = mappedName;
                break;
            }
        }
        Boolean isForceRead = readMethodMap.get(bestNameMatch);
        //表示强制选择 读 库
        if(isForceRead == Boolean.TRUE) {
            return true;
        }
        
        //如果之前选择了写库 现在还选择 写库
        if(isWrite()) {
            return false;
        }
        
        //表示应该选择读库
        if(isForceRead != null) {
            return true;
        }
        //默认选择 写库
        return false;
	}
	
	private static boolean isMatch(String methodName, String mappedName) {
        return PatternMatchUtils.simpleMatch(mappedName, methodName);
    }
	
	
}

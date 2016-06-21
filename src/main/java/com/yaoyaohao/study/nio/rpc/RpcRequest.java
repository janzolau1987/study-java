package com.yaoyaohao.study.nio.rpc;

import java.io.Serializable;
import java.util.Arrays;

/**
 * rpc交互数据对象
 * 
 * @author liujianzhu
 * @date 2016年6月21日 下午3:41:18
 *
 */
public class RpcRequest implements Serializable {
	private String interfaceName;	//目标接口类型
	private String methodName;	//目标方法名
	private Class<?>[] parameterTypes;	//参数类型
	private Object[] parameterValues;	//参数值
	
	public RpcRequest(){}

	public RpcRequest(String interfaceName, String methodName, Class<?>[] parameterTypes, Object[] parameterValues) {
		super();
		this.interfaceName = interfaceName;
		this.methodName = methodName;
		this.parameterTypes = parameterTypes;
		this.parameterValues = parameterValues;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public Object[] getParameterValues() {
		return parameterValues;
	}

	public void setParameterValues(Object[] parameterValues) {
		this.parameterValues = parameterValues;
	}

	@Override
	public String toString() {
		return "RpcData [interfaceName=" + interfaceName + ", methodName=" + methodName + ", parameterTypes="
				+ Arrays.toString(parameterTypes) + ", parameterValues=" + Arrays.toString(parameterValues) + "]";
	}

	private static final long serialVersionUID = 6952978158170726133L;
}

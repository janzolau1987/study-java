package com.yaoyaohao.study.zookeeper.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

/**
 * zookeeper工具类
 * 
 * @author liujianzhu
 * @date 2016年10月10日 下午3:08:18
 */
public class ZKHelper {
	private static Logger log = Logger.getLogger(ZKHelper.class);

	private static String hosts;
	private static ExecutorService pool = Executors.newCachedThreadPool();
	private static final String GROUP_NAME = "/SESSIONS";

	/**
	 * 初始化
	 * 
	 * @param config
	 */
	public static void initialize(Configuration config) {
		hosts = config.getServers();
	}

	/**
	 * 销毁
	 */
	public static void destory() {
		if (pool != null) {
			// 关闭
			pool.shutdown();
		}
	}

	/**
	 * 连接服务器
	 * 
	 * @return
	 */
	public static ZooKeeper connect() {
		ConnectionWatcher cw = new ConnectionWatcher();
		ZooKeeper zk = cw.connection(hosts);
		return zk;
	}

	/**
	 * 关闭会话
	 * 
	 * @param zk
	 */
	public static void close(ZooKeeper zk) {
		if (zk != null) {
			try {
				zk.close();
			} catch (InterruptedException e) {
				log.error(e);
			}
		}
	}

	/**
	 * 验证指定id节点是否有效
	 * 
	 * @param id
	 * @return
	 */
	public static boolean isValid(String id) {
		ZooKeeper zk = connect();
		if (zk != null) {
			try {
				return isValid(id, zk);
			} finally {
				close(zk);
			}
		}
		return false;
	}

	/**
	 * 验证指定ID的节点是否有效
	 * 
	 * @param id
	 * @param zk
	 * @return
	 */
	public static boolean isValid(String id, ZooKeeper zk) {
		if (zk != null) {
			// 获取元数据
			SessionMetaData metadata = getSessionMetaData(id, zk);
			// 如果不存在或是无效，则直接返回null
			if (metadata == null) {
				return false;
			}
			return metadata.isValidate();
		}
		return false;
	}

	/**
	 * 返回指定ID的session元数据
	 * 
	 * @param id
	 * @param zk
	 * @return
	 */
	public static SessionMetaData getSessionMetaData(String id, ZooKeeper zk) {
		if (zk != null) {
			String path = GROUP_NAME + "/" + id;
			try {
				// 检查节点是否存在
				Stat stat = zk.exists(path, false);
				// stat为null表示无此节点
				if (stat == null) {
					return null;
				}
				// 获取节点上数据
				byte[] data = zk.getData(path, false, null);
				if (data != null) {
					Object obj = SerializationUtils.deserialize(data);
					// 转换类型
					if (obj instanceof SessionMetaData) {
						SessionMetaData metadata = (SessionMetaData) obj;
						// 设置当前版本号
						metadata.setVersion(stat.getVersion());
						return metadata;
					}
				}
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			}
		}
		return null;
	}

	/**
	 * 更新Session节点的元数据
	 * 
	 * @param id
	 */
	public static void updateSessionMetaData(String id) {
		ZooKeeper zk = connect();
		try {
			// 获取元数据
			SessionMetaData metadata = getSessionMetaData(id, zk);
			if (metadata != null) {
				updateSessionMetaData(metadata, zk);
			}
		} finally {
			close(zk);
		}
	}

	/**
	 * 更新Session节点的元数据
	 * 
	 * @param metadata
	 * @param zk
	 */
	public static void updateSessionMetaData(SessionMetaData metadata, ZooKeeper zk) {
		try {
			if (metadata != null) {
				String id = metadata.getId();
				Long now = System.currentTimeMillis();// 当前时间
				// 检查是否过期
				Long timeout = metadata.getLastAccessTm() + metadata.getMaxIdle();
				if (timeout < now) {
					metadata.setValidate(false);
					log.debug("Session节点已超时[" + id + "]");
				}
				// 设置最后一次访问时间
				metadata.setLastAccessTm(now);
				// 更新节点数据
				String path = GROUP_NAME + "/" + id;
				byte[] data = SerializationUtils.serialize(metadata);
				zk.setData(path, data, metadata.getVersion());
				log.debug("更新Session节点元数据完成[" + id + "]");
			}
		} catch (KeeperException e) {
			log.error(e);
		} catch (InterruptedException e) {
			log.error(e);
		}
	}

	/**
	 * 返回zk服务器上的Session节点的所有数据，并装载为Map
	 * 
	 * @param id
	 * @return
	 */
	public static Map<String, Object> getSessionMap(String id) {
		ZooKeeper zk = connect();
		if (zk != null) {
			String path = GROUP_NAME + "/" + id;
			try {
				// 获取元数据
				SessionMetaData metadata = getSessionMetaData(id, zk);
				if (metadata == null || !metadata.isValidate())
					return null;
				// 获取所有子节点
				List<String> nodes = zk.getChildren(path, false);
				// 存放数据
				Map<String, Object> sessionMap = new HashMap<>();
				for (String node : nodes) {
					String dataPath = path + "/" + node;
					Stat stat = zk.exists(dataPath, false);
					if (stat != null) {
						byte[] data = zk.getData(dataPath, false, null);
						if (data != null) {
							sessionMap.put(node, SerializationUtils.deserialize(data));
						} else {
							sessionMap.put(node, null);
						}
					}
				}
				return sessionMap;
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			} finally {
				close(zk);
			}
		}
		return null;
	}

	/**
	 * 创建一个组节点
	 */
	public static void createGroupNode() {
		ZooKeeper zk = connect();
		if (zk != null) {
			try {
				Stat stat = zk.exists(GROUP_NAME, false);
				if (stat == null) {
					String createPath = zk.create(GROUP_NAME, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					log.debug("创建节点完成:[" + createPath + "]");
				} else {
					log.debug("组节点已存在，无需创建[" + GROUP_NAME + "]");
				}
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			} finally {
				close(zk);
			}
		}
	}
	
	/**
	 * 创建指定SessionID的节点
	 * @param metadata
	 * @return
	 */
	public static String createSessionNode(SessionMetaData metadata) {
		if(metadata == null)
			return null;
		ZooKeeper zk = connect();
		if(zk != null) {
			String path = GROUP_NAME + "/" + metadata.getId();
			try{
				Stat stat = zk.exists(path, false);
				if(stat == null) {
					String createPath = zk.create(path, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					log.debug("创建Session节点完成:[" + createPath + "]");
					zk.setData(path, SerializationUtils.serialize(metadata), -1);
					return createPath;
				}
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			} finally {
				close(zk);
			}
		}
		return null;
	}
	
	/**
	 * 删除指定Session ID的节点
	 * @param sid
	 * @return
	 */
	public static boolean deleteSessionNode(String sid) {
		ZooKeeper zk = connect();
		if(zk != null) {
			String path = GROUP_NAME + "/" + sid;
			try{
				Stat stat = zk.exists(path, false);
				if(stat != null) {
					List<String> nodes = zk.getChildren(path, false);
					if(nodes != null) {
						for(String node : nodes) {
							zk.delete(path + "/" + node, -1);
						}
					}
					//删除父节点
					zk.delete(path, -1);
					log.debug("删除Session节点完成:[" + path + "]");
					return true;
				}
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			} finally {
				close(zk);
			}
		}
		return false;
	}
	
	/**
	 * 在指定Session ID的节点下添加数据节点
	 * @param sid
	 * @param name
	 * @param value
	 * @return
	 */
	public static boolean setSessionData(String sid,String name,Object value) {
		boolean result = false;
		ZooKeeper zk = connect();
		if(zk != null) {
			String path = GROUP_NAME + "/" + sid;
			try{
				Stat stat = zk.exists(path, false);
				if(stat != null) {
					String dataPath = path + "/" + name;
					stat = zk.exists(dataPath, false);
					if(stat == null) {
						zk.create(dataPath, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
						log.debug("创建数据节点完成[" + dataPath + "]");
					}
					if(value instanceof Serializable) {
						int dataNodeVer = -1;
						if(stat != null) {
							dataNodeVer = stat.getVersion();
						}
						byte[] data = SerializationUtils.serialize(value);
						stat = zk.setData(dataPath, data, dataNodeVer);
						log.debug("更新数据节点数据完成[" + dataPath + "][" + value + "]");
						result = true;
					}
				}
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			} finally {
				close(zk);
			}
		}
		return result;
	}
	
	/**
	 * 返回指定Session ID的节点下数据
	 * @param sid
	 * @param name
	 * @return
	 */
	public static Object getSessionData(String sid,String name) {
		ZooKeeper zk = connect();
		if(zk != null) {
			String path = GROUP_NAME + "/" + sid;
			try{
				Stat stat = zk.exists(path, false);
				if(stat != null) {
					String dataPath = path + "/" + name;
					stat = zk.exists(dataPath, false);
					Object obj = null;
					if(stat != null) {
						byte[] data = zk.getData(dataPath, false, null);
						if(data != null) {
							obj = SerializationUtils.deserialize(data);
						}
					}
					return obj;
				}
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			} finally {
				close(zk);
			}
		}
		return null;
	}
	
	/**
	 * 删除Session ID节点下的数据节点
	 * @param sid
	 * @param name
	 */
	public static void removeSessionData(String sid,String name) {
		ZooKeeper zk = connect();
		if(zk != null) {
			String path = GROUP_NAME + "/" + sid;
			try{
				Stat stat = zk.exists(path, false);
				if(stat != null) {
					String dataPath = path + "/" + name;
					stat = zk.exists(dataPath, false);
					if(stat != null) {
						zk.delete(dataPath, -1);
					}
				}
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			} finally {
				close(zk);
			}
		}
	}
}

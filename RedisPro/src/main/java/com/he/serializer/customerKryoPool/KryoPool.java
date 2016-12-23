package com.he.serializer.customerKryoPool;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.esotericsoftware.kryo.Kryo;

public class KryoPool {
	
	KryoPooledObjectFactoryFactory kryoPooledObjectFactoryFactory = new KryoPooledObjectFactoryFactory();
	
	ObjectPool<Kryo> pool = null;
	
	{
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMaxTotal(100);
		poolConfig.setMaxIdle(10);
		pool = new GenericObjectPool<Kryo>(kryoPooledObjectFactoryFactory,poolConfig);
	}
	
	public Kryo get(){
		try {
			return pool.borrowObject();
		} catch (Exception e) {
			throw new RuntimeException("获取kryo失败",e);
		}
	}
	
	public void returnKryo(Kryo kryo){
		try {
			pool.returnObject(kryo);
		} catch (Exception e) {
			throw new RuntimeException("returnKryo失败",e);
		}
	}
	
}

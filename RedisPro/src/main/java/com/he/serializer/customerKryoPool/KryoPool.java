package com.he.serializer.customerKryoPool;

import javax.annotation.Resource;

import org.apache.commons.pool2.ObjectPool;

import com.esotericsoftware.kryo.Kryo;

public class KryoPool {
	
	@Resource
	ObjectPool<Kryo> objectPool;
	
	public ObjectPool<Kryo> getObjectPool() {
		return objectPool;
	}

	public void setObjectPool(ObjectPool<Kryo> objectPool) {
		this.objectPool = objectPool;
	}

	public Kryo borrow(){
		try {
			return objectPool.borrowObject();
		} catch (Exception e) {
			throw new RuntimeException("获取kryo失败",e);
		}
	}
	
	public void release(Kryo kryo){
		try {
			objectPool.returnObject(kryo);
		} catch (Exception e) {
			throw new RuntimeException("returnKryo失败",e);
		}
	}
	
}

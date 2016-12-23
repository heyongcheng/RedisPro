package com.he.serializer.customerKryoPool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import com.esotericsoftware.kryo.Kryo;
import com.he.serializer.KryoFactory;

public class KryoPooledObjectFactoryFactory extends BasePooledObjectFactory<Kryo>{

	@Override
	public Kryo create() throws Exception {
		return KryoFactory.getInstance();
	}

	@Override
	public PooledObject<Kryo> wrap(Kryo kryo) {
		return new DefaultPooledObject<Kryo>(kryo);
	}
}

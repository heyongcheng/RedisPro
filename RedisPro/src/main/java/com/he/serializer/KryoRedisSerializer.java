package com.he.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.annotation.Resource;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.he.serializer.customerKryoPool.KryoPool;

public class KryoRedisSerializer implements RedisSerializer<Object> {
	
	@Resource
	KryoPool kryoPool;
	
	
	public KryoPool getKryoPool() {
		return kryoPool;
	}

	public void setKryoPool(KryoPool kryoPool) {
		this.kryoPool = kryoPool;
	}

	/**
	 * 序列化
	 */
	public byte[] serialize(Object t) throws SerializationException {
		if(t == null)
			return null;
		
		byte[] array = null;
		Kryo kryo = kryoPool.borrow();
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
			Output output = new Output(bos);
			
			kryo.writeClassAndObject(output, t);
			
			array = output.toBytes();
			
			output.flush();
			
			output.close();

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(kryo != null){
				kryoPool.release(kryo);
			}
		}
		return array;
	}

	/**
	 * 反序列化
	 */
	public Object deserialize(byte[] bytes) throws SerializationException {
		if(bytes == null)
			return null;
		
		Object object = null;
		Kryo kryo = kryoPool.borrow();
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			
			Input input = new Input(bis);
			
			object = kryo.readClassAndObject(input);
			
			input.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(kryo != null){
				kryoPool.release(kryo);
			}
		}
		
		return object;
	}

}

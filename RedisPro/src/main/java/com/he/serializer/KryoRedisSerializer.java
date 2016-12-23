package com.he.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.he.serializer.customerKryoPool.KryoPool;

public class KryoRedisSerializer implements RedisSerializer<Object> {
	
	KryoPool kryoPool = new KryoPool();
	
	/**
	 * 序列化
	 */
	public byte[] serialize(Object t) throws SerializationException {
		if(t == null)
			return null;
		
		byte[] array = null;
		Kryo kryo = kryoPool.get();
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
				kryoPool.returnKryo(kryo);
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
		Kryo kryo = kryoPool.get();
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			
			Input input = new Input(bis);
			
			object = kryo.readClassAndObject(input);
			
			input.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(kryo != null){
				kryoPool.returnKryo(kryo);
			}
		}
		
		return object;
	}


}

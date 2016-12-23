package com.he.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.he.model.User;

public class KryoFactory {
	
	public static Kryo getInstance(){
		Kryo kryo = new Kryo();
		kryo.register(User.class);
		return kryo;
	}
}

package com.cts.blog.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class JsonUtils {

	public static final String toJson(Object obj, Type objType) {
		Gson gson = new Gson();
		return gson.toJson(obj, objType);
	}

	public static final <T> T fromJson(String json, Class<T> classOfT) {
		Gson gson = new Gson();
		return gson.fromJson(json, classOfT);
	}
}

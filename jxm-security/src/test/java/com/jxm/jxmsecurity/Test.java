package com.jxm.jxmsecurity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import lombok.Data;
import lombok.experimental.Accessors;

public class Test {
	public static void main(String[] args) throws Exception {
		T1<String> t1 = new T1<>("name", "jxm");
		System.out.println(t1.getKey());
		System.out.println(t1.getValue("jxm"));
	}
}
class T1<T> {

	private T key;

	private T value;

	public T1(T key, T value) {
		this.key = key;
		this.value = value;
	}

	public T getKey() {
		return key;
	}

	public <T> T getValue(T arg) {
		return arg;
	}
}

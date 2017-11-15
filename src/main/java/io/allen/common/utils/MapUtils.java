package io.allen.common.utils;

import java.util.Map;

import org.springframework.cglib.beans.BeanMap;

import com.google.common.collect.Maps;

public class MapUtils {
	public static<T> Map<String,Object>beanToMap(T bean){
		Map<String,Object> map = Maps.newHashMap();
		if(bean !=null) {
			BeanMap beanMap = BeanMap.create(bean);
			for (Object key : beanMap.keySet()) {
				map.put(key+"", beanMap.get(key));
			}
		}
		return map;
	}
}

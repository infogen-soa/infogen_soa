package com.infogen.util;

import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.infogen.aop.tools.Tool_Jackson;

/**
 * @author larry/larrylv@outlook.com/创建时间 2015年7月31日 上午9:44:55
 * @since 1.0
 * @version 1.0
 */
public class JSONObject extends HashMap<String, Object> {
	private static final long serialVersionUID = -3927973692243736378L;
	private static final Logger LOGGER = Logger.getLogger(JSONObject.class.getName());

	///////////////////////////////////////////////////////////// json工具//////////////////////////////////////////

	public String getAsString(String key, String _default) {
		return (String) this.getOrDefault(key, _default);
	}

	public Boolean getAsBoolean(String key, Boolean _default) {
		return (Boolean) this.getOrDefault(key, _default);
	}

	public Long getAsLong(String key, Long _default) {
		return (Long) this.getOrDefault(key, _default);
	}

	public Integer getAsInteger(String key, Integer _default) {
		return (Integer) this.getOrDefault(key, _default);
	}

	public Double getAsDouble(String key, Double _default) {
		return (Double) this.getOrDefault(key, _default);
	}

	public Float getAsFloat(String key, Float _default) {
		return (Float) this.getOrDefault(key, _default);
	}

	public <T> T getAsClass(String key, TypeReference<T> typereference, T _default) {
		Object object = this.get(key);
		if (object == null) {
			return _default;
		}
		try {
			return (T) Tool_Jackson.toObject(Tool_Jackson.toJson(object), typereference);
		} catch (IOException e) {
			LOGGER.error("json 转换对象失败:", e);
			return _default;
		}
	}

	public <T> T getAsClass(String key, Class<T> clazz, T _default) {
		Object object = this.get(key);
		if (object == null) {
			return _default;
		}
		try {
			return (T) Tool_Jackson.toObject(Tool_Jackson.toJson(object), clazz);
		} catch (IOException e) {
			LOGGER.error("json 转换对象失败:", e);
			return _default;
		}
	}

	public JSONObject getAsJSONObject(String key, JSONObject _default) {
		return getAsClass(key, new TypeReference<JSONObject>() {
		}, _default);
	}

	public JSONArray getAsJSONArray(String key, JSONArray _default) {
		return getAsClass(key, new TypeReference<JSONArray>() {
		}, _default);
	}
}

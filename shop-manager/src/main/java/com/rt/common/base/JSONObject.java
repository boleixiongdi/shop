package com.rt.common.base;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import com.baomidou.mybatisplus.annotations.TableField;

public class JSONObject extends JSON implements Map<String, Object>, Cloneable,
		Serializable, InvocationHandler {
	@TableField(exist = false)
	private static final int DEFAULT_INITIAL_CAPACITY = 16;
	@TableField(exist = false)
	private final Map<String, Object> map;

	public JSONObject() {
		this(16, false);
	}

	public JSONObject(Map<String, Object> map) {
		this.map = map;
	}

	public JSONObject(boolean ordered) {
		this(16, ordered);
	}

	public JSONObject(int initialCapacity) {
		this(initialCapacity, false);
	}

	public JSONObject(int initialCapacity, boolean ordered) {
		if (ordered)
			this.map = new LinkedHashMap(initialCapacity);
		else
			this.map = new HashMap(initialCapacity);
	}

	public int size() {
		return this.map.size();
	}

	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	public boolean containsKey(Object key) {
		return this.map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return this.map.containsValue(value);
	}

	public Object get(Object key) {
		return this.map.get(key);
	}

	public JSONObject getJSONObject(String key) {
		Object value = this.map.get(key);

		if (value instanceof JSONObject) {
			return ((JSONObject) value);
		}

		return ((JSONObject) toJSON(value));
	}

	public JSONArray getJSONArray(String key) {
		Object value = this.map.get(key);

		if (value instanceof JSONArray) {
			return ((JSONArray) value);
		}

		return ((JSONArray) toJSON(value));
	}

	public <T> T getObject(String key, Class<T> clazz) {
		Object obj = this.map.get(key);
		return TypeUtils.castToJavaBean(obj, clazz);
	}

	public Boolean getBoolean(String key) {
		Object value = get(key);

		if (value == null) {
			return null;
		}

		return TypeUtils.castToBoolean(value);
	}

	public byte[] getBytes(String key) {
		Object value = get(key);

		if (value == null) {
			return null;
		}

		return TypeUtils.castToBytes(value);
	}

	public boolean getBooleanValue(String key) {
		Object value = get(key);

		if (value == null) {
			return false;
		}

		return TypeUtils.castToBoolean(value).booleanValue();
	}

	public Byte getByte(String key) {
		Object value = get(key);

		return TypeUtils.castToByte(value);
	}

	public byte getByteValue(String key) {
		Object value = get(key);

		if (value == null) {
			return 0;
		}

		return TypeUtils.castToByte(value).byteValue();
	}

	public Short getShort(String key) {
		Object value = get(key);

		return TypeUtils.castToShort(value);
	}

	public short getShortValue(String key) {
		Object value = get(key);

		if (value == null) {
			return 0;
		}

		return TypeUtils.castToShort(value).shortValue();
	}

	public Integer getInteger(String key) {
		Object value = get(key);

		return TypeUtils.castToInt(value);
	}

	public int getIntValue(String key) {
		Object value = get(key);

		if (value == null) {
			return 0;
		}

		return TypeUtils.castToInt(value).intValue();
	}

	public Long getLong(String key) {
		Object value = get(key);

		return TypeUtils.castToLong(value);
	}

	public long getLongValue(String key) {
		Object value = get(key);

		if (value == null) {
			return 0L;
		}

		return TypeUtils.castToLong(value).longValue();
	}

	public Float getFloat(String key) {
		Object value = get(key);

		return TypeUtils.castToFloat(value);
	}

	public float getFloatValue(String key) {
		Object value = get(key);

		if (value == null) {
			return 0.0F;
		}

		return TypeUtils.castToFloat(value).floatValue();
	}

	public Double getDouble(String key) {
		Object value = get(key);

		return TypeUtils.castToDouble(value);
	}

	public double getDoubleValue(String key) {
		Object value = get(key);

		if (value == null) {
			return 0.0D;
		}

		return TypeUtils.castToDouble(value).doubleValue();
	}

	public BigDecimal getBigDecimal(String key) {
		Object value = get(key);

		return TypeUtils.castToBigDecimal(value);
	}

	public BigInteger getBigInteger(String key) {
		Object value = get(key);

		return TypeUtils.castToBigInteger(value);
	}

	public String getString(String key) {
		Object value = get(key);

		if (value == null) {
			return null;
		}

		return value.toString();
	}

	public java.util.Date getDate(String key) {
		Object value = get(key);

		return TypeUtils.castToDate(value);
	}

	public java.sql.Date getSqlDate(String key) {
		Object value = get(key);

		return TypeUtils.castToSqlDate(value);
	}

	public Timestamp getTimestamp(String key) {
		Object value = get(key);

		return TypeUtils.castToTimestamp(value);
	}

	public Object put(String key, Object value) {
		return this.map.put(key, value);
	}

	public void putAll(Map<? extends String, ? extends Object> m) {
		this.map.putAll(m);
	}

	public void clear() {
		this.map.clear();
	}

	public Object remove(Object key) {
		return this.map.remove(key);
	}

	public Set<String> keySet() {
		return this.map.keySet();
	}

	public Collection<Object> values() {
		return this.map.values();
	}

	public Set<Map.Entry<String, Object>> entrySet() {
		return this.map.entrySet();
	}

	public Object clone() {
		return new JSONObject(new HashMap(this.map));
	}

	public boolean equals(Object obj) {
		return this.map.equals(obj);
	}

	public int hashCode() {
		return this.map.hashCode();
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Class[] parameterTypes = method.getParameterTypes();
		if (parameterTypes.length == 1) {
			if (method.getName().equals("equals")) {
				return Boolean.valueOf(equals(args[0]));
			}

			Class returnType = method.getReturnType();
			if (returnType != Void.TYPE) {
				throw new JSONException("illegal setter");
			}

			String name = null;
			JSONField annotation = (JSONField) method
					.getAnnotation(JSONField.class);
			if ((annotation != null) && (annotation.name().length() != 0)) {
				name = annotation.name();
			}

			if (name == null) {
				name = method.getName();

				if (!(name.startsWith("set"))) {
					throw new JSONException("illegal setter");
				}

				name = name.substring(3);
				if (name.length() == 0) {
					throw new JSONException("illegal setter");
				}
				name = Character.toLowerCase(name.charAt(0))
						+ name.substring(1);
			}

			this.map.put(name, args[0]);
			return null;
		}

		if (parameterTypes.length == 0) {
			Class returnType = method.getReturnType();
			if (returnType == Void.TYPE) {
				throw new JSONException("illegal getter");
			}

			String name = null;
			JSONField annotation = (JSONField) method
					.getAnnotation(JSONField.class);
			if ((annotation != null) && (annotation.name().length() != 0)) {
				name = annotation.name();
			}

			if (name == null) {
				name = method.getName();
				if (name.startsWith("get")) {
					name = name.substring(3);
					if (name.length() == 0) {
						throw new JSONException("illegal getter");
					}
					name = Character.toLowerCase(name.charAt(0))
							+ name.substring(1);
				} else if (name.startsWith("is")) {
					name = name.substring(2);
					if (name.length() == 0) {
						throw new JSONException("illegal getter");
					}
					name = Character.toLowerCase(name.charAt(0))
							+ name.substring(1);
				} else {
					if (name.startsWith("hashCode"))
						return Integer.valueOf(hashCode());
					if (name.startsWith("toString")) {
						return toString();
					}
					throw new JSONException("illegal getter");
				}
			}

			Object value = this.map.get(name);
			return TypeUtils.cast(value, method.getGenericReturnType(),
					ParserConfig.getGlobalInstance());
		}

		throw new UnsupportedOperationException(method.toGenericString());
	}
}
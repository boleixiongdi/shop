package com.rt.common.base;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONStreamAware;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.ParseProcess;
import com.alibaba.fastjson.serializer.AfterFilter;
import com.alibaba.fastjson.serializer.BeforeFilter;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.ThreadLocalCache;
import com.alibaba.fastjson.util.TypeUtils;
import com.baomidou.mybatisplus.annotations.TableField;

public abstract class JSON implements JSONStreamAware, JSONAware {
	@TableField(exist = false)
	public static String DEFAULT_TYPE_KEY = "@type";
	@TableField(exist = false)
	public static int DEFAULT_PARSER_FEATURE;
	@TableField(exist = false)
	public static String DUMP_CLASS = null;
	@TableField(exist = false)
	public static String DEFFAULT_DATE_FORMAT;
	@TableField(exist = false)
	public static int DEFAULT_GENERATE_FEATURE;
	@TableField(exist = false)
	public static final String VERSION = "1.2.4";

	public static final Object parse(String text) {
		return parse(text, DEFAULT_PARSER_FEATURE);
	}

	public static final Object parse(String text, int features) {
		if (text == null) {
			return null;
		}

		DefaultJSONParser parser = new DefaultJSONParser(text,
				ParserConfig.getGlobalInstance(), features);
		Object value = parser.parse();

		parser.handleResovleTask(value);

		parser.close();

		return value;
	}

	public static final Object parse(byte[] input, Feature[] features) {
		return parse(input, 0, input.length, ThreadLocalCache.getUTF8Decoder(),
				features);
	}

	public static final Object parse(byte[] input, int off, int len,
			CharsetDecoder charsetDecoder, Feature[] features) {
		if ((input == null) || (input.length == 0)) {
			return null;
		}

		int featureValues = DEFAULT_PARSER_FEATURE;
		for (Feature featrue : features) {
			featureValues = Feature.config(featureValues, featrue, true);
		}

		return parse(input, off, len, charsetDecoder, featureValues);
	}

	public static final Object parse(byte[] input, int off, int len,
			CharsetDecoder charsetDecoder, int features) {
		charsetDecoder.reset();

		int scaleLength = (int) (len * charsetDecoder.maxCharsPerByte());
		char[] chars = ThreadLocalCache.getChars(scaleLength);

		ByteBuffer byteBuf = ByteBuffer.wrap(input, off, len);
		CharBuffer charBuf = CharBuffer.wrap(chars);
		IOUtils.decode(charsetDecoder, byteBuf, charBuf);

		int position = charBuf.position();

		DefaultJSONParser parser = new DefaultJSONParser(chars, position,
				ParserConfig.getGlobalInstance(), features);
		Object value = parser.parse();

		parser.handleResovleTask(value);

		parser.close();

		return value;
	}

	public static final Object parse(String text, Feature[] features) {
		int featureValues = DEFAULT_PARSER_FEATURE;
		for (Feature featrue : features) {
			featureValues = Feature.config(featureValues, featrue, true);
		}

		return parse(text, featureValues);
	}

	public static final JSONObject parseObject(String text, Feature[] features) {
		return ((JSONObject) parse(text, features));
	}

	public static final JSONObject parseObject(String text) {
		Object obj = parse(text);
		if (obj instanceof JSONObject) {
			return ((JSONObject) obj);
		}

		return ((JSONObject) toJSON(obj));
	}

	public static final <T> T parseObject(String text, TypeReference<T> type,
			Feature[] features) {
		return parseObject(text, type.getType(),
				ParserConfig.getGlobalInstance(), DEFAULT_PARSER_FEATURE,
				features);
	}

	public static final <T> T parseObject(String text, Class<T> clazz,
			Feature[] features) {
		return parseObject(text, clazz, ParserConfig.getGlobalInstance(),
				DEFAULT_PARSER_FEATURE, features);
	}

	public static final <T> T parseObject(String text, Class<T> clazz,
			ParseProcess processor, Feature[] features) {
		return parseObject(text, clazz, ParserConfig.getGlobalInstance(),
				processor, DEFAULT_PARSER_FEATURE, features);
	}

	public static final <T> T parseObject(String input, Type clazz,
			Feature[] features) {
		return parseObject(input, clazz, ParserConfig.getGlobalInstance(),
				DEFAULT_PARSER_FEATURE, features);
	}

	public static final <T> T parseObject(String input, Type clazz,
			ParseProcess processor, Feature[] features) {
		return parseObject(input, clazz, ParserConfig.getGlobalInstance(),
				DEFAULT_PARSER_FEATURE, features);
	}

	public static final <T> T parseObject(String input, Type clazz,
			int featureValues, Feature[] features) {
		if (input == null) {
			return null;
		}

		for (Feature featrue : features) {
			featureValues = Feature.config(featureValues, featrue, true);
		}

		DefaultJSONParser parser = new DefaultJSONParser(input,
				ParserConfig.getGlobalInstance(), featureValues);
		Object value = parser.parseObject(clazz);

		parser.handleResovleTask(value);

		parser.close();

		return (T) value;
	}

	public static final <T> T parseObject(String input, Type clazz,
			ParserConfig config, int featureValues, Feature[] features) {
		return parseObject(input, clazz, config, null, featureValues, features);
	}

	public static final <T> T parseObject(String input, Type clazz,
			ParserConfig config, ParseProcess processor, int featureValues,
			Feature[] features) {
		if (input == null) {
			return null;
		}

		for (Feature featrue : features) {
			featureValues = Feature.config(featureValues, featrue, true);
		}

		DefaultJSONParser parser = new DefaultJSONParser(input, config,
				featureValues);

		if (processor instanceof ExtraTypeProvider) {
			parser.getExtraTypeProviders().add((ExtraTypeProvider) processor);
		}

		if (processor instanceof ExtraProcessor) {
			parser.getExtraProcessors().add((ExtraProcessor) processor);
		}

		Object value = parser.parseObject(clazz);

		parser.handleResovleTask(value);

		parser.close();

		return (T) value;
	}

	public static final <T> T parseObject(byte[] input, Type clazz,
			Feature[] features) {
		return parseObject(input, 0, input.length,
				ThreadLocalCache.getUTF8Decoder(), clazz, features);
	}

	public static final <T> T parseObject(byte[] input, int off, int len,
			CharsetDecoder charsetDecoder, Type clazz, Feature[] features) {
		charsetDecoder.reset();

		int scaleLength = (int) (len * charsetDecoder.maxCharsPerByte());
		char[] chars = ThreadLocalCache.getChars(scaleLength);

		ByteBuffer byteBuf = ByteBuffer.wrap(input, off, len);
		CharBuffer charByte = CharBuffer.wrap(chars);
		IOUtils.decode(charsetDecoder, byteBuf, charByte);

		int position = charByte.position();

		return parseObject(chars, position, clazz, features);
	}

	public static final <T> T parseObject(char[] input, int length, Type clazz,
			Feature[] features) {
		if ((input == null) || (input.length == 0)) {
			return null;
		}

		int featureValues = DEFAULT_PARSER_FEATURE;
		for (Feature featrue : features) {
			featureValues = Feature.config(featureValues, featrue, true);
		}

		DefaultJSONParser parser = new DefaultJSONParser(input, length,
				ParserConfig.getGlobalInstance(), featureValues);
		Object value = parser.parseObject(clazz);

		parser.handleResovleTask(value);

		parser.close();

		return (T) value;
	}

	public static final <T> T parseObject(String text, Class<T> clazz) {
		return parseObject(text, clazz, new Feature[0]);
	}

	public static final JSONArray parseArray(String text) {
		if (text == null) {
			return null;
		}

		DefaultJSONParser parser = new DefaultJSONParser(text,
				ParserConfig.getGlobalInstance());

		JSONLexer lexer = parser.getLexer();
		JSONArray array;
		if (lexer.token() == 8) {
			lexer.nextToken();
			array = null;
		} else {
			if (lexer.token() == 20) {
				array = null;
			} else {
				array = new JSONArray();
				parser.parseArray(array);

				parser.handleResovleTask(array);
			}
		}
		parser.close();

		return array;
	}

	public static final <T> List<T> parseArray(String text, Class<T> clazz) {
		if (text == null) {
			return null;
		}

		DefaultJSONParser parser = new DefaultJSONParser(text,
				ParserConfig.getGlobalInstance());
		JSONLexer lexer = parser.getLexer();
		List list;
		if (lexer.token() == 8) {
			lexer.nextToken();
			list = null;
		} else {
			list = new ArrayList();
			parser.parseArray(clazz, list);

			parser.handleResovleTask(list);
		}

		parser.close();

		return list;
	}

	public static final List<Object> parseArray(String text, Type[] types) {
		if (text == null) {
			return null;
		}

		DefaultJSONParser parser = new DefaultJSONParser(text,
				ParserConfig.getGlobalInstance());
		Object[] objectArray = parser.parseArray(types);
		List list;
		if (objectArray == null)
			list = null;
		else {
			list = Arrays.asList(objectArray);
		}

		parser.handleResovleTask(list);

		parser.close();

		return list;
	}

	public static final String toJSONString(Object object) {
		return toJSONString(object, new SerializerFeature[0]);
	}

	public static final String toJSONString(Object object, SerializerFeature[] features) {
    SerializeWriter out = new SerializeWriter();
    try
    {
      JSONSerializer serializer = new JSONSerializer(out);
      for (SerializerFeature feature : features) {
        serializer.config(feature, true);
      }

      serializer.write(object);

     String xxx = out.toString();

      return xxx; } finally { out.close();
    }
  }

	public static final String toJSONStringWithDateFormat(Object object, String dateFormat, SerializerFeature[] features)
  {
    SerializeWriter out = new SerializeWriter();
    try
    {
      JSONSerializer serializer = new JSONSerializer(out);
      for (SerializerFeature feature : features) {
        serializer.config(feature, true);
      }

      serializer.config(SerializerFeature.WriteDateUseDateFormat, true);

      if (dateFormat != null) {
        serializer.setDateFormat(dateFormat);
      }

      serializer.write(object);

      String xxx = out.toString();

      return xxx; } finally { out.close();
    }
  }

	public static final String toJSONString(Object object, SerializeFilter filter, SerializerFeature[] features) {
    SerializeWriter out = new SerializeWriter();
    try
    {
      JSONSerializer serializer = new JSONSerializer(out);
      for (SerializerFeature feature : features) {
        serializer.config(feature, true);
      }

      serializer.config(SerializerFeature.WriteDateUseDateFormat, true);

      setFilter(serializer, filter);

      serializer.write(object);

      String xxx = out.toString();

      return xxx; } finally { out.close();
    }
  }

	public static final String toJSONString(Object object, SerializeFilter[] filters, SerializerFeature[] features) {
    SerializeWriter out = new SerializeWriter();
    try
    {
      JSONSerializer serializer = new JSONSerializer(out);
      for (SerializerFeature feature : features) {
        serializer.config(feature, true);
      }

      serializer.config(SerializerFeature.WriteDateUseDateFormat, true);

      setFilter(serializer, filters);

      serializer.write(object);

      String xxx = out.toString();

      return xxx; } finally { out.close();
    }
  }

	public static final byte[] toJSONBytes(Object object, SerializerFeature[] features) {
    SerializeWriter out = new SerializeWriter();
    try
    {
      JSONSerializer serializer = new JSONSerializer(out);
      for (SerializerFeature feature : features) {
        serializer.config(feature, true);
      }

      serializer.write(object);

      byte[] xxx = out.toBytes("UTF-8");

      return xxx; } finally { out.close();
    }
  }

	public static final String toJSONString(Object object,
			SerializeConfig config, SerializerFeature[] features) {
		return toJSONString(object, config, (SerializeFilter) null, features);
	}

	public static final String toJSONString(Object object, SerializeConfig config, SerializeFilter filter, SerializerFeature[] features)
  {
    SerializeWriter out = new SerializeWriter();
    try
    {
      JSONSerializer serializer = new JSONSerializer(out, config);
      for (SerializerFeature feature : features) {
        serializer.config(feature, true);
      }

      setFilter(serializer, filter);

      serializer.write(object);

      String xxx = out.toString();

      return xxx; } finally { out.close();
    }
  }

	public static final String toJSONString(Object object, SerializeConfig config, SerializeFilter[] filters, SerializerFeature[] features)
  {
    SerializeWriter out = new SerializeWriter();
    try
    {
      JSONSerializer serializer = new JSONSerializer(out, config);
      for (SerializerFeature feature : features) {
        serializer.config(feature, true);
      }

      setFilter(serializer, filters);

      serializer.write(object);

      String xxx = out.toString();

      return xxx; } finally { out.close();
    }
  }

	public static final String toJSONStringZ(Object object,
			SerializeConfig mapping, SerializerFeature[] features) {
		SerializeWriter out = new SerializeWriter(features);
		try {
			JSONSerializer serializer = new JSONSerializer(out, mapping);

			serializer.write(object);

			String str = out.toString();

			return str;
		} finally {
			out.close();
		}
	}

	public static final byte[] toJSONBytes(Object object, SerializeConfig config, SerializerFeature[] features) {
    SerializeWriter out = new SerializeWriter();
    try
    {
      JSONSerializer serializer = new JSONSerializer(out, config);
      for (SerializerFeature feature : features) {
        serializer.config(feature, true);
      }

      serializer.write(object);

      byte[] xxx = out.toBytes("UTF-8");

      return xxx; } finally { out.close();
    }
  }

	public static final String toJSONString(Object object, boolean prettyFormat) {
		if (!(prettyFormat)) {
			return toJSONString(object);
		}

		return toJSONString(object,
				new SerializerFeature[] { SerializerFeature.PrettyFormat });
	}

	public static final void writeJSONStringTo(Object object, Writer writer,
			SerializerFeature[] features) {
		SerializeWriter out = new SerializeWriter(writer);
		try {
			JSONSerializer serializer = new JSONSerializer(out);
			for (SerializerFeature feature : features) {
				serializer.config(feature, true);
			}

			serializer.write(object);
		} finally {
			out.close();
		}
	}

	public String toString() {
		return toJSONString();
	}

	public String toJSONString() {
		SerializeWriter out = new SerializeWriter();
		try {
			new JSONSerializer(out).write(this);
			String str = out.toString();

			return str;
		} finally {
			out.close();
		}
	}

	public void writeJSONString(Appendable appendable) {
		SerializeWriter out = new SerializeWriter();
		try {
			new JSONSerializer(out).write(this);
			appendable.append(out.toString());
		} catch (IOException e) {
		} finally {
			out.close();
		}
	}

	public static final Object toJSON(Object javaObject) {
		return toJSON(javaObject, ParserConfig.getGlobalInstance());
	}

	public static final Object toJSON(Object javaObject, ParserConfig mapping)
  {
    if (javaObject == null) {
      return null;
    }

    if (javaObject instanceof JSON) {
      return ((JSON)javaObject);
    }

    if (javaObject instanceof Map) {
      Map<String,String> map = (Map)javaObject;

      JSONObject json = new JSONObject(map.size());

      for (Entry<String, String> entry : map.entrySet()) {
        Object key = entry.getKey();
        String jsonKey = TypeUtils.castToString(key);
        Object jsonValue = toJSON(entry.getValue());
        json.put(jsonKey, jsonValue);
      }

      return json;
    }

    if (javaObject instanceof Collection) {
      Collection collection = (Collection)javaObject;

      JSONArray array = new JSONArray(collection.size());

      for (Iterator xxx = collection.iterator(); xxx.hasNext(); ) { Object item = xxx.next();
        Object jsonValue = toJSON(item);
        array.add(jsonValue);
      }

      return array;
    }

    Class clazz = javaObject.getClass();

    if (clazz.isEnum())
      return ((Enum)javaObject).name();
    int i;
    if (clazz.isArray()) {
      int len = Array.getLength(javaObject);

      JSONArray array = new JSONArray(len);

      for (i = 0; i < len; ++i) {
        Object item = Array.get(javaObject, i);
        Object jsonValue = toJSON(item);
        array.add(jsonValue);
      }

      return array;
    }

    if (mapping.isPrimitive(clazz)) {
      return javaObject;
    }
    try
    {
      List<FieldInfo> getters = TypeUtils.computeGetters(clazz, null);

      JSONObject json = new JSONObject(getters.size());

      for (FieldInfo field : getters) {
        Object value = field.get(javaObject);
        Object jsonValue = toJSON(value);

        json.put(field.getName(), jsonValue);
      }

      return json;
    } catch (IllegalAccessException e) {
      throw new JSONException("toJSON error", e);
    } catch (InvocationTargetException e) {
      throw new JSONException("toJSON error", e);
    }
  }

	public static final <T> T toJavaObject(JSON json, Class<T> clazz) {
		return TypeUtils.cast(json, clazz, ParserConfig.getGlobalInstance());
	}

	private static void setFilter(JSONSerializer serializer,
			SerializeFilter[] filters) {
		for (SerializeFilter filter : filters)
			setFilter(serializer, filter);
	}

	private static void setFilter(JSONSerializer serializer,
			SerializeFilter filter) {
		if (filter == null) {
			return;
		}

		if (filter instanceof PropertyPreFilter) {
			serializer.getPropertyPreFilters().add((PropertyPreFilter) filter);
		}

		if (filter instanceof NameFilter) {
			serializer.getNameFilters().add((NameFilter) filter);
		}

		if (filter instanceof ValueFilter) {
			serializer.getValueFilters().add((ValueFilter) filter);
		}

		if (filter instanceof PropertyFilter) {
			serializer.getPropertyFilters().add((PropertyFilter) filter);
		}

		if (filter instanceof BeforeFilter) {
			serializer.getBeforeFilters().add((BeforeFilter) filter);
		}

		if (filter instanceof AfterFilter)
			serializer.getAfterFilters().add((AfterFilter) filter);
	}

	static {
		int features = 0;
		features |= Feature.AutoCloseSource.getMask();
		features |= Feature.InternFieldNames.getMask();
		features |= Feature.UseBigDecimal.getMask();
		features |= Feature.AllowUnQuotedFieldNames.getMask();
		features |= Feature.AllowSingleQuotes.getMask();
		features |= Feature.AllowArbitraryCommas.getMask();
		features |= Feature.SortFeidFastMatch.getMask();
		features |= Feature.IgnoreNotMatch.getMask();
		DEFAULT_PARSER_FEATURE = features;

		DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

		features |= SerializerFeature.QuoteFieldNames.getMask();
		features |= SerializerFeature.SkipTransientField.getMask();
		features |= SerializerFeature.WriteEnumUsingToString.getMask();
		features |= SerializerFeature.SortField.getMask();

		DEFAULT_GENERATE_FEATURE = features;
	}
}
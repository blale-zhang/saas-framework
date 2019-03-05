package org.smr.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.*;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.blade.entities.base.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Fastjson工具类
 */
public class FastJsonUtils {

    protected static Logger logger = LoggerFactory.getLogger(FastJsonUtils.class);

    private static final SerializeConfig config;

    static {
        config = new SerializeConfig();
        config.put(java.util.Date.class, new DataFormatSerializer("yyyy-MM-dd HH:mm:ss"));
        config.put(java.sql.Date.class, new DataFormatSerializer("yyyy-MM-dd HH:mm:ss"));
        config.put(Status.class, new ObjectSerializer() {
            @Override
            public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) throws IOException {
                if (o == null) {
                    jsonSerializer.out.writeNull();
                } else {
                    jsonSerializer.write(((Status)o).getCode());
                }
            }
        });
    }

    private static final SerializerFeature[] serializerFeatures = {
        SerializerFeature.WriteMapNullValue, // 输出空置字段
        SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
        SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
        SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
        SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
        SerializerFeature.DisableCircularReferenceDetect    //禁止循环引用$ref
    };

    private static final SerializeFilter[] serializerFilters = {
        new NullObjectFilter()
    };

    private static final Feature[] features = {
        Feature.IgnoreNotMatch
    };

    public static String toString(Object object) {
        return JSON.toJSONString(object, config, serializerFilters, serializerFeatures);
    }

    public static Object toBean(String text) {
        return JSON.parse(text, features);
    }

    public static <T> T toBean(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz, features);
    }

    public static <T> T toBean(InputStream content, Class<T> clazz) {
        try {
            return JSON.parseObject(content, clazz, features);
        } catch (IOException e) {
            logger.error("InputStream toBean error ", e);
        }
        return null;
    }

    // 转换为数组
    public static <T> Object[] toArray(String text) {
        return toArray(text, null);
    }

    // 转换为数组
    public static <T> Object[] toArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz).toArray();
    }

    // 转换为List
    public static <T> List<T> toList(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

    // 转换为字节
    public static byte[] toBytes(Object object){
        return JSON.toJSONBytes(object);
    }

    // 转换对象
    public static <T> T toBean(byte[] bytes, Class<T> clazz){
        return JSON.parseObject(bytes, clazz);
    }
    
    /**
     * json字符串转化为map
     * @param s
     * @return
     */
    public static Map toMap(String s) {
        Map m = JSONObject.parseObject(s);
        return m;
    }

    /**
     * 获取转换器
     * @return
     */
    public static FastJsonHttpMessageConverter getConverter(){
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializeConfig(config);
        fastJsonConfig.setSerializerFeatures(serializerFeatures);
        fastJsonConfig.setSerializeFilters(serializerFilters);
        fastJsonConfig.setFeatures(features);
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        return fastConverter;
    }


}

/**
 * 日期格式
 */
class DataFormatSerializer extends SimpleDateFormatSerializer {
    public DataFormatSerializer(String pattern) {
        super(pattern);
    }
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if(object == null) {
            serializer.out.writeString("");
        } else {
            super.write(serializer, object, fieldName, fieldType, features);
        }
    }
}

/**
 * Null值过滤
 */
class NullObjectFilter implements ValueFilter {

    private final Object EMPTY_ENTITY = new Object();

    @Override
    public Object process(Object object, String name, Object value) {
        if(value == null){
            try {
                Method method = object.getClass().getMethod("get" + StringUtils.first2Up(name));
                if (BaseEntity.class.isAssignableFrom(method.getReturnType())) {
                    return EMPTY_ENTITY;
                }
            } catch (Exception e){
                return value;
            }
            return "";
        }
        return value;
    }
}


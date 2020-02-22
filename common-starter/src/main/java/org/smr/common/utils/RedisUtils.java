package org.smr.common.utils;

import org.blade.entities.base.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dvnuo on 2017/4/12.
 */
public class RedisUtils {

    private static StringRedisTemplate stringRedisTemplate;

    private static RedisTemplate<String, Object> redisTemplate ;

    /**
     * 删除缓存<br>
     * 根据key精确匹配删除
     * @param key
     */
    public static void del(String... key){
        if(key!=null && key.length > 0){
            if(key.length == 1){
                redisTemplate.delete(key[0]);
            }else{
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 取得缓存（int型）
     * @param key
     * @return
     */
    public static Integer getInt(String key){
        String value = stringRedisTemplate.boundValueOps(key).get();
        if(StringUtils.isNotBlank(value)){
            return Integer.valueOf(value);
        }
        return null;
    }

    /**
     * 取得缓存（字符串类型）
     * @param key
     * @return
     */
    public static String getStr(String key){
        return stringRedisTemplate.boundValueOps(key).get();
    }

    /**
     * 获取缓存<br>
     * 注：基本数据类型(Character除外)，请直接使用get(String key, Class<T> clazz)取值
     * @param key
     * @return
     */
    public static Object getObj(String key){
        return redisTemplate.boundValueOps(key).get();
    }

    /**
     * 获取实体
     * @param entity
     * @param <T>
     * @return
     */
    public static <T> T get(BaseEntity<T> entity) {
        //return (T)redisTemplate.boundValueOps(entity.toRedisKey()).get();
        return null;

    }

    /**
     * 获取缓存<br>
     * 注：该方法暂不支持Character数据类型
     * @param key   key
     * @param clazz 类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key, Class<T> clazz) {
        return (T)redisTemplate.boundValueOps(key).get();
    }

    /**
     * 获取Keys
     * @param pattern
     * @return
     */
    public static Set<String> getKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 保存实体
     * @param entity
     * @param time
     */
    public static void set(BaseEntity entity, long time){
        //set(entity.toRedisKey(), entity, time);
    }

    /**
     * 将value对象写入缓存
     * @param key
     * @param value
     * @param time 失效时间(秒)
     */
    public static void set(String key, Object value, long time){
        if(value instanceof String){
            stringRedisTemplate.opsForValue().set(key, value.toString());
        }else if(value instanceof Integer){
            stringRedisTemplate.opsForValue().set(key, value.toString());
        }else if(value instanceof Double){
            stringRedisTemplate.opsForValue().set(key, value.toString());
        }else if(value instanceof Float){
            stringRedisTemplate.opsForValue().set(key, value.toString());
        }else if(value instanceof Short){
            stringRedisTemplate.opsForValue().set(key, value.toString());
        }else if(value instanceof Long){
            stringRedisTemplate.opsForValue().set(key, value.toString());
        }else if(value instanceof Boolean){
            stringRedisTemplate.opsForValue().set(key, value.toString());
        }else{
            redisTemplate.opsForValue().set(key, value);
        }
        if(time > 0){
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    /**
     * 获取double类型值
     * @param key
     * @return
     */
    public static double getDouble(String key) {
        String value = stringRedisTemplate.boundValueOps(key).get();
        if(StringUtils.isNotBlank(value)){
            return Double.valueOf(value);
        }
        return 0d;
    }

    /**
     * 设置double类型值
     * @param key
     * @param value
     * @param time 失效时间(秒)
     */
    public static void setDouble(String key, double value, long time) {
        stringRedisTemplate.opsForValue().set(key, String.valueOf(value));
        if(time > 0){
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    /**
     * 设置double类型值
     * @param key
     * @param value
     * @param time 失效时间(秒)
     */
    public static void setInt(String key, int value, long time) {
        stringRedisTemplate.opsForValue().set(key, String.valueOf(value));
        if(time > 0){
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    /**
     * 指定缓存的失效时间
     * @param key 缓存KEY
     * @param time 失效时间(秒)
     */
    public static void expire(String key, long time) {
        if(time > 0){
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    /**
     * 判断是否存在key
     * @param key
     * @return
     */
    public static boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 查询过期时间
     * @param key
     * @return
     */
    public static long getExpire(String key) {
        return stringRedisTemplate.getExpire(key);
    }

}

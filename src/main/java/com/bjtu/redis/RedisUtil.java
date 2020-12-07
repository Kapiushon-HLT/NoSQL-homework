package com.bjtu.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisUtil {
    private final Jedis jedis;
    private static final int port = 6379;
    private static final String ip = "localhost";

    public RedisUtil(){
        JedisPoolConfig config = new JedisPoolConfig();


        JedisPool pool = new JedisPool(config, ip, port, 10000);
        jedis = pool.getResource();
    }

    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     */
    public void set(String key, String value) {
        try {
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 普通缓存获取
     * @param key 键
     */
    public String get(String key) {
        try {
            return jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 追加
     * @param key 键
     * @param value 值
     */
    public void append(String key, String value) {
        try {
            jedis.append(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定类型的键
     * @param keys 键
     */
    public void del(String...keys){
        try {
            jedis.del(keys);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过key向指定的set中添加value
     * @param key 键
     * @param members 内容
     */
    public void sadd(String key,String...members){
        try {
            jedis.sadd(key, members);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过key删除set中对应的value值
     * @param key 键
     * @param members 内容
     */
    public void srem(String key,String...members){
        try {
            jedis.srem(key, members);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过key获取set中value的个数
     * @param key 键
     */
    public void scard(String key){
        try {
            jedis.scard(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过key判断value是否是set中的元素
     * @param key 键
     * @param member 内容
     */
    public Boolean sismember(String key,String member){
        try {
            return jedis.sismember(key, member);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过key获取set中随机的value,不删除元素
     * @param key 键
     */
    public String srandmember(String key){
        try {
            return jedis.srandmember(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过key获取set中所有的value
     * @param key 键
     */
    public Set<String> smembers(String key){
        try {
            jedis.smembers(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 递增
     * @param key 键
     * @param delta 递增因子
     */
    public void incr(String key, int delta) {
        try {
            for (int i = 0; i < delta; ++i) {
                jedis.incr(key);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 递减
     * @param key 键
     * @param delta 递减因子
     */
    public void decr(String key, int delta) {
        try {
            for (int i = 0; i > delta; --i) {
                jedis.decr(key);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过key向list头部添加字符串
     * @param key 键
     * @param str 内容
     */
    public void lpush(String key ,String...str){
        try {
            jedis.lpush(key, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过key获取list中指定下标位置的value
     * @param key 键
     * @param index index
     */
    public String lindex(String key,int index){
        try {
            return jedis.lindex(key, index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过key获取list指定下标位置内的value
     * @param key 键
     * @param start 始
     * @param end 终
     */
    public List<String> lrange(String key,long start,long end){
        try {
           return jedis.lrange(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *获取list的个数
     * @param key 键
     */
    public Long llen(String key){
        try {
            return jedis.llen(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过key获取set中的差集
     * @param keys 键
     */
    public Set<String> sdiff(String...keys){
        try {
            return jedis.sdiff(keys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过key同时设置 hash的多个field
     * @param key 键
     * @param hash hash
     */
    public String hmset(String key,Map<String, String> hash){
        try {
            return jedis.hmset(key, hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过key 和 field 获取指定的 value
     * @param key 键
     * @param field field
     */
    public String hget(String key, String field){
        try {
            return jedis.hget(key, field);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过key 和 fields 获取指定的value 如果没有对应的value则返回null
     * @param key 键
     * @param fields fields
     */
    public List<String> hmget(String key,String...fields){
        try {
            return jedis.hmget(key, fields);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过key返回所有的field
     * @param key 键
     */
    public Set<String> hkeys(String key){
        try {
            return jedis.hkeys(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过key返回field的数量
     * @param key 键
     */
    public Long hlen(String key){
        try {
            return jedis.hlen(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 通过key返回所有和key有关的value
     * @param key 键
     */
    public List<String> hvals(String key){
        try {
            return jedis.hvals(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过key 删除指定的 field
     * @param key 键
     * @param fields 内容
     */
    public Long hdel(String key ,String...fields){
        try {
            jedis.hdel(key, fields);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过key向list尾部添加字符串
     * @param key 键
     * @param strs 内容
     */
    public Long rpush(String key ,String...strs){
        try {
            return jedis.rpush(key, strs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}

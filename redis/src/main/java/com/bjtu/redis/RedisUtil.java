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

    public void set(String key, String value) {
        try {
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String get(String key) {
        try {
            return jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void append(String key, String value) {
        try {
            jedis.append(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void del(String...keys){
        try {
            jedis.del(keys);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sadd(String key,String...members){
        try {
            jedis.sadd(key, members);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void srem(String key,String...members){
        try {
            jedis.srem(key, members);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scard(String key){
        try {
            jedis.scard(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean sismember(String key,String member){
        try {
            return jedis.sismember(key, member);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String srandmember(String key){
        try {
            return jedis.srandmember(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Set<String> smembers(String key){
        try {
            jedis.smembers(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void incr(String key, int delta) {
        try {
            for (int i = 0; i < delta; ++i) {
                jedis.incr(key);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decr(String key, int delta) {
        try {
            for (int i = 0; i > delta; --i) {
                jedis.decr(key);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void lpush(String key ,String str){
        try {
            jedis.lpush(key, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String lindex(String key,long index){
        try {
            return jedis.lindex(key, index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> lrange(String key,long start,long end){
        try {
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long llen(String key){
        try {
            return jedis.llen(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Set<String> sdiff(String...keys){
        try {
            return jedis.sdiff(keys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public long getLen(String key){
        return jedis.llen(key);
    }

    public String hmset(String key,Map<String, String> hash){
        try {
            return jedis.hmset(key, hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String hget(String key, String field){
        try {
            return jedis.hget(key, field);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> hmget(String key,String...fields){
        try {
            return jedis.hmget(key, fields);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Set<String> hkeys(String key){
        try {
            return jedis.hkeys(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long hlen(String key){
        try {
            return jedis.hlen(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<String> hvals(String key){
        try {
            return jedis.hvals(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long hdel(String key ,String...fields){
        try {
            jedis.hdel(key, fields);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long rpush(String key ,String...strs){
        try {
            return jedis.rpush(key, strs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}

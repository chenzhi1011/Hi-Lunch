package com.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

//@SpringBootTest
public class SpringDateRedisTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void testRedisTemplate(){
        System.out.println(redisTemplate);
        ValueOperations valueOperations = redisTemplate.opsForValue(); //操作字符串
        HashOperations hashOperations = redisTemplate.opsForHash();
        ListOperations listOperations = redisTemplate.opsForList();
        SetOperations setOperations = redisTemplate.opsForSet();
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
    }

    @Test
    public void testString(){
        //set get setex setnx
        redisTemplate.opsForValue().set("name","beijing");
        String name = (String) redisTemplate.opsForValue().get("name");
        System.out.println(name);

        //setex设置有效期
        redisTemplate.opsForValue().set("city","fujian",3, TimeUnit.MINUTES);

        //setnx
        redisTemplate.opsForValue().setIfAbsent("name","shenyang");

    }


    @Test
    public void testHash(){
        //hset hget hdl hkeys hvals
        HashOperations hashOperations = redisTemplate.opsForHash();
        //hset
        hashOperations.put("100","name","tom");
        hashOperations.put("100","age","20");
        //hget
        hashOperations.get("100","name");

        //hkeys
        hashOperations.keys("100");

        //hvals
        hashOperations.values("100");

        //hdel
        hashOperations.delete("100","age");
    }

    public void testList(){
        //lpush lrange rpop llen
        ListOperations listOperations = redisTemplate.opsForList();
        //lpush
        listOperations.leftPushAll("mylist","a","b","c");
        listOperations.leftPush("mylist","d");

        //lrange
        List list = listOperations.range("mylist",0,-1);

        //rpop
        listOperations.rightPop("mylist");

        //llen
        long size = listOperations.size("mylist");
    }


    public void testSet(){
        //sadd smembers scard sinter sunion srem
        SetOperations setOperations = redisTemplate.opsForSet();

    }

    public void testZset(){
        //zadd zrange zincrby zrem
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

        //zincrby 给指定元素增加xx


    }
}

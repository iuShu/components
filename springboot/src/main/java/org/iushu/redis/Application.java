package org.iushu.redis;

import org.iushu.project.bean.Staff;
import org.iushu.project.service.DefaultStaffService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@SpringBootApplication
public class Application {

    /**
     * @see RedisAutoConfiguration
     * @see org.springframework.boot.autoconfigure.data.redis.RedisProperties
     * @see org.springframework.data.redis.core.RedisTemplate
     * @see org.springframework.data.redis.connection.RedisConnectionFactory
     *
     * Default dependent component is Lettuce
     * @see org.springframework.boot.autoconfigure.data.redis.LettuceConnectionConfiguration
     */
    static void autoconfigure() {

    }

    /**
     * Two RedisTemplates provided by default
     * @see RedisAutoConfiguration#redisTemplate(RedisConnectionFactory)
     * @see org.springframework.data.redis.core.RedisTemplate
     * @see RedisAutoConfiguration#stringRedisTemplate(RedisConnectionFactory)
     * @see org.springframework.data.redis.core.StringRedisTemplate
     *
     * All object needs to be serialized (configure proper serializer for usage)
     * @see org.springframework.data.redis.serializer.RedisSerializer
     * @see org.springframework.data.redis.serializer.JdkSerializationRedisSerializer RedisTemplate by default
     * @see org.springframework.data.redis.serializer.StringRedisSerializer StringRedisTemplate by default
     * @see org.springframework.data.redis.serializer.StringRedisSerializer#UTF_8 default charset
     */
    static void prerequisite() {

    }

    /**
     * Powerby
     * @see org.springframework.data.redis.core.DefaultValueOperations#set(Object, Object)
     * @see org.springframework.data.redis.core.DefaultValueOperations#get(Object)
     * @see org.springframework.data.redis.core.DefaultValueOperations#setBit(Object, long, boolean)
     * @see org.springframework.data.redis.core.DefaultValueOperations#getBit(Object, long)
     * @see org.springframework.data.redis.core.DefaultValueOperations#multiSet(Map)
     * @see org.springframework.data.redis.core.DefaultValueOperations#multiGet(Collection)
     */
    static void simpleStringCase(StringRedisTemplate redisTemplate) {
        redisTemplate.keys("*").forEach(System.out::println);
        Object value = redisTemplate.opsForValue().get("sver");
        System.out.println(">> " + value);
    }

    /**
     * default serializer would serialize the key like 'staff-879211' to '\xac\xed\x00\x05t\x00\x0cstaff-879211'
     * @see org.springframework.data.redis.serializer.JdkSerializationRedisSerializer
     */
    static void objectValueCase(RedisTemplate redisTemplate) {
        Staff staff = DefaultStaffService.createStaff();
        redisTemplate.opsForValue().setIfAbsent("staff-" + staff.getId(), staff);

        Object object = redisTemplate.opsForValue().get("staff-" + staff.getId());
        System.out.println(object.getClass().getName());
        System.out.println((Staff) object);

        redisTemplate.keys("*").forEach(System.out::println);
    }

    /**
     * An common custom RedisTemplate
     * @see RedisCustomConfiguration#redisTemplate(RedisConnectionFactory)
     * @see org.springframework.data.redis.serializer.StringRedisSerializer#UTF_8 key serializer
     * @see org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer value serializer
     * @param redisTemplate
     */
    static void customRedisTemplateCase(RedisTemplate redisTemplate) {
        Staff staff = DefaultStaffService.createStaff();

        redisTemplate.opsForValue().setIfAbsent("staff-" + staff.getId(), staff);

        Object value = redisTemplate.opsForValue().get("staff-" + staff.getId());
        System.out.println(value.getClass().getName());
        System.out.println(value);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class);

        RedisTemplate redisTemplate = (RedisTemplate) context.getBean("redisTemplate");
        StringRedisTemplate stringRedisTemplate = context.getBean(StringRedisTemplate.class);

//        simpleStringCase(stringRedisTemplate);
//        objectValueCase(redisTemplate);
        customRedisTemplateCase(redisTemplate);

        context.close();
    }

}

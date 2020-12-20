package com.javarang.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
//import org.springframework.session.data.redis.config.ConfigureRedisAction;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

//@EnableRedisHttpSession
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60)
public class RedisConfig {

	//@Value("${spring.redis.host}")
    //private String redisHost;

   // @Value("${spring.redis.port}")
   // private int redisPort;

    //@Value("${spring.redis.password}")
    //private String redisPwd;

   /*
    @Bean
    public LettuceConnectionFactory connectionFactory() {
    	 RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();    	 
    	 redisStandaloneConfiguration.setHostName(redisHost); 
    	 redisStandaloneConfiguration.setPort(redisPort); 
      //   redisStandaloneConfiguration.setPassword(redisPwd); 
         LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
         return lettuceConnectionFactory;
    }
    
    */
    
    /*
    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
    */
        
    /*
   
 	@Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Test
    public void test() {
        ValueOperations<String,Object> vop = redisTemplate.opsForValue();

        User user = new User();
        user.setName("codegun");

        vop.set("testkey",user);

        User getUser = (User) vop.get("testkey");
        assertThat(getUser.getName()).isEqualTo("codegun");
    }
   
     */
}
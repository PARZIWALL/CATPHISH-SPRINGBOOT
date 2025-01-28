package com.CatPhish.phishing_web_app.Services;

import io.lettuce.core.dynamic.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class MLModelService {

//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//    @Value("${ml.model.endpoint}")
//    private String mlModelEndpoint;

//    private static final String REDIS_CACHE_PREFIX = "ml_model_result:";

    public boolean predictWithCache(String url) {
        // Check if result exists in Redis
//        Boolean cachedResult = (Boolean) redisTemplate.opsForValue().get(REDIS_CACHE_PREFIX + url);
//
//        if (cachedResult != null) {
//            return cachedResult;  // Return cached result
//        }
//
//        // If not cached, perform the ML model prediction
//        boolean prediction = runMLModel(url);
//
//        // Cache the result for 1 minute
//        redisTemplate.opsForValue().set(REDIS_CACHE_PREFIX + url, prediction, 1, TimeUnit.MINUTES);

//        return prediction;
        return true;
    }

    private boolean runMLModel(String url) {
        // Call ML model (you can use an HTTP client, etc. here)
        // For now, let's assume a dummy logic
//        return url.contains("malicious");  // Example logic
    return true;}
}

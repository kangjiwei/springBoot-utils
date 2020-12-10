package GuavaCache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author kangjiwei
 * @Date   2020/1/20
 */
@Component
public class CacheMap {


    private Cache<String,Object> cache = null;


    public CacheMap(){
        cache =  CacheBuilder.newBuilder().maximumSize(1000)
                .expireAfterWrite(1, TimeUnit.DAYS)
                .build();
    }

    public void put(String key,Object content){
        cache.put(key,content);
    }

    public Object  get(String key){
        return cache.getIfPresent(key);
    }

    public  Object remove(){
        return  null;
    }

}

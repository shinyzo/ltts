package com.lming.ltts.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: zhangliangming
 * @Date: 2021/3/20 1:17.
 * Description:  java vm 的内存进行数据缓存，线程安全
 */
public final class JavaMemoryCache<K,V> implements ICache<K,V> {

    // 保证更新操作的原子性
    private final Map<K,V> map = new ConcurrentHashMap<K,V>();

    private static  JavaMemoryCache instance = null;

    private JavaMemoryCache(){
    }

    /**
     * double check single instance
     * @return
     */
    public static JavaMemoryCache getInstance(){
        if(instance == null){
            synchronized (JavaMemoryCache.class){
                if(instance ==null){
                    instance = new JavaMemoryCache();
                }
            }
        }
        return instance;
    }

    @Override
    public void put(K k, V v) {
        map.put(k,v);
    }

    @Override
    public V get(K k) {
        return map.get(k);
    }

    @Override
    public boolean contains(K k) {
        return get(k) == null ? false : true;
    }

    public static void main(String[] args) {
        JavaMemoryCache<String,String> javaMemoryCache =  JavaMemoryCache.getInstance();
        javaMemoryCache.put("aa","bb");
        javaMemoryCache.put("aa","cc");
        String aa = javaMemoryCache.get("aa");
        System.out.println(aa);
    }
}
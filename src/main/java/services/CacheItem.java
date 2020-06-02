package services;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public abstract class CacheItem<T> {
    protected Instant instant;
    protected T data;

    protected abstract T create();

    /**
     * 缓存有效时间（分钟）
     * @return 缓存的有效时间
     */
    protected long lifeTime() {
        return 10;
    }

    /**
     * 清除缓存
     */
    public final void clear(){
        data = null;
    }

    public T get(){
        if(data == null || Instant.now().minusMillis(TimeUnit.MINUTES.toMillis(lifeTime())).isAfter(instant)){
            data = create();
            instant = Instant.now();
        } else {
            System.out.println("read cache:" + data);
        }

        return data;
    }
}
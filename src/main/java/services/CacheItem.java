package services;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * 缓存的数据，供
 * {@link Cache}调用。
 * @param <T>
 */
public abstract class CacheItem<T> {
    protected Instant instant;
    protected T data;

    /**
     * 创建数据的函数，必须有子类实现
     * @return 数据
     */
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

    /**
     * 获取一个数据，如果没有创建或者超过生存时间，则重新创建
     * @return 数据
     */
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
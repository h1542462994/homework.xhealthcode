package ext.cache;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class CacheCollection<L,T> {
    int maxCount;
    public CacheCollection(int maxCount){
        this.maxCount = maxCount;
    }

    private HashMap<String, CacheItem<T>> collection = new HashMap<>();
    private ArrayList<String> created = new ArrayList<>();

    public T get(L locator){
        CacheItem<T> cacheItem = collection.get(locator.toString());
        if(cacheItem == null){
            cacheItem = new CacheItem<T>() {
                @Override
                protected T create() {
                    return fetch(locator);
                }
            };
            collection.put(locator.toString(), cacheItem);
            created.add(locator.toString());
        }
        free();
        return cacheItem.get();
    }

    void free(){
        while(created.size() > maxCount){
            String lo = created.remove(0);
            collection.remove(lo);
        }
    }

    public void clear(){
        this.collection.clear();
        this.created.clear();
    }

    protected abstract T fetch(L locator);
}

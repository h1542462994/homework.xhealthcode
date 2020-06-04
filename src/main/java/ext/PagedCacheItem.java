package ext;

import ext.cache.CacheItem;
import ext.sql.Paging;

import java.util.ArrayList;
import java.util.HashMap;

@Deprecated
public abstract class PagedCacheItem<T> extends Paging<T> {

    public PagedCacheItem(long eachCount){
        super(eachCount);
    }

    private HashMap<Integer, Item> cache = new HashMap<>();

    public class Item extends CacheItem<ArrayList<T>> {
        public int getPageIndex() {
            return pageIndex;
        }

        private int pageIndex;
        public Item(int pageIndex){
            this.pageIndex = pageIndex;
        }

        @Override
        protected ArrayList<T> create() {
            return page( eachCount * pageIndex, eachCount);
        }
    }

    public int pageCount(){
        return (int)((count() - 1) / eachCount + 1);
    }
    public ArrayList<T> get(int pageIndex) {
        Item item = cache.get(pageIndex);
        if(item == null){
            item = new Item(pageIndex);
            cache.put(pageIndex, item);
        }
        return item.get();
    }
}

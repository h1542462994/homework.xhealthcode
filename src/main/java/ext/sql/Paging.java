package ext.sql;

import java.util.ArrayList;

public abstract class Paging<T> {
    public Paging(long eachCount){
        this.eachCount = eachCount;
    }

    protected abstract long count();
    protected long eachCount;
    public abstract ArrayList<T> page(long start, long count);
}

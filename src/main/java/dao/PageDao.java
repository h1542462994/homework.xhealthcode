package dao;

import java.util.ArrayList;

@Deprecated
public class PageDao<T> {
    private int pageCount;
    private int pageIndex;
    private ArrayList<T> data;

    public PageDao(int pageCount, int pageIndex, ArrayList<T> data) {
        this.pageCount = pageCount;
        this.pageIndex = pageIndex;
        this.data = data;
    }

    public PageDao() {
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public ArrayList<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }
}

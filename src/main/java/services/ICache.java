package services;

import dao.CodeSummaryCollection;
import dao.CollegeDao;

import java.util.ArrayList;

/**
 * 提供数据库的临时缓存能力，由
 * {@link ext.ServiceContainerBase#getService(Class)} )}自动创建。
 */
public interface ICache {
    CacheItem<CodeSummaryCollection> codeSummaryCollectionCache();

    CacheItem<ArrayList<CollegeDao>> collegeDaosCache();

    ArrayList<CollegeDao> collegeDaos();
}

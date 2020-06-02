package services;

import dao.CodeSummaryCollection;
import dao.CollegeDao;

import java.util.ArrayList;

/**
 * 提供临时的缓存服务
 */
public interface ICache {
    CacheItem<ArrayList<CollegeDao>> collegeDaos();

    CacheItem<CodeSummaryCollection> codeSummaryCollection();
}

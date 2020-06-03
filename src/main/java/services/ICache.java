package services;

import dao.*;

import java.util.ArrayList;

/**
 * 提供数据库的临时缓存能力，由
 * {@link ext.ServiceContainerBase#getService(Class)} )}自动创建。
 */
public interface ICache {
    CacheItem<CodeSummaryCollection> codeSummaryCollectionCache();

    CacheItem<ArrayList<CollegeDao>> collegeDaosCache();

    ArrayList<CollegeDao> collegeDaos();

    ArrayList<ProfessionDao> professionDaos(long collegeId);

    ArrayList<XclassDao> xclassDaos(long professionId);

    PageDao<UserResult> userResult(int pageIndex);
}

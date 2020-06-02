package services;

import dao.CodeSummaryCollection;
import dao.CollegeDao;
import ext.exception.ServiceConstructException;

import java.util.ArrayList;

public class Cache implements ICache {
    private ICollegeRepository collegeRepository;

    public Cache(ICollegeRepository collegeRepository){
        this.collegeRepository = collegeRepository;
    }

    private CacheItem<ArrayList<CollegeDao>> collegeDaos = new CacheItem<ArrayList<CollegeDao>>() {
        @Override
        protected ArrayList<CollegeDao> create() {
            return collegeRepository.getColleges();
        }
    };

    private CacheItem<CodeSummaryCollection> codeSummaryCollection = new CacheItem<CodeSummaryCollection>() {
        @Override
        protected CodeSummaryCollection create() {
            return collegeRepository.getSummary();
        }
    };


    @Override
    public CacheItem<ArrayList<CollegeDao>> collegeDaos() {
        return this.collegeDaos;
    }

    @Override
    public CacheItem<CodeSummaryCollection> codeSummaryCollection() {
        return this.codeSummaryCollection;
    }

    /**
     * 用于强制清除缓存
     */
    public static void clearCache(){
        try {
            Cache cache = (Cache) ServiceContainer.get().cache();
            cache.collegeDaos.clear();
            cache.codeSummaryCollection.clear();
        } catch (ServiceConstructException e) {
            e.printStackTrace();
        }
    }
}

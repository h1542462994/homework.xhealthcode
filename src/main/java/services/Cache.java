package services;

import dao.*;
import ext.ServiceContainerBase;
import ext.cache.CacheCollection;
import ext.cache.CacheItem;
import ext.exception.ServiceConstructException;

import java.util.ArrayList;


public class Cache implements ICache {
    private final ICollegeRepository collegeRepository;
    private final IUserRepository userRepository;

    public Cache(IUserRepository userRepository, ICollegeRepository collegeRepository){
        this.userRepository = userRepository;
        this.collegeRepository = collegeRepository;
    }

    private final CacheItem<ArrayList<CollegeDao>> collegeDaosCache = new CacheItem<ArrayList<CollegeDao>>() {
        @Override
        protected ArrayList<CollegeDao> create() {
            return collegeRepository.getCollegesWithFull();
        }
    };

    private final CacheItem<CodeSummaryCollection> codeSummaryCollectionCache = new CacheItem<CodeSummaryCollection>() {
        @Override
        protected CodeSummaryCollection create() {
            return collegeRepository.getSummary();
        }
    };

    private final CacheCollection<ResourceLocator, ArrayList<UserDao>> userResultCache = new CacheCollection<ResourceLocator, ArrayList<UserDao>>(20) {
        @Override
        protected ArrayList<UserDao> fetch(ResourceLocator locator) {
            return userRepository.fromLocator(locator);
        }
    };

    @Override
    public CacheCollection<ResourceLocator, ArrayList<UserDao>> userResultCache(){
        return this.userResultCache;
    }

    @Override
    public CacheItem<CodeSummaryCollection> codeSummaryCollectionCache() {
        return this.codeSummaryCollectionCache;
    }

    @Override
    public CacheItem<ArrayList<CollegeDao>> collegeDaosCache() {
        return collegeDaosCache;
    }

    @Override
    public ArrayList<CollegeDao> collegeDaos() {
        ArrayList<CollegeDao> collegeDaos = this.collegeDaosCache.get();
        CollegeDao.combine(collegeDaos, codeSummaryCollectionCache.get());
        return collegeDaos;
    }

    @Override
    public ArrayList<ProfessionDao> professionDaos(long collegeId) {
        for (CollegeDao collegeDao: collegeDaos()){
            if (collegeDao.getId() == collegeId){
                return collegeDao.getProfessions();
            }
        }
        return null;
    }

    @Override
    public ArrayList<XclassDao> xclassDaos(long professionId){
        for (CollegeDao collegeDao: collegeDaos()){
            for (ProfessionDao professionDao: professionDaos(collegeDao.getId())){
                if(professionDao.getId() == professionId){
                    return professionDao.getXclasses();
                }
            }
        }
        return null;
    }

//    @Override
//    public PageDao<UserResult> userResult(int pageIndex){
//        int pageCount = userResultCache.pageCount();
//        ArrayList<UserResult> data = userResultCache.get(pageIndex);
//        PageDao<UserResult> pageDao = new PageDao<>();
//        pageDao.setPageCount(pageCount);
//        pageDao.setPageIndex(pageIndex);
//        pageDao.setData(data);
//        return pageDao;
//    }

//    @Override
//    public PageDao<UserResult> ofTeachers(int pageIndex){
//        return userResultCache.get(ResourceLocator.teachers().page(pageIndex));
//    }
//
//    @Override
//    public PageDao<UserResult> ofStudents(int pageIndex){
//        return userResultCache.get(ResourceLocator.students().page(pageIndex));
//    }

    @Override
    public ArrayList<UserDao> getUserResultOfLocator(ResourceLocator locator){
        return userResultCache.get(locator);
    }

    /**
     * 用于强制清除缓存
     */
    public static void clearCache(){
        try {
            ICache cache = ServiceContainerBase.get().getService(ICache.class);
            assert cache != null;
            cache.collegeDaosCache().clear();
            cache.codeSummaryCollectionCache().clear();
            cache.userResultCache().clear();
        } catch (ServiceConstructException e) {
            e.printStackTrace();
        }
    }
}

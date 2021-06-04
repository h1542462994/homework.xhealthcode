package test.runtime;

import ext.ServiceContainerBase;
import ext.exception.ServiceConstructException;
import ext.sql.DbContextBase;
import services.*;

public class ServiceContainerTest extends ServiceContainerBase {

    @Override
    protected void injectServices() {
        // 这里使用了h2的数据库服务便于测试

        // 注册数据库存储服务
        this.addSingleton(DbContextBase.class, DbContext.class);
        this.addSingleton(TestHelper.class);
        this.addSingleton(IUserRepository.class, UserRepository.class);
        this.addSingleton(ICollegeRepository.class, CollegeRepository.class);
        this.addSingleton(ICache.class, Cache.class);
        this.addSingleton(IHealthFeedback.class, HealthFeedback.class);
        this.addSingleton(CurrentTimeService.class);
        this.addSingleton(ActionParser.class);
    }

    public DbContext dbContext() throws ServiceConstructException {
        return (DbContext) this.getService(DbContextBase.class);
    }

    public TestHelper testDbHelper() throws ServiceConstructException {
        return this.getService(TestHelper.class);
    }

    public IUserRepository userRepository() throws ServiceConstructException {
        return this.getService(IUserRepository.class);
    }

    public ICollegeRepository collegeRepository() throws ServiceConstructException {
        return this.getService(ICollegeRepository.class);
    }

    public ICache cache() throws ServiceConstructException {
        return this.getService(ICache.class);
    }

    public IHealthFeedback healthFeedback() throws ServiceConstructException {
        return this.getService(IHealthFeedback.class);
    }

    public CurrentTimeService timeService() throws ServiceConstructException {
        return this.getService(CurrentTimeService.class);
    }

    public static ServiceContainerTest get() {
        if (ServiceContainerBase.instance == null) {
            ServiceContainerBase.instance = new ServiceContainerTest();

        }
        return ServiceContainerBase.get();
    }

    public ActionParser actionParser() throws ServiceConstructException {
        return this.getService(ActionParser.class);
    }
}

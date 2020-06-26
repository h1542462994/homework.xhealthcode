package services;

import ext.sql.DbSettings;
import ext.exception.ServiceConstructException;
import ext.ServiceContainerBase;
import ext.sql.DbContextBase;
import ext.declare.ITestService;

import javax.servlet.ServletContext;

/**
 * 声明服务容器的实例
 */
public class ServiceContainer extends ServiceContainerBase {

    /**
     * 必须项：服务容器
     */
    @Override
    protected void injectServices() {
        addTransient(ITestService.class, TestService.class);
        addSingleton(DbContextBase.class, DbContext.class);
        addSingleton(IUserRepository.class, UserRepository.class);
        addSingleton(ICollegeRepository.class, CollegeRepository.class);
        addSingleton(ICache.class, Cache.class);
        addSingleton(IImportAction.class, ImportAction.class);

        addSingleton(IHealthFeedback.class, HealthFeedback.class);
    }

    public IHealthFeedback healthFeedback() throws ServiceConstructException{
        return (HealthFeedback)getService(IHealthFeedback.class);
    }

    public TestService testService() throws ServiceConstructException {
        return (TestService)getService(ITestService.class);
    }

    public IImportAction importAction() throws ServiceConstructException {
        return getService(IImportAction.class);
    }

    public DbContext dbContext() throws ServiceConstructException {
        return (DbContext)getService(DbContext.class);
    }

    public IUserRepository userRepository() throws ServiceConstructException {
        return (IUserRepository)getService(IUserRepository.class);
    }

    public ICollegeRepository collegeRepository() throws ServiceConstructException {
        return (ICollegeRepository)getService(ICollegeRepository.class);
    }

    public ICache cache() throws ServiceConstructException {
        return (ICache) getService(ICache.class);
    }

    /**
     * 创建服务容器并启动单例模式
     * @param context 上下文
     */
    public static void create(ServletContext context){
        instance = new ServiceContainer();
        String sqlDriver = context.getInitParameter("sql-driver");
        String sqlUrl = context.getInitParameter("sql-url");
        String sqlUser = context.getInitParameter("sql-user");
        String sqlPassword = context.getInitParameter("sql-password");
        instance.setConfig("dbsettings", new DbSettings(sqlDriver, sqlUrl, sqlUser, sqlPassword));
    }

    public static ServiceContainer get(){
        return (ServiceContainer)instance;
    }
}

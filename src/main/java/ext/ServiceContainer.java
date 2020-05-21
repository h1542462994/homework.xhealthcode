package ext;

import ext.declare.DbContextBase;
import ext.declare.ITestService;
import ext.service.DbContext;
import ext.service.TestService;

public class ServiceContainer extends ServiceContainerBase {
    @Override
    protected void injectServices() {
        addTransient(ITestService.class, TestService.class);
        addTransient(DbContextBase.class, DbContext.class);
    }

    public TestService testService() throws ServiceConstructException{
        return (TestService)getService(ITestService.class);
    }

    public DbContext dbContext() throws ServiceConstructException {
        return (DbContext)getService(DbContextBase.class);
    }

    public static ServiceContainer get(){
        return ServiceContainerBase.get(ServiceContainer.class);
    }
}

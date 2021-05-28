package test.runtime;

import ext.ServiceContainerBase;
import ext.sql.DbContextBase;
import services.DbContext;

public class ServiceContainerTest extends ServiceContainerBase {

    @Override
    protected void injectServices() {
        // 这里使用了h2的数据库服务便于测试

        // 注册数据库存储服务
        this.addSingleton(DbContextBase.class, DbContext.class);
        this.addSingleton(TestDbHelper.class);
    }

    public static ServiceContainerTest get() {
        if (ServiceContainerBase.instance == null) {
            instance = new ServiceContainerTest();
        }
        return ServiceContainerBase.get();
    }

}

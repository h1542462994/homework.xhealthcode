package test;

import ext.ServiceContainerBase;
import ext.exception.ServiceConstructException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import test.mock.AddService;
import test.mock.AddServiceImpl;
import test.mock.RepeatAddService;
import test.mock.RepeatAddServiceImpl;

/**
 * 测试类[ext.ServiceContainerBase]
 */
public class ServiceContainerBaseTest {

    private ServiceContainerBase container;

    @Before
    public void initialize() {
        container = new ServiceContainerBase() {
            @Override
            protected void injectServices() {
                // not use it.
            }
        };
    }

    /**
     * test singleton service
     */
    @Test
    public void testOneSingleTon() throws ServiceConstructException {
        // register the service
        container.addSingleton(AddService.class, AddServiceImpl.class);

        // get the service
        AddService addService = container.getService(AddService.class);
        assert addService != null;
        Assert.assertEquals(0, addService.current());
        addService.add();
        Assert.assertEquals(1, addService.current());

        AddService addService1 = container.getService(AddService.class);
        assert addService1 != null;
        // reference equal
        assert addService == addService1;
    }

    /**
     * test transient service.
     */
    @Test
    public void testOneTransient() throws ServiceConstructException {
        // register the service
        container.addTransient(AddService.class, AddServiceImpl.class);

        // get the service
        AddService addService1 = container.getService(AddService.class);
        assert addService1 != null;
        AddService addService2 = container.getService(AddService.class);
        assert addService2 != null;

        // reference not equal
        assert addService1 != addService2;
        addService1.add();
        addService2.add();

        Assert.assertEquals(1, addService1.current());
        Assert.assertEquals(1, addService2.current());
    }

    /**
     * test service dependency
     */
    @Test
    public void testDependency() throws ServiceConstructException {
        // register the service
        container.addSingleton(AddService.class, AddServiceImpl.class);
        container.addSingleton(RepeatAddService.class, RepeatAddServiceImpl.class);

        // get the service
        RepeatAddService repeatAddService = container.getService(RepeatAddService.class);
        assert repeatAddService != null;
        repeatAddService.add(5);

        Assert.assertEquals(5, repeatAddService.current());
    }
}


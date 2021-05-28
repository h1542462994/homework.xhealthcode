package test;

import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import ext.sql.DbContextBase;
import models.College;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import services.DbContext;
import test.runtime.ServiceContainerTest;
import test.runtime.TestDbHelper;

import java.io.IOException;

/**
 * test h2 memory db
 */
@FixMethodOrder(MethodSorters.JVM)
public class H2MemoryTest {
    private ServiceContainerTest container;
    private TestDbHelper testDbHelper;
    private DbContext dbContext;

    @Before
    public void before() throws ServiceConstructException {
        container = ServiceContainerTest.get();
        testDbHelper = container.getService(TestDbHelper.class);
        assert testDbHelper != null;
        testDbHelper.storeInMemory();
        dbContext = (DbContext) container.getService(DbContextBase.class);
    }

    @Test
    public void testCreate() throws IOException, OperationFailedException {
        testDbHelper.useFile("sql/structs.sql");
    }


    @Test
    public void testCollegeInsert() throws OperationFailedException, IOException {
        College college = new College();
        college.setName("test");
        Assert.assertTrue(dbContext.colleges.insertOrUpdate(college));
        College find = dbContext.colleges.query("name = ?", "test").unique();
        Assert.assertEquals("test", find.getName());
    }
}

package test;

import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import ext.sql.DbContextBase;
import models.College;
import org.junit.*;
import org.junit.runners.MethodSorters;
import services.DbContext;
import test.runtime.ServiceContainerTest;
import test.runtime.TestDbHelper;

import java.io.IOException;

/**
 * test h2 memory db
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class H2MemoryTest {
    private static ServiceContainerTest container;
    private static TestDbHelper testDbHelper;
    private static DbContext dbContext;

    @BeforeClass
    public static void before() throws ServiceConstructException {
        container = ServiceContainerTest.get();
        testDbHelper = container.getService(TestDbHelper.class);
        assert testDbHelper != null;
        testDbHelper.storeInMemory();
        dbContext = (DbContext) container.getService(DbContextBase.class);
    }

    @Test
    public void test00Create() throws IOException, OperationFailedException {
        testDbHelper.useFile("sql/structs.sql");
    }


    @Test
    public void test01CollegeInsert() throws OperationFailedException, IOException {
        College college = new College();
        college.setName("test");
        Assert.assertTrue(dbContext.colleges.insertOrUpdate(college));
        College find = dbContext.colleges.query("name = ?", "test").unique();
        Assert.assertEquals("test", find.getName());
    }
}

package test;

import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import ext.sql.DbContextBase;
import models.College;
import org.junit.jupiter.api.*;
import services.DbContext;
import test.runtime.ServiceContainerTest;
import test.runtime.TestHelper;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * test h2 memory db
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class H2MemoryTest {
    private static ServiceContainerTest container;
    private static TestHelper testHelper;
    private static DbContext dbContext;

    @BeforeAll
    public static void before() throws ServiceConstructException {
        container = ServiceContainerTest.get();
        testHelper = container.getService(TestHelper.class);
        assert testHelper != null;
        testHelper.storeInMemory();
        dbContext = (DbContext) container.getService(DbContextBase.class);
    }

    @Order(0)
    @Test
    public void testCreate() throws IOException, OperationFailedException {
        testHelper.useFile("test/sql/structs.sql");
    }

    @Order(1)
    @Test
    public void testCollegeInsert() throws OperationFailedException {
        College college = new College();
        college.setName("test");
        assertTrue(dbContext.colleges.insertOrUpdate(college));
        College find = dbContext.colleges.query("name = ?", "test").unique();
        assertEquals("test", find.getName());
    }

    @Order(2)
    @Test
    public void testCollegeDelete() throws OperationFailedException {
        dbContext.colleges.delete(1);
        long count = dbContext.colleges.count();
        assertEquals(0, count);
    }
}

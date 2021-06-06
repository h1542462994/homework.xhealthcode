package test;

import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import test.runtime.ActionParser;
import test.runtime.ServiceContainerTest;
import test.runtime.TestHelper;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HealthFeedBackTest {
    private static ActionParser actionParser;

    @BeforeAll
    public static void beforeAll() throws ServiceConstructException, OperationFailedException, IOException {
        ServiceContainerTest container = ServiceContainerTest.get();
        TestHelper testHelper = container.testDbHelper();
        testHelper.storeInMemory();
        testHelper.useFile("test/sql/structs.sql");
        testHelper.useFile("test/sql/seed_structure.sql");
        testHelper.useFile("test/sql/seed_users.sql");

        actionParser = container.actionParser();
    }

    @Order(1)
    @ParameterizedTest
    @CsvFileSource(resources ="csv/test_health_form.csv", numLinesToSkip = 1)
    public void testForm(String type, String operation, String arg1, String arg2, String arg3) {
        actionParser.dispatch(type, operation, arg1, arg2, arg3);
    }

    @Order(2)
    @ParameterizedTest
    @CsvFileSource(resources = "csv/test_health_acquire.csv", numLinesToSkip = 1)
    public void testHealthAcquire(String type, String operation, String arg1, String arg2, String arg3) {
        actionParser.dispatch(type, operation, arg1, arg2, arg3);
    }

    @Order(3)
    @ParameterizedTest
    @CsvFileSource(resources = "csv/test_health_y2g.csv", numLinesToSkip = 1)
    public void testHealthY2G(String type, String operation, String arg1, String arg2, String arg3) {
        actionParser.dispatch(type, operation, arg1, arg2, arg3);
    }
}

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

import java.io.FileNotFoundException;
import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTest {
    private static ActionParser actionParser;

    @BeforeAll
    public static void beforeAll() throws ServiceConstructException, OperationFailedException, IOException {
        ServiceContainerTest container = ServiceContainerTest.get();
        TestHelper testHelper = container.testDbHelper();
        testHelper.storeInMemory();
        testHelper.useFile("test/sql/structs.sql");
        testHelper.useFile("test/sql/seed1.sql");

        actionParser = container.actionParser();
    }

    @Order(1)
    @ParameterizedTest
    @CsvFileSource(resources = "csv/test_user_operation_1.csv", numLinesToSkip = 1)
    public void testAddSuccess(String type, String operation, String arg1, String arg2, String arg3) throws OperationFailedException, FileNotFoundException {
        actionParser.dispatch(type, operation, arg1, arg2, arg3);
    }

    @Order(2)
    @ParameterizedTest
    @CsvFileSource(resources = "csv/test_user_operation_2.csv", numLinesToSkip = 1)
    public void testAddRepeatedly(String type, String operation, String arg1, String arg2, String arg3) {
        actionParser.dispatch(type, operation, arg1, arg2, arg3);
    }


}

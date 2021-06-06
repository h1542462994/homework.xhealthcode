package test;

import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import test.runtime.ActionParser;
import test.runtime.ServiceContainerTest;
import test.runtime.TestHelper;

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
        testHelper.useFile("test/sql/seed_structure.sql");

        actionParser = container.actionParser();
    }


    /**
     * test.csv Route 1->2->#
     */
    @Order(1)
    @DisplayName("testRoute: 1->2->#")
    @ParameterizedTest
    @CsvFileSource(resources = "csv/test_user_operation_01.csv", numLinesToSkip = 1)
    public void test01(String type, String operation, String arg1, String arg2, String arg3) {
        actionParser.dispatch(type, operation, arg1, arg2, arg3);
    }

    @Order(2)
    @DisplayName("testRoute: 1->2->3->#")
    @ParameterizedTest
    @CsvFileSource(resources = "csv/test_user_operation_02.csv", numLinesToSkip = 1)
    public void test02(String type, String operation, String arg1, String arg2, String arg3) {
        actionParser.dispatch(type, operation, arg1, arg2, arg3);
    }

    @Order(3)
    @DisplayName("testRoute: 1->2->3->4->#")
    @ParameterizedTest
    @CsvFileSource(resources = "csv/test_user_operation_03.csv", numLinesToSkip = 1)
    public void test03(String type, String operation, String arg1, String arg2, String arg3) {
        actionParser.dispatch(type, operation, arg1, arg2, arg3);
    }

    @Order(4)
    @DisplayName("testRoute: 1->5->6->#")
    @ParameterizedTest
    @CsvFileSource(resources = "csv/test_user_operation_04.csv", numLinesToSkip = 1)
    public void test04(String type, String operation, String arg1, String arg2, String arg3) {
        actionParser.dispatch(type, operation, arg1, arg2, arg3);
    }

    @Order(5)
    @DisplayName("testRoute: 1->5->6->7->8->#")
    @ParameterizedTest
    @CsvFileSource(resources = "csv/test_user_operation_05.csv", numLinesToSkip = 1)
    public void test05(String type, String operation, String arg1, String arg2, String arg3) {
        actionParser.dispatch(type, operation, arg1, arg2, arg3);
    }

    @Order(6)
    @DisplayName("testRoute: 1->5->6->7->8->9->#")
    @ParameterizedTest
    @CsvFileSource(resources = "csv/test_user_operation_06.csv", numLinesToSkip = 1)
    public void test06(String type, String operation, String arg1, String arg2, String arg3) {
        actionParser.dispatch(type, operation, arg1, arg2, arg3);
    }

    @Order(7)
    @DisplayName("testRoute: 1->5->6->7->8->9->#")
    @ParameterizedTest
    @CsvFileSource(resources = "csv/test_user_operation_07.csv", numLinesToSkip = 1)
    public void test07(String type, String operation, String arg1, String arg2, String arg3) {
        actionParser.dispatch(type, operation, arg1, arg2, arg3);
    }


}

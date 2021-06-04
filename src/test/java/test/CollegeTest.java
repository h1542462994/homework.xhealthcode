package test;

import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import test.runtime.ActionParser;
import test.runtime.ServiceContainerTest;
import test.runtime.TestHelper;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 主要测试ICollegeRepository的正确性
 */
public class CollegeTest {
    private static ActionParser actionParser;

    @BeforeAll
    public static void beforeAll() throws ServiceConstructException, OperationFailedException, IOException {
        ServiceContainerTest container = ServiceContainerTest.get();
        TestHelper testHelper = container.testDbHelper();
        testHelper.storeInMemory();
        testHelper.useFile("test/sql/structs.sql");
        //testDbHelper.useFile("test/sql/seed_structure.sql");

        actionParser = container.actionParser();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "csv/test_college_operation.csv", numLinesToSkip = 1)
    public void test(String type, String operation, String key, String name, String super_name) throws OperationFailedException, FileNotFoundException {
        actionParser.dispatch(type, operation, key, name, super_name);
    }
}

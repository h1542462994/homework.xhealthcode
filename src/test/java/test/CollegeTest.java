package test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import models.College;
import models.Profession;
import models.Xclass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import services.DbContext;
import services.ICollegeRepository;
import test.runtime.ActionParser;
import test.runtime.ServiceContainerTest;
import test.runtime.TestHelper;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 主要测试ICollegeRepository的正确性
 */
public class CollegeTest {
    private static ServiceContainerTest container;
    private static ICollegeRepository collegeRepository;
    private static TestHelper testHelper;
    private static DbContext dbContext;
    private static ActionParser actionParser;

    @BeforeAll
    public static void before() throws ServiceConstructException, OperationFailedException, IOException {
        container = ServiceContainerTest.get();
        testHelper = container.testDbHelper();
        testHelper.storeInMemory();
        testHelper.useFile("test/sql/structs.sql");
        //testDbHelper.useFile("test/sql/seed1.sql");

        dbContext = container.dbContext();
        collegeRepository = container.collegeRepository();
        actionParser = container.actionParser();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "csv/test_college_operation.csv", numLinesToSkip = 1)
    public void test(String type, String operation, String key, String name, String super_name) throws OperationFailedException, FileNotFoundException {
        actionParser.test(type, operation, key, name, super_name);
    }




}

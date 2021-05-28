package test;

import dao.CollegeDao;
import dao.ProfessionDao;
import dao.XclassDao;
import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import services.ICollegeRepository;
import test.runtime.ServiceContainerTest;
import test.runtime.TestDbHelper;

import java.io.IOException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CollegeTest {
    private static ServiceContainerTest container;
    private static ICollegeRepository collegeRepository;
    private static TestDbHelper testDbHelper;

    @BeforeClass
    public static void before() throws ServiceConstructException, OperationFailedException, IOException {
        container = ServiceContainerTest.get();
        testDbHelper = container.testDbHelper();
        testDbHelper.storeInMemory();
        testDbHelper.useFile("sql/structs.sql");
        testDbHelper.useFile("sql/seed1.sql");

        collegeRepository = container.collegeRepository();
    }



    @Test
    public void test00Read() {
        CollegeDao college = collegeRepository.getCollege(1);
        Assert.assertEquals("计算机科学与技术学院", college.getName());
        ProfessionDao profession = collegeRepository.getProfession(1);
        Assert.assertEquals("软件工程", profession.getName());

    }

}

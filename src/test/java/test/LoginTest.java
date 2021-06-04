package test;

import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import ext.exception.ValidateFailedException;
import ext.validation.ValidateRule;
import ext.validation.ValidateRuleUnit;
import ext.validation.unit.ValidateRegion;
import ext.validation.unit.ValidateRequired;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import requests.UserLogin;
import test.runtime.ActionParser;
import test.runtime.ServiceContainerTest;
import test.runtime.TestHelper;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest {

    private static ActionParser actionParser;

    @BeforeAll
    public static void beforeAll() throws ServiceConstructException, OperationFailedException, IOException {
        ServiceContainerTest container = new ServiceContainerTest();
        TestHelper testHelper = container.testDbHelper();
        testHelper.storeInMemory();
        testHelper.useFile("test/sql/structs.sql");
        testHelper.useFile("test/sql/seed_structure.sql");
        testHelper.useFile("test/sql/seed_users.sql");

        actionParser = container.actionParser();
    }

    @Order(1)
    @ParameterizedTest
    @CsvFileSource(resources = "csv/test_login_validate.csv", numLinesToSkip = 1)
    public void testForm(String checkResult, String type, String name, String number, String passport) throws IllegalAccessException {
        boolean check = Boolean.parseBoolean(checkResult);
        UserLogin login = new UserLogin();
        login.type = Integer.parseInt(type);
        login.name = name;
        login.number = number;
        login.passport = passport;

        ValidateRule rule;
        if (login.type == UserLogin.STUDENT || login.type == UserLogin.TEACHER) {
            rule = new ValidateRule(
                    new ValidateRuleUnit("number",
                            new ValidateRequired(),
                            new ValidateRegion(6,20)),
                    new ValidateRuleUnit("name",
                            new ValidateRequired(),
                            new ValidateRegion(2,10)),
                    new ValidateRuleUnit("passport",
                            new ValidateRequired(),
                            new ValidateRegion(6,6))
            );
        } else {
            rule = new ValidateRule(
                    new ValidateRuleUnit("number",
                            new ValidateRequired(),
                            new ValidateRegion(6,20)),
                    new ValidateRuleUnit("passport",
                            new ValidateRequired(),
                            new ValidateRegion(6,20))
            );
        }

        boolean actual = false;
        try {
            rule.validate(login);
            actual = true;
        } catch (ValidateFailedException ignored) {
        }

        assertEquals(check, actual);
    }

    @Order(2)
    @ParameterizedTest
    @CsvFileSource(resources = "csv/test_login_login.csv", numLinesToSkip = 1)
    public void testLogin(String type, String operation, String arg1, String arg2, String arg3) {
        actionParser.dispatch(type, operation, arg1, arg2, arg3);
    }

}

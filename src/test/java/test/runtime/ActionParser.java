package test.runtime;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import dao.UserDao;
import enums.TypeType;
import ext.exception.OperationFailedException;
import ext.sql.DbContextBase;
import models.*;
import requests.UserAcquire;
import requests.UserLogin;
import requests.UserRequest;
import services.DbContext;
import services.ICollegeRepository;
import services.IHealthFeedback;
import services.IUserRepository;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 对csv作为源的action的解释器
 */
public class ActionParser {
    //region define
    private final ICollegeRepository collegeRepository;
    private final IUserRepository userRepository;
    private final IHealthFeedback healthFeedback;
    private final DbContext dbContext;
    private final TestHelper testHelper;
    private UserAccess currentUser;
    private final MockFactory mockFactory = new MockFactory();

    public ActionParser(ICollegeRepository collegeRepository, IUserRepository userRepository, IHealthFeedback healthFeedback, DbContextBase dbContext, TestHelper testHelper) {
        this.collegeRepository = collegeRepository;
        this.userRepository = userRepository;
        this.healthFeedback = healthFeedback;
        this.dbContext = (DbContext) dbContext;
        this.testHelper = testHelper;
    }
    //endregion



    //region global
    public void dispatch(String type, String operation, String arg1, String arg2, String arg3) {
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            ActType actType = method.getAnnotation(ActType.class);
            if (actType != null) {
                boolean isThisMethod = type.equals(actType.type()) && operation.equals(actType.operation());
                if (isThisMethod) {
                    int parameterCount = method.getParameterCount();
                    try {
                        if (parameterCount == 1) {
                            method.invoke(this, arg1);
                        } else if (parameterCount == 2) {
                            method.invoke(this, arg1, arg2);
                        } else if (parameterCount == 3) {
                            method.invoke(this, arg1, arg2, arg3);
                        } else {
                            throw new IllegalArgumentException("parameterCount error");
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        throw new IllegalArgumentException("method definition error", e);
                    }
                    return;
                }
            }
        }
        throw new IllegalArgumentException("method not found");
    }

    //endregion

    //region collegeTest

    @ActType(type = "college", operation = "add")
    public void doCollegeAdd(String key, String name) {
        College college = new College();
        college.setCollegeId(Long.parseLong(key));
        college.setName(name);
        assert collegeRepository != null;

        assertNotNull(collegeRepository.addCollege(college));
    }

    @ActType(type = "college", operation = "delete")
    public void doCollegeDelete(String key) {
        Long collegeId = Long.parseLong(key);
        collegeRepository.deleteCollege(collegeId);
        assertNull(collegeRepository.getCollege(collegeId));
    }

    @ActType(type = "profession", operation = "add")
    public void doProfessionAdd(String key, String name, String super_name) throws OperationFailedException {
        College college = dbContext.colleges.query("name = ?", super_name).unique();
        assertNotNull(college);

        Profession profession = new Profession();
        profession.setProfessionId(Long.parseLong(key));
        profession.setCollegeId(college.getCollegeId());
        profession.setName(name);

        assertNotNull(collegeRepository.addProfession(profession));
    }

    @ActType(type = "profession", operation = "delete")
    public void doProfessionDelete(String key) {
        Long professionId = Long.parseLong(key);
        collegeRepository.deleteCollege(professionId);
        assertNull(collegeRepository.getCollege(professionId));
    }

    @ActType(type = "xclass", operation = "add")
    public void doXclassAdd(String key, String name, String super_name) throws OperationFailedException {
        Profession profession = dbContext.professions.query("name = ?", super_name).unique();
        assertNotNull(profession);

        Xclass xclass = new Xclass();
        xclass.setProfessionId(profession.getProfessionId());
        xclass.setXclassId(Long.parseLong(key));
        xclass.setName(name);

        assertNotNull(collegeRepository.addXclass(xclass));
    }

    @ActType(type = "xclass", operation = "delete")
    public void doXclassDelete(String key) {
        long xclassId = Long.parseLong(key);
        collegeRepository.deleteXclass(xclassId);
        //assertNull(collegeRepository.get(xclassId));
    }

    @ActType(type = "check", operation = "structure")
    public void doCheckStructure(String key) throws FileNotFoundException {
        Gson gson = testHelper.gson();

        JsonElement actual = gson.toJsonTree(collegeRepository.getCollegesWithFull());
        JsonElement expected = testHelper.loadResultWithToken(key);

        System.out.println(gson.toJson(actual));

        assertEquals(expected, actual);
    }

    @ActType(type = "check", operation = "count-college")
    public void doCheckCollegeCount(String key) {
        long expected = Long.parseLong(key);
        long actual = collegeRepository.getCollegesWithFull().size();
        assertEquals(expected, actual);
    }

    @ActType(type = "check", operation = "value-college")
    public void doCheckValueCollege(String key, String arg) throws OperationFailedException, FileNotFoundException {
        String[] tokens = key.split(",");
        String p = tokens[0];
        String v = tokens[1];
        College college = null;
        if ("id".equals(p)) {
            college = dbContext.colleges.query("collegeId = ?", Long.parseLong(v)).unique();
        } else if ("name".equals(p)) {
            college = dbContext.colleges.query("name = ?", v).unique();
        }

        assertNotNull(college);
        JsonElement expected = testHelper.gson().toJsonTree(college);
        System.out.println(testHelper.gson().toJson(expected));

        JsonElement actual = testHelper.loadResultWithToken(arg);


        assertEquals(expected, actual);
    }

    @ActType(type = "check", operation = "count-profession")
    public void doCheckProfessionCount(String key, String arg) {
        long expected = Long.parseLong(arg);
        long collegeId = Long.parseLong(key);
        long actual = collegeRepository.getProfessions(collegeId).size();
        assertEquals(expected, actual);
    }

    @ActType(type = "check", operation = "value-profession")
    public void doCheckValueProfession(String key, String arg) throws OperationFailedException, FileNotFoundException {
        String[] tokens = key.split(",");
        String p = tokens[0];
        String v = tokens[1];
        Profession profession = null;
        if ("id".equals(p)) {
            profession = dbContext.professions.query("professionId = ?", Long.parseLong(v)).unique();
        } else if ("name".equals(p)) {
            profession = dbContext.professions.query("name = ?", v).unique();
        }

        assertNotNull(profession);
        JsonElement expected = testHelper.gson().toJsonTree(profession);
        System.out.println(testHelper.gson().toJson(expected));

        JsonElement actual = testHelper.loadResultWithToken(arg);


        assertEquals(expected, actual);
    }

    @ActType(type = "check", operation = "count-xclass")
    public void doCheckXclassCount(String key, String arg) {
        long expected = Long.parseLong(arg);
        long professionId = Long.parseLong(key);
        long actual = collegeRepository.getXclasses(professionId).size();
        assertEquals(expected, actual);
    }

    @ActType(type = "check", operation = "value-xclass")
    public void doCheckValueXclass(String key, String arg) throws OperationFailedException, FileNotFoundException {
        String[] tokens = key.split(",");
        String p = tokens[0];
        String v = tokens[1];
        Xclass xclass = null;
        if ("id".equals(p)) {
            xclass = dbContext.xclasses.query("xclassId = ?", Long.parseLong(v)).unique();
        } else if ("name".equals(p)) {
            xclass = dbContext.xclasses.query("name = ?", v).unique();
        }

        assertNotNull(xclass);
        JsonElement expected = testHelper.gson().toJsonTree(xclass);
        System.out.println(testHelper.gson().toJson(expected));

        JsonElement actual = testHelper.loadResultWithToken(arg);


        assertEquals(expected, actual);
    }
    //endregion

    //region userTest
    @ActType(type = "user", operation = "add")
    public void doUserAdd(String roleType, String from, String arg) {
        doActionAddJudged(roleType, from, arg, true);
    }

    @ActType(type = "user", operation = "add-fail")
    public void doUserAddFail(String roleType, String from, String arg){
        doActionAddJudged(roleType, from, arg, false);
    }

    public void doActionAddJudged(String roleType, String from, String arg, boolean success) {
        UserRequest userRequest = new UserRequest();
        Map<String, String> args = testHelper.toParameterMap(arg);

        if ("student".equals(roleType)) {
            userRequest.setType(TypeType.STUDENT);
            userRequest.setField(from);
            userRequest.setName(args.get("name"));
            userRequest.setNumber(args.get("number"));
            userRequest.setIdCard(args.get("idCard"));
            assertEquals(success, userRepository.insertOrUpdateUser(userRequest, -1));
        } else if ("teacher".equals(roleType)) {
            userRequest.setType(TypeType.TEACHER);
            userRequest.setField(from);
            userRequest.setName(args.get("name"));
            userRequest.setNumber(args.get("number"));
            userRequest.setIdCard(args.get("idCard"));
            assertEquals(success, userRepository.insertOrUpdateUser(userRequest, -1));
        } else if ("admin".equals(roleType)) {
            userRequest.setType(TypeType.ADMIN);
            userRequest.setAdminType(args.get("adminType"));
            if (args.get("adminType").equals("0")) {
                userRequest.setField(from);
            }
            userRequest.setName(args.get("name"));
            userRequest.setNumber(args.get("number"));
            userRequest.setIdCard(args.get("idCard"));
            userRequest.setPassport(args.get("passport"));
            assertEquals(success, userRepository.insertOrUpdateUser(userRequest, -1));
        } else {
            throw new IllegalArgumentException("roleType not support.");
        }
    }

    @ActType(type = "user", operation = "delete")
    public void doUserDelete(String roleType, String number) throws OperationFailedException {
        if ("student".equals(roleType)) {
            Student student = dbContext.students.query("number = ?", number).unique();
            assertNotNull(student);
            userRepository.delete(student.getUserId());
        } else if ("teacher".equals(roleType)) {
            Teacher teacher = dbContext.teachers.query("number = ?", number).unique();
            userRepository.delete(teacher.getUserId());
        } else if ("admin".equals(roleType)) {
            Teacher teacher = dbContext.teachers.query("number = ?", number).unique();
            userRepository.delete(teacher.getUserId());
        } else {
            throw new IllegalArgumentException("roleType not support.");
        }
    }
    //endregion

    //region loginTest
    @ActType(type = "user", operation = "login")
    public void doUserLogin(String roleType, String arg){
        doUserLoginJudged(roleType, arg, true);
    }

    @ActType(type = "user", operation = "login-fail")
    public void doUserLoginFail(String roleType, String arg) {
        doUserLoginJudged(roleType, arg, false);
    }

    public void doUserLoginJudged(String roleType, String arg, boolean success) {
        UserLogin userLogin = new UserLogin();
        if ("student".equals(roleType)) {
            userLogin.type = UserLogin.STUDENT;
        } else if ("teacher".equals(roleType)) {
            userLogin.type = UserLogin.TEACHER;
        } else if ("admin".equals(roleType)){
            userLogin.type = UserLogin.ADMIN;
        } else {
            throw new IllegalArgumentException("roleType not support.");
        }

        Map<String, String> args = testHelper.toParameterMap(arg);
        userLogin.name = args.get("name");
        userLogin.number = args.get("number");
        userLogin.passport = args.get("passport");

        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        //Mockito.doNothing().when(response).addCookie(null);

        UserAccess userAccess = userRepository.login(userLogin, response);

        if (success) {
            assertNotNull(userAccess);
            this.currentUser = userAccess;
            assertEquals(userAccess.getToken(), mockFactory.getMarkToken());
            System.out.println(userAccess.getToken());
        } else {
            assertNull(userAccess);
        }
    }

    @ActType(type = "user", operation = "active")
    public void doUserActive(String roleType, String number) {
        if ("none".equals(roleType)) {
            assertNull(currentUser);
        }

        assert currentUser != null;
        var request = mockFactory.mockRequest(currentUser.getToken());
        UserAccess userAccess = userRepository.active(request);
        assertEquals(currentUser.getUserId(), userAccess.getUserId());

        UserDao userDao = userRepository.get(currentUser.getUserId());
        System.out.println(testHelper.gson().toJson(userDao));

        assertEquals(number, userDao.getNumber());

        if ("student".equals(roleType)) {
            assertEquals(TypeType.STUDENT, userDao.getType());
        } else if ("teacher".equals(roleType)) {
            assertEquals(TypeType.TEACHER, userDao.getType());
        } else if ("admin".equals(roleType)) {
            assertEquals(TypeType.ADMIN, userDao.getType());
        }
    }

    @ActType(type = "user", operation = "logout")
    public void doUserLogout(String ignore) {
        assertNotNull(currentUser);

        userRepository.logout(mockFactory.mockRequest(currentUser.getToken()), mockFactory.mockResponse());
        assertNull(mockFactory.getMarkToken());
    }

    @ActType(type = "health", operation = "test")
    public void doHealthTest(String result, String arg) {
        var args = testHelper.toParameterMap(arg);
        UserAcquire userAcquire = new UserAcquire();

    }

    //endregion
}

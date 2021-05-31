package test.runtime;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import enums.TypeType;
import ext.exception.OperationFailedException;
import ext.sql.DbContextBase;
import models.College;
import models.Profession;
import models.Student;
import models.Xclass;
import requests.UserRequest;
import services.DbContext;
import services.ICollegeRepository;
import services.IUserRepository;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 对csv作为源的action的解释器
 */
public class ActionParser {
    //region define
    private final ICollegeRepository collegeRepository;
    private final IUserRepository userRepository;
    private final DbContext dbContext;
    private final TestHelper testHelper;

    public ActionParser(ICollegeRepository collegeRepository, IUserRepository userRepository, DbContextBase dbContext, TestHelper testHelper) {
        this.collegeRepository = collegeRepository;
        this.userRepository = userRepository;
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
        var args = testHelper.toParameterMap(arg);

        if ("student".equals(roleType)) {
            userRequest.setType(TypeType.STUDENT);
            userRequest.setField(from);
            userRequest.setName(args.get("name"));
            userRequest.setNumber(args.get("number"));
            userRequest.setIdCard(args.get("idCard"));
            assertEquals(success, userRepository.insertOrUpdateUser(userRequest, -1));
        }
    }

    @ActType(type = "user", operation = "delete")
    public void doUserDelete(String roleType, String number) throws OperationFailedException {
        if ("student".equals(roleType)) {
            Student student = dbContext.students.query("number = ?", number).unique();
            assertNotNull(student);
            userRepository.delete(student.getUserId());
        }
    }
    //endregion
}

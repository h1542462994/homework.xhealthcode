package test.runtime;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import ext.exception.OperationFailedException;
import ext.sql.DbContextBase;
import models.College;
import models.Profession;
import models.Xclass;
import services.DbContext;
import services.ICollegeRepository;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 对csv作为源的action的解释器
 */
public class ActionParser {
    private final ICollegeRepository collegeRepository;
    private final DbContext dbContext;
    private final TestHelper testHelper;

    public ActionParser(ICollegeRepository collegeRepository, DbContextBase dbContext, TestHelper testHelper) {
        this.collegeRepository = collegeRepository;
        this.dbContext = (DbContext) dbContext;
        this.testHelper = testHelper;
    }

    public void test(String type, String operation, String key, String name, String super_name) throws OperationFailedException, FileNotFoundException {
        if ("college".equals(type)) {
            doCollege(operation, key, name);
        } else if ("profession".equals(type)) {
            doProfession(operation, key, name, super_name);
        } else if ("xclass".equals(type)) {
            doXclass(operation, key, name, super_name);
        } else if ("check".equals(type)) {
            doCheck(operation, key, name);
        } else {
            throw new IllegalArgumentException("type " + type + " not supported");
        }
    }

    /**
     * college,*
     */
    public void doCollege(String operation, String key, String name) {
        if ("add".equals(operation)) {
            doCollegeAdd(key, name);
        } else if ("delete".equals(operation)) {
            doCollegeDelete(key);
        } else {
            throw new IllegalArgumentException("operation " + operation + " not supported");
        }
    }

    public void doCollegeAdd(String key, String name) {
        College college = new College();
        college.setCollegeId(Long.parseLong(key));
        college.setName(name);
        assert collegeRepository != null;

        assertNotNull(collegeRepository.addCollege(college));
    }

    public void doCollegeDelete(String key) {
        Long collegeId = Long.parseLong(key);
        collegeRepository.deleteCollege(collegeId);
        assertNull(collegeRepository.getCollege(collegeId));
    }

    public void doProfession(String operation, String key, String name, String super_name) throws OperationFailedException {
        if ("add".equals(operation)) {
            doProfessionAdd(key, name, super_name);
        } else if("delete".equals(operation)) {
            doProfessionDelete(key);
        } else {
            throw new IllegalArgumentException("operation " + operation + " not supported");
        }
    }

    private void doProfessionAdd(String key, String name, String super_name) throws OperationFailedException {
        College college = dbContext.colleges.query("name = ?", super_name).unique();
        assertNotNull(college);

        Profession profession = new Profession();
        profession.setProfessionId(Long.parseLong(key));
        profession.setCollegeId(college.getCollegeId());
        profession.setName(name);

        assertNotNull(collegeRepository.addProfession(profession));
    }

    private void doProfessionDelete(String key) {
        Long professionId = Long.parseLong(key);
        collegeRepository.deleteCollege(professionId);
        assertNull(collegeRepository.getCollege(professionId));
    }

    private void doXclass(String operation, String key, String name, String super_name) throws OperationFailedException {
        if ("add".equals(operation)) {
            doXclassAdd(key, name, super_name);
        } else if ("delete".equals(operation)) {
            doXclassDelete(key);
        } else {
            throw new IllegalArgumentException("operation " + operation + " not supported");
        }
    }

    private void doXclassAdd(String key, String name, String super_name) throws OperationFailedException {
        Profession profession = dbContext.professions.query("name = ?", super_name).unique();
        assertNotNull(profession);

        Xclass xclass = new Xclass();
        xclass.setProfessionId(profession.getProfessionId());
        xclass.setXclassId(Long.parseLong(key));
        xclass.setName(name);

        assertNotNull(collegeRepository.addXclass(xclass));
    }

    private void doXclassDelete(String key) {
        long xclassId = Long.parseLong(key);
        collegeRepository.deleteXclass(xclassId);
        //assertNull(collegeRepository.get(xclassId));
    }

    private void doCheck(String operation, String key, String arg) throws FileNotFoundException, OperationFailedException {
        if ("structure".equals(operation)) {
            doCheckStructure(key);
        } else if ("counts-college".equals(operation)) {
            doCheckCollegeCounts(key);
        } else if ("value-college".equals(operation)) {
            doCheckValueCollege(key, arg);
        } else if ("counts-profession".equals(operation)) {
            doCheckProfessionCounts(key, arg);
        } else if ("value-profession".equals(operation)) {
            doCheckValueProfession(key, arg);
        } else if ("counts-xclass".equals(operation)) {
            doCheckXclassCounts(key, arg);
        } else if ("value-xclass".equals(operation)) {
            doCheckValueXclass(key, arg);
        } else {
            throw new IllegalArgumentException("operation " + operation + " not supported");
        }
    }



    private void doCheckStructure(String key) throws FileNotFoundException {
        Gson gson = testHelper.gson();

        JsonElement actual = gson.toJsonTree(collegeRepository.getCollegesWithFull());
        JsonElement expected = testHelper.loadResultWithToken(key);

        System.out.println(gson.toJson(actual));

        assertEquals(expected, actual);
    }

    private void doCheckCollegeCounts(String key) {
        long expected = Long.parseLong(key);
        long actual = collegeRepository.getCollegesWithFull().size();
        assertEquals(expected, actual);
    }

    private void doCheckValueCollege(String key, String arg) throws OperationFailedException, FileNotFoundException {
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

    private void doCheckProfessionCounts(String key, String arg) {
        long expected = Long.parseLong(arg);
        long collegeId = Long.parseLong(key);
        long actual = collegeRepository.getProfessions(collegeId).size();
        assertEquals(expected, actual);
    }

    private void doCheckValueProfession(String key, String arg) throws OperationFailedException, FileNotFoundException {
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

    private void doCheckXclassCounts(String key, String arg) {
        long expected = Long.parseLong(arg);
        long professionId = Long.parseLong(key);
        long actual = collegeRepository.getXclasses(professionId).size();
        assertEquals(expected, actual);
    }

    private void doCheckValueXclass(String key, String arg) throws OperationFailedException, FileNotFoundException {
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

}

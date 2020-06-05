package dao;

import ext.exception.ServiceConstructException;
import models.College;
import services.ServiceContainer;

import java.util.ArrayList;

/**
 * 表示当前学院的信息
 */
public class CollegeDao {
    private long id;
    private String name;
    private ArrayList<ProfessionDao> professions;
    private CodeSummary studentsSummary = new CodeSummary();
    private CodeSummary teachersSummary = new CodeSummary();


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ProfessionDao> getProfessions() {
        return professions;
    }

    public void setProfessions(ArrayList<ProfessionDao> professions) {
        this.professions = professions;
    }

    public static CollegeDao fromCollege(College college){
        CollegeDao dao = new CollegeDao();
        dao.id = college.getCollegeId();
        dao.name = college.getName();
        return dao;
    }

    //TODO 将以下四个函数迁移到PathDao
    public static PathDao getPathFromXclass(long xclassId){
        try {
            PathDao path = new PathDao();
            ArrayList<CollegeDao> collegeDaos = ServiceContainer.get().cache().collegeDaosCache().get();
            for (CollegeDao collegeDao: collegeDaos) {
                for(ProfessionDao professionDao: collegeDao.professions){
                    for (XclassDao xclassDao: professionDao.getXclasses()){
                        if (xclassDao.getId() == xclassId){
                            path.setXclassId(xclassId);
                            path.setXclass(xclassDao.getName());
                            path.setProfessionId(professionDao.getId());
                            path.setProfession(professionDao.getName());
                            path.setCollegeId(collegeDao.getId());
                            path.setCollege(collegeDao.getName());
                            return path;
                        }
                    }
                }
            }
            return null;
        } catch (ServiceConstructException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static PathDao getPathFromProfession(long professionId){
        try {
            PathDao path = new PathDao();
            ArrayList<CollegeDao> collegeDaos = ServiceContainer.get().cache().collegeDaosCache().get();
            for (CollegeDao collegeDao: collegeDaos) {
                for(ProfessionDao professionDao: collegeDao.professions){
                    if (professionDao.getId() == professionId){
                        path.setProfessionId(professionDao.getId());
                        path.setProfession(professionDao.getName());
                        path.setCollegeId(collegeDao.getId());
                        path.setCollege(collegeDao.getName());
                        return path;
                    }

                }
            }
            return null;
        } catch (ServiceConstructException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static PathDao getPathFromCollege(long collegeId){
        try {
            PathDao path = new PathDao();
            ArrayList<CollegeDao> collegeDaos = ServiceContainer.get().cache().collegeDaosCache().get();
            for (CollegeDao collegeDao: collegeDaos) {
                if (collegeDao.getId() == collegeId) {
                    path.setCollegeId(collegeDao.getId());
                    path.setCollege(collegeDao.getName());
                    return path;
                }
            }

            return null;
        } catch (ServiceConstructException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static PathDao getPathFromLocator(ResourceLocator locator){
        switch (locator.getScope()) {
            case "all":
                return new PathDao();
            case "college":
                return getPathFromCollege(locator.getTag());
            case "profession":
                return getPathFromProfession(locator.getTag());
            default:
                return getPathFromXclass(locator.getTag());
        }
    }

    public static void combine(ArrayList<CollegeDao> collegeDaos, CodeSummaryCollection codeSummaryCollection){
        for (CollegeDao collegeDao:collegeDaos) {
            if (codeSummaryCollection.getOfStudent().get(collegeDao.id) != null) {
                collegeDao.studentsSummary = codeSummaryCollection.getOfStudent().get(collegeDao.id);
            }
            if (codeSummaryCollection.getOfTeacher().get(collegeDao.id) != null){
                collegeDao.teachersSummary = codeSummaryCollection.getOfTeacher().get(collegeDao.id);
            }

            for (ProfessionDao professionDao: collegeDao.professions){
                if(codeSummaryCollection.getOfStudentProfession().get(professionDao.getId()) != null){
                    professionDao.setStudentsSummary(codeSummaryCollection.getOfStudentProfession().get(professionDao.getId()));
                }
                for (XclassDao xclassDao: professionDao.getXclasses()){
                    if(codeSummaryCollection.getOfStudentXclass().get(xclassDao.getId())!= null){
                        xclassDao.setStudentsSummary(codeSummaryCollection.getOfStudentXclass().get(xclassDao.getId()));
                    }
                }
            }
        }
    }

    public CodeSummary getStudentsSummary() {
        return studentsSummary;
    }

    public void setStudentsSummary(CodeSummary studentsSummary) {
        this.studentsSummary = studentsSummary;
    }

    public CodeSummary getTeachersSummary() {
        return teachersSummary;
    }

    public void setTeachersSummary(CodeSummary teachersSummary) {
        this.teachersSummary = teachersSummary;
    }
}

package dao;

import ext.exception.ServiceConstructException;
import services.ServiceContainer;

import java.util.ArrayList;

/**
 * 表示一个路径信息
 */
public class PathDao {
    private long collegeId;
    private String college;
    private long professionId;
    private String profession;
    private long xclassId;
    private String xclass;
    private boolean error = false;


    public long getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(long collegeId) {
        this.collegeId = collegeId;
    }

    public long getProfessionId() {
        return professionId;
    }

    public void setProfessionId(long professionId) {
        this.professionId = professionId;
    }

    public long getXclassId() {
        return xclassId;
    }

    public void setXclassId(long xclassId) {
        this.xclassId = xclassId;
    }


    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getXclass() {
        return xclass;
    }

    public void setXclass(String xclass) {
        this.xclass = xclass;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public static boolean matches(PathDao pathDao, ResourceLocator locator){
        if(locator.isStudentType()){
            if(locator.equals(ResourceLocator.students())){
                return true;
            }
            if(pathDao == null){
                return false;
            }
            else if(locator.equals(ResourceLocator.studentsOfCollege())){
                return pathDao.collegeId == locator.getTag();
            } else if(locator.equals(ResourceLocator.studentsOfProfession())){
                return pathDao.professionId == locator.getTag();
            } else if(locator.equals(ResourceLocator.studentsOfXclass())) {
                return pathDao.xclassId == locator.getTag();
            }
        } else if(locator.isTeacherType()){
            if(locator.equals(ResourceLocator.teachers()) || locator.equals(ResourceLocator.ofAdmin())) {
                return true;
            }
            if(pathDao == null){
                return false;
            }
            else if(locator.equals(ResourceLocator.teachersOfCollege())) {
                return pathDao.collegeId == locator.getTag();
            }
        }
        return false;
    }

    public static PathDao fromXclass(long xclassId){
        try {
            PathDao path = new PathDao();
            ArrayList<CollegeDao> collegeDaos = ServiceContainer.get().cache().collegeDaosCache().get();
            for (CollegeDao collegeDao: collegeDaos) {
                for(ProfessionDao professionDao: collegeDao.getProfessions()){
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
            PathDao error = new PathDao();
            error.setError(true);
            error.xclassId = xclassId;
            return error;
        } catch (ServiceConstructException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static PathDao fromProfession(long professionId){
        try {
            PathDao path = new PathDao();
            ArrayList<CollegeDao> collegeDaos = ServiceContainer.get().cache().collegeDaosCache().get();
            for (CollegeDao collegeDao: collegeDaos) {
                for(ProfessionDao professionDao: collegeDao.getProfessions()){
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
    public static PathDao fromCollege(long collegeId){
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

            PathDao error = new PathDao();
            error.setError(true);
            error.setCollegeId(collegeId);
            return error;
        } catch (ServiceConstructException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static PathDao fromLocator(ResourceLocator locator){
        switch (locator.getScope()) {
            case "all":
                return new PathDao();
            case "college":
                return fromCollege(locator.getTag());
            case "profession":
                return fromProfession(locator.getTag());
            default:
                return fromXclass(locator.getTag());
        }
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}

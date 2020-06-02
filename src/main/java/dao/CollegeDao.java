package dao;

import ext.exception.ServiceConstructException;
import models.College;
import models.Xclass;
import services.ServiceContainer;

import java.util.ArrayList;

public class CollegeDao {
    private long id;
    private String name;
    private ArrayList<ProfessionDao> professions;

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

    public static CollegePath getPath(long xclassId){
        try {
            CollegePath path = new CollegePath();
            ArrayList<CollegeDao> collegeDaos = ServiceContainer.get().cache().collegeDaos().get();
            for (CollegeDao collegeDao: collegeDaos) {
                for(ProfessionDao professionDao: collegeDao.professions){
                    for (XclassDao xclassDao: professionDao.getXclasses()){
                        if (xclassDao.getId() == xclassId){
                            path.setXclassId(xclassId);
                            path.setProfessionId(professionDao.getId());
                            path.setCollegeId(collegeDao.getId());
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


}

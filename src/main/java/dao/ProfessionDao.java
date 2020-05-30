package dao;

import models.Profession;

import java.util.ArrayList;

public class ProfessionDao {
    private long id;
    private String name;
    private ArrayList<XclassDao> xclasses;

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

    public ArrayList<XclassDao> getXclasses() {
        return xclasses;
    }

    public void setXclasses(ArrayList<XclassDao> xclasses) {
        this.xclasses = xclasses;
    }

    public static ProfessionDao fromProfession(Profession profession){
        ProfessionDao professionDao = new ProfessionDao();
        professionDao.id = profession.getProfessionId();
        professionDao.name = profession.getName();
        return professionDao;
    }
}

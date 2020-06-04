package dao;

import models.Xclass;

public class XclassDao {
    private long id;
    private long professionId;
    private String name;
    private CodeSummary studentsSummary = new CodeSummary();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static XclassDao fromXClass(Xclass xClass){
        XclassDao xclassDao = new XclassDao();
        xclassDao.id = xClass.getXclassId();
        xclassDao.name = xClass.getName();
        xclassDao.professionId = xClass.getProfessionId();
        return xclassDao;
    }

    public CodeSummary getStudentsSummary() {
        return studentsSummary;
    }

    public void setStudentsSummary(CodeSummary studentsSummary) {
        this.studentsSummary = studentsSummary;
    }

    public long getProfessionId() {
        return professionId;
    }

    public void setProfessionId(long professionId) {
        this.professionId = professionId;
    }
}

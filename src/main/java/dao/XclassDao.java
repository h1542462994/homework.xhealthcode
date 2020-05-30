package dao;

import models.Xclass;

public class XclassDao {
    private long id;
    private String name;

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
        return xclassDao;
    }
}

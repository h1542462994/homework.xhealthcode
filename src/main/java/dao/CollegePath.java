package dao;

public class CollegePath {
    private long collegeId;
    private String college;
    private long professionId;
    private String profession;
    private long xclassId;
    private String xclass;


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
}

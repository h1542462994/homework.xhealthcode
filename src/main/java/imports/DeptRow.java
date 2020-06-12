package imports;

public class DeptRow {
    private int index;
    private String college;
    private String profession;
    private String xclass;
    private String msg;

    public DeptRow(int index, String college, String profession, String xclass) {
        this.index = index;
        this.college = college;
        this.profession = profession;
        this.xclass = xclass;
    }

    public DeptRow() {
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

package models;

import ext.annotation.Entity;
import ext.annotation.Primary;

@Entity(model = "xclass")
public class XClass {
    @Primary
    private long xclassId;
    private long professionId;
    private String name;

    public long getXclassId() {
        return xclassId;
    }

    public void setXclassId(long xclassId) {
        this.xclassId = xclassId;
    }

    public long getProfessionId() {
        return professionId;
    }

    public void setProfessionId(long professionId) {
        this.professionId = professionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

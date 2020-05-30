package models;

import ext.annotation.Entity;
import ext.annotation.Primary;

@Entity(model = "profession")
public class Profession {
    @Primary
    private long professionId;
    private long collegeId;
    private String name;

    public long getProfessionId() {
        return professionId;
    }

    public void setProfessionId(long professionId) {
        this.professionId = professionId;
    }

    public long getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(long collegeId) {
        this.collegeId = collegeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

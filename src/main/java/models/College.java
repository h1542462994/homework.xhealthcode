package models;

import ext.annotation.Entity;
import ext.annotation.Primary;

@Entity(model = "college")
public class College {
    @Primary
    private long collegeId;
    private String name;

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

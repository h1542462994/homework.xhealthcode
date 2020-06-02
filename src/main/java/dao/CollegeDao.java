package dao;

import models.College;

import java.util.ArrayList;

public class CollegeDao {
    private long id;
    private String name;
    private CodeSummary studentSummary;
    private CodeSummary teacherSummary;
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


    public CodeSummary getStudentSummary() {
        return studentSummary;
    }

    public void setStudentSummary(CodeSummary studentSummary) {
        this.studentSummary = studentSummary;
    }

    public CodeSummary getTeacherSummary() {
        return teacherSummary;
    }

    public void setTeacherSummary(CodeSummary teacherSummary) {
        this.teacherSummary = teacherSummary;
    }
}

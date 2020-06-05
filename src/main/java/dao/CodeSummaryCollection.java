package dao;

import enums.TypeType;

import java.util.HashMap;

/**
 * 统计所有的概要信息
 */
public class CodeSummaryCollection {
    private HashMap<Long, CodeSummary> ofTeacher = new HashMap<>();
    private HashMap<Long, CodeSummary> ofStudent = new HashMap<>();
    private HashMap<Long, CodeSummary> ofStudentProfession = new HashMap<>();
    private HashMap<Long, CodeSummary> ofStudentXclass = new HashMap<>();

    public HashMap<Long, CodeSummary> getOfTeacher() {
        return ofTeacher;
    }

    public void setOfTeacher(HashMap<Long, CodeSummary> ofTeacher) {
        this.ofTeacher = ofTeacher;
    }

    public HashMap<Long, CodeSummary> getOfStudent() {
        return ofStudent;
    }

    public void setOfStudent(HashMap<Long, CodeSummary> ofStudent) {
        this.ofStudent = ofStudent;
    }

    public HashMap<Long, CodeSummary> getOfStudentProfession() {
        return ofStudentProfession;
    }

    public void setOfStudentProfession(HashMap<Long, CodeSummary> ofStudentProfession) {
        this.ofStudentProfession = ofStudentProfession;
    }

    public HashMap<Long, CodeSummary> getOfStudentXclass() {
        return ofStudentXclass;
    }

    public void setOfStudentXclass(HashMap<Long, CodeSummary> ofStudentXclass) {
        this.ofStudentXclass = ofStudentXclass;
    }



    private void addStudent(UserResult result){
        if(result.getFieldId() != null){
            CodeSummary summary = ofStudentXclass.get(result.getFieldId());
            PathDao path = CollegeDao.getPathFromXclass(result.getFieldId());
            if(summary == null){
                summary = new CodeSummary();
                ofStudentXclass.put(result.getFieldId(), summary);
            }
            summary.increase(result.getResult());

            CodeSummary summaryOfProfession = ofStudentProfession.get(path.getProfessionId());
            if(summaryOfProfession == null){
                summaryOfProfession = new CodeSummary();
                ofStudentProfession.put(path.getProfessionId(), summaryOfProfession);
            }
            summaryOfProfession.increase(result.getResult());

            CodeSummary summaryOfCollege = ofStudent.get(path.getCollegeId());
            if(summaryOfCollege == null){
                summaryOfCollege = new CodeSummary();
                ofStudent.put(path.getCollegeId(), summaryOfCollege);
            }
            summaryOfCollege.increase(result.getResult());
        }
    }

    private void addTeacher(UserResult result){
        if(result.getFieldId() != null){
            CodeSummary summaryOfCollege = ofTeacher.get(result.getFieldId());
            if(summaryOfCollege == null){
                summaryOfCollege = new CodeSummary();
                ofTeacher.put(result.getFieldId(), summaryOfCollege);
            }
            summaryOfCollege.increase(result.getResult());
        }
    }

    public void add(UserResult result){
        if(result.getType() == TypeType.STUDENT){
            addStudent(result);
        } else if(result.getType() == TypeType.TEACHER) {
            addTeacher(result);
        }
    }

}

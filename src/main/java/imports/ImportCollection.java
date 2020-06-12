package imports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ImportCollection {
    private ArrayList<DeptRow> depts = new ArrayList<>();
    private ArrayList<StudentRow> students = new ArrayList<>();
    private ArrayList<TeacherRow> teachers = new ArrayList<>();
    private boolean isError;

    public void validate(){
        Set<String> colleges;
        Set<String> xclasses;

        HashMap<String, DeptRow> deptRowHashMap = new HashMap<>();
        // 检查colleges
        for(DeptRow deptRow: depts){
            if (deptRow.getCollege() == null){
                deptRow.setMsg("college不能为空!");
                isError = true;
            }
            if(deptRowHashMap.get(deptRow.getCollege()) != null) {
                deptRow.setMsg("college重复!");
                isError = true;
            } else {
                deptRowHashMap.put(deptRow.getCollege(), deptRow);
            }
        }

        colleges = deptRowHashMap.keySet();

        // 检查professions
        deptRowHashMap.clear();
        for(DeptRow deptRow: depts){
            if(deptRow.getProfession() != null){
                if(deptRow.getCollege() == null){
                    deptRow.setMsg("profession没有对应的college!");
                    isError = true;
                } else {
                    if(deptRowHashMap.get(deptRow.getProfession()) != null){
                        deptRow.setMsg("profession重复!");
                        isError = true;
                    } else {
                        deptRowHashMap.put(deptRow.getProfession(), deptRow);
                    }
                }
            }
        }

        // 检查xclasses
        deptRowHashMap.clear();
        for (DeptRow deptRow: depts){
            if(deptRow.getXclass() != null){
                if(deptRow.getProfession() == null){
                    deptRow.setMsg("xclass没有对应的profession");
                    isError = true;
                } else {
                    if(deptRowHashMap.get(deptRow.getXclass()) != null){
                        deptRow.setMsg("xclass重复!");
                        isError = true;
                    } else {
                        deptRowHashMap.put(deptRow.getXclass(), deptRow);
                    }
                }
            }
        }

        xclasses = deptRowHashMap.keySet();

        //检查student
        HashMap<String, StudentRow> studentRowHashMap = new HashMap<>();
        for(StudentRow studentRow: students){
            if(!studentRow.isValid()){
                isError = true;
            }
            if(!xclasses.contains(studentRow.getXclass())){
                studentRow.setMsg(studentRow.getMsg() + "不存在的xclass!;");
                isError = true;
            }
            if(studentRowHashMap.get(studentRow.getNumber()) != null){
                studentRow.setMsg(studentRow.getMsg() + "学生重复!;");
                isError = true;
            } else {
                if(studentRow.getNumber() != null){
                    studentRowHashMap.put(studentRow.getNumber(), studentRow);
                }
            }
        }

        //检查teacher
        HashMap<String, TeacherRow> teacherRowHashMap = new HashMap<>();
        for(TeacherRow teacherRow: teachers){
            if(!teacherRow.isValid()){
                isError = true;
            }
            if(teacherRow.isNormal()){
                if(teacherRow.getCollege() == null){
                    teacherRow.setMsg(teacherRow.getMsg() + "college不能为空");
                    isError = true;
                } else if(colleges.contains(teacherRow.getCollege())) {
                    teacherRow.setMsg(teacherRow.getMsg() + "college不存在");
                    isError = true;
                }
            }
            if(teacherRowHashMap.get(teacherRow.getNumber()) != null){
                teacherRow.setMsg(teacherRow.getMsg() + "教师重复!");
                isError = true;
            } else {
                if(teacherRow.getNumber() != null){
                    teacherRowHashMap.put(teacherRow.getNumber(), teacherRow);
                }
            }
        }
    }

    public ArrayList<DeptRow> getDepts() {
        return depts;
    }

    public void setDepts(ArrayList<DeptRow> depts) {
        this.depts = depts;
    }

    public ArrayList<StudentRow> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<StudentRow> students) {
        this.students = students;
    }

    public ArrayList<TeacherRow> getTeachers() {
        return teachers;
    }

    public void setTeachers(ArrayList<TeacherRow> teachers) {
        this.teachers = teachers;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }
}

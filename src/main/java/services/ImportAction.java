package services;

import enums.RoleType;
import enums.TypeType;
import ext.declare.DbContextBase;
import ext.exception.OperationFailedException;
import imports.DeptRow;
import imports.ImportCollection;
import imports.StudentRow;
import imports.TeacherRow;
import models.*;

import java.util.ArrayList;
import java.util.HashMap;

public class ImportAction implements IImportAction {
    public DbContext context;
    public ImportAction(DbContextBase context){
        this.context = (DbContext)context;
    }

    @Override
    public void importData(ImportCollection collection){
        if(!collection.isError()){
            try {
                Cache.clearCache();
                context.executeNoQuery("delete from adminuser where adminUserId != ?", 1);
                context.executeNoQuery("delete from college");
                context.executeNoQuery("delete from dailycard");
                context.executeNoQuery("delete from info");
                context.executeNoQuery("delete from profession");
                context.executeNoQuery("delete from resultcache");
                context.executeNoQuery("delete from student");
                context.executeNoQuery("delete from teacher where teacherId != ?", 1);
                context.executeNoQuery("delete from user where userId != ?", 1);
                context.executeNoQuery("delete from xclass");

                HashMap<String, College> collegeHashMap = new HashMap<>();
                //HashMap<String, Profession> professionHashMap = new HashMap<>();
                HashMap<String, Xclass> xclassHashMap = new HashMap<>();
                ArrayList<DeptRow> deptRows = collection.getDepts();
                for (DeptRow deptRow: deptRows) {
                    College college = new College();
                    college.setName(deptRow.getCollege());
                    context.colleges.add(college);
                    collegeHashMap.put(college.getName(), college);
                    if(deptRow.getProfession() != null){
                        Profession profession = new Profession();
                        profession.setName(deptRow.getProfession());
                        profession.setCollegeId(college.getCollegeId());
                        context.professions.add(profession);
                        if(deptRow.getXclass() != null){
                            Xclass xclass = new Xclass();
                            xclass.setName(deptRow.getXclass());
                            xclass.setProfessionId(profession.getProfessionId());
                            context.xclasses.add(xclass);
                            xclassHashMap.put(xclass.getName(), xclass);
                        }
                    }
                }

                ArrayList<StudentRow> studentRows = collection.getStudents();
                for(StudentRow studentRow: studentRows){
                    User user = new User();
                    user.setUserType(TypeType.STUDENT);
                    context.users.add(user);
                    Student student = new Student();
                    student.setName(studentRow.getName());
                    student.setUserId(user.getUserId());
                    student.setNumber(studentRow.getNumber());
                    student.setIdCard(studentRow.getIdCard());
                    student.setXClassId(xclassHashMap.get(studentRow.getXclass()).getXclassId());
                    context.students.add(student);
                }

                ArrayList<TeacherRow> teacherRows = collection.getTeachers();
                for(TeacherRow teacherRow: teacherRows){
                    User user = new User();
                    user.setUserType(TypeType.TEACHER);
                    context.users.add(user);
                    Teacher teacher = new Teacher();
                    teacher.setName(teacherRow.getName());
                    teacher.setUserId(user.getUserId());
                    teacher.setNumber(teacherRow.getNumber());
                    teacher.setIdCard(teacherRow.getIdCard());
                    if(teacherRow.getCollege() != null){
                        teacher.setCollegeId(collegeHashMap.get(teacherRow.getCollege()).getCollegeId());
                    }
                    context.teachers.add(teacher);

                    if(teacherRow.getAdminType() != null){
                        AdminUser adminUser = new AdminUser();
                        adminUser.setTeacherId(teacher.getTeacherId());
                        adminUser.setPassword(teacherRow.getPassword());
                        int row;
                        if(teacherRow.getAdminType().equals("校级管理员")){
                            row = RoleType.SCHOOL;
                        } else {
                            row = RoleType.COLLAGE;
                        }
                        adminUser.setRole(row);
                        context.adminUsers.add(adminUser);
                    }
                }

            } catch (OperationFailedException e) {
                e.printStackTrace();
            }
        }
    }
}

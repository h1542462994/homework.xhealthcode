package services;

import dao.PathDao;
import dao.ResourceLocator;
import dao.UserDao;
import enums.Result;
import enums.RoleType;
import enums.TypeType;
import ext.declare.DbContextBase;
import ext.exception.OperationFailedException;
import models.*;
import requests.UserLogin;
import requests.UserRequest;
import util.Checker;
import util.StringTools;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 用户仓储类，由{@link ext.ServiceContainerBase#getService(Class)}自动创建
 * @author cht
 */
public class UserRepository implements IUserRepository {
    private final DbContext context;
    private String msg;
    //private final long eachPage = 20;
    public UserRepository(DbContextBase context){
        this.context = (DbContext) context;
    }

    /**
     * 显示错误信息的接口
     */
    public String getMsg(){
        return this.msg;
    }

    /**
     * 进行登录
     */
    public UserAccess login(UserLogin login, HttpServletResponse response){
        try {
            IStudentTeacherUnion user;
            if(login.type == UserLogin.ADMIN){
                user = context.teachers.query("number = ?", login.number).unique();
                if(user == null){
                    msg = "不存在该用户";
                    return null;
                }

                AdminUser adminUser = context.adminUsers.query("teacherId = ?",user.getId()).unique();
                if(adminUser == null){
                    msg = "该用户不是管理员";
                    return null;
                }

                if(!adminUser.getPassword().equals(StringTools.md5(login.passport))){
                    msg = "密码错误";
                    return null;
                }
            } else {
                if(login.type == UserLogin.STUDENT){
                    user = context.students.query("number = ?",login.number).unique();
                } else {
                    user = context.teachers.query("number = ?",login.number).unique();
                }
                if(user == null){
                    msg = "不存在该用户";
                    return null;
                }


                System.out.println("user:" + user.getUserId() + " idCard:" + user.getIdCard().substring(user.getIdCard().length() - 6));
                System.out.println(user.getName()+"   "+login.name);
                if(!user.getName().equals(login.name)){
                    msg = "姓名错误";
                    return null;
                }
                if(!user.getIdCard().substring(user.getIdCard().length() - 6).equals(login.passport))
                {
                    System.out.println(user.getIdCard().substring(user.getIdCard().length() - 6));
                    System.out.println(login.passport);
                    msg = "密码错误";
                    return null;
                }

            }
            // 此时执行登录操作
            context.executeNoQuery("update useraccess set expired = null where userId = ?",user.getUserId());
            UserAccess access = createUserAccess(user.getUserId());
            context.userAccesses.add(access);
            response.addCookie(new Cookie("_token", access.getToken()));

            return access;
        } catch (OperationFailedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前登录的用户
     */
    public UserAccess active(HttpServletRequest request){
        try {
            String token = null;
            Cookie[] cookies = request.getCookies();
            Cookie tokenCookie = null;
            if(cookies == null)
                return null;
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("_token")) {
                    token = cookie.getValue();
                    tokenCookie = cookie;
                    break;
                }
            }

            if(token == null)
                return null;
            UserAccess access = context.userAccesses.query("token = ?",token).unique();
            if(access == null)
                return null;
            if(access.getExpired() == null)
                return null;
            if(access.getExpired().before(Timestamp.valueOf(LocalDateTime.now()))){
                return null;
            }

            return access;
        } catch (OperationFailedException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean insertOrUpdateUser(UserRequest userRequest, long id){
        Cache.clearCache();
        try {
            if (userRequest.getType() == TypeType.STUDENT){
                Xclass xclass = context.xclasses.query("name = ?", userRequest.getField()).unique();
                if(xclass != null){
                    Student studentJ = context.students.query("number = ? and userId != ?", userRequest.getNumber(), id).unique();
                    if(studentJ != null)
                        return false;
                    User user = new User();
                    user.setUserId(id);
                    user.setUserType(TypeType.STUDENT);

                    context.users.insertOrUpdate(user);

                    Student student = context.students.query("userId = ?", user.getUserId()).unique();
                    if(student == null) student = new Student();
                    student.setUserId(user.getUserId());
                    student.setName(userRequest.getName());
                    student.setNumber(userRequest.getNumber());
                    student.setIdCard(userRequest.getIdCard());
                    student.setXClassId(xclass.getXclassId());
                    context.students.insertOrUpdate(student);
                    Cache.clearCache();
                    return true;
                }
            } else {
                boolean pass = String.valueOf(RoleType.SCHOOL).equals(userRequest.getAdminType()) || String.valueOf(RoleType.SYSTEM).equals(userRequest.getAdminType());
                College college = null;
                if(!pass){
                    college = context.colleges.query("name = ?", userRequest.getField()).unique();
                }
                if(userRequest.getType() == TypeType.ADMIN){
                    if(userRequest.getAdminType() == null || String.valueOf(RoleType.SYSTEM).equals(userRequest.getAdminType())) return  false;

                }
                if(college != null || pass){
                    // 验证工号是否重复
                    Teacher teacherJ = context.teachers.query("number = ? and userId != ?", userRequest.getNumber(), id).unique();
                    if(teacherJ != null)
                        return false;

                    User user = new User();
                    user.setUserId(id);
                    user.setUserType(TypeType.TEACHER);
                    context.users.insertOrUpdate(user);

                    Teacher teacher = context.teachers.query("userId = ?", user.getUserId()).unique();
                    if(teacher == null) teacher = new Teacher();

                    teacher.setUserId(user.getUserId());
                    teacher.setName(userRequest.getName());
                    teacher.setNumber(userRequest.getNumber());
                    teacher.setIdCard(userRequest.getIdCard());
                    if(college != null){
                        teacher.setCollegeId(college.getCollegeId());
                    }

                    if(userRequest.getType() == TypeType.ADMIN){
                        AdminUser adminUser = context.adminUsers.query("teacherId = ?", teacher.getTeacherId()).unique();
                        if(adminUser == null){
                            adminUser = new AdminUser();
                            if(userRequest.getPassport() == null || Checker.checkPassword(userRequest.getPassport()) != null){
                                return false;
                            }
                        } else {
                            if(userRequest.getPassport() != null && Checker.checkPassword(userRequest.getPassport()) == null){
                                adminUser.setPassword(StringTools.md5(userRequest.getPassport()));
                            }
                        }
                        adminUser.setRole(Integer.parseInt(userRequest.getAdminType()));
                        adminUser.setTeacherId(teacher.getTeacherId());

                        context.adminUsers.insertOrUpdate(adminUser);
                    }

                    context.teachers.insertOrUpdate(teacher);
                    Cache.clearCache();
                    return true;
                }
            }
        }catch (OperationFailedException e){
            e.printStackTrace();

        }
        return false;
    }


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        UserAccess access = active(request);
        if(access != null){
            access.setExpired(null);
            response.addCookie(new Cookie("_token", null));
        }
    }

    @Override
    public UserDao get(long id){
        try {
            User user = context.users.get(id);
            return get(user);
        } catch (OperationFailedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserDao get(User user){
        try {
            //region 获取用户信息
            if(user == null)
                return null;
            UserDao result = new UserDao();
            result.setId(user.getUserId());
            result.setType(user.getUserType());

            if(result.isStudentType()){
                Student student = context.students.query("userId = ?", user.getUserId()).unique();
                if(student.getXClassId() != null){
                    PathDao path = PathDao.fromXclass(student.getXClassId());
                    result.setPath(path);
                }
                result.setFieldId(student.getXClassId());
                result.setName(student.getName());
                result.setNumber(student.getNumber());
                result.setIdCard(student.getIdCard());
            } else {
                Teacher teacher = context.teachers.query("userId =?", user.getUserId()).unique();
                if(teacher.getCollegeId()!=null){
                    PathDao path = PathDao.fromCollege(teacher.getCollegeId());
                }
                result.setFieldId(teacher.getCollegeId());
                result.setName(teacher.getName());
                result.setNumber(teacher.getNumber());
                result.setIdCard(teacher.getIdCard());

                AdminUser adminUser = context.adminUsers.query("teacherId = ?", teacher.getTeacherId()).unique();
                if(adminUser != null) {
                    result.setType(TypeType.ADMIN);
                    result.setAdminType(adminUser.getRole());
                }
            }
            //endregion

            Info info = context.infos.query("userId = ?", user.getUserId()).unique();
            if(info == null){
                result.setResult(Result.No);
            } else {
                result.setResult(info.getResult() + 1);
                result.setDate(info.getDate());
//                if(result.getResult() != Result.GREEN){
                //TODO 获取近期打卡的情况
                HashMap<Integer, Integer> summary = new HashMap<>();
                for (int i = 0; i < 7; ++i){
                    summary.put(i, Result.No);
                }


                int leaves = 7;
                for (int i = 6; i> 0; --i){
                    if(summary.get(i) == Result.GREEN){
                        leaves--;
                    } else {
                        break;
                    }
                }

                result.setSummary(new ArrayList<>(summary.values()));
                result.setRemainDays(leaves);
            }

            return result;
        } catch (OperationFailedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserDao getUserDaoByLocator(User user, ResourceLocator locator){
        try {
            //User user = context.users.get(id);
            if(user == null)
                return null;
            UserDao result = new UserDao();
            result.setId(user.getUserId());
            result.setType(user.getUserType());

            if(result.isStudentType()){
                if(!locator.isStudentType()){ // 筛选
                    return null;
                }
                Student student = context.students.query("userId = ?", user.getUserId()).unique();
                if(student.getXClassId() != null){
                    PathDao path = PathDao.fromXclass(student.getXClassId());
                    if(!PathDao.matches(path, locator)) {
                        return null;
                    }
                    result.setPath(path);
                } else if(!locator.passAll(result)) {
                    return null;
                }
                result.setFieldId(student.getXClassId());
                result.setName(student.getName());
                result.setNumber(student.getNumber());
                result.setIdCard(student.getIdCard());
            } else {
                if(!locator.isTeacherType()){ // 筛选
                    return null;
                }
                Teacher teacher = context.teachers.query("userId = ?", user.getUserId()).unique();
                if(teacher.getCollegeId()!=null){
                    PathDao path = PathDao.fromCollege(teacher.getCollegeId());
                    if(!PathDao.matches(path, locator)){
                        return null;
                    }
                    result.setPath(path);
                } else if(!locator.passAll(result)) {
                    return null;
                }
                result.setFieldId(teacher.getCollegeId());
                result.setName(teacher.getName());
                result.setNumber(teacher.getNumber());
                result.setIdCard(teacher.getIdCard());

                AdminUser adminUser = context.adminUsers.query("teacherId = ?", teacher.getTeacherId()).unique();
                if(adminUser == null){
                    if(locator.getType() == TypeType.ADMIN){
                        return null;
                    }
                } else {
                    if(locator.getType() != TypeType.ADMIN){
                        return null;
                    }
                    result.setType(TypeType.ADMIN);
                    result.setAdminType(adminUser.getRole());
                }
            }

            Info info = context.infos.query("userId = ?", user.getUserId()).unique();
            if(info == null){
                result.setResult(Result.No);
            } else {
                result.setResult(info.getResult() + 1);
                result.setDate(info.getDate());

                HashMap<Integer, Integer> summary = new HashMap<>();
                for (int i = 0; i < 7; ++i){
                    summary.put(i, Result.No);
                }
                for(DailyCard dailyCard: context.dailyCards.query("userId = ? order by date desc limit 7", user.getUserId())){
                    Period period = Period.between(dailyCard.getDate().toLocalDate(), LocalDate.now());
                    int days = 6 - period.getDays();
                    if(days >= 0 && days < 7){
                        summary.put(days, dailyCard.getResult() + 1);
                    }
                }
                int leaves = 7;
                for (int i = 6; i> 0; --i){
                    if(summary.get(i) == Result.GREEN){
                        leaves--;
                    } else {
                        break;
                    }
                }

                //如果不是查询今天的数据则覆盖summary
                if(!locator.getRawDate().equals(LocalDate.now())){
                    for (int i = 0; i < 7; ++i){
                        summary.put(i, Result.No);
                    }
                    for(DailyCard dailyCard: context.dailyCards.query("userId = ? and date between ? and ?", user.getUserId(), locator.getFirstDate(), locator.getLastDate())){
                        Period period = Period.between(dailyCard.getDate().toLocalDate(), locator.getRawDate());
                        summary.put(6 - period.getDays(), dailyCard.getResult() + 1);
                    }
                }

                result.setSummary(new ArrayList<>(summary.values()));
                result.setRemainDays(leaves);
            }

            return result;
        } catch (OperationFailedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(long id){
        Cache.clearCache();
        try {
            User user = context.users.get(id);
            if(user != null){
                if(user.getUserType() == TypeType.STUDENT){
                    Student student = context.students.query("userId = ?", user.getUserId()).unique();
                    if(student != null){
                        context.students.delete(student.getStudentId());
                        context.users.delete(id);
                    }
                } else {
                    Teacher teacher = context.teachers.query("userId = ?", user.getUserId()).unique();
                    if(teacher != null){
                        AdminUser adminUser = context.adminUsers.query("teacherId = ?", teacher.getTeacherId()).unique();
                        if(adminUser.getRole() != RoleType.SYSTEM){
                            context.adminUsers.delete(adminUser.getAdminUserId());
                            context.teachers.delete(teacher.getTeacherId());
                            context.users.delete(id);
                        }
                    }
                }
            }


        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public ArrayList<UserDao> fromLocator(ResourceLocator locator){
        try {
            //int pageIndex = locator.getPageIndex();
            ArrayList<UserDao> userDaos = new ArrayList<>();
            if(locator.isTeacherType()){
                //long count = context.users.queryCount("userType = ?", TypeType.TEACHER);
                //long pageCount = (count - 1)/eachPage + 1;
                for (User user: context.users.query("userType = ?", TypeType.TEACHER)){
                    UserDao result = getUserDaoByLocator(user, locator);
                    if (result != null){
                        userDaos.add(result);
                    }
                }
                return userDaos;
            } else if(locator.isStudentType()){
                //long count = context.users.queryCount("userType = ?", TypeType.STUDENT);
                //long pageCount = (count - 1)/eachPage + 1;
                for (User user: context.users.query("userType = ?",  TypeType.STUDENT)){
                    UserDao result = getUserDaoByLocator(user, locator);
                    if (result != null){
                        userDaos.add(result);
                    }
                }
                return userDaos;
            }
            return null;
        } catch (OperationFailedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private UserAccess createUserAccess(long userId){
        UserAccess access = new UserAccess();
        access.setUserId(userId);
        access.setToken(StringTools.makeToken());
        access.setExpired(Timestamp.valueOf(LocalDateTime.now().plusDays(3)));
        return access;
    }
}

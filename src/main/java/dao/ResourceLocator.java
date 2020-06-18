package dao;

import enums.TypeType;

import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 资源定位器
 * TODO: 为了进一步进行筛选，还需要添加健康码的颜色，以便于进一步的筛选。
 */
public class ResourceLocator {

    private int type = TypeType.STUDENT;
    private int pageIndex = 0;
    private String scope = "all";
    private int tag = 0;
    /**
     * 获取在特定时间内的打卡信息。
     */
    private String date = null;

    public ResourceLocator() {
        this.date = LocalDate.now().toString();
    }

    public ResourceLocator(int type, String scope) {
        this.type = type;
        this.scope = scope;
    }

    public int getType() {
        return type;
    }

    public boolean isTeacherType(){
        return this.type == TypeType.TEACHER || this.type == TypeType.ADMIN;
    }

    public boolean isStudentType(){
        return this.type == TypeType.STUDENT;
    }

    public boolean isDomainType(){
        return this.type == TypeType.Domain;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LocalDate getRawDate(){
        return LocalDate.parse(this.date) ;
    }

    public String getLastDate(){
        return this.date;
    }

    public String getFirstDate(){
        return this.getRawDate().minusDays(6).toString();
    }


    @Override
    public String toString() {
        return type + "," + pageIndex + "," + scope + "," + tag + "," + date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceLocator that = (ResourceLocator) o;
        return type == that.type &&
                scope.equals(that.scope);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, scope);
    }

    public ResourceLocator page(int pageIndex){
        this.pageIndex = pageIndex;
        return this;
    }
    public ResourceLocator tag(Integer tag){
        this.tag = tag;
        return this;
    }

    public boolean passAll(UserDao userDao){

         if(userDao.isStudentType()) {
            return this.equals(ResourceLocator.students());
        } else if(userDao.isTeacherType()) {
            return this.equals(ResourceLocator.teachers()) || this.equals(ResourceLocator.ofAdmin());
        }
        return true;
    }

//    public CollegePath getPath(){
//        return CollegeDao.getPathFromLocator(this);
//    }

    public static ResourceLocator teachers(){
        return new ResourceLocator(TypeType.TEACHER, "all");
    }

    public static ResourceLocator students(){
        return new ResourceLocator(TypeType.STUDENT, "all");
    }

    public static ResourceLocator teachersOfCollege(){
        return new ResourceLocator(TypeType.TEACHER,"college");
    }

    public static ResourceLocator studentsOfCollege(){
        return new ResourceLocator(TypeType.STUDENT, "college");
    }

    public static ResourceLocator ofAdmin(){
        return new ResourceLocator(TypeType.ADMIN, "all");
    }

    public static ResourceLocator studentsOfProfession(){
        return new ResourceLocator(TypeType.STUDENT, "profession");
    }

    public static ResourceLocator studentsOfXclass(){
        return new ResourceLocator(TypeType.STUDENT, "xclass");
    }



}

package dao;

import enums.TypeType;

import java.util.Objects;

public class ResourceLocator {
    private int type = TypeType.STUDENT;
    private int pageIndex = 0;
    private String scope = "all";
    private int tag = 0;

    public int getType() {
        return type;
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



    public ResourceLocator(int type, String scope) {
        this.type = type;
        this.scope = scope;
    }

    public ResourceLocator() {

    }

    @Override
    public String toString() {
        return type + "," + pageIndex + "," + scope + "," + tag;
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
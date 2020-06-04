package services;

import enums.TypeType;
import ext.Tuple;

import java.util.Objects;

public class ResourceLocator {
    public int type;
    public int pageIndex;
    public String scope;
    public Object tag;

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
    public ResourceLocator tag(Object tag){
        this.tag = tag;
        return this;
    }

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
}

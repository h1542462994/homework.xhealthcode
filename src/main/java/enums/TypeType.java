package enums;

/**
 * 用户类型，其中管理员为运行时仅有项
 * 同时也作为{@link dao.ResourceLocator}的资源定位类型来使用
 */
public interface TypeType {
    int Domain = -1;
    int STUDENT = 0;
    int TEACHER = 1;
    int ADMIN = 2;
}

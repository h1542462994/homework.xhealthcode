package models;

public interface IStudentTeacherUnion {
    long getId();
    void setId(long id);
    long getUserId();
    void setUserId(long userId);
    String getName();

    void setName(String name);
    String getIdCard();

    void setIdCard(String idCard);

    String getNumber();

    void setNumber(String number);
}

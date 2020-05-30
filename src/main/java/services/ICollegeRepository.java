package services;

import dao.CollegeDao;
import dao.ProfessionDao;
import dao.XclassDao;
import models.College;

import java.util.ArrayList;

public interface ICollegeRepository {
    /**
     * 获取所有的学院
     * @return 所有的数据
     */
    ArrayList<CollegeDao> getColleges();

    /**
     * 添加一个学院
     * @param college 学院
     * @return 添加的学院信息
     */
    CollegeDao addCollege(College college);

    /**
     * 更新一个学院
     * @param college 学院
     * @return 更新后的学院的信息
     */
    CollegeDao updateCollege(College college);

    /**
     * 删除学院[s]
     * @param ids 学院的id[s]
     */
    default void deleteColleges(long[] ids) {
        for (long id : ids) {
            deleteCollege(id);
        }
    }

    default void deleteProfessions(long[] ids){
        for (long id: ids){
            deleteProfession(id);
        }
    }

    default void deleteXClasses(long[] ids) {
        for (long id: ids){
            deleteXclass(id);
        }
    }

    /**
     * 删除学院
     * @param id 学院的id
     */
    void deleteCollege(long id);

    void deleteProfession(long id);

    void deleteXclass(long id);

    ArrayList<ProfessionDao> getProfessions(long collegeId);

    ArrayList<XclassDao> getXclasses(long professionId);
}

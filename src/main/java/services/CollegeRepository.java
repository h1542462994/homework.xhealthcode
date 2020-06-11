package services;

import dao.CodeSummaryCollection;
import dao.CollegeDao;
import dao.ProfessionDao;
import dao.XclassDao;
import ext.declare.DbContextBase;
import ext.exception.OperationFailedException;
import models.College;
import models.Profession;
import models.User;
import models.Xclass;

import java.util.ArrayList;

public class CollegeRepository implements ICollegeRepository {
    private final DbContext context;
    private final IUserRepository userRepository;

    public CollegeRepository(DbContextBase context, IUserRepository userRepository){
        this.context = (DbContext)context;
        this.userRepository = userRepository;
    }

    @Override
    public ArrayList<CollegeDao> getCollegesWithFull() {
        try {
            ArrayList<CollegeDao> collegeDaos = new ArrayList<>();
            for (College college : context.colleges.all()){
                CollegeDao collegeDao = CollegeDao.fromCollege(college);
                collegeDao.setProfessions(getProfessions(collegeDao.getId()));
                collegeDaos.add(collegeDao);
            }
            return collegeDaos;
        } catch (OperationFailedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CollegeDao getCollege(long collegeId) {
        try {
            College college = context.colleges.get(collegeId);
            if(college == null)
                return null;
            return CollegeDao.fromCollege(college);
        } catch (OperationFailedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ProfessionDao getProfession(long professionId) {
        try {
            Profession profession  = context.professions.get(professionId);
            return ProfessionDao.fromProfession(profession);
        } catch (OperationFailedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CollegeDao addCollege(College college) {
        Cache.clearCache();
        try {
            College c = context.colleges.query("name = ?", college.getName()).unique();
            if(c != null){
                return null;
            }
            context.colleges.add(college);
            return CollegeDao.fromCollege(college);
        } catch (OperationFailedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ProfessionDao addProfession(Profession profession) {
        Cache.clearCache();
        try {
            Profession c = context.professions.query("name = ?", profession.getName()).unique();
            if(c != null){
                return null;
            }
            context.professions.add(profession);
            return ProfessionDao.fromProfession(profession);
        } catch (OperationFailedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public XclassDao addXclass(Xclass xclass) {
        Cache.clearCache();
        try {
            Xclass c = context.xclasses.query("name = ?", xclass.getName()).unique();
            if(c != null){
                return null;
            }
            context.xclasses.add(xclass);
            return XclassDao.fromXClass(xclass);
        } catch (OperationFailedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CollegeDao updateCollege(College college) {
        Cache.clearCache();
        try {

            College c = context.colleges.query("name = ?", college.getName()).unique();
            if(c != null){
                return null;
            }
            context.colleges.update(college);
            return CollegeDao.fromCollege(college);
        } catch (OperationFailedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ProfessionDao updateProfession(Profession profession) {
        Cache.clearCache();
        try {

            Profession c = context.professions.query("name = ?", profession.getName()).unique();
            if(c != null){
                return null;
            }
            context.professions.update(profession);
            return ProfessionDao.fromProfession(profession);
        } catch (OperationFailedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public XclassDao updateXclass(Xclass xclass) {
        Cache.clearCache();
        try {

            Xclass c = context.xclasses.query("name = ?", xclass.getName()).unique();
            if(c != null){
                return null;
            }
            context.xclasses.update(xclass);
            return XclassDao.fromXClass(xclass);
        } catch (OperationFailedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteCollege(long id){
        Cache.clearCache();
        try {
            for (Profession profession: context.professions.query("collegeId = ?", id)){
                deleteProfession(id);
            }
            context.colleges.delete(id);
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProfession(long id) {
        try {
            for (Xclass xClass: context.xclasses.query("professionId = ?", id)){
                deleteXclass(id);
            }
            context.professions.delete(id);
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteXclass(long id) {
        try {
            context.xclasses.delete(id);
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<ProfessionDao> getProfessions(long collegeId) {
        try {
            ArrayList<ProfessionDao> professionDaos = new ArrayList<>();
            for (Profession profession: context.professions.query("collegeId = ?", collegeId)){
                ProfessionDao professionDao = ProfessionDao.fromProfession(profession);
                professionDao.setXclasses(getXclasses(professionDao.getId()));
                professionDaos.add(professionDao);
            }
            return professionDaos;
        } catch (OperationFailedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<XclassDao> getXclasses(long professionId) {
        try {
            ArrayList<XclassDao> xclassDaos = new ArrayList<>();
            for(Xclass xClass: context.xclasses.query("professionId = ?", professionId)){
                xclassDaos.add(XclassDao.fromXClass(xClass));
            }
            return xclassDaos;
        } catch (OperationFailedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public College getCollege(Object infer) {

        try {
            if(infer instanceof Profession){
               return context.colleges.get(((Profession)infer).getCollegeId());
            } else if(infer instanceof Xclass){
                return getCollege(context.professions.get(((Xclass)infer).getProfessionId()));
            } else {
                return null;
            }
        } catch (OperationFailedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CodeSummaryCollection getSummary() {
        try {
            CodeSummaryCollection codeSummaryCollection = new CodeSummaryCollection();
            for (User user: context.users.all()){
                //TODO 更改，使用更简单的UserSimpleDao容器。
                codeSummaryCollection.add(userRepository.get(user));
            }
            return codeSummaryCollection;
        } catch (OperationFailedException e) {
            e.printStackTrace();
            return null;
        }
    }
}

package services;

import dao.CollegeDao;
import ext.declare.DbContextBase;
import ext.exception.OperationFailedException;
import models.College;

import java.util.ArrayList;

public class CollegeRepository implements ICollegeRepository {
    private DbContext context;

    public CollegeRepository(DbContextBase context){
        this.context = (DbContext)context;
    }

    @Override
    public Iterable<CollegeDao> getColleges() {
        try {
            ArrayList<CollegeDao> collegeDaos = new ArrayList<>();
            for (College college : context.colleges.all()){
                collegeDaos.add(CollegeDao.fromCollege(college));
            }
            return collegeDaos;
        } catch (OperationFailedException e) {
            e.printStackTrace();
            return null;
        }
    }
}

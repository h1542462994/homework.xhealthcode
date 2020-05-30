package services;

import dao.CollegeDao;

public interface ICollegeRepository {
    public Iterable<CollegeDao> getColleges();
}

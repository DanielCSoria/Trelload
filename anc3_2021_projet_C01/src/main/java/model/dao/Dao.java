package model.dao;

public interface Dao<E> {

    void save(E item);

     void delete(E item);


}

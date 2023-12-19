package com.nfjh;

public interface DaoInterface {
     int  insert(String actno);
     void delete();
     int update(String actno,Double balance);
      String selectByactno(String actno);
}

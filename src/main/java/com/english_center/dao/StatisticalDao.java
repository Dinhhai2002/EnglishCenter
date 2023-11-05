package com.english_center.dao;

import java.util.List;

public interface StatisticalDao {
   List<Object> statisticalAmount(int numberWeek,String fromDate,String toDate,int type) throws Exception;
   
   List<Object> statisticalNumberUserDoExam(int numberWeek,String fromDate,String toDate,int type) throws Exception;
}

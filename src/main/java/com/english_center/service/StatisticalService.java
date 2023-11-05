package com.english_center.service;

import java.util.List;

public interface StatisticalService {
	List<Object> statisticalAmount(int numberWeek, String fromDate, String toDate, int type) throws Exception;
	
	List<Object> statisticalNumberUserDoExam(int numberWeek,String fromDate,String toDate,int type) throws Exception;
}

package com.english_center.dao;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.english_center.entity.ExamDetail;

@Repository("ExamDetailDao")
@SuppressWarnings("unchecked")
@Transactional
public class ExamDetailDaoImpl extends AbstractDao<Integer, ExamDetail> implements ExamDetailDao {

	@Override
	public void create(ExamDetail examDetail) throws Exception {
		this.getSession().save(examDetail);
		
	}

	@Override
	public ExamDetail findOne(int id) throws Exception {
		return this.getSession().find(ExamDetail.class,id);
	}

	@Override
	public void update(ExamDetail examDetail) throws Exception {
		this.getSession().update(examDetail);
		
	}

}

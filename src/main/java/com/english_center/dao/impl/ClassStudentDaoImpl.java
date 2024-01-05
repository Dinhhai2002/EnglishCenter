package com.english_center.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.english_center.dao.AbstractDao;
import com.english_center.dao.ClassStudentDao;
import com.english_center.entity.ClassStudent;

@Repository("ClassStudentDao")
@Transactional
public class ClassStudentDaoImpl extends AbstractDao<Integer, ClassStudent> implements ClassStudentDao {

	@Override
	public void create(ClassStudent classStudent) throws Exception {
		this.getSession().save(classStudent);
		
	}

	@Override
	public ClassStudent findOne(int id) throws Exception {
		return this.getSession().find(ClassStudent.class,id);
	}

	@Override
	public void update(ClassStudent classStudent) throws Exception {
		this.getSession().update(classStudent);
		
	}

	@Override
	public ClassStudent findByClassIdAndStudent(int classId,int studentId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<ClassStudent> query = builder.createQuery(ClassStudent.class);
		Root<ClassStudent> root = query.from(ClassStudent.class);
		query.where(builder.equal(root.get("classId"), classId));
		query.where(builder.equal(root.get("studentId"), studentId));

		return this.getSession().createQuery(query).uniqueResult();
	}

}

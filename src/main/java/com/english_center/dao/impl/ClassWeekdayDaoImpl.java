package com.english_center.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.english_center.dao.AbstractDao;
import com.english_center.dao.ClassWeekdayDao;
import com.english_center.entity.ClassWeekday;

@Repository("ClassWeekdayDao")
@Transactional
public class ClassWeekdayDaoImpl extends AbstractDao<Integer, ClassWeekday> implements ClassWeekdayDao {

	@Override
	public void create(ClassWeekday classWeekday) throws Exception {
		this.getSession().save(classWeekday);
	}

	@Override
	public ClassWeekday findOne(int id) throws Exception {
		return this.getSession().find(ClassWeekday.class, id);
	}

	@Override
	public void update(ClassWeekday classWeekday) throws Exception {
		this.getSession().update(classWeekday);

	}

	@Override
	public ClassWeekday findByAll(int classId, int weekdayId, String fromHour, String toHour) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<ClassWeekday> query = builder.createQuery(ClassWeekday.class);
		Root<ClassWeekday> root = query.from(ClassWeekday.class);
		query.where(builder.equal(root.get("classId"), classId));
		query.where(builder.equal(root.get("weekdayId"), weekdayId));
		query.where(builder.equal(root.get("fromHour"), fromHour));
		query.where(builder.equal(root.get("toHour"), toHour));

		return this.getSession().createQuery(query).uniqueResult();
	}

	@Override
	public List<ClassWeekday> getAllByClassId(int classId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<ClassWeekday> query = builder.createQuery(ClassWeekday.class);
		Root<ClassWeekday> root = query.from(ClassWeekday.class);
		query.where(builder.equal(root.get("classId"), classId));

		return this.getSession().createQuery(query).getResultList();
	}

}

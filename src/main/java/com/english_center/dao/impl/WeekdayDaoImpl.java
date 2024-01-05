package com.english_center.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.english_center.dao.AbstractDao;
import com.english_center.dao.WeekdayDao;
import com.english_center.entity.Weekday;

@Repository("WeekdayDao")
@Transactional
public class WeekdayDaoImpl extends AbstractDao<Integer, Weekday> implements WeekdayDao {

	@Override
	public void create(Weekday weekday) throws Exception {
		this.getSession().save(weekday);
	}

	@Override
	public Weekday findOne(int id) throws Exception {
		return this.getSession().find(Weekday.class, id);
	}

	@Override
	public void update(Weekday weekday) throws Exception {
		this.getSession().update(weekday);

	}

	@Override
	public List<Weekday> getAll() throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Weekday> query = builder.createQuery(Weekday.class);
//		Root<Weekday> root = query.from(Weekday.class);
		

		return this.getSession().createQuery(query).getResultList();
	}

}

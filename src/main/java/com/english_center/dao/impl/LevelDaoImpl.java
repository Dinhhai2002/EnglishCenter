package com.english_center.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.english_center.dao.AbstractDao;
import com.english_center.dao.LevelDao;
import com.english_center.entity.Level;

@Repository("LevelDao")
@Transactional
public class LevelDaoImpl extends AbstractDao<Integer, Level> implements LevelDao {

	@Override
	public void create(Level level) throws Exception {
		this.getSession().save(level);
	}

	@Override
	public Level findOne(int id) throws Exception {
		return this.getSession().find(Level.class,id);
	}

	@Override
	public void update(Level level) throws Exception {
		this.getSession().update(level);
	}

	@Override
	public Level findByNameAndCode(String name,String code) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Level> query = builder.createQuery(Level.class);
		Root<Level> root = query.from(Level.class);
		query.where(builder.equal(root.get("name"), name));
		query.where(builder.equal(root.get("code"), code));

		return this.getSession().createQuery(query).getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<Level> findAll() throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Level> query = builder.createQuery(Level.class);
//		Root<Level> root = query.from(Level.class);

		return this.getSession().createQuery(query).getResultList();
	}

}

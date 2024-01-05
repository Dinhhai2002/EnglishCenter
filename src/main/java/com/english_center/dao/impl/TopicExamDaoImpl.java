package com.english_center.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.english_center.dao.AbstractDao;
import com.english_center.dao.TopicExamDao;
import com.english_center.entity.TopicExam;

@Repository("TopicExamDao")
@Transactional
public class TopicExamDaoImpl extends AbstractDao<Integer, TopicExam> implements TopicExamDao {

	@Override
	public void create(TopicExam topicExam) throws Exception {
		this.getSession().save(topicExam);
		
	}

	@Override
	public TopicExam findOne(int id) throws Exception {
		return this.getSession().find(TopicExam.class,id);
	}

	@Override
	public void update(TopicExam topicExam) throws Exception {
		this.getSession().update(topicExam);		
		
	}

	@Override
	public TopicExam findByName(String name) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<TopicExam> query = builder.createQuery(TopicExam.class);
		Root<TopicExam> root = query.from(TopicExam.class);
		query.where(builder.equal(root.get("name"), name));

		return this.getSession().createQuery(query).getResultList().stream().findFirst().orElse(null);
	}

	@SuppressWarnings("unused")
	@Override
	public List<TopicExam> getAll() throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<TopicExam> query = builder.createQuery(TopicExam.class);
		Root<TopicExam> root = query.from(TopicExam.class);

		return this.getSession().createQuery(query).getResultList();
	}

	@Override
	public List<TopicExam> findByCategoryExamId(int categoryExamId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<TopicExam> query = builder.createQuery(TopicExam.class);
		Root<TopicExam> root = query.from(TopicExam.class);
		query.where(builder.equal(root.get("categoryExamId"), categoryExamId));

		return this.getSession().createQuery(query).getResultList();
	}

}

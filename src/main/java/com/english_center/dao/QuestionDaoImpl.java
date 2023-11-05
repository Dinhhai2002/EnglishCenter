package com.english_center.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.english_center.entity.Question;

@Repository("QuestionDao")
@Transactional
public class QuestionDaoImpl extends AbstractDao<Integer, Question> implements QuestionDao {

	@Override
	public void create(Question question) throws Exception {
		this.getSession().save(question);
		
	}

	@Override
	public Question findOne(int id) throws Exception {
		return this.getSession().find(Question.class,id);
	}

	@Override
	public void update(Question question) throws Exception {
		this.getSession().update(question);		
	}

	@Override
	public List<Question> getListByExamId(int examId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Question> query = builder.createQuery(Question.class);
		Root<Question> root = query.from(Question.class);
		query.where(builder.equal(root.get("examId"), examId));

		return this.getSession().createQuery(query).getResultList();
	}

}

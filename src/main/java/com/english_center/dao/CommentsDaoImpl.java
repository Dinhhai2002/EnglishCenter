package com.english_center.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.english_center.entity.Comments;

@Repository("CommentsDao")
@Transactional
public class CommentsDaoImpl extends AbstractDao<Integer, Comments> implements CommentsDao{

	@Override
	public void create(Comments comments) throws Exception {
		this.getSession().save(comments);
	}

	@Override
	public Comments findOne(int id) throws Exception {
		return this.getSession().find(Comments.class,id);
	}

	@Override
	public void update(Comments comments) throws Exception {
		this.getSession().update(comments);
	}
	
	@Override
	public void delete(Comments comments) {
		this.getSession().delete(comments);
	}

	@Override
	public List<Comments> findByExamId(int examId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Comments> query = builder.createQuery(Comments.class);
		Root<Comments> root = query.from(Comments.class);
		query.where(builder.equal(root.get("examId"), examId));

		return this.getSession().createQuery(query).getResultList();
	}


}

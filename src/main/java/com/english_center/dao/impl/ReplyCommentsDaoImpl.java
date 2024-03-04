package com.english_center.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.english_center.dao.AbstractDao;
import com.english_center.dao.ReplyCommentsDao;
import com.english_center.entity.ReplyComments;

@Repository("ReplyCommentsDao")
@Transactional
public class ReplyCommentsDaoImpl extends AbstractDao<Integer, ReplyComments> implements ReplyCommentsDao {

	@Override
	public void create(ReplyComments replyComments) throws Exception {
		this.getSession().save(replyComments);
	}

	@Override
	public ReplyComments findOne(int id) throws Exception {
		return this.getSession().find(ReplyComments.class, id);
	}

	@Override
	public void update(ReplyComments replyComments) throws Exception {
		this.getSession().update(replyComments);
	}

	@Override
	public void delete(ReplyComments replyComments) {
		this.getSession().delete(replyComments);
	}

	@Override
	public List<ReplyComments> findByCommentId(int commentsId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<ReplyComments> query = builder.createQuery(ReplyComments.class);
		Root<ReplyComments> root = query.from(ReplyComments.class);
		query.where(builder.equal(root.get("commentsId"), commentsId));

		return this.getSession().createQuery(query).getResultList();
	}

	@Override
	public List<ReplyComments> getAll() throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<ReplyComments> query = builder.createQuery(ReplyComments.class);
		Root<ReplyComments> root = query.from(ReplyComments.class);

		return this.getSession().createQuery(query).getResultList();
	}

}

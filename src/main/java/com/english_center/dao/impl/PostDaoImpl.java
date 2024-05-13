package com.english_center.dao.impl;

import java.util.List;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.english_center.common.enums.StoreProcedureStatusCodeEnum;
import com.english_center.common.exception.TechresHttpException;
import com.english_center.common.utils.Pagination;
import com.english_center.dao.AbstractDao;
import com.english_center.dao.PostDao;
import com.english_center.entity.Posts;
import com.english_center.model.PostModel;
import com.english_center.model.StoreProcedureListResult;

@Repository("PostDao")
@Transactional
public class PostDaoImpl extends AbstractDao<Integer, Posts> implements PostDao {

	@Override
	public void create(Posts posts) throws Exception {
		this.getSession().save(posts);
	}

	@Override
	public Posts findOne(int id) throws Exception {
		return this.getSession().find(Posts.class, id);
	}

	@Override
	public void update(Posts posts) throws Exception {
		this.getSession().update(posts);
	}

	@Override
	public List<Posts> getAll() throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Posts> query = builder.createQuery(Posts.class);
		Root<Posts> root = query.from(Posts.class);
//		query.where(builder.equal(root.get("name"), name));

		return this.getSession().createQuery(query).getResultList();
	}

	@Override
	public StoreProcedureListResult<PostModel> spGPosts(int userId, int categoryBlogId, String keySearch, int status,
			Pagination pagination) throws Exception {
		StoredProcedureQuery query = this.getSession().createStoredProcedureQuery("sp_g_list_post", PostModel.class)
				.registerStoredProcedureParameter("userId", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("categoryBlogId", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("keySearch", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("status", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_limit", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_offset", Integer.class, ParameterMode.IN)

				.registerStoredProcedureParameter("total_record", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("status_code", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("message_error", String.class, ParameterMode.OUT);

		query.setParameter("userId", userId);
		query.setParameter("categoryBlogId", categoryBlogId);
		query.setParameter("keySearch", keySearch);
		query.setParameter("status", status);
		query.setParameter("_limit", pagination.getLimit());
		query.setParameter("_offset", pagination.getOffset());

		int statusCode = (int) query.getOutputParameterValue("status_code");
		String messageError = query.getOutputParameterValue("message_error").toString();

		switch (StoreProcedureStatusCodeEnum.valueOf(statusCode)) {
		case SUCCESS:
			int totalRecord = (int) query.getOutputParameterValue("total_record");
			return new StoreProcedureListResult<>(statusCode, messageError, totalRecord, query.getResultList());
		case INPUT_INVALID:
			throw new TechresHttpException(HttpStatus.BAD_REQUEST, messageError);
		default:
			throw new Exception(messageError);
		}
	}

}

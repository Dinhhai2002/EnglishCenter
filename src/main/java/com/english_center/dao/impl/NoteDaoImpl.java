package com.english_center.dao.impl;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.english_center.common.enums.StoreProcedureStatusCodeEnum;
import com.english_center.common.exception.TechresHttpException;
import com.english_center.common.utils.Pagination;
import com.english_center.dao.AbstractDao;
import com.english_center.dao.NoteDao;
import com.english_center.entity.Note;
import com.english_center.model.StoreProcedureListResult;

@Repository("NoteDao")
@Transactional
public class NoteDaoImpl extends AbstractDao<Integer, Note> implements NoteDao {

	@Override
	public void create(Note note) throws Exception {
		this.getSession().save(note);
	}

	@Override
	public Note findOne(int id) throws Exception {
		return this.getSession().find(Note.class, id);
	}

	@Override
	public void update(Note note) throws Exception {
		this.getSession().update(note);
	}

	@SuppressWarnings("unchecked")
	@Override
	public StoreProcedureListResult<Note> spGListNote(int courseId, int chapterId,int lessonsId, String keySearch, int status,
			Pagination pagination, int isPagination) throws Exception {
		StoredProcedureQuery query = this.getSession().createStoredProcedureQuery("sp_g_list_note", Note.class)
				.registerStoredProcedureParameter("courseId", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("chapterId", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("lessonsId", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("keySearch", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("status", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("isPagination", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_limit", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("_offset", Integer.class, ParameterMode.IN)

				.registerStoredProcedureParameter("total_record", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("status_code", Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("message_error", String.class, ParameterMode.OUT);

		query.setParameter("courseId", courseId);
		query.setParameter("chapterId", chapterId);
		query.setParameter("lessonsId", lessonsId);
		query.setParameter("keySearch", keySearch);
		query.setParameter("status", status);
		query.setParameter("isPagination", isPagination);
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

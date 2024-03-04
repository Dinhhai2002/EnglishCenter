package com.english_center.dao;

import com.english_center.common.utils.Pagination;
import com.english_center.entity.Note;
import com.english_center.model.StoreProcedureListResult;

public interface NoteDao {
	void create(Note note) throws Exception;

	Note findOne(int id) throws Exception;

	void update(Note note) throws Exception;

	StoreProcedureListResult<Note> spGListNote(int courseId, int chapterId, int lessonsId, String keySearch, int status,
			Pagination pagination, int isPagination) throws Exception;
}

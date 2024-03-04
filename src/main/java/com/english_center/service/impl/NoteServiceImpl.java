package com.english_center.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.common.utils.Pagination;
import com.english_center.dao.NoteDao;
import com.english_center.entity.Note;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.service.NoteService;

@Service("NoteService")
@Transactional(rollbackFor = Error.class)
public class NoteServiceImpl implements NoteService {

	@Autowired
	NoteDao noteDao;

	@Override
	public void create(Note note) throws Exception {
		noteDao.create(note);
	}

	@Override
	public Note findOne(int id) throws Exception {
		return noteDao.findOne(id);
	}

	@Override
	public void update(Note note) throws Exception {
		noteDao.update(note);
	}

	@Override
	public StoreProcedureListResult<Note> spGListNote(int courseId, int chapterId, int lessonsId, String keySearch,
			int status, Pagination pagination, int isPagination) throws Exception {
		return noteDao.spGListNote(courseId, chapterId, lessonsId, keySearch, status, pagination, isPagination);
	}

}

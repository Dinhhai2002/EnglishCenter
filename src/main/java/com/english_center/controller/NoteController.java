package com.english_center.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.common.utils.Pagination;
import com.english_center.common.utils.StringErrorValue;
import com.english_center.entity.Chapter;
import com.english_center.entity.Course;
import com.english_center.entity.Lessons;
import com.english_center.entity.Note;
import com.english_center.model.StoreProcedureListResult;
import com.english_center.request.CRUDNoteRequest;
import com.english_center.response.BaseListDataResponse;
import com.english_center.response.BaseResponse;
import com.english_center.response.NoteResponse;

@RestController
@RequestMapping("/api/v1/note")
public class NoteController extends BaseController {

	@PostMapping("/create")
	public ResponseEntity<BaseResponse<NoteResponse>> create(@Valid @RequestBody CRUDNoteRequest wrapper)
			throws Exception {

		BaseResponse<NoteResponse> response = new BaseResponse<>();

		Course checkCourse = courseService.findOne(wrapper.getCourseId());

		if (checkCourse == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.COURSE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		Chapter chapter = chapterService.findOne(wrapper.getChapterId());

		if (chapter == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CHAPTER_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		Lessons lessons = lessonsService.findOne(wrapper.getLessonsId());

		if (lessons == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.LESSONS_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		Note note = new Note();
		note.setContent(wrapper.getContent());
		note.setCourseId(wrapper.getCourseId());
		note.setChapterId(wrapper.getChapterId());
		note.setLessonsId(wrapper.getLessonsId());
		note.setStatus(1);

		noteService.create(note);

		response.setData(new NoteResponse(note));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/{id}/update")
	public ResponseEntity<BaseResponse<NoteResponse>> update(@PathVariable("id") int id,
			@Valid @RequestBody CRUDNoteRequest wrapper) throws Exception {

		BaseResponse<NoteResponse> response = new BaseResponse<>();

		Note note = noteService.findOne(id);

		if (note == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.NOTE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		note.setContent(wrapper.getContent());
		noteService.update(note);

		response.setData(new NoteResponse(note));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/{id}/change-status")
	public ResponseEntity<BaseResponse<NoteResponse>> update(@PathVariable("id") int id) throws Exception {

		BaseResponse<NoteResponse> response = new BaseResponse<>();

		Note note = noteService.findOne(id);

		if (note == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.NOTE_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		note.setStatus(note.getStatus() == 1 ? 0 : 1);
		noteService.update(note);

		response.setData(new NoteResponse(note));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{id}/detail")
	public ResponseEntity<BaseResponse<NoteResponse>> findOne(@PathVariable("id") int id) throws Exception {

		BaseResponse<NoteResponse> response = new BaseResponse<>();

		Note note = noteService.findOne(id);

		response.setData(new NoteResponse(note));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<BaseResponse<BaseListDataResponse<NoteResponse>>> getAll(
			@RequestParam(name = "course_id", required = false, defaultValue = "-1") int courseId,
			@RequestParam(name = "chapter_id", required = false, defaultValue = "-1") int chapterId,
			@RequestParam(name = "lessons_id", required = false, defaultValue = "-1") int lessonsId,
			@RequestParam(name = "key_search", required = false, defaultValue = "") String keySearch,
			@RequestParam(name = "status", required = false, defaultValue = "-1") int status,
			@RequestParam(name = "is_pagination", required = false, defaultValue = "1") int isPagination,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "limit", required = false, defaultValue = "20") int limit) throws Exception {
		BaseResponse<BaseListDataResponse<NoteResponse>> response = new BaseResponse<>();
		Pagination pagination = new Pagination(page, limit);
		StoreProcedureListResult<Note> listNote = noteService.spGListNote(courseId, chapterId, lessonsId, keySearch,
				status, pagination, isPagination);

		BaseListDataResponse<NoteResponse> listData = new BaseListDataResponse<>();

		listData.setList(new NoteResponse().mapToList(listNote.getResult()));
		listData.setTotalRecord(listNote.getTotalRecord());

		response.setData(listData);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}

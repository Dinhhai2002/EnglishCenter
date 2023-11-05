package com.english_center.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.common.utils.StringErrorValue;
import com.english_center.entity.Level;
import com.english_center.request.CRUDLevelRequest;
import com.english_center.response.BaseResponse;
import com.english_center.response.LevelResponse;
import com.english_center.service.LevelService;

@RestController
@RequestMapping("/api/v1/level")
public class LevelController extends BaseController {
	@Autowired
	LevelService levelService;

	@SuppressWarnings("rawtypes")
	@PostMapping("/create")
	public ResponseEntity<BaseResponse> create(@Valid @RequestBody CRUDLevelRequest wrapper) throws Exception {

		BaseResponse response = new BaseResponse();

		Level level = new Level();
		level.setName(wrapper.getName());
		level.setCode(wrapper.getCode());

		levelService.create(level);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/{id}/update")
	public ResponseEntity<BaseResponse> update(@Valid @RequestBody CRUDLevelRequest wrapper, @PathVariable("id") int id)
			throws Exception {

		BaseResponse response = new BaseResponse();

		Level level = levelService.findOne(id);

		if (level == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.LEVEL_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		Level checkLevel = levelService.findByNameAndCode(wrapper.getName(), wrapper.getCode());
		if (checkLevel != null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.LEVEL_IS_EXIST);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		level.setName(wrapper.getName());
		level.setCode(wrapper.getCode());

		levelService.update(level);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/{id}")
	public ResponseEntity<BaseResponse<LevelResponse>> findOne(@PathVariable("id") int id) throws Exception {

		BaseResponse<LevelResponse> response = new BaseResponse();
		Level level = levelService.findOne(id);

		if (level == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.LEVEL_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		response.setData(new LevelResponse(level));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("")
	public ResponseEntity<BaseResponse<List<LevelResponse>>> findAll() throws Exception {

		BaseResponse<List<LevelResponse>> response = new BaseResponse();

		List<Level> levels = levelService.findAll();

		response.setData(new LevelResponse().mapToList(levels));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}

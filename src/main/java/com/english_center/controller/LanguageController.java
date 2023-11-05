//package com.english_center.controller;
//
//import java.util.List;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.english_center.common.utils.StringErrorValue;
//import com.english_center.entity.Language;
//import com.english_center.request.CRUDLanguageRequest;
//import com.english_center.response.BaseResponse;
//import com.english_center.response.LanguageResponse;
//import com.english_center.service.LanguageService;
//
//@RestController
//@RequestMapping("/api/v1/language")
//public class LanguageController {
//
//	@Autowired
//	LanguageService languageService;
//
//	@SuppressWarnings("rawtypes")
//	@PostMapping("/create")
//	public ResponseEntity<BaseResponse> create(@Valid @RequestBody CRUDLanguageRequest wrapper) throws Exception {
//
//		BaseResponse response = new BaseResponse();
//		Language checkLanguage = languageService.findByName(wrapper.getName());
//
//		if (checkLanguage != null) {
//			response.setStatus(HttpStatus.BAD_REQUEST);
//			response.setMessageError(StringErrorValue.LANGUAGES_NOT_FOUND);
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		}
//
//		Language language = new Language();
//		language.setName(wrapper.getName());
//
//		languageService.create(language);
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}
//
//	@SuppressWarnings("rawtypes")
//	@PostMapping("/{id}/update")
//	public ResponseEntity<BaseResponse> update(@Valid @RequestBody CRUDLanguageRequest wrapper,
//			@PathVariable("id") int id) throws Exception {
//
//		BaseResponse response = new BaseResponse();
//
//		Language language = languageService.findOne(id);
//
//		if (language == null) {
//			response.setStatus(HttpStatus.BAD_REQUEST);
//			response.setMessageError(StringErrorValue.LANGUAGES_NOT_FOUND);
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		}
//
//		Language checkLanguage = languageService.findByName(wrapper.getName());
//
//		if (checkLanguage != null) {
//			response.setStatus(HttpStatus.BAD_REQUEST);
//			response.setMessageError(StringErrorValue.LANGUAGES_IS_EXIST);
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		}
//
//		language.setName(wrapper.getName());
//
//		languageService.update(language);
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@GetMapping("/{id}")
//	public ResponseEntity<BaseResponse<LanguageResponse>> findOne(@PathVariable("id") int id) throws Exception {
//
//		BaseResponse<LanguageResponse> response = new BaseResponse();
//
//		Language language = languageService.findOne(id);
//
//		if (language == null) {
//			response.setStatus(HttpStatus.BAD_REQUEST);
//			response.setMessageError(StringErrorValue.LANGUAGES_NOT_FOUND);
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		}
//
//		response.setData(new LanguageResponse(language));
//
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@GetMapping("")
//	public ResponseEntity<BaseResponse<List<LanguageResponse>>> findAll() throws Exception {
//
//		BaseResponse<List<LanguageResponse>> response = new BaseResponse();
//
//		List<Language> languages = languageService.findAll();
//
//		response.setData(new LanguageResponse().mapToList(languages));
//
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}
//}

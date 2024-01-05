package com.english_center.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.english_center.response.BaseResponse;

@SuppressWarnings("rawtypes")
@RestControllerAdvice
public class BaseThrowException {
	@SuppressWarnings("unchecked")
	@ExceptionHandler(TechresHttpException.class)
	protected ResponseEntity<BaseResponse<Object>> handleTechresHttpException(TechresHttpException ex,
			WebRequest request) {
//		HttpStatus status = ex.getHttpStatus();

		BaseResponse response = new BaseResponse();

		response.setData(null);
		response.setStatus(ex.getHttpStatus());
		response.setMessageError(ex.getErrorMessage());

		return new ResponseEntity(response, ex.getHttpStatus());
	}
}

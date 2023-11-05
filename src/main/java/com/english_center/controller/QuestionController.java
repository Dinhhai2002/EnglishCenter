package com.english_center.controller;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.english_center.entity.Question;
import com.english_center.response.BaseResponse;
import com.english_center.service.QuestionService;

@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {
	@Autowired
	QuestionService questionService;

	@SuppressWarnings({ "resource", "rawtypes" })
	@PostMapping("/upload-file")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<BaseResponse> upload(@RequestParam(name = "file") MultipartFile file) throws Exception {
		BaseResponse response = new BaseResponse();
		Workbook workbook = new XSSFWorkbook(file.getInputStream());
		
		Sheet sheet = workbook.getSheetAt(0);
		
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			String content = "";
			String paragraph = "";
			String urlImage = "";
			String answerA = "";
			String answerB = "";
			String answerC = "";
			String answerD = "";
			String result = "";
			int examDetailId = 0;
			int sort = 0;
			int examId = 0;

			if (row.getCell(0) != null && row.getCell(0).getCellType() == CellType.STRING) {
				content = row.getCell(0).getStringCellValue();
			}
			if (row.getCell(1) != null && row.getCell(1).getCellType() == CellType.STRING) {
				paragraph = row.getCell(1).getStringCellValue();
			}
			if (row.getCell(2) != null && row.getCell(2).getCellType() == CellType.STRING) {
				urlImage = row.getCell(2).getStringCellValue();
			}
			if (row.getCell(3) != null && row.getCell(3).getCellType() == CellType.STRING) {
				answerA = row.getCell(3).getStringCellValue();
			}
			if (row.getCell(4) != null && row.getCell(4).getCellType() == CellType.STRING) {
				answerB = row.getCell(4).getStringCellValue();
			}
			if (row.getCell(5) != null && row.getCell(5).getCellType() == CellType.STRING) {
				answerC = row.getCell(5).getStringCellValue();
			}
			if (row.getCell(6) != null && row.getCell(6).getCellType() == CellType.STRING) {
				answerD = row.getCell(6).getStringCellValue();
			}
			if (row.getCell(7) != null && row.getCell(7).getCellType() == CellType.STRING) {
				result = row.getCell(7).getStringCellValue();
			}
			if (row.getCell(8) != null && row.getCell(8).getCellType() == CellType.NUMERIC) {
				examDetailId = (int) row.getCell(8).getNumericCellValue();
			}
			if (row.getCell(9) != null && row.getCell(9).getCellType() == CellType.NUMERIC) {
				sort = (int) row.getCell(9).getNumericCellValue();
			}

			if (row.getCell(10) != null && row.getCell(10).getCellType() == CellType.NUMERIC) {
				examId = (int) row.getCell(10).getNumericCellValue();
			}

			Question question = new Question();
			question.setContent(content);
			question.setParagraph(paragraph);
			question.setUrlImage(urlImage);
			question.setAnswerA(answerA);
			question.setAnswerB(answerB);
			question.setAnswerC(answerC);
			question.setAnswerD(answerD);
			question.setResult(result);
			question.setExamDetailId(examDetailId);
			question.setSort(sort);
			question.setExamId(examId);

			questionService.create(question);

		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}

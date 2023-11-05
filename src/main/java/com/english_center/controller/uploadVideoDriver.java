//package com.english_center.controller;
//
//import java.io.ByteArrayInputStream;
//import java.util.Collections;
//import java.util.UUID;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.english_center.response.BaseResponse;
//import com.google.api.client.http.InputStreamContent;
//import com.google.api.services.drive.Drive;
//import com.google.api.services.drive.model.File;
//import com.google.api.services.drive.model.Permission;
//
//@RestController
//@RequestMapping("/api/v1/upload-driver")
//public class uploadVideoDriver extends BaseController {
//
//	@Autowired
//	Drive googleDrive;
//
//	public static final String FOLDER_TO_UPLOAD = "1ZcI0cLXncMYOhylO4jZBgWj2yVsj5WdB";
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@PostMapping("")
//	public ResponseEntity<BaseResponse> findByWardId(MultipartFile file) throws Exception {
//
//		BaseResponse response = new BaseResponse();
//
//		File fileMetadata = new File();
//		fileMetadata.setParents(Collections.singletonList(FOLDER_TO_UPLOAD));
//		String fileName = UUID.randomUUID().toString() + file.getOriginalFilename();
//		fileMetadata.setName(fileName);
//		File uploadFile = googleDrive.files()
//				.create(fileMetadata,
//						new InputStreamContent(file.getContentType(),
//								new ByteArrayInputStream(file.getBytes())))
//				.setFields("id, size, mimeType, webViewLink, videoMediaMetadata, webContentLink").execute();
//		googleDrive.permissions().create(uploadFile.getId(), this.getPermission()).execute();
//		
//		
//
//		response.setData(uploadFile);
//
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}
//
//
//}

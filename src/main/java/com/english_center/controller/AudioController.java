package com.english_center.controller;

import java.io.File;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.english_center.entity.Audio;
import com.english_center.response.BaseResponse;
import com.english_center.service.AudioService;

@RestController
@RequestMapping("/api/v1/audio")
public class AudioController extends BaseController {
	@Autowired
	AudioService audioService;

	@Value("${upload.path}")
	private String fileUpload;

	@SuppressWarnings("rawtypes")
	@PostMapping("/upload")
	public ResponseEntity<BaseResponse> save(@RequestParam(name = "file") MultipartFile file) throws Exception {
		BaseResponse response = new BaseResponse();
		String fileExtension = getFileExtension(file.getOriginalFilename());

		if (!fileExtension.equals("mp3") && !fileExtension.equals("mp4")) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError("Chỉ hỗ trợ file mp3 và mp4!");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		String fileName = UUID.randomUUID().toString() + file.getOriginalFilename();
		String filePath = Paths.get(fileUpload, fileName).toString();
		
		file.transferTo(new File(filePath));
		Audio audio = new Audio();
		audio.setUrl(fileName);
		audio.setExamId(1);
		audioService.create(audio);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/{id}")
	public ResponseEntity<BaseResponse> findOne(@PathVariable("id") int id) throws Exception {
		BaseResponse response = new BaseResponse();

		response.setData(audioService.findOne(id));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	

	private String getFileExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf('.');
		if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
			return fileName.substring(dotIndex + 1);
		}
		return "";
	}
}

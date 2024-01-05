//package com.english_center.controller;
//
//import java.io.File;
//import java.nio.file.Paths;
//import java.util.UUID;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.english_center.entity.Image;
//import com.english_center.entity.Users;
//import com.english_center.response.BaseResponse;
//import com.english_center.response.ImageResponse;
//import com.english_center.service.IFirebaseImageService;
//import com.english_center.service.ImageService;
//import com.english_center.service.UserService;
//
//@RestController
//@RequestMapping("/api/v1/image")
//public class ImageController extends BaseController {
//
//	@Value("${upload.path}")
//	private String fileUpload;
//
//	@PostMapping("/upload-avatar")
//	public ResponseEntity<BaseResponse<ImageResponse>> create(@RequestParam(name = "file") MultipartFile file)
//			throws Exception {
//
//		BaseResponse<ImageResponse> response = new BaseResponse<>();
//		Users user = this.getUser();
//		String name = iFirebaseImageService.generateFileName(file.getOriginalFilename());
//
//		String fileName = iFirebaseImageService.save(file);
//
//		String imageUrl = iFirebaseImageService.getImageUrl(fileName);
//
//		Image image = new Image();
//		image.setUserId(user.getId());
//		image.setUrl(imageUrl);
//
//		imageService.create(image);
//		user.setAvatarId(image.getId());
//		user.setAvatarUrl(image.getUrl());
//
//		userService.update(user);
//
//		response.setData(new ImageResponse(image));
//
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}
//
//	@SuppressWarnings("rawtypes")
//	@PostMapping("/{id}/delete")
//	public ResponseEntity<BaseResponse> delete(@PathVariable(name = "id") int id) throws Exception {
//		BaseResponse response = new BaseResponse<>();
//		Users user = this.getUser();
//
//		Image image = imageService.findOne(id);
//
//		imageService.delete(image);
//
//		Image noImage = imageService.findOne(1);
//		user.setAvatarId(1);
//		user.setAvatarUrl(noImage.getUrl());
//
//		userService.update(user);
//
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}
//
//	@PostMapping("/create")
//	public ResponseEntity<BaseResponse> save(@RequestParam(name = "file") MultipartFile file) throws Exception {
//		BaseResponse response = new BaseResponse();
//		String fileExtension = getFileExtension(file.getOriginalFilename());
//
////		if (!fileExtension.equals("mp3") && !fileExtension.equals("mp4")) {
////			response.setStatus(HttpStatus.BAD_REQUEST);
////			response.setMessageError("Chỉ hỗ trợ file mp3 và mp4!");
////			return new ResponseEntity<>(response, HttpStatus.OK);
////		}
//		String fileName = UUID.randomUUID().toString() + file.getOriginalFilename();
//		String filePath = Paths.get(fileUpload, fileName).toString();
//		file.transferTo(new File(filePath));
//		Image image = new Image();
//		image.setUrl(fileName);
//		image.setUserId(1);
//		imageService.create(image);
//
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}
//
//	@SuppressWarnings("unchecked")
//	@GetMapping("/{id}")
//	public ResponseEntity<BaseResponse> findOne(@PathVariable("id") int id) throws Exception {
//		BaseResponse response = new BaseResponse();
//
//		response.setData(imageService.findOne(id));
//
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}
//
//	private String getFileExtension(String fileName) {
//		int dotIndex = fileName.lastIndexOf('.');
//		if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
//			return fileName.substring(dotIndex + 1);
//		}
//		return "";
//	}
//
//}

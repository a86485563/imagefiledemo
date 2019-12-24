package com.biquan.mysqljapimagefiledemo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.biquan.mysqljapimagefiledemo.domain.model.Imagefile;
import com.biquan.mysqljapimagefiledemo.domain.service.ImagefileService;
import com.biquan.mysqljapimagefiledemo.playload.UploadFileResponse;

@RestController
@RequestMapping("/apis")
public class Controller {

	private static final Logger logger = LoggerFactory.getLogger(Controller.class);
	@Autowired
	private ImagefileService imagefileService;

	@PostMapping("/uploadFile")
	public UploadFileResponse upload(MultipartFile file) {
		Imagefile imagefile = imagefileService.storeImage(file);
		// create links based on the current HttpServletRequest.
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(imagefile.getId()).toUriString();

		UploadFileResponse result = new UploadFileResponse(imagefile.getFileName(), fileDownloadUri,
				file.getContentType(), file.getSize());

		return result;
	}

	@PostMapping("/uploadMultipleFiles")
	public List<UploadFileResponse> uploadMulti(MultipartFile[] files)
	{
		return Arrays.asList(files).stream()
				.map(file -> upload(file)).collect(Collectors.toList());
//		return Arrays.asList(files)
//                .stream()
//                .map(file -> upload(file))
//                .collect(Collectors.toList());
//		
	}

	   @GetMapping("/downloadFile/{fileId}")
	    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileId) {
	        // Load file from database
	        Imagefile imagefile = imagefileService.getFile(fileId);

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(imagefile.getFileType()))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imagefile.getFileName() + "\"")
	                .body(imagefile.getData());
	    }
}



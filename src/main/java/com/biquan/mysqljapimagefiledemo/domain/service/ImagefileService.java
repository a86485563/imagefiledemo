package com.biquan.mysqljapimagefiledemo.domain.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.biquan.mysqljapimagefiledemo.domain.dao.repository.ImagefileRepository;
import com.biquan.mysqljapimagefiledemo.domain.model.Imagefile;
import com.biquan.mysqljapimagefiledemo.exception.FileStorageException;
import com.biquan.mysqljapimagefiledemo.exception.MyFileNotFoundException;

@Service
public class ImagefileService {

	@Autowired
	private ImagefileRepository imagefileRepository;

	public Imagefile storeImage(MultipartFile multFile) {
		// Normalize file name
		// 存入圖片
		String fileName = StringUtils.cleanPath(multFile.getOriginalFilename());

		Imagefile result = null;
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			result = new Imagefile(fileName, multFile.getContentType(), multFile.getBytes());

			return imagefileRepository.save(result);
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public Imagefile getFile(String fileId) {
		return imagefileRepository.findById(fileId)
				.orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
	}
}

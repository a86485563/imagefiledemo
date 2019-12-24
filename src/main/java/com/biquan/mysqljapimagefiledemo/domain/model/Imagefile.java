package com.biquan.mysqljapimagefiledemo.domain.model;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "imagefile")
public class Imagefile {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Column(name = "fileName")
	private String fileName;

	@Column(name = "fileType")
	private String fileType;

	@Lob // 告訴資料庫 large object
	@Column(name = "data")
	private byte[] data;

	// pojo object

	public Imagefile(String fileName, String fileType, byte[] data) {
		super();

		this.fileName = fileName;
		this.fileType = fileType;
		this.data = data;
	}

	public Imagefile() {

	}

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}

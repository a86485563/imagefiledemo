package com.biquan.mysqljapimagefiledemo.domain.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.biquan.mysqljapimagefiledemo.domain.model.Imagefile;

@Repository
public interface ImagefileRepository extends JpaRepository<Imagefile, String> {

}

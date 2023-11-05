package com.english_center.dao;

import com.english_center.entity.Image;

public interface ImageDao {
	Image findOne(int id) throws Exception;

	void update(Image image) throws Exception;
	
	Image create(Image image) throws Exception;
	
	void delete(Image image) ;
}

package com.english_center.dao;

import com.english_center.entity.Audio;

public interface AudioDao {
	Audio findOne(int id) throws Exception;

	void update(Audio audio) throws Exception;
	
	Audio create(Audio audio) throws Exception;
	
	void delete(Audio audio) ;
	
	Audio findByExamId(int examId) throws Exception;
}

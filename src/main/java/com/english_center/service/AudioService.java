package com.english_center.service;

import com.english_center.entity.Audio;

public interface AudioService {
	Audio findOne(int id) throws Exception;

	void update(Audio audio) throws Exception;
	
	Audio create(Audio audio) throws Exception;
	
	void delete(Audio audio) ;
	
	Audio findByExamId(int examId) throws Exception;
}

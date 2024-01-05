package com.english_center.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.dao.AudioDao;
import com.english_center.entity.Audio;
import com.english_center.service.AudioService;

@Service("AudioService")
@Transactional(rollbackFor = Error.class)
public class AudioServiceImpl implements AudioService {
	
	@Autowired
	AudioDao audioDao;

	@Override
	public Audio findOne(int id) throws Exception {
		
		return audioDao.findOne(id);
	}

	@Override
	public void update(Audio audio) throws Exception {
		audioDao.update(audio);
		
	}

	@Override
	public Audio create(Audio audio) throws Exception {
		return audioDao.create(audio);
	}

	@Override
	public void delete(Audio audio) {
		audioDao.delete(audio);
	}

	@Override
	public Audio findByExamId(int examId) throws Exception {
		return audioDao.findByExamId(examId);
	}
	
	

}

package com.english_center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.dao.LevelDao;
import com.english_center.entity.Level;
import com.english_center.service.LevelService;

@Service("LevelService")
@Transactional(rollbackFor = Error.class)
public class LevelServiceImpl implements LevelService {

	@Autowired
	LevelDao levelDao;
	
	@Override
	public void create(Level level) throws Exception {
		levelDao.create(level);
	}

	@Override
	public Level findOne(int id) throws Exception {
		return levelDao.findOne(id);
	}

	@Override
	public void update(Level level) throws Exception {
		levelDao.update(level);
	}

	@Override
	public Level findByNameAndCode(String name, String code) throws Exception {
		return levelDao.findByNameAndCode(name, code);
	}

	@Override
	public List<Level> findAll() throws Exception {
		return levelDao.findAll();
	}

	

}

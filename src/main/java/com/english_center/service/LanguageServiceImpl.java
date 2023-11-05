//package com.english_center.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.english_center.dao.LanguageDao;
//import com.english_center.entity.Language;
//import com.english_center.entity.Level;
//
//@Service("LanguageService")
//@Transactional(rollbackFor = Error.class)
//public class LanguageServiceImpl implements LanguageService{
//	
//	@Autowired
//	LanguageDao languageDao;
//
//	@Override
//	public void create(Language language) throws Exception {
//		languageDao.create(language);
//	}
//
//	@Override
//	public Language findOne(int id) throws Exception {
//		return languageDao.findOne(id);
//	}
//
//	@Override
//	public void update(Language language) throws Exception {
//		languageDao.update(language);
//	}
//
//	@Override
//	public Language findByName(String name) throws Exception {
//		// TODO Auto-generated method stub
//		return languageDao.findByName(name);
//	}
//
//	@Override
//	public List<Language> findAll() throws Exception {
//		return languageDao.findAll();
//	}
//
//}

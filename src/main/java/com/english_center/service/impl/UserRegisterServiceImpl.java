package com.english_center.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.dao.UserRegisterDao;
import com.english_center.entity.UserRegister;
import com.english_center.service.UserRegisterService;

@Service("UserRegisterService")
@Transactional(rollbackFor = Error.class)
public class UserRegisterServiceImpl implements UserRegisterService {

	@Autowired
	UserRegisterDao userRegisterDao;

	@Override
	public void create(UserRegister userRegister) throws Exception {
		userRegisterDao.create(userRegister);
	}

	@Override
	public UserRegister findOne(int id) throws Exception {
		return userRegisterDao.findOne(id);
	}

	@Override
	public void update(UserRegister userRegister) throws Exception {
		userRegisterDao.update(userRegister);
	}

	@Override
	public UserRegister findUsersRegisterByUsersNameAndEmail(String UsersName, String email) throws Exception {
		return userRegisterDao.findUsersRegisterByUsersNameAndEmail(UsersName, email);
	}

	@Override
	public void delete(UserRegister userRegister) {
		userRegisterDao.delete(userRegister);
	}

}

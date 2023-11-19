package com.english_center.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.english_center.common.utils.Pagination;
import com.english_center.dao.UserDao;
import com.english_center.entity.Users;
import com.english_center.model.StoreProcedureListResult;

@Service("UsersService")
@Transactional(rollbackFor = Error.class)
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao UsersDao;

	@Override
	public Users findOne(int id) throws Exception {
		return UsersDao.findOne(id);
	}

	@Override
	public StoreProcedureListResult<Users> spGUsers(String keyword, int status, int role, Pagination pagination)
			throws Exception {
		return UsersDao.spGUsers(keyword, status, role, pagination);
	}

	@Override
	public int deleteUsers(int id) throws Exception {
		return UsersDao.deleteUsers(id);
	}

	@Override
	public Users findUsersByPhone(String phone) throws Exception {
		return UsersDao.findUsersByPhone(phone);
	}

	@Override
	public Users findUsersByUsersName(String UsersName) throws Exception {
		return UsersDao.findUsersByUsersName(UsersName);
	}

	@Override
	public void update(Users user) throws Exception {
		UsersDao.update(user);

	}

	@Override
	public Users spUCreateUsers(String userName, String fullName, String email, String phone, String password,
			int gender, String birthday, int wardId, int districtId, int cityId, String fullAddress) throws Exception {

		return UsersDao.spUCreateUsers(userName, fullName, email, phone, password, gender, birthday, wardId, districtId,
				cityId, fullAddress);
	}

	@Override
	public Users findUsersByEmail(String email, int isGoogle) throws Exception {
		return UsersDao.findUsersByEmail(email, isGoogle);
	}

	@Override
	public void findUsersByIdAndUpdateIsActive(int id, int isActive) {
		UsersDao.findUsersByIdAndUpdateIsActive(id, isActive);

	}

	@Override
	public Users findUsersByUsersNameAndEmail(String UsersName, String email) throws Exception {
		return UsersDao.findUsersByUsersNameAndEmail(UsersName, email);
	}

	@Override
	public List<Users> getAll() throws Exception {
		return UsersDao.getAll();
	}

	@Override
	public void create(Users user) throws Exception {
		UsersDao.create(user);
	}

	@Override
	public Users findUsersByUsersNameAndPassword(String usersName, String password) throws Exception {
		return UsersDao.findUsersByUsersNameAndPassword(usersName, password);
	}

}

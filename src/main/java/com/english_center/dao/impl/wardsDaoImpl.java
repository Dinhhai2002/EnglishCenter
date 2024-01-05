package com.english_center.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.english_center.dao.AbstractDao;
import com.english_center.dao.WardsDao;
import com.english_center.entity.Wards;

@Repository("wardsDao")
@Transactional
public class wardsDaoImpl extends AbstractDao<Integer, Wards> implements WardsDao {

	@Override
	public List<Wards> findByDistrictId(int districtId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Wards> query = builder.createQuery(Wards.class);
		Root<Wards> root = query.from(Wards.class);
		query.where(builder.equal(root.get("districtId"), districtId));

		return (List<Wards>) this.getSession().createQuery(query).getResultList();
	}

}

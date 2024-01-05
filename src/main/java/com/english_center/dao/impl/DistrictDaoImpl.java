package com.english_center.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.english_center.dao.AbstractDao;
import com.english_center.dao.DistrictDao;
import com.english_center.entity.Districts;

@Repository("DistrictDao")
@Transactional
public class DistrictDaoImpl extends AbstractDao<Integer, Districts> implements DistrictDao {

	@Override
	public List<Districts> findByCityId(int cityId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Districts> query = builder.createQuery(Districts.class);
		Root<Districts> root = query.from(Districts.class);
		query.where(builder.equal(root.get("cityId"), cityId));

		return (List<Districts>) this.getSession().createQuery(query).getResultList();
	}

}

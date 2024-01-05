package com.english_center.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.english_center.dao.AbstractDao;
import com.english_center.dao.AudioDao;
import com.english_center.entity.Audio;

@Repository("AudioDao")
@Transactional
public class AudioDaoImpl extends AbstractDao<Integer, Audio> implements AudioDao {

	@Override
	public Audio findOne(int id) throws Exception {
		return this.getSession().find(Audio.class, id);
	}

	@Override
	public void update(Audio audio) throws Exception {
		this.getSession().update(audio);

	}

	@Override
	public Audio create(Audio audio) throws Exception {
		this.getSession().save(audio);
		return audio;
	}

	@Override
	public void delete(Audio audio) {
		this.getSession().delete(audio);

	}

	@Override
	public Audio findByExamId(int examId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<Audio> query = builder.createQuery(Audio.class);
		Root<Audio> root = query.from(Audio.class);
		query.where(builder.equal(root.get("examId"), examId));

		return this.getSession().createQuery(query).uniqueResult();
	}

}

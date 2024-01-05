package com.english_center.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.english_center.dao.AbstractDao;
import com.english_center.dao.VideoWatchHistoryDao;
import com.english_center.entity.VideoWatchHistory;

@Repository("VideoWatchHistoryDao")
@Transactional
public class VideoWatchHistoryDaoImpl extends AbstractDao<Integer, VideoWatchHistory> implements VideoWatchHistoryDao {

	@Override
	public void create(VideoWatchHistory videoWatchHistory) throws Exception {
		this.getSession().save(videoWatchHistory);

	}

	@Override
	public VideoWatchHistory findOne(int id) throws Exception {
		return this.getSession().find(VideoWatchHistory.class, id);
	}

	@Override
	public void update(VideoWatchHistory videoWatchHistory) throws Exception {
		this.getSession().update(videoWatchHistory);

	}

	@Override
	public VideoWatchHistory findByLessonsAndUser(int lessonsId, int userId) throws Exception {
		CriteriaBuilder builder = this.getBuilder();
		CriteriaQuery<VideoWatchHistory> query = builder.createQuery(VideoWatchHistory.class);
		Root<VideoWatchHistory> root = query.from(VideoWatchHistory.class);
		Predicate condition1 = builder.equal(root.get("lessonsId"), lessonsId);
		Predicate condition2 = builder.equal(root.get("userId"), userId);
		Predicate combinedCondition = builder.and(condition1, condition2);

		query.where(combinedCondition);

		return this.getSession().createQuery(query).getResultList().stream().findFirst().orElse(null);
	}

}

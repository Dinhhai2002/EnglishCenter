package com.english_center.dao.impl;
//package com.english_center.dao;
//
//import java.util.List;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Root;
//import javax.transaction.Transactional;
//
//import org.springframework.stereotype.Repository;
//
//import com.english_center.entity.Language;
//
//@Repository("LanguageDao")
//@Transactional
//public class LanguageDaoImpl extends AbstractDao<Integer, Language> implements LanguageDao {
//
//	@Override
//	public void create(Language language) throws Exception {
//		this.getSession().save(language);
//	}
//
//	@Override
//	public Language findOne(int id) throws Exception {
//		return this.getSession().find(Language.class, id);
//	}
//
//	@Override
//	public void update(Language language) throws Exception {
//		this.getSession().update(language);
//	}
//
//	@Override
//	public Language findByName(String name) throws Exception {
//		CriteriaBuilder builder = this.getBuilder();
//		CriteriaQuery<Language> query = builder.createQuery(Language.class);
//		Root<Language> root = query.from(Language.class);
//		query.where(builder.equal(root.get("name"), name));
//
//		return this.getSession().createQuery(query).uniqueResult();
//	}
//
//	@Override
//	public List<Language> findAll() throws Exception {
//		CriteriaBuilder builder = this.getBuilder();
//		CriteriaQuery<Language> query = builder.createQuery(Language.class);
//		Root<Language> root = query.from(Language.class);
//
//		return this.getSession().createQuery(query).getResultList();
//	}
//
//}

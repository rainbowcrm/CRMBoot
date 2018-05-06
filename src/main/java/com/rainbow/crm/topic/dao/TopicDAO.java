package com.rainbow.crm.topic.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.filter.model.CRMFilter;
import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.topic.model.Topic;
import com.rainbow.crm.topic.model.TopicLine;

public class TopicDAO  extends HibernateDAO{

	@Override
	public Object getById(Object PK) {
		int topicId = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Topic.class, topicId);
		closeSession(session, false);
		return obj;
	}

	
	public List<TopicLine> getUpdatedReplies (int topicId, int readReply)
	{
		Session session = openSession(false);
		Query query = session.createQuery(" from TopicLine where topic.id = :topicId and lineNumber > :readReply and     deleted = false" ) ;
		query.setParameter("topicId", topicId);
		query.setParameter("readReply", readReply);
		List<TopicLine> lst = query.list();
		closeSession(session, false);
		return lst;
	}
	
	
	public List<Topic> getOpenTopics(int company ) {
		Session session = openSession(false);
		Query query = session.createQuery(" from Topic where company.id = :company    and closed = false and deleted  = false " ) ;
		query.setParameter("company", company);
		List<Topic> lst = query.list();
		closeSession(session, false);
		return lst;
	}

	public List<Object> getPortfoliosforsku(int divisionId, int itemId, int productId, int brandId, int categoryId) {
		Session session = openSession(false);
		Query query = session.createQuery(" from Topic parent  left join   TopicLine as line with parent.id = line.topicDoc.id " + 
		    "  where parent.division.id = :divisionId and  parent.voided = false and parent.expired= false  and(  " +
		     " ( line.portfolioType.code = 'SPFITEM' and line.portfolioKey = :itemId ) or " +
			"  ( line.portfolioType.code = 'SPFPROD' and line.portfolioKey = :productId ) or " +
			" ( line.portfolioType.code = 'SPFBRAND' and line.portfolioKey = :brandId ) or " +
			"  ( line.portfolioType.code = 'SPFCATG' and line.portfolioKey = :categoryId )  " +
				" )" );
		try {
		//query.setCacheable(true);
		query.setParameter("divisionId", divisionId);
		query.setParameter("itemId", String.valueOf(itemId));
		query.setParameter("productId", String.valueOf(productId));
		query.setParameter("brandId", String.valueOf(brandId));
		query.setParameter("categoryId", String.valueOf(categoryId));
		List<Object> lst = query.list();
		System.out.println(lst);
		return lst;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally {
		closeSession(session, false);
		}
		return null;
	}
	
	/*@Override
	public void create(CRMModelObject object) {
		Topic topic = (Topic) object ;
		boolean success = false;
		Session session = openSession(true);
		try {
			session.save(topic);
			session.flush();
			success = true; 
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
			throw new DatabaseException(ex,DatabaseException.DIRTY_READ_ERROR);
		}finally {
			closeSession(session,success);
		}
	}*/
	
	

	
	
	
}

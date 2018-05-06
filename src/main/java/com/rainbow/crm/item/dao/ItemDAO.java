package com.rainbow.crm.item.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.item.model.Item;
import com.techtrade.rads.framework.utils.Utils;

public class ItemDAO extends HibernateDAO {

	@Override
	public Object getById(Object PK) {
		int itemId = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Item.class, itemId);
		closeSession(session,false);
		return obj;
	}

	public Item findByCode(int company, String code) {
		Item item = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Item where code = :code and company.id =:company  " ) ;
		query.setParameter("code", code);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst))
			item = (Item) lst.get(0) ;
		closeSession(session, false);
		return item;
	}
	
	public Item findByName(int company, String name) {
		Item item = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Item where name = :name and company.id =:company  " ) ;
		query.setParameter("name", name);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst))
			item = (Item) lst.get(0) ;
		closeSession(session, false);
		return item;
	}
	public List<Item> getAllByProduct(int company, int productId) {
		Session session = openSession(false);
		Query query = session.createQuery(" from Item where company.id =:company  and product.id =:product  " ) ;
		query.setParameter("product", productId);
		query.setParameter("company", company);
		List lst = query.list();
		return lst;
	}

}

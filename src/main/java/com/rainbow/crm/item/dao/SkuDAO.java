package com.rainbow.crm.item.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.category.model.Category;
import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.item.model.Sku;
import com.techtrade.rads.framework.utils.Utils;

public class SkuDAO extends HibernateDAO {

	@Override
	public Object getById(Object PK) {
		int itemId = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Sku.class, itemId);
		closeSession(session,false);
		return obj;
	}

	public Sku findByCode(int company, String code) {
		Sku item = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Sku where code = :code and company.id =:company  " ) ;
		query.setParameter("code", code);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst))
			item = (Sku) lst.get(0) ;
		closeSession(session, false);
		return item;
	}
	
	public Sku findByBarCode(int company, String barcode) {
		Sku item = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Sku where barcode = :barcode and company.id =:company  " ) ;
		query.setParameter("barcode", barcode);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst))
			item = (Sku) lst.get(0) ;
		closeSession(session, false);
		return item;
	}
	
	public Sku findByName(int company, String name) {
		Sku item = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Sku where name = :name and company.id =:company  " ) ;
		query.setParameter("name", name);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst))
			item = (Sku) lst.get(0) ;
		closeSession(session, false);
		return item;
	}
	public List<Sku> getAllByProduct(int company, int productId) {
		Sku item = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Sku where company.id =:company  and product.id =:product  and deleted= false " ) ;
		query.setParameter("product", productId);
		query.setParameter("company", company);
		List lst = query.list();
		return lst;
	}
	
	public List<Sku> getAllByItem(int company, int itemId) {
		Sku item = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Sku where company.id =:company  and item.id =:item and deleted= false  " ) ;
		query.setParameter("item", itemId);
		query.setParameter("company", company);
		List lst = query.list();
		return lst;
	}

	
	
	
}

package com.rainbow.crm.inventory.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.DatabaseException;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.inventory.model.Inventory;
import com.techtrade.rads.framework.utils.Utils;

public class InventoryDAO extends HibernateDAO{

	@Override
	public Object getById(Object PK) {
		int inventory = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Inventory.class, inventory);
		closeSession(session,false);
		return obj;
	}
	
	public Inventory getByItemandDivision(int item , int division ) {
		Inventory inventory = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Inventory where sku.id = :sku  and division.id =:division  " ) ;
		query.setParameter("sku", item);
		query.setParameter("division", division);
		List lst = query.list();
		if (!Utils.isNullList(lst))
			inventory = (Inventory)lst.get(0);
		closeSession(session, false);
		return inventory;
	}
	
	public List<Inventory> getByItem(int item) {
		Session session = openSession(false);
		Query query = session.createQuery(" from Inventory where sku.id = :sku    " ) ;
		query.setParameter("sku", item);
		List lst = query.list();
		closeSession(session, false);
		return lst;
	}
	
	@Override
	public void batchCreate(List<CRMModelObject> objects) throws DatabaseException{
		if(!Utils.isNullList(objects)) {
			int pk = getMaxPlusOneId("id", "Inventory");
			for (CRMModelObject object : objects ) {
				Inventory inventory = (Inventory) object;
				inventory.setId(pk ++);
			}
		super.batchCreate(objects);
		}
	}

}

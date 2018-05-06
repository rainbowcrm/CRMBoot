package com.rainbow.crm.carrier.service;

import java.util.List;

import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.carrier.model.Carrier;

public interface ICarrierService extends IBusinessService{
	
	public Carrier getByEmail(int company, String email) ;
	public Carrier getByPhone(int company, String Phone) ;
	public Carrier getByCode(int company, String code) ;
	
	public List<Carrier> getAllCarriers (int company) ;
	

}

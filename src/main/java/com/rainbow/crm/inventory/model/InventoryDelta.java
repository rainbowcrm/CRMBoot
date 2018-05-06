package com.rainbow.crm.inventory.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.item.model.Sku;
import com.techtrade.rads.framework.utils.Utils;

public class InventoryDelta extends CRMModelObject{

	List<InventoryDeltaLine> lines ;
	
	CRMContext context;

	public List<InventoryDeltaLine> getLines() {
		return lines;
	}

	public void setLines(List<InventoryDeltaLine> lines) {
		this.lines = lines;
	}
	
	public void addLine(InventoryDeltaLine line) {
		if(lines == null)
			lines = new ArrayList<InventoryDeltaLine>();
		lines.add(line);
	}

	public CRMContext getContext() {
		return context;
	}

	public void setContext(CRMContext context) {
		this.context = context;
	}
	
	
	public boolean hasChanged()
	{
		if (Utils.isNullList(lines)) return false;
		Set<Division> divisions = new LinkedHashSet<Division> ();
		Map<Sku, Integer> qtyMap =  new HashMap<Sku, Integer> ();
		for (InventoryDeltaLine line : lines ) {
			divisions.add(line.getDivision()) ;
			if(qtyMap.containsKey(line.getSku())) {
				qtyMap.put(line.getSku(),qtyMap.get(line.getSku()) + line.getQty() );
			}else {
				qtyMap.put(line.getSku(), line.getQty());
			}
		}
		if (divisions.size() > 1) return true ;
		Iterator  it =  qtyMap.keySet().iterator() ;
		while(it.hasNext()) {
			Integer qty = qtyMap.get(it.next());
			if(qty.intValue() != 0 )
				 return true ;
		}
		return false;
	}
	
}

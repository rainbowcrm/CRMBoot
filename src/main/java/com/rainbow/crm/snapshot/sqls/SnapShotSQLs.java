package com.rainbow.crm.snapshot.sqls;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class SnapShotSQLs {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /*public List<FeedbackDetail> getFeedBacksForLocation(int location, int company)
    {
        List<FeedbackDetail> feedBacks = new ArrayList<>();
        String sql = " SELECT FEEDBACK,DOCTORS.NAME , STORES.NAME, STOCKISTS.NAME FROM APPOINTMENTS , DOCTORS, STORES,STOCKISTS  WHERE APPOINTMENTS.DOCTOR_ID =DOCTORS.ID  AND " +
                " LOCATION_ID = ?  AND COMPANY_ID =  ? AND" +
                " IS_DELETED = FALSE AND APPT_STATUS IN ('CMPLTD','CLSD' ) ) ";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, new Object[]{location, company});
        while (rs.next()) {
            FeedbackDetail detail   = new FeedbackDetail();
            detail.setFeedback(rs.getString(1));
            detail.setDate();
            detail.setGivenBy();
            feedBacks.add(rs.getString(1));
        }
        return feedBacks ;

    }*/


    /*public List<ItemSale> getAllItemSales(int location , int company, Date fromDate, Date toDate)
    {
        List<ItemSale> itemSales = new ArrayList<>();
        String sql = "SELECT ITEMS.ITEM_NAME,  SUM(OL.QTY * OL.RATE) FROM APPOINTMENTS APPTS, STOCKIST_VISIT_ORDER_LINES OL, SKUS SKUS, ITEMS ITEMS  WHERE APPTS.ID = OL.APPOINTMENT_ID" +
                " AND OL.SKU_ID = SKUS.ID AND SKUS.ITEM_ID = ITEMS.ID AND APPTS.COMPANY_ID = ? AND APPTS.LOCATION_ID  =? AND  APPTS.APPT_DATE >= ? AND APPTS.APPT_DATE<= ? AND" +
                " OL.IS_DELETED= FALSE  AND APPTS.IS_DELETED = FALSE AND APPTS.APPT_STATUS IN ('CMPLTD','CLSD' ) GROUP BY ITEMS.ITEM_NAME";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, new Object[]{ company, location, new Timestamp(fromDate.getTime()), new Timestamp(toDate.getTime())});
        while (rs.next()) {
            ItemSale itemSale = new ItemSale();
            itemSale.setItem(rs.getString(1));
            itemSale.setValue(rs.getDouble(2));
            itemSales.add(itemSale);
        }
        return itemSales;
    }

    public List<StoreSale> getAllStoreSales(int location , int company, Date fromDate, Date toDate)
    {
        List<StoreSale> storeSales = new ArrayList<>();
        String sql = "SELECT STORES.NAME,  SUM(OL.QTY * OL.RATE) FROM APPOINTMENTS APPTS, STORE_VISIT_ORDER_LINES OL, STORES  WHERE APPTS.ID = OL.APPOINTMENT_ID" +
                "  AND APPTS.COMPANY_ID = ? AND APPTS.LOCATION_ID  =? AND  APPTS.APPT_DATE >= ? AND APPTS.APPT_DATE<= ? AND APPTS.STORE_ID = STORES.ID AND " +
                " OL.IS_DELETED= FALSE  AND APPTS.IS_DELETED = FALSE AND APPTS.APPT_STATUS IN ('CMPLTD','CLSD' ) GROUP BY STORES.NAME";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, new Object[]{ company, location, new Timestamp(fromDate.getTime()), new Timestamp(toDate.getTime())});
        while (rs.next()) {
            StoreSale storeSale = new StoreSale();
            storeSale.setStore(rs.getString(1));
            storeSale.setValue(rs.getDouble(2));
            storeSales.add(storeSale);
        }
        return storeSales;
    }
*/

}

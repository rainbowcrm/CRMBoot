package com.rainbow.crm.reports.service;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.reports.model.SalesReport;

public interface IReportService {

	public byte[] getSalesReport(SalesReport report, CRMContext context) throws Exception;
}

package com.accurate.erp.service.excel;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accurate.erp.helper.ExcelHelper;
import com.accurate.erp.model.invoice.InvoiceDO;
import com.accurate.erp.service.invoice.InvoiceService;

@Service
public class ExcelService {

	@Autowired
	InvoiceService invoiceService;
	
	public ByteArrayInputStream load(Map<String,String> data) {
	    List<InvoiceDO> invoices = invoiceService.getInvoiceList(data);

	    ByteArrayInputStream in = ExcelHelper.invoicesToExcel(invoices);
	    return in;
	  }
	
	
}

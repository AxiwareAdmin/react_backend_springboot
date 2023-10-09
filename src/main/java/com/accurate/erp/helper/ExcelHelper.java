package com.accurate.erp.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.accurate.erp.model.invoice.InvoiceDO;

public class ExcelHelper {
		public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	  static String[] HEADERS = { "Sr. No", "Invoice No.", "Invoice Date", "Customer Name","Due Date" };
	  static String SHEET = "Invoices";
	  
	  public static ByteArrayInputStream invoicesToExcel(List<InvoiceDO> invoiceList) {
		  
		  try(Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			  
			  Sheet sheet=workbook.createSheet(SHEET);
			  
			  Row headerRow=sheet.createRow(0);
			  
			  for(int i=0;i<HEADERS.length;i++) {
				  Cell cell=headerRow.createCell(i);
				  cell.setCellValue(HEADERS[i]);
			  }
			  
			  
			  int rowIdx=1;
			  
			  for(InvoiceDO invoice:invoiceList) {
				  Row row=sheet.createRow(rowIdx);
				  
				  row.createCell(0).setCellValue(rowIdx++);
				  
				  row.createCell(1).setCellValue(invoice.getInvoiceNo());
				  
				  SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
				  
				  if(invoice.getInvoiceDate()!=null)
				  row.createCell(2).setCellValue(sdf.format(invoice.getInvoiceDate()));
				 
				  else
					  row.createCell(2).setCellValue("");
				  
				  row.createCell(3).setCellValue(invoice.getCustomerName());
				  
				  
				  if(invoice.getDueDate()!=null)
				  row.createCell(4).setCellValue(sdf.format(invoice.getDueDate()));
				  
				  else
					  row.createCell(4).setCellValue("");
				  
				
				  
			  }
			  
			  workbook.write(out);
			  
			  return new ByteArrayInputStream(out.toByteArray());
			  
		  }catch(Exception e) {
			  System.out.println("Exception in ExcelHelper class invoicesToExcel()::"+e);
		  }
		  
		  return null;
	  }

		  
	  
	  
}

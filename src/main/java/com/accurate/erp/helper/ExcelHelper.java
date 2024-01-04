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
import com.accurate.erp.model.purchase.PurchaseDO;

public class ExcelHelper {
		public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	  static String[] HEADERS = { "Sr. No", "Invoice No.", "Invoice Date", "Customer Name","Due Date" ,"Invoice Value","SGST","CGST","IGST"};
	  static String SHEET = "Invoices";
	  
	  static String PURCHASE_SHEET="purchases";
	  
	  static String[] PURCHASE_HEADERS = { "Sr. No", "Purchase No.", "Purchase Date", "Supplier Name","Due Date"};

	  
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
				  
				  row.createCell(5).setCellValue(invoice.getInvoiceValue().doubleValue());
				  
				  row.createCell(6).setCellValue(invoice.getSgstValue().doubleValue());
				  
				  row.createCell(7).setCellValue(invoice.getCgstValue().doubleValue());
				  
				  row.createCell(8).setCellValue(invoice.getIgstValue().doubleValue());
				  
				
				  
			  }
			  
			  workbook.write(out);
			  
			  return new ByteArrayInputStream(out.toByteArray());
			  
		  }catch(Exception e) {
			  System.out.println("Exception in ExcelHelper class invoicesToExcel()::"+e);
		  }
		  
		  return null;
	  }
	  
	  
  public static ByteArrayInputStream purchasesToExcel(List<PurchaseDO> purchaseList) {
		  
		  try(Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			  
			  Sheet sheet=workbook.createSheet(PURCHASE_SHEET);
			  
			  Row headerRow=sheet.createRow(0);
			  
			  for(int i=0;i<PURCHASE_HEADERS.length;i++) {
				  Cell cell=headerRow.createCell(i);
				  cell.setCellValue(PURCHASE_HEADERS[i]);
			  }
			  
			  
			  int rowIdx=1;
			  
			  for(PurchaseDO purchase:purchaseList) {
				  Row row=sheet.createRow(rowIdx);
				  
				  row.createCell(0).setCellValue(rowIdx++);
				  
				  row.createCell(1).setCellValue(purchase.getPurchaseNo());
				  
				  SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
				  
				  if(purchase.getPurchaseDate()!=null)
				  row.createCell(2).setCellValue(sdf.format(purchase.getPurchaseDate()));
				 
				  else
					  row.createCell(2).setCellValue("");
				  
				  row.createCell(3).setCellValue(purchase.getSupplierName());
				  
				  
				  if(purchase.getDueDate()!=null)
				  row.createCell(4).setCellValue(sdf.format(purchase.getDueDate()));
				  
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

package com.accurate.erp.service.invoice;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.accurate.erp.dao.invoice.InvoiceDao;
import com.accurate.erp.dao.invoice.QuotationDAO;
import com.accurate.erp.model.invoice.CashDO;
import com.accurate.erp.model.invoice.CashSaleProductDO;
import com.accurate.erp.model.invoice.ClientDO;
import com.accurate.erp.model.invoice.CustomerDO;
import com.accurate.erp.model.invoice.InvoiceDO;
import com.accurate.erp.model.invoice.InvoiceProductDO;
import com.accurate.erp.model.invoice.ProductDO;
import com.accurate.erp.model.invoice.ProformaInvoiceDO;
import com.accurate.erp.model.invoice.ProformaInvoiceProductDO;
import com.accurate.erp.model.invoice.QuotationDO;
import com.accurate.erp.model.invoice.QuotationProductDO;
import com.accurate.erp.model.invoice.UserDO;
import com.accurate.erp.model.modelmaster.DocumentSeqMasterDO;
import com.accurate.erp.model.purchase.PurchaseDO;
import com.accurate.erp.model.purchase.PurchaseProductDO;
import com.accurate.erp.model.purchase.SupplierDO;
import com.accurate.erp.utility.mail.SendMail;

@Service
@Transactional
public class QuotationService {
	
	private static final Logger LOGGER=LogManager.getLogger(QuotationService.class);

	@Autowired
	QuotationDAO invoiceDao;
	
	@Autowired
	SendMail sendmail;
	
	
	
	public List<QuotationDO> getInvoiceList(){
		LOGGER.info("QuotationService::getInvoiceList()::start");
		return invoiceDao.getInvoiceList();
	}
	
	
	public List<QuotationDO> getInvoiceList(Map<String, String> data){
		LOGGER.info("QuotationService::getInvoiceList()::start");
		return invoiceDao.getInvoiceList(data);
	}
	
	
	
		public String getInvNo(){
		LOGGER.info("QuotationService::getInvNo()::start");
		return invoiceDao.getInvNo();
	}
	
  public List<QuotationDO> getInvoiceListByMonth(String month){
		LOGGER.info("QuotationService::getInvoiceListByMonth()::start");
		return invoiceDao.getInvoiceListByMonth(month);
	}
  
 	
	public String saveInvoice(Map<String, Object> inputJson,String registerId,String userId,String userName) throws ParseException {
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd/mm/yyyy");
		
		QuotationDO invoiceDO=new QuotationDO();
		
		Object invoiceNo=inputJson.get("invoiceNo");
		
		
		Object invoiceId=inputJson.get("invoiceId");
		
		//TO make sure invoice updates on edit
		if(invoiceId!=null && invoiceId.toString().length()>0) {
			invoiceDO=invoiceDao.getInvoiceDetails(invoiceId.toString());
		}
		
		Object sgstValue=inputJson.get("sgstValue");
		
		Object cgstValue=inputJson.get("cgstValue");
		
		Object taxableValue=inputJson.get("taxableValue");
		
		Object invoiceValue=inputJson.get("invoiceValue");
		
		Object transportCharges=inputJson.get("transportCharges");
		
		Object additionalCharges=inputJson.get("additionalCharges");
		
		Object discount=inputJson.get("discount");
		
		Object otherDiscount=inputJson.get("otherDiscount");
		
		Object shippingAddress=inputJson.get("shippingAddress");
		
		Object billingAddress=inputJson.get("billingAddress");
		
		Object poNumber=inputJson.get("poNumber");
		
		Object customerName=inputJson.get("customerName");
		
		Object invoiceDate=inputJson.get("invoiceDate");
		
		Object poDate=inputJson.get("poDate");
		
		Object challanNumber=inputJson.get("challanNumber");
		
		Object challanDate=inputJson.get("challanDate");
		
		Object paymentTerms=inputJson.get("paymentTerms");
		
		Object dueDate=inputJson.get("dueDate");
		
		Object transportMode=inputJson.get("transportMode");
		
		Object vehicleNumber=inputJson.get("vehicleNumber");
		
		Object remarks=inputJson.get("remarks");
		
		Object state=inputJson.get("state");
		
		Object gstNo=inputJson.get("gstNo");
		
		Object shippingGstNo=inputJson.get("shippingGstNo");
		
		Object serviceCheck=inputJson.get("serviceCheck");
		
		Object shippingState=inputJson.get("shippingState");
		
		Object termsAndCondition=inputJson.get("termsAndCondition");
		
		Object financialYear=inputJson.get("financialYear");
		
		
		Object additionalChargesGst=inputJson.get("otherChargesGstRate");
		
		Object transportChargesGst=inputJson.get("transportGstRate");
		
		List<Map<String,Object>> invoiceProd=(List<Map<String,Object>>)inputJson.get("invoiceProducts");
		
		java.util.Date d=sdf.parse(invoiceDate.toString());
		
		
		
		if(invoiceNo!=null)
		invoiceDO.setInvoiceNo(invoiceNo.toString());
		
		if(invoiceId != null && !invoiceId.equals(""))
			invoiceDO.setInvoiceId(Integer.parseInt(invoiceId.toString()));
		
		
		List<QuotationProductDO> invoiceProducts=new ArrayList<>();
		if(invoiceProd!=null && invoiceProd.size()>0) {
			for(Map<String,Object> tempProd:invoiceProd) {
				QuotationProductDO invoiceProduct=new QuotationProductDO();
				
				invoiceProduct.setProductName(tempProd.get("productName").toString());
				
				invoiceProduct.setProductDescription(tempProd.get("description").toString());
				
				invoiceProduct.setHsnSac(tempProd.get("hsnSac").toString());
				
				invoiceProduct.setTax(tempProd.get("tax").toString());
				
				invoiceProduct.setQuantity(tempProd.get("quantity").toString());
				
				invoiceProduct.setUnit(tempProd.get("unit").toString());
				
				invoiceProduct.setRate(tempProd.get("price").toString());
				
				invoiceProduct.setAmount(tempProd.get("amount").toString());
				
				invoiceProduct.setDiscount(tempProd.get("discount").toString());
				
				
				invoiceProduct.setInvoiceNumber(invoiceNo.toString());
				
				invoiceProduct.setInvoiceDate(sdf.parse(invoiceDate.toString()));
				
				invoiceProduct.setInvoiceDO(invoiceDO);
				
				invoiceProduct.setRegisterId("11111");
				
				invoiceProduct.setUserId("22222");
				
				
				invoiceProduct.setCreatedDate(sdf.parse(sdf.format(new Date(0))));
				
				invoiceProduct.setCreatedBy(userName);
				
				invoiceProduct.setYear(financialYear.toString());

				invoiceProduct.setMonth(new SimpleDateFormat("MMM").format(d));
				
				invoiceProduct.setYear(financialYear.toString());
				
				invoiceProducts.add(invoiceProduct);
			}
			invoiceDO.setInvoiceProductDO(invoiceProducts);
		}
		
		
		if(sgstValue!=null) {
			invoiceDO.setSgstValue(new BigDecimal(sgstValue.toString()));
		}
		
		if(financialYear!=null) {
			invoiceDO.setFinancialYear(financialYear.toString());
		}
		
		if(cgstValue!=null) {
			invoiceDO.setCgstValue(new BigDecimal(cgstValue.toString()));
		}
		
		if(taxableValue!=null) {
			invoiceDO.setTaxableValue(new BigDecimal(taxableValue.toString()));
		}
		
		if(invoiceValue!=null) {
			invoiceDO.setInvoiceValue(new BigDecimal(invoiceValue.toString()));
		}
				
		/*
		 * if(transportCharges!=null) {
		 * invoiceDO.setTransportCharges(transportCharges.toString()); }
		 */
		
		if(additionalCharges!=null) {
			invoiceDO.setAdditionalCharges(additionalCharges.toString());
		}
		
		if(discount!=null) {
			invoiceDO.setDiscount(new BigDecimal(discount.toString()));
		}
		
		if(otherDiscount!=null) {
			invoiceDO.setOtherDiscount(new BigDecimal(otherDiscount.toString()));
		}
		
		if(shippingAddress!=null) {
			invoiceDO.setShippingAddress(shippingAddress.toString());
		}
		
		if(billingAddress!=null) {
			invoiceDO.setBillingAddress(billingAddress.toString());
		}
		
		if(poNumber!=null) {
			invoiceDO.setPoNumber(poNumber.toString());
		}
		
		if(customerName!=null) {
			invoiceDO.setCustomerName(customerName.toString());
		}
		
		if(invoiceDate!=null) {
			
			invoiceDO.setInvoiceDate(sdf.parse(invoiceDate.toString()));
		}
		
		if(poDate!=null) {
			invoiceDO.setPoDate(sdf.parse(poDate.toString()));
		}
		
//		if(challanNumber!=null) {
//			invoiceDO.setChallanNo(challanNumber.toString());
//		}
//		
//		if(challanDate!=null) {
//			invoiceDO.setChallanDate(sdf.parse(challanDate.toString()));
//		}
//		
		if(dueDate!=null) {
			invoiceDO.setDueDate(sdf.parse(dueDate.toString()));
		}
		
		if(paymentTerms!=null) {
			invoiceDO.setPaymentTerms(paymentTerms.toString());
		}
		
//		if(transportMode!=null) {
//			invoiceDO.setTransportMode(transportMode.toString());
//		}
//		
//		if(vehicleNumber!=null) {
//			invoiceDO.setVehicleNo(vehicleNumber.toString());
//		}
		
		if(remarks!=null) {
			invoiceDO.setRemarks(remarks.toString());
		}
		
		
		if(state!=null) {
			invoiceDO.setState(state.toString());
		}
		
		
		if(gstNo!=null) {
			invoiceDO.setGstNo(gstNo.toString());
		}
		
		if(shippingGstNo!=null) {
			invoiceDO.setShippingGstNo(shippingGstNo.toString());
		}
		
		if(serviceCheck!=null) {
			invoiceDO.setServiceCheck(serviceCheck.toString());
		}
		
		if(shippingState!=null) {
			invoiceDO.setShippingState(shippingState.toString());
		}
		
		if(termsAndCondition!=null) {
			invoiceDO.setAdditionalTerms(termsAndCondition.toString());
		}
		
		
		
		if(transportChargesGst!=null) {
			invoiceDO.setTransportGst(Integer.parseInt(transportChargesGst.toString()));
		}
		
		invoiceDO.setRegisterId(Integer.parseInt(registerId));
		
		invoiceDO.setUserId(Integer.parseInt(userId));
		
		invoiceDO.setCreatedDate(sdf.parse(sdf.format(new Date(0))));
		
		invoiceDO.setCreatedBy(userName);
		
		invoiceDO.setMonth(new SimpleDateFormat("MMM").format(d));
		
		return invoiceDao.saveInvoice(invoiceDO);
		
	}
	

	
	
	

	

	
	public QuotationDO getInvoiceDetails(String invId){
		LOGGER.info("QuotationService::getInvoiceDetails()::start");
		return invoiceDao.getInvoiceDetails(invId);
	}
	
	
	
	public boolean DeleteInvoice(String invId){
		LOGGER.info("QuotationService::DeleteInvoice()::start");
		return invoiceDao.DeleteInvoice(invId);
	}
	
	public List<QuotationDO> getInvoiceByFinancialYear(String financialYear) {
		return invoiceDao.getInvoiceByFinancialYear(financialYear);
	}
	
	
	
	public boolean cloneInvoice(String invNo){
		LOGGER.info("QuotationService::cloneInvoice()::start");
		return invoiceDao.cloneInvoice(invNo);
	}
	
	public boolean sendMail(String invNo , String custName,MultipartFile file){
		LOGGER.info("QuotationService::sendMail()::start");
		boolean flag = false;
		try {
		
				String toMailId = invoiceDao.getCustomerEmail(custName);
//				toMailId="sachinbrajgude@gmail.com";
				toMailId="nadimk784@gmail.com";
				String subject = "Testing mail for Quotation No "+invNo;
				String body ="<html><body><p>Dear "+custName+"</p><br/>Please check your Quotation is in paid state"
						+ ".please check Quotation is correct or not <br/>Thanks & regards,<br/>"
						+ "<br/>axiware.techonology@gmail.com</body></html>";
				
				flag = sendmail.SendMails(toMailId,subject,body,file,invNo);
		}catch(Exception e) {
			LOGGER.error("Exception occured in invoiceservice :: sendMail()");
			return flag;
		}
		return flag;
	}
	
	
    
	public boolean cancelInvoiceById(Integer invoiceId) {
		return invoiceDao.cancelInvoiceById(invoiceId);
	}
	
	

}
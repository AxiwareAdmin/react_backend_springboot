package com.accurate.erp.service.po;

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

import com.accurate.erp.dao.po.CustomerPurchaseOrderDAO;
import com.accurate.erp.dao.po.SupplierPurchaseOrderDAO;
import com.accurate.erp.model.invoice.SupplierQuotationDO;
import com.accurate.erp.model.invoice.SupplierQuotationProductsDO;
import com.accurate.erp.model.po.CustomerPurchaseOrderDO;
import com.accurate.erp.model.po.CustomerPurchaseOrderProductDO;
import com.accurate.erp.model.po.SupplierPurchaseOrderDO;
import com.accurate.erp.model.po.SupplierPurchaseOrderProductDO;
import com.accurate.erp.utility.mail.SendMail;


@Service
@Transactional
public class SupplierPurchaseOrderService {


	
	private static final Logger LOGGER=LogManager.getLogger(CustomerPurchaseOrderService.class);

	@Autowired
	SupplierPurchaseOrderDAO invoiceDao;
	
	@Autowired
	SendMail sendmail;
	
	
	
	public List<SupplierPurchaseOrderDO> getInvoiceList(String financialYear){
		LOGGER.info("SupplierPurchaseOrderService::getInvoiceList()::start");
		return invoiceDao.getInvoiceList(financialYear);
	}
	
	
	public List<SupplierPurchaseOrderDO> getInvoiceList(Map<String, String> data){
		LOGGER.info("SupplierPurchaseOrderService::getInvoiceList()::start");
		return invoiceDao.getInvoiceList(data);
	}
	
	
	
		public String getInvNo(){
		LOGGER.info("SupplierPurchaseOrderService::getInvNo()::start");
		return invoiceDao.getInvNo();
	}
	
  public List<SupplierPurchaseOrderDO> getInvoiceListByMonth(String month,String financialYear){
		LOGGER.info("SupplierPurchaseOrderService::getInvoiceListByMonth()::start");
		return invoiceDao.getInvoiceListByMonth(month,financialYear);
	}
  
 	
	public String saveInvoice(Map<String, Object> inputJson,String registerId,String userId,String userName) throws ParseException {
		

		
		SimpleDateFormat sdf=new SimpleDateFormat("dd/mm/yyyy");
		
		SupplierPurchaseOrderDO invoiceDO=new SupplierPurchaseOrderDO();
		
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
		
		java.util.Date d=null;
		if(invoiceDate!=null && invoiceDate.toString().length()>0) {
			
			d=sdf.parse(invoiceDate.toString());
		}
		else {
			d=sdf.parse(new java.util.Date().toString());
		}
		
		
		if(invoiceNo!=null)
		invoiceDO.setInvoiceNo(invoiceNo.toString());
		
		if(invoiceId != null && !invoiceId.equals(""))
			invoiceDO.setInvoiceId(Integer.parseInt(invoiceId.toString()));
		
		
		List<SupplierPurchaseOrderProductDO> invoiceProducts=new ArrayList<>();
		if(invoiceProd!=null && invoiceProd.size()>0) {
			for(Map<String,Object> tempProd:invoiceProd) {
				SupplierPurchaseOrderProductDO invoiceProduct=new SupplierPurchaseOrderProductDO();
				
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
			
			invoiceDO.setInvoiceDate(d);
		}
		
		if(poDate!=null && poDate.toString().length()>0) {
			invoiceDO.setPoDate(sdf.parse(poDate.toString()));
		}
		
//		if(challanNumber!=null) {
//			invoiceDO.setChallanNo(challanNumber.toString());
//		}
//		
//		if(challanDate!=null) {
//			invoiceDO.setChallanDate(sdf.parse(challanDate.toString()));
//		}
		
		if(dueDate!=null && dueDate.toString().length()>0) {
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
		
		if(transportCharges!=null) {
			invoiceDO.setTransportCharges(transportCharges.toString());
		}
		
		
		  if(additionalChargesGst!=null) {
		  invoiceDO.setAdditionalChargesGst(new BigDecimal(additionalChargesGst.
		  toString())); 
		  }
		 
		
		if(transportChargesGst!=null) {
			invoiceDO.setTransportGst(new BigDecimal(transportChargesGst.toString()));
		}
		invoiceDO.setInvoiceStatus("Unpaid");
		
		invoiceDO.setRegisterId(Integer.parseInt(registerId));
		
		invoiceDO.setUserId(Integer.parseInt(userId));
		
		invoiceDO.setCreatedDate(sdf.parse(sdf.format(new Date(0))));
		
		invoiceDO.setCreatedBy(userName);
		
		invoiceDO.setMonth(new SimpleDateFormat("MMM").format(d));
		
		return invoiceDao.saveInvoice(invoiceDO);
		
	
	}
	

	
	
	

	

	
	public SupplierPurchaseOrderDO getInvoiceDetails(String invId){
		LOGGER.info("SupplierPurchaseOrderService::getInvoiceDetails()::start");
		return invoiceDao.getInvoiceDetails(invId);
	}
	
	
	
	public boolean DeleteInvoice(String invId){
		LOGGER.info("SupplierPurchaseOrderService::DeleteInvoice()::start");
		return invoiceDao.DeleteInvoice(invId);
	}
	
	public List<SupplierPurchaseOrderDO> getInvoiceByFinancialYear(String financialYear) {
		return invoiceDao.getInvoiceByFinancialYear(financialYear);
	}
	
	
	
	public boolean cloneInvoice(String invNo){
		LOGGER.info("SupplierPurchaseOrderService::cloneInvoice()::start");
		return invoiceDao.cloneInvoice(invNo);
	}
	
	public boolean sendMail(String invNo , String custName,MultipartFile file){
		LOGGER.info("SupplierPurchaseOrderService::sendMail()::start");
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
			LOGGER.error("Exception occured in SupplierPurchaseOrderService :: sendMail()");
			return flag;
		}
		return flag;
	}
	
	
    
	public boolean cancelInvoiceById(Integer invoiceId) {
		return invoiceDao.cancelInvoiceById(invoiceId);
	}
	
	



}

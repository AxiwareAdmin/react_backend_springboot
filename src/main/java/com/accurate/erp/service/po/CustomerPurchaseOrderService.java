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

import com.accurate.erp.dao.invoice.QuotationDAO;
import com.accurate.erp.dao.po.CustomerPurchaseOrderDAO;
import com.accurate.erp.model.invoice.QuotationDO;
import com.accurate.erp.model.invoice.QuotationProductDO;
import com.accurate.erp.model.po.CustomerPurchaseOrderDO;
import com.accurate.erp.model.po.CustomerPurchaseOrderProductDO;
import com.accurate.erp.service.invoice.QuotationService;
import com.accurate.erp.utility.mail.SendMail;


@Service
@Transactional
public class CustomerPurchaseOrderService {

	
	private static final Logger LOGGER=LogManager.getLogger(CustomerPurchaseOrderService.class);

	@Autowired
	CustomerPurchaseOrderDAO invoiceDao;
	
	@Autowired
	SendMail sendmail;
	
	
	
	public List<CustomerPurchaseOrderDO> getInvoiceList(String financialYear){
		LOGGER.info("CustomerPurchaseOrderService::getInvoiceList()::start");
		return invoiceDao.getInvoiceList(financialYear);
	}
	
	
	public List<CustomerPurchaseOrderDO> getInvoiceList(Map<String, String> data){
		LOGGER.info("CustomerPurchaseOrderService::getInvoiceList()::start");
		return invoiceDao.getInvoiceList(data);
	}
	
	
	
		public String getInvNo(){
		LOGGER.info("CustomerPurchaseOrderService::getInvNo()::start");
		return invoiceDao.getInvNo();
	}
	
  public List<CustomerPurchaseOrderDO> getInvoiceListByMonth(String month,String financialYear){
		LOGGER.info("CustomerPurchaseOrderService::getInvoiceListByMonth()::start");
		return invoiceDao.getInvoiceListByMonth(month,financialYear);
	}
  
 	
	public String saveInvoice(Map<String, Object> inputJson,String registerId,String userId,String userName) throws ParseException {
		

		
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		
		CustomerPurchaseOrderDO invoiceDO=new CustomerPurchaseOrderDO();
		
		
		
		Object invoiceNo=inputJson.get("invoiceNo");
		
		
		Object invoiceId=inputJson.get("invoiceId");
		
		//TO make sure invoice updates on edit
		if(invoiceId!=null && invoiceId.toString().length()>0) {
			invoiceDO=invoiceDao.getInvoiceDetails(invoiceId.toString());
		}
		
		Object sgstValue=inputJson.get("sgstValue");
		
		Object igstValue=inputJson.get("igstValue");
		
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
		
		
		List<CustomerPurchaseOrderProductDO> invoiceProducts=new ArrayList<>();
		if(invoiceProd!=null && invoiceProd.size()>0) {
			for(Map<String,Object> tempProd:invoiceProd) {
				CustomerPurchaseOrderProductDO invoiceProduct=new CustomerPurchaseOrderProductDO();
				
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
				
				invoiceProduct.setRegisterId(registerId);
				
				invoiceProduct.setUserId(userId);
				
				
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
			String s=sgstValue.toString().length()==0?"0":sgstValue.toString();
			invoiceDO.setSgstValue(new BigDecimal(s));
		}
		
		if(financialYear!=null) {
			invoiceDO.setFinancialYear(financialYear.toString());
		}
		
		if(cgstValue!=null) {
			
			String s=cgstValue.toString().length()==0?"0":cgstValue.toString();
			invoiceDO.setCgstValue(new BigDecimal(s));
		}
		
if(igstValue!=null) {
			
			String s=igstValue.toString().length()==0?"0":igstValue.toString();
			invoiceDO.setIgstValue(new BigDecimal(s));
		}
		
		if(taxableValue!=null) {
			invoiceDO.setTaxableValue(new BigDecimal(taxableValue.toString()));
		}
		
		if(invoiceValue!=null) {
			invoiceDO.setInvoiceValue(new BigDecimal(invoiceValue.toString()));
		}
				
		if(transportCharges!=null) {
			invoiceDO.setTransportCharges(transportCharges.toString());
		}
		
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
		
		if(challanNumber!=null) {
			invoiceDO.setChallanNo(challanNumber.toString());
		}
		
		if(challanDate!=null && challanDate.toString().length()>0) {
			invoiceDO.setChallanDate(sdf.parse(challanDate.toString()));
		}
		
		if(dueDate!=null && dueDate.toString().length()>0) {
			invoiceDO.setDueDate(sdf.parse(dueDate.toString()));
		}
		
		if(paymentTerms!=null) {
			invoiceDO.setPaymentTerms(paymentTerms.toString());
		}
		
		if(transportMode!=null) {
			invoiceDO.setTransportMode(transportMode.toString());
		}
		
		if(vehicleNumber!=null) {
			invoiceDO.setVehicleNo(vehicleNumber.toString());
		}
		
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
		
		if(additionalChargesGst!=null) {
			invoiceDO.setAdditionalChargesGst(new BigDecimal(additionalChargesGst.toString()));
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
	

	
	
	

	

	
	public CustomerPurchaseOrderDO getInvoiceDetails(String invId){
		LOGGER.info("CustomerPurchaseOrderService::getInvoiceDetails()::start");
		return invoiceDao.getInvoiceDetails(invId);
	}
	
	
	
	public boolean DeleteInvoice(String invId){
		LOGGER.info("CustomerPurchaseOrderService::DeleteInvoice()::start");
		return invoiceDao.DeleteInvoice(invId);
	}
	
	public List<CustomerPurchaseOrderDO> getInvoiceByFinancialYear(String financialYear) {
		return invoiceDao.getInvoiceByFinancialYear(financialYear);
	}
	
	
	
	public boolean cloneInvoice(String invNo){
		LOGGER.info("CustomerPurchaseOrderService::cloneInvoice()::start");
		return invoiceDao.cloneInvoice(invNo);
	}
	
	public boolean sendMail(String invNo , String custName,MultipartFile file){
		LOGGER.info("CustomerPurchaseOrderService::sendMail()::start");
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
			LOGGER.error("Exception occured in CustomerPurchaseOrderService :: sendMail()");
			return flag;
		}
		return flag;
	}
	
	
    
	public boolean cancelInvoiceById(Integer invoiceId) {
		return invoiceDao.cancelInvoiceById(invoiceId);
	}
	
	
	public List<Map<String,String>> getPONumberByCustomerName(String customerName){
		return invoiceDao.getPONumberByCustomerName(customerName);
	}
	
	public List<Map<String,String>> getPONumberByCustomerNameForCopy(String customerName){
		return invoiceDao.getPONumberByCustomerNameForCopy(customerName);
	}

}

package com.accurate.erp.service.quotation;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accurate.erp.dao.quotation.QuotationDao;
import com.accurate.erp.model.invoice.CustomerDO;
import com.accurate.erp.model.invoice.ProductDO;
import com.accurate.erp.model.modelmaster.DocumentSeqMasterDO;
import com.accurate.erp.model.quotation.QuotationDO;
import com.accurate.erp.model.quotation.QuotationProductDO;
import com.accurate.erp.utility.mail.SendMail;

@Service
public class QuotationService {
	
	private static final Logger LOGGER=Logger.getLogger(QuotationService.class);
	
	@Autowired
	QuotationDao quotationDao;
	
	@Autowired
	SendMail sendmail;
	
//	public List<CustomerDO> getCustometList() {
//		LOGGER.info("QuotationService::getCustometList()::start");
//		return quotationDao.getCustometList();
//	}
	
	public List<QuotationDO> getInvoiceList(){
		LOGGER.info("QuotationService::getInvoiceList()::start");
		return quotationDao.getInvoiceList();
	}
	
	public List<QuotationDO> getInvoiceList(Map<String, String> data){
		LOGGER.info("QuotationService::getInvoiceList()::start");
		return quotationDao.getInvoiceList(data);
	}
	
		public String getInvNo(){
		LOGGER.info("QuotationService::getInvNo()::start");
		return quotationDao.getInvNo();
	}
	
  public List<QuotationDO> getInvoiceListByMonth(String month){
		LOGGER.info("QuotationService::getInvoiceListByMonth()::start");
		return quotationDao.getInvoiceListByMonth(month);
	}
	public List<ProductDO> getInvoiceProductList(){
		LOGGER.info("QuotationService::getInvoiceProductList()::start");
		return quotationDao.getInvoiceProductList();
		
	}
	
//	public CustomerDO getCustomerById(Integer custId){
//		LOGGER.info("QuotationService::getInvoiceProductList()::start");
//		return quotationDao.getCustomerById(custId);
//	}
	
//	public ProductDO getProductById(Integer prodId){
//		LOGGER.info("QuotationService::getProductById()::start");
//		return quotationDao.getProductById(prodId);
//	}
	
	public String saveInvoice(Map<String, Object> inputJson) throws ParseException {
		
		QuotationDO QuotationDO=new QuotationDO();
		
		try {
		SimpleDateFormat sdf=new SimpleDateFormat("dd/mm/yyyy");		
		
		Object invoiceNo=inputJson.get("invoiceNo");
		
		
		Object invoiceId=inputJson.get("invoiceId");
		
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
		
		List<Map<String,Object>> invoiceProd=(List<Map<String,Object>>)inputJson.get("invoiceProducts");
		
		List<QuotationProductDO> invoiceProducts=new ArrayList<>();
		if(invoiceProd!=null && invoiceProd.size()>0) {
			for(Map<String,Object> tempProd:invoiceProd) {
				QuotationProductDO invoiceProduct=new QuotationProductDO();
				
				invoiceProduct.setQuotationProductId(null);
				
				invoiceProduct.setCreatedBy("sachin");
				invoiceProduct.setCreatedDate(sdf.parse(invoiceDate.toString()));
				invoiceProduct.setYear("23");
				
				
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
				
				invoiceProduct.setQuotation(QuotationDO);
				
				invoiceProduct.setRegisterId("11111");
				
				invoiceProduct.setUserId("22222");
				
				invoiceProducts.add(invoiceProduct);
			}
			QuotationDO.setQuotationProductDO(invoiceProducts);
		}
		
		
		
		
		if(invoiceNo!=null)
			QuotationDO.setQuotationNo(invoiceNo.toString());
		//QuotationDO.setQuotationNo(getInvNo());
		
		if(invoiceId != null && !invoiceId.equals(""))
			QuotationDO.setQuotationId(null);
		
		if(sgstValue!=null) {
			QuotationDO.setSgstValue(new BigDecimal(sgstValue.toString()));
		}
		
		if(cgstValue!=null) {
			QuotationDO.setCgstValue(new BigDecimal(cgstValue.toString()));
		}
		
		if(taxableValue!=null) {
			QuotationDO.setTaxableValue(new BigDecimal(taxableValue.toString()));
		}
		
		if(invoiceValue!=null) {
			QuotationDO.setQuotationValue(new BigDecimal(invoiceValue.toString()));
		}
				
		if(transportCharges!=null) {
			QuotationDO.setTransportCharges(transportCharges.toString());
		}
		
		if(additionalCharges!=null) {
			QuotationDO.setAdditionalCharges(additionalCharges.toString());
		}
		
		if(discount!=null) {
			QuotationDO.setDiscount(new BigDecimal(discount.toString()));
		}
		
		if(otherDiscount!=null) {
			QuotationDO.setOtherDiscount(new BigDecimal(otherDiscount.toString()));
		}
		
		if(shippingAddress!=null) {
			QuotationDO.setShippingAddress(shippingAddress.toString());
		}
		
		if(billingAddress!=null) {
			QuotationDO.setBillingAddress(billingAddress.toString());
		}
		
		if(poNumber!=null) {
			QuotationDO.setPoNumber(poNumber.toString());
		}
		
		if(customerName!=null) {
			QuotationDO.setCustomerName(customerName.toString());
		}
		
		if(invoiceDate!=null) {
			
			QuotationDO.setQuotationDate(sdf.parse(invoiceDate.toString()));
		}
		
		if(poDate!=null) {
			QuotationDO.setPoDate(sdf.parse(poDate.toString()));
		}
		
		if(challanNumber!=null) {
			QuotationDO.setChallanNo(challanNumber.toString());
		}
		
		if(challanDate!=null) {
			QuotationDO.setChallanDate(sdf.parse(challanDate.toString()));
		}
		
		if(dueDate!=null) {
			QuotationDO.setDueDate(sdf.parse(dueDate.toString()));
		}
		
		if(paymentTerms!=null) {
			QuotationDO.setPaymentTerms(paymentTerms.toString());
		}
		
		if(transportMode!=null) {
			QuotationDO.setTransportMode(transportMode.toString());
		}
		
		if(vehicleNumber!=null) {
			QuotationDO.setVehicleNo(vehicleNumber.toString());
		}
		
		if(remarks!=null) {
			QuotationDO.setRemarks(remarks.toString());
		}
		
		
		if(state!=null) {
			QuotationDO.setState(state.toString());
		}
		
		
		if(gstNo!=null) {
			QuotationDO.setGstNo(gstNo.toString());
		}
		
		if(shippingGstNo!=null) {
			QuotationDO.setShippingGstNo(shippingGstNo.toString());
		}
		
		if(serviceCheck!=null) {
			QuotationDO.setServiceCheck(serviceCheck.toString());
		}
		
		if(shippingState!=null) {
			QuotationDO.setShippingState(shippingState.toString());
		}
		
		if(termsAndCondition!=null) {
			QuotationDO.setAdditionalTerms(termsAndCondition.toString());
		}
		}catch(Exception e) {
			LOGGER.error("Exception occuer in QuotationService while save"+e);
			return "failure";
		}
		return quotationDao.saveInvoice(QuotationDO);
		
	}
	
	
	public QuotationDO getInvoiceDetails(String invId){
		LOGGER.info("QuotationService::getInvoiceDetails()::start");
		return quotationDao.getInvoiceDetails(invId);
	}
	
	public boolean DeleteInvoice(String invNo){
		LOGGER.info("QuotationService::DeleteInvoice()::start");
		return quotationDao.DeleteInvoice(invNo);
	}
	
	public boolean cloneInvoice(String invNo){
		LOGGER.info("QuotationService::cloneInvoice()::start");
		return quotationDao.cloneInvoice(invNo);
	}
	
	public boolean sendMail(String invNo , String custName){
		LOGGER.info("QuotationService::sendMail()::start");
		boolean flag = false;
		try {
		        QuotationDO quoDO = quotationDao.getInvoiceDetails(invNo);
				String toMailId = quotationDao.getCustomerEmail(custName);
				String subject = "Testing mail for Quotation No "+quoDO.getQuotationNo();
				String body ="<html><body><p>Dear "+custName+"</p><br/>Please check your quotation is in paid state"
						+ ".please check quotation is correct or not <br/>Thanks & regards,<br/>"
						+ "<br/>axiware.techonology@gmail.com</body></html>";
				
				flag = sendmail.SendMails(toMailId,subject,body);
		}catch(Exception e) {
			LOGGER.error("Exception occured in QuotationService :: sendMail()");
			return flag;
		}
		return flag;
	}
	
	
    public String saveDocMaster(Map<String, Object> inputJson) throws ParseException {
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd/mm/yyyy");
		
		DocumentSeqMasterDO documentmasterdo = new DocumentSeqMasterDO();
		
		
		Object documentId=inputJson.get("DocumentId");
		
		Object documentName=inputJson.get("DocumentName");
		
		Object prefix1=inputJson.get("Prefix1");
		
		Object prefix2=inputJson.get("Prefix2");
		
		Object series=inputJson.get("Series");
		
		Object modeVal=inputJson.get("ModeVal");
		
		if(documentId != null && !documentId.equals(""))
			documentmasterdo.setDocumentSeqId(Integer.parseInt(documentId.toString()));
		if(documentName != null && !documentName.equals(""))
			documentmasterdo.setDocumentName(documentName.toString());
		if(prefix1 != null && !prefix1.equals(""))
			documentmasterdo.setPrefix1(prefix1.toString());
		if(prefix2 != null && !prefix2.equals(""))
			documentmasterdo.setPrefix2(prefix2.toString());
		if(series != null && !series.equals(""))
			documentmasterdo.setSeries(series.toString());
		if(modeVal != null && !modeVal.equals(""))
			documentmasterdo.setMode(modeVal.toString());
		
		
		return quotationDao.saveDocMaster(documentmasterdo);
		
    }
    
    
    public List<DocumentSeqMasterDO> getDocMaster(){
		LOGGER.info("QuotationService::getDocMaster()::start");
		return quotationDao.getDocMaster();
	}
    
    public boolean deleteDocMaster(String docId){
		LOGGER.info("QuotationService::deleteDocMaster()::start");
		return quotationDao.deleteDocMaster(docId);
	}
		
	public List<String> getStandardType(String module,String subModule){
		return quotationDao.getStandardType(module, subModule);
	}

}

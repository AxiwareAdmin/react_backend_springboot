package com.accurate.erp.service.invoice;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.accurate.erp.dao.invoice.InvoiceDao;
import com.accurate.erp.model.invoice.CustomerDO;
import com.accurate.erp.model.invoice.InvoiceDO;
import com.accurate.erp.model.invoice.InvoiceProductDO;
import com.accurate.erp.model.invoice.ProductDO;
import com.accurate.erp.model.modelmaster.DocumentSeqMasterDO;
import com.accurate.erp.utility.mail.SendMail;

@Service
public class InvoiceService {
	private static final Logger LOGGER=Logger.getLogger(InvoiceService.class);

	@Autowired
	InvoiceDao invoiceDao;
	
	@Autowired
	SendMail sendmail;
	
	public List<CustomerDO> getCustometList() {
		LOGGER.info("InvoiceService::getCustometList()::start");
		return invoiceDao.getCustometList();
	}
	
	public List<InvoiceDO> getInvoiceList(){
		LOGGER.info("InvoiceService::getInvoiceList()::start");
		return invoiceDao.getInvoiceList();
	}
	
	public List<InvoiceDO> getInvoiceList(Map<String, String> data){
		LOGGER.info("InvoiceService::getInvoiceList()::start");
		return invoiceDao.getInvoiceList(data);
	}
	
		public String getInvNo(){
		LOGGER.info("InvoiceService::getInvNo()::start");
		return invoiceDao.getInvNo();
	}
	
  public List<InvoiceDO> getInvoiceListByMonth(String month){
		LOGGER.info("InvoiceService::getInvoiceListByMonth()::start");
		return invoiceDao.getInvoiceListByMonth(month);
	}
	public List<ProductDO> getInvoiceProductList(){
		LOGGER.info("InvoiceService::getInvoiceProductList()::start");
		return invoiceDao.getInvoiceProductList();
		
	}
	
	public CustomerDO getCustomerById(Integer custId){
		LOGGER.info("InvoiceService::getInvoiceProductList()::start");
		return invoiceDao.getCustomerById(custId);
	}
	
	public ProductDO getProductById(Integer prodId){
		LOGGER.info("InvoiceService::getProductById()::start");
		return invoiceDao.getProductById(prodId);
	}
	
	public String saveInvoice(Map<String, Object> inputJson) throws ParseException {
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd/mm/yyyy");
		
		InvoiceDO invoiceDO=new InvoiceDO();
		
		
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
		
		List<Map<String,Object>> invoiceProd=(List<Map<String,Object>>)inputJson.get("invoiceProducts");
		
		List<InvoiceProductDO> invoiceProducts=new ArrayList<>();
		if(invoiceProd!=null && invoiceProd.size()>0) {
			for(Map<String,Object> tempProd:invoiceProd) {
				InvoiceProductDO invoiceProduct=new InvoiceProductDO();
				
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
				
				invoiceProducts.add(invoiceProduct);
			}
			invoiceDO.setInvoiceProductDO(invoiceProducts);
		}
		
		
		
		if(invoiceNo!=null)
		invoiceDO.setInvoiceNo(invoiceNo.toString());
		
		if(invoiceId != null && !invoiceId.equals(""))
			invoiceDO.setInvoiceId(Integer.parseInt(invoiceId.toString()));
		
		if(sgstValue!=null) {
			invoiceDO.setSgstValue(new BigDecimal(sgstValue.toString()));
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
			
			invoiceDO.setInvoiceDate(sdf.parse(invoiceDate.toString()));
		}
		
		if(poDate!=null) {
			invoiceDO.setPoDate(sdf.parse(poDate.toString()));
		}
		
		if(challanNumber!=null) {
			invoiceDO.setChallanNo(challanNumber.toString());
		}
		
		if(challanDate!=null) {
			invoiceDO.setChallanDate(sdf.parse(challanDate.toString()));
		}
		
		if(dueDate!=null) {
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
		
		return invoiceDao.saveInvoice(invoiceDO);
		
	}
	
	
	public InvoiceDO getInvoiceDetails(String invNo){
		LOGGER.info("InvoiceService::getInvoiceDetails()::start");
		return invoiceDao.getInvoiceDetails(invNo);
	}
	
	public boolean DeleteInvoice(String invNo){
		LOGGER.info("InvoiceService::DeleteInvoice()::start");
		return invoiceDao.DeleteInvoice(invNo);
	}
	
	public boolean cloneInvoice(String invNo){
		LOGGER.info("InvoiceService::cloneInvoice()::start");
		return invoiceDao.cloneInvoice(invNo);
	}
	
	public boolean sendMail(String invNo , String custName){
		LOGGER.info("InvoiceService::sendMail()::start");
		boolean flag = false;
		try {
		
				String toMailId = invoiceDao.getCustomerEmail(custName);
				String subject = "Testing mail for Invoice No "+invNo;
				String body ="<html><body><p>Dear "+custName+"</p><br/>Please check your invoice is in paid state"
						+ ".please check invoice is correct or not <br/>Thanks & regards,<br/>"
						+ "<br/>axiware.techonology@gmail.com</body></html>";
				
				flag = sendmail.SendMails(toMailId,subject,body);
		}catch(Exception e) {
			LOGGER.error("Exception occured in invoiceservice :: sendMail()");
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
		
		
		return invoiceDao.saveDocMaster(documentmasterdo);
		
    }
    
    
    public List<DocumentSeqMasterDO> getDocMaster(){
		LOGGER.info("InvoiceService::getDocMaster()::start");
		return invoiceDao.getDocMaster();
	}
    
    public boolean deleteDocMaster(String docId){
		LOGGER.info("InvoiceService::deleteDocMaster()::start");
		return invoiceDao.deleteDocMaster(docId);
	}
		
	public List<String> getStandardType(String module,String subModule){
		return invoiceDao.getStandardType(module, subModule);
	}
	
}

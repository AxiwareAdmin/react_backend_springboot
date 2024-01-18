package com.accurate.erp.service.invoice;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

//import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.accurate.erp.dao.invoice.InvoiceDao;
import com.accurate.erp.model.invoice.ClientDO;
import com.accurate.erp.model.invoice.CustomerDO;
import com.accurate.erp.model.invoice.InvoiceDO;
import com.accurate.erp.model.invoice.InvoiceProductDO;
import com.accurate.erp.model.invoice.ProductDO;
import com.accurate.erp.model.invoice.UserDO;
import com.accurate.erp.model.modelmaster.DocumentSeqMasterDO;
import com.accurate.erp.model.purchase.PurchaseDO;
import com.accurate.erp.model.purchase.PurchaseProductDO;
import com.accurate.erp.model.purchase.SupplierDO;
import com.accurate.erp.utility.mail.SendMail;

@Service
@Transactional
public class InvoiceService {
	private static final Logger LOGGER=LogManager.getLogger(InvoiceService.class);

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
	
	public List<PurchaseDO> getPurchaseList(Map<String, String> data){
		LOGGER.info("InvoiceService::getPurchaseList()::start");
		return invoiceDao.getPurchaseList(data);
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
	
	public String saveInvoice(Map<String, Object> inputJson,String registerId,String userId,String userName) throws ParseException {
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd/mm/yyyy");
		
		InvoiceDO invoiceDO=new InvoiceDO();
		
		
		
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
		
		
		
		
		
		if(invoiceNo!=null)
		invoiceDO.setInvoiceNo(invoiceNo.toString());
		
		if(invoiceId != null && !invoiceId.equals(""))
			invoiceDO.setInvoiceId(Integer.parseInt(invoiceId.toString()));
		
		
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
		
		if(termsAndCondition!=null) {
			invoiceDO.setAdditionalTerms(termsAndCondition.toString());
		}
		
		if(additionalChargesGst!=null) {
			invoiceDO.setAdditionalChargesGst(Integer.parseInt(additionalChargesGst.toString()));
		}
		
		if(transportChargesGst!=null) {
			invoiceDO.setTransportGst(Integer.parseInt(transportChargesGst.toString()));
		}
		
		invoiceDO.setRegisterId(Integer.parseInt(registerId));
		
		invoiceDO.setUserId(Integer.parseInt(userId));
		
		invoiceDO.setCreatedDate(sdf.parse(sdf.format(new Date(0))));
		
		invoiceDO.setCreatedBy(userName);
		
		
		return invoiceDao.saveInvoice(invoiceDO);
		
	}
	
	
	
public String savePurchase(Map<String, Object> inputJson) throws ParseException {
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		
		PurchaseDO purchaseDO=new PurchaseDO();
		
		
		Object purchaseNo=inputJson.get("purchaseNo");
		
		
		Object purchaseId=inputJson.get("purchaseId");
		
		Object sgstValue=inputJson.get("sgstValue");
		
		Object cgstValue=inputJson.get("cgstValue");
		
		Object taxableValue=inputJson.get("taxableValue");
		
		Object purchaseValue=inputJson.get("purchaseValue");
		
		Object transportCharges=inputJson.get("transportCharges");
		
		Object additionalCharges=inputJson.get("additionalCharges");
		
		Object discount=inputJson.get("discount");
		
		Object otherDiscount=inputJson.get("otherDiscount");
		
//		Object shippingAddress=inputJson.get("shippingAddress");
		
		Object billingAddress=inputJson.get("billingAddress");
		
		Object poNumber=inputJson.get("poNumber");
		
		Object customerName=inputJson.get("supplierName");
		
		Object purchaseDate=inputJson.get("purchaseDate");
		
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
		
//		Object shippingGstNo=inputJson.get("shippingGstNo");
		
		Object serviceCheck=inputJson.get("serviceCheck");
		
//		Object shippingState=inputJson.get("shippingState");
		
		Object termsAndCondition=inputJson.get("termsAndCondition");
		
		List<Map<String,Object>> purchaseProd=(List<Map<String,Object>>)inputJson.get("purchaseProducts");
		
		List<PurchaseProductDO> purchaseProducts=new ArrayList<>();
		if(purchaseProd!=null && purchaseProd.size()>0) {
			for(Map<String,Object> tempProd:purchaseProd) {
				PurchaseProductDO purchaseProduct=new PurchaseProductDO();
				
				purchaseProduct.setProductName(tempProd.get("productName").toString());
				
				purchaseProduct.setProductDescription(tempProd.get("description").toString());
				
				purchaseProduct.setHsnSac(tempProd.get("hsnSac").toString());
				
				purchaseProduct.setTax(tempProd.get("tax").toString());
				
				purchaseProduct.setQuantity(tempProd.get("quantity").toString());
				
				purchaseProduct.setUnit(tempProd.get("unit").toString());
				
				purchaseProduct.setRate(tempProd.get("price").toString());
				
				purchaseProduct.setAmount(tempProd.get("amount").toString());
				
				purchaseProduct.setDiscount(tempProd.get("discount").toString());
				
				
				purchaseProduct.setInvoiceNumber(purchaseNo.toString());
				
				purchaseProduct.setInvoiceDate(sdf.parse(purchaseDate.toString()));
				
				purchaseProduct.setPurchaseDO(purchaseDO);
				
				purchaseProduct.setRegisterId("11111");
				
				purchaseProduct.setUserId("22222");
				
				purchaseProducts.add(purchaseProduct);
			}
			purchaseDO.setPurchaseProductDOs(purchaseProducts);
		}
		
		
		
		if(purchaseNo!=null)
			purchaseDO.setPurchaseNo(purchaseNo.toString());
		
		if(purchaseId != null && !purchaseId.equals(""))
			purchaseDO.setPurchaseId(Integer.parseInt(purchaseId.toString()));
		
//		if(sgstValue!=null) {
//			purchaseDO.setSgstValue(new BigDecimal(sgstValue.toString()));
//		}
//		
//		if(cgstValue!=null) {
//			invoiceDO.setCgstValue(new BigDecimal(cgstValue.toString()));
//		}
		
		if(taxableValue!=null) {
			purchaseDO.setTaxableValue(new BigDecimal(taxableValue.toString()));
		}
		
		if(purchaseValue!=null) {
			purchaseDO.setInvoiceValue(new BigDecimal(purchaseValue.toString()));
		}
				
		if(transportCharges!=null) {
			purchaseDO.setTransportCharges(transportCharges.toString());
		}
		
		if(additionalCharges!=null) {
			purchaseDO.setAdditionalCharges(additionalCharges.toString());
		}
		
		if(discount!=null) {
			purchaseDO.setDiscount(discount.toString());
		}
		
		if(otherDiscount!=null) {
			purchaseDO.setOtherDiscount(otherDiscount.toString());
		}
		
//		if(shippingAddress!=null) {
//			invoiceDO.setShippingAddress(shippingAddress.toString());
//		}
		
		if(billingAddress!=null) {
			purchaseDO.setAddress(billingAddress.toString());
		}
		
		if(poNumber!=null) {
			purchaseDO.setPoNo(poNumber.toString());
		}
		
		if(customerName!=null) {
			purchaseDO.setSupplierName(customerName.toString());
		}
		
		if(purchaseDate!=null) {
			java.util.Date d=sdf.parse(purchaseDate.toString());
			purchaseDO.setPurchaseDate(d);
			
			purchaseDO.setMonth(new SimpleDateFormat("MMM").format(d));
			
		}
		
		if(poDate!=null) {
			purchaseDO.setPoDate(sdf.parse(poDate.toString()));
		}
		
//		if(challanNumber!=null) {
//			purchaseDO.setChallanNo(challanNumber.toString());
//		}
//		
//		if(challanDate!=null) {
//			invoiceDO.setChallanDate(sdf.parse(challanDate.toString()));
//		}
		
		if(dueDate!=null) {
			purchaseDO.setDueDate(sdf.parse(dueDate.toString()));
		}
		
		if(paymentTerms!=null) {
			purchaseDO.setPaymentTerms(paymentTerms.toString());
		}
		
		/*
		 * if(transportMode!=null) {
		 * purchaseDO.setTransportMode(transportMode.toString()); }
		 * 
		 * if(vehicleNumber!=null) { purchaseDO.setVehicleNo(vehicleNumber.toString());
		 * }
		 */
		if(remarks!=null) {
			purchaseDO.setRemarks(remarks.toString());
		}
		
		
		if(state!=null) {
			purchaseDO.setState(state.toString());
		}
		
		
		if(gstNo!=null) {
			purchaseDO.setGstNo(gstNo.toString());
		}
		
//		if(shippingGstNo!=null) {
//			invoiceDO.setShippingGstNo(shippingGstNo.toString());
//		}
//		
//		if(serviceCheck!=null) {
//			invoiceDO.setServiceCheck(serviceCheck.toString());
//		}
		
//		if(shippingState!=null) {
//			invoiceDO.setShippingState(shippingState.toString());
//		}
		
		if(termsAndCondition!=null) {
			purchaseDO.setPaymentTerms(termsAndCondition.toString());
		}
		
	
		
		return invoiceDao.savePurchase(purchaseDO);
		
	}
	
	
	public InvoiceDO getInvoiceDetails(String invId){
		LOGGER.info("InvoiceService::getInvoiceDetails()::start");
		return invoiceDao.getInvoiceDetails(invId);
	}
	
	public boolean DeleteInvoice(String invNo){
		LOGGER.info("InvoiceService::DeleteInvoice()::start");
		return invoiceDao.DeleteInvoice(invNo);
	}
	
	public boolean deletePurchase(String purchaseId){
		LOGGER.info("InvoiceService::deletePurchase()::start");
		return invoiceDao.DeleteInvoice(purchaseId);
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
				toMailId="nadimk784@gmail.com";
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
		
		
		
		Object documentId=inputJson.get("DocumentId");
		
		DocumentSeqMasterDO documentmasterdo = invoiceDao.getDocMasterById(Integer.parseInt(documentId.toString()));
		
		Object documentName=inputJson.get("DocumentName");
		
		Object prefix1=inputJson.get("Prefix1");
		
		Object prefix2=inputJson.get("Prefix2");
		
		Object series=inputJson.get("Series");
		
		Object modeVal=inputJson.get("ModeVal");
		
		Object status=inputJson.get("Status");
//		
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
		
		if(status!=null) {
			documentmasterdo.setIsActive(status.toString());
		}
		
		
		return invoiceDao.saveDocMaster(documentmasterdo);
		
    }
    
    public DocumentSeqMasterDO getDocMasterByName(String name) {
    	LOGGER.info("InvoiceService::getDocMasterByName()::start");
    	return invoiceDao.getDocMasterByName(name);
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
	
	public List<PurchaseDO> getPurchaseList(){
		return invoiceDao.getPurchaseList();
	}
	
	public List<PurchaseDO> getPurchaseList(String month){
		return invoiceDao.getPurchaseList(month);
	}
	
	public PurchaseDO getPurchaseById(String purchaseId) {
		return invoiceDao.getPurchaseById(purchaseId);
	}
	
	public SupplierDO getSupplierById(String supplierId) {
		return invoiceDao.getSuppliersById(supplierId);
	}
	
	public List<SupplierDO> getAllSuppliers(){
		return invoiceDao.getAllSuppliers();
	}
	
	public List<InvoiceDO> getInvoiceByFinancialYear(String financialYear) {
		return invoiceDao.getInvoiceByFinancialYear(financialYear);
	}
	
	// added code for save customer details start

	public String saveCustomer(Map<String, Object> inputJson) throws ParseException {

		LOGGER.info("InvoiceService::saveCustomer()::start");
		String result = "success";

		try {

			SimpleDateFormat sdf=new SimpleDateFormat("dd/mm/yyyy");

			CustomerDO CustomerdO=new CustomerDO();


			Object customerName=inputJson.get("customerName");
			Object gstNo=inputJson.get("gstNo");
			Object shippingGstNo=inputJson.get("shippingGstNo");
			Object address1=inputJson.get("address1");
			Object address2=inputJson.get("address2");
			Object city=inputJson.get("city");
			Object pincode=inputJson.get("pincode");
			Object state=inputJson.get("state");
			Object shippingState=inputJson.get("shippingState");
			Object country=inputJson.get("country");
			Object email=inputJson.get("email");
			Object contactNo=inputJson.get("contactNo");
			Object shippingAddress1=inputJson.get("shippingAddress1");
			Object paymentTerms=inputJson.get("paymentTerms");

			if(customerName != null)
				CustomerdO.setCustomerName(customerName.toString());
			if(gstNo != null)
				CustomerdO.setGstNo(gstNo.toString());
			if(shippingGstNo != null)
				CustomerdO.setShippingGstNo(shippingGstNo.toString());
			if(address1 != null)
				CustomerdO.setAddress1(address1.toString());
			if(address2 != null)
				CustomerdO.setAddress2(address2.toString());
			if(city != null)
				CustomerdO.setCity(city.toString());
			if(pincode != null)
				CustomerdO.setPincode(Integer.parseInt(pincode.toString()));
			if(state != null)
				CustomerdO.setState(state.toString());
			if(shippingState != null)
				CustomerdO.setShippingState(shippingState.toString());
			if(country != null)
				CustomerdO.setCountry(country.toString());
			if(email != null)
				CustomerdO.setEmail(email.toString());
			if(contactNo != null)
				CustomerdO.setContactNo(contactNo.toString());
			if(shippingAddress1 != null)
				CustomerdO.setShippingAddress1(shippingAddress1.toString());
			if(paymentTerms != null)
				CustomerdO.setTermsAndCondition(paymentTerms.toString());

			CustomerdO.setStateCode("1234");
			CustomerdO.setCreatedDate("12-Dec-23");
			CustomerdO.setShippingCustomerName("Raju");
			CustomerdO.setShippingCity("Goa");
			CustomerdO.setShippingPinCode(1234);
			CustomerdO.setShippingStateCode("1111");
			CustomerdO.setShippingCountry("US");
			CustomerdO.setAccountingGroup("Y");
			CustomerdO.setUserId(121212);
			CustomerdO.setRegisterId(21212);
			CustomerdO.setDrCr("CR");
			CustomerdO.setPoNumber("11111");



			result = invoiceDao.saveCustomer(CustomerdO);
		}catch(Exception e) {
			LOGGER.error("Exception occured in InvoiceService :: saveCustomer()"+e);
			return "failure";
		}

			return result;
	}

		// added code for save customer details end

	    //added code for save product start

	public String saveProduct(Map<String, Object> inputJson) throws ParseException {

		LOGGER.info("InvoiceService::saveProduct()::start");
		String result = "success";

		try {

			SimpleDateFormat sdf=new SimpleDateFormat("dd/mm/yyyy");

			ProductDO productDO=new ProductDO();


			Object productName=inputJson.get("productName");
			Object productDescription=inputJson.get("productDescription");
			Object productType=inputJson.get("productType");
			Object partCode=inputJson.get("partCode");
			Object hsnCode=inputJson.get("hsnCode");
			Object unit=inputJson.get("unit");
			Object unitVarchar=inputJson.get("unitVarchar");
			Object rate=inputJson.get("rate");
			Object category=inputJson.get("category");
			Object applicableTax=inputJson.get("applicableTax");
			Object openingStock=inputJson.get("openingStock");


			if(productName != null)
				productDO.setProductName(productName.toString());
			if(productDescription != null)
				productDO.setProductDescription(productDescription.toString());
			if(productType != null)
				productDO.setProductType(productType.toString());
			if(partCode != null)
				productDO.setPartCode(partCode.toString());
			//if(hsnCode != null)
			//	productDO.setHsnCode(hsnCode.toString());
			if(openingStock != null) {
				productDO.setUnit(Integer.parseInt(openingStock.toString()));
				productDO.setOpeningStock(Integer.parseInt(openingStock.toString()));
			}
			if(unitVarchar != null)
				productDO.setUnitVarchar(unitVarchar.toString());
			if(rate != null)
				productDO.setRate(new BigDecimal(rate.toString()));
			if(category != null)
				productDO.setCategory(category.toString());
			if(applicableTax != null)
				productDO.setApplicableTax(new BigDecimal(applicableTax.toString()));


			productDO.setUserId(21212);
			productDO.setRegisterId(12121);
			productDO.setCreatedDate(new Date(0));
			productDO.setCreatedBy("sachin");


			result = invoiceDao.saveProduct(productDO);
		}catch(Exception e) {
			LOGGER.error("Exception occured in InvoiceService :: saveProduct()"+e);
			return "failure";
		}

			return result;
	}

	   // added code for save product code end
	
	
	public List<UserDO> getUserDOsByRegisterId(String registerId){
		return invoiceDao.getUserDOsByRegisterId(registerId);
	}
	
	public UserDO getUserByUserId(String userId) {
		return invoiceDao.getUserByUserId(userId);
	}
	
	public boolean cancelInvoiceById(Integer invoiceId) {
		return invoiceDao.cancelInvoiceById(invoiceId);
	}
	
	public ClientDO getClientDoByRegisterId(String registerId) {
		return invoiceDao.getClientDoByRegisterId(registerId);
	}
	
	public CustomerDO getCustomerByName(String custName) {
		return invoiceDao.getCustomerByName(custName);
	}
}

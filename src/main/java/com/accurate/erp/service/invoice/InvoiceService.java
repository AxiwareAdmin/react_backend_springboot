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
import org.springframework.web.multipart.MultipartFile;

import com.accurate.erp.dao.invoice.InvoiceDao;
import com.accurate.erp.model.invoice.CashDO;
import com.accurate.erp.model.invoice.CashSaleProductDO;
import com.accurate.erp.model.invoice.ClientDO;
import com.accurate.erp.model.invoice.CustomerDO;
import com.accurate.erp.model.invoice.InvoiceDO;
import com.accurate.erp.model.invoice.InvoiceProductDO;
import com.accurate.erp.model.invoice.ProductDO;
import com.accurate.erp.model.invoice.ProformaInvoiceDO;
import com.accurate.erp.model.invoice.ProformaInvoiceProductDO;
import com.accurate.erp.model.invoice.UserDO;
import com.accurate.erp.model.invoice.UserTO;
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
	
	public List<InvoiceDO> getInvoiceList(String financialYear){
		LOGGER.info("InvoiceService::getInvoiceList()::start");
		return invoiceDao.getInvoiceList(financialYear);
	}
	
	public List<CashDO> getCashList(String financialYear){
		LOGGER.info("InvoiceService::getCashList()::start");
		return invoiceDao.getCashList(financialYear);
	}
	
	public List<ProformaInvoiceDO> getProformaList(String financialYear){
		LOGGER.info("InvoiceService::getProformaList()::start");
		return invoiceDao.getProformaList(financialYear);
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
	
  public List<InvoiceDO> getInvoiceListByMonth(String month,String financialYear){
		LOGGER.info("InvoiceService::getInvoiceListByMonth()::start");
		return invoiceDao.getInvoiceListByMonth(month,financialYear);
	}
  
  public List<CashDO> getCashInvoiceListByMonth(String month,String financialYear){
		LOGGER.info("InvoiceService::getCashInvoiceListByMonth()::start");
		return invoiceDao.getCashInvoiceListByMonth(month,financialYear);
	}
  
  public List<ProformaInvoiceDO> getProformaInvoiceListByMonth(String month,String financialYear){
		LOGGER.info("InvoiceService::getCashInvoiceListByMonth()::start");
		return invoiceDao.getProformaInvoiceListByMonth(month,financialYear);
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
	
	
	
	
public String saveCashInvoice(Map<String, Object> inputJson,String registerId,String userId,String userName) throws ParseException {
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd/mm/yyyy");
		
		CashDO invoiceDO=new CashDO();
		
		
		
		Object invoiceNo=inputJson.get("invoiceNo");
		
		
		Object invoiceId=inputJson.get("invoiceId");
		
		//TO make sure invoice updates on edit
		if(invoiceId!=null && invoiceId.toString().length()>0) {
			invoiceDO=getSalesTypeClassDetails(CashDO.class,invoiceId.toString());
//			invoiceDO=invoiceDao.getInvoiceDetails(invoiceId.toString());
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
		
		
		List<CashSaleProductDO> invoiceProducts=new ArrayList<>();
		if(invoiceProd!=null && invoiceProd.size()>0) {
			for(Map<String,Object> tempProd:invoiceProd) {
				CashSaleProductDO invoiceProduct=new CashSaleProductDO();
				
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

				invoiceProduct.setMonth(new SimpleDateFormat("MMM").format(d));
				
				invoiceProduct.setYear(financialYear.toString());
				
				invoiceProducts.add(invoiceProduct);
			}
			invoiceDO.setCashSaleProductDO(invoiceProducts);
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
		
//		if(additionalChargesGst!=null) {
//			invoiceDO.setAdditionalChargesGst(Integer.parseInt(additionalChargesGst.toString()));
//		}
//		
//		if(transportChargesGst!=null) {
//			invoiceDO.setTransportGst(Integer.parseInt(transportChargesGst.toString()));
//		}
//		
		invoiceDO.setRegisterId(Integer.parseInt(registerId));
		
		invoiceDO.setUserId(Integer.parseInt(userId));
		
		invoiceDO.setCreatedDate(sdf.parse(sdf.format(new Date(0))));
		
		invoiceDO.setCreatedBy(userName);
		
		invoiceDO.setMonth(new SimpleDateFormat("MMM").format(d));
		
		return invoiceDao.saveCashInvoice(invoiceDO);
		
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
			invoiceDO.setAdditionalChargesGst(Integer.parseInt(additionalChargesGst.toString()));
		}
		
		if(transportChargesGst!=null) {
			invoiceDO.setTransportGst(Integer.parseInt(transportChargesGst.toString()));
		}
		invoiceDO.setInvoiceStatus("Overdue");
		
		invoiceDO.setRegisterId(Integer.parseInt(registerId));
		
		invoiceDO.setUserId(Integer.parseInt(userId));
		
		invoiceDO.setCreatedDate(sdf.parse(sdf.format(new Date(0))));
		
		invoiceDO.setCreatedBy(userName);
		
		invoiceDO.setMonth(new SimpleDateFormat("MMM").format(d));
		
		return invoiceDao.saveInvoice(invoiceDO);
		
	}
	
public String saveProforma(Map<String, Object> inputJson,String registerId,String userId,String userName) throws ParseException {
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd/mm/yyyy");
		
		ProformaInvoiceDO invoiceDO=new ProformaInvoiceDO();
		
		
		
		Object invoiceNo=inputJson.get("invoiceNo");
		
		
		Object invoiceId=inputJson.get("invoiceId");
		
		//TO make sure invoice updates on edit
			if(invoiceId!=null && invoiceId.toString().length()>0) {
				invoiceDO=getSalesTypeClassDetails(ProformaInvoiceDO.class,invoiceId.toString());
//					invoiceDO=invoiceDao.getInvoiceDetails(invoiceId.toString());
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
		
		java.util.Date d=null;
		if(invoiceDate!=null && invoiceDate.toString().length()>0) {
			
			d=sdf.parse(invoiceDate.toString());
		}
		else {
			d=sdf.parse(new java.util.Date().toString());
		}
		Object additionalChargesGst=inputJson.get("otherChargesGstRate");
		
		Object transportChargesGst=inputJson.get("transportGstRate");
		
		List<Map<String,Object>> invoiceProd=(List<Map<String,Object>>)inputJson.get("invoiceProducts");
		
		
		
		
		
		if(invoiceNo!=null)
		invoiceDO.setInvoiceNo(invoiceNo.toString());
		
		if(invoiceId != null && !invoiceId.equals(""))
			invoiceDO.setInvoiceId(Integer.parseInt(invoiceId.toString()));
		
		
		List<ProformaInvoiceProductDO> invoiceProducts=new ArrayList<>();
		if(invoiceProd!=null && invoiceProd.size()>0) {
			for(Map<String,Object> tempProd:invoiceProd) {
				ProformaInvoiceProductDO invoiceProduct=new ProformaInvoiceProductDO();
				
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
			invoiceDO.setAdditionalChargesGst(Integer.parseInt(additionalChargesGst.toString()));
		}
		
		if(transportChargesGst!=null) {
			invoiceDO.setTransportGst(Integer.parseInt(transportChargesGst.toString()));
		}
		
		invoiceDO.setRegisterId(Integer.parseInt(registerId));
		
		invoiceDO.setUserId(Integer.parseInt(userId));
		
		invoiceDO.setCreatedDate(sdf.parse(sdf.format(new Date(0))));
		
		invoiceDO.setCreatedBy(userName);
		
		invoiceDO.setMonth(new SimpleDateFormat("MMM").format(d));
		
		
		return invoiceDao.saveProformaInvoice(invoiceDO);
		
	}
	
	
	
public String savePurchase(Map<String, Object> inputJson,String registerID,String userId,String userName) throws ParseException {
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		
		PurchaseDO purchaseDO=new PurchaseDO();
		
		
		Object purchaseNo=inputJson.get("purchaseNo");
		
		
		Object purchaseId=inputJson.get("purchaseId");
		
		
		if(purchaseId!=null && purchaseId.toString().length()>0) {
			purchaseDO=getPurchaseById(purchaseId.toString());
//				invoiceDO=invoiceDao.getInvoiceDetails(invoiceId.toString());
		}
		
		
		Object sgstValue=inputJson.get("sgstValue");
		
		Object cgstValue=inputJson.get("cgstValue");
		
		Object taxableValue=inputJson.get("taxableValue");
		
		Object purchaseValue=inputJson.get("invoiceValue");
		
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
//		
//		Object challanNumber=inputJson.get("challanNumber");
//		
//		Object challanDate=inputJson.get("challanDate");
		
		Object paymentTerms=inputJson.get("paymentTerms");
		
		Object dueDate=inputJson.get("dueDate");
		
//		Object transportMode=inputJson.get("transportMode");
//		
//		Object vehicleNumber=inputJson.get("vehicleNumber");
		
		Object remarks=inputJson.get("remarks");
		
		Object state=inputJson.get("state");
		
		Object gstNo=inputJson.get("gstNo");
		
//		Object shippingGstNo=inputJson.get("shippingGstNo");
		
		Object serviceCheck=inputJson.get("serviceCheck");
		
//		Object shippingState=inputJson.get("shippingState");
		
		Object termsAndCondition=inputJson.get("termsAndCondition");
		
		Object financialYear=inputJson.get("financialYear");
		
		java.util.Date d=null;
		if(purchaseDate!=null && purchaseDate.toString().length()>0) {
			
			d=sdf.parse(purchaseDate.toString());
		}
		else {
			d=sdf.parse(new java.util.Date().toString());
		}
		
		List<Map<String,Object>> purchaseProd=(List<Map<String,Object>>)inputJson.get("purchaseProducts");
//		purchaseDO.getPurchaseProductDOs().clear();
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
				
				purchaseProduct.setRegisterId(registerID);
				
				purchaseProduct.setUserId(userId);
				
				purchaseProduct.setCreatedBy(userName);
				
				purchaseProduct.setYear(financialYear.toString());
				
				
				purchaseProduct.setMonth(new SimpleDateFormat("MMM").format(d));
				
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
		
		if(purchaseDate!=null && purchaseDate.toString().length()>0) {
			purchaseDO.setPurchaseDate(d);
			
			purchaseDO.setMonth(new SimpleDateFormat("MMM").format(d));
			
		}
		
		if(poDate!=null && poDate.toString().length()>0) {
			purchaseDO.setPoDate(sdf.parse(poDate.toString()));
		}
		
//		if(challanNumber!=null) {
//			purchaseDO.setChallanNo(challanNumber.toString());
//		}
//		
//		if(challanDate!=null) {
//			invoiceDO.setChallanDate(sdf.parse(challanDate.toString()));
//		}
		
		if(dueDate!=null && dueDate.toString().length()>0) {
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
		
		if(cgstValue!=null) {
			purchaseDO.setCgstValue(new BigDecimal(cgstValue.toString()));
		}
		
		if(sgstValue!=null) {
			purchaseDO.setSgstValue(new BigDecimal(sgstValue.toString()));
		}
		
		purchaseDO.setRegisterId(Integer.parseInt(registerID));
		
		purchaseDO.setUserId(Integer.parseInt(userId));
		
		purchaseDO.setCreatedDate(sdf.parse(sdf.format(new Date(System.currentTimeMillis()))));
		
		purchaseDO.setCreatedBy(userName);
		
		purchaseDO.setMonth(new SimpleDateFormat("MMM").format(d));
		
		purchaseDO.setFinancialYear(financialYear.toString());
		
		return invoiceDao.savePurchase(purchaseDO);
		
	}
	
public <T> T getSalesTypeClassDetails(Class<T> resultClass, String invId) {
	return invoiceDao.getSalesTypeClassDetails(resultClass, invId);
}
	
	public InvoiceDO getInvoiceDetails(String invId){
		LOGGER.info("InvoiceService::getInvoiceDetails()::start");
		return invoiceDao.getInvoiceDetails(invId);
	}
	
	public CashDO getCashInvoiceDetails(String invId){
		LOGGER.info("InvoiceService::getInvoiceDetails()::start");
		return invoiceDao.getCashInvoiceDetails(invId);
	}
	
	public ProformaInvoiceDO getProformaInvoiceDetails(String invId){
		LOGGER.info("InvoiceService::getProformaInvoiceDetails()::start");
		return invoiceDao.getProformaInvoiceDetails(invId);
	}
	
	public boolean DeleteInvoice(String invId,String className){
		LOGGER.info("InvoiceService::DeleteInvoice()::start");
		return invoiceDao.DeleteInvoice(invId,className);
	}
	
	public boolean deletePurchase(String purchaseId){
		LOGGER.info("InvoiceService::deletePurchase()::start");
		return invoiceDao.deletePurchase(purchaseId);
	}
	
	public boolean cloneInvoice(String invNo){
		LOGGER.info("InvoiceService::cloneInvoice()::start");
		return invoiceDao.cloneInvoice(invNo);
	}
	
	public boolean sendMail(String invNo , String custName,MultipartFile file){
		LOGGER.info("InvoiceService::sendMail()::start");
		boolean flag = false;
		try {
		
				String toMailId = invoiceDao.getCustomerEmail(custName);
				toMailId="nadimk784@gmail.com";
				String subject = "Testing mail for Invoice No "+invNo;
				String body ="<html><body><p>Dear "+custName+"</p><br/>Please check your invoice is in paid state"
						+ ".please check invoice is correct or not <br/>Thanks & regards,<br/>"
						+ "<br/>axiware.techonology@gmail.com</body></html>";
				
				flag = sendmail.SendMails(toMailId,subject,body,file,invNo);
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
	
	public List<PurchaseDO> getPurchaseList(String financialYear,String flag){
		return invoiceDao.getPurchaseList(financialYear,flag);
	}
	
	public List<PurchaseDO> getPurchaseList(String month,String financialYear,String flag){
		return invoiceDao.getPurchaseList(month,financialYear,flag);
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
	
	public List<Object[]> getOustandingCustomerByFinancialYear(String financialYear) {
		return invoiceDao.getOustandingCustomerByFinancialYear(financialYear);
	}
	
	public List<Object[]> getOustandingSuppliersByFinancialYear(String financialYear) {
		return invoiceDao.getOustandingSuppliersByFinancialYear(financialYear);
	}
	
	public List<CashDO> getCashInvoiceByFinancialYear(String financialYear) {
		return invoiceDao.getCashInvoiceByFinancialYear(financialYear);
	}
	
	
	public List<ProformaInvoiceDO> getProformaInvoiceByFinancialYear(String financialYear) {
		return invoiceDao.getProformaInvoiceByFinancialYear(financialYear);
	}
	
	
	// added code for save customer details start

	public String saveCustomer(Map<String, Object> inputJson,String regId,String userId) throws ParseException {

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
			Object openingStock=inputJson.get("openingStock");
			Object shippingCustomerName = inputJson.get("shippingCustomerName");
			Object shippingCity = inputJson.get("shippingCity");
			Object shippingPincode = inputJson.get("shippingPincode");
			Object shippingCountry = inputJson.get("shippingCountry");
			
			

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
			if(openingStock != null)
				CustomerdO.setOpeningStock(Integer.parseInt(openingStock.toString()));
			if(shippingCustomerName != null && !shippingCustomerName.equals(""))
				CustomerdO.setShippingCustomerName(shippingCustomerName.toString());
			if(shippingCity != null && !shippingCity.equals(""))
				CustomerdO.setShippingCity(shippingCity.toString());
			if(shippingPincode != null && !shippingPincode.equals(""))
				CustomerdO.setShippingPinCode(Integer.parseInt(shippingPincode.toString()));
			if(shippingCountry != null && !shippingCountry.equals(""))
				CustomerdO.setShippingCountry(shippingCountry.toString());
			if(regId != null && !regId.equals("")) {
				CustomerdO.setRegisterId(Integer.parseInt(regId));
			}else {
				CustomerdO.setRegisterId(21212);
			}
			if(userId != null && !userId.equals("")) {
				CustomerdO.setUserId(Integer.parseInt(userId));
			}else {
				CustomerdO.setUserId(121212);
			}
	
			CustomerdO.setCreatedDate(sdf.parse(sdf.format(new Date(0))).toString());
			CustomerdO.setShippingStateCode("1111");
			CustomerdO.setStateCode("1234");
			CustomerdO.setAccountingGroup("Y");			
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

	public String saveProduct(Map<String, Object> inputJson,String regId,String userId,String userName) throws ParseException {

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
			if(regId != null && !regId.equalsIgnoreCase("")) {
				productDO.setRegisterId(Integer.parseInt(regId));
			}else {
				productDO.setRegisterId(12121);
			}
			if(userId != null && !userId.equalsIgnoreCase("")) {
				productDO.setUserId(Integer.parseInt(userId));
			}else {
				productDO.setUserId(21212);
			}
			if(userName != null && !userName.equalsIgnoreCase("")) {
				productDO.setCreatedBy(userName);
			}else {
				productDO.setCreatedBy("sachin");
			}
			
			productDO.setCreatedDate(new Date(0));
			


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
	
	public boolean cancelInvoiceById(String invoiceType,Integer invoiceId) {
		return invoiceDao.cancelInvoiceById(invoiceType,invoiceId);
	}
	
	public boolean cancelPurchaseById(Integer invoiceId) {
		return invoiceDao.cancelPurchaseById(invoiceId);
	}
	
	public ClientDO getClientDoByRegisterId(String registerId) {
		return invoiceDao.getClientDoByRegisterId(registerId);
	}
	
	public CustomerDO getCustomerByName(String custName) {
		return invoiceDao.getCustomerByName(custName);
	}
	
	public SupplierDO getSupplierByName(String supplierName) {
		return invoiceDao.getSupplierByName(supplierName);
	}
	
	public List<Map<String,String>> getPONumberBySupplierName(String supplierName){
		return invoiceDao.getPONumberBySupplierName(supplierName);
	}
	
	public List<Map<String,String>> getPONumberBySupplierNameForCopy(String supplierName){
		return invoiceDao.getPONumberBySupplierNameForCopy(supplierName);
	}
	
	public UserTO getUserTOByUserId(String userId) {
		return invoiceDao.getUserTOByUserId(userId);
	}
	
	public void setFinancialYearByRegisterId(String registerId,String financialYear) {
		invoiceDao.setFinancialYearByRegisterId(registerId, financialYear);
	}
	
	public String saveClient(ClientDO clientDO) {
		return invoiceDao.saveClient(clientDO);
	}
	
	public List<UserDO> getUserList(){
		return invoiceDao.getUserList();
	}
}

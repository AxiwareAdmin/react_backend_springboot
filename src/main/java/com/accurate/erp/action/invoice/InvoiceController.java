package com.accurate.erp.action.invoice;

import org.springframework.http.MediaType;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.accurate.erp.dto.security.AuthenticationRequest;
import com.accurate.erp.helper.JwtUtil;
import com.accurate.erp.model.invoice.CashDO;
import com.accurate.erp.model.invoice.ClientDO;
import com.accurate.erp.model.invoice.CustomerDO;
import com.accurate.erp.model.invoice.EmployeeDO;
import com.accurate.erp.model.invoice.InvoiceDO;
import com.accurate.erp.model.invoice.ProductDO;
import com.accurate.erp.model.invoice.ProformaInvoiceDO;
import com.accurate.erp.model.invoice.UserDO;
import com.accurate.erp.model.modelmaster.DocumentSeqMasterDO;
import com.accurate.erp.model.purchase.PurchaseDO;
import com.accurate.erp.model.purchase.SupplierDO;
import com.accurate.erp.security.service.CustomUserDetailsService;
import com.accurate.erp.service.excel.ExcelService;
import com.accurate.erp.service.invoice.InvoiceService;

import io.jsonwebtoken.Claims;

import com.accurate.erp.model.invoice.InvoiceProductDO;

@RestController
public class InvoiceController {
	private final static Logger LOGGER=LoggerFactory.getLogger(InvoiceController.class);
	private final static String [] month = {"Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec","Jan","Feb","Mar"};
	private final static String[] completeMonth= {"April","May","June","July","August","Sepetember","October","November","December","January","February","March"};
	@Autowired
	InvoiceService invoiceService;
	
	@Value("${FILE_UPLOAD_LOCATION}")
	String fileUploadPath;
	
	@Value("${SEPERATOR}")
	String seperator;
	
	@Autowired
	ExcelService excelService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	CustomUserDetailsService userDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@GetMapping(value="/customers")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getCustomerList(){
		List<CustomerDO> custList=invoiceService.getCustometList();
		if(custList!=null && custList.size()>0) {
		return new ResponseEntity<List<CustomerDO>>(custList,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Customers are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/customer/{custId}")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getCustomer(@PathVariable Integer custId){
		CustomerDO customerDO=invoiceService.getCustomerById(custId);
		if(customerDO!=null) {
		return new ResponseEntity<CustomerDO>(customerDO,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Customers are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/customer/custname/{custname}")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getCustomerByName(@PathVariable String custname){
		CustomerDO customerDO=invoiceService.getCustomerByName(custname);
		if(customerDO!=null) {
		return new ResponseEntity<CustomerDO>(customerDO,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Customers are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	
	@GetMapping(value="/supplier/suppliername/{supplierName}")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getSupplierByName(@PathVariable String supplierName){
		SupplierDO customerDO=invoiceService.getSupplierByName(supplierName);
		if(customerDO!=null) {
		return new ResponseEntity<SupplierDO>(customerDO,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Customers are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	
	@GetMapping(value="/supplier/{supplierId}")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getSuplierById(@PathVariable String supplierId){
		SupplierDO supplierDO=invoiceService.getSupplierById(supplierId);
		if(supplierDO!=null) {
		return new ResponseEntity<SupplierDO>(supplierDO,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Suppliers are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/suppliers")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getAllSuppliers(){
		List<SupplierDO> supplierDOs=invoiceService.getAllSuppliers();
		if(supplierDOs!=null && supplierDOs.size()>0) {
		return new ResponseEntity<List<SupplierDO>>(supplierDOs,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Suppliers are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/allInvoices")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getAllInvoiceList(@RequestBody Map<String,String> map){
			
			String financialYear=map.get("financialYear");
		
		List<InvoiceDO> invoiceList=invoiceService.getInvoiceList(financialYear);
		if(invoiceList!=null && invoiceList.size()>0) {
			invoiceList.forEach(invoice->{
				invoice.setIncludeChildren(false);
			});
		return new ResponseEntity<List<InvoiceDO>>(invoiceList,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Invoices are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@PostMapping("/purchases")
	@CrossOrigin(origins = {"*"})
	public ResponseEntity<?> getAllPurchase(@RequestBody Map<String,String> map){
		
		String financialYear=map.get("financialYear");
		List<PurchaseDO> list=invoiceService.getPurchaseList(financialYear,"");
		
		if(list!=null && list.size()>0) {
			list.forEach(invoice->{
				invoice.setIncludeChildren(false);
			});
			return new ResponseEntity<List<PurchaseDO>>(list,HttpStatus.OK);
		}else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "purchases not found");
			return ResponseEntity.ok(jsonObj.toString());
		}
	}
	
	@GetMapping("/purchase/id/{id}")
	@CrossOrigin(origins = {"*"})
	public ResponseEntity<?> getPurchaseById(@PathVariable String id){
		PurchaseDO purchaseDO=invoiceService.getPurchaseById(id);
		
		if(purchaseDO!=null) {
			return new ResponseEntity<PurchaseDO>(purchaseDO,HttpStatus.OK);
		}
		
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("res", "purchases not found");
		return ResponseEntity.ok(jsonObj.toString());
		
	}

	
	@PostMapping("/purchases/{month}")
	@CrossOrigin(origins = {"*"})
	public ResponseEntity<?> getPurchasesByMonth(@PathVariable String month,@RequestBody Map<String,String> map){
		
		String financialYear=map.get("financialYear");

		List<PurchaseDO> list=invoiceService.getPurchaseList(month.substring(0,3),financialYear,"");
		
		if(list!=null && list.size()>0) {
			list.forEach(invoice->{
				invoice.setIncludeChildren(false);
			});
			return new ResponseEntity<List<PurchaseDO>>(list,HttpStatus.OK);
		}else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "purchases not found");
			return ResponseEntity.ok(jsonObj.toString());
		}
		
		
	}
	
	@GetMapping(value="/getInvNo")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getInvNo(){
		String invNo=invoiceService.getInvNo();
		if(invNo!=null && !invNo.equalsIgnoreCase("")) {
		return new ResponseEntity<String>(invNo,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Invoices are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	
	@GetMapping(value="/invoices/year/{financialYear}")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getInvoiceList(@PathVariable String financialYear){
		List<InvoiceDO> invoiceDO=invoiceService.getInvoiceByFinancialYear(financialYear);
		
		if(invoiceDO!=null) {
			invoiceDO.forEach(invoice->{
				invoice.setIncludeChildren(false);
			});
		return new ResponseEntity<List<InvoiceDO>>(invoiceDO,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Invoices are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/outstandingCustomer/year/{financialYear}")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getOutstandingCusomerList(@PathVariable String financialYear){
		List<Object[]> invoiceDO=invoiceService.getOustandingCustomerByFinancialYear(financialYear);
		if(invoiceDO!=null) {
		return new ResponseEntity<List<Object[]>>(invoiceDO,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Customers are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/outstandingSupplier/year/{financialYear}")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getOutstandingSupplierList(@PathVariable String financialYear){
		List<Object[]> invoiceDO=invoiceService.getOustandingSuppliersByFinancialYear(financialYear) ;
		if(invoiceDO!=null) {
		return new ResponseEntity<List<Object[]>>(invoiceDO,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Suppliers are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	
	
	
	@GetMapping(value="/cashInvoices/year/{financialYear}")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getCashInvoiceList(@PathVariable String financialYear){
		List<CashDO> cashDO=invoiceService.getCashInvoiceByFinancialYear(financialYear);
		if(cashDO!=null) {
			cashDO.forEach(invoice->{
				invoice.setIncludeChildren(false);
			});
		return new ResponseEntity<List<CashDO>>(cashDO,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Invoices are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/proformaInvoices/year/{financialYear}")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getProformaInvoiceList(@PathVariable String financialYear){
		List<ProformaInvoiceDO> ProformaDO=invoiceService.getProformaInvoiceByFinancialYear(financialYear);
		if(ProformaDO!=null) {
			ProformaDO.forEach(invoice->{
				invoice.setIncludeChildren(false);
			});
		return new ResponseEntity<List<ProformaInvoiceDO>>(ProformaDO,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Invoices are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	
	
	
	
	
	@PostMapping(value="/invoices/{month}")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getInvoiceListByMonth(@PathVariable String month,@RequestBody Map<String,String> map){
		
		String financialYear=map.get("financialYear");
		List<InvoiceDO> invoiceDO=invoiceService.getInvoiceListByMonth(month.substring(0,3),financialYear);
		if(invoiceDO!=null) {
			invoiceDO.forEach(invoice->{
				invoice.setIncludeChildren(false);
			});
		return new ResponseEntity<List<InvoiceDO>>(invoiceDO,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Invoices are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@PostMapping(value="/cashInvoices/{month}")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getCashInvoiceListByMonth(@PathVariable String month,@RequestBody Map<String,String> map){
		
		String financialYear=map.get("financialYear");
		List<CashDO> invoiceDO=invoiceService.getCashInvoiceListByMonth(month.substring(0,3),financialYear);
		if(invoiceDO!=null) {
			invoiceDO.forEach(invoice->{
				invoice.setIncludeChildren(false);
			});
		return new ResponseEntity<List<CashDO>>(invoiceDO,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Invoices are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@PostMapping(value="/proformaInvoices/{month}")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getProformaInvoiceListByMonth(@PathVariable String month,@RequestBody Map<String,String> map){
		
		String financialYear=map.get("financialYear");
		List<ProformaInvoiceDO> invoiceDO=invoiceService.getProformaInvoiceListByMonth(month.substring(0,3),financialYear);
		if(invoiceDO!=null) {
			invoiceDO.forEach(invoice->{
				invoice.setIncludeChildren(false);
			});
		return new ResponseEntity<List<ProformaInvoiceDO>>(invoiceDO,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Invoices are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	
	
	
	
	@GetMapping(value="/invoiceproducts")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getInvoiceProducts(){
		List<ProductDO> invoiceProductList=invoiceService.getInvoiceProductList();
		if(invoiceProductList!=null && invoiceProductList.size()>0) {
		return new ResponseEntity<List<ProductDO>>(invoiceProductList,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Invoice products are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/invoiceproduct/{id}")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getInvoiceProducts(@PathVariable Integer id){
		ProductDO prodDO=invoiceService.getProductById(id);
		if(prodDO!=null) {
		return new ResponseEntity<ProductDO>(prodDO,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Invoice products are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}

	@PostMapping(value="/saveInvoice",consumes= {"application/json"})
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> saveInvoice(@RequestBody Map<String, Object> inputJson,HttpServletRequest request) throws ParseException{
		
		System.out.print(inputJson);
		String token=request.getHeader("Authorization").split(" ")[1];
		
		
		  Claims claims= jwtUtil.extractAllClaims(token);
		  
		  LinkedHashMap<String,Object> map=claims.get("user",LinkedHashMap.class);
		  
		  String registerId=map.get("registerId").toString();
		  
		  String userId=map.get("userId").toString();
		  
		  String userName=map.get("userName").toString();
		  
		String msg=invoiceService.saveInvoice(inputJson,registerId,userId,userName);
		
		
		
		JSONObject jsonObj=new JSONObject();
		if(msg.equals("success")) {
		jsonObj.put("res", "success");
		}else {
			jsonObj.put("res", "failure");
		}
		return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
	}
	
	
	
	@PostMapping(value="/saveCashInvoice",consumes= {"application/json"})
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> saveCashInvoice(@RequestBody Map<String, Object> inputJson,HttpServletRequest request) throws ParseException{
		
		System.out.print(inputJson);
		String token=request.getHeader("Authorization").split(" ")[1];
		
		
		  Claims claims= jwtUtil.extractAllClaims(token);
		  
		  LinkedHashMap<String,Object> map=claims.get("user",LinkedHashMap.class);
		  
		  String registerId=map.get("registerId").toString();
		  
		  String userId=map.get("userId").toString();
		  
		  String userName=map.get("userName").toString();
		  
		String msg=invoiceService.saveCashInvoice(inputJson,registerId,userId,userName);
		
		
		
		JSONObject jsonObj=new JSONObject();
		if(msg.equals("success")) {
		jsonObj.put("res", "success");
		}else {
			jsonObj.put("res", "failure");
		}
		return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
	}
	
	
	@PostMapping(value="/saveProformaInvoice",consumes= {"application/json"})
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> saveProformaInvoice(@RequestBody Map<String, Object> inputJson,HttpServletRequest request) throws ParseException{
		
		System.out.print(inputJson);
		String token=request.getHeader("Authorization").split(" ")[1];
		
		
		  Claims claims= jwtUtil.extractAllClaims(token);
		  
		  LinkedHashMap<String,Object> map=claims.get("user",LinkedHashMap.class);
		  
		  String registerId=map.get("registerId").toString();
		  
		  String userId=map.get("userId").toString();
		  
		  String userName=map.get("userName").toString();
		  
		String msg=invoiceService.saveProforma(inputJson,registerId,userId,userName);
		
		
		
		JSONObject jsonObj=new JSONObject();
		if(msg.equals("success")) {
		jsonObj.put("res", "success");
		}else {
			jsonObj.put("res", "failure");
		}
		return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
	}
	
	
	
	@PostMapping(value="/savepurchase",consumes= {"application/json"})
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> savePurchase(HttpServletRequest request,@RequestBody Map<String, Object> inputJson) throws ParseException{
		
		System.out.print(inputJson);
		
		String token=request.getHeader("Authorization").split(" ")[1];
		
		
		  Claims claims= jwtUtil.extractAllClaims(token);
		  
		  LinkedHashMap<String,Object> map=claims.get("user",LinkedHashMap.class);
		  
		  String registerId=map.get("registerId").toString();
		  
		  String userId=map.get("userId").toString();
		  
		  String userName=map.get("userName").toString();
		
		String msg=invoiceService.savePurchase(inputJson,registerId,userId,userName);
		
		JSONObject jsonObj=new JSONObject();
		if(msg.equals("success")) {
		jsonObj.put("res", "success");
		}else {
			jsonObj.put("res", "failure");
		}
		return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);

		
		
	}
	@PostMapping(value="/viewSalesProformaReg")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getviewSalesProformaReg(@RequestBody Map<String,String> map){
		
		String financialYear=map.get("financialYear");
		List<ProformaInvoiceDO> cashList=invoiceService.getProformaList(financialYear);
		if(cashList!=null && cashList.size()>0) {
			
			/*invoiceList.forEach((ele) ->{
				
			});*/
			List<String> salesobj = new ArrayList<>();
			for(String str  : completeMonth) {
				JSONObject jsonObj=new JSONObject();
				Integer totalInv = 0;
				BigDecimal closingBal = new BigDecimal(0);
				BigDecimal amount = new BigDecimal(0);
				for(ProformaInvoiceDO invdo : cashList) {
					if(str.substring(0,3).equalsIgnoreCase(invdo.getMonth())) {
						totalInv = totalInv + 1;
						amount = amount.add(invdo.getInvoiceValue());
						closingBal = closingBal.add(new BigDecimal(1));
					}
				}
				jsonObj.put("month", str);
				jsonObj.put("totalInv", totalInv);
				jsonObj.put("amount", amount);
				jsonObj.put("closingBal", closingBal);
				jsonObj.put("progress", 30);
				salesobj.add(jsonObj.toString());
			}
			
		return new ResponseEntity<List<String>>(salesobj,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Invoices are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@PostMapping(value="/viewSalesCashReg")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getviewSalesCashReg(@RequestBody Map<String,String> map){
		
		String financialYear=map.get("financialYear");
		List<CashDO> cashList=invoiceService.getCashList(financialYear);
		if(cashList!=null && cashList.size()>0) {
			
			/*invoiceList.forEach((ele) ->{
				
			});*/
			List<String> salesobj = new ArrayList<>();
			for(String str  : completeMonth) {
				JSONObject jsonObj=new JSONObject();
				Integer totalInv = 0;
				BigDecimal closingBal = new BigDecimal(0);
				BigDecimal amount = new BigDecimal(0);
				for(CashDO invdo : cashList) {
					if(str.substring(0,3).equalsIgnoreCase(invdo.getMonth())) {
						totalInv = totalInv + 1;
						amount = amount.add(invdo.getInvoiceValue());
						closingBal = closingBal.add(new BigDecimal(1));
					}
				}
				jsonObj.put("month", str);
				jsonObj.put("totalInv", totalInv);
				jsonObj.put("amount", amount);
				jsonObj.put("closingBal", closingBal);
				jsonObj.put("progress", 30);
				salesobj.add(jsonObj.toString());
			}
			
		return new ResponseEntity<List<String>>(salesobj,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Invoices are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	
	@PostMapping(value="/viewSalesReg")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getviewSalesReg(@RequestBody Map<String,String> map){
		
		String financialYear=map.get("financialYear");
		
		List<InvoiceDO> invoiceList=invoiceService.getInvoiceList(financialYear);
		if(invoiceList!=null && invoiceList.size()>0) {
			
			/*invoiceList.forEach((ele) ->{
				
			});*/
			List<String> salesobj = new ArrayList<>();
			for(String str  : completeMonth) {
				JSONObject jsonObj=new JSONObject();
				Integer totalInv = 0;
				BigDecimal closingBal = new BigDecimal(0);
				BigDecimal amount = new BigDecimal(0);
				for(InvoiceDO invdo : invoiceList) {
					if(str.substring(0,3).equalsIgnoreCase(invdo.getMonth())) {
						totalInv = totalInv + 1;
						amount = amount.add(invdo.getInvoiceValue());
						closingBal = closingBal.add(new BigDecimal(1));
					}
				}
				jsonObj.put("month", str);
				jsonObj.put("totalInv", totalInv);
				jsonObj.put("amount", amount);
				jsonObj.put("closingBal", closingBal);
				jsonObj.put("progress", 30);
				salesobj.add(jsonObj.toString());
			}
			
		return new ResponseEntity<List<String>>(salesobj,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Invoices are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/getPurchases")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getPurchaseList(@RequestBody Map<String,String> map){
		
		String financialYear=map.get("financialYear");
		List<PurchaseDO> purchaseList=invoiceService.getPurchaseList(financialYear,"");
		if(purchaseList!=null && purchaseList.size()>0) {
			
			/*invoiceList.forEach((ele) ->{
				
			});*/
			List<String> salesobj = new ArrayList<>();
			for(String str  : completeMonth) {
				JSONObject jsonObj=new JSONObject();
				Integer totalInv = 0;
				BigDecimal closingBal = new BigDecimal(0);
				BigDecimal amount = new BigDecimal(0);
				for(PurchaseDO purchaseDO : purchaseList) {
					if(str.substring(0,3).equalsIgnoreCase(purchaseDO.getMonth())) {
						totalInv = totalInv + 1;
						amount = amount.add(purchaseDO.getInvoiceValue());
						closingBal = closingBal.add(new BigDecimal(1));
					}
				}
				jsonObj.put("month", str);
				jsonObj.put("totalInv", totalInv);
				jsonObj.put("amount", amount);
				jsonObj.put("closingBal", closingBal);
				MathContext precision = new MathContext(2);
				if(totalInv>0)
				jsonObj.put("progress", amount.divide(new BigDecimal(totalInv),precision).multiply(new BigDecimal(100)));
				else
					jsonObj.put("progress","0");
				salesobj.add(jsonObj.toString());
			}
			
		return new ResponseEntity<List<String>>(salesobj,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Purchase are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/viewInvoice")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getInvoiceDetails(@QueryParam("invId") String invId){
		InvoiceDO invoicedo=invoiceService.getInvoiceDetails(invId);
		if(invoicedo!=null) {
		return new ResponseEntity<InvoiceDO>(invoicedo,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Invoice Details are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/viewCashInvoice")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getCashInvoiceDetails(@QueryParam("invId") String invId){
		CashDO invoicedo=invoiceService.getCashInvoiceDetails(invId);
		if(invoicedo!=null) {
		return new ResponseEntity<CashDO>(invoicedo,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Invoice Details are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/viewProformaInvoice")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getProformaInvoiceDetails(@QueryParam("invId") String invId){
		ProformaInvoiceDO invoicedo=invoiceService.getProformaInvoiceDetails(invId);
		if(invoicedo!=null) {
		return new ResponseEntity<ProformaInvoiceDO>(invoicedo,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Invoice Details are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/deletePurchase/{id}")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> deletePurchase(@PathVariable("id") String purchaseId){
		boolean flag=invoiceService.deletePurchase(purchaseId);
		
		JSONObject jsonObj=new JSONObject();
		
		if(flag) {
			jsonObj.put("res", "sucess");
		}else {
			jsonObj.put("res", "failure");
		}
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
	}
	


	
	
	@GetMapping(value="/deleteInv")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getDeleteInvoice(@RequestParam String invoiceId, @RequestParam String invoiceType){
		String className=null;
		
		if(invoiceType.equalsIgnoreCase("CASH")) {
			className="cash_sale";
		}
		else if(invoiceType.equalsIgnoreCase("GST")) {
			className="invoice";
		}
		else if(invoiceType.equalsIgnoreCase("PROFORMA")) {
			className="proforma_invoice";
		}
			
		
		boolean flag=invoiceService.DeleteInvoice(invoiceId,className);
		
		JSONObject jsonObj=new JSONObject();
		
		if(flag) {
			jsonObj.put("res", "sucess");
		}else {
			jsonObj.put("res", "failure");
		}
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
	}
	
	@GetMapping(value="/cloneInv")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> cloneInvoice(@QueryParam("invNo") String invNo){
		boolean flag=false;
				flag = invoiceService.cloneInvoice(invNo);
		
		JSONObject jsonObj=new JSONObject();
		
		if(flag) {
			jsonObj.put("res", "sucess");
		}else {
			jsonObj.put("res", "failure");
		}
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
	}
	
	@PostMapping("/excel/invoices")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> downloadInvoicesInExcel(@RequestBody Map<String,String> data){
		String filename = "invoices.xlsx";
		System.out.println(data);
	    InputStreamResource file = new InputStreamResource(excelService.load(data));
	    
	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	            .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
	            .body(file);
	}
	
	@PostMapping("/getFilterInvoices")
	@CrossOrigin(origins = {"*"})
	public ResponseEntity<?> getFilterInvoices(@RequestBody Map<String,String> data){
		List<InvoiceDO> list=invoiceService.getInvoiceList(data);
		
		if(list!=null && list.size()>0) {
			return new ResponseEntity<List<InvoiceDO>>(list,HttpStatus.OK);
		}
		
		
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("res", "Invoice Details are not found");
		return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);

	}
	
	
	@PostMapping("/excel/purchases")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> downloadInvoicesInExcelForPurchase(@RequestBody Map<String,String> data){
		String filename = "invoices.xlsx";
		System.out.println(data);
	    InputStreamResource file = new InputStreamResource(excelService.loadPurchase(data));
	    
	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	            .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
	            .body(file);
	}
	
	/*
	 * @GetMapping(value="/sendmail")
	 * 
	 * @CrossOrigin(origins={"*"}) public ResponseEntity<?>
	 * sendMail(@QueryParam("invNo") String invNo , @QueryParam("custName") String
	 * custName ){ boolean flag=false; flag = invoiceService.sendMail(invNo ,
	 * custName);
	 * 
	 * JSONObject jsonObj=new JSONObject();
	 * 
	 * if(flag) { jsonObj.put("res", "sucess"); }else { jsonObj.put("res",
	 * "failure"); } return new
	 * ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK); }
	 */
	
	@PostMapping(value="/sendmail")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> sendMail(@RequestParam("file") MultipartFile file,@RequestParam("invoiceNo") String invoiceNo,@RequestParam("custName") String custName){
		boolean flag=false;
				flag = invoiceService.sendMail(invoiceNo , custName,file);
		
		JSONObject jsonObj=new JSONObject();
		
		if(flag) {
			jsonObj.put("res", "sucess");
		}else {
			jsonObj.put("res", "failure");
		}
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
	}
	
	@PostMapping(value="/saveDocMaster",consumes= {"application/json"})
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> saveDocMaster(@RequestBody Map<String, Object> inputJson) throws ParseException{
		
		System.out.print(inputJson);
		
		String msg=invoiceService.saveDocMaster(inputJson);
		
		JSONObject jsonObj=new JSONObject();
		if(msg.equals("success")) {
		jsonObj.put("res", "success");
		}else {
			jsonObj.put("res", "failure");
		}
		return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		
	}
	
	@GetMapping(value="/getDocMaster")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getDocMaster(){
		List<DocumentSeqMasterDO> documentSeqMaster=invoiceService.getDocMaster();
		if(documentSeqMaster!=null && documentSeqMaster.size() > 0) {
		return new ResponseEntity<List<DocumentSeqMasterDO>>(documentSeqMaster,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "document seq master data not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		
	  }
	}
	
	@GetMapping(value="/getDocMaster/{documentName}")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getDocMaster(@PathVariable String documentName){
		DocumentSeqMasterDO documentSeqMaster=invoiceService.getDocMasterByName(documentName);
		if(documentSeqMaster!=null) {
		return new ResponseEntity<DocumentSeqMasterDO>(documentSeqMaster,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "document seq master data not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		
	  }
	}
	
	@GetMapping(value="/deleteDocMaster")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> deleteDocMaster(@QueryParam("docId") String docId){
		boolean flag=invoiceService.deleteDocMaster(docId);
		
		JSONObject jsonObj=new JSONObject();
		
		if(flag) {
			jsonObj.put("res", "sucess");
		}else {
			jsonObj.put("res", "failure");
		}
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
	}
	
	@PostMapping("/login")
	@CrossOrigin(origins = {"*"})
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("executing");
		JSONObject jsonObj=new JSONObject();
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			System.out.println("Exception occured while authenticating");
			jsonObj.put("error", "Incorrect username or password");
			 response.setStatus(HttpStatus.UNAUTHORIZED.value()); // Set HTTP 401 Unauthorized status
             response.getWriter().write("{\"error\":\"Invalid credentials\"}"); // Response message
             response.getWriter().flush();
             request.setAttribute("msgFlag", 1);
            throw e;
//			throw new Exception("Incorrect username or password");
//			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.UNAUTHORIZED);
		}
		
		UserDetails userDetails=userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		
		String token=jwtUtil.generateToken(userDetails);
		
		
		jsonObj.put("token", token);
		return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
	}

	@PostMapping("/validate")
	@CrossOrigin(origins = {"*"})
	public ResponseEntity<?> validateJwt(@RequestParam(name = "token") String jwtToken,HttpServletResponse response,HttpServletRequest request) throws Exception {
		UserDetails userDetails=null;
		JSONObject jsonObj=new JSONObject();
		String userName=null;
		try {
			 userName=jwtUtil.extractUsername(jwtToken);
			
			userDetails=userDetailsService.loadUserByUsername(userName);
		}catch(Exception e) {
			jsonObj.put("error", "Incorrect username or password");
			 response.setStatus(HttpStatus.UNAUTHORIZED.value()); // Set HTTP 401 Unauthorized status
            response.getWriter().write("{\"error\":\"Invalid credentials\"}"); // Response message
            response.getWriter().flush();
            request.setAttribute("msgFlag", 1);
            throw e;
		}
		
		boolean flag=jwtUtil.validateToken(jwtToken, userDetails);
		
		if(flag) {
			jsonObj.put("res", "success");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
		else {
			jsonObj.put("res", "error");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@GetMapping("/gstRates")
	@CrossOrigin(origins = {"*"})
	public ResponseEntity<?> getGstRates(){
		List<String> gstRates=invoiceService.getStandardType("invoice", "gstrate");
		
		if(gstRates!=null && gstRates.size()>0) {
			return new ResponseEntity<List<String>>(gstRates,HttpStatus.OK);
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("res", "standard type not found");
		return new ResponseEntity<String>(jsonObject.toString(),HttpStatus.OK);
		
	}
	
	@GetMapping("/user")
	@CrossOrigin(origins = {"*"})
	public ResponseEntity<?> getUserDetails(HttpServletRequest request){
		
		String token=request.getHeader("Authorization").split(" ")[1];
		
		
		  Claims claims= jwtUtil.extractAllClaims(token);
		  
		  LinkedHashMap<String,Object> map=claims.get("user",LinkedHashMap.class);
		  
		  String userId=map.get("userId").toString();
		
		UserDO userDO=invoiceService.getUserByUserId(userId);
		
		
		if(userDO!=null) {
			return new ResponseEntity<UserDO>(userDO,HttpStatus.OK);
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("res", "User not found");
		return new ResponseEntity<String>(jsonObject.toString(),HttpStatus.OK);
		
	}
	
	//register id is nothing but client id which can have multiple users and this method is getting all the users for a client nased on one of the users
	@GetMapping("/getusersbyregistrid")
	@CrossOrigin(origins = {"*"})
	public ResponseEntity<?> getUserListByUserIdForSameClient(HttpServletRequest request) {
		String token=request.getHeader("Authorization").split(" ")[1];
		
		
		  Claims claims= jwtUtil.extractAllClaims(token);
		  
		  LinkedHashMap<String,String> map=claims.get("user",LinkedHashMap.class);
		  
		  String registerId=map.get("registerId").toString();
		 
		
//		UserDO userDO=(UserDO)jwtUtil.getClaim(token);
//		
//		
//		String registerId=userDO.getRegisterId();
		List<UserDO> userList=invoiceService.getUserDOsByRegisterId(registerId);
		
		if(userList!=null) {
			return new ResponseEntity<List<UserDO>>(userList,HttpStatus.OK);
		}
		
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("res", "Users not found");
		return new ResponseEntity<String>(jsonObject.toString(),HttpStatus.OK);
		
		
	}
	
	@GetMapping("/getClientDOForUser")
	@CrossOrigin(origins = {"*"})
	public ResponseEntity<?> getClientDOForUser(HttpServletRequest request) throws IOException{
		  String token=request.getHeader("Authorization").split(" ")[1];
		  Claims claims= jwtUtil.extractAllClaims(token);
		  
		  LinkedHashMap<String,String> map=claims.get("user",LinkedHashMap.class);
		  
		  String registerId=map.get("registerId").toString();
		  
		  ClientDO clientDO=invoiceService.getClientDoByRegisterId(registerId);
		  
		  StringBuilder directory=new StringBuilder(fileUploadPath);
		  
		  directory.append(seperator);
		  
		  directory.append(registerId);
		  
		  Path imagePath = Paths.get(directory.toString());
		  
		  try (DirectoryStream<Path> stream = Files.newDirectoryStream(imagePath)) {
	            for (Path file : stream) {
	            	
	            	if(file.getFileName().toString().contains("LOGO")) {
	            		clientDO.setLogo(Files.readAllBytes(file));
	            	}
	            	
	            	if(file.getFileName().toString().contains("SIGNATURE")) {
	            		clientDO.setSignature(Files.readAllBytes(file));
	            	}
	               
	                // Process the file here
	            }
	        } catch (Exception e) {
	            // IOException can be thrown during the iteration, fail early
	        	LOGGER.error("Exception in InvoiceController::getClientDOForUser()::"+e);
//	            throw e;
	        }

		  
		  if(clientDO!=null) {
			  return new ResponseEntity<ClientDO>(clientDO,HttpStatus.OK);
		  }
		  
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("res", "client not found");
			return new ResponseEntity<String>(jsonObject.toString(),HttpStatus.OK);
	}
	
	@GetMapping("/employee")
	@CrossOrigin(origins = {"*"})
	public ResponseEntity<?> test(@RequestBody EmployeeDO employee){
		return new ResponseEntity<EmployeeDO>(employee,HttpStatus.OK);
	}
	
	

	//add customer details code start

	@PostMapping(value="/addCustomerDetails",consumes= {"application/json"})
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> saveCustomer(@RequestBody Map<String, Object> inputJson,HttpServletRequest request) throws ParseException{

		System.out.print(inputJson);

		String token=request.getHeader("Authorization").split(" ")[1];


		  Claims claims= jwtUtil.extractAllClaims(token);

		  LinkedHashMap<String,Object> map=claims.get("user",LinkedHashMap.class);

		  String registerId=map.get("registerId").toString();

		  String userId=map.get("userId").toString();

		  //String userName=map.get("userName").toString();
		
		String msg=invoiceService.saveCustomer(inputJson,registerId,userId);

		JSONObject jsonObj=new JSONObject();
		if(!msg.equals("")) {
		jsonObj.put("res", msg);
		}else {
			jsonObj.put("res", "failure");
		}
		return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
	}
	// add customer details code end

	//add customer details code start

		@PostMapping(value="/addProductDetails",consumes= {"application/json"})
		@CrossOrigin(origins={"*"})
		public ResponseEntity<?> saveProduct(@RequestBody Map<String, Object> inputJson,HttpServletRequest request) throws ParseException{

			System.out.print(inputJson);
			
			String token=request.getHeader("Authorization").split(" ")[1];


			  Claims claims= jwtUtil.extractAllClaims(token);

			  LinkedHashMap<String,Object> map=claims.get("user",LinkedHashMap.class);

			  String registerId=map.get("registerId").toString();

			  String userId=map.get("userId").toString();

			  String userName=map.get("userName").toString();

			String msg=invoiceService.saveProduct(inputJson,registerId,userId,userName);

			JSONObject jsonObj=new JSONObject();
			if(!msg.equals("")) {
			jsonObj.put("res", msg);
			}else {
				jsonObj.put("res", "failure");
			}
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
		// add customer details code end
		
//		@PostMapping(value = "/cancelInvoice/{invoiceId}")
//		@CrossOrigin(origins={"*"})
//		public ResponseEntity<?> cancelInvoice(@PathVariable String invoiceId){
//			boolean flag=invoiceService.cancelInvoiceById(Integer.parseInt(invoiceId));
//			JSONObject jsonObj=new JSONObject();
//			if(flag==true) {
//				jsonObj.put("res", "success");
//			}else {
//				jsonObj.put("res", "failure");
//			}
//			
//			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
//		}
		
		@PostMapping(value = "/cancelInvoice")
		@CrossOrigin(origins={"*"})
		public ResponseEntity<?> cancelInvoice(@RequestParam String invoiceId,@RequestParam String invoiceType){
			
			
			
			boolean flag=invoiceService.cancelInvoiceById(invoiceType,Integer.parseInt(invoiceId));
			JSONObject jsonObj=new JSONObject();
			if(flag==true) {
				jsonObj.put("res", "success");
			}else {
				jsonObj.put("res", "failure");
			}
			
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
		
		

		@PostMapping(value = "/cancelPurchase/{id}")
		@CrossOrigin(origins={"*"})
		public ResponseEntity<?> cancelPurchase(@PathVariable("id") String invoiceId){
			
			
			
			boolean flag=invoiceService.cancelPurchaseById(Integer.parseInt(invoiceId));
			JSONObject jsonObj=new JSONObject();
			if(flag==true) {
				jsonObj.put("res", "success");
			}else {
				jsonObj.put("res", "failure");
			}
			
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	
		
		@GetMapping(value="/poBySuppName/{supplierName}")
		@CrossOrigin(origins = {"*"})
		public ResponseEntity<?> getPoNumbersBySupplierName(@PathVariable String supplierName){
			List<Map<String,String>> poNumber=invoiceService.getPONumberBySupplierName(supplierName);
			
			return new ResponseEntity<List<Map<String,String>>>(poNumber,HttpStatus.OK);
		}
		
		
		@GetMapping(value="/poBySuppNameForCopy/{supplierName}")
		@CrossOrigin(origins = {"*"})
		public ResponseEntity<?> getPoNumbersBySupplierNameForCopy(@PathVariable String supplierName){
			List<Map<String,String>> poNumber=invoiceService.getPONumberBySupplierNameForCopy(supplierName);
			
			return new ResponseEntity<List<Map<String,String>>>(poNumber,HttpStatus.OK);
		}
		
		
		@GetMapping(value="/me")
		@CrossOrigin(origins = {"*"})
		public ResponseEntity<?> getUserToByUserId(HttpServletRequest request){
			 String token=request.getHeader("Authorization").split(" ")[1];
			  Claims claims= jwtUtil.extractAllClaims(token);
			  
			  LinkedHashMap<String,Integer> map=claims.get("user",LinkedHashMap.class);
			  
			  Integer userId=map.get("userId");

			  UserDO userDO=invoiceService.getUserByUserId(userId.toString());
			  
			  if(userDO==null) {
				  new ResponseEntity<String>("user not found",HttpStatus.OK);
			  }
			  
			return new ResponseEntity<UserDO>(userDO,HttpStatus.OK);
		}
		
		@GetMapping(value="/financialYearList")
		@CrossOrigin(origins = {"*"})
		public ResponseEntity<?> getFinancialYearList(){
			
			Calendar today = Calendar.getInstance();
			
			List<String> financialYearList=new ArrayList<>();
	        
	        String fiscalYear;
	        for(int i=0;i<5;i++){
	        if ((today.get(Calendar.MONTH) + 1) <= 3) {
	            fiscalYear = (today.get(Calendar.YEAR) - 1) + "-" + Integer.toString(today.get(Calendar.YEAR)).substring(2);
	        } else {
	            fiscalYear = today.get(Calendar.YEAR) + "-" + Integer.toString(today.get(Calendar.YEAR) + 1).substring(2);
	        }
	        
	        financialYearList.add(fiscalYear);
	        
	        today.add(Calendar.YEAR, -1);
	        }
	        
	        return new ResponseEntity<List<String>>(financialYearList,HttpStatus.OK);
			
		}
		
		@PostMapping(value="/setFinancialYear")
		@CrossOrigin(origins = {"*"})
		public ResponseEntity<String> setFinancialYear(HttpServletRequest request,@RequestBody Map<String, Object> requestMap){
			try {
				String token=request.getHeader("Authorization").split(" ")[1];
				
				
				  Claims claims= jwtUtil.extractAllClaims(token);
				  
				  LinkedHashMap<String,Object> map=claims.get("user",LinkedHashMap.class);
				  
				  String registerId=map.get("registerId").toString();
				  String financialYear=requestMap.get("financialYear").toString();
				  
				  invoiceService.setFinancialYearByRegisterId(registerId, financialYear);
				  
				  return new ResponseEntity<String>("success",HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<String>("error",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@PostMapping(value = "/saveClient")
		@CrossOrigin(origins = {"*"})
		public String saveClient(@RequestBody ClientDO clientDO) throws IOException {
			
			
//			 String base64UrlEncodedData = clientDO.getLogo().toString();
//		     byte[] decodedBytes = Base64.getUrlDecoder().decode(base64UrlEncodedData);
			try {
			String filePath=null;
			
			Path path;
			
			if(clientDO.getLogo().length>0) {
			 filePath=fileUploadPath+seperator+clientDO.getClientId()+seperator+"LOGO.jpg";
			 // Convert the byte array to a Path object
             path = Paths.get(filePath);
             
             Files.createDirectories(path.getParent());

            // Write the byte array to the file
            Files.write(path, clientDO.getLogo());
			}
            
            
			if(clientDO.getSignature().length>0) {
            
            filePath=fileUploadPath+seperator+clientDO.getClientId()+seperator+"SIGNATURE.jpg";
            path = Paths.get(filePath);
            Files.createDirectories(path.getParent());
            Files.write(path, clientDO.getSignature());
			}
			}catch(Exception e) {
				LOGGER.error("Exception in InvoiceController::saveInvoice()::"+e);
				
				System.out.println("Exception in InvoiceController::saveInvoice()::"+e);
			}
            
			
			return invoiceService.saveClient(clientDO);
		}
		
		@GetMapping(value = "/users")
		@CrossOrigin(origins= {"*"})
		public ResponseEntity<?> getUserList(){
			List<UserDO> userList=invoiceService.getUserList();
			
			if(userList!=null) {
				return new ResponseEntity<List<UserDO>>(userList,HttpStatus.OK);
			}
			

			JSONObject jsonObject=new JSONObject();
			jsonObject.put("res", "Users not found");
			return new ResponseEntity<String>(jsonObject.toString(),HttpStatus.OK);
		}
		
		@PostMapping(value = "/invoiceidbyno")
		@CrossOrigin(origins= {"*"})
		public String getInvoiceIdByInvoiceNo(@RequestBody String invoiceNumber){
			String res=invoiceService.getInvoiceIdByInvoiceNumber(invoiceNumber);
			
			return res;
		}
		
		@GetMapping(value = "/clientValidity")
		@CrossOrigin(origins= {"*"})
		public String checkClientValidity(HttpServletRequest request){
			String token=request.getHeader("Authorization").split(" ")[1];
			
			
			  Claims claims= jwtUtil.extractAllClaims(token);
			  
			  LinkedHashMap<String,Object> map=claims.get("user",LinkedHashMap.class);
			  
			  String registerId=map.get("registerId").toString();
			  
			  boolean res=invoiceService.checkClientValidity(registerId);
			  
			  if(res) return "true";
			  
			  return "false";
					  
					 
		}
		
		
		
}

package com.accurate.erp.action.invoice;

import org.springframework.http.MediaType;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
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

import com.accurate.erp.dto.security.AuthenticationRequest;
import com.accurate.erp.helper.JwtUtil;
import com.accurate.erp.model.invoice.CustomerDO;
import com.accurate.erp.model.invoice.EmployeeDO;
import com.accurate.erp.model.invoice.InvoiceDO;
import com.accurate.erp.model.invoice.ProductDO;
import com.accurate.erp.model.modelmaster.DocumentSeqMasterDO;
import com.accurate.erp.model.purchase.PurchaseDO;
import com.accurate.erp.model.purchase.SupplierDO;
import com.accurate.erp.security.service.CustomUserDetailsService;
import com.accurate.erp.service.excel.ExcelService;
import com.accurate.erp.service.invoice.InvoiceService;
import com.accurate.erp.model.invoice.InvoiceProductDO;

@RestController
public class InvoiceController {
	private final static Logger LOGGER=LoggerFactory.getLogger(InvoiceController.class);
	private final static String [] month = {"Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec","Jan","Feb","Mar"};
	
	@Autowired
	InvoiceService invoiceService;
	
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
	public ResponseEntity<?> getAllInvoiceList(){
		List<InvoiceDO> invoiceList=invoiceService.getInvoiceList();
		if(invoiceList!=null && invoiceList.size()>0) {
		return new ResponseEntity<List<InvoiceDO>>(invoiceList,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Invoices are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@GetMapping("/purchases")
	@CrossOrigin(origins = {"*"})
	public ResponseEntity<?> getAllPurchase(){
		List<PurchaseDO> list=invoiceService.getPurchaseList();
		
		if(list!=null && list.size()>0) {
			return new ResponseEntity<List<PurchaseDO>>(list,HttpStatus.OK);
		}else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "purchases not found");
			return ResponseEntity.ok(jsonObj.toString());
		}
	}
	
	@GetMapping("/purchase/{id}")
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
	/*
	 * @GetMapping("/purchases")
	 * 
	 * @CrossOrigin(origins = {"*"}) public ResponseEntity<?> getPurchase(){
	 * List<PurchaseDO> list=invoiceService.getPurchaseList();
	 * 
	 * if(list!=null && list.size()>0) { return new
	 * ResponseEntity<List<PurchaseDO>>(list,HttpStatus.OK); }else { JSONObject
	 * jsonObj=new JSONObject(); jsonObj.put("res", "purchases not found"); return
	 * ResponseEntity.ok(jsonObj.toString()); } }
	 */
	
	@GetMapping("/purchases/{month}")
	@CrossOrigin(origins = {"*"})
	public ResponseEntity<?> getPurchasesByMonth(@PathVariable String month){
		
		List<PurchaseDO> list=invoiceService.getPurchaseList(month);
		
		if(list!=null && list.size()>0) {
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
	
	
	@GetMapping(value="/invoices/{month}")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getInvoiceList(@PathVariable String month){
		List<InvoiceDO> invoiceList=invoiceService.getInvoiceListByMonth(month);
		if(invoiceList!=null && invoiceList.size()>0) {
		return new ResponseEntity<List<InvoiceDO>>(invoiceList,HttpStatus.OK);
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
	public ResponseEntity<?> saveInvoice(@RequestBody Map<String, Object> inputJson) throws ParseException{
		
		System.out.print(inputJson);
		
		String msg=invoiceService.saveInvoice(inputJson);
		
		JSONObject jsonObj=new JSONObject();
		if(msg.equals("success")) {
		jsonObj.put("res", "success");
		}else {
			jsonObj.put("res", "failure");
		}
		return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);

		
		
	}
	
	
	@GetMapping(value="/viewSalesReg")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getviewSalesReg(){
		List<InvoiceDO> invoiceList=invoiceService.getInvoiceList();
		if(invoiceList!=null && invoiceList.size()>0) {
			
			/*invoiceList.forEach((ele) ->{
				
			});*/
			List<String> salesobj = new ArrayList<>();
			for(String str  : month) {
				JSONObject jsonObj=new JSONObject();
				Integer totalInv = 0;
				BigDecimal closingBal = new BigDecimal(0);
				BigDecimal amount = new BigDecimal(0);
				for(InvoiceDO invdo : invoiceList) {
					if(str.equalsIgnoreCase(invdo.getMonth())) {
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
	
	@GetMapping(value = "/getPurchases")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getPurchaseList(){
		List<PurchaseDO> purchaseList=invoiceService.getPurchaseList();
		if(purchaseList!=null && purchaseList.size()>0) {
			
			/*invoiceList.forEach((ele) ->{
				
			});*/
			List<String> salesobj = new ArrayList<>();
			for(String str  : month) {
				JSONObject jsonObj=new JSONObject();
				Integer totalInv = 0;
				BigDecimal closingBal = new BigDecimal(0);
				BigDecimal amount = new BigDecimal(0);
				for(PurchaseDO purchaseDO : purchaseList) {
					if(str.equalsIgnoreCase(purchaseDO.getMonth())) {
						totalInv = totalInv + 1;
						amount = amount.add(new BigDecimal(purchaseDO.getInvoiceValue()));
						closingBal = closingBal.add(new BigDecimal(1));
					}
				}
				jsonObj.put("month", str);
				jsonObj.put("totalInv", totalInv);
				jsonObj.put("amount", amount);
				jsonObj.put("closingBal", closingBal);
				if(totalInv>0)
				jsonObj.put("progress", amount.divide(new BigDecimal(totalInv)).multiply(new BigDecimal(100)));
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
	
	@GetMapping(value="/deletePurchase/{id}")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> deletePurchase(@PathVariable String purchaseId){
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
	public ResponseEntity<?> getDeleteInvoice(@QueryParam("invNo") String invNo){
		boolean flag=invoiceService.DeleteInvoice(invNo);
		
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
	
	@GetMapping(value="/sendmail")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> sendMail(@QueryParam("invNo") String invNo , @QueryParam("custName") String custName ){
		boolean flag=false;
				flag = invoiceService.sendMail(invNo , custName);
		
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
	
	@GetMapping("/employee")
	@CrossOrigin(origins = {"*"})
	public ResponseEntity<?> test(@RequestBody EmployeeDO employee){
		return new ResponseEntity<EmployeeDO>(employee,HttpStatus.OK);
	}
	
	//add customer details code start
	
	@PostMapping(value="/addCustomerDetails",consumes= {"application/json"})
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> saveCustomer(@RequestBody Map<String, Object> inputJson) throws ParseException{
		
		System.out.print(inputJson);
		
		String msg=invoiceService.saveCustomer(inputJson);
		
		JSONObject jsonObj=new JSONObject();
		if(msg.equals("success")) {
		jsonObj.put("res", "success");
		}else {
			jsonObj.put("res", "failure");
		}
		return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
	}
	// add customer details code end
	
	//add customer details code start
	
		@PostMapping(value="/addProductDetails",consumes= {"application/json"})
		@CrossOrigin(origins={"*"})
		public ResponseEntity<?> saveProduct(@RequestBody Map<String, Object> inputJson) throws ParseException{
			
			System.out.print(inputJson);
			
			String msg=invoiceService.saveProduct(inputJson);
			
			JSONObject jsonObj=new JSONObject();
			if(msg.equals("success")) {
			jsonObj.put("res", "success");
			}else {
				jsonObj.put("res", "failure");
			}
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
		// add customer details code end
		
	
	
}

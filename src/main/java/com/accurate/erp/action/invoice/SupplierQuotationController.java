package com.accurate.erp.action.invoice;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.accurate.erp.helper.JwtUtil;
import com.accurate.erp.model.invoice.InvoiceDO;
import com.accurate.erp.model.invoice.QuotationDO;
import com.accurate.erp.model.invoice.SupplierQuotationDO;
import com.accurate.erp.security.service.CustomUserDetailsService;
import com.accurate.erp.service.excel.ExcelService;
import com.accurate.erp.service.invoice.InvoiceService;
import com.accurate.erp.service.invoice.QuotationService;
import com.accurate.erp.service.invoice.SupplierQuotationService;

import io.jsonwebtoken.Claims;

@RestController
public class SupplierQuotationController {
	
	private final static Logger LOGGER=LoggerFactory.getLogger(QuotationController.class);
	private final static String [] month = {"Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec","Jan","Feb","Mar"};
	private final static String[] completeMonth= {"April","May","June","July","August","Sepetember","October","November","December","January","February","March"};
	
	@Autowired
	SupplierQuotationService invoiceService;
	
	@Autowired
	ExcelService excelService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	CustomUserDetailsService userDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@GetMapping(value="/supplierQuotations/year/{financialYear}")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getInvoiceList(@PathVariable String financialYear){
		List<SupplierQuotationDO> invoiceDO=invoiceService.getInvoiceByFinancialYear(financialYear);
		if(invoiceDO!=null) {
			invoiceDO.forEach(invoice->{
				invoice.setIncludeChildren(false);
			});
		return new ResponseEntity<List<SupplierQuotationDO>>(invoiceDO,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Quotation are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/allSupplierQuotations")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getAllInvoiceList(@RequestBody Map<String,String> map){
		
		String financialYear=map.get("financialYear");
		List<SupplierQuotationDO> invoiceList=invoiceService.getInvoiceList(financialYear);
		if(invoiceList!=null && invoiceList.size()>0) {
			invoiceList.forEach(invoice->{
				invoice.setIncludeChildren(false);
			});
		return new ResponseEntity<List<SupplierQuotationDO>>(invoiceList,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Quotations are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/getSupplierQuoNO")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getInvNo(){
		String invNo=invoiceService.getInvNo();
		if(invNo!=null && !invNo.equalsIgnoreCase("")) {
		return new ResponseEntity<String>(invNo,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Quotations are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@PostMapping(value="/supplierQuotations/{month}")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getInvoiceListByMonth(@PathVariable String month,@RequestBody Map<String,String> map){
		
		String financialYear=map.get("financialYear");
		List<SupplierQuotationDO> invoiceDO=invoiceService.getInvoiceListByMonth(month.substring(0,3),financialYear);
		if(invoiceDO!=null) {	
			invoiceDO.forEach(invoice->{
				invoice.setIncludeChildren(false);
			});
		return new ResponseEntity<List<SupplierQuotationDO>>(invoiceDO,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Quotations are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@PostMapping(value="/saveSupplierQuotation",consumes= {"application/json"})
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> saveQuotation(@RequestBody Map<String, Object> inputJson,HttpServletRequest request) throws ParseException{
		
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
	
	@PostMapping(value="/viewSalesRegSupplierQuotation")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getviewSalesReg(@RequestBody Map<String,String> map){
		
		String financialYear=map.get("financialYear");
		List<SupplierQuotationDO> invoiceList=invoiceService.getInvoiceList(financialYear);
		if(invoiceList!=null && invoiceList.size()>0) {
			
			/*invoiceList.forEach((ele) ->{
				
			});*/
			List<String> salesobj = new ArrayList<>();
			for(String str  : completeMonth) {
				JSONObject jsonObj=new JSONObject();
				Integer totalInv = 0;
				BigDecimal closingBal = new BigDecimal(0);
				BigDecimal amount = new BigDecimal(0);
				for(SupplierQuotationDO invdo : invoiceList) {
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
			jsonObj.put("res", "Quotations are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/viewSupplierQuotation")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getInvoiceDetails(@QueryParam("invId") String invId){
		SupplierQuotationDO invoicedo=invoiceService.getInvoiceDetails(invId);
		if(invoicedo!=null) {
		return new ResponseEntity<SupplierQuotationDO>(invoicedo,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Quotation Details are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@PostMapping(value="/sendmailSupplierQuotation")
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
	
	@GetMapping(value = "/cancelSupplierQuotation")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> cancelInvoice(@RequestParam("QuoId") String invoiceId){
		
		
		
		boolean flag=invoiceService.cancelInvoiceById(Integer.parseInt(invoiceId));
		JSONObject jsonObj=new JSONObject();
		if(flag==true) {
			jsonObj.put("res", "success");
		}else {
			jsonObj.put("res", "failure");
		}
		
		return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
	}
	
	@GetMapping(value="/deleteSupplierQuo")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getDeleteInvoice(@RequestParam("QuoId") String invoiceId){
		String className=null;
			
		
		boolean flag=invoiceService.DeleteInvoice(invoiceId);
		
		JSONObject jsonObj=new JSONObject();
		
		if(flag) {
			jsonObj.put("res", "sucess");
		}else {
			jsonObj.put("res", "failure");
		}
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
	}
	
	
	
	


}
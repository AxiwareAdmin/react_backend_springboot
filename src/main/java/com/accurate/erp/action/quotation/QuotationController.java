package com.accurate.erp.action.quotation;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.accurate.erp.action.invoice.InvoiceController;
import com.accurate.erp.dto.security.AuthenticationRequest;
import com.accurate.erp.helper.JwtUtil;
import com.accurate.erp.model.invoice.CustomerDO;
import com.accurate.erp.model.modelmaster.DocumentSeqMasterDO;
import com.accurate.erp.model.quotation.QuotationDO;
import com.accurate.erp.security.service.CustomUserDetailsService;
import com.accurate.erp.service.excel.ExcelService;
import com.accurate.erp.service.quotation.QuotationService;

@RestController
public class QuotationController {
	
	private final static Logger LOGGER=LoggerFactory.getLogger(QuotationController.class);
	private final static String [] month = {"Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec","Jan","Feb","Mar"};
	
	@Autowired
	QuotationService quotationService;
	
	@Autowired
	ExcelService excelService;
	
	
	@GetMapping(value="/allQuotations")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getAllQuotationList(){
		List<QuotationDO> invoiceList=quotationService.getInvoiceList();
		if(invoiceList!=null && invoiceList.size()>0) {
		return new ResponseEntity<List<QuotationDO>>(invoiceList,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Quotations are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	@GetMapping(value="/getQuoNo")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getQuoNo(){
		String invNo=quotationService.getInvNo();
		if(invNo!=null && !invNo.equalsIgnoreCase("")) {
		return new ResponseEntity<String>(invNo,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Quotations are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	
	@GetMapping(value="/quotations/{month}")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getQuotationList(@PathVariable String month){
		List<QuotationDO> invoiceList=quotationService.getInvoiceListByMonth(month);
		if(invoiceList!=null && invoiceList.size()>0) {
		return new ResponseEntity<List<QuotationDO>>(invoiceList,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Quotations are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	
	
//	@GetMapping(value="/quotationproducts")
//	@CrossOrigin(origins={"*"})
//	public ResponseEntity<?> getQuotationProducts(){
//		List<ProductDO> invoiceProductList=quotationService.getInvoiceProductList();
//		if(invoiceProductList!=null && invoiceProductList.size()>0) {
//		return new ResponseEntity<List<ProductDO>>(invoiceProductList,HttpStatus.OK);
//		}
//		else {
//			JSONObject jsonObj=new JSONObject();
//			jsonObj.put("res", "Invoice products are not found");
//			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
//		}
//	}
	
//	@GetMapping(value="/quotationproduct/{id}")
//	@CrossOrigin(origins={"*"})
//	public ResponseEntity<?> getQuotationProducts(@PathVariable Integer id){
//		ProductDO prodDO=quotationService.getProductById(id);
//		if(prodDO!=null) {
//		return new ResponseEntity<ProductDO>(prodDO,HttpStatus.OK);
//		}
//		else {
//			JSONObject jsonObj=new JSONObject();
//			jsonObj.put("res", "Invoice products are not found");
//			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
//		}
//	}

	@PostMapping(value="/saveQuotation",consumes= {"application/json"})
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> saveQuotation(@RequestBody Map<String, Object> inputJson) throws ParseException{
		
		System.out.print(inputJson);
		
		String msg=quotationService.saveInvoice(inputJson);
		
		JSONObject jsonObj=new JSONObject();
		if(msg.equals("success")) {
		jsonObj.put("res", "success");
		}else {
			jsonObj.put("res", "failure");
		}
		return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);

		
		
	}
	
	
	@GetMapping(value="/viewSalesRegquotation")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getviewSalesReg(){
		List<QuotationDO> invoiceList=quotationService.getInvoiceList();
		if(invoiceList!=null && invoiceList.size()>0) {
			
			/*invoiceList.forEach((ele) ->{
				
			});*/
			List<String> salesobj = new ArrayList<>();
			for(String str  : month) {
				JSONObject jsonObj=new JSONObject();
				Integer totalInv = 0;
				BigDecimal closingBal = new BigDecimal(0);
				BigDecimal amount = new BigDecimal(0);
				for(QuotationDO invdo : invoiceList) {
					if(str.equalsIgnoreCase(invdo.getMonth())) {
						totalInv = totalInv + 1;
						amount = amount.add(invdo.getQuotationValue());
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
	
	@GetMapping(value="/viewquotation")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getQuotationDetails(@QueryParam("invId") String invId){
		QuotationDO QuotationDO=quotationService.getInvoiceDetails(invId);
		if(QuotationDO!=null) {
		return new ResponseEntity<QuotationDO>(QuotationDO,HttpStatus.OK);
		}
		else {
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("res", "Quotations Details are not found");
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/deleteQuotation")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> getDeleteQuotation(@QueryParam("invNo") String invNo){
		boolean flag=quotationService.DeleteInvoice(invNo);
		
		JSONObject jsonObj=new JSONObject();
		
		if(flag) {
			jsonObj.put("res", "sucess");
		}else {
			jsonObj.put("res", "failure");
		}
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
	}
	
	@GetMapping(value="/cloneQuotation")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> cloneQuotation(@QueryParam("invNo") String invNo){
		boolean flag=false;
				flag = quotationService.cloneInvoice(invNo);
		
		JSONObject jsonObj=new JSONObject();
		
		if(flag) {
			jsonObj.put("res", "sucess");
		}else {
			jsonObj.put("res", "failure");
		}
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
	}
	
	@PostMapping("/excel/quotation")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> downloadQuotationInExcel(@RequestBody Map<String,String> data){
		String filename = "Quotations.xlsx";
		System.out.println(data);
	    InputStreamResource file = new InputStreamResource(excelService.load(data));
	    
	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	            .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
	            .body(file);
	}
	
		@GetMapping(value="/sendmailQuotation")
	@CrossOrigin(origins={"*"})
	public ResponseEntity<?> sendMail(@QueryParam("invNo") String invNo , @QueryParam("custName") String custName ){
		boolean flag=false;
				flag = quotationService.sendMail(invNo , custName);
		
		JSONObject jsonObj=new JSONObject();
		
		if(flag) {
			jsonObj.put("res", "sucess");
		}else {
			jsonObj.put("res", "failure");
		}
			return new ResponseEntity<String>(jsonObj.toString(),HttpStatus.OK);
	}
		
}

package com.accurate.erp.dao.invoice;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.accurate.erp.action.invoice.InvoiceController;
import com.accurate.erp.filter.CustomValidationFilter;
import com.accurate.erp.model.invoice.CashDO;
import com.accurate.erp.model.invoice.CustomerDO;
import com.accurate.erp.model.invoice.InvoiceDO;
import com.accurate.erp.model.invoice.InvoiceProductDO;
import com.accurate.erp.model.invoice.ProformaInvoiceDO;
import com.accurate.erp.model.invoice.QuotationDO;
import com.accurate.erp.model.invoice.QuotationProductDO;
import com.accurate.erp.model.invoice.SupplierQuotationDO;
import com.accurate.erp.model.invoice.SupplierQuotationProductsDO;
import com.accurate.erp.model.modelmaster.DocumentSeqMasterDO;

@Repository
public class SupplierQuotationDAO {
	
	private final static Logger LOGGER = LogManager.getLogger(SupplierQuotationDAO.class);

	@Autowired
	SessionFactory sessionFactory;
	
	
	public Integer getFullYear(Date d){
        return d.getYear()+1900;
    }
    public  String getCurrentFinancialYear() {
		String fiscalyear = "";
		Date today = new Date();
		if (today.getMonth() + 1 <= 3) {
		  fiscalyear = getFullYear(today) - 1 + "-" + getFullYear(today).toString().substring(2);
		} else {
		  fiscalyear = getFullYear(today) + "-" + (getFullYear(today) + 1)+"".substring(2);
		}
		return fiscalyear;
	  }
	
    @SuppressWarnings("deprecation")
	@Transactional
	public SupplierQuotationDO getInvoiceDetails(String invId) {
		LOGGER.info("SupplierQuotationDAO :: getInvoiceDetails :: Start ");
		SupplierQuotationDO invDO = null;
		try {

			Session session = getSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<SupplierQuotationDO> query = builder.createQuery(SupplierQuotationDO.class);

			Root<SupplierQuotationDO> root = query.from(SupplierQuotationDO.class);

			query.select(root);

			Predicate predicate = builder.equal(root.get("invoiceId"), invId);

			query.where(predicate);

			invDO = session.createQuery(query).getSingleResult();
			/*
			 * Criteria criteria=session.createCriteria(InvoiceDO.class);
			 * criteria.add(Restrictions.eq("invoiceNo",invNo));
			 * invDO=(InvoiceDO)criteria.uniqueResult();
			 */

		} catch (Exception e) {
			LOGGER.error("Exception occured in SupplierQuotationDAO :: getInvoiceDetails ");
		}
		LOGGER.info("SupplierQuotationDAO :: getInvoiceDetails method end");
		return invDO;
	}
    
    public String saveInvoice(SupplierQuotationDO invoiceDO) {
		LOGGER.info("SupplierQuotationDAO::saveInvoice::start");

		try {
			SupplierQuotationDO tmp = invoiceDO;
			SupplierQuotationDO invDO = null;
			invoiceDO.setInvoiceProductId(1);
			Calendar cal = Calendar.getInstance();
			invoiceDO.setMonth(new SimpleDateFormat("MMM").format(cal.getTime()));
			invoiceDO.setCity("Mum");
			invoiceDO.setIgstValue(new BigDecimal(1));
			Session session=getSession();
			
//			Transaction tx=session.beginTransaction();
			session.saveOrUpdate(invoiceDO);
//			session.flush();
//			tx.commit();

		} catch (Exception e) {
			LOGGER.info("Exception occured in SupplierQuotationDAO::saveInvoice::" + e);
			return "failure";
		}

		LOGGER.info("SupplierQuotationDAO::saveInvoice()::end");
		return "success";
	}
    
    public List<SupplierQuotationDO> getInvoiceList(String financialYear) {
		LOGGER.info("SupplierQuotationDAO :: getInvoiceList :: Start ");
		List<SupplierQuotationDO> invoiceList = new ArrayList<SupplierQuotationDO>();
		try {

			Session session = getSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<SupplierQuotationDO> query = builder.createQuery(SupplierQuotationDO.class);

			Root<SupplierQuotationDO> root = query.from(SupplierQuotationDO.class);
			
			Predicate pred=builder.equal(root.get("financialYear"), financialYear);

			 String registerId=CustomValidationFilter.getCurrentRegisterId();
				
				Predicate predicate3=builder.equal(root.get("registerId"), registerId);
	
			query.select(root);
			
			query.where(builder.and(pred,predicate3));

			invoiceList = session.createQuery(query).getResultList();

			/*
			 * Criteria criteria=session.createCriteria(InvoiceDO.class);
			 * invoiceList=criteria.list();
			 */

		} catch (Exception e) {
			LOGGER.error("Exception occured in SupplierQuotationDAO :: getInvoiceList ");
		}
		LOGGER.info("SupplierQuotationDAO :: getInvoiceList method end");
		return invoiceList;
	}
    
    public List<SupplierQuotationDO> getInvoiceList(Map<String, String> data) {
		LOGGER.info("SupplierQuotationDAO :: getInvoiceList :: Start ");
		List<SupplierQuotationDO> invoiceList = new ArrayList<SupplierQuotationDO>();
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
			String customerName = data.get("customerName");

			Date toDate = sdf.parse(data.get("toDate"));
			
			toDate=sdf2.parse(sdf2.format(toDate));

			Date fromDate = sdf.parse(data.get("fromDate"));
			
			fromDate=sdf2.parse(sdf2.format(fromDate));

			String status = data.get("status");

			String category = data.get("category");
			
			String userId=data.get("userId");

			Session session = getSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<SupplierQuotationDO> query = builder.createQuery(SupplierQuotationDO.class);

			Root<SupplierQuotationDO> root = query.from(SupplierQuotationDO.class);

			query.select(root);

			Predicate customerPredicate = builder.equal(root.get("customerName"), customerName);

			Predicate datePredicate = builder.between(root.get("invoiceDate"), fromDate, toDate);

			Predicate statusPredicate = builder.equal(root.get("invoiceStatus"), status);

			Predicate userPredicate=builder.equal(root.get("userId"), userId);
			Predicate[] predicate = new Predicate[3];
			List<Predicate> predList = new ArrayList<>();
			
			 int predCount=0;
			  
			  if(customerName!=null && customerName.length()>0) {
				  predList.add(customerPredicate);
			  }
			  
			  if(fromDate!=null && toDate!=null) { 
				  predList.add(datePredicate);
			  }
			 
			  if(status!=null && status.length()>0) { 
				  predList.add(statusPredicate);
			   }
			 
			  if(userId!=null && userId.length()>0) {
				  predList.add(userPredicate);
			  }

			Predicate finalPred = builder.and(predList.toArray(new Predicate[0]));

			query.where(finalPred);

//			Predicate categoryPredicate=builder.equal(root.get(null), statusPredicate)

			invoiceList = session.createQuery(query).getResultList();

			/*
			 * Criteria criteria=session.createCriteria(InvoiceDO.class);
			 * invoiceList=criteria.list();
			 */

		} catch (Exception e) {
			LOGGER.error("Exception occured in SupplierQuotationDAO :: getInvoiceList ");
		}
		LOGGER.info("SupplierQuotationDAO :: getInvoiceList method end");
		return invoiceList;
	}
    
    public String getInvNo(){
		LOGGER.info("SupplierQuotationDAO :: getInvNo :: Start ");
		String invNo="";
		DocumentSeqMasterDO documentSeqMasterDO = null;
		InvoiceDO invoiceDO = null;
		String tempInv = "";
		List<String> temp;
		try {
			
			Session session=getSession();
			
			CriteriaBuilder builder=session.getCriteriaBuilder();
			
			CriteriaQuery<DocumentSeqMasterDO> query=builder.createQuery(DocumentSeqMasterDO.class);
			
			Root<DocumentSeqMasterDO> root=query.from(DocumentSeqMasterDO.class);
			
			query.select(root);
			
			Predicate predicate=builder.equal(root.get("documentName"), "Quotation");
			
			query.where(predicate);
			
			documentSeqMasterDO=session.createQuery(query).getSingleResult();
			
			/*
			 * Criteria criteria=session.createCriteria(DocumentSeqMasterDO.class);
			 * criteria.add(Restrictions.eq("documentName","Invoice"));
			 * documentSeqMasterDO=(DocumentSeqMasterDO) criteria.uniqueResult();
			 */
			
			if(documentSeqMasterDO != null) {
			String matchVal = "%"+documentSeqMasterDO.getPrefix1() +"/"+documentSeqMasterDO.getPrefix2()+"/%";
			Session session1=getSession();
			
			CriteriaQuery<String> query1=builder.createQuery(String.class);
			
			Root<SupplierQuotationDO> root2=query1.from(SupplierQuotationDO.class);
			query1.select(root2.get("invoiceNo"));
			
			query1.orderBy(builder.desc(root2.get("invoiceNo")));
			
			Predicate predicate1=builder.like(root2.get("invoiceNo"), matchVal);
			
			query1.where(predicate1);
			
			
			temp=session.createQuery(query1).getResultList();
            if(!temp.isEmpty())
			tempInv = temp.get(0);
			
			}
			System.out.println("max invoice no :"+tempInv);
			if(tempInv != null && !tempInv.equalsIgnoreCase("")) {
				int len = (tempInv.split("/")[2]).length();
				Integer nextInvNo = Integer.parseInt((tempInv.split("/")[2]))+1;
				invNo = documentSeqMasterDO.getPrefix1() +"/"+documentSeqMasterDO.getPrefix2()+"/"+String.format("%04d", nextInvNo);
			}else {
				invNo = documentSeqMasterDO.getPrefix1() +"/"+documentSeqMasterDO.getPrefix2()+"/"+documentSeqMasterDO.getSeries();
			}
			
			return invNo;
			
			
		}catch(Exception e) {
			LOGGER.error("Exception occured in SupplierQuotationDAO :: getInvNo "+e);
		}
		LOGGER.info("SupplierQuotationDAO :: getInvNo method end");
		return invNo;
	}
    
    public List<SupplierQuotationDO> getInvoiceListByMonth(String month,String financialYear) {
		LOGGER.info("SupplierQuotationDAO :: getInvoiceListByMonth :: Start ");
		List<SupplierQuotationDO> invoiceList = new ArrayList<SupplierQuotationDO>();
		try {

			Session session = getSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<SupplierQuotationDO> query = builder.createQuery(SupplierQuotationDO.class);

			Root<SupplierQuotationDO> root = query.from(SupplierQuotationDO.class);

			query.select(root);

			Predicate predicate1 = builder.equal(root.get("month"), month);
			
			Predicate predicate2 = builder.equal(root.get("financialYear"), financialYear);

			 String registerId=CustomValidationFilter.getCurrentRegisterId();
				
				Predicate predicate3=builder.equal(root.get("registerId"), registerId);
	
			query.where(builder.and(predicate1,predicate2,predicate3));

			Order order = builder.asc(root.get("invoiceDate"));

			query.orderBy(order);

			invoiceList = session.createQuery(query).getResultList();


		} catch (Exception e) {
			LOGGER.error("Exception occured in SupplierQuotationDAO :: getInvoiceListByMonth ");
		}
		LOGGER.info("SupplierQuotationDAO :: getInvoiceListByMonth method end");
		return invoiceList;
	}
    
    @Transactional
	public boolean DeleteInvoice(String invId) {
		LOGGER.info("SupplierQuotationDAO :: DeleteInvoice :: Start ");
		boolean flag = false;
		try {

			Session session = getSession();
			
			//String idName=className.equalsIgnoreCase("proforma_invoice")?"pi_id":"Invoice_id";
			
			Query query = session.createNativeQuery("delete from supplier_quotation where Quotation_Id ='" + invId + "'");
			
			query.executeUpdate();
			flag = true;

		} catch (Exception e) {
			LOGGER.error("Exception occured in SupplierQuotationDAO :: DeleteInvoice ");
			flag = false;
		}
		LOGGER.info("SupplierQuotationDAO :: DeleteInvoice method end");
		return flag;
	}
    
    public boolean cloneInvoice(String invNo) {
		LOGGER.info("SupplierQuotationDAO :: cloneInvoice :: Start ");
		boolean flag=false;
		SupplierQuotationDO invDO= new SupplierQuotationDO();
		List<SupplierQuotationDO> invtemp = new ArrayList<>();
		Integer gInvNo = 0;
		String fInvNo="";
		try {
			
//			invtemp = getInvoiceList();
//			gInvNo = invtemp.size();
			
			Session session=getSession();
			
			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<SupplierQuotationDO> query = builder.createQuery(SupplierQuotationDO.class);

			Root<SupplierQuotationDO> root = query.from(SupplierQuotationDO.class);

			query.select(root);

			Predicate predicate = builder.equal(root.get("invoiceId"), invNo);

			query.where(predicate);
			
			invDO=session.createQuery(query).getResultList().get(0);
			
			/*
			 * Criteria criteria=session.createCriteria(InvoiceDO.class);
			 * criteria.add(Restrictions.eq("invoiceNo",invNo)); invDO =
			 * (InvoiceDO)criteria.list().get(0);
			 */
			
			fInvNo = getInvNo();
			invDO.setInvoiceNo(fInvNo);
					
			List<SupplierQuotationProductsDO> productdo = new ArrayList<SupplierQuotationProductsDO>();
			for(SupplierQuotationProductsDO temp : invDO.getInvoiceProductDO()) {
				SupplierQuotationProductsDO invprod = new SupplierQuotationProductsDO();
				invprod = temp;
				invprod.setInvoiceDO(invDO);
				invprod.setInvoiceProductId(null);
				productdo.add(invprod);
			}
			invDO.getInvoiceProductDO().clear();
			invDO.setInvoiceProductDO(productdo);
			
			/*
			 * session.clear(); closeSession(session);
			 */
			
			closeSession(session);
			
			flag  = saveCloneInv(invDO);
			

		} catch (Exception e) {
			LOGGER.error("Exception occured in SupplierQuotationDAO :: cloneInvoice ");
			flag = false;
		}
		LOGGER.info("SupplierQuotationDAO :: cloneInvoice method end");
		return flag;
	}
    
    public boolean saveCloneInv(SupplierQuotationDO invdo) {

		LOGGER.info("SupplierQuotationDAO::saveCloneInv::start");
		try {

//			Session session = getSession();
			Session session=sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(invdo);
			session.flush();
			tx.commit();
			session.close();

		} catch (Exception e) {
			LOGGER.info("Exception occured in SupplierQuotationDAO::saveCloneInv::" + e);
			return false;
		}

		LOGGER.info("SupplierQuotationDAO::saveCloneInv::end");
		return true;

	}
    
public String getCustomerEmail(String custName) {
		
        LOGGER.info("SupplierQuotationDAO::getCustomerEmail::start");
        String email = "";
		try {
			
			Session session=getSession();	
			
			CriteriaBuilder criteriaBuilder=session.getCriteriaBuilder();
			
			CriteriaQuery<String> query=criteriaBuilder.createQuery(String.class);
			
			Root<CustomerDO> root=query.from(CustomerDO.class);
			
			query.select(root.get("email"));
			
			Predicate predicate=criteriaBuilder.equal(root.get("customerName"), custName);
			
			query.where(predicate);
			
			email=session.createQuery(query).getSingleResult();
			
			
		}catch(Exception e) {
			LOGGER.info("Exception occured in SupplierQuotationDAO::getCustomerEmail::"+e);
			return email;
		}
		
		LOGGER.info("SupplierQuotationDAO::getCustomerEmail::end");
		return email;
		
	}

public boolean cancelInvoiceById(Integer invoiceId) {
	LOGGER.info("SupplierQuotationDAO::calcelInvoice()::start");
	boolean flag=false;
	
	try {
		
		Session session=getSession();
		
		
		SupplierQuotationDO invoiceDO=getInvoiceDetails(invoiceId.toString());
		
		//Object invoiceDO=getSalesTypeClassDetails(className, invoiceId.toString());
		
		String[] methodNames1 = {"setAdditionalCharges","setTransportCharges"};
		
		for(String methodName:methodNames1) {
			   Method method = invoiceDO.getClass().getMethod(methodName,String.class);
	            
	            method.invoke(invoiceDO,"0");
	            
		}
		
		String[] methodNames2 = {"setDiscount","setOtherDiscount","setSgstValue",
				"setCgstValue","setIgstValue","setTaxableValue","setInvoiceValue"};
		
		
		for(String methodName:methodNames2) {
			   Method method = invoiceDO.getClass().getMethod(methodName,BigDecimal.class);
	            
	            method.invoke(invoiceDO,BigDecimal.ZERO);
	            
		}
		        
        System.out.println("done.");
		
		flag=true;
		
		
	}catch(Exception e) {
		LOGGER.error("Exception in SupplierQuotationDAO::cancelInvoiceById()::"+e);
	}
	
	LOGGER.info("SupplierQuotationDAO::calcelInvoice()::end");
	
	return flag;
	
}

public List<SupplierQuotationDO> getInvoiceByFinancialYear(String financialYear) {
	LOGGER.info("SupplierQuotationDAO::getInvoiceByFinancialYear()::Start");
	List<SupplierQuotationDO> invoiceDO=null;
	
	try {
		Session session=getSession();
		
		CriteriaBuilder criteriaBuilder=session.getCriteriaBuilder();
		
		CriteriaQuery<SupplierQuotationDO> query=criteriaBuilder.createQuery(SupplierQuotationDO.class);
		
		Root<SupplierQuotationDO> root=query.from(SupplierQuotationDO.class);
		
		query.select(root);
		
		Predicate predicate=criteriaBuilder.equal(root.get("financialYear"), financialYear);
		 String registerId=CustomValidationFilter.getCurrentRegisterId();
			
			Predicate predicate3=criteriaBuilder.equal(root.get("registerId"), registerId);

		query.where(criteriaBuilder.and(predicate,predicate3));
		
		invoiceDO=session.createQuery(query).getResultList();
		
	}catch(Exception e) {
		LOGGER.error("Exception in SupplierQuotationDAO::getInvoiceByFinancialYear()::"+e);
	}
	LOGGER.info("SupplierQuotationDAO::getInvoiceByFinancialYear()::End");
	return invoiceDO;
}






		


	


	
	
	public Session getSession() {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (Exception e) {
			session = sessionFactory.openSession();
		}
		return session;
	}

	public void closeSession(Session session) {
		if (session != null && session.isOpen()) {
			session.close();
		}
	}


}
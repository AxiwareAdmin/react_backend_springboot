package com.accurate.erp.dao.quotation;

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

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.accurate.erp.action.invoice.InvoiceController;
import com.accurate.erp.model.invoice.CustomerDO;
import com.accurate.erp.model.invoice.ProductDO;
import com.accurate.erp.model.invoice.UserDO;
import com.accurate.erp.model.modelmaster.DocumentSeqMasterDO;
import com.accurate.erp.model.quotation.QuotationDO;
import com.accurate.erp.model.quotation.QuotationProductDO;
import com.accurate.erp.model.util.StandardTypeDO;

@Repository
public class QuotationDao {
	
	private final static Logger LOGGER = Logger.getLogger(InvoiceController.class);

	@Autowired
	SessionFactory sessionFactory;

	@SuppressWarnings("deprecation")
	@Transactional
//	public List<CustomerDO> getCustometList() {
//		LOGGER.info("QuotationDao :: getCustomerList :: Start ");
//		List<CustomerDO> custList = new ArrayList<CustomerDO>();
//		try {
//
//			Session session = getSession();
//			CriteriaBuilder builder = session.getCriteriaBuilder();
//
//			CriteriaQuery<CustomerDO> criteriaQuery = builder.createQuery(CustomerDO.class);
//
//			Root<CustomerDO> root = criteriaQuery.from(CustomerDO.class);
//
//			criteriaQuery.select(root);
//
//			custList = session.createQuery(criteriaQuery).getResultList();
//
//		} catch (Exception e) {
//			LOGGER.error("Exception occured in QuotationDao :: GetCustomerList ");
//		}
//		LOGGER.info("QuotationDao :: getCustomerList method end");
//		return custList;
//	}
//
//	public CustomerDO getCustomerById(Integer custId) {
//		LOGGER.info("QuotationDao :: getCustomerById :: Start ");
//		CustomerDO customerDO = null;
//		try {
//			Session session = getSession();
//			CriteriaBuilder builder = session.getCriteriaBuilder();
//
//			CriteriaQuery<CustomerDO> criteriaQuery = builder.createQuery(CustomerDO.class);
//
//			Root<CustomerDO> root = criteriaQuery.from(CustomerDO.class);
//
//			criteriaQuery.select(root);
//
//			Predicate predicate = builder.equal(root.get("customerId"), custId);
//
//			criteriaQuery.where(predicate);
//
//			customerDO = session.createQuery(criteriaQuery).getSingleResult();
//
//			/*
//			 * Criteria criteria=session.createCriteria(CustomerDO.class);
//			 * criteria.add(Restrictions.eq("customerId",custId));
//			 * customerDO=(CustomerDO)criteria.uniqueResult();
//			 */
//
//		} catch (Exception e) {
//			LOGGER.error("Exception occured in QuotationDao :: getCustomerById ");
//		}
//		LOGGER.info("QuotationDao :: getCustomerById method end");
//		return customerDO;
//	}
//
//	public ProductDO getProductById(Integer prodId) {
//		LOGGER.info("QuotationDao :: getProductById :: Start ");
//		ProductDO productDO = null;
//		try {
//
//			Session session = getSession();
//
//			CriteriaBuilder builder = session.getCriteriaBuilder();
//
//			CriteriaQuery<ProductDO> query = builder.createQuery(ProductDO.class);
//
//			Root<ProductDO> root = query.from(ProductDO.class);
//
//			query.select(root);
//
//			Predicate predicate = builder.equal(root.get("invoiceProductId"), prodId);
//
//			query.where(predicate);
//
//			productDO = session.createQuery(query).getSingleResult();
//
//			/*
//			 * Criteria criteria=session.createCriteria(ProductDO.class);
//			 * criteria.add(Restrictions.eq("invoiceProductId",prodId));
//			 * productDO=(ProductDO)criteria.uniqueResult();
//			 */
//
//		} catch (Exception e) {
//			LOGGER.error("Exception occured in QuotationDao :: getProductById ");
//		}
//		LOGGER.info("QuotationDao :: getProductById method end");
//		return productDO;
//	}

	public List<QuotationDO> getInvoiceList() {
		LOGGER.info("QuotationDao :: getInvoiceList :: Start ");
		List<QuotationDO> invoiceList = new ArrayList<QuotationDO>();
		try {

			Session session = getSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<QuotationDO> query = builder.createQuery(QuotationDO.class);

			Root<QuotationDO> root = query.from(QuotationDO.class);

			query.select(root);

			invoiceList = session.createQuery(query).getResultList();

			/*
			 * Criteria criteria=session.createCriteria(QuotationDO.class);
			 * invoiceList=criteria.list();
			 */

		} catch (Exception e) {
			LOGGER.error("Exception occured in QuotationDao :: getInvoiceList ");
		}
		LOGGER.info("QuotationDao :: getInvoiceList method end");
		return invoiceList;
	}

	public List<QuotationDO> getInvoiceList(Map<String, String> data) {
		LOGGER.info("QuotationDao :: getInvoiceList :: Start ");
		List<QuotationDO> invoiceList = new ArrayList<QuotationDO>();
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String customerName = data.get("customerName");

			Date toDate = sdf.parse(data.get("toDate"));

			Date fromDate = sdf.parse(data.get("fromDate"));

			String status = data.get("status");

			String category = data.get("category");

			Session session = getSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<QuotationDO> query = builder.createQuery(QuotationDO.class);

			Root<QuotationDO> root = query.from(QuotationDO.class);

			query.select(root);

			Predicate customerPredicate = builder.equal(root.get("customerName"), customerName);

			Predicate datePredicate = builder.between(root.get("invoiceDate"), fromDate, toDate);

			Predicate statusPredicate = builder.equal(root.get("invoiceStatus"), status);

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
			 

			Predicate finalPred = builder.and(predList.toArray(new Predicate[0]));

			query.where(finalPred);

//			Predicate categoryPredicate=builder.equal(root.get(null), statusPredicate)

			invoiceList = session.createQuery(query).getResultList();

			/*
			 * Criteria criteria=session.createCriteria(QuotationDO.class);
			 * invoiceList=criteria.list();
			 */

		} catch (Exception e) {
			LOGGER.error("Exception occured in QuotationDao :: getInvoiceList ");
		}
		LOGGER.info("QuotationDao :: getInvoiceList method end");
		return invoiceList;
	}
	
		public String getInvNo(){
		LOGGER.info("QuotationDao :: getInvNo :: Start ");
		String invNo="";
		DocumentSeqMasterDO documentSeqMasterDO = null;
		QuotationDO QuotationDO = null;
		String tempInv = "";
		List<String> temp;
		try {
			
			Session session=getSession();
			
			CriteriaBuilder builder=session.getCriteriaBuilder();
			
			CriteriaQuery<DocumentSeqMasterDO> query=builder.createQuery(DocumentSeqMasterDO.class);
			
			Root<DocumentSeqMasterDO> root=query.from(DocumentSeqMasterDO.class);
			
			query.select(root);
			
			Predicate predicate=builder.like(root.get("documentName"), "Quotation");
			
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
			
			Root<QuotationDO> root2=query1.from(QuotationDO.class);
			query1.select(root2.get("quotationNo"));
			
			query1.orderBy(builder.desc(root2.get("quotationNo")));
			
			Predicate predicate1=builder.like(root2.get("quotationNo"), matchVal);
			
			query1.where(predicate1);
			
			
			temp=session.createQuery(query1).getResultList();
            if(!temp.isEmpty())
			tempInv = temp.get(0);
			
			
			/*
			 * Criteria criteria1=session1.createCriteria(QuotationDO.class);
			 * criteria1.add(Restrictions.like("invoiceNo",matchVal,MatchMode.ANYWHERE));
			 * Projection p1 = Projections.max("invoiceNo");
			 
			criteria1.setProjection(p1);
			 tempInv=(String) criteria1.uniqueResult();
			 
			 */
			}
			System.out.println("max Quotation no :"+tempInv);
			if(tempInv != null && !tempInv.equalsIgnoreCase("")) {
				int len = (tempInv.split("/")[2]).length();
				Integer nextInvNo = Integer.parseInt((tempInv.split("/")[2]))+1;
				invNo = documentSeqMasterDO.getPrefix1() +"/"+documentSeqMasterDO.getPrefix2()+"/"+String.format("%04d", nextInvNo);
			}else {
				invNo = documentSeqMasterDO.getPrefix1() +"/"+documentSeqMasterDO.getPrefix2()+"/"+documentSeqMasterDO.getSeries();
			}
			
			return invNo;
			
			
		}catch(Exception e) {
			LOGGER.error("Exception occured in QuotationDao :: getInvNo "+e);
		}
		LOGGER.info("QuotationDao :: getInvNo method end");
		return invNo;
	}


	public List<QuotationDO> getInvoiceListByMonth(String month) {
		LOGGER.info("QuotationDao :: getInvoiceListByMonth :: Start ");
		List<QuotationDO> invoiceList = new ArrayList<QuotationDO>();
		try {

			Session session = getSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<QuotationDO> query = builder.createQuery(QuotationDO.class);

			Root<QuotationDO> root = query.from(QuotationDO.class);

			query.select(root);

			Predicate predicate = builder.equal(root.get("month"), month);

			query.where(predicate);

			Order order = builder.desc(root.get("quotationDate"));

			query.orderBy(order);

			invoiceList = session.createQuery(query).getResultList();

			/*
			 * Criteria criteria=session.createCriteria(QuotationDO.class);
			 * criteria.add(Restrictions.eq("month",month)); invoiceList=criteria.list();
			 */

		} catch (Exception e) {
			LOGGER.error("Exception occured in QuotationDao :: getInvoiceListByMonth ");
		}
		LOGGER.info("QuotationDao :: getInvoiceListByMonth method end");
		return invoiceList;
	}

	public List<ProductDO> getInvoiceProductList() {
		LOGGER.info("QuotationDao :: getInvoiceProductList :: Start ");
		List<ProductDO> invoiceProductList = new ArrayList<ProductDO>();
		try {

			Session session = getSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<ProductDO> query = builder.createQuery(ProductDO.class);

			Root<ProductDO> root = query.from(ProductDO.class);

			query.select(root);

			invoiceProductList = session.createQuery(query).getResultList();
			/*
			 * Criteria criteria=session.createCriteria(ProductDO.class);
			 * invoiceProductList=criteria.list();
			 */

		} catch (Exception e) {
			LOGGER.error("Exception occured in QuotationDao :: getInvoiceProductList ");
		}
		LOGGER.info("QuotationDao :: getInvoiceProductList method end");
		return invoiceProductList;
	}

	/*
	 * public static void main(String[] args) { try {
	 * 
	 * ProductDO productDO=null;
	 * 
	 * QuotationDao QuotationDao=new QuotationDao();
	 * 
	 * Session session=QuotationDao.getSession(); Transaction
	 * tx=session.getTransaction(); tx.begin();
	 * 
	 * Criteria criteria=session.createCriteria(ProductDO.class);
	 * criteria.add(Restrictions.eq("invoiceProductId",1));
	 * 
	 * productDO=(ProductDO)criteria.uniqueResult();
	 * 
	 * productDO.setProductDescription("nadimkhan"); tx.commit(); session.flush();
	 * session.close();
	 * 
	 * 
	 * }catch(Exception e) { System.out.println("Exception:"+e); } }
	 */

	public String saveInvoice(QuotationDO QuotationDO) {
		LOGGER.info("QuotationDao::saveInvoice::start");

		try {
			/*
			 * QuotationDO tmp = QuotationDO; QuotationDO invDO = null;
			 */
			QuotationDO.setQuotationProductId(1);
			Calendar cal = Calendar.getInstance();
			QuotationDO.setMonth(new SimpleDateFormat("MMM").format(cal.getTime()));
			QuotationDO.setCity("Mum");
			QuotationDO.setIgstValue(new BigDecimal(1));
			Session session=getSession();
			Transaction tx=session.beginTransaction();
			session.saveOrUpdate(QuotationDO);
			session.flush();
			tx.commit();

		} catch (Exception e) {
			LOGGER.info("Exception occured in QuotationDao::saveInvoice::" + e);
			return "failure";
		}

		LOGGER.info("QuotationDao::saveInvoice::end");
		return "success";
	}

	@SuppressWarnings("deprecation")
	@Transactional
	public QuotationDO getInvoiceDetails(String invId) {
		LOGGER.info("QuotationDao :: getInvoiceDetails :: Start ");
		QuotationDO invDO = null;
		try {

			Session session = getSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<QuotationDO> query = builder.createQuery(QuotationDO.class);

			Root<QuotationDO> root = query.from(QuotationDO.class);

			query.select(root);

			Predicate predicate = builder.equal(root.get("quotationId"), invId);

			query.where(predicate);

			invDO = session.createQuery(query).getSingleResult();
			/*
			 * Criteria criteria=session.createCriteria(QuotationDO.class);
			 * criteria.add(Restrictions.eq("invoiceNo",invNo));
			 * invDO=(QuotationDO)criteria.uniqueResult();
			 */

		} catch (Exception e) {
			LOGGER.error("Exception occured in QuotationDao :: getInvoiceDetails ");
		}
		LOGGER.info("QuotationDao :: getInvoiceDetails method end");
		return invDO;
	}

	@Transactional
	public boolean DeleteInvoice(String invNo) {
		LOGGER.info("QuotationDao :: DeleteInvoice :: Start ");
		boolean flag = false;
		QuotationDO invDo = null;
		try {

			Session session = getSession();
			Query query = session.createNativeQuery("delete from Quotation where Quotation_id ='" + invNo + "'");
			/*
			 * Criteria criteria=session.createCriteria(QuotationDO.class);
			 * criteria.add(Restrictions.eq("invoiceNo",invNo)); invDo =
			 * (QuotationDO)criteria.uniqueResult(); session.delete(invDo);
			 */
			query.executeUpdate();
			flag = true;

		} catch (Exception e) {
			LOGGER.error("Exception occured in QuotationDao :: DeleteInvoice ");
			flag = false;
		}
		LOGGER.info("QuotationDao :: DeleteInvoice method end");
		return flag;
	}

//	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean cloneInvoice(String invNo) {
		LOGGER.info("QuotationDao :: cloneInvoice :: Start ");
		boolean flag=false;
		QuotationDO invDO= new QuotationDO();
		List<QuotationDO> invtemp = new ArrayList<>();
		Integer gInvNo = 0;
		String fInvNo="";
		try {
			
			invtemp = getInvoiceList();
			gInvNo = invtemp.size();
			
			Session session=getSession();
			
			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<QuotationDO> query = builder.createQuery(QuotationDO.class);

			Root<QuotationDO> root = query.from(QuotationDO.class);

			query.select(root);

			Predicate predicate = builder.equal(root.get("quotationId"), invNo);

			query.where(predicate);
			
			invDO=session.createQuery(query).getResultList().get(0);
			
			/*
			 * Criteria criteria=session.createCriteria(QuotationDO.class);
			 * criteria.add(Restrictions.eq("invoiceNo",invNo)); invDO =
			 * (QuotationDO)criteria.list().get(0);
			 */
			
			fInvNo = getInvNo();
			invDO.setQuotationNo(fInvNo);
					
			List<QuotationProductDO> productdo = new ArrayList<QuotationProductDO>();
			for(QuotationProductDO temp : invDO.getQuotationProductDO()) {
				QuotationProductDO invprod = new QuotationProductDO();
				invprod = temp;
				invprod.setQuotation(invDO);
				invprod.setQuotationProductId(null);
				productdo.add(invprod);
			}
			invDO.getQuotationProductDO().clear();
			invDO.setQuotationProductDO(productdo);
			
			/*
			 * session.clear(); closeSession(session);
			 */
			
			closeSession(session);
			
			flag  = saveCloneInv(invDO);
			

		} catch (Exception e) {
			LOGGER.error("Exception occured in QuotationDao :: cloneInvoice ");
			flag = false;
		}
		LOGGER.info("QuotationDao :: cloneInvoice method end");
		return flag;
	}

//	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean saveCloneInv(QuotationDO invdo) {

		LOGGER.info("QuotationDao::saveCloneInv::start");
		try {

//			Session session = getSession();
			Session session=sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(invdo);
			session.flush();
			tx.commit();
			session.close();

		} catch (Exception e) {
			LOGGER.info("Exception occured in QuotationDao::saveCloneInv::" + e);
			return false;
		}

		LOGGER.info("QuotationDao::saveCloneInv::end");
		return true;

	}
	public UserDO getUserDO(String loginId) {
		LOGGER.info("QuotationDao::getUserDO()::Start");
		UserDO userDO=null;
		
		try {
			Session session=getSession();
			
			CriteriaBuilder criteriaBuilder=session.getCriteriaBuilder();
			
			CriteriaQuery<UserDO> query=criteriaBuilder.createQuery(UserDO.class);
			
			Root<UserDO> root=query.from(UserDO.class);
			
			query.select(root);
			
			Predicate predicate=criteriaBuilder.equal(root.get("loginId"), loginId);
			
			query.where(predicate);
			
			userDO=session.createQuery(query).getSingleResult();
			
		}catch(Exception e) {
			LOGGER.error("Exception in QuotationDao::getUserDO()::"+e);
		}
		LOGGER.info("QuotationDao::getUserDO()::End");
		return userDO;
	}
		public String getCustomerEmail(String custName) {
		
        LOGGER.info("QuotationDao::getCustomerEmail::start");
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
			
			
			/*
			 * Criteria criteria=session.createCriteria(CustomerDO.class);
			 * criteria.add(Restrictions.eq("customerName",custName)); Projection p1 =
			 * Projections.property("email");
			 
			criteria.setProjection(p1);
			email=(String) criteria.uniqueResult();
			*/
			
		}catch(Exception e) {
			LOGGER.info("Exception occured in QuotationDao::getCustomerEmail::"+e);
			return email;
		}
		
		LOGGER.info("QuotationDao::getCustomerEmail::end");
		return email;
		
	}
	
//	public String getFinYear() {
//		LOGGER.info("QuotationDao::getFinYear::start");
//		String fY = "";
//		try {
//			
//			int year = Calendar.getInstance().get(Calendar.YEAR);
//
//		    int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
//	
//		    if (month < 3) {
//		        fY = (year - 1) + "-" + String.valueOf(year).substring(2);
//		    } else {
//		        fY =  year + "-" + String.valueOf((year + 1)).substring(2);
//		    }
//			
//		}catch(Exception e) {
//			LOGGER.info("Exception occured in QuotationDao::getFinYear::"+e);
//		}
//		return fY;
//	}
//	
	
	
	public List<DocumentSeqMasterDO> getDocMaster(){
		LOGGER.info("QuotationDao :: getDocMaster :: Start ");
		
		List<DocumentSeqMasterDO> documentSeqMasterDO = new ArrayList<DocumentSeqMasterDO>();
		try {
			
			Session session=getSession();
			
            CriteriaBuilder builder=session.getCriteriaBuilder();
			
			CriteriaQuery<DocumentSeqMasterDO> query=builder.createQuery(DocumentSeqMasterDO.class);
			
			Root<DocumentSeqMasterDO> root=query.from(DocumentSeqMasterDO.class);
			
			query.select(root);
			
			documentSeqMasterDO=session.createQuery(query).getResultList();
			
			
			/*
			 * Criteria criteria=session.createCriteria(DocumentSeqMasterDO.class);
			 * documentSeqMasterDO=criteria.list();
			 */
			
			
		}catch(Exception e) {
			LOGGER.error("Exception occured in QuotationDao :: getDocMaster ");
		}
		LOGGER.info("QuotationDao :: getDocMaster method end");
		return documentSeqMasterDO;
	}
	
	public String saveDocMaster(DocumentSeqMasterDO docmentseqmasterdo) {
		LOGGER.info("QuotationDao::saveDocMaster::start");
		
		try {
			
			Session session=getSession();
			Transaction tx=session.beginTransaction();
			session.saveOrUpdate(docmentseqmasterdo);
			session.flush();
			tx.commit();
			
		}catch(Exception e) {
			LOGGER.info("Exception occured in QuotationDao::saveDocMaster::"+e);
			return "failure";
		}
		return "success";
	}
	
	
	public boolean deleteDocMaster(String docId){
		LOGGER.info("QuotationDao :: deleteDocMaster :: Start ");
		boolean flag=false;
		try {
			
			Session session=getSession();
			Query query= session.createSQLQuery("delete from document_seq_master where document_seq_Id ='"+docId+"'");
			query.executeUpdate();
			flag = true;
			
		}catch(Exception e) {
			LOGGER.error("Exception occured in QuotationDao :: deleteDocMaster ");
			flag = false;
		}
		LOGGER.info("QuotationDao :: deleteDocMaster method end");
		return flag;
	}
	
	public List<String> getStandardType(String module,String subModule){
		LOGGER.info("QuotationDao::getGstRates()::start");
		List<String> gstRates=null;
		try {
			Session session=getSession();
			CriteriaBuilder builder=session.getCriteriaBuilder();
			CriteriaQuery<String> query=builder.createQuery(String.class);
			
			Root<StandardTypeDO> root=query.from(StandardTypeDO.class);
			
			query.select(root.get("value"));
			
			Predicate predicate1 =builder.equal(root.get("module"), module);
			
			Predicate predicate2 =builder.equal(root.get("subModule"), subModule);
			
			query.where(builder.and(predicate1,predicate2));
			
			gstRates=session.createQuery(query).getResultList();
			
			
		}catch(Exception e) {
			
		}
		LOGGER.info("QuotationDao::getGstRates()::end");
		return gstRates;
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

package com.accurate.erp.dao.invoice;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.apache.log4j.Logger;
import org.hibernate.Query;
/*import org.hibernate.Criteria;
import org.hibernate.Query;*/
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
//import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.accurate.erp.action.invoice.InvoiceController;
import com.accurate.erp.model.invoice.ClientDO;
import com.accurate.erp.model.invoice.CustomerDO;
import com.accurate.erp.model.invoice.InvoiceDO;
import com.accurate.erp.model.invoice.ProductDO;
import com.accurate.erp.model.invoice.UserDO;
import com.accurate.erp.model.modelmaster.DocumentSeqMasterDO;
import com.accurate.erp.model.purchase.PurchaseDO;
import com.accurate.erp.model.purchase.SupplierDO;
import com.accurate.erp.model.util.StandardTypeDO;

/*import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;*/

import com.accurate.erp.model.invoice.InvoiceProductDO;

@Repository
public class InvoiceDao {

	private final static Logger LOGGER = LogManager.getLogger(InvoiceController.class);

	@Autowired
	SessionFactory sessionFactory;

	@SuppressWarnings("deprecation")
	@Transactional
	public List<CustomerDO> getCustometList() {
		LOGGER.info("InvoiceDao :: getCustomerList :: Start ");
		List<CustomerDO> custList = new ArrayList<CustomerDO>();
		try {

			Session session = getSession();
			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<CustomerDO> criteriaQuery = builder.createQuery(CustomerDO.class);

			Root<CustomerDO> root = criteriaQuery.from(CustomerDO.class);

			criteriaQuery.select(root);

			custList = session.createQuery(criteriaQuery).getResultList();

		} catch (Exception e) {
			LOGGER.error("Exception occured in InvoiceDao :: GetCustomerList ");
		}
		LOGGER.info("InvoiceDao :: getCustomerList method end");
		return custList;
	}

	public CustomerDO getCustomerById(Integer custId) {
		LOGGER.info("InvoiceDao :: getCustomerById :: Start ");
		CustomerDO customerDO = null;
		try {
			Session session = getSession();
			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<CustomerDO> criteriaQuery = builder.createQuery(CustomerDO.class);

			Root<CustomerDO> root = criteriaQuery.from(CustomerDO.class);

			criteriaQuery.select(root);

			Predicate predicate = builder.equal(root.get("customerId"), custId);

			criteriaQuery.where(predicate);

			customerDO = session.createQuery(criteriaQuery).getSingleResult();

			/*
			 * Criteria criteria=session.createCriteria(CustomerDO.class);
			 * criteria.add(Restrictions.eq("customerId",custId));
			 * customerDO=(CustomerDO)criteria.uniqueResult();
			 */

		} catch (Exception e) {
			LOGGER.error("Exception occured in InvoiceDao :: getCustomerById ");
		}
		LOGGER.info("InvoiceDao :: getCustomerById method end");
		return customerDO;
	}

	public ProductDO getProductById(Integer prodId) {
		LOGGER.info("InvoiceDao :: getProductById :: Start ");
		ProductDO productDO = null;
		try {

			Session session = getSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<ProductDO> query = builder.createQuery(ProductDO.class);

			Root<ProductDO> root = query.from(ProductDO.class);

			query.select(root);

			Predicate predicate = builder.equal(root.get("invoiceProductId"), prodId);

			query.where(predicate);

			productDO = session.createQuery(query).getSingleResult();

			/*
			 * Criteria criteria=session.createCriteria(ProductDO.class);
			 * criteria.add(Restrictions.eq("invoiceProductId",prodId));
			 * productDO=(ProductDO)criteria.uniqueResult();
			 */

		} catch (Exception e) {
			LOGGER.error("Exception occured in InvoiceDao :: getProductById ");
		}
		LOGGER.info("InvoiceDao :: getProductById method end");
		return productDO;
	}

	public List<InvoiceDO> getInvoiceList() {
		LOGGER.info("InvoiceDao :: getInvoiceList :: Start ");
		List<InvoiceDO> invoiceList = new ArrayList<InvoiceDO>();
		try {

			Session session = getSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<InvoiceDO> query = builder.createQuery(InvoiceDO.class);

			Root<InvoiceDO> root = query.from(InvoiceDO.class);

			query.select(root);

			invoiceList = session.createQuery(query).getResultList();

			/*
			 * Criteria criteria=session.createCriteria(InvoiceDO.class);
			 * invoiceList=criteria.list();
			 */

		} catch (Exception e) {
			LOGGER.error("Exception occured in InvoiceDao :: getInvoiceList ");
		}
		LOGGER.info("InvoiceDao :: getInvoiceList method end");
		return invoiceList;
	}
	
	
	public List<PurchaseDO> getPurchaseList(Map<String, String> data) {
		LOGGER.info("InvoiceDao :: getPurchaseList :: Start ");
		List<PurchaseDO> purchaseList = new ArrayList<PurchaseDO>();
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

			Session session = getSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<PurchaseDO> query = builder.createQuery(PurchaseDO.class);

			Root<PurchaseDO> root = query.from(PurchaseDO.class);

			query.select(root);

			Predicate customerPredicate = builder.equal(root.get("supplierName"), customerName);

			Predicate datePredicate = builder.between(root.get("purchaseDate"), fromDate, toDate);

//			Predicate statusPredicate = builder.equal(root.get("invoiceStatus"), status);

			Predicate[] predicate = new Predicate[3];
			List<Predicate> predList = new ArrayList<>();
			
			 int predCount=0;
			  
			  if(customerName!=null && customerName.length()>0) {
				  predList.add(customerPredicate);
			  }
			  
			  if(fromDate!=null && toDate!=null) { 
				  predList.add(datePredicate);
			  }
			 
			 

			Predicate finalPred = builder.and(predList.toArray(new Predicate[0]));

			query.where(finalPred);

//			Predicate categoryPredicate=builder.equal(root.get(null), statusPredicate)

			purchaseList = session.createQuery(query).getResultList();

			/*
			 * Criteria criteria=session.createCriteria(InvoiceDO.class);
			 * invoiceList=criteria.list();
			 */

		} catch (Exception e) {
			LOGGER.error("Exception occured in InvoiceDao :: getPurchaseList ");
		}
		LOGGER.info("InvoiceDao :: getPurchaseList method end");
		return purchaseList;
	}
	

	public List<InvoiceDO> getInvoiceList(Map<String, String> data) {
		LOGGER.info("InvoiceDao :: getInvoiceList :: Start ");
		List<InvoiceDO> invoiceList = new ArrayList<InvoiceDO>();
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

			CriteriaQuery<InvoiceDO> query = builder.createQuery(InvoiceDO.class);

			Root<InvoiceDO> root = query.from(InvoiceDO.class);

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
			LOGGER.error("Exception occured in InvoiceDao :: getInvoiceList ");
		}
		LOGGER.info("InvoiceDao :: getInvoiceList method end");
		return invoiceList;
	}
	
		public String getInvNo(){
		LOGGER.info("InvoiceDao :: getInvNo :: Start ");
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
			
			Predicate predicate=builder.equal(root.get("documentName"), "Invoice");
			
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
			
			Root<InvoiceDO> root2=query1.from(InvoiceDO.class);
			query1.select(root2.get("invoiceNo"));
			
			query1.orderBy(builder.desc(root2.get("invoiceNo")));
			
			Predicate predicate1=builder.like(root2.get("invoiceNo"), matchVal);
			
			query1.where(predicate1);
			
			
			temp=session.createQuery(query1).getResultList();
            if(!temp.isEmpty())
			tempInv = temp.get(0);
			
			
			/*
			 * Criteria criteria1=session1.createCriteria(InvoiceDO.class);
			 * criteria1.add(Restrictions.like("invoiceNo",matchVal,MatchMode.ANYWHERE));
			 * Projection p1 = Projections.max("invoiceNo");
			 
			criteria1.setProjection(p1);
			 tempInv=(String) criteria1.uniqueResult();
			 
			 */
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
			LOGGER.error("Exception occured in InvoiceDao :: getInvNo "+e);
		}
		LOGGER.info("InvoiceDao :: getInvNo method end");
		return invNo;
	}


	public List<InvoiceDO> getInvoiceListByMonth(String month) {
		LOGGER.info("InvoiceDao :: getInvoiceListByMonth :: Start ");
		List<InvoiceDO> invoiceList = new ArrayList<InvoiceDO>();
		try {

			Session session = getSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<InvoiceDO> query = builder.createQuery(InvoiceDO.class);

			Root<InvoiceDO> root = query.from(InvoiceDO.class);

			query.select(root);

			Predicate predicate = builder.equal(root.get("month"), month);

			query.where(predicate);

			Order order = builder.desc(root.get("invoiceDate"));

			query.orderBy(order);

			invoiceList = session.createQuery(query).getResultList();

			/*
			 * Criteria criteria=session.createCriteria(InvoiceDO.class);
			 * criteria.add(Restrictions.eq("month",month)); invoiceList=criteria.list();
			 */

		} catch (Exception e) {
			LOGGER.error("Exception occured in InvoiceDao :: getInvoiceListByMonth ");
		}
		LOGGER.info("InvoiceDao :: getInvoiceListByMonth method end");
		return invoiceList;
	}

	public List<ProductDO> getInvoiceProductList() {
		LOGGER.info("InvoiceDao :: getInvoiceProductList :: Start ");
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
			LOGGER.error("Exception occured in InvoiceDao :: getInvoiceProductList ");
		}
		LOGGER.info("InvoiceDao :: getInvoiceProductList method end");
		return invoiceProductList;
	}

	/*
	 * public static void main(String[] args) { try {
	 * 
	 * ProductDO productDO=null;
	 * 
	 * InvoiceDao invoiceDao=new InvoiceDao();
	 * 
	 * Session session=invoiceDao.getSession(); Transaction
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

	public String saveInvoice(InvoiceDO invoiceDO) {
		LOGGER.info("InvoiceDao::saveInvoice::start");

		try {
			InvoiceDO tmp = invoiceDO;
			InvoiceDO invDO = null;
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
			LOGGER.info("Exception occured in invoiceDao::saveInvoice::" + e);
			return "failure";
		}

		LOGGER.info("InvoiceDao::saveInvoice::end");
		return "success";
	}
	
	
	public String savePurchase(PurchaseDO purchaseDO) {
		LOGGER.info("InvoiceDao::saveInvoice::start");

		try {
			Session session=getSession();
			Transaction tx=session.beginTransaction();
			session.saveOrUpdate(purchaseDO);
			session.flush();
			tx.commit();

		} catch (Exception e) {
			LOGGER.info("Exception occured in invoiceDao::saveInvoice::" + e);
			return "failure";
		}

		LOGGER.info("InvoiceDao::saveInvoice::end");
		return "success";
	}

	@SuppressWarnings("deprecation")
	@Transactional
	public InvoiceDO getInvoiceDetails(String invId) {
		LOGGER.info("InvoiceDao :: getInvoiceDetails :: Start ");
		InvoiceDO invDO = null;
		try {

			Session session = getSession();

			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<InvoiceDO> query = builder.createQuery(InvoiceDO.class);

			Root<InvoiceDO> root = query.from(InvoiceDO.class);

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
			LOGGER.error("Exception occured in InvoiceDao :: getInvoiceDetails ");
		}
		LOGGER.info("InvoiceDao :: getInvoiceDetails method end");
		return invDO;
	}

	@Transactional
	public boolean DeleteInvoice(String invNo) {
		LOGGER.info("InvoiceDao :: DeleteInvoice :: Start ");
		boolean flag = false;
		InvoiceDO invDo = null;
		try {

			Session session = getSession();
			Query query = session.createNativeQuery("delete from Invoice where Invoice_id ='" + invNo + "'");
			/*
			 * Criteria criteria=session.createCriteria(InvoiceDO.class);
			 * criteria.add(Restrictions.eq("invoiceNo",invNo)); invDo =
			 * (InvoiceDO)criteria.uniqueResult(); session.delete(invDo);
			 */
			query.executeUpdate();
			flag = true;

		} catch (Exception e) {
			LOGGER.error("Exception occured in InvoiceDao :: DeleteInvoice ");
			flag = false;
		}
		LOGGER.info("InvoiceDao :: DeleteInvoice method end");
		return flag;
	}
	
	public boolean deletePurchase(String purchaseId) {
		LOGGER.info("InvoiceDao :: DeleteInvoice :: Start ");
		boolean flag = false;
		InvoiceDO invDo = null;
		try {

			Session session = getSession();
			Query query = session.createNativeQuery("delete from purchase where purchase_id ='" + purchaseId + "'");
			/*
			 * Criteria criteria=session.createCriteria(InvoiceDO.class);
			 * criteria.add(Restrictions.eq("invoiceNo",invNo)); invDo =
			 * (InvoiceDO)criteria.uniqueResult(); session.delete(invDo);
			 */
			query.executeUpdate();
			flag = true;

		} catch (Exception e) {
			LOGGER.error("Exception occured in InvoiceDao :: DeleteInvoice ");
			flag = false;
		}
		LOGGER.info("InvoiceDao :: DeleteInvoice method end");
		return flag;
	}
	
	

//	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean cloneInvoice(String invNo) {
		LOGGER.info("InvoiceDao :: cloneInvoice :: Start ");
		boolean flag=false;
		InvoiceDO invDO= new InvoiceDO();
		List<InvoiceDO> invtemp = new ArrayList<>();
		Integer gInvNo = 0;
		String fInvNo="";
		try {
			
			invtemp = getInvoiceList();
			gInvNo = invtemp.size();
			
			Session session=getSession();
			
			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<InvoiceDO> query = builder.createQuery(InvoiceDO.class);

			Root<InvoiceDO> root = query.from(InvoiceDO.class);

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
					
			List<InvoiceProductDO> productdo = new ArrayList<InvoiceProductDO>();
			for(InvoiceProductDO temp : invDO.getInvoiceProductDO()) {
				InvoiceProductDO invprod = new InvoiceProductDO();
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
			LOGGER.error("Exception occured in InvoiceDao :: cloneInvoice ");
			flag = false;
		}
		LOGGER.info("InvoiceDao :: cloneInvoice method end");
		return flag;
	}

//	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean saveCloneInv(InvoiceDO invdo) {

		LOGGER.info("InvoiceDao::saveCloneInv::start");
		try {

//			Session session = getSession();
			Session session=sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(invdo);
			session.flush();
			tx.commit();
			session.close();

		} catch (Exception e) {
			LOGGER.info("Exception occured in invoiceDao::saveCloneInv::" + e);
			return false;
		}

		LOGGER.info("InvoiceDao::saveCloneInv::end");
		return true;

	}
	public UserDO getUserDO(String loginId) {
		LOGGER.info("InvoiceDao::getUserDO()::Start");
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
			LOGGER.error("Exception in InvoiceDao::getUserDO()::"+e);
		}
		LOGGER.info("InvoiceDao::getUserDO()::End");
		return userDO;
	}
	
	public UserDO getUserByUserId(String userId) {
		LOGGER.info("InvoiceDao::getUserByUserId()::Start");
		UserDO userDO=null;
		
		try {
			Session session=getSession();
			
			CriteriaBuilder criteriaBuilder=session.getCriteriaBuilder();
			
			CriteriaQuery<UserDO> query=criteriaBuilder.createQuery(UserDO.class);
			
			Root<UserDO> root=query.from(UserDO.class);
			
			query.select(root);
			
			Predicate predicate=criteriaBuilder.equal(root.get("userId"), userId);
			
			query.where(predicate);
			
			userDO=session.createQuery(query).getSingleResult();
			
		}catch(Exception e) {
			LOGGER.error("Exception in InvoiceDao::getUserByUserId()::"+e);
		}
		LOGGER.info("InvoiceDao::getUserByUserId()::End");
		return userDO;
	}
	
	
	public List<InvoiceDO> getInvoiceByFinancialYear(String financialYear) {
		LOGGER.info("InvoiceDao::getInvoiceByFinancialYear()::Start");
		List<InvoiceDO> invoiceDO=null;
		
		try {
			Session session=getSession();
			
			CriteriaBuilder criteriaBuilder=session.getCriteriaBuilder();
			
			CriteriaQuery<InvoiceDO> query=criteriaBuilder.createQuery(InvoiceDO.class);
			
			Root<InvoiceDO> root=query.from(InvoiceDO.class);
			
			query.select(root);
			
			Predicate predicate=criteriaBuilder.equal(root.get("financialYear"), financialYear);
			
			query.where(predicate);
			
			invoiceDO=session.createQuery(query).getResultList();
			
		}catch(Exception e) {
			LOGGER.error("Exception in InvoiceDao::getInvoiceByFinancialYear()::"+e);
		}
		LOGGER.info("InvoiceDao::getInvoiceByFinancialYear()::End");
		return invoiceDO;
	}
	
		public String getCustomerEmail(String custName) {
		
        LOGGER.info("InvoiceDao::getCustomerEmail::start");
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
			LOGGER.info("Exception occured in invoiceDao::getCustomerEmail::"+e);
			return email;
		}
		
		LOGGER.info("InvoiceDao::getCustomerEmail::end");
		return email;
		
	}
	
//	public String getFinYear() {
//		LOGGER.info("InvoiceDao::getFinYear::start");
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
//			LOGGER.info("Exception occured in invoiceDao::getFinYear::"+e);
//		}
//		return fY;
//	}
//	
	
	
	public List<DocumentSeqMasterDO> getDocMaster(){
		LOGGER.info("InvoiceDao :: getDocMaster :: Start ");
		
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
			LOGGER.error("Exception occured in InvoiceDao :: getDocMaster ");
		}
		LOGGER.info("InvoiceDao :: getDocMaster method end");
		return documentSeqMasterDO;
	}
	
	public DocumentSeqMasterDO getDocMasterById(Integer id) {
		DocumentSeqMasterDO doc=null;
		try {
		Session session=getSession();	
		
		CriteriaBuilder criteriaBuilder=session.getCriteriaBuilder();
		
		CriteriaQuery<DocumentSeqMasterDO> query=criteriaBuilder.createQuery(DocumentSeqMasterDO.class);
		
		Root<DocumentSeqMasterDO> root=query.from(DocumentSeqMasterDO.class);
		
		query.select(root);
		
		Predicate predicate=criteriaBuilder.equal(root.get("documentSeqId"), id);
		
		query.where(predicate);
		
		doc=session.createQuery(query).getSingleResult();
		}
		catch(Exception e) {
			LOGGER.error("Exception in InvoiceDao::getDocMasterById()::"+e);
		}
		
		return doc;
	}
	
	
	public DocumentSeqMasterDO getDocMasterByName(String name) {
		DocumentSeqMasterDO doc=null;
		try {
		Session session=getSession();	
		
		CriteriaBuilder criteriaBuilder=session.getCriteriaBuilder();
		
		CriteriaQuery<DocumentSeqMasterDO> query=criteriaBuilder.createQuery(DocumentSeqMasterDO.class);
		
		Root<DocumentSeqMasterDO> root=query.from(DocumentSeqMasterDO.class);
		
		query.select(root);
		
		Predicate predicate=criteriaBuilder.equal(root.get("documentName"), name);
		
		query.where(predicate);
		
		doc=session.createQuery(query).getSingleResult();
		}
		catch(Exception e) {
			LOGGER.error("Exception in InvoiceDao::getDocMasterById()::"+e);
		}
		
		return doc;
	}
	
	public String saveDocMaster(DocumentSeqMasterDO docmentseqmasterdo) {
		LOGGER.info("InvoiceDao::saveDocMaster::start");
		
		try {
			
			Session session=getSession();
			Transaction tx=session.beginTransaction();
//			DocumentSeqMasterDO doc=session.get(DocumentSeqMasterDO.class, docmentseqmasterdo.getDocumentSeqId());
			session.saveOrUpdate(docmentseqmasterdo);
			tx.commit();
//			session.flush();
			session.close();
			
		}catch(Exception e) {
			LOGGER.info("Exception occured in invoiceDao::saveDocMaster::"+e);
			return "failure";
		}
		return "success";
	}
	
	
	public boolean deleteDocMaster(String docId){
		LOGGER.info("InvoiceDao :: deleteDocMaster :: Start ");
		boolean flag=false;
		try {
			
			Session session=getSession();
			Query query= session.createSQLQuery("delete from document_seq_master where document_seq_Id ='"+docId+"'");
			query.executeUpdate();
			flag = true;
			
		}catch(Exception e) {
			LOGGER.error("Exception occured in InvoiceDao :: deleteDocMaster ");
			flag = false;
		}
		LOGGER.info("InvoiceDao :: deleteDocMaster method end");
		return flag;
	}
	
	public List<String> getStandardType(String module,String subModule){
		LOGGER.info("InvoiceDao::getGstRates()::start");
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
		LOGGER.info("InvoiceDao::getGstRates()::end");
		return gstRates;
	}
	
	
	public List<PurchaseDO> getPurchaseList(){
		LOGGER.info("InvoiceDao::getPurchaseList()::start");
		 
		List<PurchaseDO> purchaseList=null;
		
		try {
			Session session=getSession();
			
			CriteriaBuilder builder=session.getCriteriaBuilder();
			
			CriteriaQuery<PurchaseDO> query=builder.createQuery(PurchaseDO.class);
			
			Root<PurchaseDO> root=query.from(PurchaseDO.class);
			
			query.select(root);
			
			purchaseList=session.createQuery(query).getResultList();
		}catch(Exception e) {
			LOGGER.error("Exception in InvoiceDao::getPurchaseList()::"+e);
		}
		LOGGER.info("InvoiceDao::getPurchaseList()::end");
		return purchaseList;
	}
	
	
	public List<PurchaseDO> getPurchaseList(String month){
		LOGGER.info("InvoiceDao::getPurchaseList()::start");
		 
		List<PurchaseDO> purchaseList=null;
		
		try {
			Session session=getSession();
			
			CriteriaBuilder builder=session.getCriteriaBuilder();
			
			CriteriaQuery<PurchaseDO> query=builder.createQuery(PurchaseDO.class);
			
			Root<PurchaseDO> root=query.from(PurchaseDO.class);
			
			query.select(root);
			
			Predicate predicate=builder.equal(root.get("month"), month);
			
			query.where(predicate);
			
			purchaseList=session.createQuery(query).getResultList();
		}catch(Exception e) {
			LOGGER.error("Exception in InvoiceDao::getPurchaseList()::"+e);
		}
		LOGGER.info("InvoiceDao::getPurchaseList()::end");
		return purchaseList;
	}
	
	public PurchaseDO getPurchaseById(String purchaseId) {
		LOGGER.info("InvoiceDao::getPurchaseById()::start");
		
		PurchaseDO purchseDO=null;
		
		try {
			
			Session session=getSession();
			
			CriteriaBuilder builder=session.getCriteriaBuilder();
			
			CriteriaQuery<PurchaseDO> query=builder.createQuery(PurchaseDO.class);
			
			Root<PurchaseDO> root=query.from(PurchaseDO.class);
			
			query.select(root);
			
			Predicate predicate=builder.equal(root.get("purchaseId"), purchaseId);
			
			query.where(predicate);
			
			purchseDO=session.createQuery(query).getSingleResult();
			
		}catch(Exception e) {
			LOGGER.error("Exception in InvoiceDao::getPurchaseById()::");
		}
		
		
		
		LOGGER.info("InvoiceDao::getPurchaseById()::end");
		return purchseDO;
	}
	
	public List<SupplierDO> getAllSuppliers() {
		LOGGER.info("InvoiceDao::getAllSuppliers()::start");
		List<SupplierDO> list=null;
		try {
			
			Session session=getSession();
			
			CriteriaBuilder builder=session.getCriteriaBuilder();
			
			CriteriaQuery<SupplierDO> query=builder.createQuery(SupplierDO.class);
			
			Root<SupplierDO> root=query.from(SupplierDO.class);
			
			query.select(root);
			
			list=session.createQuery(query).getResultList();
			
		}catch(Exception e) {
			LOGGER.error("Exception in InvoiceDao::getAllSuppliers()::"+e);
		}
		
		LOGGER.info("InvoiceDao::getAllSuppliers()::end");
		return list;
	}
	
	public SupplierDO getSuppliersById(String supplierId) {
		LOGGER.info("InvoiceDao::getSuppliersById()::start");
		SupplierDO supplierDO=null;
		try {
			
			Session session=getSession();
			
			CriteriaBuilder builder=session.getCriteriaBuilder();
			
			CriteriaQuery<SupplierDO> query=builder.createQuery(SupplierDO.class);
			
			Root<SupplierDO> root=query.from(SupplierDO.class);
			
			query.select(root);
			
			Predicate predicate=builder.equal(root.get("supplierId"), supplierId);
			
			query.where(predicate);
			
			supplierDO=session.createQuery(query).uniqueResult();
			
		}catch(Exception e) {
			LOGGER.error("Exception in InvoiceDao::getSuppliersById()::"+e);
		}
		
		LOGGER.info("InvoiceDao::getSuppliersById()::end");
		return supplierDO;
	}
	
	public String saveCustomer(CustomerDO customerdo) {
		LOGGER.info("InvoiceDao::saveCustomer()::start");
		String result = "success";
		try {
			Session session=getSession();
			Transaction tx=session.beginTransaction();
			session.saveOrUpdate(customerdo);
			session.flush();
			tx.commit();

            Session session1=getSession();	

			CriteriaBuilder criteriaBuilder=session1.getCriteriaBuilder();

			CriteriaQuery<CustomerDO> query=criteriaBuilder.createQuery(CustomerDO.class);

			Root<CustomerDO> root=query.from(CustomerDO.class);

			query.select(root);

			Predicate predicate=criteriaBuilder.equal(root.get("customerName"), customerdo.getCustomerName());

			query.where(predicate);

			CustomerDO cust = session1.createQuery(query).getSingleResult();

			result = cust.getCustomerId().toString();			
		}catch(Exception e) {
			LOGGER.error("Exception occured in ::saveCustomer()::"+e);
			return "failure";
		}
		LOGGER.info("InvoiceDao::saveCustomer()::end");
		return result;
	}

	public String saveProduct(ProductDO prodDO) {
		LOGGER.info("InvoiceDao::saveProduct()::start");
		String result = "";
		try {
			Session session=getSession();
			Transaction tx=session.beginTransaction();
			session.saveOrUpdate(prodDO);
			session.flush();
			tx.commit();

            Session session1=getSession();	

			CriteriaBuilder criteriaBuilder=session1.getCriteriaBuilder();

			CriteriaQuery<ProductDO> query=criteriaBuilder.createQuery(ProductDO.class);

			Root<ProductDO> root=query.from(ProductDO.class);

			query.select(root);

			Predicate predicate=criteriaBuilder.equal(root.get("productName"), prodDO.getProductName());

			query.where(predicate);

			ProductDO prod =session1.createQuery(query).getSingleResult();

			result = prod.getInvoiceProductId().toString();

		}catch(Exception e) {
			LOGGER.error("Exception occured in ::saveProduct()::"+e);
			return "failure";
		}
		LOGGER.info("InvoiceDao::saveProduct()::end");
		return result;
	}

	public List<UserDO> getUserDOsByRegisterId(String registerId){
		LOGGER.info("InvoiceDao::getUserDOsByUserId():start");
		List<UserDO> userList=null;
		
		try {
			Session session=getSession();
			
			CriteriaBuilder builder=session.getCriteriaBuilder();
			
			CriteriaQuery<UserDO> query=builder.createQuery(UserDO.class);
			
			Root<UserDO> root=query.from(UserDO.class);
			
			query.select(root);
			
			Predicate predicate=builder.equal(root.get("registerId"), registerId);
			
			query.where(predicate);
			
			userList=session.createQuery(query).getResultList();
			
			
		}catch(Exception e) {
			LOGGER.error("Exception in InvoiceDao::getUserDOsByUserId()::"+e);
		}
		
		
		
		LOGGER.info("InvoiceDao::getUserDOsByUserId():end");
		
		return userList;
	}
	
	public boolean cancelInvoiceById(Integer invoiceId) {
		LOGGER.info("InvoiceDao::calcelInvoice()::start");
		boolean flag=false;
		
		try {
			
			Session session=getSession();
			
			InvoiceDO invoiceDO=getInvoiceDetails(invoiceId.toString());
			
			invoiceDO.setAdditionalCharges("0");
			
			invoiceDO.setTransportCharges("0");
			
			invoiceDO.setDiscount(BigDecimal.ZERO);
			
			invoiceDO.setOtherDiscount(BigDecimal.ZERO);
			
			invoiceDO.setSgstValue(BigDecimal.ZERO);
			
			invoiceDO.setCgstValue(BigDecimal.ZERO);
			
			invoiceDO.setIgstValue(BigDecimal.ZERO);
			
			invoiceDO.setTaxableValue(BigDecimal.ZERO);
			
			invoiceDO.setInvoiceValue(BigDecimal.ZERO);
			Transaction transaction=session.beginTransaction();
			
			session.saveOrUpdate(invoiceDO);
			
			session.flush();
			
			transaction.commit();
			
			flag=true;
			
			
		}catch(Exception e) {
			LOGGER.error("Exception in InvoiceDao::cancelInvoiceById()::"+e);
		}
		
		LOGGER.info("InvoiceDao::calcelInvoice()::end");
		
		return flag;
		
	}
	
	public ClientDO getClientDoByRegisterId(String registerId) {
		LOGGER.info("invoiceDao::getClientDoByRegisterId()::start");
		ClientDO clientDO=null;
		
		try {
			
			Session session=getSession();
			
			CriteriaBuilder builder=session.getCriteriaBuilder();
			
			CriteriaQuery<ClientDO> query=builder.createQuery(ClientDO.class);
			
			Root<ClientDO> root=query.from(ClientDO.class);
			
			query.select(root);
			
			Predicate predicate=builder.equal(root.get("clientId"), registerId);
			
			query.where(predicate);
			
			clientDO=session.createQuery(query).getSingleResult();
			
		}catch(Exception e) {
			LOGGER.error("Excpetion in invoiceDao::getClientDoByRegisterId()::"+e);
		}
		
		LOGGER.info("invoiceDao::getClientDoByRegisterId()::end");
		return clientDO;
	}
	
	public CustomerDO getCustomerByName(String custName) {
		LOGGER.info("invoiceDao::getCustomerByName()::start");
		
		CustomerDO custDO=null;
		
		try {
			
			Session session=getSession();
			
			CriteriaBuilder builder=session.getCriteriaBuilder();
			
			CriteriaQuery<CustomerDO> query=builder.createQuery(CustomerDO.class);
			
			Root<CustomerDO> root=query.from(CustomerDO.class);
			
			query.select(root);
			
			Predicate predicate=builder.equal(root.get("customerName"), custName);
			
			query.where(predicate);
			
			custDO=session.createQuery(query).getSingleResult();
			
			
		}catch(Exception e) {
			
		}
		
		return custDO;
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

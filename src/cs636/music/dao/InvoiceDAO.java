package cs636.music.dao;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import cs636.music.domain.Invoice;
import cs636.music.domain.LineItem;


/**
 * 
 * Access Invoice table through this class. 
 * @author Chung-Hsien (Jacky) Yu
 */
public class InvoiceDAO {
	private DbDAO dbdao;
	
	/**
	 * An Data Access Object for Invoice table table
	 * @param db the database connection 
	 * @throws SQLException
	 */
	public InvoiceDAO(DbDAO db) {
		dbdao = db;
	}
	
	/**
	 * Insert invoice data into invoice and lineitem table
	 * Note: it's OK for the argued invoice object to
	 * reference a detached User or Product object here,
	 * because JPA only needs their ids for this insert
	 * and users and products are never deleted in this app.
	 * Also, it works to persist the line items before the
	 * invoice itself. JPA is smart enough to know to insert
	 * the invoice first whichever way we do the persists.
	 * @param invoice
	 */
	public void insertInvoice(Invoice invoice) {
		EntityManager em = dbdao.getEM();
		em.persist(invoice);
		for (LineItem item : invoice.getLineItems())
			em.persist(item);
	}
	
	/**
	 * find all unprocessed invoice
	 * @return all unprocessed invoice in db
	 */
	public Set<Invoice> findAllUnprocessedInvoices() {
		EntityManager em = dbdao.getEM();
//		TypedQuery<Invoice> q = 
//				em.createQuery("select i from Invoice i where i.isProcessed='n'", Invoice.class);
//		List<Invoice> invoices = q.getResultList();
		Query q = em.createNativeQuery("select * from invoice where is_processed = 'n'", Invoice.class);
		@SuppressWarnings("unchecked")
		List<Invoice> invoices = (List<Invoice>)q.getResultList();
		return new HashSet<Invoice>(invoices);
	}
	
	/**
	 * find all invoices
	 * @return all invoices in db
	 */
	public Set<Invoice> findAllInvoices() {
		EntityManager em = dbdao.getEM();
		TypedQuery<Invoice> q = 
				em.createQuery("select i from Invoice i", Invoice.class);
		List<Invoice> invoices = q.getResultList();
		return new HashSet<Invoice>(invoices);
	}
	
	/**
	 * Find an invoice by id
	 * @param invoiceId
	 */
	public Invoice findInvoice(long invoiceId){
		EntityManager em = dbdao.getEM();
		Invoice invoice = em.find(Invoice.class, invoiceId);
		return invoice;
	}
}

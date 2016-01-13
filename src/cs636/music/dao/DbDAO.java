package cs636.music.dao;

import static cs636.music.dao.DBConstants.DOWNLOAD_TABLE;
import static cs636.music.dao.DBConstants.INVOICE_TABLE;
import static cs636.music.dao.DBConstants.LINEITEM_TABLE;
import static cs636.music.dao.DBConstants.SYS_TABLE;
import static cs636.music.dao.DBConstants.USER_TABLE;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 * Database connection and initialization.
 * Implemented singleton on this class.
 * 
 * @author Chung-Hsien (Jacky) Yu
 * 
 */
public class DbDAO {
	   
	private EntityManagerFactory emf;
	private EntityManager em;

	public EntityManager getEM() {
		return em;
	}

	public DbDAO(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	public void startTransaction() {
		em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	}

	public void commitTransaction() {
		// the commit call can throw, and then the caller needs to rollback
		em.getTransaction().commit();
		// We are using an application-managed entity manager, so we need
		// to explicitly close it to release its resources.
		// See Keith & Schincariol, pg. 138, first paragraph.
		// By closing the em at the end of the transaction, we are
		// following the pattern of transaction-scoped entity managers
		// used in EJBs by default.
		em.close(); // this causes the entities to become detached
		em = null;  // to avoid use of closed em
	}

	public void rollbackTransaction() {
		try {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}
	
	// Exceptions occurring in JPA code are almost always fatal to the
	// EntityManager context, meaning that we need to rollback the transaction
	// (and also close the EntityManager in our setup) and start over
	// or fail the action. An exception to this rule is the NoResultException
	// from the method singleResult()--it's OK to handle the exception and
	// continue the EntityManager/transaction after that particular exception.
	// If the caller has already seen an exception, it probably
	// doesn't want to handle a failing rollback, so it can use this.
	// Then the caller should issue its own exception based on the
	// original exception.
	public void rollbackAfterException() {
		try {
			rollbackTransaction();
		} catch (Exception e) {
			// discard secondary exception--probably server can't be reached
		}
	}
	
	/**
	*  bring DB back to original state
	*  @throws  SQLException
	**/
	public void initializeDb() throws SQLException {
		clearTable(DOWNLOAD_TABLE);
		clearTable(LINEITEM_TABLE);
		clearTable(INVOICE_TABLE);
		clearTable(USER_TABLE);	
		initSysTable();
	}

	// We can use direct SQL for DB setup easily as follows.
	// Any SQLException is handled by EL, marking the
	// transaction as rollback-only, and then EL throws a
	// org.eclipse.persistence.exceptions.DatabaseException
	private void clearTable(String tableName) {
		Query q = em.createNativeQuery("delete from " + tableName);
		int n = q.executeUpdate(); // SQL of update shows in FINE logging
		System.out.println("deleted " + n + " rows from " + tableName);
	}
	
	/**
	*  Set all the index number used in other tables back to 1
	*  @throws  SQLException
	**/
	private void initSysTable() {
		System.out.println("inserting new id start values into " + SYS_TABLE);
		Query q = em.createNativeQuery("update " + SYS_TABLE + " set gen_val=0");
		q.executeUpdate();
	}
}

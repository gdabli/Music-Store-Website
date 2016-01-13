package cs636.music.config;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import cs636.music.dao.AdminDAO;
import cs636.music.dao.DbDAO;
import cs636.music.dao.DownloadDAO;
import cs636.music.dao.InvoiceDAO;
import cs636.music.dao.ProductDAO;
import cs636.music.dao.UserDAO;
import cs636.music.service.AdminService;
import cs636.music.service.AdminServiceAPI;
import cs636.music.service.UserService;
import cs636.music.service.UserServiceAPI;

/**
 * @author Betty O'Neil, modified by Chung-Hsien Yu
 *
 * Configure the service objects, shut them down
 * 
 */

public class MusicSystemConfig {
	// could be externally specified--
	public static final String SOUND_BASE_URL = "http://www.cs.umb.edu/cs636/music1-setup/sound/";
	// the service objects in use, representing all lower layers to the app
	private static AdminServiceAPI adminService;
	private static UserServiceAPI userService;
	// the lower-level service objects-- 
	private static DownloadDAO downloadDao;
	// invoiceDao also persists its LineItems
	private static InvoiceDAO invoiceDao;
	private static ProductDAO productDao;
	private static UserDAO userDao;
	private static AdminDAO adminDao;
	
	private static EntityManagerFactory emf;
	
	static{
		
		try {
			configureServices();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Music System Config: static block");
			e.printStackTrace();
		}
	}

	// set up service API, data access objects
	public static void configureServices()
		throws Exception {	
		try {
			// read persistence.xml, etc.
			emf = configureJPA(); 
			//tryDS();
			testEMF(emf);  // check we can connect, and try to report JDBC isolation level
			// Uncomment these to see isolation levels in DataSource Connection pools:			//tryDS("jdbc/dbs2");
			//tryDS("jdbc/mysql");
			//tryDS("jdbc/hsql");
			System.out.println("calling dbDAO constructor");
			DbDAO dbDAO = new DbDAO(emf);
			// configure rest of service and DAO singleton objects--
			// These objects get what they need at creation-time
			// This is "constructor injection"
			productDao = new ProductDAO(dbDAO);
			userDao = new UserDAO(dbDAO);
			downloadDao = new DownloadDAO(dbDAO);		
			invoiceDao = new InvoiceDAO(dbDAO);
			adminDao= new AdminDAO(dbDAO);
			
			adminService = new AdminService(dbDAO,downloadDao, invoiceDao, adminDao);
			userService = new UserService(productDao,userDao,downloadDao,invoiceDao, dbDAO);
		} catch (Exception e) {
			System.out.println("Problem with contacting DB");
		    // rethrow to notify caller (caller should print exception details)
			throw new Exception("Exception in configureServices",e); 
		}
	}
	
	// The configuration information is read from the persistence.xml file
	// on the classpath.  This may throw a RuntimeException.
	// During this call, TransactionSessionCustomizer is used
	// to set serializable isolation level.
	public static EntityManagerFactory configureJPA() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("music2el");
		return emf;
	}
	
	// Try to get a DataSource from tomcat's JNDI object repository config'd in context.xml
	// Uncomment the call to this above if interested
	@SuppressWarnings("unused")
	private static void tryDS(String dataSourceName) {
		try {
			System.out.println("starting tryDS: try to get a DataSource object for JNDI name " + dataSourceName);
			Context initCtx = new InitialContext(); // JNDI lookup object
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup(dataSourceName); // lookup JNDI name
			Connection conn = ds.getConnection();
			System.out
					.println("JDBC isolation level for " + dataSourceName + " (0=none,1=RU,2=RC,4=RR,8=SR) is "
							+ conn.getTransactionIsolation());
		} catch (javax.naming.NoInitialContextException e) {
			System.out.println("running outside of web container, i.e, in client-server context, so JNDI is unavailable");
		} catch	(Exception e) {
			System.out
					.println(" Exception trying to get isolation level from DS: "
							+ e + "\n Continuing...");
		}
	}
	
	// This method is not needed for setup: just testing early for ability to get an EM
	// Try a test EM session and get the isolation level, if such access is supported by this driver
	private static void testEMF(EntityManagerFactory emf) throws Exception
	{
		EntityManager em = null;
		System.out.println("starting testEMF");
		try {
			em = emf.createEntityManager();
		} catch (Throwable e) {
			throw new Exception("testEMF: can't get an EntityManager");  // bail out
		}
		Map<String, Object> props = emf.getProperties();
		System.out.println(props);
		System.out.println("testEMF: Got an EM");
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// dig down in software to get the actual JDBC Connection, if this driver will allow us...
		try {
			System.out.println("testEM: Trying to get JDBC Connection from EM (not always supported) ...");
			// This trick doesn't work for mysql, or some versions of it anyway
			Connection conn = em.unwrap(Connection.class);
			if (conn == null)
				System.out.println("failed to get underlying JDBC Connection from EM, so won't be able to get isolation level this way");
			else {
				System.out.println("Got Connection: JDBC isolation level (0=none,1=RU,2=RC,4=RR,8=SR) is " 
					+ conn.getTransactionIsolation());
			}
			tx.commit();
			em.close();
		} catch (Exception e) {
			System.out.println(" Exception trying to get isolation level: " + e + "\n Continuing...");
		}
	}

	// Compose an exception report
	// and return the string for callers to use
	public static String exceptionReport(Exception e) {
		String message = e.toString(); // exception name + message
		if (e.getCause() != null) {
			message += "\n  cause: " + e.getCause();
			if (e.getCause().getCause() != null) {
				message += "\n    cause's cause: " + e.getCause().getCause();
			}
		}
		message += "\n Stack Trace: " + exceptionStackTraceString(e);
		return message;
	}

	private static String exceptionStackTraceString(Throwable e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

	// call this to free up system resources
	// allocated by configureJPA(). 
	public static void shutdownServices() {
		if (emf != null && !emf.isOpen())
			emf.close();
	}
	
	// Let the apps get the business logic layer services
	public static AdminServiceAPI getAdminService() {
		return adminService;
	}

	public static UserServiceAPI getUserService() {
		return userService;
	}
}

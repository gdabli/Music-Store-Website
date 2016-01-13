package cs636.music.dao;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManagerFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cs636.music.config.MusicSystemConfig;
import cs636.music.domain.Product;

public class ProductDAOTest {
	private DbDAO dbDAO;
	private ProductDAO productdao;
	private static EntityManagerFactory emf;
	
	@BeforeClass
	public static void setUpClass() {
		// we usually use HSQLDB as a db for testing, but 
		// this can run for other DBs too
		// Note: need to load it first and "ant config-hsqldb"
		// to get the persistence.xml file onto the classpath
		// Do this part once for this whole class--takes some time
		emf = MusicSystemConfig.configureJPA();
	}

	@Before
	// each test runs in its own transaction, on same db setup
	public void setup() throws Exception {
		dbDAO = new DbDAO(emf);
		dbDAO.startTransaction();
		dbDAO.initializeDb(); // no orders, toppings, sizes
		dbDAO.commitTransaction();
		productdao = new ProductDAO(dbDAO);
	}

	@After
	public void tearDown() {
		// This executes even after an exception
		// so we need to rollback here in case of exception
		// (If the transaction was successful, it's already
		// committed, and this won't hurt.)
		dbDAO.rollbackAfterException();
	}
	@AfterClass
	public static void tearDownClass() throws Exception {
		MusicSystemConfig.shutdownServices();
	}

	
	@Test
	public void testFindProductByCode() throws Exception
	{
		dbDAO.startTransaction();
		Product p2 = productdao.findProductByCode("8601");
		dbDAO.commitTransaction();
		assertTrue(1 == p2.getId());
	}
}
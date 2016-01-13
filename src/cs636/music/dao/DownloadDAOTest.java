package cs636.music.dao;

import static org.junit.Assert.assertTrue;
import java.util.Date;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cs636.music.config.MusicSystemConfig;
import cs636.music.domain.Download;
import cs636.music.domain.Product;
import cs636.music.domain.User;

public class DownloadDAOTest {
	private DbDAO dbDAO;
	private DownloadDAO downloaddao;
	private ProductDAO productdao;
	private static EntityManagerFactory emf;
        private User user;  // set up in setup()

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
		dbDAO.initializeDb(); //  no users, etc.
		// Need a user to test Downloads
		user = new User();
		user.setEmailAddress("doe@joe.com");
		user.setFirstname("doe");
		user.setLastname("schmo");
		EntityManager em = dbDAO.getEM();
		em.persist(user);
		dbDAO.commitTransaction();
		downloaddao = new DownloadDAO(dbDAO);
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
	public void testInsertDownload() throws Exception
	{
		dbDAO.startTransaction();
		Product p = productdao.findProductByCode("8601");
		
		Download d = new Download();
		d.setDownloadDate(new Date());
		d.setUser(user);
		d.setTrack(p.getTracks().iterator().next());
		downloaddao.insertDownload(d);
		dbDAO.commitTransaction();
	}
	
	@Test
	public void testFindAllDownloads() throws Exception {
		dbDAO.startTransaction();
		Product p = productdao.findProductByCode("8601");
		
		Download d = new Download();
		d.setDownloadDate(new Date());
		d.setUser(user);
		d.setTrack(p.getTracks().iterator().next());
		downloaddao.insertDownload(d);
		
		Set<Download> downloads = downloaddao.findAllDownloads();
		assertTrue(1 == downloads.size());
		assertTrue("doe".equals(downloads.iterator().next().getUser().getFirstname()));
		
		dbDAO.commitTransaction();
	}
}

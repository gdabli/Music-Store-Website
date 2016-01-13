package cs636.music.dao;

import static cs636.music.dao.DBConstants.ADMIN_TABLE;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * 
 * Access admin related tables through this class.
 */
public class AdminDAO {

	private DbDAO dbDao;
	
	public AdminDAO (DbDAO db) {
		this.dbDao = db;
	}
	
	
	/**
	 * check login user name and password
	 * @param uid
	 * @param pwd
	 */
	public Boolean findAdminUser(String uid, String pwd) {
		// We can do a native query and avoid setting up a domain class
		// for the userpass table that isn't needed in the service layer.
		EntityManager em = dbDao.getEM();
		Query q = em.createNativeQuery("select count(*) from " + ADMIN_TABLE + 
				" userpass where username = '" + uid +
				"' and password = '" + pwd + "'");
		int count = ((Number)q.getSingleResult()).intValue();
		if (count > 0)
			return true;
		else
			return false;
	}
}

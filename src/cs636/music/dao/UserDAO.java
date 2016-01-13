
package cs636.music.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import cs636.music.domain.User;

/**
 * 
 * Access site_user table through this class.
 * @author Chung-Hsien (Jacky) Yu
 */
public class UserDAO {
	
	private DbDAO dbdao;

	
	/**
	 * An Data Access Object for site_user table
	 * @param db the database connection
	 */
	public UserDAO(DbDAO db) {
		dbdao = db;
	}
	
	/**
	 * 
	 * @param usr the site_user domain contains user data
	 */
	public void insertUser(User user) {
		System.out.println("user persisted");
		dbdao.getEM().persist(user);
	}
	
	/**
	 * Find a user from site user table by its email
	 * @param email user's email we try to find
	 * @return an User object if exist, or return null 
	 */
	public User findUserByEmail(String email) {
		EntityManager em = dbdao.getEM();
		TypedQuery<User> q = em.createQuery("select u from User u where u.emailAddress = '" + email + "'", User.class);
		List<User> user = q.getResultList();
		if (user.size() > 0)
			return user.get(0);
		else
			return null;
	}
	

}

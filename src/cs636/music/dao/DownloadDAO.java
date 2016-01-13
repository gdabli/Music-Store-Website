
package cs636.music.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import cs636.music.domain.Download;

/**
 * 
 * Access Download table through this class. 
 * @author Chung-Hsien (Jacky) Yu
 */
public class DownloadDAO {
	
	private DbDAO dbdao;

	/**
	 * An Data Access Object for Download table
	 * @param db the database connection
	 * @param user_db using the site user table 
	 */
	public DownloadDAO(DbDAO db ){
		dbdao = db;
	}

	/**
	 * insert a download history to download table
	 * Note: it's OK for download to reference a detached User
	 * and/or Track in this call, since only their ids
	 * are needed, and User and Track are never deleted
	 * in this app.
	 * @param download
	 */
	public void insertDownload(Download download) {
		EntityManager em = dbdao.getEM();
		em.persist(download);
	}
	
	
	/**
	 * find all downloads
	 * @return all download history in a Set
	 */
	public Set<Download> findAllDownloads() {
		EntityManager em = dbdao.getEM();
		TypedQuery<Download> q = em.createQuery("select d from Download d", Download.class);
		List<Download> downloads = q.getResultList();
		Set<Download> downloadSet = new HashSet<Download>(downloads);
		return downloadSet;
	}
}

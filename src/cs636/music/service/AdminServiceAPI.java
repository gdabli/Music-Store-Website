package cs636.music.service;

import java.util.Set;

import cs636.music.domain.Download;
import cs636.music.domain.Invoice;

public interface AdminServiceAPI {

	public void initializeDB() throws ServiceException;
	
	/**
	 * process the invoice
	 * @param invoice_id
	 * @throws ServiceException
	 */	
	public void processInvoice(long invoice_id) throws ServiceException;
	
	/**
	 * Get a list of all invoices, including line items and user details
	 * @return list of all invoices
	 * @throws ServiceException
	 */
	
	public Set<Invoice> getListofInvoices() throws ServiceException;
	/**
	 * Get a list of all unprocessed invoices, no details
	 * @return list of all unprocessed invoices
	 * @throws ServiceException
	 */
	public Set<Invoice> getListofUnprocessedInvoices() throws ServiceException;
	
	/**
	 * Get a list of all downloads, including track, product, and user details
	 * @return list of all downloads
	 * @throws ServiceException
	 */
	public Set<Download> getListofDownloads() throws ServiceException;
	
	/**
	 * Check login user
	 * @param username
	 * @param password
	 * @return true if useranme and password exist, otherwise return false
	 * @throws ServiceException
	 */
	public Boolean checkLogin(String username,String password) throws ServiceException;
}

package cs636.music.service;

import java.util.Set;

import cs636.music.domain.Cart;
import cs636.music.domain.Product;
import cs636.music.domain.Track;
import cs636.music.domain.User;

public interface UserServiceAPI {
	
	/**
	 * Getting list of all products
	 * 
	 * @return list of all product
	 * @throws ServiceException
	 */
	public Set<Product> getProductList() throws ServiceException;
	
	/**
	 * Create a new cart
	 * 
	 * @return the cart
	 */
	public Cart createCart();
	
	/**
	 * Add a product to the cart. If the product is already in the cart, add
	 * quantity. Otherwise, insert a new line item.
	 * 
	 * @param prod
	 * @param cart
	 * @param quantity
	 */
	public void addItemtoCart(Product prod, Cart cart, int quantity);
	
	/**
	 * Change the quantity of one item. If quantity <= 0 then delete this item.
	 * 
	 * @param prod
	 * @param cart
	 * @param quantity
	 */
	public void changeCart(Product prod, Cart cart, int quantity);
	
	/**
	 * Remove a product item from the cart
	 * 
	 * @param prod
	 * @param cart
	 */
	public void removeCartItem(Product prod, Cart cart);

	/**
	 * Register user if the email does not exist in the db, otherwise, get the
	 * user info from db
	 * 
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @return the user info
	 * @throws ServiceException
	 */
	public User registerUser(String firstname, String lastname, String email)
			throws ServiceException;
	
	/**
	 * Get user info by given email address
	 * 
	 * @param email
	 * @return the user info found, return null if not found
	 * @throws ServiceException
	 */
	public User getUserInfo(String email) throws ServiceException;
	
	/**
	 * Return a product info by given product code
	 * 
	 * @param prodCode
	 *            product code
	 * @return the product info
	 * @throws ServiceException
	 */
	public Product getProduct(String prodCode) throws ServiceException;
	
	/**
	 * Check out the cart from the user order and then generate an invoice for
	 * this order. Empty the cart after
	 * 
	 * @param cart
	 * @param user
	 * @throws ServiceException
	 */
	public void checkout(Cart cart, User user) throws ServiceException;
	
	/**
	 * Add one download history, record the user and track
	 * 
	 * @param usr
	 *            user who download the track
	 * @param track
	 *            the track which was downloaded
	 * @throws ServiceException
	 */
	public void addDownload(User usr, Track track) throws ServiceException;
	
}

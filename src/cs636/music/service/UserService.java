package cs636.music.service;

import java.util.Date;
import java.util.Set;

import cs636.music.dao.DbDAO;
import cs636.music.dao.DownloadDAO;
import cs636.music.dao.InvoiceDAO;
import cs636.music.dao.ProductDAO;
import cs636.music.dao.UserDAO;
import cs636.music.domain.Cart;
import cs636.music.domain.Download;
import cs636.music.domain.Invoice;
import cs636.music.domain.LineItem;
import cs636.music.domain.Product;
import cs636.music.domain.Track;
import cs636.music.domain.User;

/**
 * 
 * Provide user level services to user app through accessing DAOs
 * 
 */
public class UserService implements UserServiceAPI {

	private DownloadDAO downloadDb;
	private InvoiceDAO invoiceDb;
	private ProductDAO prodDb;
	private UserDAO userDb;
	private DbDAO db;

	/**
	 * construct a user service provider
	 * 
	 * @param productDao
	 * @param userDao
	 * @param downloadDao
	 * @param lineItemDao
	 * @param invoiceDao
	 */
	public UserService(ProductDAO productDao, UserDAO userDao,
			DownloadDAO downloadDao, InvoiceDAO invoiceDao, DbDAO dbdao) {
		downloadDb = downloadDao;
		invoiceDb = invoiceDao;
		prodDb = productDao;
		userDb = userDao;
		this.db = dbdao;
	}

	/**
	 * Getting list of all products
	 * 
	 * @return list of all product
	 * @throws ServiceException
	 */
	public Set<Product> getProductList() throws ServiceException {
		try {
			db.startTransaction();
			Set<Product> prodList = prodDb.findAllProducts();
			// Load tracks for all products, so they can be accessed
			// after commit, even if lazy loading is in use,
			// in presentation code (not needed for SystemTest)
			for (Product product : prodList) {
				for (Track track : product.getTracks())
					track.getSampleFilename();
			}
			db.commitTransaction();
			return prodList;
		} catch (Exception e) {
			db.rollbackAfterException();
			throw new ServiceException("Can't find product list in db: ", e);
		}
	}

	/**
	 * Create a new cart
	 * 
	 * @return the cart
	 */
	public Cart createCart() {
		return new Cart();
	}

	/**
	 * Add a product to the cart. If the product is already in the cart, add
	 * quantity. Otherwise, insert a new line item.
	 * 
	 * @param prod
	 * @param cart
	 * @param quantity
	 */
	public void addItemtoCart(Product prod, Cart cart, int quantity) {
		LineItem item = cart.findItem(prod);
		if (item != null) { // product is already in the cart, add quantity
			int qty = item.getQuantity();
			item.setQuantity(qty + quantity);
		} else { // not in the cart, add new item with quantity
			item = new LineItem();
			item.setProduct(prod);
			item.setQuantity(quantity);
			// cart.addItem(item);
			cart.getItems().add(item);
		}
	}

	/**
	 * Change the quantity of one item. If quantity <= 0 then delete this item.
	 * 
	 * @param prod
	 * @param cart
	 * @param quantity
	 */
	public void changeCart(Product prod, Cart cart, int quantity) {
		LineItem item = cart.findItem(prod);
		if (item != null) {
			if (quantity > 0) {
				item.setQuantity(quantity);
			} else { // if the quantity was changed to 0 or less, remove the
						// product from cart
				cart.removeItem(prod);
			}
		}
	}

	/**
	 * Remove a product item from the cart
	 * 
	 * @param prod
	 * @param cart
	 */
	public void removeCartItem(Product prod, Cart cart) {
		LineItem item = cart.findItem(prod);
		if (item != null) {
			cart.removeItem(prod);
		}
	}

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
			throws ServiceException {
		User user = null;
		try {
			System.out.println("inside register user");
			db.startTransaction();
			user = userDb.findUserByEmail(email);
			System.out.println("user registered 2");
			if (user == null) { // this user has not registered yet
				user = new User();
				user.setFirstname(firstname);
				user.setLastname(lastname);
				user.setEmailAddress(email);
				System.out.println("user registered 3");

				userDb.insertUser(user);
				System.out.println("user registered 4");

			}
			db.commitTransaction();
		} catch (Exception e) {
			db.rollbackAfterException();
			throw new ServiceException("Error while registering user: ", e);
		}
		return user;
	}

	/**
	 * Get user info by given email address
	 * 
	 * @param email
	 * @return the user info found, return null if not found
	 * @throws ServiceException
	 */
	public User getUserInfo(String email) throws ServiceException {

		try {
			db.startTransaction();
			User user = userDb.findUserByEmail(email);
			db.commitTransaction();
			return user;
		} catch (Exception e) {
			db.rollbackAfterException();
			throw new ServiceException("Error while getting user info: ", e);
		}
	}

	/**
	 * Return a product info by given product code
	 * 
	 * @param prodCode
	 *            product code
	 * @return the product info
	 * @throws ServiceException
	 */
	public Product getProduct(String prodCode) throws ServiceException {
		try {
			db.startTransaction();
			Product prd = prodDb.findProductByCode(prodCode);
			// (System test needs track info for a Product)
			// Without the following access loop, Eclipselink runs
			// a query outside the em lifetime to get this Track
			// data, apparently using info it has in the emf
			for (Track track : prd.getTracks())
				track.getSampleFilename();
			db.commitTransaction();
			return prd;
		} catch (Exception e) {
			db.rollbackAfterException();
			throw new ServiceException("Error while registering user: ", e);
		}
	}

	/**
	 * Check out the cart from the user order and then generate an invoice for
	 * this order. Empty the cart after
	 * 
	 * @param cart
	 * @param user
	 * @throws ServiceException
	 */
	public void checkout(Cart cart, User user) throws ServiceException {

		try {
			db.startTransaction();
			Invoice invoice = new Invoice();
			invoice.setInvoiceDate(new Date());
			for (LineItem item : cart.getItems()) {
				item.setInvoice(invoice);
			}
			invoice.setLineItems(cart.getItems());
			invoice.setIsProcessed("n");
			invoice.setUser(user);
			invoice.setTotalAmount(invoice.calculateTotalAmount());

			// Note that the LineItems here are new, never yet
			// managed by JPA.  Each LineItem has a ref to a Product
			// and the invoice has a ref to a User, and these objects
			// are detached.  We are using the User and Product objects as part
			// of the object graph being processed in insertInvoice, but it's
			// OK because only the user_id and product_id are used, and 
			// JPA providers  are willing to take an id out of a detached 
			// object. Our app never deletes or updates a User or a Product, 
			// so these ids should still be there, and correct.
			// This is noted in the header comment to insertInvoice
			// in InvoiceDAO.
			invoiceDb.insertInvoice(invoice);
			db.commitTransaction();
			cart.clear();
		} catch (Exception e) {
			db.rollbackAfterException();
			throw new ServiceException("Can't check out: ", e);
		}
	}

	/**
	 * Add one download history, record the user and track
	 * 
	 * @param usr
	 *            user who download the track
	 * @param track
	 *            the track which was downloaded
	 * @throws ServiceException
	 */
	public void addDownload(User usr, Track track) throws ServiceException {
		try {
			db.startTransaction();
			Download download = new Download();
			download.setUser(usr);
			download.setTrack(track);
			download.setDownloadDate(new Date());
			// See comment in DownloadDAO on this method: 
			// usr and track are detached objects but never deleted, 
			// and JPA just needs their ids for the insert
			downloadDb.insertDownload(download);
			db.commitTransaction();
		} catch (Exception e) {
			db.rollbackAfterException();
			throw new ServiceException("Can't add download: ", e);
		}
	}
}

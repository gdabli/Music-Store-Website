package cs636.music.presentation;

import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

import cs636.music.config.MusicSystemConfig;
import cs636.music.domain.Cart;
import cs636.music.domain.Product;
import cs636.music.domain.Track;
import cs636.music.domain.User;
import cs636.music.service.ServiceException;
import cs636.music.service.UserServiceAPI;

/**
 * 
 * Line-oriented client code for Music Site user. To be replaced by JSP pages
 * later. 
 * 
 */
/**
 * @author eoneil, modified by Chung-Hsine Yu
 * 
 */
public class UserApp {
	/**
	 * 
	 * Since the page flow in this app is tree-like, plus back arrows to the
	 * topmost Catalog page, we can implement it easily with methods so that
	 * execution calls down the page tree and returns back to the top level. In
	 * a more general page flow, we would need to have one top-level command
	 * loop dispatching to various commands each of which returns with a value
	 * that specifies which command to do next, and possibly arguments for it.
	 * (Actually, this page flow is not quite a tree, because you can reach the
	 * Cart page from several other pages, but since you always go back to the
	 * Catalog page after that, we can still just return from wherever after
	 * calling the Cart page method.)l
	 * 
	 */

	private UserServiceAPI userService;
	private Scanner in; // the user input source

	// state held in the client--
	private User user = null; // the user once registered
	private Cart cart = null; // the shopping cart for the user

	// also note the product variable in processProduct page, also held across
	// service calls.

	public UserApp() throws Exception {
		MusicSystemConfig.configureServices();
		userService = MusicSystemConfig.getUserService();
		in = new Scanner(System.in);
	}

	public static void main(String[] args) {

		try {
			UserApp myApp = new UserApp();
			System.out.println("starting User app");
			myApp.handleCatalogPage();
			System.out.println("Thanks for visiting the Band's site.");
		} catch (Exception e) {
			System.out.println("Error in run: StackTrace for it: ");
			e.printStackTrace();
			System.out.println("Error in run, shorter report: " + MusicSystemConfig.exceptionReport(e));
		}
	}

	public void handleCatalogPage() throws IOException, ServiceException {
		Set<Product> prodlist;
		Product prod = null;
		while (true) {
			System.out.println("---Catalog Page---");
			prodlist = this.userService.getProductList();
			System.out.println("Displaying list of CDs: product codes and descriptions");
			PresentationUtils.displayCDCatlog(prodlist, System.out);
			String productCode = PresentationUtils.readEntry(in,"Enter the product code of product to see info, or S to show cart or Q to quit");
			if (productCode.equalsIgnoreCase("S")) {
				if (cart == null)
					cart = userService.createCart();
				handleCartPage(null);
				continue;
			} else if (productCode.equalsIgnoreCase("Q"))
				return;
			// Now product is chosen, use it on Product page
			prod = this.userService.getProduct(productCode);
			if (prod != null) {
				processProduct(prod);
			}
			else {
				System.out.println(" Can't find the product: " + productCode + " !!!");
			}
				
		}
	}

	/**
	 * Here when user has selected a particular product. Let them work with it,
	 * listening to tracks, registering, etc. and when done, we'll return to
	 * Catalog page.
	 * 
	 * Since the Catalog page and the Sound page share so many user commands, we
	 * use this one method to handle both, with a variable "listening" to tell
	 * which one we're handling at the moment: listening == false: Product Page
	 * commands A S L B listening == true: Sound Page commands T A S B
	 * 
	 * @throws IOException
	 */
	public void processProduct(Product product) throws IOException, ServiceException{
		// listening == true means the user has used the L command to listen
		// to tracks since first coming here from the Catalog page
		boolean listening = false;

		while (true) {
			if (listening) {
				System.out.println("---Sound Page---");
				System.out.println(" Displaying list of tracks: track# and title");
				PresentationUtils.displayTracks(product, System.out);			
			} else {
				System.out.println("---Product Page--");
				System.out.println("displaying product info for this product");
				PresentationUtils.displayProductInfo(product, System.out);
			}

			String command = null;
			// creating empty Cart if none yet
			// Note: calling service layer to create cart
			if (cart == null)
				cart = this.userService.createCart();
			System.out.println("Possible Commands");
			if (listening){
				System.out.println("T: Track# to play");
			}
			System.out.println("A: Add CD to Cart");
			System.out.println("S: Show Cart - has other options like CheckOut");
			if (!listening) {
				System.out.println("L: Listen to Sample ");
			}
			System.out.println("B: Browse Catalog");

			command = PresentationUtils.readEntry(in,"Please Enter the Command");

			if (command.equalsIgnoreCase("A")) {
				// add this CD to cart
				handleCartPage(product);
				return; // redisplay whole catalog after add-to-cart
			} else if (command.equalsIgnoreCase("S")) {
				// show cart
				handleCartPage(null);
				return; // redisplay whole catalog after display of cart

			} else if (command.equalsIgnoreCase("L") && (!listening)) {
				// creating User if necessary (required for download)
				if (user == null)
				   user = userUI(); // do simple registration if needed
				listening = true; // switch this method to the Sound page

			} else if (command.equalsIgnoreCase("T") && listening) {
				String trackNumberString = PresentationUtils.readEntry(in,
						"Please enter track# to download & play");
				int trackNumber = Integer.parseInt(trackNumberString);
				System.out.println("getting Track " + trackNumber	
				 	+ ", recording download and fake-playing track");
				// user stays on Sound page (listening == true)
				Track track = product.findTrackByNumber(trackNumber);
				if (track != null) {
					PresentationUtils.playTrack(track, System.out);
					userService.addDownload(user, track);
				} else{
					System.out.println("Can't find track #" + trackNumber);
				}
			} else if (command.equalsIgnoreCase("B")) {  // browse command
				return;  // back to catalog page
			} else
				System.out.println("Invalid Command: " + command + "! Try again...");
		}
	}

	/**
	 * Handle Cart page actions (pg. 664) No product is singled out at this
	 * point, except that the argued newProduct, if non-null, is being added to
	 * the cart here. Return to top level when done here.
	 * 
	 */

	public void handleCartPage(Product newProduct) throws IOException, ServiceException {
		System.out.println("---Cart Page---");
		// if newProduct is non-null, adding it to Cart
		if (newProduct != null){
			this.userService.addItemtoCart(newProduct, cart, 1);
		}

		while (true) {
			System.out.println("displaying Cart");
			PresentationUtils.displayCart(cart, System.out);
			System.out.println("Possible Commands");
			System.out.println("C: Change to Cart");
			System.out.println("R: Remove from Cart ");
			System.out.println("O: Check Out ");
			System.out.println("B: Browse Catalog");
			String command = PresentationUtils.readEntry(in,
					"Please Enter the Command");

			if (command.equalsIgnoreCase("C")) {
				String productCode = PresentationUtils.readEntry(in,
						"Enter the product code of product to change");

				int quantity = Integer.parseInt(PresentationUtils.readEntry(in,
						"Enter the new quantity of the product"));
				System.out.println("calling service layer to update Cart for product"
								+ productCode + ", quantity " + quantity);
				Product prd = userService.getProduct(productCode);
				if (prd == null) {
					System.out.println(" Can't find the product: " + productCode + " !!!");
				} else {
					userService.changeCart(prd, cart, quantity);
				}
			} else if (command.equalsIgnoreCase("R")) {
				String productCode = PresentationUtils.readEntry(in,
						"Enter the product code for removal");
				System.out.println("calling service layer to remove product "
						+ productCode + " from cart");
				Product prd = userService.getProduct(productCode);
				if (prd == null) {
					System.out.println(" Can't find the product: " + productCode + " !!!");
				} else {
					userService.removeCartItem(prd, cart);
				}
			} else if (command.equalsIgnoreCase("O")) {
				processCheckOut();
				return;
			} else if (command.equalsIgnoreCase("B")) {
				return;
			} else
				System.out.println("Invalid Command!");
		}
	}

	public void processCheckOut() throws IOException, ServiceException {
		if (cart.getItems().size()>0)
		{		
			System.out.println("---CheckOut Page---");			
			if (user == null)
				user = userUI();
			System.out.println("\nCreating order for:");
			System.out.println("displaying Cart");
			PresentationUtils.displayCart(cart, System.out);
			System.out.println("calling service layer to checkout");
			// Note: passing user to service layer here
			// so the service layer doesn't have to save user info itself
			// (idea of stateless service layer)
			userService.checkout(cart, user);
			System.out.println("\nThank you for your order, your cart is now empty.\n");

		} else {
			System.out.println("The cart is empty!!");
		}
		
	}

	public User userUI() throws IOException, ServiceException{
		System.out.println("---User Registration Page---");
		User registeredUser = null;
		String fName = PresentationUtils.readEntry(in,
				"\n Give us a few registration details:\n\t\t FirstName: ");
		String lName = PresentationUtils.readEntry(in, "\n\t\t LastName: ");
		String eMail = PresentationUtils.readEntry(in, "\n\t\t Email: ");
		System.out.println("calling service layer to register user: " + fName
				+ " " + lName + " " + eMail);
		// Note: creating User object in service layer, not here!
		registeredUser = this.userService.registerUser(fName,lName,eMail);
		PresentationUtils.displayUserInfo(registeredUser, System.out);

		return registeredUser;
	}

}

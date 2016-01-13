package cs636.music.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
/**
 * A Cart is a memory-only POJO holding Products that the
 * user has chosen but not yet bought via "checkout".
 * At checkout, an Invoice with the same set of LineItems
 * is created and inserted in the database.
 * Like pg. 649, except:
 * --uses Set rather than List for items
 * --no setItems: caller should do addItem one by one.
 * --has findItem to find a certain product's item in the cart.
 *
 */
public class Cart implements Serializable {

	private static final long serialVersionUID = 1L;
	private Set<LineItem> items;
    /**
     * Construct a new Cart to hold items  
     */
	public Cart() {
		items = new HashSet<LineItem>();
	}
	
    /**
     * Obtain all items in this cart
     * @return all items in the cart 
     */
	public Set<LineItem> getItems() {
		return items;
	}
	
	/**
	 * Find an item of this cart through its product id.
	 * @param product the product id
	 * @return the item in this cart with the given product id. 
	 */
	public LineItem findItem(Product product) {
		// This could be done by product code rather than id
		long prodId = product.getId(); 

		for (LineItem l : items) {
			if (l.getProduct().getId() == prodId) {
				return l;
			}
		}
		return null;
	}

	/**
	 * Add an item into this cart. 
	 * If the item already exists in the cart, only the quantity is changed. 
	 * @param item
	 */
	public void addItem(LineItem item) {
		// If the item already exists in the cart, only the quantity is changed.
		long prodId = item.getProduct().getId();
		int quantity = item.getQuantity();

		for (LineItem l : items) {

			if (l.getProduct().getId() == prodId) {
				l.setQuantity(quantity);
				return;
			}
		}
		// here if item not there yet
		items.add(item);
	}

	/**
	 * Remove an item with given product id from this cart 
	 * @param product the product need to be removed 
	 */
	public void removeItem(Product product) {
		long prodId = product.getId(); 

		for (LineItem l : items) {
			if (l.getProduct().getId() == prodId) {
				items.remove(l);
				return;
			}
		}
	}
	

	/**
	 * clean out cart (for end of checkout)
	 */
	public void clear() {
		items.clear();
	}
}
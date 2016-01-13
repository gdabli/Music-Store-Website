package cs636.music.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;


/**
 * The persistent class for the LINEITEM database table.
 * 
 */
@Entity
@Table(name="LINEITEM")
public class LineItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name="LineItemIdGen",
			table = "music_id_gen",
			pkColumnName = "GEN_NAME",
			valueColumnName = "GEN_VAL",
			pkColumnValue = "lineitemid_gen")
	@GeneratedValue(generator="LineItemIdGen")
	@Column(name="LINEITEM_ID")
	private long lineitemId;

	@Column(nullable=false)
	private int quantity;

	//bi-directional many-to-one association to Invoice
    @ManyToOne
	@JoinColumn(name="INVOICE_ID", nullable=false)
	private Invoice invoice;

	//uni-directional many-to-one association to Product
    @ManyToOne
	@JoinColumn(name="PRODUCT_ID", nullable=false)
	private Product product;

    public LineItem() {
    }

	public long getLineitemId() {
		return this.lineitemId;
	}

	public void setLineitemId(long lineitemId) {
		this.lineitemId = lineitemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Invoice getInvoice() {
		return this.invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public BigDecimal calculateItemTotal() {
		// We can't use * to multiply with BigDecimal, but it knows how--
		BigDecimal total = product.getPrice().multiply(new BigDecimal(quantity));
		return total;
	}
}
package cs636.music.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the INVOICE database table.
 * 
 */
@Entity
@Table(name="INVOICE")
public class Invoice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name="InvoiceIdGen",
			table = "music_id_gen",
			pkColumnName = "GEN_NAME",
			valueColumnName = "GEN_VAL",
			pkColumnValue = "invoiceid_gen")
	@GeneratedValue(generator="InvoiceIdGen")
	@Column(name="INVOICE_ID")
	private long invoiceId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="INVOICE_DATE", nullable=false)
	private Date invoiceDate;

	@Column(name="IS_PROCESSED", length=1)
	private String isProcessed;

	@Column(name="TOTAL_AMOUNT", nullable=false, precision=10, scale=2)
	private BigDecimal totalAmount;

	//uni-directional many-to-one association to User
    @ManyToOne//( cascade = CascadeType.PERSIST)
	@JoinColumn(name="USER_ID", nullable=false)
	private User siteUser;

	//bi-directional many-to-one association to Lineitem
	@OneToMany(mappedBy="invoice")
	private Set<LineItem> lineitems;

    public Invoice() {
    }

	public long getInvoiceId() {
		return this.invoiceId;
	}

	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Date getInvoiceDate() {
		return this.invoiceDate;
	}

	public void setInvoiceDate(Date date) {
		this.invoiceDate = date;
	}

	public String getIsProcessed() {
		return this.isProcessed;
	}
	
	public boolean isProcessed() {
		return isProcessed.equals("y");
	}

	public void setIsProcessed(String isProcessed) {
		this.isProcessed = isProcessed;
	}

	public BigDecimal getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public User getUser() {
		return this.siteUser;
	}

	public void setUser(User siteUser) {
		this.siteUser = siteUser;
	}
	
	public Set<LineItem> getLineItems() {
		return this.lineitems;
	}

	public void setLineItems(Set<LineItem> lineitems) {
		this.lineitems = lineitems;
	}
	
	public BigDecimal calculateTotalAmount() {
		BigDecimal total = new BigDecimal(0);
		for (LineItem item : lineitems) {
			total = total.add(item.calculateItemTotal());
		}
		return total;
	}
	
}
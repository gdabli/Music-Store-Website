package cs636.music.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 * The persistent class for the PRODUCT database table.
 * 
 */
@Entity
@Table(name="PRODUCT",
	uniqueConstraints = @UniqueConstraint(columnNames="PRODUCT_CODE"))
@Cacheable

public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PRODUCT_ID")
	private long productId;

	@Column(name="PRODUCT_CODE", nullable=false, length=10)
	private String productCode;

	@Column(name="PRODUCT_DESCRIPTION", nullable=false, length=100)
	private String productDescription;

	@Column(name="PRODUCT_PRICE", nullable=false, precision=10, scale=2)
	private BigDecimal productPrice;

	//bi-directional one-to-many association to Track
	@OneToMany(mappedBy="product",cascade=CascadeType.MERGE)
	private Set<Track> tracks;

    public Product() {
    }

	public long getId() {
		return this.productId;
	}

	public void setId(long productId) {
		this.productId = productId;
	}

	public String getCode() {
		return this.productCode;
	}

	public void setCode(String productCode) {
		this.productCode = productCode;
	}

	public String getDescription() {
		return this.productDescription;
	}

	public void setDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public BigDecimal getPrice() {
		return this.productPrice;
	}

	public void setPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public Set<Track> getTracks() {
		return this.tracks;
	}

	public void setTracks(Set<Track> tracks) {
		this.tracks = tracks;
	}
	
	public Track findTrackByNumber(int trackNum) {
		for (Track t : tracks) {
			if (t.getTrackNumber() == trackNum)
				return t;
		}
		return null;
	}
}
package cs636.music.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the DOWNLOAD database table.
 * 
 */
@Entity
@Table(name="DOWNLOAD")
public class Download implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@TableGenerator(name="DownloadIdGen",
			table = "music_id_gen",
			pkColumnName = "GEN_NAME",
			valueColumnName = "GEN_VAL",
			pkColumnValue = "downloadid_gen")
	@GeneratedValue(generator="DownloadIdGen")
	@Column(name="DOWNLOAD_ID")
	private long downloadId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DOWNLOAD_DATE", nullable=false)
	private Date downloadDate;
	
	//uni-directional many-to-one association to User
    @ManyToOne
	@JoinColumn(name="USER_ID", nullable=false)
	private User siteUser;
    
	//uni-directional many-to-one association to Track
    @ManyToOne
	@JoinColumn(name="TRACK_ID", nullable=false)
	private Track track;

    public Download() {
    }

	public long getDownloadId() {
		return this.downloadId;
	}

	public void setDownloadId(long downloadId) {
		this.downloadId = downloadId;
	}

	public Date getDownloadDate() {
		return this.downloadDate;
	}

	public void setDownloadDate(Date downloadDate) {
		this.downloadDate = downloadDate;
	}
	
	public User getUser() {
		return this.siteUser;
	}

	public void setUser(User siteUser) {
		this.siteUser = siteUser;
	}
	
	public Track getTrack() {
		return this.track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}
	
}
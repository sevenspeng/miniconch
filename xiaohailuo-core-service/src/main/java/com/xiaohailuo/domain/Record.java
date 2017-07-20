package com.xiaohailuo.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
//import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.xiaohailuo.bean.CommentInfo;

public class Record {
	SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private String id;
	private String title;
	private String coordinates;
	private BigDecimal lat;
	private BigDecimal lng;
	private String isOfficial;
	private String uid;
	private String summary;
	private String icon;
	private String recordFile;
	private Integer replyCount;
	private Integer likeCount;
	private String description;
	//private Date/*Timestamp*/ recordDate;
	private String recordDate;
	private String poi;
	private String cityCode;
	private String url;
	private String timeLength;
	
	private List<CommentInfo> comments;

	/*public Record(String id, String title, String summary, String isOfficial, String icon, Date recordDate,
			String timeLength, String recordFile, Integer replyCount, Integer likeCount, String description,
			String coordinates, BigDecimal lat, BigDecimal lng, String uid, String poi, String cityCode, String url) {
		super();
		this.id = id;
		this.title = title;
		this.summary = summary;
		this.isOfficial = isOfficial;
		this.icon = icon;
		this.recordDate = recordDate;
		this.timeLength = timeLength;
		this.recordFile = recordFile;
		this.replyCount = replyCount;
		this.likeCount = likeCount;
		this.description = description;
		this.coordinates = coordinates;
		this.lat = lat;
		this.lng = lng;
		this.uid = uid;
		this.poi = poi;
		this.cityCode = cityCode;
		this.url = url;
	}*/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getIsOfficial() {
		return isOfficial;
	}

	public void setIsOfficial(String isOfficial) {
		this.isOfficial = isOfficial;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	/*public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(DateTimestamp recordDate) {
		//dateFormater.format(recordDate);
		this.recordDate = recordDate;
	}*/
	
	public String getRecordDate() {
		return recordDate;
	}
	
	/*public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}*/
	public void setRecordDate(Date recordDate) {
		this.recordDate = dateFormater.format(recordDate);
	}

	public String getTimeLength() {
		return timeLength;
	}

	public void setTimeLength(String timeLength) {
		this.timeLength = timeLength;
	}

	public String getRecordFile() {
		return recordFile;
	}

	public void setRecordFile(String recordFile) {
		this.recordFile = recordFile;
	}

	public Integer getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public BigDecimal getLat() {
		return lat;
	}

	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

	public BigDecimal getLng() {
		return lng;
	}

	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPoi() {
		return poi;
	}

	public void setPoi(String poi) {
		this.poi = poi;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<CommentInfo> getComments() {
		return comments;
	}

	public void setComments(List<CommentInfo> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Record [id=" + id + ", title=" + title + ", summary=" + summary + ", isOfficial=" + isOfficial
				+ ", icon=" + icon + ", recordDate=" + recordDate + ", timeLength=" + timeLength + ", recordFile="
				+ recordFile + ", replyCount=" + replyCount + ", likeCount=" + likeCount + ", description="
				+ description + ", coordinates=" + coordinates + ", lat=" + lat + ", lng=" + lng + ", uid=" + uid
				+ ", poi=" + poi + ", cityCode=" + cityCode + ", url=" + url + "]";
	}

}

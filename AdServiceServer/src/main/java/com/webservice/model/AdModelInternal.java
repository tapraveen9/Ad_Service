package main.java.com.webservice.model;

public class AdModelInternal {
	
	private String partnerId;
	private String adContent;
	private long duration;
	
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getAdContent() {
		return adContent;
	}
	public void setAdContent(String adContent) {
		this.adContent = adContent;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public void copyAdModel(AdModel model) {
		model.setAdContent(adContent);
		model.setPartnerId(partnerId);
		long sysTime = System.currentTimeMillis();
		long netTime = duration-sysTime;
		float time = netTime/1000;
		model.setDuration(time);
	}

}

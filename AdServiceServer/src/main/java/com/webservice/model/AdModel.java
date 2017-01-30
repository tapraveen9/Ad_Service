package main.java.com.webservice.model;

public class AdModel  {
	
	private String partnerId;
	private String adContent;
	private float duration;
	
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
	public float getDuration() {
		return duration;
	}
	public void setDuration(float duration) {
		this.duration = duration;
	}
	
	public void copyAdModel(AdModelInternal model) {
		model.setAdContent(adContent);
		model.setPartnerId(partnerId);
		long time = (long)(duration*1000);
		long netTime = System.currentTimeMillis()+time;
		model.setDuration(netTime);
	}

}

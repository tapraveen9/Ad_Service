package main.java.com.webservice.worker;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import main.java.com.webservice.model.*;

public class CleanUpWorkerThread implements Runnable{
	Map<String,AdModelInternal> modelCache_;
	
	public CleanUpWorkerThread(Map<String,AdModelInternal> modelCache_) {
		this.modelCache_ = modelCache_;
	}

	@Override
	public void run() {
		
		try {
		//keep polling the map and see for the ads that are out of duration and delete them
		while(true) {
		   if(modelCache_!=null && !modelCache_.isEmpty()) {
			   Iterator<Entry<String, AdModelInternal>> it = modelCache_.entrySet().iterator();
			   while(it.hasNext()) {
				   Entry<String,AdModelInternal> entry = it.next();
				   String key = entry.getKey();
				   AdModelInternal model = entry.getValue();
				   //get the duration which is in long milli secs
				   long sysTime = System.currentTimeMillis();
				   long duration = model.getDuration();
				   if(sysTime > duration) {
					   it.remove();
					   System.out.println("worker task removed the ad with partner id: "+key);
				   }
			   }
			   //make thread sleep for 5 secs
			   System.out.println("worker task put to sleep.....");
			   Thread.sleep(5000);
		   }
		}
	}catch(Exception e) {
		System.out.println(e.getMessage());
	}
		
	}

}

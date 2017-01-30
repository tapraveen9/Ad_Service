package main.java.com.webservice.accessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.com.webservice.model.AdModel;
import main.java.com.webservice.model.AdModelInternal;
import main.java.com.webservice.util.JsonHelper;
import main.java.com.webservice.util.ResultObject;
import main.java.com.webservice.worker.CleanUpWorkerThread;

public class ModelAccessor {
	private static ModelAccessor instance;
	private Map<String,AdModelInternal> modelCache_;
	private boolean isWorkerThreadStarted;
	
	/*constructor generates only one instance of
	 * this class
	 * */
	public static ModelAccessor getInstance() {
		if (instance == null) {
			synchronized (ModelAccessor.class) {
				if (instance == null) {
					instance = new ModelAccessor();
					instance.init();
				}
			}
		}
		return instance;
	}
	
	private void init()
	             {
		System.out.println("initializing the ModelAccessor init() method....");
	    //initialize the cache and synchronize it externally
		modelCache_ = Collections.synchronizedMap(new HashMap<String,AdModelInternal>());
		//start the worker task for polling
		initWorkerThread();
		System.out.println("ModelAccessor init() complete....");
	  }
	
	public  ResultObject getModel(String key) {
		System.out.println(" printing contents of the datastore for logging");
		System.out.println(getAllModels()+" size of the store is: "+modelCache_.size());
		ResultObject result = new ResultObject();
		try {
		//if key is null or empty throw exception
		if(key == null || key.isEmpty()) {
			//return error message
			//return error message 
			String message = "Ads cannot be retrieved , partner id provided is empty";
			//set the status code
			result.setStatusCode(200);
			result.setMessage(message);
			return result;
		}
		System.out.println("logging key received: "+key);
		AdModelInternal model = modelCache_.get(key);
		//if model null then return error message
		if(model == null) {
			//return error message 
			String message = "ad with the provided partner id: "+key+" doesnt exist";
			//set the status code
			result.setStatusCode(200);
			result.setMessage(message);
			return result;
		}
		//convert it to AdModel before returning
		AdModel temp = new AdModel();
		model.copyAdModel(temp);
		String message = JsonHelper.getInstance().convertToJson(temp);
		//set the status code
		result.setStatusCode(200);
		result.setMessage(message);
		}catch(Exception e) {
			//log the exception
			System.out.println(e);
			String message = " request failed due to an error on server , apologize for the inconvenience";
			//set the status code
			result.setStatusCode(500);
			result.setMessage(message);
			return result;
		}
		
		return result;
	}
	
	public  ResultObject insertModel(AdModel model) {
		System.out.println(" printing contents of the datastore for logging");
		System.out.println(getAllModels()+" size of the store is: "+modelCache_.size());
		ResultObject result = new ResultObject();
		String key = model.getPartnerId();
		try {
		//if key is null or empty throw exception
		if(key == null || key.isEmpty()) {
			//return error message 
			String message = "Ads cannot be retrieved , partner id provided is empty";
			//set the status code
			result.setStatusCode(200);
			result.setMessage(message);
			return result;
		}
		AdModelInternal temp = modelCache_.get(key);
		//if a record already exists dont insert and return an error
		if(temp!=null) {
			//dont insert and return an error
			String message="Ad cannot be created , a similar ad with the partner id"+key+ "already exists";
			//set the status code
			result.setStatusCode(200);
			result.setMessage(message);
			return result;
		}
		//if null then insert in to cache
		//valide the model
		
		//create a internal model and insert it to the cache
		temp = new AdModelInternal();
		model.copyAdModel(temp);
		modelCache_.put(key, temp);
		//return success message
		String message = "Ad with partner id: "+key +"is successfully created";
		//set the status code
		result.setStatusCode(200);
		result.setMessage(message);
		}catch(Exception e) {
			//if key is inserted in to the cache , undo it
			if(modelCache_!=null && !modelCache_.isEmpty() && modelCache_.containsKey(key)) {
				modelCache_.clear();
			}
			//log the exception
			System.out.println(e);
			String message = " Ad creation failed due to an error on server , apologize for the inconvenience";
			//set the status code
			result.setStatusCode(500);
			result.setMessage(message);
			return result;
		}
		
		return result;
		
	}
	
	public  AdModel updateModel(String key) {
		return null;
	}
	
	public  AdModel deleteModel(String key) {
		return null;
	}
	
	public  ResultObject getAllModels() {
		ResultObject result = new ResultObject();
		try {
		//if mode cache not empty
		if(modelCache_.isEmpty()) {
			//return message saying empty
			String message = "no ads currently exist in the datastore";
			//set the status code
			result.setStatusCode(200);
			result.setMessage(message);
			return result;
		}
		List<AdModel> models = new ArrayList<AdModel>(modelCache_.size());
		for(AdModelInternal value:modelCache_.values()) {
			AdModel temp = new AdModel();
			value.copyAdModel(temp);
			models.add(temp);
		}
		//convert the object to json
		String message = JsonHelper.getInstance().convertToJson(models);
		//set the status code
		result.setStatusCode(200);
		result.setMessage(message);
		}catch(Exception e) {
			System.out.println(e);
			String message = " Ad creation failed due to an error on server , apologize for the inconvenience";
			//set the status code
			result.setStatusCode(500);
			result.setMessage(message);
		}
		return result;
	}
	
	private void initWorkerThread() {
		//start the polling thread that cleans up the inactive tasks
				Runnable workerTask = new CleanUpWorkerThread(modelCache_);
				try {
					Thread workerThread = new Thread(workerTask);
					workerThread.start();
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				isWorkerThreadStarted = true;
				System.out.println("worker thread started and initiated....");
	}
	
	/*Important - Only to be called by testng suite
	 * 
	 * */
	public void clearModelCache() {
		if(modelCache_!=null && !modelCache_.isEmpty()) {
			modelCache_.clear();
		}
	}
	
	
	

}

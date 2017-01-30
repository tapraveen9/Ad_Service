package main.java.com.webservice.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.exc.UnrecognizedPropertyException;

import main.java.com.webservice.exceptions.AdServiceException;


public class JsonHelper {
	public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	private static JsonHelper instance_ = new JsonHelper();


	private ObjectMapper objectMapper_;

	private JsonHelper ()
	{
		objectMapper_ = new ObjectMapper();
		DateFormat sdf = new SimpleDateFormat(DATETIME_PATTERN);
        objectMapper_.setDateFormat(sdf);
	}

	public static JsonHelper getInstance()
	{
		return instance_;
	}
	
	public <T> T convertFromJson(String src, Class<T> clazz) throws AdServiceException
	{
		T t = null;
		try
		{
			if (src != null && !src.isEmpty())
			{
				t =  objectMapper_.readValue(src, clazz);
			}
		}catch(JsonParseException e) {
			System.out.println(e.getMessage());
	    	throw new AdServiceException("request cannot be completed.error in processing the input.Please check the json format and syntax and re-submit");
		}
		catch(UnrecognizedPropertyException e) {
		    System.out.println(e.getMessage());
	    	throw new AdServiceException("request cannot be completed.error in processing the input. Please check the field names and values in the input and re-submit");
		}
		catch(JsonMappingException e) {
			System.out.println(e.getMessage());
			String message = "request cannot be completed.error in processing the input. "+
			                 "please check the values assigned.partner id should be a string type, "+
					          "ad content should be a string type, "+
			                  "duration should be float type";
			throw new AdServiceException(message);
		}
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	    	throw new RuntimeException("Can not convert json to object.Please check the json format , syntax and field names and re-submit", e);
	    }

		return t;
	}
	
	public String convertToJson(Object object)
	{
		String json = null;
		try
		{
			json = objectMapper_.writeValueAsString(object);
		}
	    catch (Exception e)
	    {
	    	throw new RuntimeException("Can not convert object to json.", e);
	    }

		return json;
	}
	

}


package main.java.com.webservice.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import main.java.com.webservice.accessor.ModelAccessor;
import main.java.com.webservice.exceptions.AdServiceException;
import main.java.com.webservice.model.AdModel;
import main.java.com.webservice.util.JsonHelper;
import main.java.com.webservice.util.ResultObject;


@Path("/")
public class AdServiceController {
	
	ModelAccessor modelAccessor_ = null;
	
	@POST
	@Path("/ad")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@SOAPBinding(style=Style.DOCUMENT, use=Use.LITERAL)
	public Response insertAd(InputStream inData) {
		ResultObject result = null;
		try {
		StringBuilder adBuilder = new StringBuilder();
			BufferedReader in = new BufferedReader(new InputStreamReader(inData));
			String line = null;
			while ((line = in.readLine()) != null) {
				adBuilder.append(line);
			}
		System.out.println("Data Received: " + adBuilder.toString());
		Class clazz = AdModel.class;
		AdModel model = (AdModel)JsonHelper.getInstance().convertFromJson(adBuilder.toString(), clazz);
		modelAccessor_ = ModelAccessor.getInstance();
		result = modelAccessor_.insertModel(model);
		}catch(Exception e) {
			e.printStackTrace();
			String message = " request failed due to an error on server , apologize for the inconvenience";
			result = new ResultObject();
			if(e instanceof AdServiceException) {
				message = e.getMessage();
				//set the status code
				result.setStatusCode(400);
			}
			else {
				//set the status code
				result.setStatusCode(500);
				}
			result.setMessage(message);
			return Response.status(result.getStatusCode()).entity(result.getMessage()).build();
		}
		// return HTTP response 200 in case of success
		return Response.status(result.getStatusCode()).entity(result.getMessage()).build();
	}
 
	
	@GET
	@Path("/ad/{partner}")
	@Produces(MediaType.APPLICATION_JSON)
	@SOAPBinding(style=Style.DOCUMENT, use=Use.LITERAL)
	public Response getAds(@PathParam("partner") String key,
			InputStream inData) {
		ResultObject result = null;
		try {
		if(key == null || key.isEmpty()) {
			String message = "cannot retrieve ad , the partner parameter is not valid";
			result = new ResultObject();
			result.setStatusCode(200);
			result.setMessage(message);
		}
		else {
			modelAccessor_ = ModelAccessor.getInstance();
		result = modelAccessor_.getModel(key);
		}
		}catch(Exception e) {
			System.out.println(e);
			String message = " request failed due to an error on server , apologize for the inconvenience";
			//set the status code
			result.setStatusCode(500);
			result.setMessage(message);
			return Response.status(result.getStatusCode()).entity(result.getMessage()).build();
		}
		// return HTTP response 200 in case of success
		return Response.status(result.getStatusCode()).entity(result.getMessage()).build();
	}
	
	
	@PUT
	@Path("/ad")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@SOAPBinding(style=Style.DOCUMENT, use=Use.LITERAL)
	public Response updateAd(InputStream inData) {
		String result = "service Successfully started.."+"update() api called";
		 
		// return HTTP response 200 in case of success
		return Response.status(200).entity(result).build();
	}
	
	
	@DELETE
	@Path("/ad")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@SOAPBinding(style=Style.DOCUMENT, use=Use.LITERAL)
	public Response deleteAd(InputStream inData) {
		String result = "service Successfully started.."+"delete() api called";
		 
		// return HTTP response 200 in case of success
		return Response.status(200).entity(result).build();
	}
	

	@GET
	@Path("/allAds")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@SOAPBinding(style=Style.DOCUMENT, use=Use.LITERAL)
	public Response getAllAds(InputStream inData) {
		ResultObject result = null;
		modelAccessor_ = ModelAccessor.getInstance();
		result = modelAccessor_.getAllModels();
		// return HTTP response 200 in case of success
		return Response.status(result.getStatusCode()).entity(result.getMessage()).build();
	}

}

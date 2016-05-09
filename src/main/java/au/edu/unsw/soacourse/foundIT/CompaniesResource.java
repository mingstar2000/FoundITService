package au.edu.unsw.soacourse.foundIT;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import au.edu.unsw.soacourse.foundIT.dao.CompaniesDao;
import au.edu.unsw.soacourse.foundIT.model.Company;

@Path("/companies")
public class CompaniesResource {
	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	//get specific company profile
	//TODO: consider response
	@GET	
	@Path("{cmpID}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Company getCompany(@PathParam("cmpID") String cmpid) {
		
		//System.out.println(cmpid);
		Company c = CompaniesDao.instance.getCompany(cmpid).get(cmpid);
		if(c==null)
			throw new RuntimeException("GET: Company with" + cmpid +  " not found");
		
		return c; 
	}
	
    // create new company profile
	//TODO: consider response
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Company newCompany(
			@FormParam("name") String name,
			@FormParam("email") String email,
			@FormParam("addr") String addr,
			@FormParam("telNum") String telNum,
			@FormParam("indType") String indType,
			@FormParam("webSite") String webSite,
			@FormParam("cmpDsp") String cmpDsp
	) throws IOException {
		//create new cmpID
		//TODO: need to decide what the rule is....
		int count = CompaniesDao.instance.getCompany().size();
		String cmpid = String.valueOf(count + 1);
		
		//create new company using 'not null columns'
		Company c = new Company(cmpid, name, email);
		
		//check others and set each parameter into the company profile
		if (addr!=null) c.setAddr(addr);
		if (telNum!=null) c.setTelNum(telNum);
		if (indType!=null) c.setIndType(indType);
		if (webSite!=null) c.setWebSite(webSite);
		if (cmpDsp!=null) c.setCmpDsp(cmpDsp);
		
    	System.out.println("new company name is = " + c.getName());
		
		Company new_c = CompaniesDao.instance.setCompany(c).get(cmpid);
		if(new_c==null)
			throw new RuntimeException("Create new company profile failed!");
		
		return new_c;
	}
	
    // update company profile
	//TODO: consider response
	//TODO: when the id doesn't exist, we need to consider new company profile?
	@PUT
	@Path("{cmpID}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Company updateCompany(
			@PathParam("cmpID") String cmpid,
			@FormParam("name") String name,
			@FormParam("email") String email,
			@FormParam("addr") String addr,
			@FormParam("telNum") String telNum,
			@FormParam("indType") String indType,
			@FormParam("webSite") String webSite,
			@FormParam("cmpDsp") String cmpDsp
	) throws IOException {

    	System.out.println("company id is = " + cmpid);
    	
		Company c = new Company(cmpid, name, email);	
		//check others and set each parameter into the company profile
		if (addr!=null) c.setAddr(addr);
		if (telNum!=null) c.setTelNum(telNum);
		if (indType!=null) c.setIndType(indType);
		if (webSite!=null) c.setWebSite(webSite);
		if (cmpDsp!=null) c.setCmpDsp(cmpDsp);
		
		Company updated_c = CompaniesDao.instance.setCompany(cmpid, c).get(cmpid);
		if(updated_c==null)
			throw new RuntimeException("Create new company profile failed!");
		
		return updated_c;
	}

	//delete specific company profile
	@DELETE	
	@Path("{cmpID}")
	public Response deleteCompany(@PathParam("cmpID") String cmpid) {
		
		Company c = CompaniesDao.instance.setCompany(cmpid).get(cmpid);
		//TODO: consider response
		if(c != null)
			Response.serverError().build();
		
		return Response.ok().build(); 
	}
}

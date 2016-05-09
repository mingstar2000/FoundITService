package au.edu.unsw.soacourse.foundIT.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import au.edu.unsw.soacourse.foundIT.model.Company;

public enum CompaniesDao {

    instance;

    private Map<String, Company> contentStore = new HashMap<String, Company>();
   
    
    public Map<String, Company> getCompany(){

    	connectDB("select * from tb_companyProfile");
    	       
        return contentStore;
    } 
    
    public Map<String, Company> getCompany(String cmpID){

    	connectDB("select * from tb_companyProfile where cmpID = '" + cmpID + "';");
    	
        return contentStore;
    } 
    
    //create new company profile
    //TODO: consider example value like 'Sunny's company' -> it can cause error because of "'"
    public Map<String, Company> setCompany(Company c){

    	String queryStatement = "insert into tb_companyProfile values('";
    	
    	queryStatement = queryStatement + c.getId() + "' , '";
    	queryStatement = queryStatement + c.getName() + "' , '";
    	queryStatement = queryStatement + c.getEmail() + "' , '";
    	queryStatement = queryStatement + c.getAddr() + "' , '";
    	queryStatement = queryStatement + c.getTelNum() + "' , '";
    	queryStatement = queryStatement + c.getIndType() + "' , '";
    	queryStatement = queryStatement + c.getWebSite() + "' , '";
    	queryStatement = queryStatement + c.getCmpDsp() + "');";
    	    	
    	System.out.println(queryStatement);
    	
    	//insert new data into DB
    	connectDB(c.getId(), queryStatement);
    	
        return contentStore;
    } 
    
    //delete existing company profile
    public Map<String, Company> setCompany(String cmpid){

    	String queryStatement = "delete from tb_companyprofile where cmpID = '" + cmpid + "';";

    	System.out.println(queryStatement);
    	
    	//delete existing data from DB
    	connectDB(cmpid, queryStatement);
    	
        return contentStore;
    } 
    
    //update existing company profile
    public Map<String, Company> setCompany(String cmpid, Company c){

    	String queryStatement = "update tb_companyProfile set ";
    	
    	queryStatement = queryStatement + "cmpID = '" + c.getId() + "' , ";
    	queryStatement = queryStatement + "Name = '" + c.getName() + "' , ";
    	queryStatement = queryStatement + "Email = '" + c.getEmail() + "' , ";
    	queryStatement = queryStatement + "Addr = '" + c.getAddr() + "' , ";
    	queryStatement = queryStatement + "TelNum = '" + c.getTelNum() + "' , ";
    	queryStatement = queryStatement + "IndType = '" + c.getIndType() + "' , ";
    	queryStatement = queryStatement + "WebSite = '" + c.getWebSite() + "' , ";
    	queryStatement = queryStatement + "CmpDsp = '" + c.getCmpDsp() + "'";
    	queryStatement = queryStatement + "where cmpID = '" + cmpid + "';";
    	    	
    	System.out.println(queryStatement);
    	
    	//update new data into DB
    	connectDB(c.getId(), queryStatement);
    	
        return contentStore;
    }
       
    //get existing data
    private void connectDB(String querySatement){
 
        try  
        {
         String cmpID, name, email;
         contentStore.clear();
         
         Class.forName("org.sqlite.JDBC");  
         Connection conn = DriverManager.getConnection("jdbc:sqlite:c:/cs9322-Prac/workspace/FoundITService/database/foundITServer.db");  
         Statement statement = conn.createStatement();
         
         //get existing data from DB
         ResultSet rs = statement.executeQuery(querySatement);
         while (rs.next()) {
        	cmpID = rs.getString("cmpID");
        	name = rs.getString("name");
        	email = rs.getString("email");
            Company c = new Company(cmpID, name, email);
     		if (rs.getString("addr")!=null) c.setAddr(rs.getString("addr"));
    		if (rs.getString("telNum")!=null) c.setTelNum(rs.getString("telNum"));
    		if (rs.getString("indType")!=null) c.setIndType(rs.getString("indType"));
    		if (rs.getString("webSite")!=null) c.setWebSite(rs.getString("webSite"));
    		if (rs.getString("cmpDsp")!=null) c.setCmpDsp(rs.getString("cmpDsp"));             
            contentStore.put(cmpID, c);            
        }  
        rs.close();  
        statement.close();  
        conn.close();
        }  
        catch( Exception e )  
        {  
         e.printStackTrace ();  
        }
    }
    
    //create or update data
    private void connectDB(String cmpid, String querySatement){
    	 
        try  
        {
         String cmpID, name, email;
         contentStore.clear();
         
         Class.forName("org.sqlite.JDBC");  
         Connection conn = DriverManager.getConnection("jdbc:sqlite:c:/cs9322-Prac/workspace/FoundITService/database/foundITServer.db");  
         Statement statement = conn.createStatement();
         
         //create or update data
         statement.executeUpdate(querySatement);
         
         //get created or updated data from DB 
         ResultSet rs = statement.executeQuery("select * from tb_companyprofile where cmpID = " + cmpid + ";");
         while (rs.next()) {
        	cmpID = rs.getString("cmpID");
        	name = rs.getString("name");
        	email = rs.getString("email");
            Company c = new Company(cmpID, name, email);
      		if (rs.getString("addr")!=null) c.setAddr(rs.getString("addr"));
     		if (rs.getString("telNum")!=null) c.setTelNum(rs.getString("telNum"));
     		if (rs.getString("indType")!=null) c.setIndType(rs.getString("indType"));
     		if (rs.getString("webSite")!=null) c.setWebSite(rs.getString("webSite"));
     		if (rs.getString("cmpDsp")!=null) c.setCmpDsp(rs.getString("cmpDsp"));             
            contentStore.put(cmpID, c);            
        }  
        rs.close();  
        statement.close();  
        conn.close();
        }  
        catch( Exception e )  
        {  
         e.printStackTrace ();  
        }
    }

}

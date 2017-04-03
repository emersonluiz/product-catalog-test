package br.com.emersonluiz.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.UrlValidator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import br.com.emersonluiz.exception.FailureException;

public class Validator {

	public boolean validate(FileReader schema, String json) throws FailureException {
		
		JSONParser parser = new JSONParser();
		
		try {
			
			//Schema
			Object obj1 = parser.parse(schema);
			JSONObject jsonObject1 = (JSONObject) obj1;
            JSONArray objSchema = (JSONArray) jsonObject1.get("schema");           
			
            //Data
			Object objData = parser.parse(json);
			
			if (objData.toString().indexOf("[") == 0) {
				JSONArray jsonObject = (JSONArray) objData;
				verifyObjects(objSchema, jsonObject);
			} else {
				JSONObject jsonObject = (JSONObject) objData;
				verifyObject(objSchema, jsonObject);
			}
            
		} catch (IOException e) {
			throw new FailureException("IO Error");
		} catch (ParseException e) {
			throw new FailureException("Json Parse Error");
		}
		
		return true;
	}
	
	private void verifyObjects(JSONArray schema, JSONArray data) throws FailureException {
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iterator = data.iterator();
        while (iterator.hasNext()) {
        	JSONObject ob = iterator.next(); 
        	verifyObject(schema, ob);
        }
	}

	private void verifyObject(JSONArray schema, JSONObject data) throws FailureException {
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iterator = schema.iterator();
        while (iterator.hasNext()) {
        	
        	JSONObject ob = iterator.next(); 
        	
        	//verify mixed            	
        	if (ob.get("type").equals("mixed")) {
        		if (data.get(ob.get("name")).toString().indexOf("[") != -1) {
        			verifyArray(data, ob);
            	} else {
            		verifyString(data, ob);
            	}
            	continue;
        	}
        	
        	//verify array
        	if (ob.get("type").equals("array")) {
        		if (data.get(ob.get("name")).toString().indexOf("[") == -1) {
        			throw new FailureException("Field '" + ob.get("name") + "' is invalid! ("+data.get(ob.get("name")).toString()+")");
        		}
        		verifyArray(data, ob);
        		
        		continue;
        	}
        	
        	//verify string
        	verifyString(data, ob);
        	
        	//verify double
        	if (ob.get("type").equals("double")) {
        		String value = (String) data.get(ob.get("name"));
        		
        		if (value != null) {
	        		Pattern pattern = Pattern.compile("^[0-9]{1,}\\.\\d{2,2}");
	                Matcher matcher = pattern.matcher(value);
	         
	                if(!matcher.find()) {     	
	                	
	                	pattern = Pattern.compile("^[0-9]{1,}$");
	                    matcher = pattern.matcher(value);
	                    
	                    if(!matcher.find()) {                    	
	                    	throw new FailureException("Field '" + ob.get("name") + "' is invalid! ("+data.get(ob.get("name")).toString()+")");
	                    }
	                }
        		}
        	}
        }
	}

	private void verifyArray(JSONObject jsonObject, JSONObject ob) throws FailureException {
		JSONArray node = (JSONArray) jsonObject.get(ob.get("name"));
		if ((Boolean) ob.get("required")) {
			if (node == null) {
				throw new FailureException("Field '" + ob.get("name") + "' is required! ("+jsonObject.get(ob.get("name")).toString()+")");
			} else {
				if (node.size() < 1) {
					throw new FailureException("Field '" + ob.get("name") + "' is required! ("+jsonObject.get(ob.get("name")).toString()+")");
				}
			}
			
		}
	}

	private void verifyString(JSONObject data, JSONObject schema) throws FailureException {
		
		String value = (String) data.get(schema.get("name"));
		if ((Boolean) schema.get("required")) {        		
			if (value == null) {
				throw new FailureException("Field '" + schema.get("name") + "' is required!");
			}
			if (value.trim().equals("")) {
				throw new FailureException("Field '" + schema.get("name") + "' is required!");
			}	
		}
		verifyImageExtension(schema, data);
	}
	
	private void verifyImageExtension(JSONObject schema, JSONObject data) throws FailureException {
		if (schema.get("image") != null) {
			if ((Boolean) schema.get("image")) {
				if (data.get(schema.get("name")) != null) {
					String text = data.get(schema.get("name")).toString();
		    		if (text != null) {
		    			String extensions = ".jpg|.jpeg|.png|.gif";
		    			if (text.indexOf(".") != -1) {
			    			String extension = text.substring(text.lastIndexOf("."), text.length());
			    			if (!extensions.contains(extension)) {
			    				throw new FailureException("Field '" + schema.get("name") + "' is invalid! ("+data.get(schema.get("name")).toString()+")");
			    			} else {
			    				String[] schemes = {"http","https"}; 
			    				UrlValidator urlValidator = new UrlValidator(schemes);
			    				if (!urlValidator.isValid( data.get(schema.get("name")).toString() )) {
			    				   throw new FailureException("URL is invalid! ("+data.get(schema.get("name")).toString()+")");
			    				}
			    			}
		    			} else {
		    				throw new FailureException("Field '" + schema.get("name") + "' is invalid! ("+data.get(schema.get("name")).toString()+")");
		    			}
		    		}
				}
			}
    	}
	}
	
}

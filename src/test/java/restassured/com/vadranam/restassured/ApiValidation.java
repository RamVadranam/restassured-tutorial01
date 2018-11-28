package restassured.com.vadranam.restassured;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class ApiValidation {
	
	String baseURL =  "https://reqres.in";
	
	
	@Test
	public void validateGetResponse() {
		
		RestAssured.baseURI = baseURL;
		RequestSpecification request = RestAssured.given();
		
		Response response = request.get("/api/users?page=2");
		
		int responseCode = response.getStatusCode();
		
		Assert.assertEquals(responseCode, 200);
		
		ResponseBody body = response.getBody();
		
		String responseString = body.asString();
		
		Assert.assertTrue(responseString.contains("first_name"));
		
		JsonPath jsonpathEvaluation = response.jsonPath();
		
		Assert.assertEquals(jsonpathEvaluation.get("data[0].id"), 4);
		Assert.assertEquals(jsonpathEvaluation.get("data[0].first_name"), "Eve");
		Assert.assertEquals(jsonpathEvaluation.get("data[0].last_name"), "Holt");
		Assert.assertEquals(jsonpathEvaluation.get("data[0].avatar"), "https://s3.amazonaws.com/uifaces/faces/twitter/marcoramires/128.jpg");		
			
	}
	
	@Test
	public void validatePost() {
		
		RestAssured.baseURI = baseURL;
		RequestSpecification request = RestAssured.given();
		
		request.header("Content-Type","application/json");
		
		JSONObject object = new JSONObject();
		object.put("name", "morpheus");
		object.put("job", "leader");
		
		request.body(object.toJSONString());
		
		Response response = request.post("/api/users");
		
		ResponseBody body = response.body();
		
	    String responseString = body.asString();
	    
	    Assert.assertTrue(responseString.contains("id"));
	    Assert.assertTrue(responseString.contains("createdAt"));
	    
		
		int responseCode = response.getStatusCode();
		
		Assert.assertEquals(responseCode, 201);
		
		JsonPath jsonPath = response.jsonPath();
		
		Assert.assertEquals(jsonPath.get("name"), "morpheus");
		Assert.assertEquals(jsonPath.get("job"), "leader");
		
		
	
	}

}

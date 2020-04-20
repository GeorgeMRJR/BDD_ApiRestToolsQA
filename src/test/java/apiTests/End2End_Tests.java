   
   /////////////////////////////////////////////////////////////////////////
  ////////classe mantida para fins didaticos / ainda em andamento//////////
 /////////////////////////////////////////////////////////////////////////

package apiTests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class End2End_Tests {
	
	private static Map<String, String> login;
	private static String token;
	private static String userId;
	private static String isbn;
	
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://bookstore.toolsqa.com";
		
		userId = "ded1d37d-a222-4f1a-91e5-0f8fe6f41bdf";
		isbn = "9781449325862";

    	login = new HashMap<String, String>();
//		login.put("UserName", "gtest"+new Random().nextInt(1000));
		login.put("UserName", "gtest586");
		login.put("Password", "Abc@123");
		System.out.println(login);
		
	    	  
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}
	
//    @Test
    public void teste_0_deveRegistarUmUsuario() {

    	userId = 
    			given()
    				.body(login)
    				.contentType(ContentType.JSON)
    			.when()
    				.post("/Account/v1/User")
    			.then()
					.statusCode(201)
					.extract()
			    	.path("userID").toString()    	
		;
    	System.out.println(userId);
    }
    
    @Test
    public void teste_1_deveFazerLogin() {
    	token = given()
    		.body(login)
    		.contentType(ContentType.JSON)
    	.when()
			.post("/Account/v1/GenerateToken")
    	.then()
    	.statusCode(200)
    	.body("status", is("Success"))
    	.body("token", notNullValue())
    	.extract().path("token").toString()    	
    	;
    	
    	 
    }
    
    @Test
    public void teste_2_deveObterListaDeLivros() {
    	
    	String listaDeLivros = given()
    		.header("Authorization", "Bearer " + token)
    		.contentType(ContentType.JSON)
    	.when()
			.get("/BookStore/v1/Books")
    	.then()
    	.statusCode(200)
    	.extract().body().asString()
    	;
    	System.out.println(listaDeLivros);
    }
    
    @Test
    public void teste_3_deveAdicionarLivroNaListaDeLivros() {

    	given()
    		.header("Authorization", "Bearer " + token)
    		.body(
    				"{\"userId\": \""+userId+"\",\"collectionOfIsbns\": [{\"isbn\": \""+isbn+"\"}]}"
    				)
    		.contentType(ContentType.JSON)
    	.when()
			.post("/BookStore/v1/Books")
    	.then()
    	.statusCode(201)
    	;
    }
    
    @Test
    public void teste_4_deveRemoverLivroNaListaDelivros() {
    given()
		.header("Authorization", "Bearer " + token)
		.body(
				"{\"userId\": \""+userId+"\",\"collectionOfIsbns\": [{\"isbn\": \""+isbn+"\"}]}"
				)
		.contentType(ContentType.JSON)
	.when()
		.delete("/BookStore/v1/Book")
	.then()
	.statusCode(200)
	;
    }
    
    @Test
    public void teste_5_deveVerificarListaVasiaDeLivrosDoUsuario() {
	    given()
	    .contentType(ContentType.JSON)
			.header("Authorization", "Bearer " + token)
		.when()
			.get("/Account/v1/User/"+userId)
		.then()
		.statusCode(204)
		;
    }
}

package apiEngine;

import apiEngine.model.requests.AddBooksRequest;
import apiEngine.model.requests.AuthorizationRequest;
import apiEngine.model.requests.RemoveBookRequest;
import apiEngine.model.responses.Token;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EndPoints {
	private final RequestSpecification request;

	public EndPoints(String baseUrl) {
		RestAssured.baseURI = baseUrl;
		request = RestAssured.given();
		request.header("Content-Type", "application/json");

		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}

	public void authenticateUser(AuthorizationRequest authRequest) {
		Response response = request.body(authRequest).post(Route.generateToken());

		this.verificadorStatusCode2xx(response);

		Token tokenResponse = response.body().jsonPath().getObject("$", Token.class);
		request.header("Authorization", "Bearer " + tokenResponse.token);

	}

	public Response getBooks() {
		Response response = request.get(Route.books());
		this.verificadorStatusCode2xx(response);

		return response;
	}

	public Response addBook(AddBooksRequest bookRequest) {
		Response response = request.body(bookRequest).post(Route.books());
		this.verificadorStatusCode2xx(response);

		return response;
	}

	public Response removeBook(RemoveBookRequest rmBookRequest) {
		Response response = request.body(rmBookRequest).delete(Route.book());
		this.verificadorStatusCode2xx(response);

		return response;
	}

	public Response getUserAccount(String userId) {
		Response response = request.get(Route.userAccount(userId));
		this.verificadorStatusCode2xx(response);

		return response;
	}

	private void verificadorStatusCode2xx(Response response) {
		Integer statusCode = response.statusCode();
		if (statusCode.toString().indexOf("2") != 0) {
			System.out.println(
//			throw new RuntimeException(
					"<<!>>Falha! Status Code:" + response.statusCode() + ". Conteudo da Resposta: "
					+ response.asString());
		}

	}
}

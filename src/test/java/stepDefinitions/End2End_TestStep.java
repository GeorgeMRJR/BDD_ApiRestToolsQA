package stepDefinitions;

import org.junit.Assert;

import apiEngine.EndPoints;
import apiEngine.model.Book;
import apiEngine.model.requests.AddBooksRequest;
import apiEngine.model.requests.AuthorizationRequest;
import apiEngine.model.requests.ISBN;
import apiEngine.model.requests.RemoveBookRequest;
import apiEngine.model.responses.Books;
import apiEngine.model.responses.UserAccount;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;

public class End2End_TestStep {
	private static final String BASE_URL = "http://bookstore.toolsqa.com";
	private final String USER_ID = "0379b61b-8035-41cb-8181-08c605c7aae8";
	private Response response;
	private Book book;
	private EndPoints endPoints;

	public End2End_TestStep() {
		endPoints = new EndPoints(BASE_URL);
	}

	@Dado("Que sou um usuario autorizado")
	public void queSouUmUsuarioAutorizado() {

		endPoints.authenticateUser(new AuthorizationRequest("02gtest", "Abc@123"));
	}

	@Dado("Que a lista de livros esta disponivel")
	public void queAListaDeLivrosEstaDisponivel() {

		response = endPoints.getBooks();
		response.then().statusCode(200);
		Books books = response.getBody().as(Books.class);
		book = books.books.get(0);
	}

	@Quando("Eu adiciono um livro na minha lista de leitura")
	public void euAdicionoUmLivroNaMinhaListaDeLeitura() {
		AddBooksRequest addBooksRequest = new AddBooksRequest(USER_ID, new ISBN(book.isbn));
		response = endPoints.addBook(addBooksRequest);
	}

	@Entao("O livro sera adicionado")
	public void oLivroSeraAdicionado() {
		Assert.assertEquals(201, response.getStatusCode());

		UserAccount userAccount = response.getBody().as(UserAccount.class);
		Assert.assertEquals(USER_ID, userAccount.userID);
		Assert.assertEquals(book.isbn, userAccount.books.get(0).isbn);

	}

	@Quando("Eu removo um livro na minha lista de leitura")
	public void euRemovoUmLivroNaMinhaListaDeLeitura() {

		response = endPoints.removeBook(new RemoveBookRequest(USER_ID, book.isbn));
		response.then().statusCode(204);
	}

	@Entao("O livro sera removido")
	public void oLivroSeraRemovido() {
		response = endPoints.getUserAccount(USER_ID);
		response.then().statusCode(200);

		UserAccount userAccount = response.getBody().as(UserAccount.class);
		Assert.assertEquals(0, userAccount.books.size());

	}

}

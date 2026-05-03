package com.devsuperior.dsmovie.controllers;

import com.devsuperior.dsmovie.tests.TokenUtil;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class MovieControllerRA {

	private Long existingMovieId, nonExistingMovieId;
	private String movieTitle;
	private String clientUsername, clientPassword, adminUsername, adminPassword;
	private String adminToken, clientToken, invalidToken;

	private Map<String, Object> postMovieInstance;

	@BeforeEach
	void setUp() throws Exception {
		baseURI = "http://localhost:8080";

		existingMovieId = 1L;
		nonExistingMovieId = 1000L;

		movieTitle = "Witcher";

		clientUsername = "alex@gmail.com";
		clientPassword = "123456";
		adminUsername = "maria@gmail.com";
		adminPassword = "123456";

		clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);
		adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);
		invalidToken = adminToken + "xpto";

		postMovieInstance = new HashMap<>();
		postMovieInstance.put("title", "Gênio Indomável");
		postMovieInstance.put("score", 4.1);
		postMovieInstance.put("count", 3);
		postMovieInstance.put("image", "https://image.tmdb.org/t/p/w600_and_h900_face/dTe3EpJGnfUeR5swLbWv61iRyFc.jpg");
	}

	@Test
	public void findAllShouldReturnOkWhenMovieNoArgumentsGiven() {
		given()
				.get("/movies")
		.then()
				.statusCode(200)
				.body("content.id", hasItems(1, 2))
				.body("content.title", hasItems("The Witcher", "Venom: Tempo de Carnificina"));
	}
	
	@Test
	public void findAllShouldReturnPagedMoviesWhenMovieTitleParamIsNotEmpty() {
		given()
				.get("/movies?title={movieTitle}", movieTitle)
		.then()
				.statusCode(200)
				.body("content.id[0]", is(1))
				.body("content.title[0]", is("The Witcher"))
				.body("content.score[0]", is(4.5F))
				.body("content.count[0]", is(2))
				.body("content.image[0]", is("https://www.themoviedb.org/t/p/w533_and_h300_bestv2/jBJWaqoSCiARWtfV0GlqHrcdidd.jpg"));
	}
	
	@Test
	public void findByIdShouldReturnMovieWhenIdExists() {
		given()
				.get("/movies/{id}", existingMovieId)
		.then()
				.statusCode(200)
				.body("title", is("The Witcher"))
                .body("score", is(4.5F));
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() {
		given()
				.get("/movies/{id}", nonExistingMovieId)
		.then()
				.statusCode(404);
	}
	
	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndBlankTitle() {
		postMovieInstance.put("title", "");
		JSONObject newMovieJSON = new JSONObject(postMovieInstance);
		given()
				.header("Authorization", "Bearer " + adminToken)
				.body(newMovieJSON)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
		.when()
				.post("/movies")
		.then()
				.statusCode(422)
				.body("error", is("Dados inválidos"))
				.body("errors.fieldName", hasItem("title"))
				.body("errors.message", hasItems("Campo requerido", "Tamanho deve ser entre 5 e 80 caracteres"));
	}
	
	@Test
	public void insertShouldReturnForbiddenWhenClientLogged() {
		JSONObject newMovieJSON = new JSONObject(postMovieInstance);
		given()
				.header("Authorization", "Bearer " + clientToken)
				.body(newMovieJSON)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
		.when()
				.post("/movies")
		.then()
				.statusCode(403);
	}
	
	@Test
	public void insertShouldReturnUnauthorizedWhenInvalidToken() {
		JSONObject newMovieJSON = new JSONObject(postMovieInstance);
		given()
				.header("Authorization", "Bearer " + invalidToken)
				.body(newMovieJSON)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
		.when()
				.post("/movies")
		.then()
				.statusCode(401);
	}
	
}

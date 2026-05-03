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

public class ScoreControllerRA {

    private Long existingMovieId, nonExistingMovieId;
    private String adminUsername, adminPassword;
    private String adminToken, clientToken;

    private Map<String, Object> putScoreInstance;

    @BeforeEach
    void setUp() throws Exception {
        baseURI = "http://localhost:8080";

        existingMovieId = 1L;
        nonExistingMovieId = 1000L;

        adminUsername = "maria@gmail.com";
        adminPassword = "123456";

        adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);

        putScoreInstance = new HashMap<>();
        putScoreInstance.put("movieId", 1);
        putScoreInstance.put("score", 5);
    }

    @Test
    public void saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist() {
        putScoreInstance.put("movieId", nonExistingMovieId);
        JSONObject saveScoreJSON = new JSONObject(putScoreInstance);
        given()
                .header("Authorization", "Bearer " + adminToken)
                .body(saveScoreJSON)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
        .when()
                .put("/scores")
        .then()
                .statusCode(404)
                .body("error", is("Recurso não encontrado"));
    }

    @Test
    public void saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId() {
        putScoreInstance.put("movieId", "");
        JSONObject saveScoreJSON = new JSONObject(putScoreInstance);
        given()
                .header("Authorization", "Bearer " + adminToken)
                .body(saveScoreJSON)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
        .when()
                .put("/scores")
        .then()
                .statusCode(422)
                .body("error", is("Dados inválidos"))
                .body("errors.fieldName[0]", is("movieId"))
                .body("errors.message[0]", is("Campo requerido"));
    }

    @Test
    public void saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero() {
        putScoreInstance.put("score", -1);
        JSONObject saveScoreJSON = new JSONObject(putScoreInstance);
        given()
                .header("Authorization", "Bearer " + adminToken)
                .body(saveScoreJSON)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
        .when()
                .put("/scores")
        .then()
                .statusCode(422)
                .body("error", is("Dados inválidos"))
                .body("errors.fieldName[0]", is("score"))
                .body("errors.message[0]", is("Valor mínimo 0"));
    }

}

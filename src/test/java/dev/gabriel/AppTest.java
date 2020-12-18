package dev.gabriel;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class AppTest {

    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = "http://192.168.0.22:8001/tasks-backend";
    }

    @Test
    public void deveRetornarTarefas(){
        RestAssured.given()
        .when()
            .get("/todo")
        .then()
            .statusCode(200)
        ;
    }

    @Test
    public void deveAddTarefa(){
        RestAssured.given()
            .body(" {\"task\": \"test\",\"dueDate\": \"2021-12-23\"}")
            .contentType(ContentType.JSON)
        .when()
            .post("/todo")
        .then()
            .statusCode(201)
        ;
    }

    @Test
    public void naoDeveAddTarefaInvalida(){
        RestAssured.given()
            .body(" {\"task\": \"test\",\"dueDate\": \"2011-12-23\"}")
            .contentType(ContentType.JSON)
        .when()
            .post("/todo")
        .then()
            .statusCode(400)
            .body("message", CoreMatchers.is("Due date must not be in past"))
        ;
    }
}

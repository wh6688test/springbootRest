package org.tutorials.wproject1.end2end;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

import static org.assertj.core.api.Assertions.assertThat;


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;

import org.tutorials.wproject1.model.Group;
import org.tutorials.wproject1.model.Member;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//end to end test
public class Wproject1RestAssuredIT {




    @BeforeAll
    public static void setUp() throws Exception {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;

		given().contentType("application/json")
				.when().get("/actuator/health").then().assertThat().statusCode(HttpStatus.OK.value())
				.time(lessThan(6000L));



		//Check to make sure service is up
		//RestAssured.basePath = "/wrest";
		//RequestSpecification request = RestAssured.given();
	}

	/**
	@BeforeEach
    public  void setUpEach() throws Exception {
	}**/

	@AfterAll
    public static void tearDown() throws Exception {
        RestAssured.reset();
	}

	/**
    @AfterEach
    public  void teardownEach() throws Exception {
		RestAssured.reset();
	}**/


	@Test
	void whenPostGroupResponseOKWithinTimeLimit() throws Exception {
    	//request
		Map attr=new HashMap<String, String>();
		attr.put("attr1", "value1");
		Group responseGroup=given().basePath("/wrest").contentType("application/json").body(attr)
		  .when().post("/group").then().assertThat().statusCode(HttpStatus.CREATED.value())
				.time(lessThan(6000L))
				.extract().as(Group.class);
		assertThat(responseGroup.getGid(), greaterThanOrEqualTo(1L)) ;
		assertThat(responseGroup.getAttributes(), hasKey("attr1")) ;
		assertThat(responseGroup.getAttributes(), hasValue("value1")) ;
		assertThat(responseGroup.getAttributes().entrySet(), hasSize(1)) ;
	}

	@Test
	void whenGetAllGroupsThenReturnJsonArrayWithinTimeLimit() throws Exception {

		Map attr1Map=new HashMap<String, String>();
		attr1Map.put("attr2", "value2");


		given().contentType("application/json").body(attr1Map)
				.when().post("/wrest/group").then().assertThat().statusCode(HttpStatus.CREATED.value());

		String responseBody=given().basePath("/wrest").contentType("application/json")
				.when().get("/groups").then().assertThat().statusCode(HttpStatus.OK.value())
				.time(lessThan(6000L))
				.extract().asString();
		assertThat(responseBody.contains("attr2"));
		assertThat(responseBody.contains("value2"));
	}

	@Test
	void whenGetSpecificGroupThenReturnTheGroup200WithinTimeLimit() throws Exception {

		Map attr1Map=new HashMap<String, String>();
		attr1Map.put("attr1", "value1");

		given().basePath("/wrest").contentType("application/json").body(attr1Map)
				.when().post("/group").then().assertThat().statusCode(HttpStatus.CREATED.value());

		Group retrievedGroup=given().basePath("/wrest").contentType("application/json") .queryParam("gid", "1")
				.when().get("/group").then().statusCode(HttpStatus.OK.value())
				.time(lessThan(3000L))
				.extract().as(Group.class);
		assertThat(retrievedGroup.getAttributes().containsKey("attr1"));
		assertThat(retrievedGroup.getAttributes().values().contains("value1"));

	}


	@Test
	void whenUpdateGroupMemberThenReturnUpdatedGroup200WithinLimit() throws Exception {

		Map attr1Map=new HashMap<String, String>();
		attr1Map.put("attr1", "value1");
		Member member1= new Member("1", (short)5);

		given().basePath("/wrest").contentType("application/json").body(attr1Map)
				.when().post("/group").then().assertThat().statusCode(HttpStatus.CREATED.value());

		Map<String, Member> responseMembers=given().basePath("/wrest").contentType("application/json")
				.body(member1)
				.when().put("/group/{gid}/member/rating", 1L).then().assertThat().statusCode(HttpStatus.OK.value())
				.time(lessThan(3000L)).and().body("gid", is(1))
				.extract().path("members");
		assertThat(responseMembers.containsKey("1"));
		assertThat(responseMembers.containsValue(member1.toString()));
	}


	@Test
	void whenGetSpecificGidThenReturnGroup200WithinLimit() throws Exception {

		Map attr1Map=new HashMap<String, String>();
		attr1Map.put("attr1", "value1");
		Member member1= new Member("1", (short)5);

		given().basePath("/wrest").contentType("application/json").body(attr1Map)
				.when().post("/group").then().assertThat().statusCode(HttpStatus.CREATED.value());

		Map<String, Member> responseMembers=given().basePath("/wrest").contentType("application/json")
				.body(member1)
				.when().put("/group/{gid}/member/rating", 1L).then().assertThat().statusCode(HttpStatus.OK.value())
				.time(lessThan(3000L)).and().body("gid", is(1))
				.extract().path("members");

		String responseGroup=given().basePath("/wrest").contentType("application/json")
				.queryParam("gid", "1")
				.when().get("/group").then().assertThat().statusCode(HttpStatus.OK.value())
				.time(lessThan(3000L)).and().body("gid", is(1))
				.extract().asString();

		assertThat(responseGroup.contains("\"gid\":1"));
		assertThat(responseGroup.contains("\"attr1\":\"value1\""));
		assertThat(responseGroup.contains("\"id\":\"1\",\"rating\":\"5\""));
	}


	@Test
	void  whenDeleteGroupReturn204WithinLimit() throws Exception {

		Map attr1Map=new HashMap<String, String>();
		attr1Map.put("attr1", "value1");
		Member member1= new Member("1", (short)5);

		given().basePath("/wrest").contentType("application/json").body(attr1Map)
				.when().post("/group").then().assertThat().statusCode(HttpStatus.CREATED.value());

		Map<String, Member> responseMembers=given().basePath("/wrest").contentType("application/json")
				.body(member1)
				.when().put("/group/{gid}/member/rating", 1L).then().assertThat().statusCode(HttpStatus.OK.value())
				.time(lessThan(3000L)).and().body("gid", is(1))
				.extract().path("members");

		given().basePath("/wrest").contentType("application/json")
				.when().delete("/group/{gid}", 1L).then().assertThat().statusCode(HttpStatus.NO_CONTENT.value())
				.time(lessThan(3000L));
	}

}
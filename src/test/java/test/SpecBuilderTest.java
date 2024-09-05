package test;


import Pojo.AddPlace;
import Pojo.Location;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class SpecBuilderTest {

    public static void main(String[] args) {
        AddPlace p = new AddPlace();
        p.setAccuracy(50);
        p.setAddress("29, side layout, cohen 09");
        p.setLanguage("French-IN");
        p.setPhone_number("0900664234");
        p.setWebsite("http://google.com");
        p.setName("Front line house");
        List<String> myList = new ArrayList<>();
        myList.add("shoe park");
        myList.add("shop");
        p.setTypes(myList);
        Location l = new Location();
        l.setLat(0.235);
        l.setLng(8.15456);
        p.setLocation(l);

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();

        RequestSpecification res = given().spec(req)
                .body(p).log().all();
        ResponseSpecification resExpect = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        String response = res.when().post("/maps/api/place/add/json")
                .then().log().all().spec(resExpect).extract().response().asString();
        System.out.println(response);
    }

}

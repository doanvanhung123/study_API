package test;


import Pojo.AddPlace;
import Pojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class serializeTest {

    public static void main(String[] args) {
       baseURI = "https://rahulshettyacademy.com";

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
        String res= given().log().all().queryParam("key","qaclick123").body(p)
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response().asString();
        System.out.println(res);
    }

}

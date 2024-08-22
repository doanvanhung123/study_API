package APIBasic;

import commons.ReUseableMethod;
import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basic {
    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(Payload.AddPlace()).when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
        System.out.println(response);
        JsonPath js = new JsonPath(response);
        String placeId = js.getString("place_id");// for parsing json
        System.out.println(placeId);

        String newAddress = "Kaka Monocato";
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body("{\n" +
                        "\"place_id\":\"" + placeId + "\",\n" +
                        "\"address\":\""+newAddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}")
                .when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));


        // Get place
        String getPlaceResponse = given().log().all().queryParam("key","qaclick123")
                .queryParam("place_id",placeId)
                .when().get("maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
        JsonPath js1 = ReUseableMethod.rawToJson(getPlaceResponse);
        String actualAddress = js1.getString("address");
        System.out.println(actualAddress);

        Assert.assertEquals(actualAddress,newAddress);
        //Junit , testng
    }
}

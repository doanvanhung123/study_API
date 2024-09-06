package cucumber.stepDefinitions;

import Pojo.Other.AddPlace;
import Pojo.Other.Location;
import io.cucumber.java.en.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class StepDefinition {
    RequestSpecification res;

    ResponseSpecification resExpect;

    Response response;

    @Given("Add Place Payload")
    public void add_place_payload() {
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

                res = given().spec(req)
                        .body(p).log().all();
                resExpect = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
    }
    @When("User calls {string} with Post http request")
    public void user_calls_with_post_http_request(String string) {
        response = res.when().post("/maps/api/place/add/json")
                .then().log().all().spec(resExpect).extract().response();
        System.out.println(response);
    }
    @Then("The API call is success with status code {int}")
    public void the_api_call_is_success_with_status_code(Integer int1) {
       Assert.assertEquals(response.getStatusCode(),200);
    }
    @Then("{string} in response body is {string}")
    public void in_response_body_is_ok(String key,String value) {
       String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        Assert.assertEquals(js.get(key),value);
    }
}

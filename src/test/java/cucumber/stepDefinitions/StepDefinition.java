package cucumber.stepDefinitions;

import io.cucumber.java.en.*;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class StepDefinition extends Utils {
    RequestSpecification res;
    ResponseSpecification resExpect;
    Response response;
    TestDataBuild testDataBuild = new TestDataBuild();

    public static String place_Id;


    @Given("Add Place Payload with {string} {string} {string}")
    public void add_place_payload(String name, String language,String address) throws IOException {
        res = given().spec(requestSpecification())
                .body(testDataBuild.addPlacePayLoad(name,language,address));
    }

    @When("User calls {string} with {string} http request")
    public void user_calls_with_post_http_request(String resource,String method) {
        System.out.println(method);
        APIResources resources = APIResources.valueOf(resource);
        resExpect = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        if(method.equalsIgnoreCase("GET")){
            response = res.when().get(resources.getResource());
        }else if(method.equalsIgnoreCase("POST")) {
            response = res.when().post(resources.getResource());
        }
    }

    @Then("The API call is success with status code {int}")
    public void the_api_call_is_success_with_status_code(Integer int1) {
        response = response.then().spec(resExpect).extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Then("{string} in response body is {string}")
    public void in_response_body_is_ok(String key, String value) {

        Assert.assertEquals(getJsonPath(response,key), value);
    }

    @Then("Verify place_Id created maps to {string} using {string}")
    public void verify_place_id_created_maps_to_using(String value, String resource) throws IOException {
        place_Id = getJsonPath(response,"place_id");
        res = given().spec(requestSpecification()).queryParam("place_id",place_Id);
        user_calls_with_post_http_request(resource,"get");
        String place_id = getJsonPath(response,"name");
        Assert.assertEquals(value,place_id);

    }

    @Given("DeletePlace Payload")
    public void delete_place_payload() throws IOException {
        res=given().spec(requestSpecification())
                .body(testDataBuild.deletePlacePayload(place_Id));
    }
}

package cucumber.stepDefinitions;

import io.cucumber.java.en.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import resources.TestDataBuild;
import utilitys.Utils;

import java.io.FileNotFoundException;

import static io.restassured.RestAssured.given;

public class StepDefinition extends Utils {
    RequestSpecification res;
    ResponseSpecification resExpect;
    Response response;
    TestDataBuild testDataBuild = new TestDataBuild();

    @Given("Add Place Payload")
    public void add_place_payload() throws FileNotFoundException {
        res = given().spec(requestSpecification())
                .body(testDataBuild.addPlacePayLoad()).log().all();
    }

    @When("User calls {string} with Post http request")
    public void user_calls_with_post_http_request(String string) {
        resExpect = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        response = res.when().post("/maps/api/place/add/json")
                .then().log().all().spec(resExpect).extract().response();
        System.out.println(response);
    }

    @Then("The API call is success with status code {int}")
    public void the_api_call_is_success_with_status_code(Integer int1) {
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Then("{string} in response body is {string}")
    public void in_response_body_is_ok(String key, String value) {
        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        Assert.assertEquals(js.get(key), value);
    }
}

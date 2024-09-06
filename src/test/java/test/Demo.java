package test;
import Pojo.Other.Api;
import Pojo.Other.GetCourse;
import Pojo.Other.WebAutomation;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
public class Demo {
    public static void main(String[] args) {
        List <String> expectedList = List.of("Selenium Webdriver Java","Cypress","Protractor");

        baseURI="https://rahulshettyacademy.com/";
           String Auth = given()
                    .formParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                    .formParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                    .formParams("grant_type","client_credentials")
                    .formParams("scope","trust")
                    .when().log().all()
                    .post("oauthapi/oauth2/resourceOwner/token").asString();
        System.out.println(Auth);
        JsonPath jsonPath = new JsonPath(Auth);
        String accessToken = jsonPath.getString("access_token");

        GetCourse getCourse = given()
                .queryParams("access_token",accessToken)
                .when().log().all()
                .get("oauthapi/getCourseDetails").as(GetCourse.class);
        System.out.println(getCourse.getLinkedIn());
        System.out.println(getCourse.getInstructor());
        List<Api> apiCourse = getCourse.getCourses().getApi();
        for (Api api : apiCourse){
            System.out.println(api.getCourseTitle());
        }

        List<WebAutomation> w = getCourse.getCourses().getWebAutomation();
        List<String> a = new ArrayList<>();

        for(WebAutomation webAutomation :w){
            a.add(webAutomation.getCourseTitle());
        }

        Assert.assertEquals(a,expectedList);
    }
}

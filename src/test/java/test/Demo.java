package test;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
public class Demo {
    public static void main(String[] args) {
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

        String response2 = given()
                .queryParams("access_token",accessToken)
                .when().log().all()
                .get("oauthapi/getCourseDetails").asString();
        System.out.println(response2);
    }
}

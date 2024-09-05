package E2E;

import Pojo.Ecommerce.LoginRequest;
import Pojo.Ecommerce.LoginResponse;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.File;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class EcommerceAPITest {
    public static void main(String[] args) {
        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
                .setContentType(ContentType.JSON).build();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("hung@gmail.com");
        loginRequest.setUserPassword("@Hh123123");

        RequestSpecification res = given().spec(req).body(loginRequest).log().all();

        ResponseSpecification resExpect = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        LoginResponse loginResponse = res.post("api/ecom/auth/login").then().log().all().spec(resExpect).extract().response().as(LoginResponse.class);

        String token = loginResponse.getToken();
        String userId = loginResponse.getUserId();
        System.out.println(token);

        RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
                .addHeader("authorization", token)
                .build();

        RequestSpecification addProductReq = given().spec(addProductBaseReq)
                .param("productName", "Laptop")
                .param("productAddedBy", userId)
                .param("productCategory", "fashion")
                .param("productSubCategory", "shirts")
                .param("productPrice", "11500")
                .param("productDescription", "Addias Originals")
                .param("productFor", "women")
                .multiPart("productImage", new File("C:\\Users\\hung.doanv\\OneDrive - CÔNG TY CỔ PHẦN CÔNG NGHỆ TITAN\\Pictures\\done edit.png"));

        String addProductRes = addProductReq.when().post("api/ecom/product/add-product").then().log().all().extract().response().asString();
        JsonPath js = new JsonPath(addProductRes);
        String productId = js.getString("productId");



    }
}

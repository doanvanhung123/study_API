package E2E;

import Pojo.Ecommerce.LoginRequest;
import Pojo.Ecommerce.LoginResponse;
import Pojo.Ecommerce.OrderDetails;
import Pojo.Ecommerce.Orders;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;

import javax.print.attribute.standard.MediaSize;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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


        //addProduct
        RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
                .addHeader("authorization", token)
                .build();
        String imgUpload = System.getProperty("user.dir") + "/file-upload/upload_demo.png";
        RequestSpecification addProductReq = given().spec(addProductBaseReq)
                .param("productName", "Laptop2")
                .param("productAddedBy", userId)
                .param("productCategory", "fashion")
                .param("productSubCategory", "shirts")
                .param("productPrice", "11500")
                .param("productDescription", "Addias Originals")
                .param("productFor", "women")
                .multiPart("productImage", new File(imgUpload)).log().all();

        String addProductRes = addProductReq.when().post("api/ecom/product/add-product").then().log().all().extract().response().asString();
        JsonPath js = new JsonPath(addProductRes);
        String productId = js.getString("productId");

        //create Order
        RequestSpecification createOtherBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
                .setContentType(ContentType.JSON)
                .addHeader("authorization", token)
                .build();


        List<OrderDetails> otherDetailList = new ArrayList<>();
        OrderDetails orderDetails1 = new OrderDetails();
        orderDetails1.setCountry("Afghanistan");
        orderDetails1.setProductOrderedId(productId);

        OrderDetails orderDetails2 = new OrderDetails();
        orderDetails2.setCountry("India");
        orderDetails2.setProductOrderedId(productId);
        otherDetailList.add(orderDetails1);

        Orders orders = new Orders();
        orders.setOrders(otherDetailList);

        RequestSpecification createOrderReq = given().log().all().spec(createOtherBaseReq).body(orders).log().all();
        String createOrderRes = createOrderReq.when().post("api/ecom/order/create-order").then().log().all().extract().response().asString();
        JsonPath jsonPath = new JsonPath(createOrderRes);
        String createOrderId = jsonPath.getString("orders");

        System.out.println(createOrderRes);

        //Delete product

        RequestSpecification deleteProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
                .setContentType(ContentType.JSON)
                .addHeader("authorization", token)
                .build();

        RequestSpecification deleteProductReq = given().log().all().spec(deleteProductBaseReq).pathParams("productId", productId);
        String deleteProductRes = deleteProductReq.when().delete("api/ecom/product/delete-product/{productId}")
                .then().log().all().extract().response().asString();
        JsonPath deleteProductJs = new JsonPath(deleteProductRes);
        String message = deleteProductJs.getString("message");
        Assert.assertEquals(message,"Product Deleted Successfully");
        System.out.println(deleteProductRes);
    }
}

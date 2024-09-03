package test;


import commons.ReUseableMethod;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;

public class BugTest  {
    public static void main(String[] args) {
        baseURI="https://doanvanhungcntt.atlassian.net/";
        String createIssueResponse = given().header("Content-Type","application/json")
                .header("Authorization","Basic ZG9hbnZhbmh1bmdjbnR0QGdtYWlsLmNvbTpBVEFUVDN4RmZHRjBLRXJwREY5bnJiVlEyTFV4anJIRnAtdUYtQVVieDRpNzFBZDVEQThDOFc1QjZKVXk4RmsteW55TkI4RmxsYzVqRGZpY2Z0VUhuSGl6N2NYMXRuS2pjbzJTT0loajlHdGl5bVh0TVFVNkItN1dwekpXcFA4eTlrR2V4V2FrcHVVLWRpbjNSRlItYzlqLVRaSk13bUFXUnB5UmNDbGxCNmo2NEpVbVVKVHcxNmc9QkM4RUUzQjU=")
                .body("{\n" +
                        "    \"fields\": {\n" +
                        "       \"project\":\n" +
                        "       {\n" +
                        "          \"key\": \"SCRUM\"\n" +
                        "       },\n" +
                        "       \"summary\": \"Button can't click and add attachments\",\n" +
                        "       \"issuetype\": {\n" +
                        "          \"name\": \"Bug\"\n" +
                        "       }\n" +
                        "   }\n" +
                        "}")
                .log().all()
                .post("rest/api/3/issue")
                .then().log().all().assertThat().statusCode(201)
                .extract().response().asString();

        JsonPath js = new JsonPath(createIssueResponse);
        String issueId= js.getString("id");
        System.out.println(issueId);
        given()
                .header("Content-Type","multipart/form-data").pathParams("key",issueId)
                .header("Authorization","Basic ZG9hbnZhbmh1bmdjbnR0QGdtYWlsLmNvbTpBVEFUVDN4RmZHRjBLRXJwREY5bnJiVlEyTFV4anJIRnAtdUYtQVVieDRpNzFBZDVEQThDOFc1QjZKVXk4RmsteW55TkI4RmxsYzVqRGZpY2Z0VUhuSGl6N2NYMXRuS2pjbzJTT0loajlHdGl5bVh0TVFVNkItN1dwekpXcFA4eTlrR2V4V2FrcHVVLWRpbjNSRlItYzlqLVRaSk13bUFXUnB5UmNDbGxCNmo2NEpVbVVKVHcxNmc9QkM4RUUzQjU=")
                .header("X-Atlassian-Token","no-check")
                .multiPart("file",new File("C:/Users/pc/Pictures/430002730_674457958068299_7535546869992941457_n.png"))
                .log().all()
                .post("rest/api/3/issue/{key}/attachments")
                .then().log().all().assertThat().statusCode(200);
    }
}

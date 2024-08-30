package files;

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
import static org.hamcrest.Matchers.*;
public class DynamicJson {
    //validate if Add Place API is wordking as expected
    // Add place -> Update Place with new Address - > get place to validate if new address is present in reponse

    //Given - all input details
    // when - Submit the APi - resource ,http method
    //Then - validate the response
    // content of the file to String -> Content of file can convert into Byte -> Byte data to String
    @Test(dataProvider="BooksData")
    public void addBook(String isbn,String aisle) throws IOException {
        RestAssured.baseURI = "http://216.10.245.166";
        String res = given().header("Content-Type","application/json")
//                .body(Payload.Addbook(isbn,aisle))
                .body(new String(Files.readAllBytes(Paths.get("D:\\API_study\\doc-file\\AddBook.json"))))
                .when().post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();
       JsonPath addBookJs = ReUseableMethod.rawToJson(res);
       String id = addBookJs.get("ID");
        System.out.println(id);
    }
    @DataProvider(name="BooksData")
    public Object[][] getData(){
        return new Object[][] {{"adad","13212"},{"adada","1321332"},{"adassd","1324112"}};
    }
}

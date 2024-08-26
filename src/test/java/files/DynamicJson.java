package files;

import commons.ReUseableMethod;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class DynamicJson {
    @Test(dataProvider="BooksData")
    public void addBook(String isbn,String aisle){
        RestAssured.baseURI = "http://216.10.245.166";
        String res = given().header("Content-Type","application/json")
                .body(Payload.Addbook(isbn,aisle))
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

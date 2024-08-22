package APIBasic;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
    public static void main(String[] args) {
        JsonPath js = new JsonPath(Payload.CoursePrice());

        System.out.println(js.getString("courses[0].title"));
       int count = js.getInt("courses.size()");
        System.out.println(js.getInt("dashboard.purchaseAmount"));
        System.out.println(js.get("courses[0].title").toString());

        for(int i=0;i<count;i++){
            String title = js.getString("courses["+i+"].title");
            String price = js.getString("courses["+i+"].price");
            System.out.println(title + ": " + price);
        }
    }
}

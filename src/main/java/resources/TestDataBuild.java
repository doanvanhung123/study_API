package resources;

import Pojo.Other.AddPlace;
import Pojo.Other.Location;

import java.util.ArrayList;
import java.util.List;

public class TestDataBuild {

    public AddPlace addPlacePayLoad(String name, String language,String address) {
        AddPlace p = new AddPlace();

        p.setAccuracy(50);
        p.setAddress(address);
        p.setLanguage(language);
        p.setPhone_number("0900664234");
        p.setWebsite("http://google.com");
        p.setName(name);
        List<String> myList = new ArrayList<>();
        myList.add("shoe park");
        myList.add("shop");
        p.setTypes(myList);
        Location l = new Location();
        l.setLat(0.235);
        l.setLng(8.15456);
        p.setLocation(l);
        return p;
    }
    public String deletePlacePayload(String placeId){
        return "{\n" +
                "    \"place_id\":\""+placeId+"\"\n" +
                "}";
    }

}

package resources;

import Pojo.Other.AddPlace;
import Pojo.Other.Location;

import java.util.ArrayList;
import java.util.List;

public class TestDataBuild {

    public AddPlace addPlacePayLoad() {
        AddPlace p = new AddPlace();

        p.setAccuracy(50);
        p.setAddress("29, side layout, cohen 09");
        p.setLanguage("French-IN");
        p.setPhone_number("0900664234");
        p.setWebsite("http://google.com");
        p.setName("Front line house");
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
}

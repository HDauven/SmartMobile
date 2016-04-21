package grapp.com.grapp.model;

import android.location.Location;

import java.util.Random;

/**
 * Created by Erwin on 21-4-2016.
 */
public class GrappContact {

    public String Name;
    public Location location;
    public float Distance;
    public Random r;

    public GrappContact(String name)
    {
        Name = name;
        r = new Random();
        location = new Location("");
        location.setLatitude(-180.0 + r.nextDouble() * 180.0);
        location.setLongitude(-180.0 + r.nextDouble() * 180.0);
    }

}

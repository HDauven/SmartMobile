package grapp.com.grapp;

import java.util.Date;

/**
 * Created by Hein on 4/15/2016.
 */
public class GrappNotification {

    private String message;
    private Date date;
    private String imageResource;
    private boolean isSeen;

    public GrappNotification(String message, Date date, String imageResource, boolean isSeen) {
        this.message = message;
        this.date = date;
        this.imageResource = imageResource;
        this.isSeen = isSeen;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setIsSeen(boolean isSeen) {
        this.isSeen = isSeen;
    }
}

package grapp.com.grapp.model;

/**
 * Created by Hein on 4/15/2016.
 */
public class GrappGroup {

    private String groupName;
    private String groupDescription;
    private String imageResource;

    public GrappGroup(String groupName, String imageResource) {
        this.groupName = groupName;
        this.groupDescription = "";
        this.imageResource = imageResource;
    }

    public GrappGroup(String groupName, String groupDescription, String imageResource) {
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.imageResource = imageResource;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }
}

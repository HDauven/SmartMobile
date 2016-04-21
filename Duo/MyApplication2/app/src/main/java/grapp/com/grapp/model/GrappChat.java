package grapp.com.grapp.model;

/**
 * Created by Hein on 4/15/2016.
 */
public class GrappChat {

    private String chatName;
    private String imageResource;
    private String lastMessage;

    public GrappChat(String chatName, String imageResource) {
        this.chatName = chatName;
        this.imageResource = imageResource;
        this.lastMessage = "New Chat";
    }

    public GrappChat(String chatName, String imageResource, String lastMessage) {
        this.chatName = chatName;
        this.imageResource = imageResource;
        this.lastMessage = lastMessage;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}

package fr.ensim.interop.introrest.api.mc;

public class MessageRequest {

    private String chat_id;
    private String text;

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MessageRequest(String text) {
        this.text = text;
    }

    public MessageRequest(String chat_id, String text) {
        this.chat_id = chat_id;
        this.text = text;
    }
}

package fr.ensim.interop.introrest.api.mc;

public class Joke {
    private int id;
    private String title;
    private String text;
    private int rating;

    public Joke(int id, String title, String text, int rating) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.rating = rating;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}


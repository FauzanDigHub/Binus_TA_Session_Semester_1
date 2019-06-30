package sample;

import javafx.beans.property.SimpleStringProperty;

public class Vote { // class for vote that listed the date and title

    private SimpleStringProperty date , title;

    public Vote(String date , String title){
        this.date = new SimpleStringProperty(date);
        this.title = new SimpleStringProperty(title);
    }

    public String getDate() {
        return date.get();
    }

    public String getTitle() {
        return title.get();
    }

}

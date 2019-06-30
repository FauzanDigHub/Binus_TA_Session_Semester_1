package sample;

import javafx.beans.property.SimpleStringProperty;

public class Answers { // class to store the string inside vote
    private SimpleStringProperty choice , result;

    public Answers(String choice , String result){ // function to get the value and string
        this.choice = new SimpleStringProperty(choice);
        this.result = new SimpleStringProperty(result);
    }

    public String getChoice() {
        return choice.get();
    }

    public String getResult() {
        return result.get();
    }

}

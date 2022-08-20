package fr.guillaumeli.educationalgame;

public class Data {

    private String word, rightLetter, falseLetter;
    private int id;

    public Data(int id, String word, String rightLetter, String falseLetter) {
        this.word = word;
        this.rightLetter = rightLetter;
        this.falseLetter = falseLetter;
        this.id = id;
    }

    // toSting
    @Override
    public String toString() {
        return "Data{" +
                "word='" + word + '\'' +
                ", rightLetter='" + rightLetter + '\'' +
                ", falseLetter='" + falseLetter + '\'' +
                ", id=" + id +
                '}';
    }

    // Getter and setter
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getRightLetter() {
        return rightLetter;
    }

    public void setRightLetter(String rightLetter) {
        this.rightLetter = rightLetter;
    }

    public String getFalseLetter() {
        return falseLetter;
    }

    public void setFalseLetter(String falseLetter) {
        this.falseLetter = falseLetter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

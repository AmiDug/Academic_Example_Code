package examples;

public class Question {

    private String text;
    private String[] options;
    private String[] answers;
    private int pos;
    
    public Question(String text, String options, String answers){
        this.text = text;
        this.options = options.split("/");
        this.answers = answers.split("/");
    }
}

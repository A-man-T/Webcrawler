package assignment;

public class Token {
    private String word;

    public Token(){}
    public Token(String s){
        this.word = s;
    }

    @Override
    public String toString() {
        return word;
    }
}

class AndToken extends Token {
    @Override
    public String toString() {
        return "AndToken";
    }
}

class OrToken extends Token {
    @Override
    public String toString() {
        return "OrToken";
    }
}
class LeftParenToken extends Token {
    @Override
    public String toString() {
        return "(";
    }
}
class RightParenToken extends Token {
    @Override
    public String toString() {
        return ")";
    }
}
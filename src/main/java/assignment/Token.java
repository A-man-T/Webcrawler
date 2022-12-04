package assignment;

public class Token {
    public String word;

    public Token(String s){
        this.word = s;
    }

    public Token(){}



    @Override
    public String toString() {
        return word;
    }
}

class wordToken extends Token{
    public wordToken(String substring) {
        this.word = substring;
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
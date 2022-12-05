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
class phraseToken extends Token{
    public phraseToken(String substring) {
        this.word = substring;
    }
}

class notToken extends Token{
    @Override
    public String toString() {
        return "notToken";
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
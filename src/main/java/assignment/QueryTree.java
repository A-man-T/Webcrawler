package assignment;

public class QueryTree {
    public static class Node {
        Token token;
        Node left;
        Node right;

        public Node(Token token, Node left, Node right) {
            this.token = token;
            this.left = left;
            this.right = right;
        }
    }

    public Node root;
    public QueryTree() {
        root = null;
    }
}

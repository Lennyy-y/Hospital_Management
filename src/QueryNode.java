public class QueryNode {
    public enum NodeType {
        TABLE, JOIN, FILTER, SELECT
    }

    private NodeType type;
    private String value;
    private QueryNode left;
    private QueryNode right;

    public QueryNode(NodeType type, String value) {
        this.type = type;
        this.value = value;
    }

    public NodeType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public void setLeft(QueryNode left) {
        this.left = left;
    }

    public QueryNode getLeft() {
        return left;
    }

    public void setRight(QueryNode right) {
        this.right = right;
    }

    public QueryNode getRight() {
        return right;
    }
}

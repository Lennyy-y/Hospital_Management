import java.util.Stack;

public class QueryTreeBuilder {
    private QueryNode root;
    private QueryNode selectNode;
    private Stack<QueryNode> nodeStack;

    public QueryTreeBuilder() {
        nodeStack = new Stack<>();
    }

    public void setBaseTable(String table) {
        root = new QueryNode(QueryNode.NodeType.TABLE, table);
        nodeStack.push(root);
    }
    public String getBaseTable()
    {
    	return root.getValue();
    }

    public void addSelectOption(String columns) {
        selectNode = new QueryNode(QueryNode.NodeType.SELECT, columns);
    }

    public void addJoinCondition(String joinType, String table, String condition) {
        QueryNode joinNode = new QueryNode(QueryNode.NodeType.JOIN, joinType + " " + table + " ON " + condition);
        joinNode.setLeft(root);
        root = joinNode;
        nodeStack.push(joinNode);
    }

    public void addFilterCondition(String condition) {
        QueryNode filterNode = new QueryNode(QueryNode.NodeType.FILTER, "WHERE " + condition);
        filterNode.setLeft(root);
        root = filterNode;
        nodeStack.push(filterNode);
    }

    public void undoLastOperation() {
        if (!nodeStack.isEmpty()) {
            nodeStack.pop();
            if (!nodeStack.isEmpty()) {
                root = nodeStack.peek();
            } else {
                root = null;
            }
        } else {
            System.out.println("No operations to undo.");
        }
    }

    public String buildQuery() {
        if (root == null) {
            return "";
        }

        StringBuilder query = new StringBuilder("SELECT ");
        if (selectNode != null) {
            query.append(selectNode.getValue());
        } else {
            query.append("*");
        }
        query.append(" FROM ");
        buildQueryRecursive(root, query);
        return query.toString();
    }

    private void buildQueryRecursive(QueryNode node, StringBuilder query) {
        if (node == null) {
            return;
        }

        buildQueryRecursive(node.getLeft(), query);
        if (node.getType() != QueryNode.NodeType.TABLE) {
            query.append(" ");
        }
        query.append(node.getValue());
    }
}

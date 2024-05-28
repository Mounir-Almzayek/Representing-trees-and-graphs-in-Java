public class Node {
    int height;
    int key;
    Node left;
    Node right;
    char colour;
    Node parent;
    public Node(int key, Node left, Node right) {
        this.key = key;
        this.left = left;
        this.right = right;
        this.colour = 'R'; // colour . either 'R' or 'B'
        this.parent = null; // required at time of rechecking.
        this.height = 1;
    }

    public Node(int key) {
        this.key = key;
        this.left = null; // left subtree
        this.right = null; // right subtree
        this.colour = 'R'; // colour . either 'R' or 'B'
        this.parent = null; // required at time of rechecking.
        this.height = 1;
    }

    public Node() {

    }

    public Node maxValueNode (Node node){
        if(node.right == null)
            return node;
        return maxValueNode(node);
    }

    public Node minValueNode (Node node){
        if(node.left == null)
            return node;
        return minValueNode(node);
    }

    public Node successor(Node node){
        return node.right.minValueNode(node.right);
    }

    public Node predecessor(Node node){
        return node.left.maxValueNode(node.left);
    }
}

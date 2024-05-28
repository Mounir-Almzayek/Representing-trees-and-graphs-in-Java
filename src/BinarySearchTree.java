public class BinarySearchTree {
    Node root;
    static int PreIndex = 0;

    public BinarySearchTree(Node root) {
        this.root = root;
    }

    public BinarySearchTree() {

    }


    void preOrder(Node root) {
        if (root != null) {
            System.out.print(root.key + " ");
            preOrder(root.left);
            preOrder(root.right);
        }
    }

    public void inOrder(Node root){
        if (root != null) {
            inOrder(root.left);
            System.out.print(root.key + " ");
            inOrder(root.right);
        }
    }

    public void postOrder(Node root){
        if (root != null) {
            postOrder(root.left);
            postOrder(root.right);
            System.out.print(root.key + " ");
        }
    }

    public boolean search (Node root, int key){
        if (root == null)
            return false;
        if (root.key == key)
            return true;
        if (root.key > key){
            return search(root.left,key);
        }
        if (root.key < key){
            return search(root.right,key);
        }
        return false;
    }

    public int maxValue (Node root){
        if(root.right == null)
            return root.key;
        return maxValue(root.right);
    }

    public int minValue (Node root){
        if(root.left == null)
            return root.key;
        return minValue(root.left);
    }

    public Node insertRec(Node root, int key) {
        if (root == null) {
            root = new Node(key,null,null);
            return root;
        }
        if (key < root.key)
            root.left = insertRec(root.left, key);
        else if (key > root.key)
            root.right = insertRec(root.right, key);
        return root;
    }


    public Node deleteRec(Node root, int key) {
        if (root == null)
            return root;
        if (key < root.key)
            root.left = deleteRec(root.left, key);
        else if (key > root.key)
            root.right = deleteRec(root.right, key);
        else {
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;
            root.key = minValue(root.right);
            root.right = deleteRec(root.right, root.key);
        }
        return root;
    }

    static Boolean isBST(Node root, Node l, Node r)
    {
        if (root == null)
            return true;
        if (l != null && root.key <= l.key)
            return false;
        if (r != null && root.key >= r.key)
            return false;
        return isBST(root.left, l, root) && isBST(root.right, root, r);
    }

    Node BuildTree (int [ ] in , int [ ] pre , int inStart , int inEnd){
         if (inStart > inEnd)
             return null;
         Node tNode = new Node ( pre[PreIndex++] );
         if (inStart == inEnd)
             return tNode;
         int inIndex = searchByIndex( in , inStart, inEnd , tNode.key);
         tNode.left = BuildTree(in , pre , inStart , inIndex -1);
         tNode.right = BuildTree(in , pre , inIndex + 1, inEnd);
         return tNode;
    }

    int searchByIndex (int [ ] in, int Start , int End, int item){
        for(int i = Start ; i< End ; i++){
            if (in[i] == item)
                return i;
        }
        return -1;
    }
}

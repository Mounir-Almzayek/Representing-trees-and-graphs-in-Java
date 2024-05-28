import static java.lang.Math.max;

// Java program for insertion in AVL Tree
class AVLTree {

    Node root;

    public AVLTree(Node root) {
        this.root = root;
    }

    public AVLTree() {
    }
    void preOrder(Node node) {
        if (node != null) {
            System.out.print(node.key + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    public void inOrder(Node node){
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.key + " ");
            inOrder(node.right);
        }
    }

    public void postOrder(Node node){
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.print(node.key + " ");
        }
    }

    // A utility function to get the height of the tree
    int height(Node N) {
        if (N == null)
            return 0;

        return N.height;
    }

    public boolean isAVL (Node root){
        if (height(root.right) - height(root.left) == 1 || height(root.right) - height(root.left) == 0 || height(root.right) - height(root.left) == -1)
            return true;
        else
            return false;
    }

    /**
     T1, T2 and T3 are subtrees of the tree, rooted with y (on the left side) or x (on the right side)

          y                                x
        /  \       Right Rotation        /  \
       x   T3     - - - - - - - >      T1   y
      / \         < - - - - - - -          / \
     T1  T2       Left Rotation          T2  T3

     Keys in both of the above trees follow the following order
     keys(T1) < key(x) < keys(T2) < key(y) < keys(T3)
     So BST property is not violated anywhere.

     **/

    // A utility function to right rotate subtree rooted with y
    // See the diagram given above.
    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    // A utility function to left rotate subtree rooted with x
    // See the diagram given above.
    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }

    // Get Balance factor of node N
    int getBalance(Node N) {
        if (N == null)
            return 0;

        return height(N.left) - height(N.right);
    }

    Node insert(Node root, int key) {

        /* 1. Perform the normal BST insertion */
        if (root == null)
            return (new Node(key));

        if (key < root.key)
            root.left = insert(root.left, key);
        else if (key > root.key)
            root.right = insert(root.right, key);
        else // Duplicate keys not allowed
            return root;

        /* 2. Update height of this ancestor node */
        root.height = 1 + max(height(root.left), height(root.right));

		/* 3. Get the balance factor of this ancestor
			node to check whether this node became
			unbalanced */
        int balance = getBalance(root);

        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
        /**

         T1, T2, T3 and T4 are subtrees.
                z                                       y
               / \                                    /   \
              y   T4      Right Rotate (z)          x      z
             / \          - - - - - - - - ->      /  \    /  \
            x   T3                               T1  T2  T3  T4
           / \
         T1   T2

         **/
        if (balance > 1 && key < root.left.key)
            return rightRotate(root);

        // Right Right Case
        /**

             z                                   y
           /  \                                /   \
          T1   y       Left Rotate(z)        z      x
             /  \     - - - - - - - ->      / \    / \
            T2   x                         T1  T2 T3  T4
                / \
              T3  T4

         */
        if (balance < -1 && key > root.right.key)
            return leftRotate(root);

        // Left Right Case
        /**

                 z                               z                             x
                / \                            /   \                          /  \
               y   T4  Left Rotate (y)        x    T4   Right Rotate(z)     y      z
              / \      - - - - - - - - ->    /  \       - - - - - - - ->   / \    / \
            T1   x                          y    T3                      T1  T2 T3  T4
                / \                        / \
              T2   T3                    T1   T2

         */
        if (balance > 1 && key > root.left.key) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Left Case
        /**

              z                              z                              x
             / \                            / \                            /  \
           T1   y     Right Rotate (y)    T1   x      Left Rotate(z)     z      y
               / \    - - - - - - - - ->     /  \   - - - - - - - ->    / \    / \
              x   T4                        T2   y                    T1  T2  T3  T4
             / \                                /  \
           T2   T3                             T3   T4

         */
        if (balance < -1 && key < root.right.key) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        /* return the (unchanged) node pointer */
        return root;
    }

    /*
    Given a non-empty binary search tree, return the node with minimum key value found in that tree.
    Note that the entire tree does not need to be searched.
    */
    Node minValueNode(Node root) {
        Node current = root;

        /* loop down to find the leftmost leaf */
        while (current.left != null)
            current = current.left;

        return current;
    }

    Node deleteNode(Node root, int key) {
        // STEP 1: PERFORM STANDARD BST DELETE
        if (root == null)
            return root;

        // If the key to be deleted is smaller than
        // the root's key, then it lies in left subtree
        if (key < root.key)
            root.left = deleteNode(root.left, key);

        // If the key to be deleted is greater than the
        // root's key, then it lies in right subtree
        else if (key > root.key)
            root.right = deleteNode(root.right, key);

        // if key is same as root's key, then this is the node
        // to be deleted
        else {
            // node with only one child or no child
            if ((root.left == null) || (root.right == null)) {
                Node temp = null;
                if (temp == root.left)
                    temp = root.right;
                else
                    temp = root.left;

                // No child case
                if (temp == null) {
                    temp = root;
                    root = null;
                }
                // One child case
                else
                    root = temp;
                // Copy the contents of the non-empty child
            }
            else {
                // node with two children: Get the inorder
                // successor (smallest in the right subtree)
                Node temp = minValueNode(root.right);

                // Copy the inorder successor's data to this node
                root.key = temp.key;

                // Delete the inorder successor
                root.right = deleteNode(root.right, temp.key);
            }
        }

        // If the tree had only one node then return
        if (root == null)
            return root;

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.height = max(height(root.left), height(root.right)) + 1;

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
        // this node became unbalanced)
        int balance = getBalance(root);

        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
        /**

         T1, T2, T3 and T4 are subtrees.
                z                                       y
               / \                                    /   \
              y   T4      Right Rotate (z)          x      z
             / \          - - - - - - - - ->      /  \    /  \
            x   T3                               T1  T2  T3  T4
           / \
         T1   T2

         **/
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        // Left Right Case
        /**

                z                               z                             x
               / \                            /   \                          /  \
              y   T4  Left Rotate (y)        x    T4   Right Rotate(z)     y      z
             / \      - - - - - - - - ->    /  \       - - - - - - - ->   / \    / \
           T1   x                          y    T3                      T1  T2 T3  T4
               / \                        / \
             T2   T3                    T1   T2

         */
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Right Case
        /**

             z                                   y
           /  \                                /   \
          T1   y       Left Rotate(z)        z      x
             /  \     - - - - - - - ->      / \    / \
            T2   x                         T1  T2 T3  T4
                / \
              T3  T4

         */
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        // Right Left Case
        /**

               z                              z                              x
              / \                            / \                            /  \
            T1   y     Right Rotate (y)    T1   x      Left Rotate(z)     z      y
                / \    - - - - - - - - ->     /  \   - - - - - - - ->    / \    / \
               x   T4                        T2   y                    T1  T2  T3  T4
              / \                                /  \
            T2   T3                             T3   T4

         */
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
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

    public Node makeBalance(Node root){
        int balance = getBalance(root);

        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
        /**

         T1, T2, T3 and T4 are subtrees.
         z                                       y
         / \                                    /   \
         y   T4      Right Rotate (z)          x      z
         / \          - - - - - - - - ->      /  \    /  \
         x   T3                               T1  T2  T3  T4
         / \
         T1   T2

         **/
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        // Left Right Case
        /**

         z                               z                             x
         / \                            /   \                          /  \
         y   T4  Left Rotate (y)        x    T4   Right Rotate(z)     y      z
         / \      - - - - - - - - ->    /  \       - - - - - - - ->   / \    / \
         T1   x                          y    T3                      T1  T2 T3  T4
         / \                        / \
         T2   T3                    T1   T2

         */
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Right Case
        /**

         z                                   y
         /  \                                /   \
         T1   y       Left Rotate(z)        z      x
         /  \     - - - - - - - ->      / \    / \
         T2   x                         T1  T2 T3  T4
         / \
         T3  T4

         */
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        // Right Left Case
        /**

         z                              z                              x
         / \                            / \                            /  \
         T1   y     Right Rotate (y)    T1   x      Left Rotate(z)     z      y
         / \    - - - - - - - - ->     /  \   - - - - - - - ->    / \    / \
         x   T4                        T2   y                    T1  T2  T3  T4
         / \                                /  \
         T2   T3                             T3   T4

         */
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }
}
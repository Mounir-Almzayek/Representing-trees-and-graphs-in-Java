public class Main {
    public static void main(String[] args) {
        BinarySearchTree binarySearchTree = new BinarySearchTree(new Node(
                50,
                new Node(
                        25,
                        new Node(
                                10,
                                null,
                                null
                        ),
                        new Node(
                                40,
                                null,
                                null
                        )
                ),
                new Node(
                        75,
                        new Node(
                                60,
                                null,
                                null
                        ),
                        new Node(
                                90,
                                new Node(
                                        80,
                                        null,
                                        null
                                ),
                                new Node(
                                        95,
                                        null,
                                        null
                                )
                        )
                )
            )
        );

        //      50
        //    /     \
        //   25      75
        //  /  \    /  \
        // 10  40  60  90
        //            /  \
        //           80  95




//        int in[] = { 10 , 25 , 40 , 50 ,60 , 75 ,80 ,90 ,95};
//        int pre[] = { 50, 25 , 10 ,40 ,75 , 60 ,90 ,80 ,95 };
//        BinarySearchTree binarySearchTree1 = new BinarySearchTree( binarySearchTree.BuildTree(in,pre,0,8));
//
//        binarySearchTree1.inOrder(binarySearchTree1.root);
//        System.out.println();
//        binarySearchTree1.preOrder(binarySearchTree1.root);
//
//        System.out.println();
//        binarySearchTree1.deleteRec(binarySearchTree1.root,90);
//        binarySearchTree1.preOrder(binarySearchTree1.root);
//
//        System.out.println();

        AVLTree avlTree = new AVLTree(new Node(
                                            2 ,
                                            new Node(
                                                    3,
                                                    new Node(4),
                                                    new Node()),
                                            new Node())
        );

        avlTree.root = avlTree.makeBalance(avlTree.root);
        System.out.println(avlTree.isAVL(avlTree.root));
    }
}



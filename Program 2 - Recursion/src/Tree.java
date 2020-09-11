// ******************ERRORS********************************
// Throws UnderflowException as appropriate
//Grayson Felt, CS2420, ASSN2

//planning: I need to create 12 individual methods + helpers that solve recursive problems
//regarding binary search trees.

//System Analysis: Input will be based upon predetermined method calls. Output will be printed using to(String)
// or inside method.

import java.lang.reflect.Array;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

class UnderflowException extends RuntimeException {
    /**
     * Construct this exception object.
     * @param message the error message.
     */
    public UnderflowException(String message) {
        super(message);
    }
}

public class Tree<E extends Comparable<? super E>> {
    final String ENDLINE = "\n";
    private BinaryNode<E> root;  // Root of tree
    private BinaryNode<E> curr;  // Last node accessed in tree
    private String treeName;     // Name of tree
    private int index = 0;

    /**
     * Create an empty tree
     *
     * @param label Name of tree
     */
    public Tree(String label) {
        treeName = label;
        root = null;
    }

    /**
     * Create non ordered tree from list in preorder
     *
     * @param arr    List of elements
     * @param label  Name of tree
     * @param height Maximum height of tree
     */
    public Tree(ArrayList<E> arr, String label, int height) {
        this.treeName = label;
        root = buildTree(arr, height, null);
    }

    /**
     * Create BST
     *
     * @param arr   List of elements to be added
     * @param label Name of tree
     */
    public Tree(ArrayList<E> arr, String label) {
        root = null;
        treeName = label;
        for (int i = 0; i < arr.size(); i++) {
            bstInsert(arr.get(i));
        }
    }


    /**
     * Create BST from Array
     *
     * @param arr   List of elements to be added
     * @param label Name of  tree
     */
    public Tree(E[] arr, String label) {
        root = null;
        treeName = label;
        for (int i = 0; i < arr.length; i++) {
            bstInsert(arr[i]);
        }
    }

    /**
     * Change name of tree
     *
     * @param name new name of tree
     */
    public void changeName(String name) {
        this.treeName = name;
    }

    /**
     * Return a string displaying the tree contents as a tree with one node per line
     */
    //Big O Notation: O(n)
    public String toString(BinaryNode node, BinaryNode parent) {
        //Case for empty tree
        if (root == null)
            return (treeName + " Empty tree\n");
        //Base case for end of path
        if (node == null) {
            return "";
        }
        //To add tree name, find the root and print the name
        if (node == root) {
            return (treeName + ENDLINE + toString(node.right, node) + node.element + " [no parent]") + ENDLINE + toString(node.left, node);
        }
        //For normal nodes,
        else {
            String message = (node.element + " [" + parent.element + "]");
            return toString(node.right, node) + message + ENDLINE + toString(node.left, node);
        }

    }

    public String toString() {
        return toString(root, root);
    }

    /**
     * Return a string displaying the tree contents as a single line
     */
    public String toString2() {
        if (root == null)
            return treeName + " Empty tree";
        else
            return treeName + " " + toString2(root);
    }

    /**
     * reverse left and right children recursively
     */
    //Big O Notation: O(n)
    public void flip(BinaryNode node) {
        //base case
        if (node == null) {
            return;
        }
        //flip right and left nodes
        BinaryNode left = node.left;
        node.left = node.right;
        node.right = left;
        //recursively flip the next node's left/right
        if (node.left != null) flip(node.left);
        if (node.right != null) flip(node.right);
    }

    public void flip() {
        flip(root);
    }

    /**
     * Find successor of "curr" node in tree
     *
     * @return String representation of the successor
     */
    //Big O Notation: O(n)
    public String successor(BinaryNode node, BinaryNode successor, int key) {
        curr = node;
        if (curr == null) return null;
        //if curr is the element we are looking for
        if (Integer.parseInt(curr.element.toString()) == key) {
            if (curr.right != null) {
                curr = curr.right;
                while (curr.left != null) {
                    curr = curr.left;
                }
                return curr.element.toString();
            }
        //find curr
        } else if (key < Integer.parseInt(curr.element.toString())) {
            successor = node;
            return successor(node.left, successor, key);
        } else {
            return successor(node.right, successor, key);
        }
        //return curr's successor
        return successor.element.toString();
    }

    public String successor(int val) {
        return successor(root, root, val);
    }

    /**
     * Counts number of nodes in specifed level
     *
     * @param level Level in tree, root is zero
     * @return count of number of nodes at specified level
     */
    //Big O Notation: O(n)
    public int nodesInLevel(BinaryNode root, int level) {
        int count = 0;
        if (root == null) {
            return 0;
        }
        //for final level
        else if (level == 0) {
            return 1;
        }
        //add to running sum the next level, essentially counting each node once
        if (root.left != null) {
            count += nodesInLevel(root.left, level - 1);
        }
        if (root.right != null) {
            count += nodesInLevel(root.right, level - 1);
        }
        return count;
    }

    public int nodesInLevel(int level) {
        return nodesInLevel(root, level);
    }

    /**
     * Print all paths from root to leaves
     */
    //Big O Notation: O(n*n)
    public void printAllPaths(BinaryNode root, ArrayList path) {
        //add current node to path
        path.add(root.element);
        //end of line
        if (root.left == null && root.right == null) {
            System.out.println(path);
            return;
        }
        //print paths for both right and left, using new array to keep track of each path
        else {
            if(root.right!=null){
                printAllPaths(root.right, new ArrayList(path));
            }
            if(root.left!=null){
                printAllPaths(root.left, new ArrayList(path));
            }
        }

    }

    public void printAllPaths() {
        ArrayList<Integer> path = new ArrayList<>();
        printAllPaths(root, path);
    }

    /**
     * Print contents of levels in zig zag order starting at maxLevel
     *
     * @param maxLevel
     */
//    public void byLevelZigZag(int maxLevel) {
//        ArrayList<String> currLevel = new ArrayList<>();
//        BinaryNode currNode = root;
//        int level;
//        if (root == null) {
//            return;
//        }
//        if (maxLevel == 0) {
//            level = 0;
//        } else if (maxLevel % 2 == 0) {
//            level = 0;
//        } else {
//            level = 1;
//        }
//        for (int i = nodesInLevel(root, maxLevel); i != 0; i--) {
//            if (level == 1) {
//                //print left to right
//                if (currNode.left != null) {
//                    currLevel.add(currNode.left.element.toString());
//                }
//                if (currNode.right != null) {
//                    currLevel.add(currNode.right.element.toString());
//                }
//            } else {
//                //print right to left
//                if (currNode.right != null) {
//                    currLevel.add(currNode.right.element.toString());
//                }
//                if (currNode.left != null) {
//                    currLevel.add(currNode.left.element.toString());
//                }
//            }
//        }
//        System.out.println(currLevel);
//        byLevelZigZag(maxLevel--);
//    }

    /**
     * Counts all non-null binary search trees embedded in tree
     *
     * @return Count of embedded binary search trees
     */
    //Big O Notation: O(n)
    public Integer countBST(BinaryNode<E> node) {
        //base case
        if (node == null) return 0;
        //if left<node and right>node
        if (validLR(node)) {
            //return 1 + next nodes
            return 1 + countBST(node.left) + countBST(node.right);
        } else return countBST(node.right) + countBST(node.left);
    }
    public Boolean validLR(BinaryNode<E> node){
        //assume true since nodes could be null
        Boolean leftSub = true;
        Boolean rightSub = true;
        if (node.right != null) {
            //will be false if right is smaller, or the next node is invalid
            if(node.right.element.compareTo(node.element) < 0){
                rightSub = false;
            }
            if(!validLR(node.right)){
                rightSub = false;
            }
        }
        if (node.left != null) {
            //will be false if left is smaller, or the next node is invalid
            if (node.left.element.compareTo(node.element) > 0) {
                leftSub = false;
            }
            if(!validLR(node.left)){
                leftSub = false;
            }
        }
        return leftSub && rightSub;
    }

    public Integer countBST() {
        return countBST(root);
    }

    /**
     * Insert into a bst tree; duplicates are allowed
     *
     * @param x the item to insert.
     */
    public void bstInsert(E x) {

        root = bstInsert(x, root, null);
    }

    /**
     * Determines if item is in tree
     *
     * @param item the item to search for.
     * @return true if found.
     */
    public boolean contains(E item) {

        return bstContains(item, root);
    }

    /**
     * Remove all paths from tree that sum to less than given value
     *
     * @param sum: minimum path sum allowed in final tree
     */
//    public void pruneK(Integer sum) {
//        int currSum = 0;
//        ArrayList<BinaryNode<E>> path = new ArrayList<>();
//        path = (genereatePathSum(sum, root, path, currSum));
//        for(int i = 0; i < path.size() - 1; i++){
//            bstInsert(path.get(i).element);
//        }
//    }
//    private ArrayList<BinaryNode<E>> genereatePathSum(Integer sum, BinaryNode<E> node, ArrayList<BinaryNode<E>> path, int currSum){
//        if (node == null) {
//            if(currSum < sum){
//                return null;
//            }
//            else{
//                return path;
//            }
//        }
//        int element = Integer.parseInt(node.element.toString());
//        path.add(node);
//        currSum += element;
//        genereatePathSum(sum, node.right, new ArrayList<>(path), currSum);
//        genereatePathSum(sum, node.left, new ArrayList<>(path), currSum);
//    }
    /**
     * Build tree given inOrder and preOrder traversals.  Each value is unique
     *
     * @param inOrder  List of tree nodes in inorder
     * @param preOrder List of tree nodes in preorder
     */
    //Big O Notation: O(n*n)
    public BinaryNode buildTreeTraversals(int initial, int ending, E[] inOrder, E[] preOrder) {
        //base case for final node
        if (initial > ending) {
            return null;
        }
        BinaryNode<E> node = new BinaryNode<E>(preOrder[index++]);
        //base case for final node
        if (initial == ending) {
            return node;
        }
        //find first node of preOrder for root and create left/right subtrees using same logic
        int in = ending;
        for (int i = initial; i <= ending; i++) {
            if (inOrder[i].toString().equals(node.element.toString())) {
                in = i;
                break;
            }
        }
        node.left = buildTreeTraversals(initial, in - 1, inOrder, preOrder);
        node.right = buildTreeTraversals(in + 1, ending, inOrder, preOrder);
        return node;
    }
    public BinaryNode buildTreeTraversals(E[] inOrder, E[] postOrder){
        int initial = 0;
        int ending = inOrder.length - 1;
        return buildTreeTraversals(initial, ending, inOrder, postOrder);
    }

    /**
     * Find the least common ancestor of two nodes
     * @param a first node
     * @param b second node
     * @return String representation of ancestor
     */
    //Big O Notation: O(n)
    public String lca(BinaryNode<E> root, int a, int b) {
        //base case for final node
        if(root == null){
            return null;
        }
        //if smaller, go left
        if(Integer.parseInt(root.element.toString()) > a &&
                Integer.parseInt(root.element.toString()) > b){
            return lca(root.left, a, b);
        }
        //if bigger, go right
        if(Integer.parseInt(root.element.toString()) < a &&
                Integer.parseInt(root.element.toString()) < b){
            return lca(root.right, a, b);
        }
        //return the found ancestor
        return root.element.toString();
    }
    //I created private inOrder methods to assist me in balancing the BST
    private ArrayList<BinaryNode> inOrder(){
        ArrayList<BinaryNode> range = new ArrayList<>();
        ArrayList<BinaryNode> newRange = inOrder(range, root);
        return newRange;
    }
    private ArrayList<BinaryNode> inOrder(ArrayList<BinaryNode> range, BinaryNode node){
        if(node == null){
            return null;
        }
        //simple inOrder recursion
        inOrder(range, node.left);
        range.add(node);
        inOrder(range, node.right);
        return range;
    }
    /**
     * Balance the tree
     */
    //Big O Notation: O(n*n)
    public BinaryNode balanceTree(){
        //helper method creates an ArrayList of the BST in order
        ArrayList<BinaryNode> range = inOrder();
        //The initial root will be the middle element
        root = range.get((range.size() - 1)/2);
        //remove root
        range.remove((range.size() - 1)/2);
        int len = range.size();
        //initialize right and left subtree
        ArrayList<BinaryNode> head = new ArrayList<>();
        ArrayList<BinaryNode> tail = new ArrayList<>();
        //from inorder, add to tail until middle is reached
        for(int i = 0; i < len/2; i++){
            tail.add(range.get(i));
        }
        //from inorder, add to head until max is reached
        for(int i = len/2; i < len; i++){
            head.add(range.get(i));
        }
        //now the root is finished, onto right and left subtree
        root.right = balanceTree(head);
        root.left= balanceTree(tail);
        return root;
    }
    public BinaryNode balanceTree(ArrayList<BinaryNode> range) {
        BinaryNode currRoot = range.get((range.size() - 1)/2);
        //for last three nodes, special case (base case)
        if(range.size() == 3){
            currRoot = range.get(1);
            BinaryNode a = range.get(0);
            BinaryNode b = range.get(2);
            a.right = null;
            a.left = null;
            b.right = null;
            b.left = null;
            currRoot.right = a;
            currRoot.left = b;
            return currRoot;
        }
        int len = range.size();
        ArrayList<BinaryNode> head = new ArrayList<>();
        ArrayList<BinaryNode> tail = new ArrayList<>();
        for(int i = 0; i < len/2; i++){
            tail.add(range.get(i));
        }
        for(int i = len/2; i < len; i++){
            head.add(range.get(i));
        }
        currRoot.right = balanceTree(head);
        currRoot.left= balanceTree(tail);
        return currRoot;
    }

    /**
     * In a BST, keep only nodes between range
     * @param a lowest value
     * @param b highest value
     */
    //Big O Notation: O(n)
    public BinaryNode keepRange(BinaryNode root, int a, int b) {
        //base case for end of tree
        if(root == null){
            return null;
        }
        //special case for removing root
        if(root == this.root){
            //replacing root with min of right subtree
            int element =  Integer.parseInt(this.root.element.toString());
            if(element > b || element < a){
                this.root = keepRange(root.left, a, b);
                root.left = keepRange(root.left, a, b);
                root.right = keepRange(root.right, a, b);
            }
        }
        //reconstruct tree based on removed nodes
        root.left = keepRange(root.left, a, b);
        root.right = keepRange(root.right, a, b);
        int element = Integer.parseInt(root.element.toString());
        //return left/right based on comparison
        if(element < a){
            return root.right;
        }
        if(element > b){
            return root.left;
        }
        //remove root otherwise
        return root;
     }
    //PRIVATE

     /**
     * Build a NON BST tree by preorder
     *
     * @param arr    nodes to be added
     * @param height maximum height of tree
     * @param parent parent of subtree to be created
     * @return new tree
     */
    private BinaryNode<E> buildTree(ArrayList<E> arr, int height, BinaryNode<E> parent) {
        if (arr.isEmpty()) return null;
        BinaryNode<E> curr = new BinaryNode<>(arr.remove(0), null, null, parent);
        if (height > 0) {
            curr.left = buildTree(arr, height - 1, curr);
            curr.right = buildTree(arr, height - 1, curr);
        }
        return curr;
    }

    /**
     * Internal method to insert into a subtree.
     * In tree is balanced, this routine runs in O(log n)
     *
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<E> bstInsert(E x, BinaryNode<E> t, BinaryNode<E> parent) {
        if (t == null)
            return new BinaryNode<>(x, null, null, parent);

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0) {
            t.left = bstInsert(x, t.left, t);
        } else {
            t.right = bstInsert(x, t.right, t);
        }

        return t;
    }


    /**
     * Internal method to find an item in a subtree.
     * This routine runs in O(log n) as there is only one recursive call that is executed and the work
     * associated with a single call is independent of the size of the tree: a=1, b=2, k=0
     *
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     *          SIDE EFFECT: Sets local variable curr to be the node that is found
     * @return node containing the matched item.
     */
    private boolean bstContains(E x, BinaryNode<E> t) {
        curr = null;
        if (t == null)
            return false;

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            return bstContains(x, t.left);
        else if (compareResult > 0)
            return bstContains(x, t.right);
        else {
            curr = t;
            return true;    // Match
        }
    }



    /**
     * Internal method to return a string of items in the tree in order
     * This routine runs in O(??)
     * @param t the node that roots the subtree.
     */
    private String toString2(BinaryNode<E> t) {
        if (t == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(toString2(t.left));
        sb.append(t.element.toString() + " ");
        sb.append(toString2(t.right));
        return sb.toString();
    }

    // Basic node stored in unbalanced binary  trees
    private static class BinaryNode<AnyType> {
        AnyType element;            // The data in the node
        BinaryNode<AnyType> left;   // Left child
        BinaryNode<AnyType> right;  // Right child
        BinaryNode<AnyType> parent; //  Parent node

        // Constructors
        BinaryNode(AnyType theElement) {
            this(theElement, null, null, null);
        }

        BinaryNode(AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt, BinaryNode<AnyType> pt) {
            element = theElement;
            left = lt;
            right = rt;
            parent = pt;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Node:");
            sb.append(element);
            if (parent == null) {
                sb.append("<>");
            } else {
                sb.append("<");
                sb.append(parent.element);
                sb.append(">");
            }

            return sb.toString();
        }

    }


    // Test program
    public static void main(String[] args) {
        long seed = 436543;
        Random generator = new Random(seed);  // Don't use a seed if you want the numbers to be different each time
        final String ENDLINE = "\n";

        int val = 60;
        final int SIZE = 8;

        Integer[] v1 = {25, 10, 60, 55, 58, 56, 14, 63, 8, 50, 6, 9};
        ArrayList<Integer> v2 = new ArrayList<Integer>();
        for (int i = 0; i < SIZE * 2; i++) {
            int t = generator.nextInt(200);
            v2.add(t);
        }
        v2.add(val);
        Integer[] v3 = {200, 15, 3, 65, 83, 70, 90};
        ArrayList<Integer> v4 = new ArrayList<Integer>(Arrays.asList(v3));
        Integer[] v = {21, 8, 5, 6, 7, 19, 10, 40, 43, 52, 12, 60};
        ArrayList<Integer> v5 = new ArrayList<Integer>(Arrays.asList(v));
        Integer[] inorder = {4, 2, 1, 7, 5, 8, 3, 6};
        Integer[] preorder = {1, 2, 4, 3, 5, 7, 8, 6};


        Tree<Integer> tree1 = new Tree<Integer>(v1, "Tree1:");
        Tree<Integer> tree2 = new Tree<Integer>(v2, "Tree2:");
        Tree<Integer> tree3 = new Tree<Integer>(v3, "Tree3:");
        Tree<Integer> treeA = new Tree<Integer>(v4, "TreeA:", 2);
        Tree<Integer> treeB = new Tree<Integer>(v5, "TreeB", 3);
        Tree<Integer> treeC = new Tree<Integer>("TreeC");
        System.out.println(tree1.toString());
        System.out.println(tree1.toString2());
        System.out.println(treeA.toString());
//
        treeA.flip(treeA.root);
        System.out.println("Now flipped" + treeA.toString());
//
        System.out.println(tree2.toString());
        tree2.contains(val);  //Sets the current node inside the tree6 class.
        int succCount = 5;  // how many successors do you want to see?
        System.out.println("In Tree2, starting at " + val + ENDLINE);
        for (int i = 0; i < succCount; i++) {
            System.out.println("The next successor is " + tree2.successor(val));
            val = Integer.parseInt(tree2.successor(val));
        }

        System.out.println(tree1.toString());
        for (int mylevel = 0; mylevel < SIZE; mylevel += 1) {
            System.out.println("Number nodes at level " + mylevel + " is " + tree1.nodesInLevel(mylevel));
        }
            System.out.println("All paths from tree1");
            tree1.printAllPaths();
//        Could not complete code, have to comment out
//        System.out.print("Tree1 byLevelZigZag: ");
//        tree1.byLevelZigZag(5);
//        System.out.print("Tree2 byLevelZigZag (3): ");
//        tree2.byLevelZigZag(3);
        treeA.flip();
        System.out.println(treeA.toString());
        System.out.println("treeA Contains BST: " + treeA.countBST());

        System.out.println(treeB.toString());
        System.out.println("treeB Contains BST: " + treeB.countBST());
//        Code will not work, have to comment out.
//        treeB.pruneK(60);
//        treeB.changeName("treeB after pruning 60");
//        System.out.println(treeB.toString());
//        treeA.pruneK(220);
//        treeA.changeName("treeA after pruning 220");
//        System.out.println(treeA.toString());
//
        treeC.root = treeC.buildTreeTraversals(inorder, preorder);
        treeC.changeName("Tree C built from inorder and preorder traversals");
        System.out.println(treeC.toString());
//
        System.out.println(tree1.toString());
        System.out.println("tree1 Least Common Ancestor of (56,61) " + tree1.lca(tree1.root,56, 61) + ENDLINE);

        System.out.println("tree1 Least Common Ancestor of (6,25) " + tree1.lca(tree1.root, 6, 25) + ENDLINE);
        System.out.println(tree3.toString());
        tree3.balanceTree();
        tree3.changeName("tree3 after balancing");
        System.out.println(tree3.toString());

        System.out.println(tree1.toString());
        tree1.keepRange(tree1.root,10, 50);
        tree1.changeName("tree1 after keeping only nodes between 10 and 50");
        System.out.println(tree1.toString());
        tree3.keepRange(tree3.root,3, 85);
        tree3.changeName("tree3 after keeping only nodes between 3  and 85");
        System.out.println(tree3.toString());
    }

}
//---------------------OUTPUT--------------------------

//1. toString()
/*
//Tree1:
//63 [60]
//60 [25]
//58 [55]
//56 [58]
//55 [60]
//50 [55]
//25 [no parent]
//14 [10]
//10 [25]
//9 [8]
//8 [10]
//6 [8]
//
//Tree1: 6 8 9 10 14 25 50 55 56 58 60 63
//TreeA:
//90 [83]
//83 [200]
//70 [83]
//200 [no parent]
//65 [15]
//15 [200]
//3 [15]
 */
//2. flip()
/*
//Now flippedTreeA:
//3 [15]
//15 [200]
//65 [15]
//200 [no parent]
//70 [83]
//83 [200]
//90 [83]
 */
//3. successor()
/*
//The next successor is 67
//The next successor is 70
//The next successor is 76
//The next successor is 77
//The next successor is 92
 */
//4. nodesInLevel(level)
/*
///Tree1:
//63 [60]
//60 [25]
//58 [55]
//56 [58]
//55 [60]
//50 [55]
//25 [no parent]
//14 [10]
//10 [25]
//9 [8]
//8 [10]
//6 [8]
//
//Number nodes at level 0 is 1
//Number nodes at level 1 is 2
//Number nodes at level 2 is 4
//Number nodes at level 3 is 4
//Number nodes at level 4 is 1
//Number nodes at level 5 is 0
//Number nodes at level 6 is 0
//Number nodes at level 7 is 0
 */
//5. printAllPaths()
/*
///All paths from tree1
//[25, 60, 63]
//[25, 60, 55, 58, 56]
//[25, 60, 55, 50]
//[25, 10, 14]
//[25, 10, 8, 9]
//[25, 10, 8, 6]
 */
//6. byLevelZigZag
/*
///COULDN'T SOLVE THIS ONE FULLY, ONLY GIVEN ERROR
 */
//7. countBST()
/*
TreeA:
90 [83]
83 [200]
70 [83]
200 [no parent]
65 [15]
15 [200]
3 [15]

83
70
90
15
3
65
treeA Contains BST: 6
TreeB
43 [21]
60 [52]
52 [43]
12 [52]
21 [no parent]
40 [19]
19 [8]
10 [19]
8 [21]
7 [5]
5 [8]
6 [5]

52
12
60
19
10
40
7
6
treeB Contains BST: 8

 */
//8. pruneK(int k)
/*
// COULDN'T SOLVE THIS ONE FULLY, ONLY GIVEN ERROR
 */
//9. buildTreeTraversals(...)
/*
//Tree C built from inorder and preorder traversals
//6 [3]
//3 [1]
//8 [5]
//5 [3]
//7 [5]
//1 [no parent]
//2 [1]
//4 [2]
 */
//10. lca(..)
/*
Tree1:
63 [60]
60 [25]
58 [55]
56 [58]
55 [60]
50 [55]
25 [no parent]
14 [10]
10 [25]
9 [8]
8 [10]
6 [8]

tree1 Least Common Ancestor of (56,61) 60

tree1 Least Common Ancestor of (6,25) 25
 */
//11.balanceTree()
/*
Tree3:
200 [no parent]
90 [83]
83 [65]
70 [83]
65 [15]
15 [200]
3 [15]

tree3 after balancing
83 [90]
90 [70]
200 [90]
70 [no parent]
3 [15]
15 [70]
65 [15]
 */
//12. keepRange(int first, int last)
/*
Tree1:
63 [60]
60 [25]
58 [55]
56 [58]
55 [60]
50 [55]
25 [no parent]
14 [10]
10 [25]
9 [8]
8 [10]
6 [8]

tree1 after keeping only nodes between 10 and 50
50 [25]
25 [no parent]
14 [10]
10 [25]

tree3 after keeping only nodes between 3  and 85
83 [65]
70 [83]
65 [15]
15 [no parent]
3 [15]
 */
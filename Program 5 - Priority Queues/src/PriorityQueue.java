/***
 *
 * Some of my code is optimized by referencing the textbook's example, which had much more efficient code.
 */

public class PriorityQueue<AnyType extends Comparable<? super AnyType>> {
    private node<AnyType> root;
    private static class node<AnyType> {
        node(AnyType x) {
            this(x, null, null);
        }

        node(AnyType x, node<AnyType> leftChild, node<AnyType> rightChild) {
            left = leftChild;
            right = rightChild;
            value = x;
            path = 0;
        }
        AnyType value;
        node<AnyType> left;
        node<AnyType> right;
        int path;
    }
    //leftist heap initializer
    public PriorityQueue() {
        root = null;
    }
    //when inserting, create a new empty heap and merge
    public void insert(AnyType node) {
        root = merge(new node<>(node), root);
    }
    //helper method for merge (inspired by textbook which made it much simpler)
    private node<AnyType> merge(node<AnyType> first, node<AnyType> second) {
        if (second == null) {
            return first;
        }
        if (first == null) {
            return second;
        }
        if (first.value.compareTo(second.value) < 0) {
            return completeMerge(first, second);
        }
        //merge with second becoming root
        else {
            return completeMerge(second, first);
        }
    }
    public AnyType getMin(){
        if(root == null){
            return null;
        }
        return root.value;
    }

    private static <AnyType> void swap(node<AnyType> tree) {
        //temp variable
        node<AnyType> left = tree.left;
        tree.left = tree.right;
        tree.right = left;
    }
    //return the root and merge children
    public AnyType deleteMin() {
        if (root == null) {
            return null;
        }
        AnyType min = root.value;
        root = merge(root.left, root.right);
        return min;
    }
    private node<AnyType> completeMerge(node<AnyType> first, node<AnyType> second) {
        if (first.left == null) {
            first.left = second;
        }
        else {
            first.right = merge(first.right, second);
            if (first.left.path < first.right.path) {
                swap(first);
            }
            first.path = first.right.path + 1;
        }
        return first;
    }
}

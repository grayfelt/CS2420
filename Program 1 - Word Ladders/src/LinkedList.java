public class LinkedList<E extends WordInfo> {
    //initialize the head/tail/words/next
    Node head;
    Node tail;
    static class Node{
        WordInfo words;
        Node next;
        //constructor to add a concatenated string of all the words in a ladder ex.(slowblowbrow)
        Node(WordInfo d){
            words = d;
            next = null;
        }
    }

    //insert method, FIFO implementation for the correct queue function
    public void insert(WordInfo words){
        Node current = new Node(words);
        //case for if list is empty
        if(head == null) {
            head = current;
            tail = current;
            return;
        }
        //add at the end of the queue, the tail
        tail.next = current;
        tail = current;

    }

    //insert method, also implementing FIFO
    public WordInfo dequeue(){
        Node current = head;
        if (head == null){
            return null;
        }
        head = head.next;
        if(head == null){
            tail = null;
        }
        //returning the non-null head of the queue, the first element added
        return current.words;

    }
    public Boolean isEmpty(){
        return(head == null);
    }
}

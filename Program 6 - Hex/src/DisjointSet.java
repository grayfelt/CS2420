import java.util.ArrayList;

class DisjointSet {
    private ArrayList<Integer> sets;
    DisjointSet(){
        sets = new ArrayList<>();
    }
    private void printList(){
        for(int a = 1; a < sets.size(); a++){
            System.out.println(a);
            System.out.println(sets.get(a) + "\t");
            System.out.println("\t");
        }
    }
    void insert(int a){
        if(a < 0){
            System.out.println("Cannot insert a number < 0");
            return;
        }
        //implemented for any size, not just 121
        try{
            sets.set(a, -1);
        } catch(IndexOutOfBoundsException e){
            while(sets.size() - 1 < a){
                sets.add(0);
            }
            sets.set(a, -1);
        }
    }
    int find(int cell){
        try {
            int parent = cell;
            if (sets.get(cell) == 0) {
                return 0;
            }
            while (sets.get(parent) > 0) parent = sets.get(parent);
            return parent;
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }
    }

    private void smartFind(int cell){
        //get root
        int root = find(cell);
        if (root == 0) return;
        //get parent
        int parent = sets.get(cell);
        int temp = cell;
        //bump node up one row to get closer to parent
        while (parent > 0 && temp != root) {
            sets.set(temp, root);
            temp = parent;
            parent = sets.get(parent);
        }
    }
    private void union(int cell1, int cell2) {
        int root1 = find(cell1);
        int root2 = find(cell2);
        //not a cell
        if (root1 == 0 || root2 == 0) {
            return;
        }
        else{
            //already connected
            if (root1 == root2) {
                smartFind(cell1);
                smartFind(cell2);
            }
            else{
                //cell1 is smaller
                if (sets.get(root1) <= sets.get(root2)) {
                    sets.set(root1, sets.get(root1) + sets.get(root2));
                    sets.set(root2, root1);
                }
                //cell2 is smaller
                else {
                    sets.set(root2, sets.get(root2) + sets.get(root1));
                    sets.set(root1, root2);
                }
            }
        }
    }
    void union2(int cell, int[] neighbors) {
        for (int cell2 : neighbors) {
            if (cell2 > 0) {
                union(cell, cell2);
            }
       }
    }

    public static void main(String[] args) {
        //Test

        //insert works properly
        DisjointSet set = new DisjointSet();
        set.insert(1);
        set.insert(2);
        set.insert(3);
        System.out.println("Set1:");
        set.printList();

        //union works properly
        DisjointSet set2 = new DisjointSet();
        set2.insert(1);
        set2.insert(10);
        set2.insert(100);
        set2.insert(101);
        System.out.println("Set2 before union");
        System.out.println("10 parent: " + set2.find(10) + " | " + "101 parent: " + set2.find(101));
        System.out.println("Set2 after union");
        set2.union(10,101);
        System.out.println("10 parent: " + set2.find(10) + " | " + "101 parent: " + set2.find(101));

        //only the 1 is inserted
        DisjointSet set3 = new DisjointSet();
        set3.insert(0);
        set3.insert(-1);
        set3.insert(1);
        System.out.println("Set3:");
        set3.printList();

        //union 10 with every fifth number and then two with every even number
        //even numbers unioned with 10 up tree
        DisjointSet set4 = new DisjointSet();
        for(int i = 0; i < 51; i++){
            set4.insert(i);
        }
        for(int a = 5; a < 51; a+=5){
            set4.union(a, a-5);
        }
        for(int b = 2; b < 50; b+=2){
            set4.union(b, b-2);
        }
        set4.find(2);
        set4.find(4);
        set4.find(6);
        set4.printList();
    }
}


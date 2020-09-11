public class Task2 extends Task {
    public Task2(int ID, int start, int deadline, int duration) {
        super(ID,start,deadline,duration);
    }
    // Priority is start time
    @Override
    public int compareTo(Task t2) {
//        System.out.println("Using Task2 compareTo");
        if(start == t2.start){
            if(deadline > t2.deadline){
                return 1;
            }
            else if(t2.deadline > deadline){
                return -1;
            }
            else{
                return 0;
            }
        }
        return start-t2.start;
    }

}

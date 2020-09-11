public class Task3 extends Task {
    public Task3(int ID, int start, int deadline, int duration) {
        super(ID,start,deadline,duration);
    }
    // Prioirity is distance from goal
    @Override
    public int compareTo(Task t2) {
//        System.out.println("Using Task3 compareTo");
        return (duration - t2.duration);
    }

}

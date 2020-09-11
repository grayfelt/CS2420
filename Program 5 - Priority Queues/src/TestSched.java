import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class TestSched {


    public static void readTasks(String filename,
                          ArrayList<Task> task1, ArrayList<Task> task2,
                                 ArrayList<Task> task3) {
        // Create lists where base type is different
        int start;
        int deadline;
        int mins;
        int id = 0;
        File file = new File("src/" + filename);
        try{
            Scanner sc = new Scanner(file);
            while(sc.hasNextInt()){
                id += 1;
                start = Integer.parseInt(sc.next());
                deadline = Integer.parseInt(sc.next());
                mins = Integer.parseInt(sc.next());
                //for each set of three numbers, create three tasks
                Task t1 = new Task1(id, start, deadline, mins);
                Task t2 = new Task2(id, start, deadline, mins);
                Task t3 = new Task3(id, start, deadline, mins);
                //add each to their respected queues
                task1.add(t1);
                task2.add(t2);
                task3.add(t3);
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }


    }

    public static void main(String args[]) {
        Scheduler s = new Scheduler();
        String [] files = {"taskset1.txt","taskset2.txt","taskset3.txt","taskset4.txt","taskset5.txt" };
        for (String f : files) {
            ArrayList<Task> t1 = new ArrayList();    // elements are Task1
            ArrayList<Task> t2 = new ArrayList();    // elements are Task2
            ArrayList<Task> t3 = new ArrayList();    // elements are Task3
            readTasks(f, t1, t2, t3);
            s.makeSchedule("Deadline Priority "+ f, t1);
            s.makeSchedule("Start time Priority " + f, t2);
            s.makeSchedule("Wild and Crazy priority " + f, t3);
       }

    }
}

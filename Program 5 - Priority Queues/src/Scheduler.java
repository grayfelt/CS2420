import java.util.ArrayList;

public class Scheduler {
    public void makeSchedule(String title, ArrayList<Task> t){
        System.out.println(title);
        PriorityQueue queue = new PriorityQueue();
        int time = 0;
        int lateTasks = 0;
        int lateTally = 0;
        //create queue
        for(Task a : t){
            queue.insert(a);
        }
        //find min to make sure we aren't dealing with a null-root queue
        Task min = (Task) queue.getMin();
        while(!(min==null)){
            Task current = (Task) queue.deleteMin();
            time+=1;
            //end of queue
            if(current == null){
                break;
            }
            //increment the amount of time worked on task once, and then reevaluate
            current.timeWorked += 1;
            //if we have worked on this task for as long as needed
            if(current.timeWorked >= current.duration){
                //late, completed
                if(time > current.deadline){
                    lateTasks += 1;
                    lateTally += time - current.deadline;
                    System.out.println("Time: " + time + " Task: " + current.ID + "**" + " Late: " + (time-current.deadline));
                }
                //not late, and completed
                else{System.out.println("Time: " + time + " Task: " + current.ID + "**");}
            }
            //not completed, shove back into queue
            else{
                System.out.println("Time: " + time + " Task: " + current.ID);
                queue.insert(current);
            }
        }
        System.out.println("Tasks late: " + lateTasks + " || Total Late: " + lateTally);
        System.out.println();

    }
}

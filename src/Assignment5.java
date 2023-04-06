import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Assignment5 {
    public static void main(String[] args) {
        scheduleTasks("taskset1.txt");
        scheduleTasks("taskset2.txt");
        scheduleTasks("taskset3.txt");
        scheduleTasks("taskset4.txt");
        scheduleTasks("taskset5.txt");
    }

    public static void scheduleTasks(String taskFile) {
        ArrayList<Task> tasksByDeadline = new ArrayList<>();
        ArrayList<Task> tasksByStart = new ArrayList<>();
        ArrayList<Task> tasksByDuration = new ArrayList<>();

        readTasks(taskFile, tasksByDeadline, tasksByStart, tasksByDuration);

        schedule("Deadline Priority : "+ taskFile, tasksByDeadline);
        schedule("Startime Priority : " + taskFile, tasksByStart);
        schedule("Duration priority : " + taskFile, tasksByDuration);
    }

    public static void simpleQueueTest() {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        queue.enqueue(9);
        queue.enqueue(7);
        queue.enqueue(5);
        queue.enqueue(3);
        queue.enqueue(1);
        queue.enqueue(10);

        while (!queue.isEmpty()) {
            System.out.println(queue.dequeue());
        }
    }

    public static void readTasks(String filename,
                                 ArrayList<Task> tasksByDeadline,
                                 ArrayList<Task> tasksByStart,
                                 ArrayList<Task> tasksByDuration) {
        File file1 = new File(filename);
        try(Scanner input = new Scanner(file1)){
            int counter = 1;
            while (input.hasNextLine()){
                String[] splitLine = input.nextLine().split("\t");
                tasksByDuration.add(new TaskByDuration(counter,Integer.parseInt(splitLine[0]),Integer.parseInt(splitLine[1]),Integer.parseInt(splitLine[2])));
                tasksByStart.add(new TaskByStart(counter,Integer.parseInt(splitLine[0]),Integer.parseInt(splitLine[1]),Integer.parseInt(splitLine[2])));
                tasksByDeadline.add(new TaskByDeadline(counter,Integer.parseInt(splitLine[0]),Integer.parseInt(splitLine[1]),Integer.parseInt(splitLine[2])));
                counter++;
            }

        } catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the dictionary: " + ex);
        }
    }

    public static void schedule(String label, ArrayList<Task> tasks) {
        System.out.println(label);
        PriorityQueue<Task> priorityQueue = new PriorityQueue<>();
        int time = 1;
        int lateTasks = 0;
        int lateAmount = 0;
        ArrayList<Task> waitingArray = new ArrayList<>(tasks);
        while(waitingArray.size() != 0 || !priorityQueue.isEmpty()) {
            String return1 = "";
            if (waitingArray.size() != 0) {
                ArrayList<Task> removedElements = new ArrayList<>();
                for (Task task : waitingArray) {
                    if (task.start == time) {
                        priorityQueue.enqueue(task);
                        removedElements.add(task);
                    }
                }
                for (Task removedElement : removedElements) {
                    waitingArray.remove(removedElement);
                }
            }


            if (priorityQueue.isEmpty()){
                return1 += " ---";
            }
            else {
                Task priorityElement = priorityQueue.dequeue();
                priorityElement.duration--;
                return1 += priorityElement;
                if (priorityElement.duration == 0) {
                    return1 += " (Task finished)";
                    if (priorityElement.deadline < time){
                        lateTasks++;
                        return1 += " Late " + (time- priorityElement.deadline);
                        lateAmount+=(time-priorityElement.deadline);
                    }
                } else {
                    priorityQueue.enqueue(priorityElement);
                }


            }
            System.out.println("Time " + time + "\t:  " + return1);
            time++;
        }
        System.out.println("Tasks late " + lateTasks + " Total late " + lateAmount);
        System.out.println("\n");

    }
}

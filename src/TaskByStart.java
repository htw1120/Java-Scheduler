public class TaskByStart extends Task {
    public TaskByStart(int ID, int start, int deadline, int duration) {
        super(ID, start, deadline, duration);
    }

    @Override
    public int compareTo(Task o) {
        if (start > o.start) {
            return 1;
        }
        else if (start < o.start) {
            return -1;
        }
        else {
            if (deadline > o.deadline) {
                return 1;
            }
            else {
                return -1;
            }
        }
    }
}

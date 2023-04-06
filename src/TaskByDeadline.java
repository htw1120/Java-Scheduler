public class TaskByDeadline extends Task {
    public TaskByDeadline(int ID, int start, int deadline, int duration) {
        super(ID, start, deadline, duration);
    }

    @Override
    public int compareTo(Task o) {
        return Integer.compare(deadline, o.deadline);
    }
}

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        tasks.add(task);
    }

    @Override
    public String toString() {
        if (tasks.isEmpty()) {
            return "There is nothing here. [sigh]";
        } else {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < tasks.size(); i++) {
                sb.append(i + 1);
                sb.append(".");
                sb.append(tasks.get(i));
                if (i < tasks.size() - 1) sb.append("\n");
            }

            return "Here are your current tasks:\n" + sb.toString();
        }
    }

    public Task deleteTask(int index) throws MarvinException {
        checkIndex(index);
        return tasks.remove(index - 1);
    }

    public Task markTask(int index) throws MarvinException {
        checkIndex(index);
        Task selectedTask = tasks.get(index - 1);
        selectedTask.mark();
        return selectedTask;
    }

    public Task unmarkTask(int index) throws MarvinException {
        checkIndex(index);
        Task selectedTask = tasks.get(index - 1);
        selectedTask.unmark();
        return selectedTask;
    }

    public Task getTask(int index) throws MarvinException {
        checkIndex(index);
        return tasks.get(index - 1);
    }

    public int numTasks() {
        return tasks.size();
    }

    private void checkIndex(int index) throws MarvinException {
        if (index <= 0 || index > tasks.size()) { // 1-based indexing,
            throw new MarvinException("That task does not exist. I checked. Twice.");
        }
    }
}

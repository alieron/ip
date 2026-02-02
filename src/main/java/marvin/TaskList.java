package marvin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import marvin.task.Task;

/**
 * Provides an abstraction for the list of tasks.
 */
public class TaskList {
    private List<Task> tasks;

    /**
     * Instantiates a new TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Overloaded constructor.
     * Instantiates a new TaskList from an existing list of tasks.
     *
     * @param tasks The list of tasks
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Add a task to the list of tasks.
     *
     * @param newTasks The task
     */
    public void addTask(Task ... newTasks) {
        tasks.addAll(Arrays.asList(newTasks));
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
                if (i < tasks.size() - 1) {
                    sb.append("\n");
                }
            }

            return sb.toString();
        }
    }

    /**
     * Delete a task at a specific one-based index.
     *
     * @param index The one-based index
     * @return The task
     * @throws MarvinException If no task is found at the provided index
     */
    public Task deleteTask(int index) throws MarvinException {
        checkIndex(index);
        return tasks.remove(index - 1);
    }

    /**
     * Mark a task at a specific one-based index.
     *
     * @param index The one-based index
     * @return The task
     * @throws MarvinException If no task is found at the provided index
     */
    public Task markTask(int index) throws MarvinException {
        checkIndex(index);
        Task selectedTask = tasks.get(index - 1);
        selectedTask.mark();
        return selectedTask;
    }

    /**
     * Unmark a task at a specific one-based index.
     *
     * @param index The one-based index
     * @return The task
     * @throws MarvinException If no task is found at the provided index
     */
    public Task unmarkTask(int index) throws MarvinException {
        checkIndex(index);
        Task selectedTask = tasks.get(index - 1);
        selectedTask.unmark();
        return selectedTask;
    }

    /**
     * Returns a task at a specific one-based index.
     *
     * @param index The one-based index
     * @return The task
     * @throws MarvinException If no task is found at the provided index
     */
    public Task getTask(int index) throws MarvinException {
        checkIndex(index);
        return tasks.get(index - 1);
    }

    /**
     * Returns the number of tasks currently in the list of tasks.
     *
     * @return The number of tasks
     */
    public int numTasks() {
        return tasks.size();
    }

    private void checkIndex(int index) throws MarvinException {
        if (index <= 0 || index > tasks.size()) { // 1-based indexing,
            throw new MarvinException("That task does not exist. I checked. Twice.");
        }
    }

    /**
     * Returns the list of tasks.
     *
     * @return The entire list of tasks
     */
    public List<Task> getTasks() {
        return this.tasks;
    }

    public TaskList findTasksContains(String snippet) {
        return new TaskList(this.tasks.stream().filter(t -> t.checkDescContains(snippet)).toList());
    }
}

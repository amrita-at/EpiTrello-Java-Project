package fr.epita.epitrello.datamodel;

public class Task {

	private String task_name;
	private int estimated_time;
	private int priority;
	private String description;
	private String list_name;
	private String assigned_user;
	private boolean completed;

	public Task(String list_name, String task_name, int estimated_time, int priority, String description) {

		this.task_name = task_name;
		this.estimated_time = estimated_time;
		this.priority = priority;
		this.description = description;
		this.list_name = list_name;
		this.assigned_user = "Unassigned";
		this.completed = false;

	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public String getTask_name() {
		return task_name;
	}

	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}

	public int getEstimated_time() {
		return estimated_time;
	}

	public void setEstimated_time(int estimated_time) {
		this.estimated_time = estimated_time;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getList_name() {
		return list_name;
	}

	public void setList_name(String list_name) {
		this.list_name = list_name;
	}

	public String getAssigned_user() {
		return assigned_user;
	}

	public void setAssigned_user(String assigned_user) {
		this.assigned_user = assigned_user;
	}

	@Override
	public String toString() {
		return priority + "|" + task_name + "|" + assigned_user + "|" + estimated_time + "h";
	}

}

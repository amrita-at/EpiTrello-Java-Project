package fr.epita.epitrello.datamodel;

import java.util.*;

public class EpitrelloDataService {

	Map<String, String> userMap;
	Map<String, String> listMap;
	Map<String, Task> taskMap;

	public EpitrelloDataService() {
		userMap = new HashMap<String, String>();
		listMap = new HashMap<String, String>();
		taskMap = new HashMap<String, Task>();
	}

	public String addUser(String name) {
		if (UserExists(name)) {
			return "User Already Exists!";
		}
		userMap.put(name, name);
		return "Success";
	}

	public String addList(String name) {
		if (ListExists(name)) {
			return "List Already Exists!";
		}
		listMap.put(name, name);
		return "Success";
	}

	public String addTask(String list, String task, int time, int priority, String description) {

		Task newtask = new Task(list, task, time, priority, description);
		if (taskMap.containsKey(newtask.getTask_name())) {
			return "Task Already Exists!";
		}
		taskMap.put(newtask.getTask_name(), newtask);
		return "Success";
	}

	public String editTask(String task, int time, int priority, String description) {

		Task EditTask = new Task("listname", task, time, priority, description);

		if (TaskExists(task)) {
			// Replacing the values of the task object on basis of task name as the key
			taskMap.replace(task, EditTask);
			return "Success";
		}
		return "Task does not exist!";
	}

	public String assignTask(String taskname, String username) {
		Task UpdateTask = taskMap.get(taskname);
		if (TaskExists(taskname)) {
			UpdateTask.setAssigned_user(username);
			return "Success";
		}
		return "Error in assigning the task!";
	}

	public String printTask(String taskname) {
		return getTaskDetails(taskname);
	}

	public String completeTask(String taskname) {
		Task CompleteTask = taskMap.get(taskname);
		if (TaskExists(taskname)) {
			CompleteTask.setCompleted(true);
			return "Success";
		}
		return "Error!";
	}

	// Checking whether the task is not null and exists in the taskMap
	public boolean TaskExists(String task_name) {
		if (task_name != null && taskMap.containsKey(task_name))
			return true;
		return false;
	}

	// Checking whether the list is not null and exists in the listMap
	public boolean ListExists(String list_name) {
		if (list_name != null && listMap.containsKey(list_name))
			return true;
		return false;
	}

	// Checking whether the user is not null and exists in the userMap
	public boolean UserExists(String user_name) {
		if (user_name != null && userMap.containsKey(user_name))
			return true;
		return false;
	}

	// getting all the details of the task
	public String getTaskDetails(String taskname) {
		String rsp = null;
		Task taskDetails = taskMap.get(taskname);
		if (TaskExists(taskname)) {
			rsp = taskDetails.getTask_name() + "\n" + taskDetails.getDescription() + "\nPriority: "
					+ taskDetails.getPriority() + "\nEstimated Time: " + taskDetails.getEstimated_time()
					+ "\nAssigned to " + taskDetails.getAssigned_user();
			return rsp;
		} else
			return "Task does not exist!";

	}

	public String printUsersByPerformance() {
		Map<String, Integer> userPerformance = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		userMap.forEach((entry, val) -> {
			int time = RemainingTime(val);
			userPerformance.put(entry, time);
		});
		userPerformance.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach((entry) -> {
			sb.append(entry.getKey().toString() + "\n");
		});
		;
		return sb.toString();
	}

	public String printUsersByWorkload() {
		Map<String, Integer> usertime = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		userMap.forEach((entry, val) -> {
			int time = TotalEstTime(val);
			usertime.put(entry, time);
		});
		usertime.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach((entry) -> {
			sb.append(entry.getKey().toString() + "\n");
		});
		;
		return sb.toString();
	}

	public String printUnassignedTasksByPriority() {
		Map<Task, Integer> taskpriority = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		taskMap.forEach((entry, val) -> {
			if (val.getAssigned_user().equalsIgnoreCase("Unassigned"))
				taskpriority.put(val, val.getPriority());
		});
		taskpriority.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach((entry) -> {
			sb.append(entry.getKey().toString() + "\n");
		});
		;
		return sb.toString();
	}

	public String deleteTask(String taskname) {
		if (TaskExists(taskname)) {
			taskMap.remove(taskname);
			return "Success";
		}
		return "Error/Task does not exist!";
	}

	public String printAllUnfinishedTasksByPriority() {
		Map<Task, Integer> taskpriority = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		taskMap.forEach((entry, val) -> {
			if (!val.isCompleted())
				taskpriority.put(val, val.getPriority());
		});
		taskpriority.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach((entry) -> {
			sb.append(entry.getKey().toString() + "\n");
		});
		;
		return sb.toString();
	}

	public String moveTask(String taskname, String listname) {
		if (ListExists(listname) && TaskExists(taskname)) {
			taskMap.get(taskname).setList_name(listname);
			return "Success";
		}
		return "Error- list/task does not exist!";
	}

	public String printList(String listname) {
		StringBuilder sb = new StringBuilder();
		sb.append("\nList " + listname+ "\n");
		taskMap.forEach((entry, val) -> {
			if (val.getList_name().equalsIgnoreCase(listname)) {
				sb.append(val.toString() + "\n");
			}
		});
		return sb.toString();
	}

	public String printAllLists() {
		StringBuilder sb = new StringBuilder();
		listMap.forEach((entry, val) -> {
			sb.append(val.toString() + "\n");
		});
		return sb.toString();
	}

	// Returns the total estimated time of each of an user
	public Integer TotalEstTime(String username) {
		if (UserExists(username)) {
			Integer totalEst = taskMap.entrySet().stream()
					.filter((entry) -> entry.getValue().getAssigned_user().equalsIgnoreCase(username))
					.mapToInt((entry) -> entry.getValue().getEstimated_time()).sum();
			return totalEst;
		}
		return 0;
	}

	// Returns total time of completed task of an user
	public Integer CompletedTime(String username) {
		if (UserExists(username)) {
			Integer completeTime = taskMap.entrySet().stream().filter((entry) -> {
				Task task = entry.getValue();
				return task.getAssigned_user().equalsIgnoreCase(username) && task.isCompleted();
			}).mapToInt((entry) -> entry.getValue().getEstimated_time()).sum();
			return completeTime;
		}
		return 0;
	}

	// Returns remaining time for tasks of an user
	public Integer RemainingTime(String username) {
		if (UserExists(username)) {
			return TotalEstTime(username) - CompletedTime(username);
		}
		return 0;
	}

	public String printUserTasks(String username) {
		if (UserExists(username)) {
			StringBuilder sb = new StringBuilder();
			taskMap.forEach((entry, val) -> {
				if (val.getAssigned_user().equalsIgnoreCase(username)) {
					sb.append(val.toString() + "\n");
				}
			});
			return sb.toString();
		}
		return "User does not exist!";
	}

}

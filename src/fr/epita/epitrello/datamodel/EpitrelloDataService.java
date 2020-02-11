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
	
	/**
	* Adding users to the card
	* @param name
	* @return
	*/
	public String addUser(String name) {
		if (UserExists(name)) {
			return "User Already Exists!";
		}
		userMap.put(name, name);
		return "Success";
	}
	
	/**
	* Adding list to the card
	* @param name
	* @return
	*/
	public String addList(String name) {
		if (ListExists(name)) {
			return "List Already Exists!";
		}
		listMap.put(name, name);
		return "Success";
	}
	
	/**
	* Adding new task elements into the card linking with the list name
	* @param list
	* @param task
	* @param time
	* @param priority
	* @param description
	* @return
	*/
	public String addTask(String list, String task, int time, int priority, String description) {

		Task newtask = new Task(list, task, time, priority, description);
		if (taskMap.containsKey(newtask.getTask_name())) {
			return "Task Already Exists!";
		}
		taskMap.put(newtask.getTask_name(), newtask);
		return "Success";
	}
	
	/**
	* Editing the existing task
	* @param task
	* @param time
	* @param priority
	* @param description
	* @return
	*/
	public String editTask(String task, int time, int priority, String description) {

		Task EditTask = new Task("listname", task, time, priority, description);

		if (TaskExists(task)) {
			// Replacing the values of the task object on basis of task name as the key
			taskMap.replace(task, EditTask);
			return "Success";
		}
		return "Task does not exist!";
	}
	
	/**
	* Assigning the existing task to the valid users
	* @param taskname
	* @param username
	* @return
	*/
	public String assignTask(String taskname, String username) {
		Task UpdateTask = taskMap.get(taskname);
		if (TaskExists(taskname)) {
			UpdateTask.setAssigned_user(username);
			return "Success";
		}
		return "Error in assigning the task!";
	}

	/**
	*Printing the task details of the specific task by calling the function getTaskDetails
	* @param taskname
	* @return the function
	*/
	public String printTask(String taskname) {
		return getTaskDetails(taskname);
	}

	/**
	*Setting the task status to complete
	* @param taskname
	* @return
	*/
	public String completeTask(String taskname) {
		Task CompleteTask = taskMap.get(taskname);
		if (TaskExists(taskname)) {
			CompleteTask.setCompleted(true);
			return "Success";
		}
		return "Error!";
	}
	
	/**
	*Checking whether the task is not null and exists in the taskMap
	* @param task_name
	* @return 
	*/
	public boolean TaskExists(String task_name) {
		if (task_name != null && taskMap.containsKey(task_name))
			return true;
		return false;
	}

	/** Checking whether the list is not null and exists in the listMap
	* @param list_name
	* @return
	*/
	public boolean ListExists(String list_name) {
		if (list_name != null && listMap.containsKey(list_name))
			return true;
		return false;
	}

	/** Checking whether the user is not null and exists in the userMap
	* @param user_name
	* @return
	*/
	public boolean UserExists(String user_name) {
		if (user_name != null && userMap.containsKey(user_name))
			return true;
		return false;
	}

	/** 
	*formatting all the details of the task
	* @param taskname
	* @return task details
	*/
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
	
	/**
	* Printing users by sorting in accordance to their performance
	* @return
	*/
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

	/**
	* Printing users by sorting in accordance to their workload by calculating the total estimated time
	* @return
	*/
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
	
	/**
	* Printing all the unassigned tasks by sorting their priority
	* @return
	*/
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

	/**
	* Removes the particular task from the card
	* @return
	*/
	public String deleteTask(String taskname) {
		if (TaskExists(taskname)) {
			taskMap.remove(taskname);
			return "Success";
		}
		return "Error/Task does not exist!";
	}
	
	/**
	* Printing all the unfinished tasks by sorting the priority order
	* @return
	*/
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
	
	/**
	* Moving the task to another list
	* @param taskname
	* @param listname
	* @return
	*/
	public String moveTask(String taskname, String listname) {
		if (ListExists(listname) && TaskExists(taskname)) {
			taskMap.get(taskname).setList_name(listname);
			return "Success";
		}
		return "Error- list/task does not exist!";
	}
	
	/**
	* Printing the particular list with its tasks
	* @param listname
	* @return
	*/
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
	
	/**
	* Printing all the lists with their tasks
	* @return
	*/
	public String printAllLists() {
		StringBuilder sb = new StringBuilder();
		listMap.forEach((entry, val) -> {
			sb.append(val.toString() + "\n");
		});
		return sb.toString();
	}

	/** 
	* Returns the total estimated time of the given user
	* @param username
	* @return
	*/
	public Integer TotalEstTime(String username) {
		if (UserExists(username)) {
			Integer totalEst = taskMap.entrySet().stream()
					.filter((entry) -> entry.getValue().getAssigned_user().equalsIgnoreCase(username))
					.mapToInt((entry) -> entry.getValue().getEstimated_time()).sum();
			return totalEst;
		}
		return 0;
	}

	/** 
	* Returns the total time of completed task of the given user
	* @param username
	* @return
	*/
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

	/** 
	* Returns the remaining time of the given user
	* @param username
	* @return
	*/
	public Integer RemainingTime(String username) {
		if (UserExists(username)) {
			return TotalEstTime(username) - CompletedTime(username);
		}
		return 0;
	}

	/** 
	* Printing all the tasks assigned to the user
	* @param username
	* @return
	*/
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

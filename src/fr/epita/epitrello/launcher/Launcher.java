package fr.epita.epitrello.launcher;

import fr.epita.epitrello.datamodel.EpitrelloDataService;
import fr.epita.epitrello.filehandler.FileHandler;

public class Launcher {

	public static void main(String[] args) {

		EpitrelloDataService dataservice = new EpitrelloDataService();

		System.out.println(dataservice.addUser("Thomas")); // addUser(string username)
		System.out.println(dataservice.addUser("AmirAli"));
		System.out.println(dataservice.addUser("Rabih"));

		System.out.println(dataservice.addList("Code")); // addList(string name)
		System.out.println(dataservice.addList("Description"));
		System.out.println(dataservice.addList("Misc"));

		System.out.println(dataservice.addTask("Code", "Do Everything", 12, 1, "Write the whole code"));
		/*
		 * addTask(string list, string name, unsigned int estimatedTime, unsigned int
		 * priory, striting description)
		 */
		System.out.println(dataservice.editTask("Do Everything", 12, 10, "Write the whole code"));
		/*
		 * editTask(string task, unsigned int estimatedTime, unsigned int priority,
		 * string description)
		 */

		System.out.println(dataservice.assignTask("Do Everything", "Rabih")); // assignTask(string task, string user)
		System.out.println(dataservice.printTask("Do Everything")); // printTask(string task)

		System.out.println(dataservice.addTask("Code", "Destroy code formatting", 1, 2,
				"Rewrite the whole code in a worse format"));
		System.out.println(dataservice.assignTask("Destroy code formatting", "Thomas"));

		System.out.println(dataservice.addTask("Description", "Write Description", 3, 1, "Write the damn description"));
		System.out.println(dataservice.assignTask("Write Description", "AmirAli"));
		System.out.println(dataservice.addTask("Misc", "Upload Assignment", 1, 1, "Upload it"));

		System.out.println(dataservice.completeTask("Do Everything")); // completeTask(string task)
		System.out.println(dataservice.printUsersByPerformance());
		System.out.println(dataservice.printUsersByWorkload());

		System.out.println(dataservice.printUnassignedTasksByPriority());
		System.out.println(dataservice.deleteTask("Upload Assignment")); // deleteTask(string task)
		System.out.println(dataservice.printAllUnfinishedTasksByPriority());

		System.out.println(dataservice.addTask("Misc", "Have fun", 10, 2, "Just do it"));
		System.out.println(dataservice.moveTask("Have fun", "Code")); // moveTask(string task, string list)
		System.out.println(dataservice.printTask("Have fun"));

		System.out.println(dataservice.printList("Code")); // printList(string list)

		System.out.println(dataservice.printAllLists());

		System.out.println(dataservice.printUserTasks("AmirAli")); // printUserTasks(string user)

		System.out.println(dataservice.printUnassignedTasksByPriority());

		System.out.println(dataservice.printAllUnfinishedTasksByPriority());

		// TODO Write all the prints into a file.

		FileHandler.writeToFile(dataservice.printTask("Do Everything"));
		FileHandler.writeToFile(dataservice.printUsersByPerformance());
		FileHandler.writeToFile(dataservice.printUsersByWorkload());
		FileHandler.writeToFile(dataservice.printUnassignedTasksByPriority());
		FileHandler.writeToFile(dataservice.printAllUnfinishedTasksByPriority());
		FileHandler.writeToFile(dataservice.printTask("Have fun"));
		FileHandler.writeToFile(dataservice.printList("Code"));
		FileHandler.writeToFile(dataservice.printAllLists());
		FileHandler.writeToFile(dataservice.printUserTasks("AmirAli"));
		FileHandler.writeToFile(dataservice.printUnassignedTasksByPriority());
		FileHandler.writeToFile(dataservice.printAllUnfinishedTasksByPriority());

		// TODO Save users in a db.
	}

}

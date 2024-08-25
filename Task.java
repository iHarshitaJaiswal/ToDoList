package ToDoList;
import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Task {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		boolean flag=true;
		while(flag) {
			System.out.print("\nTO-DO list-\n1.Add a Task.\n2.Update a Task.\n3.Delete a Task\n4.Show all Tasks.\n5.Exit.\nChoose an option:");
			int ch = sc.nextInt();


			switch(ch)
			{
			case 1:
				TaskManager.addTask();	
				break;
			case 2:
				TaskManager.updateTask();
				break;
			case 3:
				TaskManager.deleteTask();
				break;
			case 4:
				TaskManager.displayTasks();
				break;
			case 5:
				System.out.println("Program Exited.");
				flag=false;
				break;
			default: 
				System.out.println("Wrong Choice Entered!");
			}
		}
		sc.close();
	}

}
class TaskManager
{
	private static final String FileName = "demo.txt";
	static Scanner sc = new Scanner(System.in);
	public static  final DateTimeFormatter Format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static void addTask() {
		System.out.print("Enter the task title: ");
		String title = sc.nextLine();

		System.out.print("Enter the task description: ");
		String description = sc.nextLine();

		// LocalDate date = null;
		System.out.print("Enter the task deadline date (YYYY-MM-DD): ");
		String dateInput = sc.nextLine();
		LocalDate date;
	        try {
	            date = LocalDate.parse(dateInput, Format);
	        } catch (Exception e) {
	            System.out.println("Invalid date format. Please use yyyy-mm-dd.");
	            return;
	        }

		System.out.print("Enter the task priority (e.g., Low, Medium, High): ");
		String priority = sc.nextLine();

		System.out.print("Enter the task Status (Ongoing/Done): ");
		String status = sc.nextLine();

		String taskEntry = String.format("%s | %s | %s | %s | %s", title, description, dateInput, priority, status);

		try (FileWriter myWriter = new FileWriter(FileName, true);
				BufferedWriter bw = new BufferedWriter(myWriter)){
			bw.write(taskEntry);
			bw.newLine();
			bw.close();
			System.out.println("Task added: " + taskEntry);
		} catch (IOException e) {
			System.err.println("Error writing to file: " + e.getMessage());
		}


	}

	public static void displayTasks() {
		List<String> tasks = readTasks();
		if (tasks.isEmpty()) {
			System.out.println("No tasks to display.");
			return;
		}

		System.out.println("Current tasks:");
		for (int i = 0; i < tasks.size(); i++) {
			String[] parts = tasks.get(i).split("\\|");
			if (parts.length == 5) {
				System.out.printf("%d. Title: %s, Description: %s, Priority: %s, Due Date: %s, Status: %s%n",
						(i + 1), parts[0], parts[1], parts[2], parts[3], parts[4]);
			}
		}
	}


	public static void deleteTask() {
		List<String> tasks= readTasks();
		if (tasks.isEmpty()) {
			System.out.println("No tasks found.");
			return;
		}

		System.out.println("Current tasks:");
		for (int i = 0; i < tasks.size(); i++) {
			System.out.println((i + 1) + ". " + tasks.get(i));
		}

		System.out.print("Enter the task number to delete: ");
		int taskNumber = sc.nextInt();
		//sc.nextLine(); 

		if (taskNumber < 1 || taskNumber > tasks.size()) {
			System.out.println("Invalid task number.");
			return;
		}

		tasks.remove(taskNumber - 1);
		writeTasks(tasks);
		System.out.println("Task deleted successfully.");
	}


	public static void updateTask() {
		List<String> tasks= readTasks();
		if (tasks.isEmpty()) {
			System.out.println("No tasks found.");
			return;
		}

		System.out.println("Current tasks:");
		for (int i = 0; i < tasks.size(); i++) {
			System.out.println((i + 1) + ". " + tasks.get(i));
		}

		System.out.print("Enter the task number to update: ");
		int taskNumber = sc.nextInt();
		//sc.nextLine(); 

		if (taskNumber < 1 || taskNumber > tasks.size()) {
			System.out.println("Invalid task number.");
			return;
		}

		System.out.print("Enter the task title: ");
		String title = sc.nextLine();

		System.out.print("Enter the task description: ");
		String description = sc.nextLine();

		
		System.out.print("Enter the task date (YYYY-MM-DD): ");
		String dateInput = sc.nextLine();
		LocalDate date;
        try {
            date = LocalDate.parse(dateInput, Format);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use yyyy-mm-dd.");
            return;
        }

		System.out.print("Enter the task priority (e.g., Low, Medium, High): ");
		String priority = sc.nextLine();

		System.out.print("Enter the task Status (Ongoing/Done): ");
		String status = sc.nextLine();

		String updated = String.format("%s | %s | %s | %s | %s", title, description, dateInput, priority, status);
		tasks.set(taskNumber - 1, updated);
		writeTasks(tasks);
		System.out.println("Task updated successfully.");

	}
	private static List<String> readTasks() {
		List<String> tasks = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(FileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				tasks.add(line);
			}
		}catch (IOException e) {
			System.err.println("Error writing to file: " + e.getMessage());
		}
		return tasks;
	}

	private static void writeTasks(List<String> tasks) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FileName))) {
			for (String task : tasks) {
				bw.write(task);
				bw.newLine();
			}
		} catch (IOException e) {
			System.err.println("Error writing to file: " + e.getMessage());
		}
	}

}


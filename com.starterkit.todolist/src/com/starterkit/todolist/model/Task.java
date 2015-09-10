package com.starterkit.todolist.model;


public class Task extends ModelObject{
	private static Long lastID = 0L;
	private Long taskId;
	private String taskName;
	private String description;
		
	public Task(String taskName, String description) {
		setDescription(description);
		setTaskName(taskName);
		setTaskId(Task.lastID++);
	}
	
	public String getTaskName() {
		return taskName;
	}
	
	public void setTaskName(String taskName) {
		firePropertyChange("taskName", this.taskName, this.taskName = taskName);
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		firePropertyChange("description", this.description, this.description = description);
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		firePropertyChange("taskId", this.taskId, this.taskId = taskId);
	}

	public static Long getLastID() {
		return lastID;
	}
	
	@Override
	public String toString() {
		String header = "Details of task \"" + this.taskName + " \" (task id: " + this.taskId + ")";
		return header + "\n\n" + description;
	}
}

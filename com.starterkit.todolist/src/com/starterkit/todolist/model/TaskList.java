package com.starterkit.todolist.model;

import java.util.ArrayList;
import java.util.List;

public class TaskList extends ModelObject {
	
	private List<Task> taskList = new ArrayList<>();
	private Integer size = new Integer(0);
	
	public void addTaskToList(Task task) {
		taskList.add(task);
		setSize(getSize() + 1);
	}
	
	public boolean removeTaskFromList(Long id){
		Task task = findTaskById(id);
		if (task == null) return false;
		taskList.remove(task);
		setSize(getSize() - 1);
		return true;
	}
	
	private Task findTaskById(Long id){
		for (Task task : taskList) {
			if (task.getTaskId() == id)
				return task;
		}
		return null;
	}
	
	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		firePropertyChange("taskList", this.taskList, this.taskList = taskList);
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		firePropertyChange("size", this.size, this.size = size);
	}
}

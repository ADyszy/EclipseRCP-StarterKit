package com.starterkit.todolist.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public enum ModelProvider {
	INSTANCE;
	
	private List<Task> taskList;
	private List<Task> archive;
	
	private ModelProvider() {
		taskList = new ArrayList<Task>();
		archive = new ArrayList<Task>();
		showExample(true);
	}

	public List<Task> getTasks() {
		return taskList;
	}
	
	public Task findByIdInArchive(Long id) {
		for (Task task : archive)
			if(task.getTaskId() == id)
				return task;
		return null;
	}
	
	public Task findByIdInTaskList(Long id) {
		for (Task task : taskList)
			if(task.getTaskId() == id)
				return task;
		return null;
	}
	
	public List<Task> getArchive() {
		return archive;
	} 
	
	public void addTask(Task task) {
		taskList.add(task);
	}

	public void deleteTask(Long id) {
		for (Task task : taskList)
			if(task.getTaskId() == id)
				taskList.remove(task);
				return;
	}
	
	public void archivize(Long id){
		for (Task task : taskList)
			if(task.getTaskId() == id){
				archive.add(task);
				taskList.remove(task);
				return;
			}
	}
	
	private void showExample(boolean bool) {
		if(!bool)return;
		taskList.addAll(Arrays.asList(new Task("Go to shop", "Buy some fruits and sth to drink.")
		, new Task("Call ur mom", "Say her hello.")));
	}
	
}

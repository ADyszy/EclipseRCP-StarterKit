package com.starterkit.todolist.views;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;

import com.starterkit.todolist.model.Task;


import com.starterkit.todolist.model.TaskList;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;


public class ToDoListCreator extends ViewPart {
	
	private Task task;
	TaskList taskList = new TaskList();
	
	// view properties
	public static final String ID = "com.starterkit.todolist.views.ToDoList";
	private Text taskDescription;
	private Text taskName;
	private Button addTaskButton;
	private Label todoNameLabel;
	private Label crateATodoLabel;

	public ToDoListCreator() {
	}

	@Override
	public void createPartControl(Composite parent) {
		task = new Task("", "");
		viewElementsInit(parent);
		observablesInit();
		bindingInit();
	}

	@Override
	public void setFocus() {
	}
	
	// Bindings
	DataBindingContext dbc;
	private void bindingInit(){
		dbc = new DataBindingContext();
		dbc.bindValue(taskNameModel, taskNameObservable);
		dbc.bindValue(taskDescriptionObservable, taskDescriptionModel);
	}
	
	// Observables
	private IObservableValue taskDescriptionModel;
	private IObservableValue taskNameModel;
	private ISWTObservableValue taskNameObservable;
	private ISWTObservableValue taskDescriptionObservable;
	private void observablesInit() {
		taskDescriptionModel = PojoProperties.value("description").observe(task);
		taskNameModel = PojoProperties.value("taskName").observe(task);
		taskNameObservable = WidgetProperties.text(SWT.Modify)
				.observe(taskName);
		taskDescriptionObservable = WidgetProperties.text(
				SWT.Modify).observe(taskDescription);
	}
	
	// view build..
	private void viewElementsInit(Composite parent) {
		parent.setLayout(null);
		
		taskDescription = new Text(parent, SWT.BORDER | SWT.WRAP);
		taskDescription.setBounds(10, 137, 187, 255);
		taskName = new Text(parent, SWT.BORDER);
		taskName.setBounds(10, 79, 187, 26);
		buttonsInit(parent);
		labelsInit(parent);
		textVerifiedButtonActivator(addTaskButton, taskName);
	}
	
	private void textVerifiedButtonActivator(final Button button, final Text text) {
		taskName.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				buttonBlocker(button, text);
			}
		});
		taskName.addModifyListener(new ModifyListener() {	
			@Override
			public void modifyText(ModifyEvent e) {
				buttonBlocker(button, text);
			}
		});
	}
	
	private boolean verifyMyText(Text text) {
		if(text == null) return false;
		String textS = text.getText();
		if(textS.isEmpty()) return false;
		if(textS.split(" ").length == 0) return false;
		return true;
	}
	
	private void buttonBlocker(Button button, Text text) {
		if(!verifyMyText(text)){
			addTaskButton.setEnabled(false);
			return;
		}
		addTaskButton.setEnabled(true);
	}
	
	
	private void buttonsInit(Composite parent){
		addTaskButton = new Button(parent, SWT.NONE);
		addTaskButton.setBounds(107, 398, 90, 30);
		addTaskButton.setText("Add");
		addTaskButton.setEnabled(false);
		buttonListenersInit();
	}
	
	// buttons control.
	private void buttonListenersInit() {
		addTaskToListButtonSelectionListener(addTaskButton);
	}
	
	private void addTaskToListButtonSelectionListener(Button button) {
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (task.getTaskName() == null || task.getTaskName().isEmpty()) return;
				Task newTask = new Task(task.getTaskName(), task.getDescription());
				taskList.addTaskToList(newTask);
				ToDoList.addNewTask(newTask);
			}
		});
	}
	
	
	private void labelsInit(Composite parent) {
		todoNameLabel = new Label(parent, SWT.NONE);
		todoNameLabel.setBounds(10, 53, 187, 20);
		todoNameLabel.setText("TODO name:");

		crateATodoLabel = new Label(parent, SWT.NONE);
		crateATodoLabel.setFont(SWTResourceManager.getFont("Verdana", 11,
				SWT.BOLD | SWT.ITALIC));
		crateATodoLabel.setBounds(10, 20, 213, 27);
		crateATodoLabel.setText("Create a TODO here:");
	}
}

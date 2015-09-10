package com.starterkit.todolist.views;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.starterkit.todolist.model.ModelProvider;
import com.starterkit.todolist.model.Task;

import org.eclipse.swt.widgets.Table;

public class ToDoList extends ViewPart {
	
	private static WritableList writableTaskList;
	
	private Table undoneTasksTable;
	private Button setSelectedTaskDoneButton;
	private Button deleteSelectedTaskButton;
	private Button detailsButton;
	private Label yourTodosLabel;
	private Label todoListLabel;
	private org.eclipse.swt.widgets.TableColumn undoneTaskColumn;

	public ToDoList() {
	}

	@Override
	public void createPartControl(Composite parent) {
		viewElementsInit(parent);
		createBindings();
		
		MenuManager menuManager = new MenuManager();
		Menu menu = menuManager.createContextMenu(taskViewer.getTable());
		taskViewer.getTable().setMenu(menu);
		getSite().registerContextMenu(menuManager, taskViewer);
		getSite().setSelectionProvider(taskViewer);
	}

	// bindings
	TableViewer taskViewer;
	private void createBindings() {
		taskViewer = new TableViewer(undoneTasksTable);
		writableTaskList = new WritableList(ModelProvider.INSTANCE.getTasks(),
				Task.class);
		ViewerSupport.bind(taskViewer, writableTaskList,
				BeanProperties.values(new String[] { "taskName" }));
	}
	
	// inits
	private void viewElementsInit(Composite parent) {
		parent.setLayout(null);
		buttonsAppearanceInit(parent);
		buttonsListenersInit();
		labelsDesignInit(parent);
		tableInit(parent);
	}
	
	private void tableInit(Composite parent) {
		undoneTasksTable = new Table(parent, SWT.BORDER | SWT.FULL_SELECTION);
		undoneTasksTable.setBounds(10, 93, 194, 225);
		undoneTasksTable.setHeaderVisible(true);
		undoneTasksTable.setLinesVisible(false);
		columnsInit(undoneTasksTable);
	}
	
	private void columnsInit(Table table){
		undoneTaskColumn = new org.eclipse.swt.widgets.TableColumn(
				table, SWT.NONE);
		undoneTaskColumn.setWidth(146);
		undoneTaskColumn.setText("Ur ToDos:");
	}
	
	private void labelsDesignInit(Composite parent) {
		yourTodosLabel = new Label(parent, SWT.NONE);
		yourTodosLabel.setFont(SWTResourceManager.getFont("Verdana", 12,
				SWT.BOLD | SWT.ITALIC));
		yourTodosLabel.setBounds(10, 22, 162, 30);
		yourTodosLabel.setText("Your TODOs:");

		todoListLabel = new Label(parent, SWT.NONE);
		todoListLabel.setBounds(10, 67, 70, 20);
		todoListLabel.setText("TODO list:");
	}

	private void buttonsAppearanceInit(Composite parent) {
		setSelectedTaskDoneButton = new Button(parent, SWT.NONE);
		setSelectedTaskDoneButton.setBounds(10, 337, 194, 30);
		setSelectedTaskDoneButton.setText("Set selected as done");

		deleteSelectedTaskButton = new Button(parent, SWT.NONE);
		deleteSelectedTaskButton.setBounds(10, 410, 194, 30);
		deleteSelectedTaskButton.setText("Delete selected");
		
		detailsButton = new Button(parent, SWT.NONE);
		detailsButton.setBounds(10, 373, 194, 30);
		detailsButton.setText("Details");
	}

	private void buttonsListenersInit() {
		addSetTaskDoneListener(setSelectedTaskDoneButton);
		addShowDetailsPopupListener(detailsButton);
		addDeleteTaskListener(deleteSelectedTaskButton);
	}

	
	// button listeners
	private void addSetTaskDoneListener(Button button) {
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Integer index = undoneTasksTable.getSelectionIndex();
				if (index < 0 || index == null) return;
				archivize(index);
				removeTask(index);
			}
		});
	}
	
	private void addShowDetailsPopupListener(Button button) {
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell activeShell = Display.getDefault().getActiveShell();
				int index = undoneTasksTable.getSelectionIndex();
				if (index < 0) return;
				Task task = ((Task) undoneTasksTable.getItem(index).getData());
				MessageDialog.openInformation(activeShell, "Task details", task.toString());
			}
		});
	}
	
	private void addDeleteTaskListener(Button button) {
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = undoneTasksTable.getSelectionIndex();
				if (index < 0)return;
				removeTask(index);
			}
		});
	}
	
	// operations
	private void archivize(int index){
		Long id = getTaskByIndex(index).getTaskId();
		String name = getTaskByIndex(index).getTaskName();
		Archived.addTaskToArchived(name, String.valueOf(id));
		ModelProvider.INSTANCE.archivize(id);
	}
	
	private void removeTask(int index){
		undoneTasksTable.remove(index);
	}
	
	private Task getTaskByIndex(int index) {
		return ((Task) undoneTasksTable.getItem(index).getData());
	}
	
	public static void addNewTask(Task newTask) {
		writableTaskList.add(newTask);
	}
	
		
	@Override
	public void setFocus() {
		taskViewer.getControl().setFocus();
	}
}

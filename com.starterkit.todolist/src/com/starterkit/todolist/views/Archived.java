package com.starterkit.todolist.views;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.List;

import com.starterkit.todolist.model.ModelProvider;
import com.starterkit.todolist.model.Task;


public class Archived extends ViewPart {

	private static List archived;
	private static List archivedIds;
	
	private Button deleteSelectedTaskButton;
	private Button detailsButton;
	private Label yourTodosLabel;
	private Label todoListLabel;
	
	
	public Archived() {
	}
	
	@Override
	public void createPartControl(Composite parent) {
		viewElementsInit(parent);		
		buttonsAppearanceInit(parent);
		buttonsListenersInit();
	}
	
	
	// inits
	private void viewElementsInit(Composite parent) {
		parent.setLayout(null);
		buttonsAppearanceInit(parent);
		buttonsListenersInit();
		labelsInit(parent);
		listsInit(parent);
	}
	
	private void listsInit(Composite parent) {
		archived = new List(parent, SWT.BORDER);
		archived.setBounds(10, 94, 194, 291);
		archivedIds = new List(parent, SWT.BORDER);
	}
	
	private void labelsInit(Composite parent) {
		yourTodosLabel = new Label(parent, SWT.NONE);
		yourTodosLabel.setFont(SWTResourceManager.getFont("Verdana", 12, SWT.BOLD
				| SWT.ITALIC));
		yourTodosLabel.setBounds(10, 22, 213, 30);
		yourTodosLabel.setText("Your TODOs done:");

		todoListLabel = new Label(parent, SWT.NONE);
		todoListLabel.setBounds(10, 67, 70, 20);
		todoListLabel.setText("TODO list:");
	}
	
	private void buttonsAppearanceInit(Composite parent) {		
		deleteSelectedTaskButton = new Button(parent, SWT.NONE);
		deleteSelectedTaskButton.setBounds(10, 391, 194, 30);
		deleteSelectedTaskButton.setText("Delete selected");
		
		detailsButton = new Button(parent, SWT.NONE);
		detailsButton.setBounds(10, 429, 194, 30);
		detailsButton.setText("Details");
	}
	
	private void buttonsListenersInit() {
		addDeleteTaskDoneListener(deleteSelectedTaskButton);
		addShowDetailsPopupListener(detailsButton);
	}
	
	
	// button listeners
	private void addDeleteTaskDoneListener(Button button) {
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				removeSelectedTaskFromArchive();
			}
		});
	}
	
	private void addShowDetailsPopupListener(Button button) {
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell activeShell = Display.getDefault().getActiveShell();
				int index = archived.getSelectionIndex();
				if (index < 0) return;
				Long id = Long.valueOf(archivedIds.getItem(index));
				Task task = ModelProvider.INSTANCE.findByIdInArchive(id);
				MessageDialog.openInformation(activeShell, "Task details", task.toString());
			}
		});
	}
	
	// operations
	public static void addTaskToArchived(String taskName, String id) {
		archived.add(new String(taskName));
		archivedIds.add(id);
	}
	
	public static void removeSelectedTaskFromArchive() {
		int index = archived.getSelectionIndex();
		if (index < 0) return;
		archived.remove(index);
		archivedIds.remove(index);
	}
	
	@Override
	public void setFocus() {
	}
}

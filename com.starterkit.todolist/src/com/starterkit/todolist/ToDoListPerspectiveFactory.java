package com.starterkit.todolist;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class ToDoListPerspectiveFactory implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.setFixed(true);
		layout.setEditorAreaVisible(false);
		addFastViews(layout);
		// TODO Auto-generated method stub

		layout.addView("com.starterkit.todolist.views.ToDoListCreator", IPageLayout.LEFT, 1f, IPageLayout.ID_EDITOR_AREA);
		layout.addView("com.starterkit.todolist.views.ToDoList", IPageLayout.LEFT, 1f, IPageLayout.ID_EDITOR_AREA);
		layout.addView("com.starterkit.todolist.views.Archived", IPageLayout.LEFT, 1f, IPageLayout.ID_EDITOR_AREA);
	}

	private void addFastViews(IPageLayout layout) {
	}
}

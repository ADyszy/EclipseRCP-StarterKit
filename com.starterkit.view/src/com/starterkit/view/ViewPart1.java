package com.starterkit.view;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;

import com.starterkit.view.model.Person;

public class ViewPart1 extends ViewPart {
	private Text text;
	private Text text_1;

	public ViewPart1() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(3, false));
		
		Label lblFirstName = new Label(parent, SWT.NONE);
		lblFirstName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFirstName.setText("first name");
		
		text = new Text(parent, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnButton = new Button(parent, SWT.NONE);
		btnButton.setText("button");
		
		Label lblLastName = new Label(parent, SWT.NONE);
		lblLastName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLastName.setText("last name");
		
		text_1 = new Text(parent, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(parent, SWT.NONE);
		
		btnButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				text_1.setText(text.getText());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		// TODO Auto-generated method stub
		
		Person p = new Person();
		
		//create new context...
		DataBindingContext ctx = new DataBindingContext();
		
		//def Obserwables
		// powiazanie kontrolek z obiektem.
		IObservableValue target = WidgetProperties.text(SWT.Modify).observe(text);
		IObservableValue model = BeanProperties.value(Person.class, "firstName").observe(p);
		
		ctx.bindValue(target, model);
		
		IObservableValue target2 = WidgetProperties.text(SWT.Modify).observe(text_1);
		ctx.bindValue(target2, model);
		
	}
	
	
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}

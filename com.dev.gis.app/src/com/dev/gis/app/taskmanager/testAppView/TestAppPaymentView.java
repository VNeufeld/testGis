package com.dev.gis.app.taskmanager.testAppView;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.IBeanObservable;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.taskmanager.TaskViewAbstract;
import com.dev.gis.connector.joi.protocol.CreditCard;
import com.dev.gis.connector.joi.protocol.Payment;
import com.dev.gis.task.execution.api.IEditableTask;
import com.dev.gis.task.execution.api.ITaskResult;

public class TestAppPaymentView extends TaskViewAbstract {
	public static final String ID = IEditableTask.ID_TestAppPaymentView;

	private Text creditCardNr1, creditCardNr2, creditCardNr3, creditCardNr4;
	private Text creditCardValidBisMonth;
	private Text creditCardValidBisYear;
	private Text creditCardOwner;

//	private DataBindingContext dbc;
	
	@Override
	public void createPartControl(Composite parent) {
		
//		dbc = new DataBindingContext();
		
		ScrolledComposite sc=new ScrolledComposite(parent,  SWT.H_SCROLL | SWT.V_SCROLL );
		sc.setAlwaysShowScrollBars(false);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		
		
		Composite composite = new Composite(sc, SWT.BORDER);
		composite.setLayout(new GridLayout(1, false));
	
		sc.setContent(composite);
		
		final Group groupCustomer = new Group(composite, SWT.TITLE);
		groupCustomer.setText("Customer:");
		groupCustomer.setLayout(new GridLayout(4, true));
		groupCustomer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		new Label(groupCustomer, SWT.NONE).setText("Name: ");
		
		Text name = new Text(groupCustomer, SWT.BORDER | SWT.SINGLE);
		//name.setLayoutData(gridData);
		//name.setTextLimit(4);

		new Label(groupCustomer, SWT.NONE).setText("Vorname: ");
		Text firstName = new Text(groupCustomer, SWT.BORDER | SWT.SINGLE);
		//name.setLayoutData(gridData);
		new Label(groupCustomer, SWT.NONE).setText("Adresse: ");


		final Group groupDriver = new Group(composite, SWT.TITLE);
		groupDriver.setText("Driver:");
		groupDriver.setLayout(new GridLayout(4, true));
		groupDriver.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		new Label(groupDriver, SWT.NONE).setText("Name: ");
		
		Text driverName = new Text(groupDriver, SWT.BORDER | SWT.SINGLE);
		//name.setLayoutData(gridData);
		//name.setTextLimit(4);

		new Label(groupDriver, SWT.NONE).setText("Vorname: ");
		Text firstNameDriver = new Text(groupDriver, SWT.BORDER | SWT.SINGLE);
		//name.setLayoutData(gridData);
		
		
		final Group groupPayment = new Group(composite, SWT.TITLE);
		groupPayment.setText("Payment:");
		groupPayment.setLayout(new GridLayout(3, false));
		groupPayment.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		
		
		
		final Button[] radioButtons = new Button[3];
		radioButtons[0] = new Button(groupPayment, SWT.RADIO);
		radioButtons[0].setSelection(true);
		radioButtons[0].setText("Credit Card");
		
		
	 
		radioButtons[1] = new Button(groupPayment, SWT.RADIO);
		radioButtons[1].setText("Bank Account");
	 
		radioButtons[2] = new Button(groupPayment, SWT.RADIO);
		radioButtons[2].setText("Paypal");
	 
		final Composite paymentTypeComposite=new Composite(groupPayment, SWT.NONE);
		GridDataFactory.fillDefaults().span(3, 1).align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(paymentTypeComposite);
		final StackLayout sl=new StackLayout();
		paymentTypeComposite.setLayout(sl);
		
		final Composite creditCardGroup = createCreditCardGroup(paymentTypeComposite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).span(3, 1).applyTo(creditCardGroup);
		
		final Composite bankAccountGroup = createBankAccountGroup(paymentTypeComposite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).span(3, 1).applyTo(bankAccountGroup);
		
		final Composite paypalGroup = createPayPalGroup(paymentTypeComposite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).span(3, 1).applyTo(paypalGroup);
		
		sl.topControl=creditCardGroup;
		
		radioButtons[0].addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				sl.topControl=creditCardGroup;
				paymentTypeComposite.layout();
			}
			
		});
		radioButtons[1].addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				sl.topControl=bankAccountGroup;
				paymentTypeComposite.layout();
			}
			
		});
		radioButtons[2].addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				sl.topControl=paypalGroup;
				paymentTypeComposite.layout();
			}
			
		});
		
		Payment p = new Payment();
		CreditCard cc = new CreditCard();
		cc.setCardNumber(createCardNum());
		cc.setCardMonth(creditCardValidBisMonth.getText());
		cc.setCardYear(creditCardValidBisYear.getText());
		cc.setOwnerName(creditCardOwner.getText());
		p.setCard(cc);
		
		Composite emptyComposite=new Composite(composite, SWT.NONE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).hint(1, 1).grab(true, true).applyTo(emptyComposite);
		
		final Button buttonOK = new Button(composite, SWT.PUSH | SWT.LEFT);
		buttonOK.setText("Save");
		buttonOK.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
//				dbc.updateModels();
			}
			
		});
		GridDataFactory.fillDefaults().align(SWT.END,SWT.END).grab(false, false).applyTo(buttonOK);
		
		
		sc.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}


	private Composite createPayPalGroup(Composite groupPayment) {
		final Group group = new Group(groupPayment, SWT.TITLE);
		group.setText("PayPal Card");
		group.setLayout(new GridLayout(2, false));
		return group;
	}


	private Composite createBankAccountGroup(Composite groupPayment) {
		final Group group = new Group(groupPayment, SWT.TITLE);
		group.setText("Bank Account");
		group.setLayout(new GridLayout(2, false));
		return group;
	}


	private Composite createCreditCardGroup(final Composite groupPayment) {
		final Group groupCC = new Group(groupPayment, SWT.TITLE);
		groupCC.setText("Credit Card");
		groupCC.setLayout(new GridLayout(2, false));
		
		//groupCC.setEnabled(false);
		

		
		new Label(groupCC, SWT.NONE).setText("Credit Card Nr: ");
		
		Composite creditNumberComposite=new Composite(groupCC, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(4).equalWidth(true).applyTo(creditNumberComposite);
		
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER).grab(false,false).applyTo(creditNumberComposite);
		
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		//gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 1;
		gridData.widthHint = 30;
		
		creditCardNr1 = new Text(creditNumberComposite, SWT.BORDER | SWT.SINGLE);
		creditCardNr1.setLayoutData(gridData);
		creditCardNr1.setTextLimit(4);
//		creditCardNr1.addModifyListener(new ModifyListener() {
//			
//			@Override
//			public void modifyText(ModifyEvent arg0) {
//				System.out.println(arg0);
//				System.out.println(creditCardNr1.getText());
//				creditCardNr1.setText(creditCardNr1.getText()+"-");
//			}
//		});
//		creditCardNr1.addVerifyListener(new VerifyListener() {
//			
//			@Override
//			public void verifyText(VerifyEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		ISWTObservableValue observeText = SWTObservables.observeText(creditCardNr1, SWT.Modify);
//		CreditCard cc=null;
//		IObservableValue modelValue=BeanProperties.value(CreditCard.class, "cardNumber").observe(cc) ;
//		dbc.bindValue(observeText, modelValue, new UpdateValueStrategy(UpdateValueStrategy.POLICY_ON_REQUEST), new UpdateValueStrategy(UpdateValueStrategy.POLICY_UPDATE));

		creditCardNr2 = new Text(creditNumberComposite, SWT.BORDER | SWT.SINGLE);
		creditCardNr2.setLayoutData(gridData);
		creditCardNr2.setTextLimit(4);
		
		creditCardNr3 = new Text(creditNumberComposite, SWT.BORDER | SWT.SINGLE);
		creditCardNr3.setLayoutData(gridData);
		creditCardNr3.setTextLimit(4);
		
		creditCardNr4 = new Text(creditNumberComposite, SWT.BORDER | SWT.SINGLE);
		creditCardNr4.setLayoutData(gridData);
		creditCardNr4.setTextLimit(4);
		
		GridData gdFirm = new GridData();
		gdFirm.grabExcessHorizontalSpace = true;
		gdFirm.horizontalAlignment = SWT.FILL;
		gdFirm.widthHint = 30;

//		GridData gdFirm2 = new GridData();
//		gdFirm2.grabExcessHorizontalSpace = true;
//		gdFirm2.horizontalAlignment = SWT.FILL;
//		gdFirm2.widthHint = 30;
//        gridData.horizontalSpan = 3;

		new Label(groupCC, SWT.NONE).setText("Valid To ( MM/YYYY) :");
		
		Composite validDateComposite=new Composite(groupCC, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(true).applyTo(validDateComposite);
		
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER).grab(false,false).applyTo(validDateComposite);
		
		creditCardValidBisMonth = new Text(validDateComposite, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING).grab(false, false).applyTo(creditCardValidBisMonth);
//		creditCardValidBisMonth.setLayoutData(gdFirm);
		creditCardValidBisMonth.setTextLimit(2);

		creditCardValidBisYear = new Text(validDateComposite, SWT.BORDER | SWT.SINGLE);
//		creditCardValidBisYear.setLayoutData(gdFirm);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING).grab(false, false).applyTo(creditCardValidBisMonth);
		creditCardValidBisYear.setTextLimit(4);
		
		GridData gdOwner = new GridData();
		gdOwner.grabExcessHorizontalSpace = true;
		gdOwner.horizontalAlignment = SWT.FILL;
		
		new Label(groupCC, SWT.NONE).setText("Owner :");
		
		creditCardOwner = new Text(groupCC, SWT.BORDER | SWT.SINGLE);
		creditCardOwner.setLayoutData(gdOwner);
		creditCardOwner.setTextLimit(30);
		
		return groupCC;
	}

	
	private String createCardNum() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(ITaskResult result) {
		// TODO Auto-generated method stub
		
	}



	
}

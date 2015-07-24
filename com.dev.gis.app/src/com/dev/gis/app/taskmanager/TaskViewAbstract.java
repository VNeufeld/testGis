package com.dev.gis.app.taskmanager;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.ui.part.ViewPart;

import com.dev.gis.app.taskmanager.SunnyCarsView.SunnyOfferViewUpdater;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.VehicleHttpService;
import com.dev.gis.connector.sunny.VehicleRequest;
import com.dev.gis.connector.sunny.VehicleResponse;
import com.dev.gis.task.execution.api.ITaskResult;

public abstract class TaskViewAbstract extends ViewPart {


	protected class AbstractListener implements SelectionListener {

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			widgetSelected(arg0);

		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			// TODO Auto-generated method stub
			
		}

	}
	
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	public void refresh(ITaskResult result) {
		// TODO Auto-generated method stub
		
	}
	
	
	public class GetOfferOperation implements IRunnableWithProgress {
		  
		  private final VehicleRequest request;
		  private final int pageSize;
		  
		  private VehicleResponse response;
		  

		  /**
		   * LongRunningOperation constructor
		   * 
		   * @param indeterminate whether the animation is unknown
		   */
		  public GetOfferOperation(VehicleRequest request, int pageSize) {
		    this.request = request;
		    this.pageSize = pageSize;
		  }

		@Override
		public void run(IProgressMonitor monitor)
				throws InvocationTargetException, InterruptedException {
			
			new SunnyOfferViewUpdater().clearView();

			
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			VehicleHttpService service = serviceFactory
					.getVehicleJoiService();

			monitor.setTaskName("create Vehicle...");
			monitor.beginTask(" running HSGW GetCars ", 15000);
			
			ShowTimer showTimer = new ShowTimer(monitor);
			new Thread(showTimer).start();
			
			this.response = service.getOffers(request, false, pageSize);
			showTimer.setWork(false);
			monitor.done();
			
			
		}

		public VehicleResponse getResponse() {
			return response;
		}
	}
	
	public class ShowTimer implements Runnable {
		final IProgressMonitor monitor;
		private boolean work = true;
		
		ShowTimer(IProgressMonitor monitor) {
			this.monitor = monitor;
		}

		@Override
		public void run() {
			while ( work ) {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			    monitor.worked(300);
			}
		}

		public void setWork(boolean work) {
			this.work = work;
		}
		
	}

}

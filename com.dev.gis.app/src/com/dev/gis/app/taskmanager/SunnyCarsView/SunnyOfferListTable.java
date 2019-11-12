package com.dev.gis.app.taskmanager.SunnyCarsView;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPartSite;

import com.dev.gis.app.view.elements.AbstractListTable;
import com.dev.gis.app.view.listener.SunnyTooltipListener;
import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.api.SunnyOfferDo;

public class SunnyOfferListTable extends AbstractListTable {
	
	
	
	public SunnyOfferListTable(IWorkbenchPartSite site,final Composite parent, 
			IDoubleClickListener selectOfferListener, 
			ISelectionChangedListener selectionChangedListener  ) {
		
		super(site, parent, selectOfferListener, selectionChangedListener);
		
	}

	@Override
	public void createColumns(Composite parent, TableViewer viewer) {
		String[] titles = { "BodyStyle", "Name", "Group", "Supplier", "Station",
				"Service Catalog", "Price", "Status",  "Rating", "OneWay Fee", "OneWay Fee in Sell Currency", "Incl. km." };
		int[] bounds = { 200,250, 100, 80, 80, 50, 120,50, 50, 120, 120, 100 };

		// first column is for the first name
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				return o.getVehicle().getBodyStyleText();
			}
		});

		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				return o.getCarDescription();
			}
		});

		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				return o.getGroup();
			}
		});

		// second column is supplierId
		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				return String.valueOf(o.getSupplierId());
			}
		});

		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				String value = String.valueOf(o.getPickUpStationId());
				if ( o.getDropOffStationId() != o.getPickUpStationId()) {
					value = value + " / " + String.valueOf(o.getDropOffStationId());
				}
				return value;
			}
		});

		col = createTableViewerColumn(titles[5], bounds[5], 5);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				return String.valueOf(o.getServiceCatalogId());
			}
		});

		col = createTableViewerColumn(titles[6], bounds[6], 6);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				String price = "";
				if (o.getPrice() != null) {
					price = o.getPrice().getAmount();
					price = price + " " + o.getPrice().getCurrency();
				}
				return price;
			}

		});

		col = createTableViewerColumn(titles[7], bounds[7], 7);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				return o.getStatus();
			}

		});
		
		col = createTableViewerColumn(titles[8], bounds[8], 8);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				if (o.getRating() == null)
					return "null";
				return o.getRating().toString();
			}

		});

		col = createTableViewerColumn(titles[9], bounds[9], 9);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				String oneWay = "";
				if (o.getOneWayFee() != null) {
					oneWay = o.getOneWayFee().getAmount();
					oneWay = oneWay + " " + o.getOneWayFee().getCurrency();
					if ( o.getOneWayFeeIncluded() != null ) {
						if ( o.getOneWayFeeIncluded().booleanValue() == true)
							oneWay = oneWay + " ( Included ) ";
						else
							oneWay = oneWay + " ( Not Included ) ";
					}
					if ( o.getOneWayTaxIncluded() != null ) {
						if ( o.getOneWayTaxIncluded().booleanValue() == true)
							oneWay = oneWay + " with Tax ";
						else
							oneWay = oneWay + " without Tax ";
					}
				}
				return oneWay;
			}

		});

		col = createTableViewerColumn(titles[10], bounds[10], 10);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				String oneWay = "";
				if (o.getOneWayFeeInSellCurrency() != null) {
					oneWay = o.getOneWayFeeInSellCurrency().getAmount();
					oneWay = oneWay + " " + o.getOneWayFeeInSellCurrency().getCurrency();
				}
				return oneWay;
			}

		});
		
		col = createTableViewerColumn(titles[11], bounds[11], 11);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SunnyOfferDo o = (SunnyOfferDo) element;
				String incl = o.getInclusiveKm();
				return incl;
			}
		});

	}


	@Override
	public void update() {
		getViewer().setInput(SunnyModelProvider.INSTANCE.getOfferDos());
		getViewer().refresh();
		
		
	}

	@Override
	protected void addTooltipListener() {
		SunnyTooltipListener listener = new SunnyTooltipListener(getParent().getShell(), getViewer().getTable());
		
		getViewer().getControl().addListener(SWT.MouseHover, listener.getTableListener());
	}
	
}

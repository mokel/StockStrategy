package fr.mokel.jstockstrategy.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXTextField;

import fr.mokel.jstockstrategy.data.MarketDataException;
import fr.mokel.jstockstrategy.data.MarketDataServices;
import fr.mokel.jstockstrategy.gui.components.IndicatorPanel;
import fr.mokel.jstockstrategy.model.DayValue;

public class MainPanel extends JPanel {

	private JXTextField stockField;
	private GraphViewer graph = new GraphViewer();
	private IndicatorPanel indicPanel = new IndicatorPanel();
	private JComboBox<Integer> nbMonthHistory = new JComboBox<Integer>();

	public MainPanel() {
		setLayout(new MigLayout("insets 5", "", ""));
		stockField = new JXTextField("yahoo code");
		stockField.setText("BNP.PA");
		stockField.addActionListener(new StockListener());
		add(new JLabel("Yahoo stock code:"), "align right");
		add(stockField, "wrap");

		DefaultComboBoxModel<Integer> cbModel = new DefaultComboBoxModel<Integer>();
		cbModel.addElement(1);
		cbModel.addElement(2);
		cbModel.addElement(3);
		cbModel.addElement(6);
		cbModel.addElement(12);
		cbModel.addElement(24);
		cbModel.addElement(36);
		nbMonthHistory.setModel(cbModel);
		nbMonthHistory.setSelectedItem(Integer.valueOf(36));
		nbMonthHistory.addActionListener(new StockListener());

		add(new JLabel("History length:"), "align right");
		add(nbMonthHistory, "wrap");

		add(indicPanel, "wrap, span 2");
		add(graph, "dock south");

	}
	
	class StockListener implements ActionListener, Observer {
		@Override
		public void actionPerformed(ActionEvent e) {
			String stock = stockField.getText();
			MarketDataServices mds = new MarketDataServices();
			mds.addObserver(this);
			new Thread() {
				public void run() {
					try {
						mds.getPrices(stock,
								LocalDate.now()
										.minusMonths((Integer) nbMonthHistory.getSelectedItem()),
								LocalDate.now()
								.minusDays(1));
					} catch (MarketDataException e) {
						JOptionPane.showMessageDialog(MainPanel.this,
								"Error while loading stock prices: " + e.getMessage());
					}
				};
			}.start();
		}

		@Override
		public void update(Observable obs, Object value) {
			@SuppressWarnings("unchecked")
			List<DayValue> list = (List<DayValue>) value;
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					graph.setStock(list);
				}
			});
		}
	}
}

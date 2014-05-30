package fr.mokel.jstockstrategy.gui.components;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import fr.mokel.jstockstrategy.gui.MainFrame;
import fr.mokel.jstockstrategy.gui.util.ConstraintsBuilder;
import fr.mokel.jstockstrategy.gui.util.EventManager;
import fr.mokel.jstockstrategy.gui.util.EventManager.EventType;
import fr.mokel.jstockstrategy.gui.util.FormPanel;
import fr.mokel.jstockstrategy.indicator.IndicatorType;

public class IndicatorPanel extends JPanel {

	JComboBox<IndicatorType> indicatorCombo;

	JButton addButton = new JButton("add");

	JPanel containerPanel;
	FormPanel paramsPanel;
	GridBagConstraints paramsPanelConstraints = new ConstraintsBuilder(2, 0, true).fillBoth()
			.gridwidth(GridBagConstraints.REMAINDER).build();

	public IndicatorPanel() {
		setLayout(new MigLayout("insets 0", "left", "top"));
		indicatorCombo = new JComboBox<IndicatorType>();
		DefaultComboBoxModel<IndicatorType> indicModel = new DefaultComboBoxModel<IndicatorType>();
		indicModel.addElement(IndicatorType.None);
		indicModel.addElement(IndicatorType.SMA);
		indicModel.addElement(IndicatorType.FFT);
		indicModel.addElement(IndicatorType.CMA);
		indicModel.addElement(IndicatorType.CCI);
		indicModel.addElement(IndicatorType.DERIVATIVE);
		indicatorCombo.setModel(indicModel);
		add(new JLabel("Indicators: "), "gaptop 3");
		add(indicatorCombo);
		// add dummy panel
		containerPanel = new JPanel();
		containerPanel.setLayout(new MigLayout("insets 0"));
		paramsPanel = new FormPanel();
		containerPanel.add(paramsPanel);
		add(containerPanel, "wrap");
		add(addButton);
		indicatorCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadParamsPanel((IndicatorType) indicatorCombo.getSelectedItem());
			}
		});
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object params = paramsPanel.getObject();
				EventManager.fireEvent(EventType.IndicatorAdded,
						(IndicatorType) indicatorCombo.getSelectedItem(), params);
			}
		});
	}

	private void loadParamsPanel(IndicatorType selectedIndic) {
		containerPanel.remove(paramsPanel);
		paramsPanel = new FormPanel(selectedIndic.getClazz());
		containerPanel.add(paramsPanel);
		MainFrame.INTANCE.pack();
	}
}

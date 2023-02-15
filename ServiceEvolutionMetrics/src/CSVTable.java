
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class CSVTable extends JFrame {

	private static final int NUMBER_OF_COLUMNS = 4;
	private static final String AEKOS_LINK = "Link";
	private static final String LONG = "Long";
	private static final String LAT = "Lat";
	private static final String CRS = "CRS";

	private Logger logger = Logger.getLogger(this.getName());
	private List<TableDataModel> tableList = new ArrayList<TableDataModel>();
	/**
	 * Default Serialization Version
	 */
	private static final long serialVersionUID = 1L;

	public CSVTable() {
		super();
		InputStream stream = this.getClass().getResourceAsStream("CSV_1.csv");
		readAndUpdateTableDataModel(stream);

		Object[] columnNames = new Object[NUMBER_OF_COLUMNS];
		columnNames[0] = AEKOS_LINK;
		columnNames[1] = LONG;
		columnNames[2] = LAT;
		columnNames[3] = CRS;

		DefaultTableModel model = new DefaultTableModel(columnNames,0);

		populateModel(model);

		JTable table = new JTable(model);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(new Rectangle(new Dimension(400, 300)));

		this.getContentPane().add(scrollPane);

	}

	private void populateModel(DefaultTableModel model) {
		for (int i = 0; i < tableList.size(); i++) {
			model.addRow(this.tableList.get(i).getDataAsObjectList());
		}
	}

	public static void main(String args[]) {
		final CSVTable table = new CSVTable();
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				table.setTitle("CSV Table");
				table.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				table.pack();
				table.setVisible(true);
			}
		});

	}

	public void readAndUpdateTableDataModel(InputStream in) {

		Scanner scan = new Scanner(in);

		while (scan.hasNext()) {
			String rowValue = scan.nextLine();
			seperateAndPopulate(tableList, rowValue);
		}
		Collections.sort(tableList);

	}

	private void seperateAndPopulate(List<TableDataModel> tableList,
			String rowValue) {
		String splitValue[] = rowValue.split(",");
		if (splitValue.length == NUMBER_OF_COLUMNS) {
			if (splitValue[0].equals(AEKOS_LINK)) {
				return;
			}
			TableDataModel model = TableDataModel.newInstance(splitValue[0],
					splitValue[1], splitValue[2], splitValue[3]);
			tableList.add(model);
		} else {
			logger.log(Level.SEVERE, "Issue with data");
		}
	}

}

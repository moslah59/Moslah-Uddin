import javax.swing.table.DefaultTableModel;

class CustomTableModel extends DefaultTableModel {
    private final boolean[] editableColumns;

    public CustomTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
        editableColumns = new boolean[columnNames.length];

        // Make only Product ID non-editable
        for (int i = 0; i < editableColumns.length; i++) {
            editableColumns[i] = i != 0; // Product ID (column 0) is false, others are true
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return editableColumns[column];
    }
}

package viewer;

import javax.swing.table.AbstractTableModel;
import java.util.Map;

public class TableModel extends AbstractTableModel {
    private String[] columns;
    private Map<Integer, Object[]> data;

    public TableModel(String[] columns, Map<Integer, Object[]> data) {
        this.columns = columns;
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        return data.get(i)[i1];
    }

    @Override
    public String getColumnName(int column){
        return columns[column];
    }
}

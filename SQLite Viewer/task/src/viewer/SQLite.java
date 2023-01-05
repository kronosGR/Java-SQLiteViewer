package viewer;

import org.sqlite.SQLiteDataSource;

import javax.lang.model.element.Name;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLite implements AutoCloseable {

    public static String QUERY_SELECT_ALL = "SELECT * FROM %s;";
    private String url;
    private Connection conn;

    public SQLite(String filename) {
        url = String.format("jdbc:sqlite:%s", filename);
        connect();
    }

    @Override
    public void close() throws Exception {
        conn.close();
    }

    private void connect() {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(url);
        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getTables() {
        List<String> tables = new ArrayList<>();
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT name FROM sqlite_master WHERE type ='table' AND name NOT LIKE 'sqlite_%';");
            while (rs.next()) {
                String tmp = rs.getString("name");
                tables.add(tmp);
            }
            return tables;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public TableModel executeQuery(String query, String tableName) {
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData md = rs.getMetaData();

            int clCount = md.getColumnCount();
            String[] columns = new String[clCount];
            for (int c = 0; c < md.getColumnCount(); c++) {
                columns[c] = md.getColumnName(c + 1);
            }

            Map<Integer, Object[]> data = new HashMap<>();
            int rowI = 0;
            while (rs.next()) {
                Object[] row = new Object[clCount];
                for (int c = 0; c < clCount; c++) {
                    row[c] = rs.getObject(c + 1);
                }
                data.put(rowI, row);
                rowI++;
            }
            return new TableModel(columns, data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

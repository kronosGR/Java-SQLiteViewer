package viewer;

import org.sqlite.SQLiteDataSource;

import javax.lang.model.element.Name;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLite implements AutoCloseable{

    public static String QUERY_SELECT_ALL = "SELECT * FROM %s";
    private String url;
    private Connection conn;

    public SQLite(String filename){
        url = String.format("jdbc:sqlite:%s", filename);
        connect();
    }

    @Override
    public void close() throws Exception {
        conn.close();
    }

    private void connect()  {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(url);
        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getTables(){
        List<String> tables = new ArrayList<>();
        try (Statement st = conn.createStatement()){
            ResultSet rs = st.executeQuery("SELECT name FROM sqlite_master WHERE type ='table' AND name NOT LIKE 'sqlite_%';");
            while (rs.next()){
                String tmp = rs.getString("name");
                tables.add(tmp);
            }
            return tables;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

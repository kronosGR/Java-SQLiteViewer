package viewer;

import org.sqlite.SQLiteDataSource;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteViewer extends JFrame {

    private String dbLocation;

    public SQLiteViewer() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 900);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("SQLite Viewer");

        initializeWindow();
    }

    private void initializeWindow() {
        JPanel p = new JPanel();
        p.setBounds(10, 10, 680, 50);
        p.setVisible(true);
        p.setAlignmentY(TOP_ALIGNMENT);
        add(p);

        JTextField jt = new JTextField();
        jt.setName("FileNameTextField");
        jt.setBounds(10, 10, 100, 50);
        jt.setColumns(50);
        p.add(jt);

        JButton jb = new JButton("Open");
        jb.setName("OpenFileButton");
        jt.setVisible(true);
        p.add(jb);

        JComboBox<String> tablesComboBox = new JComboBox<>();
        tablesComboBox.setBounds(10, 60, 650, 30);
        tablesComboBox.setName("TablesComboBox");
        add(tablesComboBox);

        JPanel p2 = new JPanel();
        p2.setVisible(true);
        p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
        p2.setMaximumSize(new Dimension(680, 100));
        p2.setBounds(10, 110, 680, 110);
        p2.setAlignmentY(Component.TOP_ALIGNMENT);
        add(p2);

        JTextArea queryTextArea = new JTextArea(8, 35);
        queryTextArea.setName("QueryTextArea");
        queryTextArea.setEnabled(true);
        p2.add(queryTextArea);

        JButton executeButton = new JButton("Execute");
        executeButton.setName("ExecuteQueryButton");
        executeButton.setEnabled(true);
        p2.add(executeButton);

        JTable table = new JTable();
        table.setName("Table");;
        JScrollPane tableSCRPanel = new JScrollPane(table);
        tableSCRPanel.setBounds(10, 250, 670, 400);
        tableSCRPanel.setVisible(true);
        table.setVisible(true);
        add(tableSCRPanel);


        jb.addActionListener(actionEvent -> {
            String filename = jt.getText().toString();
            String cwd = System.getProperty("user.dir");
            Path fp = Paths.get(cwd + "\\" + filename);
            dbLocation = fp.toString();
            if (!jt.getText().equals("") && Files.exists(fp))
            {
                tablesComboBox.removeAllItems();
                try ( SQLite sqLite = new SQLite(dbLocation)) {
                    sqLite.getTables().forEach(tablesComboBox::addItem);
                    queryTextArea.setText(String.format(SQLite.QUERY_SELECT_ALL, tablesComboBox.getSelectedItem()));
                    executeButton.setEnabled(true);
                    queryTextArea.setEnabled(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                tablesComboBox.removeAllItems();
                queryTextArea.setText(null);
                queryTextArea.setEnabled(false);
                executeButton.setEnabled(false);
                JOptionPane.showMessageDialog(
                        new Frame(),
                        "File doesn't exist!",
                        "File error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        tablesComboBox.addItemListener(actionEvent -> {
            queryTextArea.setText(String.format("SELECT * FROM %s;", actionEvent.getItem().toString()));
        });

        executeButton.addActionListener( event ->{
            try (SQLite sqLite = new SQLite(dbLocation)){
                TableModel tableModel = sqLite.executeQuery(queryTextArea.getText(),
                        (String) tablesComboBox.getSelectedItem());
                table.setModel(tableModel);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        new Frame(),
                        e.getMessage(),
                        "SQL error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}

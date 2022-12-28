package viewer;

import javax.swing.*;
import java.awt.*;

public class SQLiteViewer extends JFrame {

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
        jt.setBounds(10,10, 100, 50);
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

        JTextArea queryTextArea = new JTextArea(8,35);
        queryTextArea.setName("QueryTextArea ");
        queryTextArea.setEnabled(false);
        p2.add(queryTextArea);


        JButton executeButton = new JButton("Execute");
        executeButton.setName("ExecuteQueryButton");
        executeButton.setEnabled(false);
        p2.add(executeButton);

    }
}

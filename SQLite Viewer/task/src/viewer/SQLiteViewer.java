package viewer;

import javax.swing.*;

public class SQLiteViewer extends JFrame {

    public SQLiteViewer() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 900);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("SQLite Viewer");

        JPanel p = new JPanel();
        p.setBounds(10, 10, 680, 50);
        p.setVisible(true);
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
    }
}

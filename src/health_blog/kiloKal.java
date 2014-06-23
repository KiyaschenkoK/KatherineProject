package health_blog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.Vector;


public class kiloKal extends JFrame{
    private JPanel rootPanel;
    private JTextArea eatTextArea1;
    private JTextArea needTextArea2;
    private JTextArea balanceTextArea;
    private JPanel eatPanel;
    private JPanel needPanel;
    private JPanel foodPanel;
    private JPanel workPanel;
    private JTextField weightBox;
    private JTextField kKalBox1;
    private JButton eatAdd;
    private JScrollPane workPane;
    private JComboBox eatComboBox;
    private JTable eatTable;
    private JScrollPane eatPane;
    private JComboBox workComboBox;
    private JTextField workTime;
    private JButton workAdd;
    private JTable workTable;
    private JTextField kKalBox2;
    private JPanel test;

    public HashMap<String,Integer> EatMap=new HashMap<String, Integer>();
    public HashMap<String,Integer> WorkMap=new HashMap<String, Integer>();


    DefaultTableModel mod;
    DefaultTableModel mod1;

    int eatkkal=0;
    int needkkal=0;

    public kiloKal() {
        super("Баланс килоКалорий");

        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        eatTextArea1.setBackground(new Color(0,0,0,0));
        needTextArea2.setBackground(new Color(0,0,0,0));
        balanceTextArea.setBackground(new Color(0,0,0,0));

        eatPanel.setBorder(new MyBorder());
        needPanel.setBorder(new MyBorder());
        foodPanel.setBorder(new MyBorder());
        workPanel.setBorder(new MyBorder());


        balance();
        try {
            if (!LoadFiles()){
                eatComboBox.addItem("Files not found!");
                eatComboBox.setEnabled(false);
                eatAdd.setEnabled(false);

                workComboBox.addItem("Files not found!");
                workComboBox.setEnabled(false);
                workAdd.setEnabled(false);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        Vector<String> headerVect = new Vector<String>();
        headerVect.add("Блюдо,вес");
        headerVect.add("кКал");
        mod = new DefaultTableModel(headerVect, 0);
        eatTable.setModel(mod);
        Vector<String> headerVect1 = new Vector<String>();
        headerVect1.add("Деятельность, время");
        headerVect1.add("кКал");
        mod1 = new DefaultTableModel(headerVect1, 0);
        workTable.setModel(mod1);



        setVisible(true);
        eatAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<String> newRow = new Vector<String>();
                newRow.add(eatComboBox.getSelectedItem().toString() + ", " + weightBox.getText() + "г.");
                newRow.add(kKalBox1.getText());
                mod.addRow(newRow);
                if (!kKalBox1.getText().equals(""))
                    eatkkal += Integer.parseInt(kKalBox1.getText());
                balance();
            }
        });
        workAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<String> newRow = new Vector<String>();
                newRow.add(workComboBox.getSelectedItem().toString() + ", " + workTime.getText() + "мин.");
                newRow.add(kKalBox2.getText());
                mod1.addRow(newRow);
                if (!kKalBox2.getText().equals(""))
                    needkkal += Integer.parseInt(kKalBox2.getText());
                balance();
            }
        });

        eatTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int a = Integer.parseInt(mod.getValueAt(eatTable.getSelectedRow(), 1).toString().equals("") ? "0" : mod.getValueAt(eatTable.getSelectedRow(), 1).toString());
                eatkkal -= a;
                balance();
                mod.removeRow(eatTable.getSelectedRow());
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        workTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int a = Integer.parseInt(mod1.getValueAt(workTable.getSelectedRow(), 1).toString().equals("") ? "0" : mod1.getValueAt(workTable.getSelectedRow(), 1).toString());
                needkkal -= a;
                balance();
                mod1.removeRow(workTable.getSelectedRow());
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


        eatComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int w = Integer.parseInt(weightBox.getText());
                kKalBox1.setText(EatMap.get(eatComboBox.getSelectedItem()) * w / 100 + "");
            }
        });
        workComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int w = Integer.parseInt(workTime.getText());
                kKalBox2.setText(WorkMap.get(workComboBox.getSelectedItem()) * w / 60 + "");
            }
        });

        weightBox.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                int w = Integer.parseInt(weightBox.getText());
                kKalBox1.setText(EatMap.get(eatComboBox.getSelectedItem()) * w / 100 + "");
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        workTime.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                int w = Integer.parseInt(workTime.getText());
                kKalBox2.setText(WorkMap.get(workComboBox.getSelectedItem()) * w / 60 + "");
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
 
    public void balance(){
        rootPane.repaint();
        eatTextArea1.setText("↑ "+eatkkal+" кКал");
        needTextArea2.setText("↓ "+needkkal+" кКал");
        balanceTextArea.setText((eatkkal-needkkal)+" кКал");


    }
    public boolean LoadFiles() throws IOException {
        try {
            BufferedReader buffer;
            buffer = new BufferedReader(new InputStreamReader(kiloKal.class.getResource("products.dat").openStream()));
            String s=buffer.readLine();
            while (s!=null){
                Integer k=Integer.parseInt(String.copyValueOf(s.toCharArray(),s.lastIndexOf(" ")+1,s.length()-1-s.lastIndexOf(" ")));
                EatMap.put(String.copyValueOf(s.toCharArray(),0,s.lastIndexOf(" ")),k);
                eatComboBox.addItem(String.copyValueOf(s.toCharArray(),0,s.lastIndexOf(" ")));
                s=buffer.readLine();
            }
            buffer=new BufferedReader(new InputStreamReader(kiloKal.class.getResource("fitness.dat").openStream()));
            s=buffer.readLine();
            while (s!=null){
                Integer k=Integer.parseInt(String.copyValueOf(s.toCharArray(),s.lastIndexOf(" ")+1,s.length()-1-s.lastIndexOf(" ")));
                WorkMap.put(String.copyValueOf(s.toCharArray(),0,s.lastIndexOf(" ")),k);
                workComboBox.addItem(String.copyValueOf(s.toCharArray(),0,s.lastIndexOf(" ")));
                s=buffer.readLine();
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            return false;
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

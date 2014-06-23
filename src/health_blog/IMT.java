package health_blog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IMT extends JFrame{
    private JPanel rootPanel;
    private JTextField ростTextField;
    private JTextField весTextField;
    private JTextField обхватТалииTextField;
    private JTextField обхватШеиTextField;
    private JTextField обхватБедерЖенщинаTextField;
    private JRadioButton мужчинаRadioButton;
    private JRadioButton женщинаRadioButton;
    private JButton считатьButton;
    private JTextArea процентЖираTextArea;
    private JTextArea индексМассыТелаTextArea;
    private JTextArea textArea3;
    private JTextArea textArea4;

    public IMT() {
        super("Индекс массы тела");

        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);


        считатьButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean sex=true; //какой пол выбран (true - female;  false - male)
                if (женщинаRadioButton.isSelected())
                    sex=true;
                else if (мужчинаRadioButton.isSelected())
                    sex=false;
                else{
                    JOptionPane.showMessageDialog(IMT.this,"Неполные данные!\nДля начала укажите свой пол!");
                    return;
                }
                double рост,вес,обхватТалии, обхватШеи, обхватБедер = 0;

                //на случай неправильного формата входных данных
                try {
                    рост = Double.parseDouble(ростTextField.getText());
                    вес = Double.parseDouble(весTextField.getText());
                    обхватТалии = Double.parseDouble(обхватТалииTextField.getText());
                    обхватШеи = Double.parseDouble(обхватШеиTextField.getText());
                    if (sex)
                        обхватБедер = Double.parseDouble(обхватБедерЖенщинаTextField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(IMT.this, "Неверный формат данных!");
                    return;
                }


                Double I,fat;
                I = вес / (Math.pow((рост/100),2.0));//подсчет ИМТ

                //подсчет процента жира(в зависимости от пола)
                if (sex)
                {
                    fat=495.0/(1.29579-0.35004*(Math.log(обхватТалии+обхватБедер-обхватШеи))+0.22100*(Math.log(рост)))-450.0;
                }
                else
                {
                    fat=495.0/(1.0324-0.19077*(Math.log(обхватТалии-обхватШеи))+0.15456*(Math.log(рост)))-450.0;
                }

                процентЖираTextArea.setText(fat.toString().substring(0,5));
                индексМассыТелаTextArea.setText(I.toString().substring(0,5));

                //сравненте с табличными данными
                if (I<=16){
                    textArea4.setBackground(null);
                    textArea4.setText("Выраженный дефицит массы тела");
                }
                else if (I<=18.5){
                    textArea4.setBackground(null);
                    textArea4.setText("Недостаточная (дефицит) масса тела");
                }
                else if (I<=25){
                    textArea4.setBackground(Color.white);
                    textArea4.setText("Норма");
                }
                else if (I<=30){
                    textArea4.setBackground(null);
                    textArea4.setText("Избыточная масса тела (предожирение)");
                }
                else if (I<=35){
                    textArea4.setBackground(null);
                    textArea4.setText("Ожирение первой степени");
                }
                else if (I<=40){
                    textArea4.setBackground(null);
                    textArea4.setText("Ожирение второй степени");
                }
                else {
                    textArea4.setBackground(null);
                    textArea4.setText("Ожирение третьей степени (морбидное)");
                }

                if (sex)
                {
                    if (fat<10){
                        textArea3.setBackground(null);
                        textArea3.setText("");
                    }
                    else if (fat<=13){
                        textArea3.setBackground(Color.white);
                        textArea3.setText("Существенный жир");
                    }
                    else if (fat<=20){
                        textArea3.setBackground(Color.cyan);
                        textArea3.setText("Спортсмены");
                    }
                    else if (fat<=24){
                        textArea3.setBackground(Color.green);
                        textArea3.setText("Средний");
                    }
                    else if (fat<=31){
                        textArea3.setBackground(Color.pink);
                        textArea3.setText("Приемлемый");
                    }
                    else {
                        textArea3.setBackground(Color.red);
                        textArea3.setText("Тучный");
                    }
                }
                else
                {

                }




            }
        });



        setVisible(true);
    }


}

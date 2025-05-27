package mybanksystem;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Date;
import java.io.*;

public class Withdrawl extends JFrame implements ActionListener {

    JTextField t1;
    JButton b1, b2;
    JLabel l1, l2;
    String pin;

    Withdrawl(String pin) {
        this.pin = pin;

        // Background Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(700, 500, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, 700, 500);
        add(background);

        // Labels
        l1 = new JLabel("MAXIMUM WITHDRAWAL IS RS.10,000");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.PLAIN, 12));
        background.add(l1);

        l2 = new JLabel("PLEASE ENTER YOUR AMOUNT");
        l2.setForeground(Color.WHITE);
        l2.setFont(new Font("System", Font.PLAIN, 12));
        background.add(l2);

        // Text field
        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.PLAIN, 14));
        background.add(t1);

        // Buttons
        b1 = new JButton("WITHDRAW");
        b1.setFont(new Font("System", Font.PLAIN, 12));
        background.add(b1);

        b2 = new JButton("BACK");
        b2.setFont(new Font("System", Font.PLAIN, 12));
        background.add(b2);

        // Centered bounds for labels, text field, and buttons
        l1.setBounds(150, 170, 400, 20);
        l2.setBounds(180, 190, 300, 20);
        t1.setBounds(170, 230, 200, 25);
        b1.setBounds(160, 270, 100, 25);
        b2.setBounds(300, 270, 100, 25);

        // Frame setup
        b1.addActionListener(this);
        b2.addActionListener(this);
        setLayout(null);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setUndecorated(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String amount = t1.getText();
            Date date = new Date();

            if (ae.getSource() == b1) {
                if (amount.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the Amount to you want to Withdraw");
                } else {
                    File file = new File("transactions.txt");
                    file.createNewFile();

                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line;
                    int balance = 0;

                    while ((line = reader.readLine()) != null) {
                        String[] data = line.split(",");
                        if (data.length == 4 && data[0].equals(pin)) {
                            if (data[2].equalsIgnoreCase("Deposit")) {
                                balance += Integer.parseInt(data[3]);
                            } else if (data[2].equalsIgnoreCase("Withdrawl")) {
                                balance -= Integer.parseInt(data[3]);
                            }
                        }
                    }
                    reader.close();

                    int withdrawAmount = Integer.parseInt(amount);
                    if (balance < withdrawAmount) {
                        JOptionPane.showMessageDialog(null, "Insufficient Balance");
                        return;
                    }

                    FileWriter fw = new FileWriter(file, true);
                    fw.write(pin + "," + date + ",Withdrawl," + amount + "\n");
                    fw.close();

                    JOptionPane.showMessageDialog(null, "Rs. " + amount + " Debited Successfully");

                    setVisible(false);
                    new Transactions(pin).setVisible(true);
                }
            } else if (ae.getSource() == b2) {
                setVisible(false);
                new Transactions(pin).setVisible(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Withdrawl("").setVisible(true);
    }
}

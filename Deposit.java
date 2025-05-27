
package mybanksystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Deposit extends JFrame implements ActionListener {

    JTextField t1;
    JButton b1, b2;
    JLabel l1;
    String pin;

    Deposit(String pin) {
        this.pin = pin;

        // Load and scale the background image
        ImageIcon i1 = new ImageIcon(getClass().getResource("/icons/atm.jpg")); // Ensure the image exists in src/icons/
        Image i2 = i1.getImage().getScaledInstance(850, 700, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, 850, 700);
        add(background);

        // Label
        l1 = new JLabel("ENTER AMOUNT YOU WANT TO DEPOSIT");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(160, 230, 400, 35);
        background.add(l1);

        // TextField
        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 22));
        t1.setBounds(160, 280, 320, 30);
        background.add(t1);

        // Deposit Button
        b1 = new JButton("DEPOSIT");
        b1.setFont(new Font("System", Font.BOLD, 14));
        b1.setBounds(160, 350, 150, 35);
        b1.addActionListener(this);
        background.add(b1);

        // Back Button
        b2 = new JButton("BACK");
        b2.setFont(new Font("System", Font.BOLD, 14));
        b2.setBounds(330, 350, 150, 35);
        b2.addActionListener(this);
        background.add(b2);

        // Frame setup
        setLayout(null);
        setSize(850, 700);
        setLocationRelativeTo(null); // Center on screen
        setUndecorated(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String amount = t1.getText().trim();
        Date date = new Date();

        if (ae.getSource() == b1) {
            if (amount.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter the amount you want to deposit.");
            } else {
                try {
                    FileWriter fw = new FileWriter("transactions.txt", true);
                    fw.write(pin + "," + date.toString() + ",Deposit," + amount + "\n");
                    fw.close();

                    JOptionPane.showMessageDialog(null, "Rs. " + amount + " Deposited Successfully");
                    setVisible(false);
                    new Transactions(pin).setVisible(true);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error saving deposit: " + e.getMessage());
                }
            }
        } else if (ae.getSource() == b2) {
            setVisible(false);
            new Transactions(pin).setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Deposit("1234").setVisible(true);
    }
}

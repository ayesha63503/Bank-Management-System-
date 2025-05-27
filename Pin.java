package mybanksystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Pin extends JFrame implements ActionListener {

    JPasswordField oldPinField, newPinField, rePinField;
    JButton changeBtn, backBtn;
    JLabel l1, l2, l3, l4;
    String pin;

    Pin(String pin) {
        this.pin = pin;

        setLayout(null);

        l1 = new JLabel("CHANGE YOUR PIN");
        l1.setFont(new Font("System", Font.BOLD, 18));
        l1.setBounds(100, 30, 250, 30);
        add(l1);

        l2 = new JLabel("Enter Old PIN:");
        l2.setBounds(50, 80, 200, 25);
        add(l2);

        oldPinField = new JPasswordField();
        oldPinField.setBounds(200, 80, 150, 25);
        add(oldPinField);

        l3 = new JLabel("New PIN:");
        l3.setBounds(50, 120, 200, 25);
        add(l3);

        newPinField = new JPasswordField();
        newPinField.setBounds(200, 120, 150, 25);
        add(newPinField);

        l4 = new JLabel("Re-Enter New PIN:");
        l4.setBounds(50, 160, 200, 25);
        add(l4);

        rePinField = new JPasswordField();
        rePinField.setBounds(200, 160, 150, 25);
        add(rePinField);

        changeBtn = new JButton("CHANGE");
        changeBtn.setBounds(70, 220, 100, 30);
        changeBtn.addActionListener(this);
        add(changeBtn);

        backBtn = new JButton("BACK");
        backBtn.setBounds(200, 220, 100, 30);
        backBtn.addActionListener(this);
        add(backBtn);

        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == changeBtn) {
            String oldPin = oldPinField.getText();
            String newPin = newPinField.getText();
            String rePin = rePinField.getText();

            if (!newPin.equals(rePin)) {
                JOptionPane.showMessageDialog(this, "New PIN does not match!");
                return;
            }

            try (BufferedReader br = new BufferedReader(new FileReader("transactions.txt"))) {
                String line;
                boolean pinFound = false;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts[0].trim().equals(oldPin)) {
                        pinFound = true;
                        break;
                    }
                }

                if (pinFound) {
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter("transactions.txt", true))) {
                        bw.write(newPin + "," + "PIN Changed" + "," + new java.util.Date() + "\n");
                        JOptionPane.showMessageDialog(this, "PIN changed successfully!");
                        this.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Old PIN incorrect!");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        } else if (ae.getSource() == backBtn) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new Pin("");
    }
}

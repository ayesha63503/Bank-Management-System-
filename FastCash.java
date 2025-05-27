package mybanksystem;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener {

    String pin;
    JButton b100, b500, b1000, b2000, b5000, b10000, backBtn;

    FastCash(String pin) {

       ImageIcon i1 = new ImageIcon(getClass().getResource("/icons/MyAtm.jpeg"));
       if (i1.getImageLoadStatus() != MediaTracker.COMPLETE) {
       JOptionPane.showMessageDialog(this, "Image not found or could not be loaded.");
}

        this.pin = pin;
        setLayout(null);

        JLabel label = new JLabel("Fast Cash - PIN: " + pin);
        label.setBounds(50, 30, 300, 25);
        add(label);

        b100 = new JButton("Rs 100");
        b100.setBounds(50, 80, 100, 30);
        b100.addActionListener(this);
        add(b100);

        b500 = new JButton("Rs 500");
        b500.setBounds(160, 80, 100, 30);
        b500.addActionListener(this);
        add(b500);

        b1000 = new JButton("Rs 1000");
        b1000.setBounds(50, 130, 100, 30);
        b1000.addActionListener(this);
        add(b1000);

        b2000 = new JButton("Rs 2000");
        b2000.setBounds(160, 130, 100, 30);
        b2000.addActionListener(this);
        add(b2000);

        b5000 = new JButton("Rs 5000");
        b5000.setBounds(50, 180, 100, 30);
        b5000.addActionListener(this);
        add(b5000);

        b10000 = new JButton("Rs 10000");
        b10000.setBounds(160, 180, 100, 30);
        b10000.addActionListener(this);
        add(b10000);

        backBtn = new JButton("Back");
        backBtn.setBounds(110, 230, 100, 30);
        backBtn.addActionListener(this);
        add(backBtn);

        setSize(350, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == backBtn) {
            new Transactions(pin).setVisible(true);
            this.dispose();
            return;
        }

        String cmd = ((JButton) ae.getSource()).getText();
        int amount = Integer.parseInt(cmd.replaceAll("[^0-9]", ""));
        String enteredPin = JOptionPane.showInputDialog(this, "Enter PIN for verification:");

        if (!verifyPinFromFile(enteredPin)) {
            JOptionPane.showMessageDialog(this, "Incorrect PIN. Try again.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader("transactions.txt"))) {
            String line;
            int balance = 0;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[0].trim().equals(enteredPin.trim())) {
                    String mode = parts[2].trim().toLowerCase();
                    int amt = Integer.parseInt(parts[3].trim());
                    if (mode.equals("deposit")) {
                        balance += amt;
                    } else if (mode.equals("withdraw")) {
                        balance -= amt;
                    }
                }
            }

            if (balance >= amount) {
                balance -= amount;
                try (FileWriter fw = new FileWriter("transactions.txt", true)) {
                    String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
                    fw.write(enteredPin + "," + date + ",Withdraw," + amount + "\n");
                }
                JOptionPane.showMessageDialog(this, "Rs. " + amount + " withdrawn successfully. Remaining balance: Rs. " + balance);
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient balance!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private boolean verifyPinFromFile(String enteredPin) {
        try (BufferedReader br = new BufferedReader(new FileReader("transactions.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[0].trim().equals(enteredPin.trim())) {
                    return true;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage());
        }
        return false;
    }
}

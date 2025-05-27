package mybanksystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class BalanceEnquiry extends JFrame implements ActionListener {

    JButton backButton;
    JLabel balanceLabel;
    String pin;

    BalanceEnquiry(String pin) {
        this.pin = pin;

        // Load and scale background image
        ImageIcon bgIcon = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image scaledImage = bgIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(scaledImage));
        background.setBounds(0, 0, 800, 600);
        add(background);

        // Label to show balance
        balanceLabel = new JLabel();
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setFont(new Font("System", Font.BOLD, 14));  // smaller font for better fit
        balanceLabel.setBounds(150, 250, 500, 30);               // wider label bounds
        background.add(balanceLabel);

        // Back button
        backButton = new JButton("BACK");
        backButton.setBounds(330, 350, 150, 30);
        background.add(backButton);

        backButton.addActionListener(this);

        // Calculate balance and set label text
        int balance = getBalance(pin);
        balanceLabel.setText("Your Current Account Balance is Rs " + balance);

        setLayout(null);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Allow closing the window
        setVisible(true);
    }

    private int getBalance(String pin) {
        int balance = 0;
        try {
            File file = new File("transactions.txt");
            if (!file.exists()) file.createNewFile(); // Ensure file exists

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4 && parts[0].equals(pin)) {
                    String mode = parts[2];
                    int amount = Integer.parseInt(parts[3]);
                    if (mode.equalsIgnoreCase("Deposit")) {
                        balance += amount;
                    } else if (mode.equalsIgnoreCase("Withdrawl")) {
                        balance -= amount;
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error reading transactions: " + e.getMessage());
        }
        return balance;
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Transactions(pin).setVisible(true);
    }

    public static void main(String[] args) {
        new BalanceEnquiry("1234");
    }
}

package mybanksystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Login extends JFrame implements ActionListener {

    JButton login, signup, clear;
    JTextField cardTextField;
    JPasswordField pinTextField;

    public Login() {
        
         ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
        Image i2 = i1.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l11 = new JLabel(i3);
        l11.setBounds(150, 20, 60, 70);
        add(l11);
        
        
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createBankFileIfNotExists();

        JLabel text = new JLabel("Welcome to Riphah Bank");
        text.setFont(new Font("Osward", Font.BOLD, 38));
        text.setBounds(240, 40, 400, 40);
        add(text);

        JLabel cardno = new JLabel("Card No:");
        cardno.setFont(new Font("Raleway", Font.BOLD, 28));
        cardno.setBounds(120, 150, 150, 30);
        add(cardno);

        cardTextField = new JTextField();
        cardTextField.setBounds(300, 150, 230, 30);
        cardTextField.setFont(new Font("Ariel", Font.BOLD, 14));
        add(cardTextField);

        JLabel pin = new JLabel("PIN:");
        pin.setFont(new Font("Raleway", Font.BOLD, 28));
        pin.setBounds(120, 220, 250, 30);
        add(pin);

        pinTextField = new JPasswordField();
        pinTextField.setBounds(300, 220, 230, 30);
        pinTextField.setFont(new Font("Ariel", Font.BOLD, 14));
        add(pinTextField);

        login = new JButton("SIGN IN");
        login.setBounds(300, 300, 100, 30);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);

        clear = new JButton("CLEAR");
        clear.setBounds(430, 300, 100, 30);
        clear.setBackground(Color.BLACK);
        clear.setForeground(Color.WHITE);
        clear.addActionListener(this);
        add(clear);

        signup = new JButton("SIGN UP");
        signup.setBounds(300, 350, 230, 30);
        signup.setBackground(Color.BLACK);
        signup.setForeground(Color.WHITE);
        signup.addActionListener(this);
        add(signup);

        getContentPane().setBackground(Color.WHITE);

        setSize(800, 480);
        setVisible(true);
        setLocation(350, 200);
        setTitle("Banking System");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == clear) {
            cardTextField.setText("");
            pinTextField.setText("");
        } else if (ae.getSource() == login) {
            String cardNo = cardTextField.getText().trim();
            String pin = new String(pinTextField.getPassword()).trim();

            if (cardNo.isEmpty() || pin.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter Card No and PIN");
                return;
            }

            boolean loginSuccess = false;
            String userName = "";

            try (BufferedReader reader = new BufferedReader(new FileReader("bank.txt"))) {
    String line;
    while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length >= 2) {
            String storedCard = parts[0].trim();  // card no stored at index 0
            String storedPin = parts[1].trim();   // pin stored at index 1

            // Check if card number and pin match the input
            if (storedCard.equals(cardNo) && storedPin.equals(pin)) {
                loginSuccess = true;
                break;  // no need to check further
            }
        }
    }
} catch (IOException e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(null, "Error reading bank.txt file");
}
 

            if (loginSuccess) {
    JOptionPane.showMessageDialog(null, "Login Successful!");
    setVisible(false);
    new Transactions(pin).setVisible(true);
} else {
    JOptionPane.showMessageDialog(null, "Incorrect Card No or PIN");
}


        } else if (ae.getSource() == signup) {
            setVisible(false);
            new SignupOne().setVisible(true);
        }
    }

    private void createBankFileIfNotExists() {
        try {
            File file = new File("bank.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error creating bank.txt file.");
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}

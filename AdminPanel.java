
package mybanksystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class AdminPanel extends JFrame implements ActionListener {

    JButton addButton, updateButton, deleteButton, viewButton, logoutButton;

    public AdminPanel() {
        
        
        String adminPassword = JOptionPane.showInputDialog(null, "Enter Admin Password:", "Admin Login", JOptionPane.PLAIN_MESSAGE);
if (adminPassword == null || !adminPassword.equals("admin123")) {
    JOptionPane.showMessageDialog(null, "Access Denied! Incorrect password.", "Error", JOptionPane.ERROR_MESSAGE);
    System.exit(0);
}
  
        setTitle("Admin Panel");
        setLayout(new GridLayout(5, 1, 10, 10));

        addButton = new JButton("Add User");
        updateButton = new JButton("Update User");
        deleteButton = new JButton("Delete User");
        viewButton = new JButton("View Users");
        logoutButton = new JButton("Logout");

        addButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        viewButton.addActionListener(this);
        logoutButton.addActionListener(this);

        add(addButton);
        add(updateButton);
        add(deleteButton);
        add(viewButton);
        add(logoutButton);

        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addButton) {
            new Login().setVisible(true);
        } else if (ae.getSource() == updateButton) {
            new Pin("").setVisible(true);
        } else if (ae.getSource() == deleteButton) {
            new DeleteUserGUI().setVisible(true);
        } else if (ae.getSource() == viewButton) {
            displayUserData();
        } else if (ae.getSource() == logoutButton) {
            dispose();
            new Login().setVisible(true);
        }
    }

    private void displayUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.txt"))) {
            String line;
            System.out.println("User Data from transactions.txt:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading user data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new AdminPanel();
    }
}

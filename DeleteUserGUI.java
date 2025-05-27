package mybanksystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DeleteUserGUI extends JFrame implements ActionListener {

    JTextField userIdField;
    JButton deleteButton, backButton;

    public DeleteUserGUI() {
        setTitle("Delete User");
        setLayout(null);

        JLabel label = new JLabel("Enter Card No or PIN:");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setBounds(50, 50, 300, 30);
        add(label);

        userIdField = new JTextField();
        userIdField.setBounds(50, 100, 300, 30);
        add(userIdField);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(50, 150, 100, 30);
        deleteButton.addActionListener(this);
        add(deleteButton);

        backButton = new JButton("Back");
        backButton.setBounds(200, 150, 100, 30);
        backButton.addActionListener(this);
        add(backButton);

        setSize(400, 300);
        setLocation(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == deleteButton) {
            String userId = userIdField.getText().trim();
            if (userId.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter Card No or PIN");
                return;
            }

            // Call the delete function
            boolean deleted = DeleteUser.deleteUserByCardOrPin(userId);
            if (deleted) {
                JOptionPane.showMessageDialog(null, "User deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "User not found.");
            }
        } else if (ae.getSource() == backButton) {
            setVisible(false);
            new Login().setVisible(true);
        }
    }

    public static void main(String[] args) {
        new DeleteUserGUI();
    }
}


package mybanksystem;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MiniStatement extends JFrame implements ActionListener {

    JLabel l1, l2, l3, l4;
    JButton exit;

    public MiniStatement(String pin) {
        super("Mini Statement");
        getContentPane().setBackground(Color.WHITE);
        setSize(400, 600);
        setLocation(20, 20);
        setLayout(null);

        l1 = new JLabel();
        l1.setBounds(20, 140, 400, 300);
        add(l1);

        l2 = new JLabel("Riphah Bank");
        l2.setBounds(150, 20, 200, 20);
        add(l2);

        l3 = new JLabel();
        l3.setBounds(20, 80, 300, 20);
        add(l3);

        l4 = new JLabel();
        l4.setBounds(20, 460, 300, 20);
        add(l4);

        exit = new JButton("Exit");
        exit.setBounds(20, 500, 100, 25);
        exit.addActionListener(this);
        add(exit);

        loadUserData(pin);
    }

    private void loadUserData(String inputPin) {
        File file = new File("transactions.txt");
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "transactions.txt not found!");
            return;
        }

        boolean pinFound = false;
        StringBuilder statement = new StringBuilder("<html><b>Transactions:</b><br><br>");
        int balance = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Expected format: PIN,Date,Mode,Amount
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String pin = parts[0].trim();
                    String date = parts[1].trim();
                    String mode = parts[2].trim().toLowerCase();  // convert to lower case
                    int amount = Integer.parseInt(parts[3].trim());

                    if (pin.equals(inputPin)) {
                        pinFound = true;

                        statement.append(date).append("&nbsp;&nbsp;&nbsp;")
                                 .append(parts[2].trim())  // show original mode (case preserved)
                                 .append("&nbsp;&nbsp;&nbsp;Rs ")
                                 .append(amount).append("<br><br>");

                        if (mode.contains("deposit")) {
                            balance += amount;
                        } else if (mode.contains("withdraw")) {
                            balance -= amount;
                        }
                    }
                }
            }

            statement.append("</html>");

            if (pinFound) {
                l1.setText(statement.toString());
                l4.setText("Your total Balance after transactions is Rs " + balance);
            } else {
                JOptionPane.showMessageDialog(null, "No transactions found for the entered PIN.");
                l1.setText("No transactions available.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        this.setVisible(false);
    }

    public static void main(String[] args) {
        String enteredPin = JOptionPane.showInputDialog("Enter PIN:");
        if (enteredPin != null && !enteredPin.trim().isEmpty()) {
            new MiniStatement(enteredPin.trim()).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "PIN cannot be empty.");
        }
    }
}

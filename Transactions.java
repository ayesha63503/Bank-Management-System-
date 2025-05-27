package mybanksystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Transactions extends JFrame implements ActionListener {

    JLabel l1;
    JButton b1, b2, b3, b4, b5, b6, b7;
    String pin;

    Transactions(String pin) {
        this.pin = pin;

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, 800, 600);
        add(background);

        l1 = new JLabel("Please Select Your Transaction", SwingConstants.CENTER);
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 14));
        l1.setBounds(0, 194, 600, 30);

        background.add(l1);

        Font btnFont = new Font("System", Font.BOLD, 11);
        int btnWidth = 130;
        int btnHeight = 25;
        int baseY = 230;
        int gapY = 38;

        int leftX = 160;
        int rightX = leftX + btnWidth + 30; // 320

        b1 = createButton("DEPOSIT", btnFont, leftX, baseY, btnWidth, btnHeight);
        background.add(b1);

        b3 = createButton("FAST CASH", btnFont, leftX, baseY + gapY, btnWidth, btnHeight);
        background.add(b3);

        b5 = createButton("PIN CHANGE", btnFont, leftX, baseY + 2 * gapY, btnWidth, btnHeight);
        background.add(b5);

        b2 = createButton("CASH WITHDRAWAL", btnFont, rightX, baseY, btnWidth, btnHeight);
        background.add(b2);

        b4 = createButton("MINI STATEMENT", btnFont, rightX, baseY + gapY, btnWidth, btnHeight);
        background.add(b4);

        b6 = createButton("BALANCE ENQUIRY", btnFont, rightX, baseY + 2 * gapY, btnWidth, btnHeight);
        background.add(b6);

        // Center Exit button horizontally
       int exitX = (800 - btnWidth) / 2 - 86; // 50 pixels left of center

        b7 = createButton("EXIT", btnFont, exitX, baseY + 3 * gapY, btnWidth, btnHeight);
        background.add(b7);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);

        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setUndecorated(false);
        setVisible(true);
    }

    private JButton createButton(String text, Font font, int x, int y, int width, int height) {
        JButton btn = new JButton(text);
        btn.setFont(font);
        btn.setBounds(x, y, width, height);
        btn.setMargin(new Insets(2, 5, 2, 5)); // less padding, helps fit text
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
            setVisible(false);
            new Deposit(pin).setVisible(true);
        } else if (ae.getSource() == b2) {
            setVisible(false);
            new Withdrawl(pin).setVisible(true);
        } else if (ae.getSource() == b3) {
            setVisible(false);
            new FastCash(pin).setVisible(true);
        } else if (ae.getSource() == b4) {
            new MiniStatement(pin).setVisible(true);
        } else if (ae.getSource() == b5) {
            setVisible(false);
            new Pin(pin).setVisible(true);
        } else if (ae.getSource() == b6) {
            setVisible(false);
            new BalanceEnquiry(pin).setVisible(true);
        } else if (ae.getSource() == b7) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Transactions("").setVisible(true);
    }
}

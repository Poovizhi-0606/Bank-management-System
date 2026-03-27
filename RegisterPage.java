import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class RegisterPage extends JFrame implements ActionListener {

    JTextField user;
    JPasswordField pass;
    JButton register;

    RegisterPage() {

        setTitle("Register");

        JLabel l1 = new JLabel("New Username");
        JLabel l2 = new JLabel("New Password");

        user = new JTextField();
        pass = new JPasswordField();

        register = new JButton("Register");

        l1.setBounds(50, 50, 120, 30);
        l2.setBounds(50, 100, 120, 30);

        user.setBounds(170, 50, 100, 30);
        pass.setBounds(170, 100, 100, 30);

        register.setBounds(100, 150, 100, 30);

        add(l1); add(l2); add(user); add(pass); add(register);

        setSize(350, 250);
        setLayout(null);
        setVisible(true);

        register.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        String u = user.getText();
        String p = pass.getText();

        try {
            BufferedWriter bw = new BufferedWriter(
                new FileWriter("users.txt", true)
            );

            bw.write(u + "," + p);
            bw.newLine();
            bw.close();

            JOptionPane.showMessageDialog(this, "Registered Successfully");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error");
        }
    }
}
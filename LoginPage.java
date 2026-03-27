import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class LoginPage extends JFrame implements ActionListener {

    JTextField user;
    JPasswordField pass;
    JButton login, register, viewUsers;

    LoginPage() {

        setTitle("Bank Login");

        JLabel l1 = new JLabel("Username");
        JLabel l2 = new JLabel("Password");

        user = new JTextField();
        pass = new JPasswordField();

        login = new JButton("Login");
        register = new JButton("Register");
        viewUsers = new JButton("View Users");

        l1.setBounds(50, 50, 100, 30);
        l2.setBounds(50, 100, 100, 30);

        user.setBounds(150, 50, 100, 30);
        pass.setBounds(150, 100, 100, 30);

        login.setBounds(100, 150, 100, 30);
        register.setBounds(100, 180, 100, 30);
        viewUsers.setBounds(90, 210, 120, 30);

        add(l1);
        add(l2);
        add(user);
        add(pass);
        add(login);
        add(register);
        add(viewUsers);

        setSize(300, 300);
        setLayout(null);
        setVisible(true);

        login.addActionListener(this);
        register.addActionListener(e -> new RegisterPage());
        viewUsers.addActionListener(e -> showUsers());
    }

    // 🔐 Validate user from file
    boolean validateUser(String u, String p) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(u) && data[1].equals(p)) {
                    br.close();
                    return true;
                }
            }
            br.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reading file");
        }
        return false;
    }

    // 🎯 Login button action
    public void actionPerformed(ActionEvent e) {

        String u = user.getText();
        String p = pass.getText();

        if (validateUser(u, p)) {
            new BankMenu(u);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Login");
        }
    }

    // ⭐ VERY IMPORTANT METHOD (placed before main)
    void showUsers() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));

            String line, data = "";

            while ((line = br.readLine()) != null) {
                String[] user = line.split(",");
                data += "Username: " + user[0] + "\n";
            }

            br.close();

            JOptionPane.showMessageDialog(this, data);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reading users");
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
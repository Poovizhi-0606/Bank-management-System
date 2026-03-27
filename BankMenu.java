import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BankMenu extends JFrame implements ActionListener {

    JButton deposit, withdraw, balance, history;
    double bal;
    String username;

    BankMenu(String user) {

        username = user;

        setTitle("Bank Menu - " + username);

        deposit = new JButton("Deposit");
        withdraw = new JButton("Withdraw");
        balance = new JButton("Check Balance");
        history = new JButton("History");

        deposit.setBounds(80, 50, 150, 30);
        withdraw.setBounds(80, 100, 150, 30);
        balance.setBounds(80, 150, 150, 30);
        history.setBounds(80, 200, 150, 30);

        add(deposit); add(withdraw); add(balance); add(history);

        setSize(300, 300);
        setLayout(null);
        setVisible(true);

        deposit.addActionListener(this);
        withdraw.addActionListener(this);
        balance.addActionListener(this);
        history.addActionListener(this);

        loadBalance();
    }

    void loadBalance() {
        try {
            File f = new File(username + ".txt");

            if (!f.exists()) {
                bal = 1000;
                saveBalance();
            } else {
                BufferedReader br = new BufferedReader(new FileReader(f));
                bal = Double.parseDouble(br.readLine());
                br.close();
            }
        } catch (Exception e) {
            bal = 1000;
        }
    }

    void saveBalance() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(username + ".txt"));
            bw.write(String.valueOf(bal));
            bw.close();
        } catch (Exception e) {
            System.out.println("Error saving");
        }
    }

    void saveHistory(String msg) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String time = LocalDateTime.now().format(dtf);

            BufferedWriter bw = new BufferedWriter(
                new FileWriter(username + "_his.txt", true)
            );

            bw.write(msg + " | " + time);
            bw.newLine();
            bw.close();

        } catch (Exception e) {
            System.out.println("Error history");
        }
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == deposit) {
            String amt = JOptionPane.showInputDialog("Enter amount");

            try {
                double a = Double.parseDouble(amt);
                bal += a;
                saveBalance();
                saveHistory("Deposited: " + a);
                JOptionPane.showMessageDialog(this, "Deposited");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input");
            }
        }

        if (e.getSource() == withdraw) {
            String amt = JOptionPane.showInputDialog("Enter amount");

            try {
                double a = Double.parseDouble(amt);

                if (a <= bal) {
                    bal -= a;
                    saveBalance();
                    saveHistory("Withdrawn: " + a);
                    JOptionPane.showMessageDialog(this, "Withdrawn");
                } else {
                    JOptionPane.showMessageDialog(this, "Low Balance");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input");
            }
        }

        if (e.getSource() == balance) {
            JOptionPane.showMessageDialog(this, "Balance = " + bal);
        }

        if (e.getSource() == history) {
            try {
                BufferedReader br = new BufferedReader(
                    new FileReader(username + "_his.txt")
                );

                String line, data = "";
                while ((line = br.readLine()) != null) {
                    data += line + "\n";
                }

                br.close();
                JOptionPane.showMessageDialog(this, data);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "No History");
            }
        }
    }
}
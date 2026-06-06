package form;

import javax.swing.*;

public class LoginFrame extends JFrame {

    JLabel lblTitle;
    JLabel lblUser;
    JLabel lblPass;

    JTextField txtUser;
    JPasswordField txtPass;

    JButton btnLogin;
    JButton btnKeluar;

    public LoginFrame() {

        setTitle("Login Kas Sekolah");
        setSize(450,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        lblTitle = new JLabel("LOGIN KAS SEKOLAH");
        lblTitle.setBounds(140,20,200,30);

        lblUser = new JLabel("Username");
        lblUser.setBounds(50,80,100,25);

        txtUser = new JTextField();
        txtUser.setBounds(150,80,200,25);

        lblPass = new JLabel("Password");
        lblPass.setBounds(50,120,100,25);

        txtPass = new JPasswordField();
        txtPass.setBounds(150,120,200,25);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(100,180,100,30);

        btnKeluar = new JButton("Keluar");
        btnKeluar.setBounds(220,180,100,30);

        add(lblTitle);
        add(lblUser);
        add(txtUser);
        add(lblPass);
        add(txtPass);
        add(btnLogin);
        add(btnKeluar);
    }
}
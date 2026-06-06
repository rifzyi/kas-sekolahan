// File: form/LoginFrame.java
package form;

import controller.AuthController;import java.awt.*;import javax.swing.*;import model.User;import util.SessionManager;import util.UIUtils;

public class LoginFrame extends JFrame {
    private final JTextField usernameField=UIUtils.createModernTextField("Username");
    private final JPasswordField passwordField=new JPasswordField();
    private final AuthController authController=new AuthController();
    public LoginFrame(){ setTitle("Login SKM"); setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(420,320); setLocationRelativeTo(null); JPanel root=new JPanel(new GridBagLayout()); root.setBackground(UIUtils.BACKGROUND); root.setBorder(BorderFactory.createEmptyBorder(15,15,15,15)); JPanel card=UIUtils.card(); card.setLayout(new GridLayout(0,1,8,8)); card.add(UIUtils.title("SKM Login")); card.add(UIUtils.label("Username")); card.add(usernameField); card.add(UIUtils.label("Password")); passwordField.setBorder(usernameField.getBorder()); card.add(passwordField); JButton login=UIUtils.createModernButton("Masuk",UIUtils.PRIMARY); login.addActionListener(e->login()); card.add(login); root.add(card); setContentPane(root); getRootPane().setDefaultButton(login); }
    private void login(){ try{ User user=authController.login(usernameField.getText().trim(), new String(passwordField.getPassword())); if(user==null){ JOptionPane.showMessageDialog(this,"Username/password salah","Login",JOptionPane.WARNING_MESSAGE); return;} SessionManager.getInstance().setCurrentUser(user); new MainFrame().setVisible(true); dispose(); }catch(Exception ex){ UIUtils.showError(this,ex);} }
}

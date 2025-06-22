package sn.Fama_Taha.view;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
     private JTextField loginField;
     private JPasswordField passwordField;
     private JButton loginButton;
     private JLabel errorMessage;

     public LoginView() {
         setTitle("Login");
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setSize(300, 200);
         setLocationRelativeTo(null);
         setLayout(new GridLayout(4, 2));

         loginField = new JTextField();
         passwordField = new JPasswordField();
         loginButton = new JButton("Login");
         errorMessage = new JLabel("");

         add(new JLabel("Login:"));
         add(loginField);
         add(new JLabel("Password:"));
         add(passwordField);
         add(loginButton);
         add(errorMessage);
     }
}
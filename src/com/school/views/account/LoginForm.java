package com.school.views.account;

import com.school.auth.CreateAccount;
import com.school.auth.SignIn;
import com.school.auth.ValidateInputs;
import com.school.views.Dashboard;
import com.school.views.Index;
import com.school.views.MainWindow;
import com.school.views.partials.helpers.AddPlaceholders;
import com.school.views.partials.helpers.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JPanel implements ActionListener {
    private final Index index = new Index();

    private java.awt.event.FocusEvent focusEvent;
    JTextField emailCreate = new JTextField();
    JTextField emailSignIn = new JTextField();
    JPasswordField passwordCreate = new JPasswordField();
    JPasswordField passwordSignIn = new JPasswordField();
    JTextField firstNameCreate = new JTextField();
    JTextField lastNameCreate = new JTextField();
    JButton createAcctBtn = new JButton();
    JButton signInBtn = new JButton();

    public LoginForm() {
        initComponents();
    }

    private void initComponents() {
        addPropertyChangeListener (e -> {if ("border" .equals (e .getPropertyName () )) throw new RuntimeException( ); });

        setLayout(new GridBagLayout());
        setBackground(Colors.middleBlue);

        ((GridBagLayout)getLayout()).columnWidths = new int[] {25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        emailCreate.setText("Email");
        new AddPlaceholders(emailCreate, focusEvent, "Email");
        add(emailCreate, new GridBagConstraints(3, 17, 8, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        emailSignIn.setText("Email");
        new AddPlaceholders(emailSignIn, focusEvent, "Email");
        add(emailSignIn, new GridBagConstraints(12, 20, 8, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        passwordCreate.setText("pppp");
        new AddPlaceholders(passwordCreate, focusEvent, "pppp");
        add(passwordCreate, new GridBagConstraints(3, 20, 8, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        passwordSignIn.setText("pppp");
        new AddPlaceholders(passwordSignIn, focusEvent, "pppp");
        add(passwordSignIn, new GridBagConstraints(12, 23, 8, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        firstNameCreate.setText("First name");
        new AddPlaceholders(firstNameCreate, focusEvent, "First name");
        add(firstNameCreate, new GridBagConstraints(3, 23, 8, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        lastNameCreate.setText("Last name");
        new AddPlaceholders(lastNameCreate, focusEvent, "Last name");
        add(lastNameCreate, new GridBagConstraints(3, 26, 8, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        createAcctBtn.setText("Create Account");
        createAcctBtn.addActionListener(this);
        add(createAcctBtn, new GridBagConstraints(3, 34, 8, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        signInBtn.setText("Sign In");
        signInBtn.addActionListener(this);
        add(signInBtn, new GridBagConstraints(12, 34, 8, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("Create Account")) {
            ValidateInputs inputs = new ValidateInputs(
                firstNameCreate.getText(),
                lastNameCreate.getText(),
                emailCreate.getText(),
                passwordCreate.getPassword()
            );

            if (inputs.validateRegister().isPresent()) {
                System.out.println("FAIL");
                return;
            }

            new CreateAccount().createAccount(inputs);
        } else if (action.equals("Sign In")) {
            ValidateInputs inputs = new ValidateInputs(
                emailSignIn.getText(),
                passwordSignIn.getPassword()
            );

            if (inputs.validateSignIn().isPresent()) {
                System.out.println("FAIL");
                return;
            }

            new SignIn().signIn(inputs);
        }

        MainWindow.WINDOW.getContentPane().removeAll();
        MainWindow.WINDOW.add(index.getHeaderLabel(), BorderLayout.PAGE_START);
        MainWindow.WINDOW.add(new Dashboard().displayDashboard(), BorderLayout.CENTER);
        MainWindow.WINDOW.add(index.getFooterLabel(), BorderLayout.PAGE_END);
        MainWindow.WINDOW.repaint();
        MainWindow.WINDOW.revalidate();
    }
}

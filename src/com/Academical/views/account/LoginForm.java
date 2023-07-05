package com.Academical.views.account;

import com.Academical.Launcher;
import com.Academical.api.auth.AuthenticateApi;
import com.Academical.api.auth.BearerTokenApi;
import com.Academical.auth.ValidateInputs;
import com.Academical.exceptions.RegisterUserException;
import com.Academical.exceptions.SignInUserException;
import com.Academical.exceptions.ValidateInputsException;
import com.Academical.views.Dashboard;
import com.Academical.views.Index;
import com.Academical.views.windows.MainWindow;
import com.Academical.views.partials.helpers.AddPlaceholders;
import com.Academical.views.partials.helpers.Colors;
import org.apache.logging.log4j.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginForm extends JPanel implements ActionListener {
    private final AuthenticateApi authenticateApi = new AuthenticateApi();
    private final BearerTokenApi bearerTokenApi = new BearerTokenApi();
    private final Index index = new Index(false);

    private java.awt.event.FocusEvent focusEvent;
    JTextField emailCreate = new JTextField();
    JTextField emailSignIn = new JTextField();
    JPasswordField passwordCreate = new JPasswordField();
    JPasswordField passwordSignIn = new JPasswordField();
    JTextField firstNameCreate = new JTextField();
    JTextField lastNameCreate = new JTextField();
    JButton createAcctBtn = new JButton();
    JButton signInBtn = new JButton();
    JLabel errorMessage = new JLabel();

    Timer errorMessageDisplayTimer = new Timer(10000, e -> errorMessage.setVisible(false));

    public LoginForm() {
        initComponents();
    }

    private void initComponents() {
        addPropertyChangeListener (e -> {if ("border" .equals (e .getPropertyName () )) throw new RuntimeException( ); });

        setLayout(new GridBagLayout());
        setBackground(Colors.MIDDLE_BLUE);

        ((GridBagLayout)getLayout()).columnWidths = new int[] {25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        emailCreate.setText("Email");
        new AddPlaceholders(emailCreate, focusEvent, "Email");
        add(emailCreate, new GridBagConstraints(3, 16, 8, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        emailSignIn.setText("Email");
        new AddPlaceholders(emailSignIn, focusEvent, "Email");
        add(emailSignIn, new GridBagConstraints(12, 19, 8, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        passwordCreate.setText("pppp");
        new AddPlaceholders(passwordCreate, focusEvent, "pppp");
        add(passwordCreate, new GridBagConstraints(3, 19, 8, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        passwordSignIn.setText("pppp");
        new AddPlaceholders(passwordSignIn, focusEvent, "pppp");
        add(passwordSignIn, new GridBagConstraints(12, 22, 8, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        firstNameCreate.setText("First name");
        new AddPlaceholders(firstNameCreate, focusEvent, "First name");
        add(firstNameCreate, new GridBagConstraints(3, 22, 8, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        lastNameCreate.setText("Last name");
        new AddPlaceholders(lastNameCreate, focusEvent, "Last name");
        add(lastNameCreate, new GridBagConstraints(3, 25, 8, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        createAcctBtn.setText("Create Account");
        createAcctBtn.addActionListener(this);
        add(createAcctBtn, new GridBagConstraints(3, 35, 8, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        signInBtn.setText("Sign In");
        signInBtn.addActionListener(this);
        add(signInBtn, new GridBagConstraints(12, 35, 8, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        errorMessage.setFont(new Font("Roboto", Font.ITALIC, 14));
        errorMessage.setHorizontalAlignment(JLabel.CENTER);
        errorMessage.setForeground(Color.RED);
        errorMessage.setVisible(false);
        add(errorMessage, new GridBagConstraints(7, 32, 8, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String action = event.getActionCommand();
        if (action.equals("Create Account")) {
            ValidateInputs inputs = new ValidateInputs(
                firstNameCreate.getText(),
                lastNameCreate.getText(),
                emailCreate.getText(),
                passwordCreate.getPassword()
            );

            try {
                inputs.validateRegister();
                authenticateApi.registerUser(inputs);
            } catch (ValidateInputsException e) {
                Launcher.logAll(Level.TRACE, e);
                displayErrorMessage("Account inputs invalid");
                return;
            } catch (RegisterUserException | IOException e) {
                Launcher.logAll(Level.TRACE, e);
                displayErrorMessage("Failed to create account");
                return;
            }
        } else if (action.equals("Sign In")) {
            ValidateInputs inputs = new ValidateInputs(
                emailSignIn.getText(),
                passwordSignIn.getPassword()
            );

            try {
                inputs.validateSignIn();
                bearerTokenApi.getBearerByCreds(inputs.email, inputs.password);
            } catch (ValidateInputsException e) {
                Launcher.logAll(Level.TRACE, e);
                displayErrorMessage("Account inputs invalid");
                return;
            } catch (IOException | SignInUserException e) {
                Launcher.logAll(Level.TRACE, e);
                displayErrorMessage("Failed to sign user in");
                return;
            }
        }

        showMainWindow();
    }

    public void showMainWindow() {
        Index index = new Index(true, true);
        MainWindow.WINDOW.getContentPane().removeAll();
        MainWindow.WINDOW.add(index.getHeaderLabel(), BorderLayout.PAGE_START);
        MainWindow.WINDOW.add(new Dashboard(), BorderLayout.CENTER);
        MainWindow.WINDOW.add(index.getFooterLabel(), BorderLayout.PAGE_END);
        MainWindow.WINDOW.repaint();
        MainWindow.WINDOW.revalidate();
    }

    public void displayErrorMessage(String message) {
        errorMessage.setText(message);
        errorMessage.setVisible(true);
        errorMessageDisplayTimer.start();
    }
}

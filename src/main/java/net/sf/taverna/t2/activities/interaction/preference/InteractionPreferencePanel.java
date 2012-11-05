/**
 * 
 */
package net.sf.taverna.t2.activities.interaction.preference;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.sf.taverna.t2.activities.interaction.jetty.InteractionJetty;
import net.sf.taverna.t2.security.credentialmanager.CMException;
import net.sf.taverna.t2.security.credentialmanager.CredentialManager;
import net.sf.taverna.t2.workbench.helper.Helper;
import net.sf.taverna.t2.workbench.ui.credentialmanager.password.GetPasswordDialog;

import org.apache.log4j.Logger;

/**
 * @author alanrw
 *
 */
public class InteractionPreferencePanel extends JPanel {
	
	private static Logger logger = Logger.getLogger(InteractionPreferencePanel.class);
	
	private static InteractionPreference pref = InteractionPreference.getInstance();
	
	private String hostCache = pref.getHost();
	private String feedPathCache = pref.getFeedPath();
	private String webDavPathCache = pref.getWebDavPath();
	
	private JCheckBox useJettyField;
	private JTextField portField;
	private JTextField hostField;
	private JTextField feedPathField;
	private JTextField webDavPathField;
	private JCheckBox useUsernameField;
//	private JCheckBox useHttpsField;
	
	/**
	 * The size of the field for the JTextFields.
	 */
	private static int TEXTFIELD_SIZE = 25;

	public InteractionPreferencePanel() {
		super();
		initComponents();
		setFields();
	}
	
	private void initComponents() {
		JPanel everything = new JPanel();
		everything.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		
		// Title describing what kind of settings we are configuring here
        JTextArea descriptionText = new JTextArea("Interaction preference");
        descriptionText.setLineWrap(true);
        descriptionText.setWrapStyleWord(true);
        descriptionText.setEditable(false);
        descriptionText.setFocusable(false);
        descriptionText.setBorder(new EmptyBorder(10, 10, 10, 10));
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        everything.add(descriptionText, gbc);
        
		useJettyField = new JCheckBox("Use internal Jetty");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10,0,0,0);
        everything.add(useJettyField, gbc);
        useJettyField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateSelectability();
			}});
        
        useUsernameField = new JCheckBox("Secure with usename and password");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10,0,0,0);
        everything.add(useUsernameField, gbc);
       
/*        useHttpsField = new JCheckBox("Secure communication with HTTPS");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10,0,0,0);
        everything.add(useHttpsField, gbc);
*/       
        hostField = new JTextField(TEXTFIELD_SIZE);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10,0,0,0);
        everything.add(new JLabel("Host"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        everything.add(hostField, gbc);
        
		portField = new JTextField(TEXTFIELD_SIZE);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10,0,0,0);
        everything.add(new JLabel("Port"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        everything.add(portField, gbc);
        
		feedPathField = new JTextField(TEXTFIELD_SIZE);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10,0,0,0);
        everything.add(new JLabel("Feed Path"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        everything.add(feedPathField, gbc); 
        
		webDavPathField = new JTextField(TEXTFIELD_SIZE);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10,0,0,0);
        everything.add(new JLabel("WebDav Path"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        everything.add(webDavPathField, gbc);
        
        // Add buttons panel
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0);
        everything.add(createButtonPanel(), gbc);
        
        this.setLayout(new BorderLayout());
        this.add(everything, BorderLayout.NORTH);
	}
	
	/**
	 * Create the panel to contain the buttons
	 * 
	 * @return
	 */
	@SuppressWarnings("serial")
	private JPanel createButtonPanel() {
		final JPanel panel = new JPanel();

		/**
		 * The helpButton shows help about the current component
		 */
		JButton helpButton = new JButton(new AbstractAction("Help") {
			public void actionPerformed(ActionEvent arg0) {
				Helper.showHelp(panel);
			}
		});
		panel.add(helpButton);

		/**
		 * The resetButton changes the property values shown to those
		 * corresponding to the configuration currently applied.
		 */
		JButton resetButton = new JButton(new AbstractAction("Reset") {
			public void actionPerformed(ActionEvent arg0) {
				setFields();
			}
		});
		panel.add(resetButton);

		/**
		 * The applyButton applies the shown field values to the
		 * {@link HttpProxyConfiguration} and saves them for future.
		 */
		JButton applyButton = new JButton(new AbstractAction("Apply") {
			public void actionPerformed(ActionEvent arg0) {
				applySettings();
				setFields();
			}
		});
		panel.add(applyButton);

		return panel;
	}

	protected void applySettings() {
		pref.setUseJetty(useJettyField.isSelected());
		pref.setHost(hostField.getText());
		hostCache = hostField.getText();
		pref.setFeedPath(feedPathField.getText());
		feedPathCache = feedPathField.getText();
		pref.setWebDavPath(webDavPathField.getText());
		webDavPathCache = webDavPathField.getText();
		if (pref.getUseJetty()) {
			if (useUsernameField.isSelected() && (!pref.getUseUsername() || !pref.getPort().equals(portField.getText()))) {
				try {
					URI serviceURI = InteractionJetty.createServiceURI(portField.getText());
					
					CredentialManager credMan = CredentialManager.getInstance();
					if (!credMan.hasUsernamePasswordForService(serviceURI)) {
						GetPasswordDialog getPasswordDialog = new GetPasswordDialog("Please enter the username and password\nto secure the interaction",
								true);
						getPasswordDialog.setLocationRelativeTo(null);
						getPasswordDialog.setVisible(true);
						
						String username = getPasswordDialog.getUsername();
						String password = getPasswordDialog.getPassword();
						credMan.saveUsernameAndPasswordForService(username, password, serviceURI.toString());
					}
				} catch (URISyntaxException e) {
					logger.error(e);
				} catch (CMException e) {
					logger.error(e);
				}
			}
		}
		pref.setPort(portField.getText());
		pref.setUseUsername(useUsernameField.isSelected());
//		pref.setUseHttps(useHttpsField.isSelected());
		pref.store();
		JOptionPane.showMessageDialog(null, "The changes will not take effect until Taverna is restarted",
				"Interaction Preference", JOptionPane.WARNING_MESSAGE);
	}

	protected void setFields() {
		useJettyField.setSelected(pref.getUseJetty());
		portField.setText(pref.getPort());
		hostField.setText(pref.getHost());
		feedPathField.setText(pref.getFeedPath());
		webDavPathField.setText(pref.getWebDavPath());
		useUsernameField.setSelected(pref.getUseUsername());
//		useHttpsField.setSelected(pref.getUseHttps());
		updateSelectability();
	}

	private void updateSelectability() {
		if (useJettyField.isSelected()) {
			hostCache = hostField.getText();
			hostField.setText(pref.getDefaultHost());
			hostField.setEnabled(false);
			
			feedPathCache = feedPathField.getText();
			feedPathField.setText(pref.getDefaultFeedPath());
			feedPathField.setEnabled(false);
			
			webDavPathCache = webDavPathField.getText();
			webDavPathField.setText(pref.getDefaultWebDavPath());
			webDavPathField.setEnabled(false);
			
			useUsernameField.setEnabled(true);
			
//			useHttpsField.setEnabled(true);
						
		} else {
			hostField.setText(hostCache);
			hostField.setEnabled(true);
			
			feedPathField.setText(feedPathCache);
			feedPathField.setEnabled(true);
			
			webDavPathField.setText(webDavPathCache);
			webDavPathField.setEnabled(true);
			
			useUsernameField.setEnabled(false);
//			useHttpsField.setEnabled(false);
		}
	}
	
	
}

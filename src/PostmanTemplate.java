import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class PostmanTemplate implements ActionListener {

	
	JTextField collectionNameField;
	JRadioButton dev, test, staging, demo, qa, qa2, prod, dis_lookup_dev, localhost;
	JButton chooseFile;
	ButtonGroup envs;
	GroupButtonUtils buttonUtils;
	JTextField portField;
	
	String inputFilePath, collectionName, backendAPI;
	boolean minifyOutput;

    public static void main (String [] args) {
    	PostmanTemplate gui = new PostmanTemplate();
        gui.go();
    }

    public void go() {
    	
    	buttonUtils = new GroupButtonUtils();
    	JFrame frame = new JFrame("Postman Collection Generator");
    	
    	
    	JPanel collectionNamePanel = new JPanel(new GridBagLayout());
    	
    	JPanel topPanel = new JPanel();
    	JPanel middlePanel = new JPanel();
    	JPanel bottomPanel = new JPanel();
    	
    	JPanel groupButtonPanel = new JPanel();
    	JLabel groupButtonLabel = new JLabel("Please choose your backend enviroment.");    	
    	
    	dev = new JRadioButton("dev");
    	test = new JRadioButton("test");
    	staging = new JRadioButton("staging");
    	demo = new JRadioButton("demo");
    	qa = new JRadioButton("qa");
    	qa2 = new JRadioButton("qa2");
    	prod = new JRadioButton("prod");
    	dis_lookup_dev = new JRadioButton("dis_lookup_dev");
    	localhost = new JRadioButton("localhost");
    	
    	envs = new ButtonGroup();
    	envs.add(dev);
    	envs.add(test);
    	envs.add(staging);
    	envs.add(demo);
    	envs.add(qa);
    	envs.add(qa2);
    	envs.add(prod);
    	envs.add(dis_lookup_dev);
    	envs.add(localhost);
    	
    	groupButtonPanel.add(dev);
    	groupButtonPanel.add(test);
    	groupButtonPanel.add(staging);
    	groupButtonPanel.add(staging);
    	groupButtonPanel.add(qa);
    	groupButtonPanel.add(qa2);
    	groupButtonPanel.add(prod);
    	groupButtonPanel.add(dis_lookup_dev);
    	groupButtonPanel.add(localhost);
    	JLabel portLabel = new JLabel(":");
    	portField = new JTextField("3000");
    	portField.setPreferredSize(new Dimension(50, 22));
    	portField.setEditable(false);
    	groupButtonPanel.add(portLabel);
    	groupButtonPanel.add(portField);
    	
    	localhost.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (localhost.isSelected()) {
					portField.setEditable(true);
				}
			}
		});
    	
    	
    	
    	chooseFile = new JButton("Choose HAR file.");
    	topPanel.add(BorderLayout.CENTER, chooseFile);
    	chooseFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
				    System.out.println(selectedFile.getAbsolutePath());
				}
			}
		});
    	
    	JCheckBox minOutput = new JCheckBox("Minify output file.");
    	bottomPanel.add(BorderLayout.WEST, minOutput);
    	
    	JLabel label = new JLabel("Please enter the collection name. This will be the name of your Postman collection.");
    	collectionNameField = new JTextField("i.e. schoolmint-qa");
    	collectionNameField.setPreferredSize(new Dimension(150, 22));
    	
    	JButton start = new JButton("Start!");
    	bottomPanel.add(BorderLayout.EAST, start);
    	
    	
    	
    	start.addActionListener(this);
    	
    	GridBagConstraints gbc = new GridBagConstraints();
    	gbc.insets = new Insets(3, 3, 3, 3);
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	collectionNamePanel.add(label, gbc);
    	
    	gbc.gridx = 0;
    	gbc.gridy = 1;    	
    	collectionNamePanel.add(collectionNameField, gbc);
    	
    	
    	middlePanel.add(BorderLayout.NORTH, collectionNamePanel);
    	middlePanel.add(BorderLayout.CENTER, groupButtonLabel);
    	middlePanel.add(BorderLayout.SOUTH, groupButtonPanel);
    	
    	frame.getContentPane().add(BorderLayout.NORTH, topPanel);
    	frame.getContentPane().add(BorderLayout.CENTER, middlePanel);
    	frame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);
    	frame.setSize(750,220);
    	frame.setVisible(true);
    	frame.setResizable(false);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	

    }

    @Override
    public void actionPerformed(ActionEvent event) {
		JOptionPane.showMessageDialog(null, buttonUtils.getSelectedButtonText(envs));
		String collectionName = collectionNameField.getText();
    }
}


package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Display implements ActionListener {
    JFrame frame;
    JPanel panel = new JPanel();
    JButton cancel = new JButton("Kopla ner"); // Button to cancel/exit
    JTextArea area = new JTextArea(20, 40); // Text area to display messages
    JTextField text = new JTextField(40); // Text field for user input
    JScrollPane sp = new JScrollPane(area, // Scroll pane to provide scrollbars for the text area
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    String c_name, message;
    ObjectOutputStream server_write;

    public Display(ObjectOutputStream server_write) {
        this.server_write = server_write;

        c_name = JOptionPane.showInputDialog("User name:"); // Prompt user for name

        frame = new JFrame(c_name);
        frame.add(panel);

        panel.setLayout(new BorderLayout());
        panel.add(cancel, BorderLayout.NORTH); // Add cancel button to the top of the panel
        panel.add(sp, BorderLayout.CENTER); // Add scroll pane to the center of the panel
        panel.add(text, BorderLayout.SOUTH); // Add text field to the bottom of the panel

        frame.pack(); // Pack the frame to fit its contents

        frame.setLocation(50, 60); // Set the frame's location on the screen
        frame.setSize(400, 400); // Set the frame's size

        frame.setVisible(true); // Make the frame visible
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Close the application when the frame is closed

        cancel.addActionListener(this); // Add ActionListener to the cancel button
        text.addActionListener(this); // Add ActionListener to the text field
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancel) {
            System.exit(0); // Exit the application if cancel button is clicked
        }

        message = text.getText(); // Get the text from the text field
        try {
            server_write.writeObject(message); // Write the message to the server
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        text.setText(""); // Clear the text field
    }
}


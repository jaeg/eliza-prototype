package Eliza.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Eliza.ai.Eliza;

import com.gtranslate.Audio;
import com.gtranslate.Language;


public class ElizaGUI extends JPanel
{

	private static final long serialVersionUID = 1L;

	private JTextField inputField;
	private JTextArea chatLog;
	private JScrollPane chatLogPane;

	private Eliza eliza;


	public static void main(String[] args)
	{
		JFrame frame = new JFrame("ELIZA");
		frame.getContentPane().add(new ElizaGUI());
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public ElizaGUI()
	{	
		
		eliza = new Eliza();
		
		this.setBorder(new EmptyBorder(40, 40, 40, 40));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{	
				//sayIt(inputField.getText());
				String response = eliza.response(inputField.getText());
				chatLog.append("\nME: "+inputField.getText()+"\nELIZA: "+ response);
				sayIt(response.toLowerCase());
				inputField.setText("");
			}
		});


		chatLog = new JTextArea(5, 30);
		chatLogPane = new JScrollPane(chatLog);
		chatLogPane.setPreferredSize(new Dimension(450, 110));
		
		inputField = new JTextField(4);
		setAlignmentX(0.5f);
		add(new JLabel("Chat Log:"));
		add(chatLogPane);
		add(new JLabel("I say:"));
		add(inputField);
		add(submitButton);

	}
	
	/**
	 * Use Google Translate to say the given string
	 * @param message The message to say
	 */
	public void sayIt(String message)
	{
		Audio audio = Audio.getInstance();
		
		String messageChunks[] = message.split("[{,}{.}]+");
		
		try
		{
			for (String chunk: messageChunks)
			{
				audio.play(audio.getAudio(chunk, Language.ENGLISH));
			}
		}
		
		
		catch (Exception ex)
		{
			System.out.println(ex);
		}
	}
}
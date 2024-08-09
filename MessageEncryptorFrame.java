package za.ac.tut.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
/**
 *
 * @author Alex
 */
public class MessageEncryptorFrame extends JFrame
{
    private JPanel mainPnel;
    private JPanel areaPnel;
    
    private JMenu manuName;
    private JMenuBar itemHolder;
    
    private JMenuItem openFile;
    private JMenuItem encrypMessage;
    private JMenuItem saveEncrMessage;
    private JMenuItem clear;
    private JMenuItem exit;
    
    private JLabel heading;
    
    private JTextArea plainMessageArea;
    private JTextArea encrypedMessageArea;
    
    private JScrollPane scroll;
    private JScrollPane scrolls;
    
    public MessageEncryptorFrame()
    {
        setSize(800,500);
        setTitle("Secure Message");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //initializing 
        areaPnel = new JPanel(new FlowLayout());
        manuName = new JMenu("File");
        
        //creating menu
        itemHolder = new JMenuBar();
        mainPnel = new JPanel(new FlowLayout());
       openFile = new JMenuItem("Open file...");
        encrypMessage = new JMenuItem("Encrypt message...");
        saveEncrMessage = new JMenuItem("Save encrypted message...");
        clear = new JMenuItem("Clear");
        exit = new JMenuItem("Exit");
        
        //adding action listeners
        openFile.addActionListener(new encryptedMessages());
        encrypMessage.addActionListener(new encryptedMessages());
        saveEncrMessage.addActionListener(new encryptedMessages());
        clear.addActionListener(new encryptedMessages());
        exit.addActionListener(new encryptedMessages());
        
        //Label
        heading = new JLabel("Message Encryptor");
        heading.setFont(new Font(Font.SANS_SERIF,Font.ITALIC+Font.BOLD,30));
        heading.setForeground(Color.BLUE);
        heading.setBorder(new BevelBorder(BevelBorder.RAISED));
        
        //creating area
        plainMessageArea = new JTextArea(20,40);
        encrypedMessageArea = new JTextArea(20,40);
        
        // added scroll pane to plainMessageArea
        scroll = new JScrollPane(plainMessageArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBorder(new TitledBorder(new LineBorder(Color.BLACK,2),"Plain message"));
        
        // added scroll pane to encrypedMessageArea
        scrolls = new JScrollPane(encrypedMessageArea);
        scrolls.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrolls.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrolls.setBorder(new TitledBorder(new LineBorder(Color.BLACK,2),"Encrypted message"));
        
        //adding to the menu
        manuName.add(openFile);
        manuName.add(encrypMessage);
        manuName.add(saveEncrMessage);
        manuName.add(clear);
        manuName.add(exit);
        itemHolder.add(manuName);
        setJMenuBar(itemHolder);
       
        areaPnel.add(scroll);
        areaPnel.add(scrolls);
        
        mainPnel.add(areaPnel);
        
        add(heading);
        add(mainPnel);
        setVisible(true);
    }
    
    private class encryptedMessages implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(e.getSource() == openFile)
            {
                JFileChooser fileSaver = new JFileChooser();
                int results = fileSaver.showOpenDialog(MessageEncryptorFrame.this);
                if(results == JFileChooser.APPROVE_OPTION)
                {
                    File file = fileSaver.getSelectedFile();
                    try (BufferedReader read = new BufferedReader(new FileReader(file))) 
                    {
                        plainMessageArea.read(read, "Plain message");
                    } 
                    catch (IOException ex)
                    {
                        JOptionPane.showMessageDialog(MessageEncryptorFrame.this, ex.getMessage());
                    }
                }
            }
            else if(e.getSource() == encrypMessage)
            {
                String plainMessage = plainMessageArea.getText();
                if(!plainMessage.isEmpty())
                {
                    String encryptedMessage = WeDoSecureApp(plainMessage, 3);
                    encrypedMessageArea.setText(encryptedMessage);
                }
            }
            else if (e.getSource() == saveEncrMessage) 
            {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showSaveDialog(MessageEncryptorFrame.this);
                if (result == JFileChooser.APPROVE_OPTION) 
                {
                    File file = fileChooser.getSelectedFile();
                    try (FileWriter writer = new FileWriter(file)) 
                    {
                        encrypedMessageArea.write(writer);
                    } 
                    catch (IOException ex) 
                    {
                        JOptionPane.showMessageDialog(MessageEncryptorFrame.this, ex.getMessage());
                    }
                }
            }
            else if (e.getSource() == clear) 
            {
                plainMessageArea.setText("");
                encrypedMessageArea.setText("");
            }
            else if (e.getSource() == exit) 
            {
                System.exit(0);
            } 
        }
    }

        private String WeDoSecureApp(String message, int shift)
        {
            String encryptedText = "";
            shift = shift % 26 + 26;
            for (char i : message.toCharArray()) 
            {
                if (Character.isLetter(i))
                {
                    if (Character.isUpperCase(i))
                    {
                        encryptedText += (char) ('A' + (i - 'A' + shift) % 26);
                    } 
                    else
                    {
                        encryptedText += (char) ('a' + (i - 'a' + shift) % 26);
                    }
                }
                else 
                {
                    encryptedText += i;
                }
            }
            return encryptedText;
        }
}
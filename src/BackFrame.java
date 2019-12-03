

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class BackFrame extends JFrame {
	private JPanel contentPane;
	private JTextField nameTextField;
	private DataBackup dataBackup = new DataBackup();
	private JComboBox dataBaseComboBox;
	private JFileChooser fileChooser = new JFileChooser(new File("."));
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					BackFrame frame = new BackFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		File file = new File("db-default.properties");
		if(file.exists()==false) {
			ConfigUtil.propertyOut(file);
		}
	}
    /**
     * Create the frame.
     */
    public BackFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 250);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu fileMenu = new JMenu("�����ļ�");
        fileMenu.setFont(new Font("΢���ź�", Font.PLAIN, 16));
        menuBar.add(fileMenu);
        
        JMenuItem openMenuItem = new JMenuItem("��");
        openMenuItem.setFont(new Font("΢���ź�", Font.PLAIN, 16));
        fileMenu.add(openMenuItem);
        openMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FileFilter filter = new FileNameExtensionFilter("�����ļ���properties��", "properties");// �����ļ�������				
				fileChooser.setFileFilter(filter);
				fileChooser.showOpenDialog(getContentPane());// ��ʾ�ļ�ѡ��Ի���
		        List list = dataBackup.getDatabases(ConfigUtil.getProperties(fileChooser));
		        for(int i = 0;i<list.size();i++){
		        	dataBaseComboBox.addItem(list.get(i));
		        }
			}
		});
        
//        JMenuItem openMenuItem = new JMenuItem("Ĭ��");
//        openMenuItem.setFont(new Font("΢���ź�", Font.PLAIN, 16));
//        fileMenu.add(openMenuItem);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setTitle("MySQL���ݿⱸ��");
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 434, 196);
        contentPane.add(panel);
        panel.setLayout(null);
        
        JLabel messageLabel = new JLabel("ѡ����Ҫ���ݵ����ݿ⣺");
        messageLabel.setBounds(37, 39, 148, 15);
        panel.add(messageLabel);
//        List list = dataBackup.getDatabase();
//        String[] daName = new String[list.size()];
//        for(int i = 0;i<list.size();i++){
//            daName[i] = list.get(i).toString();
//        }
        dataBaseComboBox  = new JComboBox();//daName);
        dataBaseComboBox.setBounds(182, 36, 187, 21);
        panel.add(dataBaseComboBox);
        
        JLabel backLabel = new JLabel("�����ļ��������ƣ�");
        backLabel.setBounds(62, 85, 117, 15);
        panel.add(backLabel);
        
        nameTextField = new JTextField();
        nameTextField.setBounds(182, 82, 187, 21);
        panel.add(nameTextField);
        nameTextField.setColumns(10);
        
        JButton backButton = new JButton("����");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                do_backButton_actionPerformed(arg0);
            }
        });
        backButton.setBounds(171, 141, 93, 23);
        panel.add(backButton);

    }
	//���ݰ�ť�ĵ����¼�
	protected void do_backButton_actionPerformed(ActionEvent agr0) {
		String dataBase = dataBaseComboBox.getSelectedItem().toString();
		String name = nameTextField.getText();
		if(!dataBase.equals("") && !name.equals("")) {
			dataBackup.mysqldump(dataBase,"D:\\"+name);
		}
		JOptionPane.showMessageDialog(getContentPane(), "���ݱ��ݳɹ���","��Ϣ��ʾ��",JOptionPane.WARNING_MESSAGE);
	}
}

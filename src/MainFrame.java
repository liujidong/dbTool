import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.HeadlessException;
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
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainFrame extends JFrame {
	private JFileChooser fileChooser = new JFileChooser(new File("."));
	private JComboBox dataBaseComboBox1;
    private JTextField fileTextField1;
    private JTextField fileTextField2;
	private JComboBox dataBaseComboBox2;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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

	public MainFrame() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 250);
        setTitle("MySQL数据库工具");
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu fileMenu = new JMenu("配置文件");
        fileMenu.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        menuBar.add(fileMenu);
        
        JMenuItem openMenuItem = new JMenuItem("打开");
        openMenuItem.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        fileMenu.add(openMenuItem);
        openMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FileFilter filter = new FileNameExtensionFilter("配置文件（properties）", "properties");// 设置文件过滤器				
				fileChooser.setFileFilter(filter);
				fileChooser.showOpenDialog(getContentPane());// 显示文件选择对话框
		        List list = DBUtil.getDatabases(ConfigUtil.getProperties(fileChooser));
		        
		        dataBaseComboBox1.removeAll();
		        for(int i = 0;i<list.size();i++){
		        	dataBaseComboBox1.addItem(list.get(i));
		        }
		        dataBaseComboBox2.removeAll();
		        dataBaseComboBox2.addItem("直接恢复");
		        for(int i = 0;i<list.size();i++){
		        	dataBaseComboBox2.addItem(list.get(i));
		        }
			}
		});
        
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("备份", initPane1());
        tabs.addTab("恢复", initPane2());
        setContentPane(tabs);
	}
	protected JPanel initPane1() {
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setBounds(0, 0, 434, 196);
        panel.setLayout(null);
        
        JLabel messageLabel = new JLabel("选择需要备份的数据库：");
        messageLabel.setBounds(37, 39, 148, 15);
        panel.add(messageLabel);
//        List list = dataBackup.getDatabase();
        String[] daName = new String[]{"请选择配置文件"};
//        for(int i = 0;i<list.size();i++){
//            daName[i] = list.get(i).toString();
//        }
        dataBaseComboBox1  = new JComboBox(daName);
        dataBaseComboBox1.setBounds(182, 36, 187, 21);
        panel.add(dataBaseComboBox1);
        
        JLabel backLabel = new JLabel("备份文件保存名称：");
        backLabel.setBounds(62, 85, 117, 15);
        panel.add(backLabel);
        
        fileTextField1 = new JTextField();
        fileTextField1.setBounds(182, 82, 187, 21);
        panel.add(fileTextField1);
        fileTextField1.setColumns(10);
        
        JButton backButton = new JButton("备份");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                do_backButton_actionPerformed(arg0);
            }
        });
        backButton.setBounds(171, 141, 93, 23);
        panel.add(backButton);
        return panel;
	}
	//备份按钮的单击事件
	protected void do_backButton_actionPerformed(ActionEvent agr0) {
		String dataBase = dataBaseComboBox1.getSelectedItem().toString();
		String name = fileTextField1.getText();
		if(!dataBase.equals("请选择配置文件") && !name.equals("")) {
			DBUtil.mysqldump(dataBase,"D:\\"+name);
			JOptionPane.showMessageDialog(getContentPane(), "数据备份成功！","信息提示框",JOptionPane.WARNING_MESSAGE);
		}
	}
	protected JPanel initPane2() {
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setBounds(0, 0, 434, 139);
        panel.setLayout(null);
        
        JLabel fileLabel = new JLabel("备份文件：");
        fileLabel.setBounds(48, 43, 67, 15);
        panel.add(fileLabel);
        
        fileTextField2 = new JTextField();
        fileTextField2.setBounds(125, 40, 174, 21);
        panel.add(fileTextField2);
        fileTextField2.setColumns(10);
        
        JButton browseButton = new JButton("浏览");
        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                do_browseButton_actionPerformed(arg0);
            }
        });
        browseButton.setBounds(320, 39, 74, 23);
        panel.add(browseButton);
        
        JLabel databaseLabel = new JLabel("恢复数据库：");
        databaseLabel.setBounds(36, 89, 80, 15);
        panel.add(databaseLabel);
//        List list = util.getDatabase();
        String name[] = new String[]{"请选择配置文件"};//[list.size()];
//        for (int i = 0; i < list.size(); i++) {
//            name[i] = (String) list.get(i);
//        }
        dataBaseComboBox2 = new JComboBox(name);
        dataBaseComboBox2.setBounds(125, 86, 174, 21);
        panel.add(dataBaseComboBox2);
        
        JButton resumeButton = new JButton("恢复");
        resumeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                do_resumeButton_actionPerformed(arg0);
            }
        });
        resumeButton.setBounds(320, 85, 74, 23);
        panel.add(resumeButton);
        return panel;
	}
    // 浏览按钮的单击处理事件
    protected void do_browseButton_actionPerformed(ActionEvent arg0) {
        java.awt.FileDialog fd = new FileDialog(this);
        fd.setVisible(true);
        fileTextField2.setText(fd.getDirectory() + fd.getFile());        
    } 
    // 恢复按钮的单击事件
    protected void do_resumeButton_actionPerformed(ActionEvent arg0) {        
        String fileName = fileTextField2.getText();
        String dataName = dataBaseComboBox2.getSelectedItem().toString();
        if (!fileName.equals("") && (!dataName.equals("请选择配置文件"))) {
            boolean bool = DBUtil.mysqlresume(dataName, fileName);
            JOptionPane.showMessageDialog(getContentPane(), 
                    "数据恢复成功！", "信息提示框", JOptionPane.WARNING_MESSAGE);
        }
    }
}

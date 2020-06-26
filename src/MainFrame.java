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
		File file = new File(ConfigUtil.DB_DEFAULT);
		if(file.exists()==false) {
			ConfigUtil.propertyOut(file);
		}
	}

	public MainFrame() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(600, 500, 450, 300);
        //setLocationRelativeTo(null);  
        setTitle("MySQL数据库工具");
        //---------菜单部分--------------
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        //配置文件>打开
        JMenu fileMenu = new JMenu("配置文件");
        fileMenu.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        menuBar.add(fileMenu);
        
        JMenuItem openMenuItem = new JMenuItem("打开");
        openMenuItem.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        fileMenu.add(openMenuItem);
        //------------菜单事件----------------
        openMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FileFilter filter = new FileNameExtensionFilter("配置文件（properties）", "properties");// 设置文件过滤器				
				fileChooser.setFileFilter(filter);
				fileChooser.showOpenDialog(getContentPane());// 显示文件选择对话框
				if(fileChooser.getSelectedFile()==null) {return;}
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
        //-------------tab页------------------
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("备份", initPane1());
        tabs.addTab("恢复", initPane2());
        setContentPane(tabs);
	}
	//页面布局：
	//	选择需要备份的数据库：【请选择配置文件 】
	//  备份文件保存路径：	  【                                     】
	//               【备份】
	protected JPanel initPane1() {
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setBounds(0, 0, 434, 196);
        panel.setLayout(null);
        
        JLabel messageLabel = new JLabel("选择需要备份的数据库：");
        messageLabel.setBounds(37, 39, 148, 15);
        panel.add(messageLabel);
        List list = DBUtil.getDatabases();
        String[] daName = new String[list.size()];
        for(int i = 0;i<list.size();i++){
      	  daName[i] = list.get(i).toString();
        }
        dataBaseComboBox1  = new JComboBox(daName);
        dataBaseComboBox1.setBounds(182, 36, 187, 21);
        panel.add(dataBaseComboBox1);
        
        JLabel backLabel = new JLabel("备份文件保存路径：");
        backLabel.setBounds(62, 85, 117, 15);
        panel.add(backLabel);
        
        fileTextField1 = new JTextField();
        fileTextField1.setBounds(182, 82, 187, 21);
        panel.add(fileTextField1);
        fileTextField1.setColumns(10);
        
        JButton backButton = new JButton("备份");
        backButton.setBounds(171, 141, 93, 23);
        panel.add(backButton);
        //----------添加事件处理------------
        dataBaseComboBox1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("dataBaseComboBox1 changed!");
				fileTextField1.setText("D:\\"+dataBaseComboBox1.getSelectedItem()+".sql");
			}
		});
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                do_backButton_actionPerformed(arg0);
            }
        });
        return panel;
	}
	//备份按钮的单击事件
	protected void do_backButton_actionPerformed(ActionEvent agr0) {
		String dataBase = dataBaseComboBox1.getSelectedItem().toString();
		String path = fileTextField1.getText();
		if(!dataBase.equals(DBUtil.TIP_DEFAULT) && !path.equals("")) {
			DBUtil.mysqldump(dataBase,path);
			JOptionPane.showMessageDialog(getContentPane(), "数据备份成功！","信息提示框",JOptionPane.WARNING_MESSAGE);
		}
	}
	//页面布局：
	//	备份文件：【                                        】【浏览】
	//  恢复数据库：	  【 请选择配置文件  】【恢复】
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
        browseButton.setBounds(320, 39, 74, 23);
        panel.add(browseButton);
        
        JLabel databaseLabel = new JLabel("恢复数据库：");
        databaseLabel.setBounds(36, 89, 80, 15);
        panel.add(databaseLabel);
        List list = DBUtil.getDatabases();
        String[] names = new String[list.size()];
        for(int i = 0;i<list.size();i++){
        	names[i] = list.get(i).toString();
        }
        dataBaseComboBox2 = new JComboBox(names);
        dataBaseComboBox2.setBounds(125, 86, 174, 21);
        panel.add(dataBaseComboBox2);
        
        JButton resumeButton = new JButton("恢复");
        resumeButton.setBounds(320, 85, 74, 23);
        panel.add(resumeButton);
        //----------事件处理------------------
        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                do_browseButton_actionPerformed(arg0);
            }
        });
        resumeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                do_resumeButton_actionPerformed(arg0);
            }
        });
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
        if (!fileName.equals("") && (!dataName.equals(DBUtil.TIP_DEFAULT))) {
            boolean bool = DBUtil.mysqlresume(dataName, fileName);
        	String msg = "数据恢复成功！";
            if(bool==false) {
            	msg = "数据恢复失败！";
            }
        	JOptionPane.showMessageDialog(getContentPane(), msg, "信息提示框", JOptionPane.WARNING_MESSAGE);
        }
    }
}

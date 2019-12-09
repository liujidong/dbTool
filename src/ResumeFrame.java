
import java.awt.EventQueue;
import java.awt.FileDialog;
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

public class ResumeFrame extends JFrame {
    
    private JPanel contentPane;
    private JTextField fileTextField;
    private JComboBox databaseComboBox;
    ResumeUtil util = new ResumeUtil();
	private JFileChooser fileChooser = new JFileChooser(new File("."));   
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ResumeFrame frame = new ResumeFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    /**
     * Create the frame.
     */
    public ResumeFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 200);
        
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
		        List list = util.getDatabase(ConfigUtil.getProperties(fileChooser));
		        databaseComboBox.removeAll();
		        databaseComboBox.addItem("直接恢复");
		        for(int i = 0;i<list.size();i++){
		        	databaseComboBox.addItem(list.get(i));
		        }
			}
		});
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setTitle("数据恢复");
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 434, 139);
        contentPane.add(panel);
        panel.setLayout(null);
        
        JLabel fileLabel = new JLabel("备份文件：");
        fileLabel.setBounds(48, 43, 67, 15);
        panel.add(fileLabel);
        
        fileTextField = new JTextField();
        fileTextField.setBounds(125, 40, 174, 21);
        panel.add(fileTextField);
        fileTextField.setColumns(10);
        
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
        databaseComboBox = new JComboBox(name);
        databaseComboBox.setBounds(125, 86, 174, 21);
        panel.add(databaseComboBox);
        
        JButton resumeButton = new JButton("恢复");
        resumeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                do_resumeButton_actionPerformed(arg0);
            }
        });
        resumeButton.setBounds(320, 85, 74, 23);
        panel.add(resumeButton);
    }
    
    // 浏览按钮的单击处理事件
    protected void do_browseButton_actionPerformed(ActionEvent arg0) {
        java.awt.FileDialog fd = new FileDialog(this);
        fd.setVisible(true);
        fileTextField.setText(fd.getDirectory() + fd.getFile());        
    }    
    // 恢复按钮的单击事件
    protected void do_resumeButton_actionPerformed(ActionEvent arg0) {        
        String fileName = fileTextField.getText();
        String dataName = databaseComboBox.getSelectedItem().toString();
        if (!fileName.equals("") && (!dataName.equals("请选择配置文件"))) {
            boolean bool = util.mysqlresume(dataName, fileName);
            JOptionPane.showMessageDialog(getContentPane(), 
                    "数据恢复成功！", "信息提示框", JOptionPane.WARNING_MESSAGE);
        }
    }
}

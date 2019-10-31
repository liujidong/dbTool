

import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BackFrame extends JFrame {
	private JPanel contentPane;
	private JTextField nameTextField;
	private DataBackup dataBackup = new DataBackup();
	private JComboBox dataBaseComboBox;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					BackFrame frame = new BackFrame();
					frame.setVisible(true);
					//...
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//备份按钮的单击事件
	protected void do_backButton_actionPerformed(ActionEvent agr0) {
		String dataBase = dataBaseComboBox.getSelectedItem().toString();
		String name = nameTextField.getText();
		if(!dataBase.equals("") && !name.equals("")) {
			dataBackup.mysqldump(dataBase,"D:\\"+name);
		}
		JOptionPane.showMessageDialog(getContentPane(), "数据备份成功！","信息提示框",JOptionPane.WARNING_MESSAGE);
	}
}

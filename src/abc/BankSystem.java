package abc;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import DB.DatabaseOperate;

class BankSystem extends JDialog implements ActionListener {
	
	static String[] s = new String[4];
	
	private static final long serialVersionUID = 1L;
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel p3 = new JPanel();
	JPanel p4 = new JPanel();
	JPanel p5 = new JPanel();
	JPanel p6 = new JPanel();
	JLabel label = new JLabel("用户登录：");
	JTextField txtUserName = new JTextField(15);
	JPasswordField txtPwd = new JPasswordField(15);
	JButton ok = new JButton("确定");
	JButton cancel = new JButton("取消");

	public BankSystem() {
		setModal(true);
		setBackground(Color.white);
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new GridLayout(6, 1));
		label.setFont(new Font("Sans Serif", Font.BOLD, 15));
		p2.add(label);
		p3.add(new JLabel("用户名:"));
		p3.add(txtUserName);
		p4.add(new JLabel("密    码:"));
		p4.add(txtPwd);
		p5.add(ok);
		p5.add(cancel);
		ok.addActionListener(this);
		cancel.addActionListener(this);
		txtUserName.addActionListener(this);
		txtPwd.addActionListener(this);
		txtPwd.setEchoChar('*');
		contentPane.add(p1);
		contentPane.add(p2);
		contentPane.add(p3);
		contentPane.add(p4);
		contentPane.add(p5);
		contentPane.add(p6);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screen.width - 500) / 2, (screen.height - 300) / 2);
		setTitle("登录");
		setResizable(false);
		setVisible(true);
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ok || e.getSource() == txtPwd)
		{
			String password = new String(txtPwd.getPassword());
			
			if ((txtUserName.getText().trim().equals("root"))
					&& (password.equals("root"))) {
				JOptionPane.showMessageDialog(null,
						"admin用户登录成功");
				dispose();
				new AdminFrame();
			} 
			else {
				DatabaseOperate myLogin = new DatabaseOperate();
				s = myLogin.login(txtUserName.getText().trim());
				if (s!=null && (txtUserName.getText().trim().equals(s[2].trim()))
						&& (password.equals(s[3].trim()))) {
					JOptionPane.showMessageDialog(null, "普通用户");
					dispose();
					new MainFrame();
				} 
				else {
					JOptionPane.showMessageDialog(null, "密码错误",
							"警告", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if (e.getSource() == cancel)
		{
			dispose();
			System.exit(0);
		} else if (e.getSource() == txtUserName)
		{
			txtPwd.requestFocus();
		}
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		JDialog.setDefaultLookAndFeelDecorated(false);
		Font font = new Font("JFrame", Font.PLAIN, 14);
		Enumeration keys = UIManager.getLookAndFeelDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			if (UIManager.get(key) instanceof Font)
				UIManager.put(key, font);
		}
		new BankSystem();
	}

}

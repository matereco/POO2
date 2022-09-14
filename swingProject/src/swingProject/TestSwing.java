package swingProject;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.io.Console;

public class TestSwing extends JFrame {
	//private JFrame janela2 = null;
	private JPanel contentPane;
	private JTextField txtID;
	private JTextField txtDtCadastro;
	private JTextField txtDescricao;
	private JTable tabProduto;
	private boolean isUpdating = false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestSwing frame = new TestSwing();
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
	public TestSwing() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 510, 380);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(10, 11, 83, 30);
		contentPane.add(lblNewLabel);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(34, 16, 96, 20);
		contentPane.add(txtID);
		txtID.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Data cadastro");
		lblNewLabel_1.setBounds(150, 13, 92, 26);
		contentPane.add(lblNewLabel_1);

		txtDtCadastro = new JTextField();
		txtDtCadastro.setEditable(false);
		txtDtCadastro.setBounds(231, 16, 96, 20);
		contentPane.add(txtDtCadastro);
		txtDtCadastro.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Descrição");
		lblNewLabel_2.setBounds(20, 52, 96, 20);
		contentPane.add(lblNewLabel_2);

		txtDescricao = new JTextField();
		txtDescricao.setEditable(false);
		txtDescricao.setBounds(77, 52, 328, 20);
		contentPane.add(txtDescricao);
		txtDescricao.setColumns(10);

		JButton btnNewButton_1 = new JButton("Novo");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isUpdating = false;
				txtDescricao.setEditable(true);
				txtDescricao.setText(null);
				txtID.setText(null);
				txtDtCadastro.setText(null);
				txtDescricao.requestFocus();
			}
		});
		btnNewButton_1.setBounds(48, 86, 68, 23);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_1_1 = new JButton("Gravar");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isUpdating!=false) {
					try {
						atualizar();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
				}else {
					try {
						gravar();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
				}

			}
		});
		btnNewButton_1_1.setBounds(129, 86, 81, 23);
		contentPane.add(btnNewButton_1_1);

		JButton btnNewButton_1_1_1 = new JButton("Atualizar");
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isUpdating = true;
				txtDescricao.setEditable(true);
				txtDescricao.requestFocus();
				/*try {
					atualizar();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
			}
		});
		btnNewButton_1_1_1.setBounds(220, 86, 92, 23);
		contentPane.add(btnNewButton_1_1_1);

		JButton btnNewButton_1_1_1_1 = new JButton("Excluir");
		btnNewButton_1_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					excluir();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1_1_1_1.setBounds(322, 86, 83, 23);
		contentPane.add(btnNewButton_1_1_1_1);

		tabProduto = new JTable();
		tabProduto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				linhaSelecionada();
			}
		});
		tabProduto.setBounds(77, 133, 328, 167);
		contentPane.add(tabProduto);

		//inicia mostrando a tabela
		try {
			listarProduto();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


	private void listarProduto() throws SQLException{
		Connection con=null;
		generic objconexao=new generic();
		con=objconexao.conectar();
		if(con ==null)
		{ JOptionPane.showMessageDialog(null,"conexão não realizada");
		}
		else
		{ Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM loja.produto");
		String[] colunasTabela = new String[]{ "ID", "Descrição", "Pontuação" };
		DefaultTableModel modeloTabela = new DefaultTableModel(null,colunasTabela);
		modeloTabela.addRow(new String[] {"ID", "DESCRIÇÃO", "CADASTRO"});
		if(rs != null) {
			while(rs.next()) {
				modeloTabela.addRow(new String[] {
						String.valueOf(rs.getInt("ID")),
						rs.getString("descricao"),
						rs.getString("data_cadastro")
				});
			}
		}
		tabProduto.setModel(modeloTabela);
		}
	}
	
	private void linhaSelecionada()
	{
		desabilitarText();
		DefaultTableModel tableModel = (DefaultTableModel) tabProduto.getModel();
		int row = tabProduto.getSelectedRow();
		if (tableModel.getValueAt(row, 0).toString()!="ID")
		{ txtID.setText(tableModel.getValueAt(row, 0).toString());
		txtDescricao.setText(tableModel.getValueAt(row, 1).toString());
		txtDtCadastro.setText(tableModel.getValueAt(row, 2).toString());
		}

	}

	private void desabilitarText()
	{
		txtDescricao.setEditable(false);
		txtID.setEditable(false);
		txtDtCadastro.setEditable(false);
	}

	private void gravar() throws SQLException
	{
		Connection con=null;
		generic objconexao=new generic();
		try
		{con=objconexao.conectar();
		if(con ==null)
		{ JOptionPane.showMessageDialog(null,"conexão não realizada");
		}
		else
		{ Statement stmt = con.createStatement();
		String query="insert into loja.produto(descricao) values('"+txtDescricao.getText()+"')";
		stmt.executeUpdate(query);
		listarProduto();
		txtDescricao.setText(null);
		desabilitarText();
		}
		}
		catch(Exception ex)
		{con.close();
		JOptionPane.showMessageDialog(null,"Não foi possível gravar. "+ex.getMessage());
		}
	}

	private void atualizar() throws SQLException
	{
		System.out.println(txtID.getText()+""+ txtDescricao.getText());

		Connection con=null;
		generic objconexao=new generic();
		try
		{con=objconexao.conectar();
		if(con ==null)
		{ JOptionPane.showMessageDialog(null,"conexão não realizada");
		}
		else
		{ Statement stmt = con.createStatement();
		String query="update produto "
				+ "set descricao = ('"+txtDescricao.getText()+"')"
				+ " where id = ('"+txtID.getText()+"')";
		stmt.executeUpdate(query);
		listarProduto();;
		}
		}
		catch(Exception ex)
		{con.close();
		JOptionPane.showMessageDialog(null,"Não foi possível gravar. "+ex.getMessage());
		}
	}

	private void excluir() throws SQLException
	{	
		Connection con=null;
		generic objconexao=new generic();
		try
		{con=objconexao.conectar();
		if(con ==null)
		{ JOptionPane.showMessageDialog(null,"conexão não realizada");
		}
		else
		{ Statement stmt = con.createStatement();
		String query="delete from produto "
				+ " where id = ('"+txtID.getText()+"')";
		stmt.executeUpdate(query);
		listarProduto();;
		}
		}
		catch(Exception ex)
		{con.close();
		JOptionPane.showMessageDialog(null,"Não foi possível gravar. "+ex.getMessage());
		}
	}
}



package view;

import java.awt.EventQueue;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.ContatoDAO;

import model.Contato;

public class TelaAgenda extends JFrame 
{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textFieldEmail;
	private JTextField textFieldTelefone;
	private JTable table;

	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					TelaAgenda frame = new TelaAgenda();
					frame.setVisible(true);
				} 
				
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	public TelaAgenda() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 11, 48, 14);
		contentPane.add(lblNome);

		textField = new JTextField();
		textField.setBounds(83, 8, 115, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(10, 36, 48, 14);
		contentPane.add(lblEmail);

		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(83, 33, 115, 20);
		contentPane.add(textFieldEmail);
		textFieldEmail.setColumns(10);

		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setBounds(10, 61, 63, 14);
		contentPane.add(lblTelefone);

		textFieldTelefone = new JTextField();
		textFieldTelefone.setBounds(83, 58, 115, 20);
		contentPane.add(textFieldTelefone);
		textFieldTelefone.setColumns(10);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String nome = textField.getText();
					String telefone = textFieldTelefone.getText();
					String email = textFieldEmail.getText();

					Contato contato = new Contato(nome, telefone, email);
					ContatoDAO dao = new ContatoDAO();
					dao.save(contato);

					JOptionPane.showMessageDialog(null, "Salvo!");
					atualizarTabela();
					limparCampos();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnSalvar.setBounds(10, 100, 89, 23);
		contentPane.add(btnSalvar);

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int linha = table.getSelectedRow();
					if (linha != -1) {
						int id = (int) table.getValueAt(linha, 0);
						String nome = textField.getText();
						String telefone = textFieldTelefone.getText();
						String email = textFieldEmail.getText();

						Contato contato = new Contato(nome, telefone, email);
						contato.setId(id);

						ContatoDAO dao = new ContatoDAO();
						dao.update(contato);

						JOptionPane.showMessageDialog(null, "Atualizado!");
						atualizarTabela();
						limparCampos();
					} else {
						JOptionPane.showMessageDialog(null, "Selecione uma linha para atualizar.");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		btnAtualizar.setBounds(109, 100, 89, 23);
		
		contentPane.add(btnAtualizar);

		JButton btnExcluir = new JButton("Excluir");
		
		btnExcluir.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					int linha = table.getSelectedRow();
					
					if (linha != -1) 
					{
						int id = (int) table.getValueAt(linha, 0);

						int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza?", "Atenção", JOptionPane.YES_NO_OPTION);
						
						if (confirm == JOptionPane.YES_OPTION)
						{
							ContatoDAO dao = new ContatoDAO();
							
							dao.deleteByID(id);

							atualizarTabela();
							limparCampos();
						}
					} 
					
					else 
					{
						JOptionPane.showMessageDialog(null, "Selecione uma linha para excluir.");
					}
					
				} 
				
				catch (Exception ex) 
				{
					ex.printStackTrace();
				}
			}
		});
		
		btnExcluir.setBounds(10, 134, 89, 23);
		
		contentPane.add(btnExcluir);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(211, 11, 313, 289);
		contentPane.add(scrollPane);

		table = new JTable();
		
		
        table.addMouseListener(new MouseAdapter() 
        {
            @Override
            
            public void mouseClicked(MouseEvent e) 
            {
                int linha = table.getSelectedRow();
                
                if (linha != -1) 
                {
                    Object nomeObj = table.getValueAt(linha, 1);
                    
                    if (nomeObj != null) 
                    {
                        textField.setText(nomeObj.toString());
                        
                    } 
                    
                    else 
                    {
                        textField.setText("");
                    }

                    Object emailObj = table.getValueAt(linha, 2);
                    
                    if (emailObj != null) 
                    {
                        textFieldEmail.setText(emailObj.toString());
                        
                    }
                    
                    else 
                    {
                        textFieldEmail.setText("");
                    }

                    Object telObj = table.getValueAt(linha, 3);
                    
                    if (telObj != null)
                    {
                        textFieldTelefone.setText(telObj.toString());
                        
                    } 
                    
                    else 
                    {
                    	
                        textFieldTelefone.setText("");
                    }
                }
            }
        });
        
		scrollPane.setViewportView(table);
		
		atualizarTabela();
	}

	protected void atualizarTabela() 
	{
		try 
		{
			ContatoDAO dao = new ContatoDAO();
			List<Contato> contatos = dao.getContatos();

			DefaultTableModel model = new DefaultTableModel();
			model.addColumn("ID");
			model.addColumn("Nome");
			model.addColumn("Email");
			model.addColumn("Telefone");

			for (Contato c : contatos) 
			{
				model.addRow(new Object[] { c.getId(), c.getNome(), c.getEmail(), c.getTelefone() });
			}

			table.setModel(model);
			
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private void limparCampos() 
	{
		textField.setText("");
		textFieldEmail.setText("");
		textFieldTelefone.setText("");
	}
}
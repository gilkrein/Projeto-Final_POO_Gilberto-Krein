package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Contato;

public class ContatoDAO 
{

    public void save(Contato contato) 
    {
    	
        String sql = "INSERT INTO contatos (nome, telefone, email) VALUES (?, ?, ?)";
        
        Connection conn = null;
        
        PreparedStatement ps = null;

        try 
        {
        	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda_db", "root", "SENHA"); // Professor, altere "SENHA" para a senha do seu banco
            ps = conn.prepareStatement(sql);
            ps.setString(1, contato.getNome());
            ps.setString(2, contato.getTelefone());
            ps.setString(3, contato.getEmail());
            ps.execute();
        } 
        
        catch (Exception e) 
        {
            e.printStackTrace();
        } 
        
        finally
        {
            try 
            {
                if (ps != null) ps.close();
                
                if (conn != null) conn.close();
                
            } 
            
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
    }

    public List<Contato> getContatos() 
    {
        String sql = "SELECT * FROM contatos";
        List<Contato> contatos = new ArrayList<Contato>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rset = null;

        try 
        {
        	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda_db", "root", "SENHA"); // Professor, altere "SENHA" para a senha do seu banco
            ps = conn.prepareStatement(sql);
            rset = ps.executeQuery();

            while (rset.next()) 
            {
                Contato contato = new Contato();
                contato.setId(rset.getInt("id"));
                contato.setNome(rset.getString("nome"));
                contato.setTelefone(rset.getString("telefone"));
                contato.setEmail(rset.getString("email"));
                contatos.add(contato);
            }
        } 
        
        catch (Exception e) 
        {
            e.printStackTrace();
        } 
        
        finally 
        {
            try 
            {
                if (rset != null) rset.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } 
            
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
        
        return contatos;
    
    }
    
    public void update(Contato contato) 
    {
        String sql = "UPDATE contatos SET nome = ?, telefone = ?, email = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try 
        {
        	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda_db", "root", "SENHA"); // Professor, altere "SENHA" para a senha do seu banco
            ps = conn.prepareStatement(sql);
            ps.setString(1, contato.getNome());
            ps.setString(2, contato.getTelefone());
            ps.setString(3, contato.getEmail());
            ps.setInt(4, contato.getId());
            ps.execute();
        } 
        
        catch (Exception e) 
        {
            e.printStackTrace();
        } 
        
        finally 
        {
            try 
            {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } 
            
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
    }

    public void deleteByID(int id) 
    {
        String sql = "DELETE FROM contatos WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try 
        {
        	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda_db", "root", "SENHA"); // Professor, altere "SENHA" para a senha do seu banco
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            
        } 
        
        catch (Exception e)
        {
            e.printStackTrace();
            
        } 
        
        finally 
        {
            try
            {
                if (ps != null) ps.close();
                
                if (conn != null) conn.close();
                
            } 
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
}
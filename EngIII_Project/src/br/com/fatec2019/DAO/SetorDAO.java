package br.com.fatec2019.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fatec2019.Dominio.EntidadeDominio;
import br.com.fatec2019.Dominio.Funcionario;
import br.com.fatec2019.Dominio.Setor;

public class SetorDAO extends AbstractDAO
{	@Override public void Salvar(EntidadeDominio entidade) 
	{	
	}

	@Override public void Alterar(EntidadeDominio entidade) 
	{	
	}

	@Override public void Inativar(EntidadeDominio entidade) 
	{	
	}

	@Override public EntidadeDominio ConsultarEntidade(EntidadeDominio entidade) 
	{	PreparedStatement ps = null;//inicializa comando preparada
		Setor setor = (Setor)entidade;
		try
		{	if(this.connection == null || this.connection.isClosed())	
			{this.connection = this.getConnection();}
			//impede o auto-commit
			this.connection.setAutoCommit(false);
			//prepara o comando SQL
			ps = this.connection.prepareStatement("SELECT nome FROM setor WHERE setor_id = ?");
			
			ps.setInt(1, setor.getCodigo());
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{setor.setNome(rs.getString("nome"));}
		}
		catch(ClassNotFoundException | SQLException e)
		{	try 
			{	System.err.println(e.getMessage());
				this.connection.rollback();
			}
			catch(SQLException e1) {e1.printStackTrace();}
			e.printStackTrace();
		}
		finally
		{	try
			{	ps.close();
				this.connection.close();
			}
			catch(SQLException e2){e2.printStackTrace();}
		}
		return setor;
	}

	@Override public List<EntidadeDominio> Consultar(EntidadeDominio entidade) 
	{	PreparedStatement ps = null;//inicializa comando preparada
		Setor setor = (Setor)entidade;
		List<EntidadeDominio> setores = new ArrayList<>();
		try
		{	if(this.connection == null || this.connection.isClosed())	
			{this.connection = this.getConnection();}
			//impede o auto-commit
			this.connection.setAutoCommit(false);
			//prepara o comando SQL
			ps = this.connection.prepareStatement("SELECT nome FROM setor WHERE setor_id = ?");
			
			ps.setInt(1, setor.getCodigo());
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{	Setor s = new Setor(rs.getInt("setor_id"));
				s.setNome("nome");
				setores.add(s);
			}
		}
		catch(ClassNotFoundException | SQLException e)
		{	try 
			{	System.err.println(e.getMessage());
				this.connection.rollback();
			}
			catch(SQLException e1) {e1.printStackTrace();}
			e.printStackTrace();
		}
		finally
		{	try
			{	ps.close();
				this.connection.close();
			}
			catch(SQLException e2){e2.printStackTrace();}
		}
		return setores;
	}
	
	@Override public List<EntidadeDominio> Consultar()
	{	Setor setor = null;
		PreparedStatement ps = null;
		List<EntidadeDominio> setores = new ArrayList<>();
		try
		{	if(this.connection == null || this.connection.isClosed())
			{this.connection = this.getConnection();}
			//impede o auto-commit
			this.connection.setAutoCommit(false);
			ps = this.connection.prepareStatement("SELECT * FROM setor");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{	setor = new Setor(rs.getInt("setor_id"));
				setor.setNome(rs.getNString("nome"));
				setores.add(setor);
			}
		}
		catch(ClassNotFoundException | SQLException e)	
		{	System.out.println(e.getMessage());
			try {this.connection.rollback();} 
			catch (SQLException e1) {System.out.println(e1.getMessage());}
		}
		finally
		{	try
			{	ps.close();
				this.connection.close();
			}
			catch(SQLException e1)	{System.out.println(e1.getMessage());}
		}
		return setores;
	}
}

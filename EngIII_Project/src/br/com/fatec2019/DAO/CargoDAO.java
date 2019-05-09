package br.com.fatec2019.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fatec2019.Dominio.EntidadeDominio;
import br.com.fatec2019.Dominio.Setor;
import br.com.fatec2019.Dominio.Cargo;

public class CargoDAO extends AbstractDAO
{	@Override public void Salvar(EntidadeDominio entidade) {}

	@Override public void Alterar(EntidadeDominio entidade) {}

	@Override public void Inativar(EntidadeDominio entidade) {}

	@Override public EntidadeDominio ConsultarEntidade(EntidadeDominio entidade) 
	{	PreparedStatement ps = null;		//inicializa comando preparada
		Cargo cargo = (Cargo)entidade;
		try
		{	if(this.connection == null || this.connection.isClosed())
			{this.connection = this.getConnection();}
		
			//impede o auto-commit
			this.connection.setAutoCommit(false);
			//prepara o comando SQL
			
			ps = this.connection.prepareStatement("SELECT * FROM perfil WHERE cargo_id = ?");
			
			ps.setInt(1, cargo.getCodigo());
			
			//execução da query de busca
			ResultSet rs = ps.executeQuery();
			
			//enquanto houver registro
			while(rs.next())
			{cargo.setNome(rs.getString("nome"));}
			
			this.connection.commit();
		}
		catch(ClassNotFoundException | SQLException e)	
		{	System.err.println(e.getMessage());
			try {this.connection.rollback();}
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
		return cargo;
	}
	
	@Override public List<EntidadeDominio> Consultar(EntidadeDominio entidade) 
	{	PreparedStatement ps = null;		//inicializa comando preparada
		Cargo cargo = (Cargo)entidade;
		Cargo c = null;
		List<EntidadeDominio> perfils = new ArrayList<>();
		try
		{	if(this.connection == null || this.connection.isClosed())
			{this.connection = this.getConnection();}
		
			//impede o auto-commit
			this.connection.setAutoCommit(false);
			//prepara o comando SQL
			
			ps = this.connection.prepareStatement("SELECT * FROM perfil WHERE cargo_id = ?");
			
			ps.setInt(1, cargo.getCodigo());
			
			//execução da query de busca
			ResultSet rs = ps.executeQuery();
			
			//enquanto houver registro
			while(rs.next())
			{	c = new Cargo(rs.getInt("cargo_id"));
				c.setNome(rs.getString("nome"));
				perfils.add(c);
			}
			
			this.connection.commit();
		}
		catch(ClassNotFoundException | SQLException e)	
		{	System.err.println(e.getMessage());
			try {this.connection.rollback();}
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
		return perfils;
	}
	
	@Override public List<EntidadeDominio> Consultar()
	{	Cargo cargo = null;
		PreparedStatement ps = null;
		List<EntidadeDominio> cargos = new ArrayList<>();
		try
		{	if(this.connection == null || this.connection.isClosed())
			{this.connection = this.getConnection();}
			//impede o auto-commit
			this.connection.setAutoCommit(false);
			ps = this.connection.prepareStatement("SELECT * FROM perfil");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{	cargo = new Cargo(rs.getInt("cargo_id"));
				cargo.setNome(rs.getString("nome"));
				cargos.add(cargo);
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
		return cargos;
	}
}

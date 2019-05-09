package br.com.fatec2019.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fatec2019.Dominio.EntidadeDominio;
import br.com.fatec2019.Dominio.Regional;
import br.com.fatec2019.Dominio.Setor;

public class RegionalDAO extends AbstractDAO
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
		Regional regional = (Regional)entidade;
		try
		{	if(this.connection == null || this.connection.isClosed())	
			{this.connection = this.getConnection();}
			//impede o auto-commit
			this.connection.setAutoCommit(false);
			//prepara o comando SQL
			ps = this.connection.prepareStatement("SELECT nome FROM regional WHERE regional_id = ?");
			
			ps.setInt(1, regional.getCodigo());
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{regional.setNome(rs.getString("nome"));}
		}
		catch(ClassNotFoundException | SQLException e)
		{	try {this.connection.rollback();}
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
		return regional;
	}

	@Override public List<EntidadeDominio> Consultar(EntidadeDominio entidade) 
	{	PreparedStatement ps = null;//inicializa comando preparada
		List<EntidadeDominio> regionais = new ArrayList<>();
		Regional regional = (Regional)entidade;
		try
		{	if(this.connection == null || this.connection.isClosed())	
			{this.connection = this.getConnection();}
			//impede o auto-commit
			this.connection.setAutoCommit(false);
			//prepara o comando SQL
			ps = this.connection.prepareStatement("SELECT * FROM regional WHERE regional_id = ?");
			
			ps.setInt(1, regional.getCodigo());
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{	regional.getCodigo();
				regional.setNome(rs.getString("nome"));
			}
		}
		catch(ClassNotFoundException | SQLException e)
		{	try {this.connection.rollback();}
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
		return regionais;
	}

	@Override
	public List<EntidadeDominio> Consultar() 
	{	PreparedStatement ps = null;//inicializa comando preparada
		List<EntidadeDominio> regionais = new ArrayList<>();
		Regional regional = null;
		try
		{	if(this.connection == null || this.connection.isClosed())	
			{this.connection = this.getConnection();}
			//impede o auto-commit
			this.connection.setAutoCommit(false);
			//prepara o comando SQL
			ps = this.connection.prepareStatement("SELECT * FROM regional");
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{	regional = new Regional(rs.getInt("regional_id"));
				regional.setNome(rs.getString("nome"));
				regionais.add(regional);
			}
		}
		catch(ClassNotFoundException | SQLException e)
		{	try {this.connection.rollback();}
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
		return regionais;
	}
}

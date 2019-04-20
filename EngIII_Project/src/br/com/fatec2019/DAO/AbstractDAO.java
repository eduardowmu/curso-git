package br.com.fatec2019.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import br.com.fatec2019.Dominio.*;

//classe abstrata de DAO que todo DAO de Entidades deverá herdar atributos e métodos
public abstract class AbstractDAO implements IDAO
{	protected Connection connection;			//conexão
	protected String table;						//tabela
	protected int idTable;						//id da tabela
	protected boolean ctrlTransaction = true;	//
	
	//como todas as entidades deverão ser deletadas da mesma maneira
	//(ATRAVÉS DO SEU CODIGO), todas as entidades serão deletadas 
	//através deste método.
	@Override public void Excluir(EntidadeDominio entidade) 
	{	this.connection = null;
		PreparedStatement ps = null;
		//passa tudo para minúscula e armazena o nome da entidade
		this.table = entidade.getClass().getName().toLowerCase();
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		sb.append(this.getTableName(this.table));
		sb.append(" WHERE matricula = ?");
		try//conexão e comando sql no BD
		{	connection = this.getConnection();
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(sb.toString());
			ps.setInt(1, entidade.getCodigo());
			ps.executeUpdate();
			connection.commit();
		}
		catch(ClassNotFoundException | SQLException e1)
		{	try {connection.rollback();}
			catch(SQLException  e2) {e2.printStackTrace();}
		
			e1.printStackTrace();
		}
		finally
		{	try
			{	ps.close();
				if(this.ctrlTransaction) 
					connection.close();
			}
			catch(SQLException e) {e.printStackTrace();}
		}
	}
	
	private String getTableName(String table)
	{	if(table.contains("funcionario"))
			return "funcionarios";
		
		else if(table.contains("perfil"))
			return "perfil";
	
		else if(table.contains("regional"))
			return "regional";
	
		else if(table.contains("setor"))
			return "setor";
		
		else if(table.contains("chamado"))
			return "chamados";
		
		return table;
	}
	//método de obter a conexão com BD
	protected Connection getConnection() 
			throws ClassNotFoundException, SQLException
	{	Class.forName("com.mysql.jdbc.Driver");//para MySQL
		this.connection = DriverManager.getConnection(
			"jdbc:mysql://localhost:3306/fatec2019", "root", "");//para MySQL
		//Class.forName("org.postgresql.Driver");//para pgSQL
		//this.connection = (Connection)DriverManager.getConnection("jdbc:postgresql://localhost:5432/fatec2019", "postgres", "e290");
					
		return this.connection;
	}
}

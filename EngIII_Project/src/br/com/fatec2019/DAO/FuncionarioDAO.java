package br.com.fatec2019.DAO;
import br.com.fatec2019.Dominio.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import br.com.fatec2019.Dominio.EntidadeDominio;
import br.com.fatec2019.Strategy.ValidadorRegional;
import br.com.fatec2019.Strategy.ValidadorSetor;
import br.com.fatec2019.Strategy.ValidadorCargo;
//classe responsável pela definição e manipulação de 
//dados no BD da classe de funcionários. O método de 
//excluir estará na classe abstrata, que poderá servir 
//para qualquer entidade.
public class FuncionarioDAO extends AbstractDAO
{	private CargoDAO pdao = null;
	private SetorDAO sDAO = null;
	private RegionalDAO rDAO = null;
	@Override public void Salvar(EntidadeDominio entidade) 
	{	this.connection = null;//inicializa conexão
		PreparedStatement ps = null;//inicializa comando preparada
		Funcionario funcionario = (Funcionario)entidade;//cast
		//tenttiva de conexão com BD
		try
		{	this.connection = this.getConnection();
			//impede o auto-commit
			this.connection.setAutoCommit(false);
			//prepara o comando SQL
			ps = this.connection.prepareStatement("INSERT INTO funcionarios " + 
					"(nome, cpf, cargo, setor_id, regional_id, email, contratado, estatus, senha, usuario)" + 
					" VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", ps.RETURN_GENERATED_KEYS);
			
			//valores que substituem os ?
			ps.setString(1, funcionario.getNome());
			ps.setString(2, funcionario.getCpf());
			ps.setInt(3, funcionario.getCargo().getCodigo());
			ps.setInt(4, funcionario.getSetor().getCodigo());
			ps.setInt(5, funcionario.getRegional().getCodigo());
			ps.setString(6, funcionario.getEmail());
			ps.setDate(7, new java.sql.Date(funcionario.getDataRegistro().getTime()));
			ps.setString(8, funcionario.getStatus());
			ps.setString(9, funcionario.getSenha());
			ps.setInt(10, funcionario.getUsuario().getCodigo());
			
			//executa o comando sql
			ps.executeUpdate();
			
			//recupera a chave primaria
			ResultSet rs = ps.getGeneratedKeys();
			int idEnd=0;
			//pega a chave primaria gerada e atribui ao objeto
			if(rs.next())
				idEnd = rs.getInt(1);
			//ajusta a matricula do funcionario para o codigo gerado
			funcionario.setCodigo(idEnd);
			
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
	}

	@Override public void Alterar(EntidadeDominio entidade) 
	{	this.connection = null;//inicializa conexão
		PreparedStatement ps = null;//inicializa comando preparada
		Funcionario funcionario = (Funcionario)entidade;//cast
		//tenttiva de conexão com BD
		try
		{	this.connection = this.getConnection();
			//impede o auto-commit
			this.connection.setAutoCommit(false);
			//prepara o comando SQL
			ps = this.connection.prepareStatement("UPDATE funcionarios SET nome = ?, cpf = ?, cargo = ?," + 
				"setor_id = ?, regional_id= ?, email = ?, contratado = ?, senha = ? WHERE matricula = ?");
			
			ps.setString(1, funcionario.getNome());
			ps.setString(2, funcionario.getCpf());
			ps.setInt(3, funcionario.getCargo().getCodigo());
			ps.setInt(4, funcionario.getSetor().getCodigo());
			ps.setInt(5, funcionario.getRegional().getCodigo());
			ps.setString(6, funcionario.getEmail());
			ps.setDate(7, new java.sql.Date(funcionario.getDataRegistro().getTime()));
			ps.setString(8, funcionario.getSenha());
			ps.setInt(9, funcionario.getCodigo());
			
			//executa comando SQL
			ps.executeUpdate();
			if(this.connection != null && !this.connection.isClosed())
				this.connection.commit();
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
				//pega a data de registro para publicar na tabela de funcionarios (ListaFuncionarios.jsp)
				for(EntidadeDominio ed : this.Consultar(funcionario))
				{	Funcionario f = (Funcionario)ed;
					if(funcionario.getCodigo() == f.getCodigo())
					{	DateFormat df = new SimpleDateFormat("EEE, dd/MM/yyyy HH:mm:ss");
						try
						{funcionario.setDataCadastro(df.parse(f.getDataCadastro()));}
						catch(Exception e) {}
						funcionario.setStatus(f.getStatus());
						break;
					}
				}//buscar nome do perfil
			}
			catch(SQLException e2){e2.printStackTrace();}
		}
	}
	//metodo de consulta de funcionarios
	@Override public List<EntidadeDominio> Consultar(EntidadeDominio entidade) 
	{	PreparedStatement ps = null;
		Funcionario funcionario = (Funcionario)entidade;
		Usuario u = null;
		//instancia de funcionario que será criado dentro do looping de consulta (ABAIXO)
		Funcionario f = null;
		//lista que será retornada pela consulta
		List<EntidadeDominio> funcionarios = new ArrayList<>();
		try	//obtem a conexão a se estiver nula
		{	if(this.connection == null || this.connection.isClosed())
				this.connection = this.getConnection();
			
			//impede o auto-commit
			this.connection.setAutoCommit(false);
			//prepara o comando SQL de consulta
			ps = this.connection.prepareStatement("SELECT * FROM funcionarios " +
				"WHERE matricula = ? OR nome LIKE ? OR cpf LIKE ? OR cargo = ? OR setor_id = ? OR regional_id = ? " +
				"OR email LIKE ? OR contratado = ? ");
			
			ps.setInt(1, funcionario.getCodigo());
			if(!funcionario.getNome().equals(""))
			{ps.setString(2, funcionario.getNome() + "%");}
			else ps.setString(2, funcionario.getNome());
			if(!funcionario.getCpf().equals(""))
			{ps.setString(3, funcionario.getCpf() + "%");}
			else ps.setString(3, funcionario.getCpf());
			ps.setInt(4, funcionario.getCargo().getCodigo());
			ps.setInt(5, funcionario.getSetor().getCodigo());
			ps.setInt(6, funcionario.getRegional().getCodigo());
			if(!funcionario.getEmail().equals(""))
			{ps.setString(7, funcionario.getEmail() + "%");}
			else ps.setString(7, funcionario.getEmail());
			ps.setDate(8, new java.sql.Date(funcionario.getDataRegistro().getTime()));
			
			//execução da query de busca
			ResultSet rs = ps.executeQuery();
			
			//enquanto houver registro
			while(rs.next())
			{	f = new Funcionario();//instancia um novo funcionario
				//atribui valores das colunas de cada registro
				f.setCodigo(rs.getInt("matricula"));
				f.setNome(rs.getString("nome"));
				f.setCpf(rs.getString("cpf"));
				f.setCargo(new Cargo(rs.getInt("cargo")));
				CargoDAO cdao = new CargoDAO();
				f.getCargo().setNome(cdao.ConsultarEntidade(f.getCargo()).getNome());
				f.setSetor(new Setor(rs.getInt("setor_id")));
				SetorDAO sdao = new SetorDAO();
				f.getSetor().setNome(sdao.ConsultarEntidade(f.getSetor()).getNome());
				f.setRegional(new Regional(rs.getInt("regional_id")));
				RegionalDAO rdao = new RegionalDAO();
				f.getRegional().setNome(rdao.ConsultarEntidade(f.getRegional()).getNome());
				f.setEmail(rs.getString("email"));
				f.setDataCadastro(new java.sql.Timestamp(rs.getTimestamp("cadastro").getTime()));
				f.setDataRegistro(new java.sql.Date(rs.getDate("contratado").getTime()));
				f.setStatus(rs.getString("estatus"));
				f.setSenha(rs.getString("senha"));
				u = new Usuario();
				u.setCodigo(rs.getInt("usuario"));
				f.setUsuario(u);
				//adiciona este novo funcionario na lista de funcionarios a
				//ser retornado
				funcionarios.add(f);
			}
			if(this.connection != null && !this.connection.isClosed())
				this.connection.commit();
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
		return funcionarios;
	}
	
	@Override public void Inativar(EntidadeDominio entidade) 
	{	this.connection = null;//inicializa conexão
		PreparedStatement ps = null;//inicializa comando preparada
		Funcionario funcionario = (Funcionario)entidade;//cast
		//tenttiva de conexão com BD
		//tenttiva de conexão com BD
		try
		{	this.connection = this.getConnection();
			//impede o auto-commit
			this.connection.setAutoCommit(false);
			//prepara o comando SQL
			ps = this.connection.prepareStatement("UPDATE funcionarios SET estatus = ? " + 
				"WHERE matricula = ?");
			
			ps.setString(1, funcionario.getStatus());
			ps.setInt(2, funcionario.getCodigo());
					
			//executa comando SQL
			ps.executeUpdate();
			if(this.connection != null && !this.connection.isClosed())
				this.connection.commit();
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
	}

	public void AlterarSenha(EntidadeDominio entidade) 
	{	this.connection = null;//inicializa conexão
		PreparedStatement ps = null;//inicializa comando preparada
		Funcionario funcionario = (Funcionario)entidade;//cast
		//tenttiva de conexão com BD
		try
		{	this.connection = this.getConnection();
			//impede o auto-commit
			this.connection.setAutoCommit(false);
			//prepara o comando SQL, alterar a senha de um funcionario de acordo com seu email
			ps = this.connection.prepareStatement("UPDATE funcionarios SET senha = ? " + 
				"WHERE email = ?");
			
			ps.setString(1, funcionario.getSenha());
			ps.setString(2, funcionario.getEmail());
			
			//executa comando SQL
			ps.executeUpdate();
			this.connection.commit();
		}
		catch(ClassNotFoundException | SQLException e)
		{	try {this.connection.rollback();}
			catch(SQLException e1) {e1.printStackTrace();}
			e.printStackTrace();
		}
		finally
		{	try
			{	//pega a data de registro
				if(!this.Consultar(funcionario).isEmpty())
				{	DateFormat df = new SimpleDateFormat("EEE, dd/MM/yyyy HH:mm:ss");
					for(EntidadeDominio ed : this.Consultar(funcionario))
					{	Funcionario f = (Funcionario)ed;
						//pegaremos como parametro de igualdade seu email, pois é um UNIQUE em BD
						if(funcionario.getEmail().equals(f.getEmail()))
						{	funcionario.setCodigo(f.getCodigo());
							funcionario.setNome(f.getNome());
							funcionario.setCpf(f.getCpf());
							funcionario.setSetor(f.getSetor());
							funcionario.setRegional(f.getRegional());
							funcionario.setCargo(f.getCargo());
							funcionario.setStatus(f.getStatus());
							funcionario.setDataRegistro(f.getDataRegistro());
							try {funcionario.setDataCadastro(df.parse(f.getDataCadastro()));} 
							catch (ParseException e) {e.printStackTrace();}
							break;
						}
					}
				}//fecha a conexão com BD
				ps.close();
				this.connection.close();
			}
			catch(SQLException e2){e2.printStackTrace();}
		}
	}

	@Override
	public EntidadeDominio ConsultarEntidade(EntidadeDominio entidade) 
	{	PreparedStatement ps = null;
		Funcionario f = (Funcionario)entidade;
		CargoDAO cdao = new CargoDAO();
		SetorDAO sdao = new SetorDAO();
		RegionalDAO rdao = new RegionalDAO();
		//tenttiva de conexão com BD
		try
		{	this.connection = this.getConnection();
			//impede o auto-commit
			this.connection.setAutoCommit(false);
			//prepara o comando SQL, alterar a senha de um funcionario de acordo com seu email
			ps = this.connection.prepareStatement("SELECT * FROM funcionarios WHERE matricula = ?");
				
			ps.setInt(1, f.getCodigo());
					
			//execução da query de busca
			ResultSet rs = ps.executeQuery();
			
			//enquanto houver registro
			while(rs.next())
			{	//atribui valores das colunas de cada registro
				f.setNome(rs.getString("nome"));
				f.setCpf(rs.getString("cpf"));
				f.setCargo(new Cargo(rs.getInt("cargo")));
				cdao.ConsultarEntidade(f.getCargo());
				f.setSetor(new Setor(rs.getInt("setor_id")));
				sdao.ConsultarEntidade(f.getSetor());
				f.setRegional(new Regional(rs.getInt("regional_id")));
				rdao.ConsultarEntidade(f.getRegional());
				f.setEmail(rs.getString("email"));
				f.setDataCadastro(new java.sql.Timestamp(rs.getTimestamp("cadastro").getTime()));
				f.setDataRegistro(new java.sql.Date(rs.getDate("contratado").getTime()));
				f.setStatus(rs.getString("estatus"));
				f.setSenha(rs.getString("senha"));
				break;
			}
			if(this.connection != null && !this.connection.isClosed())
				this.connection.commit();
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
		return f;
	}

	@Override public List<EntidadeDominio> Consultar() 
	{	PreparedStatement ps = null;
		//instancia de funcionario que será criado dentro do looping de consulta (ABAIXO)
		Funcionario f = null;
		Usuario u = null;
		//lista que será retornada pela consulta
		List<EntidadeDominio> funcionarios = new ArrayList<>();
		try	//obtem a conexão a se estiver nula
		{	if(this.connection == null || this.connection.isClosed())
				this.connection = this.getConnection();
			
			//impede o auto-commit
			this.connection.setAutoCommit(false);
			//prepara o comando SQL de consulta
			ps = this.connection.prepareStatement("SELECT * FROM funcionarios");
			
			//execução da query de busca
			ResultSet rs = ps.executeQuery();
			
			//enquanto houver registro
			while(rs.next())
			{	f = new Funcionario();//instancia um novo funcionario
				//atribui valores das colunas de cada registro
				f.setCodigo(rs.getInt("matricula"));
				f.setNome(rs.getString("nome"));
				f.setCpf(rs.getString("cpf"));
				f.setCargo(new Cargo(rs.getInt("cargo")));
				CargoDAO cdao = new CargoDAO();
				f.getCargo().setNome(cdao.ConsultarEntidade(f.getCargo()).getNome());
				f.setSetor(new Setor(rs.getInt("setor_id")));
				SetorDAO sdao = new SetorDAO();
				f.getSetor().setNome(sdao.ConsultarEntidade(f.getSetor()).getNome());
				f.setRegional(new Regional(rs.getInt("regional_id")));
				RegionalDAO rdao = new RegionalDAO();
				f.getRegional().setNome(rdao.ConsultarEntidade(f.getRegional()).getNome());
				f.setEmail(rs.getString("email"));
				f.setDataCadastro(new java.sql.Timestamp(rs.getTimestamp("cadastro").getTime()));
				f.setDataRegistro(new java.sql.Date(rs.getDate("contratado").getTime()));
				f.setStatus(rs.getString("estatus"));
				f.setSenha(rs.getString("senha"));
				u = new Usuario();
				u.setCodigo(rs.getInt("usuario"));
				f.setUsuario(u);
				//adiciona este novo funcionario na lista de funcionarios a
				//ser retornado
				funcionarios.add(f);
			}
			if(this.connection != null && !this.connection.isClosed())
				this.connection.commit();
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
		return funcionarios;
	}
}

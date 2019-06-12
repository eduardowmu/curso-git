package br.com.fatec2019.Controle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.fatec2019.Dominio.Cargo;
import br.com.fatec2019.Dominio.EntidadeDominio;
import br.com.fatec2019.Dominio.Funcionario;
import br.com.fatec2019.Dominio.Regional;
import br.com.fatec2019.Dominio.Setor;
import br.com.fatec2019.Dominio.Usuario;
import br.com.fatec2019.DAO.*;
import br.com.fatec2019.Strategy.*;
import br.com.fatec2019.DAO.IDAO;
import br.com.fatec2019.Controle.Resposta;

public class Fachada implements IFachada
{	private Map<String, IDAO> daos;//mapa de DAOS que ser� indexado pelo nome da entidade
									//o valor � uma instancia do DAO para uma dada entidade

	//Mapa para conter as regras de neg�cio de todas as opera��es por entidade
	//O valor � um mapa de regras de neg�cio indexadas pela opera��o
	private Map<String, Map<String, List<IStrategy>>> regrasNeg;
	private Resposta resposta;//mensagem a ser retornada
	StringBuilder sb;//nos possibilita armazenar Strings e escrever em arquivo JSP codigos HTML.
	
	//construtor, onde ser� definida as regras de neg�cio e as DAOs respons�veis
	//pela conex�o, defini��o e manipula��o de dados no BD.
	public Fachada()
	{	daos = new HashMap<String, IDAO>();//instanciando um mapa de DAOs
	
		/*Int�nciando o Map de Regras de Neg�cio*/
		regrasNeg = new HashMap<String, Map<String, List<IStrategy>>>();
		
		//definindo as DAOs de cada entidade
		FuncionarioDAO funcDAO = new FuncionarioDAO();
		
		//Adicionando as DAOs instanciadas no mapa de DAOs
		daos.put(Funcionario.class.getName(), funcDAO);
		
		//instancias de Strategies
		ValidadorDadosCadastrais vDC = new ValidadorDadosCadastrais();
		ValidadorCargo vu = new ValidadorCargo();
		ValidadorCPF vCPF = new ValidadorCPF();
		ValidadorEmail vEmail = new ValidadorEmail();
		ValidadorExistencia vEx = new ValidadorExistencia();
		ValidadorRegional vr = new ValidadorRegional();
		ValidadorSetor vs = new ValidadorSetor();
		ValidadorSenha vSenha = new ValidadorSenha();
		ComparadorSenhas cs = new ComparadorSenhas();
		ValidadorCodigo vc = new ValidadorCodigo();
		ValidadorStatusFunc vSF = new ValidadorStatusFunc();
		RegistradorData rd = new RegistradorData();
		ValidadorLogin vl = new ValidadorLogin();
		ValidadorUsuario vus = new ValidadorUsuario();
		
		//instanciando listas de regras de neg�cio para cada fun��o do CRUD
		List<IStrategy> rnSalvarFunc = new ArrayList<>();
		List<IStrategy> rnConsultarFunc = new ArrayList<>();
		List<IStrategy> rnVisualizarFunc = new ArrayList<>();
		List<IStrategy> rnAlterarFunc = new ArrayList<>();
		//altera��o de senha de funcionario
		List<IStrategy> rnAlterarSenha = new ArrayList<>();
		List<IStrategy> rnDeletarFunc = new ArrayList<>();
		List<IStrategy> rnLogarFunc = new ArrayList<>();
		List<IStrategy> rnInativarFunc = new ArrayList<>();
		List<IStrategy> rnFormularFunc = new ArrayList<>();
		
		//adicionar na lista de regras para salvar um cadastro de funcionario
		rnSalvarFunc.add(vDC);
		rnSalvarFunc.add(vCPF);
		rnSalvarFunc.add(vSF);
		rnSalvarFunc.add(vEx);
		rnSalvarFunc.add(vr);
		rnSalvarFunc.add(vs);
		rnSalvarFunc.add(vu);
		rnSalvarFunc.add(vEmail);
		rnSalvarFunc.add(vSenha);
		rnSalvarFunc.add(cs);
		rnSalvarFunc.add(rd);
		
		//adicionar na lista de regras para Consultar um funcionario
		rnConsultarFunc.add(vDC);
		
		//adicionar na lista de regras para visualizar um funcionario
		rnVisualizarFunc.add(vc);
		
		//adicionar na lista de regras para Alterar um funcionario
		rnAlterarFunc.add(vDC);
		rnAlterarFunc.add(vCPF);
		rnAlterarFunc.add(vr);
		rnAlterarFunc.add(vs);
		rnAlterarFunc.add(vu);
		rnAlterarFunc.add(vEmail);
		rnAlterarFunc.add(vSenha);
		rnAlterarFunc.add(cs);
		
		//add na lista de regras para Alterar senha de funcionarios
		rnAlterarSenha.add(vEmail);
		rnAlterarSenha.add(vSenha);
		rnAlterarSenha.add(cs);
		
		//criar regra para inativar funcionario... que na verdade tamb�m
		//ser� respons�vel pela ativa��o, caso esteja inativado
		rnInativarFunc.add(vc);
		rnInativarFunc.add(vSF);
		
		//add na lista de regras para deletar um funcionario
		rnDeletarFunc.add(vc);
		
		//add na lista de regras para logar um funcionario
		rnLogarFunc.add(vEmail);
		rnLogarFunc.add(vSenha);
		rnLogarFunc.add(vl);
		
		/*Cria o mapa que poder� conter todas as listas de regras de 
		neg�cio espec�fica por opera��o  do fornecedor*/
		Map<String, List<IStrategy>> regrasFuncionario = new HashMap<String, 
			List<IStrategy>>();
		
		//Cada chave (KEY) desse Map � o valor ("value") armazenadas nas tags 
		//<input type="submit"/> das p�ginas HTML, e os valores s�o as regras de neg�cios
		//para cada evento
		regrasFuncionario.put("CadastrarFuncionario", rnSalvarFunc);
		regrasFuncionario.put("ConsultarFuncionario", rnConsultarFunc);
		regrasFuncionario.put("AlterarFuncionario", rnAlterarFunc);
		regrasFuncionario.put("AlterarSenha", rnAlterarSenha);
		regrasFuncionario.put("ExcluirFuncionario", rnDeletarFunc);
		regrasFuncionario.put("LogarFuncionario", rnLogarFunc);
		regrasFuncionario.put("InativarFuncionario", rnInativarFunc);
		regrasFuncionario.put("VisualizarFuncionario", rnVisualizarFunc);
		
		/*Adiciona o mapa com as regras indexadas pelas opera��es no mapa 
		geral indexado pelo nome da entidade*/
		regrasNeg.put(Funcionario.class.getName(), regrasFuncionario);
	}
	//execu��o das regras de neg�cio
	private String executeRules(EntidadeDominio entidade, String operacao)
	{	StringBuilder msg = new StringBuilder();
		//busca as Stragegies do evento desejado
		Map<String, List<IStrategy>> regrasOperacao = 
				regrasNeg.get(entidade.getClass().getName());
		//se a lista n�o estiver vazia
		if(regrasOperacao != null)//atribui a uma outra lista, inserindo apenas as regras
		{	List<IStrategy> regras = regrasOperacao.get(operacao);
			if(regrasOperacao.get(operacao) !=  null)//se n�o estiver vazia
			{	for(IStrategy validador : regras)//looping por todas as regras para valida��o
				{	if(validador.Processar(entidade) != null)//se o retorno for diferente de nulo
					{msg.append(validador.Processar(entidade));}//significa que houve algum erro da familia 400
				}
			}
		}
		if(msg.length() > 0) return msg.toString();
		
		else return null;
	}
	
	//m�todos que ir�o retornar ao cliente conforme as requisi��es
	@Override public Resposta salvar(EntidadeDominio entidade) 
	{	resposta = new Resposta();//vari�vel a ser retornado
		//se a execu��o, indexada por um nome de uma classe, 
		//das regras da entidade para salvar n�o for nula
		List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
		Usuario usuario = null;
		if(entidade instanceof Funcionario)
		{	Funcionario f = (Funcionario)entidade;
			usuario = f.getUsuario();
		}
		entidades.add(usuario);
		
		if(this.executeRules(entidade, "CadastrarFuncionario") == null)
		{	IDAO dao = daos.get(entidade.getClass().getName());
			try	{dao.Salvar(entidade);}
			catch(Exception e)
			{	e.printStackTrace();	
				resposta.setMsg("N�o foi poss�vel realizar o registro!");
			}
			finally
			{	entidades.add(entidade);
				this.addElementsList(entidades);
				resposta.setEntidades(entidades);
			}
		}
		else resposta.setMsg(this.executeRules(entidade, "CadastrarFuncionario"));
		
		return resposta;
	}

	@Override public Resposta alterar(EntidadeDominio entidade) 
	{	resposta = new Resposta();//vari�vel a ser retornado
		String nameClass = entidade.getClass().getName();
		//se a execu��o, indexada por um nome de uma classe, 
		//das regras da entidade para salvar n�o for nula
		List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
		Usuario usuario = null;
		if(entidade instanceof Funcionario)
		{	Funcionario f = (Funcionario)entidade;
			usuario = f.getUsuario();
		}
		entidades.add(usuario);
		
		if(this.executeRules(entidade, "AlterarFuncionario") == null)
		{	IDAO dao = daos.get(nameClass);
			//chama o m�todo de alterar
			try	{dao.Alterar(entidade);}
			catch(Exception e)
			{	e.printStackTrace();	
				resposta.setMsg("N�o foi poss�vel alterar o registro!");
			}
		}
		else resposta.setMsg(this.executeRules(entidade, "AlterarFuncionario"));
		entidades.add(entidade);
		this.addElementsList(entidades);
		resposta.setEntidades(entidades);
		return resposta;
	}
	
	@Override public Resposta alterarSenha(EntidadeDominio entidade) 
	{	resposta = new Resposta();//vari�vel a ser retornado
		String nameClass = entidade.getClass().getName();
		//se a execu��o, indexada por um nome de uma classe, 
		//das regras da entidade para salvar n�o for nula
		List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
		if(this.executeRules(entidade, "AlterarSenha") == null)
		{	FuncionarioDAO dao = new FuncionarioDAO();
			//chama o m�todo de alterar
			try	{dao.AlterarSenha(entidade);}
			catch(Exception e)
			{	e.printStackTrace();	
				resposta.setMsg("N�o foi poss�vel alterar a senha!");
			}
		}
		else resposta.setMsg(this.executeRules(entidade, "AlterarSenha"));
		entidades.add(entidade);
		this.addElementsList(entidades);
		resposta.setEntidades(entidades);
		return resposta;
	}

	@Override public Resposta excluir(EntidadeDominio entidade) 
	{	resposta = new Resposta();//vari�vel a ser retornado
		String nameClass = entidade.getClass().getName();
		//se a execu��o, indexada por um nome de uma classe, 
		//das regras da entidade para excluir n�o for nula
		List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
		Usuario usuario = null;
		if(entidade instanceof Funcionario)
		{	Funcionario f = (Funcionario)entidade;
			usuario = f.getUsuario();
		}
		entidades.add(usuario);
		
		if(this.executeRules(entidade, "ExcluirFuncionario") == null)
		{	IDAO dao = daos.get(nameClass);
			try
			{	dao.Excluir(entidade);
				//entidades.add(entidade);
			}
			catch(Exception e)
			{	e.printStackTrace();	
				resposta.setMsg("N�o foi poss�vel excluir o registro!");
			}
			finally
			{	this.addElementsList(entidades);
				resposta.setEntidades(entidades);
			}
		}
		else resposta.setMsg(this.executeRules(entidade, "ExcluirFuncionario"));
		return resposta;
	}

	@Override public Resposta consultar(EntidadeDominio entidade) 
	{	resposta = new Resposta();//vari�vel a ser retornado
		//se a execu��o, indexada por um nome de uma classe, 
		//das regras da entidade para consultar n�o for nula
		List<EntidadeDominio> entidades = new ArrayList<>();
		Usuario usuario = null;
		if(entidade instanceof Funcionario)
		{	Funcionario f = (Funcionario)entidade;
			usuario = f.getUsuario();
		}
		entidades.add(usuario);
		
		if(this.executeRules(entidade, "ConsultarFuncionario") == null)
		{	IDAO dao = daos.get(entidade.getClass().getName());
			try	
			{	if(dao.Consultar(entidade).isEmpty() || dao.Consultar(entidade) == null)
				{resposta.setMsg("N�o foi poss�vel realizar a busca");}
				
				else entidades.addAll(dao.Consultar(entidade));
			}
			catch(Exception e)
			{	e.printStackTrace();	
				resposta.setMsg("N�o foi poss�vel consultar o registro!");
			}
		}	
		else resposta.setMsg(this.executeRules(entidade, "ConsultarFuncionario"));
		this.addElementsList(entidades);
		resposta.setEntidades(entidades);
		return resposta;
	}
	//m�todo que far� busca de Entidade que deseja ser alterado, para
	//ser exibido em um formul�rio para altera��o
	@Override public Resposta visualizar(EntidadeDominio entidade) 
	{	resposta = new Resposta();
		List<EntidadeDominio> entidades = new ArrayList<>();
		Usuario usuario = null;
		if(entidade instanceof Funcionario)
		{	Funcionario f = (Funcionario)entidade;
			usuario = f.getUsuario();
		}
		entidades.add(usuario);
		
		if(this.executeRules(entidade, "VisualizarFuncionario") == null)
		{	IDAO dao = daos.get(entidade.getClass().getName());
			try
			{	for(EntidadeDominio ed:dao.Consultar(entidade))
				{	if(ed.getCodigo() == entidade.getCodigo())
					{	entidades.add(ed);
						break;
					}
				}
			}
			catch(Exception e)
			{	e.printStackTrace();	
				resposta.setMsg("N�o foi poss�vel encontrar o funcionario");
			}
			finally
			{	this.addElementsList(entidades);
				resposta.setEntidades(entidades);
			}
		}
		else resposta.setMsg(this.executeRules(entidade, "VisualizarFuncionario"));
		return resposta;
	}
	
	@Override public Resposta Logar(EntidadeDominio entidade)
	{	resposta = new Resposta();
		List<EntidadeDominio> entidades = new ArrayList<>();
		Usuario usuario = null;
		if(entidade instanceof Funcionario)
		{	Funcionario f = (Funcionario)entidade;
			usuario = f.getUsuario();
		}
		entidades.add(usuario);
		
		if(this.executeRules(entidade, "LogarFuncionario") == null)
		{	IDAO dao = daos.get(entidade.getClass().getName());
			try	{entidades.addAll(dao.Consultar(entidade));}
			catch(Exception e)
			{	e.printStackTrace();
				resposta.setMsg("N�o foi poss�vel encontrar o funcionario");
			}
			finally
			{	this.addElementsList(entidades);
				resposta.setEntidades(entidades);
			}
		}
		else resposta.setMsg(this.executeRules(entidade, "LogarFuncionario"));
		return resposta;
	}
	
	//este m�todo foi pensado tanto para desativar como ativar funcionario / Entidade
	@Override public Resposta Inativar(EntidadeDominio entidade) 
	{	resposta = new Resposta();
		List<EntidadeDominio> entidades = new ArrayList<>();
		Usuario usuario = null;
		if(entidade instanceof Funcionario)
		{	Funcionario f = (Funcionario)entidade;
			usuario = f.getUsuario();
		}
		entidades.add(usuario);
		if(this.executeRules(entidade, "InativarFuncionario") == null)
		{	IDAO dao = daos.get(entidade.getClass().getName());
			try
			{	dao.Inativar(entidade);
				entidades.add(entidade);
				resposta.setEntidades(entidades);
			}
			catch(Exception e)
			{	e.printStackTrace();	
				resposta.setMsg("N�o foi poss�vel encontrar o funcionario");
			}
		}
		else resposta.setMsg(this.executeRules(entidade, "InativarFuncionario"));
		this.addElementsList(entidades);
		return resposta;
	}
	
	@Override public Resposta Formular(EntidadeDominio entidade) 
	{	resposta = new Resposta();
		List<EntidadeDominio> entidades = new ArrayList<>();
		Usuario usuario = null;
		if(entidade instanceof Funcionario)
		{	Funcionario f = (Funcionario)entidade;
			usuario = f.getUsuario();
		}
		entidades.add(usuario);
		this.addElementsList(entidades);
		resposta.setEntidades(entidades);
		return resposta;
	}
	
	public void addElementsList(List<EntidadeDominio> entidades)
	{	SetorDAO sdao = new SetorDAO();
		entidades.addAll(sdao.Consultar());
		//for(EntidadeDominio ed:sdao.Consultar())
		//{entidades.add(ed);}
		CargoDAO cdao = new CargoDAO();
		entidades.addAll(cdao.Consultar());
		//for(EntidadeDominio ed:cdao.Consultar())
		//{entidades.add(ed);}
		RegionalDAO rdao = new RegionalDAO();
		entidades.addAll(rdao.Consultar());
		//for(EntidadeDominio ed:rdao.Consultar())
		//{entidades.add(ed);}
	}
}

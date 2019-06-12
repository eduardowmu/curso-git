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
{	private Map<String, IDAO> daos;//mapa de DAOS que será indexado pelo nome da entidade
									//o valor é uma instancia do DAO para uma dada entidade

	//Mapa para conter as regras de negócio de todas as operações por entidade
	//O valor é um mapa de regras de negócio indexadas pela operação
	private Map<String, Map<String, List<IStrategy>>> regrasNeg;
	private Resposta resposta;//mensagem a ser retornada
	StringBuilder sb;//nos possibilita armazenar Strings e escrever em arquivo JSP codigos HTML.
	
	//construtor, onde será definida as regras de negócio e as DAOs responsáveis
	//pela conexão, definição e manipulação de dados no BD.
	public Fachada()
	{	daos = new HashMap<String, IDAO>();//instanciando um mapa de DAOs
	
		/*Intânciando o Map de Regras de Negócio*/
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
		
		//instanciando listas de regras de negócio para cada função do CRUD
		List<IStrategy> rnSalvarFunc = new ArrayList<>();
		List<IStrategy> rnConsultarFunc = new ArrayList<>();
		List<IStrategy> rnVisualizarFunc = new ArrayList<>();
		List<IStrategy> rnAlterarFunc = new ArrayList<>();
		//alteração de senha de funcionario
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
		
		//criar regra para inativar funcionario... que na verdade também
		//será responsável pela ativação, caso esteja inativado
		rnInativarFunc.add(vc);
		rnInativarFunc.add(vSF);
		
		//add na lista de regras para deletar um funcionario
		rnDeletarFunc.add(vc);
		
		//add na lista de regras para logar um funcionario
		rnLogarFunc.add(vEmail);
		rnLogarFunc.add(vSenha);
		rnLogarFunc.add(vl);
		
		/*Cria o mapa que poderá conter todas as listas de regras de 
		negócio específica por operação  do fornecedor*/
		Map<String, List<IStrategy>> regrasFuncionario = new HashMap<String, 
			List<IStrategy>>();
		
		//Cada chave (KEY) desse Map é o valor ("value") armazenadas nas tags 
		//<input type="submit"/> das páginas HTML, e os valores são as regras de negócios
		//para cada evento
		regrasFuncionario.put("CadastrarFuncionario", rnSalvarFunc);
		regrasFuncionario.put("ConsultarFuncionario", rnConsultarFunc);
		regrasFuncionario.put("AlterarFuncionario", rnAlterarFunc);
		regrasFuncionario.put("AlterarSenha", rnAlterarSenha);
		regrasFuncionario.put("ExcluirFuncionario", rnDeletarFunc);
		regrasFuncionario.put("LogarFuncionario", rnLogarFunc);
		regrasFuncionario.put("InativarFuncionario", rnInativarFunc);
		regrasFuncionario.put("VisualizarFuncionario", rnVisualizarFunc);
		
		/*Adiciona o mapa com as regras indexadas pelas operações no mapa 
		geral indexado pelo nome da entidade*/
		regrasNeg.put(Funcionario.class.getName(), regrasFuncionario);
	}
	//execução das regras de negócio
	private String executeRules(EntidadeDominio entidade, String operacao)
	{	StringBuilder msg = new StringBuilder();
		//busca as Stragegies do evento desejado
		Map<String, List<IStrategy>> regrasOperacao = 
				regrasNeg.get(entidade.getClass().getName());
		//se a lista não estiver vazia
		if(regrasOperacao != null)//atribui a uma outra lista, inserindo apenas as regras
		{	List<IStrategy> regras = regrasOperacao.get(operacao);
			if(regrasOperacao.get(operacao) !=  null)//se não estiver vazia
			{	for(IStrategy validador : regras)//looping por todas as regras para validação
				{	if(validador.Processar(entidade) != null)//se o retorno for diferente de nulo
					{msg.append(validador.Processar(entidade));}//significa que houve algum erro da familia 400
				}
			}
		}
		if(msg.length() > 0) return msg.toString();
		
		else return null;
	}
	
	//métodos que irão retornar ao cliente conforme as requisições
	@Override public Resposta salvar(EntidadeDominio entidade) 
	{	resposta = new Resposta();//variável a ser retornado
		//se a execução, indexada por um nome de uma classe, 
		//das regras da entidade para salvar não for nula
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
				resposta.setMsg("Não foi possível realizar o registro!");
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
	{	resposta = new Resposta();//variável a ser retornado
		String nameClass = entidade.getClass().getName();
		//se a execução, indexada por um nome de uma classe, 
		//das regras da entidade para salvar não for nula
		List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
		Usuario usuario = null;
		if(entidade instanceof Funcionario)
		{	Funcionario f = (Funcionario)entidade;
			usuario = f.getUsuario();
		}
		entidades.add(usuario);
		
		if(this.executeRules(entidade, "AlterarFuncionario") == null)
		{	IDAO dao = daos.get(nameClass);
			//chama o método de alterar
			try	{dao.Alterar(entidade);}
			catch(Exception e)
			{	e.printStackTrace();	
				resposta.setMsg("Não foi possível alterar o registro!");
			}
		}
		else resposta.setMsg(this.executeRules(entidade, "AlterarFuncionario"));
		entidades.add(entidade);
		this.addElementsList(entidades);
		resposta.setEntidades(entidades);
		return resposta;
	}
	
	@Override public Resposta alterarSenha(EntidadeDominio entidade) 
	{	resposta = new Resposta();//variável a ser retornado
		String nameClass = entidade.getClass().getName();
		//se a execução, indexada por um nome de uma classe, 
		//das regras da entidade para salvar não for nula
		List<EntidadeDominio> entidades = new ArrayList<EntidadeDominio>();
		if(this.executeRules(entidade, "AlterarSenha") == null)
		{	FuncionarioDAO dao = new FuncionarioDAO();
			//chama o método de alterar
			try	{dao.AlterarSenha(entidade);}
			catch(Exception e)
			{	e.printStackTrace();	
				resposta.setMsg("Não foi possível alterar a senha!");
			}
		}
		else resposta.setMsg(this.executeRules(entidade, "AlterarSenha"));
		entidades.add(entidade);
		this.addElementsList(entidades);
		resposta.setEntidades(entidades);
		return resposta;
	}

	@Override public Resposta excluir(EntidadeDominio entidade) 
	{	resposta = new Resposta();//variável a ser retornado
		String nameClass = entidade.getClass().getName();
		//se a execução, indexada por um nome de uma classe, 
		//das regras da entidade para excluir não for nula
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
				resposta.setMsg("Não foi possível excluir o registro!");
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
	{	resposta = new Resposta();//variável a ser retornado
		//se a execução, indexada por um nome de uma classe, 
		//das regras da entidade para consultar não for nula
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
				{resposta.setMsg("Não foi possível realizar a busca");}
				
				else entidades.addAll(dao.Consultar(entidade));
			}
			catch(Exception e)
			{	e.printStackTrace();	
				resposta.setMsg("Não foi possível consultar o registro!");
			}
		}	
		else resposta.setMsg(this.executeRules(entidade, "ConsultarFuncionario"));
		this.addElementsList(entidades);
		resposta.setEntidades(entidades);
		return resposta;
	}
	//método que fará busca de Entidade que deseja ser alterado, para
	//ser exibido em um formulário para alteração
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
				resposta.setMsg("Não foi possível encontrar o funcionario");
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
				resposta.setMsg("Não foi possível encontrar o funcionario");
			}
			finally
			{	this.addElementsList(entidades);
				resposta.setEntidades(entidades);
			}
		}
		else resposta.setMsg(this.executeRules(entidade, "LogarFuncionario"));
		return resposta;
	}
	
	//este método foi pensado tanto para desativar como ativar funcionario / Entidade
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
				resposta.setMsg("Não foi possível encontrar o funcionario");
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

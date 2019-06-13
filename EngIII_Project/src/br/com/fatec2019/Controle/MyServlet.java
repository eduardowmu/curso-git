package br.com.fatec2019.Controle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.com.fatec2019.Dominio.*;
import br.com.fatec2019.command.*;
import br.com.fatec2019.View.*;
/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet 
{	private static final long serialVersionUID = 1L;
	//mapa de comandos
	private static Map<String, ICommand> commands;
	//mapa de view helper
	private static Map<String, IViewHelper> views;
    /**
     * @see HttpServlet#HttpServlet()
     * Contrutor!
     */
    public MyServlet() 
    {	/* Utilizando a command para chamar a fachada e indexando cada command 
   	 	pela operação, garantimos que esta servelet atenderá qualquer operação*/
		commands = new HashMap<String, ICommand>();
		
		//insere os comandos que podem ser utilizados (estão faltando alguns comandos!, incluí-las!!!)
		commands.put("CadastrarFuncionario", new SalvarCommand());		//Salvar Entidade
		commands.put("AlterarFuncionario", new AlterarCommand());		//Alterar	Entidade
		commands.put("AlterarSenha", new AltSenhaCommand());			//Alterar Senha de Funcionario
		commands.put("ExcluirFuncionario", new DeletarCommand());		//Deletar Entidade
		commands.put("ConsultarFuncionario", new ConsultarCommand());	//Consultar Entidade
		commands.put("VisualizarFuncionario", new VisualizarCommand());	//Visualização de Entidade existente para futura alteração
		commands.put("LogarFuncionario", new LogarCommand());			//Login de Usuario
		commands.put("InativarFuncionario", new InativarCommand());		//Inativar Funcionario
		commands.put("AtivarFuncionario", new InativarCommand());		//Ativar Novamente. REPARE QUE ESTÁ REAPROVEITANDO O MÉTODO DE DESATIVAR!!!
		commands.put("FormularFuncionario", new FormularCommand());		//preparar formulário para cadastrar novo usuario
		
		/* Utilizando o ViewHelper para tratar as especificações de qualquer tela 
		 e indexando cada viewhelper pela url em que esta servlet é chamada 
		 no form garantimos que esta servelt atenderá qualquer entidade*/
	   	views = new HashMap<String, IViewHelper>();
	   	
	   	/*A chave do mapa é o mapeamento da servlet para cada form que 
	   	está configurado e utilizado no action do html*/
	   	views.put("/EngIII_Project/MyServlet", new FuncionarioVH());
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{this.ProcessRequest(request, response);}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{this.ProcessRequest(request, response);}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{this.ProcessRequest(request, response);}
	
	protected void ProcessRequest(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException
	{	//Obtêm a uri que invocou esta servlet (O que foi definido no methdo do form html)
		//Obtêm um viewhelper indexado pela uri que invocou esta servlet
		IViewHelper vh = views.get(request.getRequestURI());
		
		//O viewhelper retorna a entidade especifica para a tela que chamou esta servlet
		EntidadeDominio entidade = vh.getEntidade(request);
		
		//Obtêm o command para executar a respectiva operação
		ICommand command = commands.get(request.getParameter(entidade.getClass().getSimpleName().toLowerCase()));
		
		/*Executa o command que chamará a fachada para executar a operação requisitada
		 o retorno é uma instância da classe resultado que pode conter mensagens derro 
		 ou entidades de retorno*/
		Resposta resposta = command.execute(entidade);
		
		/*Executa o método setView do view helper específico para definir como deverá ser 
		apresentado o resultado para o usuário*/
		vh.setView(resposta, request, response);
	}
}

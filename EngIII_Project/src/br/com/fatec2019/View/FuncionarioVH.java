package br.com.fatec2019.View;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.taglibs.standard.tag.common.core.NullAttributeException;
import com.sun.org.apache.xerces.internal.impl.dv.xs.DateTimeDV;

import br.com.fatec2019.Controle.Resposta;
import br.com.fatec2019.Dominio.EntidadeDominio;
import br.com.fatec2019.Dominio.Funcionario;
import br.com.fatec2019.Dominio.Cargo;
import br.com.fatec2019.Dominio.Regional;
import br.com.fatec2019.Dominio.Setor;

//classe responsável por instanciar, principalmente a 
//classe de funcionario 
public class FuncionarioVH implements IViewHelper
{	@Override public EntidadeDominio getEntidade(HttpServletRequest request) 
	{	Funcionario funcionario = null;
		Setor setor = null;
		Regional regional = null;
		Cargo cargo = null;
		DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
		switch(request.getParameter("funcionario"))//verifica qual evento do CRUD foi requisitado
		{	case "CadastrarFuncionario"://se for para cadastrar
				//atribui os valores preecnhidos nos respectivos campos , desconsiderando espaços a 
				//esquerda e direita do valor (trim())
				funcionario = new Funcionario();
				funcionario.setNome(request.getParameter("nome").trim());
				funcionario.setCpf(request.getParameter("cpf"));
				
				try//data de contratação
				{funcionario.setDataRegistro(dtf.parse(request.getParameter("data")));}
				catch(ParseException e) {}
				
				//instanciando um setor
				funcionario.setSetor(new Setor(Integer.parseInt(request.getParameter("setor"))));
				//instanciando um regional
				funcionario.setRegional(new Regional(Integer.parseInt(request.getParameter("regional"))));
				
				//Perfil
				funcionario.setCargo(new Cargo(Integer.parseInt(request.getParameter("cargo"))));
				
				funcionario.setEmail(request.getParameter("email").trim());
				funcionario.setSenha(request.getParameter("senha1").trim());
				funcionario.setSenha2(request.getParameter("senha2").trim());
				break;
				
			case "ConsultarFuncionario":
				funcionario = new Funcionario();
				try	//se o campo de codigo estiver vazio, atribua zero
				{	if(request.getParameter("matricula").equals(""))
					{funcionario.setCodigo(0);}
					
					//se os respectivos campos estiverem preenchidos, atribua-os os valores dos campos 
					else funcionario.setCodigo(Integer.parseInt(request.getParameter("matricula")));
				}
				catch(NullPointerException e) {funcionario.setCodigo(0);}
				
				try	//mesmo pensamento anterior
				{	if(request.getParameter("nome").equals(""))
					{funcionario.setNome("");}
					//idem
					else funcionario.setNome(request.getParameter("nome").trim());
				}
				catch(NullPointerException e) {funcionario.setNome("");}
				
				try	//mesmo pensamento anterior
				{	if(request.getParameter("cpf").equals(""))
					{funcionario.setCpf("");}
					//idem
					else funcionario.setCpf(request.getParameter("cpf").trim());
				}
				catch(NullPointerException e) {funcionario.setCpf("");}
				
				try//data de contratação
				{	if(!request.getParameter("data").equals(""))
					{funcionario.setDataRegistro(dtf.parse(request.getParameter("data")));}
				
					else  funcionario.setDataRegistro(dtf.parse(""));
				}
				catch(ParseException e) 
				{	try {funcionario.setDataRegistro(dtf.parse("0000-01-01"));} 
					catch (ParseException e1) {}
				}
				
				try
				{	if(request.getParameter("setor").equals("0"))
					{funcionario.setSetor(new Setor(0));}
						
					else funcionario.setSetor(new Setor(Integer.parseInt(request.getParameter("setor"))));
				}
				catch(NullPointerException e) {funcionario.setSetor(new Setor(0));}
				
				try
				{	if(request.getParameter("regional").equals("0"))
					{funcionario.setRegional(new Regional(0));}
						
					else funcionario.setRegional(new Regional(Integer.parseInt(request.getParameter("regional"))));
				}
				catch(NullPointerException e) {funcionario.setSetor(new Setor(0));}
				
				try
				{	if(!request.getParameter("cargo").equals(""))
					{funcionario.setCargo(new Cargo(Integer.parseInt(request.getParameter("cargo"))));}
				}
				catch(NullPointerException e)	{funcionario.setCargo(new Cargo(0));}
				
				try
				{	if(request.getParameter("email").equals(""))
					{funcionario.setEmail("");}
					
					else funcionario.setEmail(request.getParameter("email").trim());
				}
				catch(NullPointerException e)	{funcionario.setEmail("");}
				break;
			
			case "VisualizarFuncionario":
				funcionario = new Funcionario();
				//int codigo = Integer.parseInt(request.getParameter("id"));
				//busca do funcionario apenas pelo ID
				funcionario.setCodigo(Integer.parseInt(request.getParameter("id")));
				//não permitir valores nulos
				funcionario.setNome(request.getParameter("name"));
				funcionario.setCpf(request.getParameter("CPF"));
				funcionario.setEmail(request.getParameter("Email"));
				funcionario.setSetor(new Setor(Integer.parseInt(request.getParameter("setor2"))));
				funcionario.setRegional(new Regional(Integer.parseInt(request.getParameter("regional2"))));
				funcionario.setEmail("Email");
				funcionario.setCargo(new Cargo(Integer.parseInt(request.getParameter("cargo2"))));
				//data de contratação
				try {funcionario.setDataRegistro(dtf.parse(request.getParameter("contrato")));} 
				catch (ParseException e1) {}
				
				break;
			
			case "AlterarFuncionario":
				//atribui os valores preecnhidos nos respectivos campos , desconsiderando espaços a 
				//esquerda e direita do valor (trim())
				funcionario = new Funcionario();
				
				funcionario.setCodigo(Integer.parseInt(request.getParameter("matricula")));
				
				funcionario.setNome(request.getParameter("nome").trim());
				funcionario.setCpf(request.getParameter("cpf").trim());
				
				
				try//data de contratação
				{funcionario.setDataRegistro(dtf.parse(request.getParameter("data")));}
				catch(ParseException e) {}
				
				//instanciando um setor
				funcionario.setSetor(new Setor(Integer.parseInt(request.getParameter("setor"))));
				
				//instanciando um regional
				funcionario.setRegional(new Regional(Integer.parseInt(request.getParameter("regional"))));
				
				//Perfil
				funcionario.setCargo(new Cargo(Integer.parseInt(request.getParameter("cargo"))));
				
				funcionario.setEmail(request.getParameter("email").trim());
				funcionario.setSenha(request.getParameter("senha1").trim());
				funcionario.setSenha2(request.getParameter("senha2").trim());
				break;
				
			case "AlterarSenha":
				funcionario = new Funcionario();
				//a persistencia dos dados estão nas tags <input ... required/>
				funcionario.setCodigo(0);
				funcionario.setNome("");
				funcionario.setCpf("");
				funcionario.setSetor(new Setor(0));
				funcionario.setRegional(new Regional(0));
				funcionario.setCargo(new Cargo(0));
				//data de contratação
				try {funcionario.setDataRegistro(dtf.parse("0000-01-01"));} 
				catch (ParseException e1) {}
				funcionario.setEmail(request.getParameter("email").trim());
				funcionario.setSenha(request.getParameter("senha").trim());
				funcionario.setSenha2(request.getParameter("senha2").trim());
				break;
				
			case "LogarFuncionario":
				funcionario = new Funcionario();
				//a persistencia dos dados estão nas tags <input ... required/>
				funcionario.setCodigo(0);
				funcionario.setNome("");
				funcionario.setCpf("");
				funcionario.setCargo(new Cargo(0));
				funcionario.setSetor(new Setor(0));
				funcionario.setRegional(new Regional(0));
				try {funcionario.setDataRegistro(dtf.parse("0000-01-01"));} 
				catch (ParseException e1) {}
				funcionario.setEmail(request.getParameter("email").trim());
				funcionario.setSenha(request.getParameter("senha").trim());
				break;
			
			case "ExcluirFuncionario":
				funcionario = new Funcionario();
				try	
				{	if(!request.getParameter("id").equals(""))
					{funcionario.setCodigo(Integer.parseInt(request.getParameter("id")));}
				
					else funcionario.setCodigo(0);
				}
				catch(NullPointerException e) {funcionario.setCodigo(0);}
				break;
				
			case "InativarFuncionario":
				funcionario = new Funcionario();
				try
				{	if(request.getParameter("id").equals(""))
					{funcionario.setCodigo(0);}
					
					else funcionario.setCodigo(Integer.parseInt(request.getParameter("id")));
				}
				catch(NullPointerException e) {funcionario.setCodigo(0);}
				/*funcionario.setNome("");
				funcionario.setCpf("");
				funcionario.setSetor(new Setor(0));
				funcionario.setRegional(new Regional(0));
				funcionario.setEmail("");
				funcionario.setPerfils(new ArrayList<Perfil>());
				funcionario.getPerfils().add(new Perfil(0, funcionario));
				//data de contratação
				try {funcionario.setDataRegistro(dtf.parse("0000-01-01"));} 
				catch (ParseException e1) {}
				
				//como o comando é para inativar, significa que o funcionario esta ativo
				try
				{	if(request.getParameter("status").equals(""))
					{funcionario.setStatus("");}
				
					else	funcionario.setStatus(request.getParameter("status"));
				}
				catch(NullPointerException e1)	{funcionario.setStatus("");}*/
				break;
		}
		//retorno de um dos eventos acima
		return funcionario;
	}

	@Override
	public void setView(Resposta resposta, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
	{	RequestDispatcher rd = null;	//direciona os resultados para a página 
										//de gerenciamento de funcionarios
		
		//se não houver nenhuma resposta de erro de validação
		switch(request.getParameter("funcionario"))
		{	case "CadastrarFuncionario":
				//recebe toda a resposta para futuramente inserir 
				//na Lista de Cliente
				if(resposta.getMsg() == null)
				{	resposta.setMsg("Funcionário Cadastrado!");
					request.getSession().setAttribute("resposta", resposta);
					rd = request.getRequestDispatcher("ListaFuncionarios.jsp");
				}
					
				else
				{	request.getSession().setAttribute("resposta", resposta);
					rd = request.getRequestDispatcher("FuncForm.html");
				}
					
				break;
				
			case "AlterarFuncionario":
				if(resposta.getMsg() == null)
				{	resposta.setMsg("Dados Alterados!");
					request.getSession().setAttribute("resposta", resposta);
					rd = request.getRequestDispatcher("ListaFuncionarios.jsp");
				}
				
				request.getSession().setAttribute("resposta", resposta);
				rd = request.getRequestDispatcher("ListaFuncionarios.jsp");
				break;
					
			case "LogarFuncionario":
				if(resposta.getMsg() == null)
				{	resposta.setMsg("Seja bem vindo ao portal de Chamados!");
					request.getSession().setAttribute("resposta", resposta);
					rd = request.getRequestDispatcher("ListaFuncionarios.jsp");
				}
				
				else
				{	request.getSession().setAttribute("reposta", resposta);
					rd = request.getRequestDispatcher("login.html");
				}
				
				break;
				
			/*case "AtivarFuncionario":
				resposta.setMsg("Funcionário Ativo!");
				request.getSession().setAttribute("resposta", resposta);
				rd = request.getRequestDispatcher("ListaFuncionarios.jsp");
				break;*/
			
			case "InativarFuncionario":
				if(resposta.getMsg() == null)
					resposta.setMsg("Situação do Funcionario");
				request.getSession().setAttribute("resposta", resposta);
				rd = request.getRequestDispatcher("ListaFuncionarios.jsp");
				break;
				
			case "AlterarSenha":
				if(resposta.getMsg() == null)
				{	resposta.setMsg("Senha Alterada!");
					request.getSession().setAttribute("resposta", resposta);
					rd = request.getRequestDispatcher("ListaFuncionarios.jsp");
				}
				
				else
				{	request.getSession().setAttribute("resposta", resposta);
					rd = request.getRequestDispatcher("alterar_senha.html");
				}
				break;
			
			case "ConsultarFuncionario":
				if(resposta.getMsg() == null)
					resposta.setMsg("Dados da Consulta!");
				request.getSession().setAttribute("resposta", resposta);
				rd = request.getRequestDispatcher("ListaFuncionarios.jsp");
				break;
				
			case "VisualizarFuncionario":
				if(resposta.getMsg() == null)
				{	//mensagem a ser exibida
					resposta.setMsg("Insira os dados a serem alterados");
					request.getSession().setAttribute("resposta", resposta);
					rd = request.getRequestDispatcher("FuncForm.jsp");
				}
				
				else
				{	request.getSession().setAttribute("resposta", resposta);
					rd = request.getRequestDispatcher("ListaFuncionarios.jsp");
				}
				break;
				
			case "ExcluirFuncionario":
				if(resposta.getMsg() == null)
					resposta.setMsg("Funcionário excluído!");
				request.getSession().setAttribute("resposta", resposta);
				rd = request.getRequestDispatcher("ListaFuncionarios.jsp");
				break;
		}
		//joga a resposta para a ListaFuncionarios.jsp e exibe
		rd.forward(request, response);
	}
}

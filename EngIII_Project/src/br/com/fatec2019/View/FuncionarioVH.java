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
import br.com.fatec2019.Dominio.Usuario;

//classe respons�vel por instanciar, principalmente a 
//classe de funcionario 
public class FuncionarioVH implements IViewHelper
{	@Override public EntidadeDominio getEntidade(HttpServletRequest request) 
	{	Funcionario funcionario = null;
		Usuario usuario = null;
		Setor setor = null;
		Regional regional = null;
		Cargo cargo = null;
		DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
		switch(request.getParameter("funcionario"))//verifica qual evento do CRUD foi requisitado
		{	case "CadastrarFuncionario"://se for para cadastrar
				//atribui os valores preecnhidos nos respectivos campos , desconsiderando espa�os a 
				//esquerda e direita do valor (trim())
				usuario = new Usuario();
				usuario.setNome(request.getParameter("cf"));
				usuario.setCodigo(Integer.parseInt(request.getParameter("cf1")));
				funcionario = new Funcionario();
				funcionario.setUsuario(usuario);
				funcionario.setNome(request.getParameter("nome").trim());
				funcionario.setCpf(request.getParameter("cpf"));
				
				try//data de contrata��o
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
				usuario = new Usuario();
				usuario.setNome(request.getParameter("cf"));
				usuario.setCodigo(Integer.parseInt(request.getParameter("cf1")));
				funcionario = new Funcionario();
				funcionario.setUsuario(usuario);
				if(request.getParameter("matricula").equals("") || request.getParameter("matricula") == null)
				{funcionario.setCodigo(0);}
					
				//se os respectivos campos estiverem preenchidos, atribua-os os valores dos campos 
				else funcionario.setCodigo(Integer.parseInt(request.getParameter("matricula")));
				
				if(request.getParameter("nome").equals("") || request.getParameter("nome") == null)
				{funcionario.setNome("");}
				
				//idem
				else funcionario.setNome(request.getParameter("nome").trim());
				
				if(request.getParameter("cpf").equals("") || request.getParameter("cpf") == null)
				{funcionario.setCpf("");}
				
				//idem
				else funcionario.setCpf(request.getParameter("cpf").trim());
				
				try//data de contrata��o
				{	if(!request.getParameter("data").equals(""))
					{funcionario.setDataRegistro(dtf.parse(request.getParameter("data")));}
				
					else  funcionario.setDataRegistro(dtf.parse("1000-01-01"));
				}
				catch(ParseException e) 
				{	try {funcionario.setDataRegistro(dtf.parse("1000-01-01"));} 
					catch (ParseException e1) {}
				}
				
				if(request.getParameter("setor").equals("0"))
				{funcionario.setSetor(new Setor(0));}
						
				else funcionario.setSetor(new Setor(Integer.parseInt(request.getParameter("setor"))));
				
				if(request.getParameter("regional").equals("0"))
				{funcionario.setRegional(new Regional(0));}
						
				else funcionario.setRegional(new Regional(Integer.parseInt(request.getParameter("regional"))));
				
				if(request.getParameter("cargo").equals("0"))
				{funcionario.setCargo(new Cargo(0));}
				
				else funcionario.setCargo(new Cargo(Integer.parseInt(request.getParameter("cargo"))));
				
				if(request.getParameter("email").equals("") || request.getParameter("email") == null)
				{funcionario.setEmail("");}
					
				else funcionario.setEmail(request.getParameter("email").trim());
				
				break;
			
			case "VisualizarFuncionario":
				funcionario = new Funcionario();
				//int codigo = Integer.parseInt(request.getParameter("id"));
				//busca do funcionario apenas pelo ID
				funcionario.setCodigo(Integer.parseInt(request.getParameter("id")));
				//n�o permitir valores nulos
				funcionario.setNome(request.getParameter("name"));
				funcionario.setCpf(request.getParameter("CPF"));
				funcionario.setEmail(request.getParameter("Email"));
				funcionario.setSetor(new Setor(Integer.parseInt(request.getParameter("setor2"))));
				funcionario.setRegional(new Regional(Integer.parseInt(request.getParameter("regional2"))));
				funcionario.setEmail("Email");
				usuario = new Usuario();
				usuario.setNome(request.getParameter("cf2"));
				usuario.setCodigo(Integer.parseInt(request.getParameter("cf3")));
				funcionario.setUsuario(usuario);
				funcionario.setCargo(new Cargo(Integer.parseInt(request.getParameter("cargo2"))));
				//data de contrata��o
				try {funcionario.setDataRegistro(dtf.parse(request.getParameter("contrato")));} 
				catch (ParseException e1) {}
				
				break;
			
			case "AlterarFuncionario":
				//atribui os valores preecnhidos nos respectivos campos , desconsiderando espa�os a 
				//esquerda e direita do valor (trim())
				funcionario = new Funcionario();
				funcionario.setUsuario(usuario);
				
				funcionario.setCodigo(Integer.parseInt(request.getParameter("matricula")));
				
				funcionario.setNome(request.getParameter("nome").trim());
				funcionario.setCpf(request.getParameter("cpf").trim());
				
				
				try//data de contrata��o
				{funcionario.setDataRegistro(dtf.parse(request.getParameter("data")));}
				catch(ParseException e) {}
				
				//instanciando um setor
				funcionario.setSetor(new Setor(Integer.parseInt(request.getParameter("setor"))));
				
				//instanciando um regional
				funcionario.setRegional(new Regional(Integer.parseInt(request.getParameter("regional"))));
				
				//Perfil
				funcionario.setCargo(new Cargo(Integer.parseInt(request.getParameter("cargo"))));
				
				funcionario.setEmail(request.getParameter("email").trim());
				usuario = new Usuario();
				usuario.setNome(request.getParameter("cf"));
				usuario.setCodigo(Integer.parseInt(request.getParameter("cf1")));
				funcionario.setUsuario(usuario);
				
				funcionario.setSenha(request.getParameter("senha1").trim());
				funcionario.setSenha2(request.getParameter("senha2").trim());
				break;
				
			case "AlterarSenha":
				funcionario = new Funcionario();
				//a persistencia dos dados est�o nas tags <input ... required/>
				funcionario.setCodigo(0);
				funcionario.setNome("");
				funcionario.setCpf("");
				funcionario.setSetor(new Setor(0));
				funcionario.setRegional(new Regional(0));
				funcionario.setCargo(new Cargo(0));
				//data de contrata��o
				try {funcionario.setDataRegistro(dtf.parse("0000-01-01"));} 
				catch (ParseException e1) {}
				funcionario.setEmail(request.getParameter("email").trim());
				funcionario.setSenha(request.getParameter("senha").trim());
				funcionario.setSenha2(request.getParameter("senha2").trim());
				break;
				
			case "LogarFuncionario":
				funcionario = new Funcionario();
				//a persistencia dos dados est�o nas tags <input ... required/>
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
				usuario = new Usuario();
				usuario.setNome(funcionario.getEmail());
				funcionario.setUsuario(usuario);
				
				break;
			
			case "ExcluirFuncionario":
				usuario = new Usuario();
				usuario.setNome(request.getParameter("cf2"));
				usuario.setCodigo(Integer.parseInt(request.getParameter("cf3")));
				funcionario = new Funcionario();
				funcionario.setUsuario(usuario);
				if(!request.getParameter("id").equals("") && request.getParameter("id") != null)
				{funcionario.setCodigo(Integer.parseInt(request.getParameter("id")));}
				
				else funcionario.setCodigo(0);
				
				break;
				
			case "FormularFuncionario":
				funcionario = new Funcionario();
				usuario = new Usuario();
				if(request.getParameter("cf").equals("") || request.getParameter("cf") == null)
				{usuario.setNome("");}
				
				else usuario.setNome(request.getParameter("cf"));
				
				if(request.getParameter("cf1").equals("") || request.getParameter("cf1") == null)
				{usuario.setCodigo(0);}
				
				else usuario.setCodigo(Integer.parseInt(request.getParameter("cf1")));
				
				funcionario.setUsuario(usuario);
				
				break;
				
			case "InativarFuncionario":
				usuario = new Usuario();
				usuario.setNome(request.getParameter("cf2"));
				usuario.setCodigo(Integer.parseInt(request.getParameter("cf3")));
				
				funcionario = new Funcionario();
				funcionario.setUsuario(usuario);
				
				if(request.getParameter("id").equals("") || request.getParameter("id") == null)
				{funcionario.setCodigo(0);}
					
				else funcionario.setCodigo(Integer.parseInt(request.getParameter("id")));
				/*funcionario.setNome("");
				funcionario.setCpf("");
				funcionario.setSetor(new Setor(0));
				funcionario.setRegional(new Regional(0));
				funcionario.setEmail("");
				funcionario.setPerfils(new ArrayList<Perfil>());
				funcionario.getPerfils().add(new Perfil(0, funcionario));
				//data de contrata��o
				try {funcionario.setDataRegistro(dtf.parse("0000-01-01"));} 
				catch (ParseException e1) {}
				
				//como o comando � para inativar, significa que o funcionario esta ativo
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
	{	RequestDispatcher rd = null;	//direciona os resultados para a p�gina 
										//de gerenciamento de funcionarios
		
		//se n�o houver nenhuma resposta de erro de valida��o
		switch(request.getParameter("funcionario"))
		{	case "CadastrarFuncionario":
				//recebe toda a resposta para futuramente inserir 
				//na Lista de Cliente
				if(resposta.getMsg() == null)
				{	resposta.setMsg("Funcion�rio Cadastrado!");
					request.getSession().setAttribute("resposta", resposta);
					rd = request.getRequestDispatcher("ListaFuncionarios.jsp");
				}
					
				else
				{	request.getSession().setAttribute("resposta", resposta);
					rd = request.getRequestDispatcher("CadFunc.jsp");
				}
					
				break;
				
			case "AlterarFuncionario":
				if(resposta.getMsg() == null)
				{	resposta.setMsg("Dados Alterados!");
					request.getSession().setAttribute("resposta", resposta);
					rd = request.getRequestDispatcher("ListaFuncionarios.jsp");
				}
				
				else
				{	request.getSession().setAttribute("resposta", resposta);
					rd = request.getRequestDispatcher("FuncForm.jsp");
				}
				break;
					
			case "LogarFuncionario":
				if(resposta.getMsg() == null)
				{	resposta.setMsg("Seja bem vindo ao portal de Chamados!");
					request.getSession().setAttribute("resposta", resposta);
					rd = request.getRequestDispatcher("ListaFuncionarios.jsp");
				}
				
				else
				{	request.getSession().setAttribute("resposta", resposta);
					rd = request.getRequestDispatcher("login.jsp");
				}
				
				break;
				
			/*case "AtivarFuncionario":
				resposta.setMsg("Funcion�rio Ativo!");
				request.getSession().setAttribute("resposta", resposta);
				rd = request.getRequestDispatcher("ListaFuncionarios.jsp");
				break;*/
			
			case "InativarFuncionario":
				if(resposta.getMsg() == null)
					resposta.setMsg("Situa��o do Funcionario");
				request.getSession().setAttribute("resposta", resposta);
				rd = request.getRequestDispatcher("ListaFuncionarios.jsp");
				break;
				
			case "AlterarSenha":
				if(resposta.getMsg() == null)
				{resposta.setMsg("Senha Alterada!");}
				
				request.getSession().setAttribute("resposta", resposta);
				rd = request.getRequestDispatcher("login.jsp");
				
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
				
			case "FormularFuncionario":
				resposta.setMsg("Insira os dados a serem alterados");
				request.getSession().setAttribute("resposta", resposta);
				rd = request.getRequestDispatcher("CadFunc.jsp");
				break;
				
			case "ExcluirFuncionario":
				if(resposta.getMsg() == null)
					resposta.setMsg("Funcion�rio exclu�do!");
				request.getSession().setAttribute("resposta", resposta);
				rd = request.getRequestDispatcher("ListaFuncionarios.jsp");
				break;
		}
		//joga a resposta para a ListaFuncionarios.jsp e exibe
		rd.forward(request, response);
	}
}

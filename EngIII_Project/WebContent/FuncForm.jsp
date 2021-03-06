<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--Importação de Lista e pacote onde estão as classes de dominio e controle-->
<%@ page import = "java.util.*,br.com.fatec2019.Dominio.*" %>
<%@ page import = "java.util.*,br.com.fatec2019.Controle.*" %>
<%@ page import = "java.text.*" %>
<!--possibilita usar a tag core, que chama o looping forEach-->    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Atualizar Funcionario</title>
		<!-- Bootstrap -->
    	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements
    	     and media queries -->
	    <!-- WARNING: Respond.js doesn't work if you view the page via
	         file://-->
	    <!--[if lt IE 9]>
	    <script src=
	      "https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js">
	    </script>
	    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js">
	    </script>
	    <![endif]-->
	    <link href="CSS/FuncForm.css" rel="stylesheet">
	</head>
	<body>
		<div>
    		<nav class="navbar navbar-fixed-top navbar-inverse navbar-transparente">
				<div class="container">
					<!-- Estrutura de Header -->
					<div class="navbar-header">
						<!-- na classe foi inserido o mesmo nome dado a
							classe da <div></div> para linkar as listas
							ao botão -->
						<button type="button" class="navbar-toggle collapsed" 
							data-toggle="collapse" data-target="#barra-navegacao">
							<!-- botão que aparece quando a tela fica menor
								de forma que não fique visível alguns componentes -->
							<span class="sr-only">Alternar navegação</span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
						</button>
					</div>
					<!-- compatibilidade para dispositivos menores-->
					<div class="collapse navbar-collapse" id="barra-navegacao">
						<h2 class="barra">
							<b id=titulo>Sistema de Atendimento</b><br/>
							<%  Resposta resposta = (Resposta)session.getAttribute("resposta");
								String user_email = "";
								int user_id = 0;
								if(resposta != null && resposta.getEntidades() != null &&
									!resposta.getEntidades().isEmpty())
								{	for(EntidadeDominio ed:resposta.getEntidades())
									{	if(ed instanceof Usuario)
										{	user_email = ed.getNome();
											user_id = ed.getCodigo();
											out.print(user_email);
										}
									}
								}
							%>
						</h2>
						<!-- barra do link abaixo a direita. -->
						<ul class="nav navbar-nav navbar-right">
							<li><a class="barra-direita" href="FuncForm.jsp">Funcionarios</a></li>
							<li><a class="barra-direita" href="#">Categorias</a></li>
							<li><a class="barra-direita" href="#">Chamados</a></li>
							<li><a class="barra-direita" href="login.jsp">Sair</a></li>
						</ul>
					</div>
				</div>
			</nav>
    	</div>
    	<div id="form" align="center">
    		<%	StringBuilder sbRegistro = null;
    			if(resposta != null && resposta.getMsg() != null)	
				{	if(resposta.getMsg().equals("Insira os dados a serem alterados"))
					{out.print("<div class='alert alert-info' align='center'>" + resposta.getMsg() + "</div>");}
				
					else if(resposta.getMsg().contains("Preencha pelo menos um campo") ||
							resposta.getMsg().contains("CPF inválido") ||
							resposta.getMsg().contains("Regional inválido") ||
							resposta.getMsg().contains("Setor inválido") ||
							resposta.getMsg().contains("Cargo inválido") ||
							resposta.getMsg().contains("E-mail incorreto") ||
							resposta.getMsg().contains("Senha inválida") ||
							resposta.getMsg().contains("Senhas não batem"))
					{out.print("<div class='alert alert-danger' align='center'>" + resposta.getMsg() + "</div>");}
				}
			%>
			<fieldset>
    			<legend><i><b>Alteração de Funcionários</b></i></legend>
		        <form action="MyServlet" method="POST">
		            <input type="hidden" id="cf" name="cf" value="<% out.print(user_email);%>"/>
					<input type="hidden" id="cf1" name="cf1" value="<% out.print(user_id);%>"/>
					<table>
						<td style="display:none;">
		            	<input type="hidden" name="matricula" value="<%	if(resposta != null && resposta.getMsg() != null)
												                    	{	for(EntidadeDominio ed:resposta.getEntidades())
													                    	{	if(ed instanceof Funcionario)
														                    	{out.print(ed.getCodigo());}
													                    	}
												                    	}
		            												%>"/>
		            	</td>
		            	<tr>
		                    <td><input type="text" name="nome" id="nome" class="form-control" 
		                    	size="50" placeholder="Nome completo" required
		                    	value="<% 	if(resposta != null && resposta.getMsg() != null)
					                    	{	for(EntidadeDominio ed:resposta.getEntidades())
						                    	{	if(ed instanceof Funcionario)
							                    	{out.print(ed.getNome());}
						                    	}
					                    	}
		                    			%>"/>
		                    </td>
		                    <td><input type="number" name="cpf" id="cpf" class="form-control" 
		                    	size="50" placeholder="Número de CPF" required
		                    	value="<% 	if(resposta != null && resposta.getMsg() != null)
					                    	{	for(EntidadeDominio ed:resposta.getEntidades())
						                    	{	if(ed instanceof Funcionario)
							                    	{	Funcionario funcionario = (Funcionario)ed;
							                    		out.print(funcionario.getCpf());
							                    	}
						                    	}
					                    	}
		                    			%>"/>
		                    </td>
		                    <td><input type="email" name="email" id="email" placeholder="e-mail" 
		                         size="50" class="form-control" required
		                    	value="<% 	if(resposta != null && resposta.getMsg() != null)
					                    	{	for(EntidadeDominio ed:resposta.getEntidades())
						                    	{	if(ed instanceof Funcionario)
							                    	{	Funcionario funcionario = (Funcionario)ed;
							                    		out.print(funcionario.getEmail());
							                    	}
						                    	}
					                    	}
		                    			%>"/>
		                    </td>
		                </tr>
		                <tr>
		                    <td align="center">Regional:<br/>
		                    	<select name="regional" id="regional" class="form-control">
		                    		<%	if(resposta != null)
										{	Funcionario funcionario = null;
		                    				for(EntidadeDominio ed:resposta.getEntidades())
											{	if(ed instanceof Funcionario)
												{funcionario = (Funcionario)ed;}
												else if(ed instanceof Regional)
												{	sbRegistro = new StringBuilder();
													sbRegistro.setLength(0);
													if(funcionario.getRegional().getCodigo() == ed.getCodigo())
													{	sbRegistro.append("<option value = '" + 
														ed.getCodigo() + "' selected>" + 
														ed.getNome() +"</option>");
													}
													else
													{	sbRegistro.append("<option value = '" + 
														ed.getCodigo() + "'>" + 
														ed.getNome() +"</option>");
													}
													out.print(sbRegistro.toString());
												}
											}
										}
									%>
		                    		<!-- <option value = "1">Zona Sul</option>
		                    		<option value = "2">Zona Leste</option>
		                    		<option value = "3">Zona Norte</option>
		                    		<option value = "4">Zona Oeste</option>
		                    		<option value = "5">Mogi das Cruzes</option> -->
		                    	</select>
		                    </td>
		                    <td align="center">Setor:<br/>
		                    	<select name="setor" id="setor" class="form-control">
		                    		<%	if(resposta != null)
										{	Funcionario funcionario = null;
	                    					for(EntidadeDominio ed:resposta.getEntidades())
											{	if(ed instanceof Funcionario)
												{funcionario = (Funcionario)ed;}
												else if(ed instanceof Setor)
												{	sbRegistro = new StringBuilder();
													sbRegistro.setLength(0);
													if(funcionario.getSetor().getCodigo() == ed.getCodigo())
													{	sbRegistro.append("<option value = '" + 
														ed.getCodigo() + "' selected>" + 
														ed.getNome() +"</option>");
													}
													else
													{	sbRegistro.append("<option value = '" + 
														ed.getCodigo() + "'>" + 
														ed.getNome() +"</option>");
													}
													out.print(sbRegistro.toString());
												}
											}
										}
									%>
		                    		<!-- <option value = "1">Recursos Humanos</option>
		                    		<option value = "2">Tecnologia da Informacao</option>
		                    		<option value = "3">Suprimentos</option>
		                    		<option value = "4">Marketing</option>
		                    		<option value = "5">Pos Venda</option>
		                    		<option value = "6">Engenharia</option> -->
		                    	</select>
		                    </td>
		                    <td align="center">Cargo:<br/>
								<select name="cargo" id="cargo" class="form-control">
		                    		<%	if(resposta != null)
										{	Funcionario funcionario = null;
                    						for(EntidadeDominio ed:resposta.getEntidades())
											{	if(ed instanceof Funcionario)
												{funcionario = (Funcionario)ed;}
												else if(ed instanceof Cargo)
												{	sbRegistro = new StringBuilder();
													sbRegistro.setLength(0);
													if(funcionario.getCargo().getCodigo() == ed.getCodigo())
													{	sbRegistro.append("<option value = '" + 
														ed.getCodigo() + "' selected>" + 
														ed.getNome() +"</option>");
													}
													else
													{	sbRegistro.append("<option value = '" + 
														ed.getCodigo() + "'>" + 
														ed.getNome() +"</option>");
													}
													out.print(sbRegistro.toString());
												}
											}
										}
									%>
		                    		<!-- <option value = "1">Engenheiro</option>
		                    		<option value = "2">Secretario</option>
		                    		<option value = "3">Operador</option>
		                    		<option value = "4">Desenvolvedor</option>
		                    		<option value = "5">Analista</option> -->
		                    	</select>
							</td>
		                </tr>
		                <tr>
							<td align="center">Senha:<br/>
								<input type="password" name="senha1" id="senha1" 
									placeholder="8 dig., 1 Caracter especial, 1 letra maicuscula e minuscula" 
									required class="form-control" value="<%	if(resposta != null)
																			{	for(EntidadeDominio ed:resposta.getEntidades())
																				{	if(ed instanceof Funcionario)
																					{	Funcionario funcionario = (Funcionario)ed;
																						out.print(funcionario.getSenha());
																						break;
																					}
																				}
																			}
																			%>"/>
							</td>
							<td align="center">Confirme a senha:<br/>
								<input type="password" name="senha2" id="senha2" placeholder="Repita senha" 
									 required class="form-control" value="<%if(resposta != null)
																			{	for(EntidadeDominio ed:resposta.getEntidades())
																				{	if(ed instanceof Funcionario)
																					{	Funcionario funcionario = (Funcionario)ed;
																						out.print(funcionario.getSenha());
																						break;
																					}
																				}
																			}
																			%>"/>
							</td>
							<td align="center">Contratado em:<br/>
		                    	<input type="date" name="data" id="data" class="form-control" 
		                    		placeholder="Contratado em" required value="<%	if(resposta != null)
																					{	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			                    														for(EntidadeDominio ed:resposta.getEntidades())
																						{	if(ed instanceof Funcionario)
																							{	Funcionario funcionario = (Funcionario)ed;
																								out.print(df.format(funcionario.getDataRegistro()));
																								break;
																							}
																						}
																					}
																				%>"/>
		                    </td>
						</tr>
						<tr>
							<td><input type="hidden" class="form-control"/></td>
		                    <td align="center"><br/>
		                    	<button type="submit" class="btn btn-primary form-control" name="funcionario" 
		                    		value="AlterarFuncionario"><img src="imagens/edit_pencil_6320.png"/>	Alterar</button>
		                    
						</tr>
		            </table>
		        </form>
			</fieldset>
		</div>
		<div id="rodape2">
	    	<footer id="rodape">
			    <div class="container">
			    	<div class="row">
			    		<div class="col-md-2">
			    			<h4>A Empresa</h4>
			    			<ul class="nav">
			    				<li class="item"><a href="#">Sobre</a></li>
			    				<li class="item"><a href="#">Empregos</a></li>
			    				<li class="item"><a href="#">Imprensa</a></li>
			    			</ul>
			    		</div>
			    		<div class="col-md-2">
			    			<h4>Comunidades</h4>
			    			<ul class="nav">
			    				<li class="item"><a href="#">Artistas</a></li>
			    				<li class="item"><a href="#">Desenvolvedores</a></li>
			    				<li class="item"><a href="#">Portfólio</a></li>
			    			</ul>
			    		</div>
			    		<div class="col-md-4">
			    			<ul class="nav">
			    				<li class="item-rede-social"><a href="https://www.linkedin.com/in/emurakoshi/">
			    					<img src="imagens/linkedin.png" class="img-circle">
			    				</a></li>
			    				<li class="item-rede-social"><a href="https://github.com/eduardowmu">
			    					<img src="imagens/github.png" class="img-circle"><br/>
			    					Meus<br/>Projetos
			    				</a></li>
			    				<li class="item-rede-social"><a href="https://www.linkedin.com/in/emurakoshi/">
			    					<img src="imagens/eu.png" class="img-circle">
			    					<br/>
			    					Eduardo<br/>
			    					Full Stack<br/>
			    					developer
			    				</a></li>
			    			</ul>
			    		</div>
			    	</div>
			    	<div align="center"><p id="copy">&copy; 2019 - Fatec de Mogi das Cruzes</p></div>
			    </div>
			</footer>
	    </div>
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<!-- Include all compiled plugins (below), or include individual files as needed -->
		<script src="bootstrap/js/bootstrap.min.js"></script>
	</body>
</html>
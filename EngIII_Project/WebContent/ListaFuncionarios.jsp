<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--Importação de Lista e pacote onde estão as classes de dominio e controle-->
<%@ page import = "java.util.*,br.com.fatec2019.Dominio.*" %>
<%@ page import = "java.util.*,br.com.fatec2019.Controle.*" %>
<%@ page import = "java.util.*,br.com.fatec2019.DAO.*" %>
<%@ page import = "java.text.*" %>
<!--possibilita usar a tag core, que chama o looping forEach-->    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta charset="UTF-8">
		<title>Funcionarios</title>
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
	    <script
		  src="https://code.jquery.com/jquery-3.4.1.min.js"
		  integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
		  crossorigin="anonymous"></script>
	    <script>
	    	$(document).ready(function() {	
	    		//var confirmouSla = false;
	    		$(".btn-excluir").on("click", function(e) {	
	    			//if (!confirmouSla) {	
	    				e.preventDefault();
		    			
		    			var s = confirm("Tem certeza que deseja excluir?");
		    			if(s) {	
		    				//confirmouSla = true;
		    				$(this).click();
		    			}
	    			} 
	    			//else {$(this).click();}
	    		});
	    	});
	    </script>
	    <link href="CSS/ListaFuncionarios.css" rel="stylesheet">
	    <script src="scripts.js"></script>
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
							<span class="icon-bar"></span>
						</button>
					</div>
					<!-- compatibilidade para dispositivos menores-->
					<div class="collapse navbar-collapse" id="barra-navegacao">
						<h2 class="barra">
							<b id=titulo>Sistema de Atendimento</b><br/>
							<%  Resposta resposta = (Resposta)session.getAttribute("resposta");
								if(resposta != null && resposta.getEntidades() != null &&
									!resposta.getEntidades().isEmpty())
								{	for(EntidadeDominio ed:resposta.getEntidades())
									{	if(ed instanceof Usuario)
										{	out.print(ed.getNome());
											break;
										}
									}
								}
							%>
						</h2>
						<!-- barra do link abaixo a direita. -->
						<ul class="nav navbar-nav navbar-right">
							<li><a class="barra-direita" href="#">Funcionarios</a></li>
							<li><a class="barra-direita" href="#">Categorias</a></li>
							<li><a class="barra-direita" href="#">Chamados</a></li>
							<li><a class="barra-direita" href="login.jsp">Sair</a></li>
						</ul>
					</div>
				</div>
			</nav>
    	</div>
		<div id="form">
			<%	StringBuilder sbRegistro = null;
				if(resposta != null && resposta.getMsg() != null)
				{	if(resposta != null && (resposta.getMsg().equals("Funcionário Cadastrado!") ||
						resposta.getMsg().equals("Dados da Consulta!") || 
						resposta.getMsg().equals("Funcionário excluído!") || 
						resposta.getMsg().equals("Situação do Funcionario") || 
						resposta.getMsg().equals("Seja bem vindo ao portal de Chamados!") ||
						resposta.getMsg().equals("Senha Alterada!") ||
						resposta.getMsg().equals("Dados Alterados!")))
					{out.print("<div class='alert alert-success' align='center'>" + resposta.getMsg() + "</div>");}
				
					else out.print("<div class='alert alert-warning' align='center'>" + resposta.getMsg() + "</div>");
				}
			%>
			<form action="MyServlet" method="get">
				<table align="center">
					<thead>
					<tr>
						<td class="formulario"><br/>
							<input type="number" name="matricula" placeholder="matrícula (Somente números)" 
								size="30" class="form-control"/>
						</td>
						<td class="formulario"><br/>
							<input type="text" name="nome" placeholder="Nome Completo" 
								size="30" class="form-control"/>
						</td>
						<td class="formulario"><br/>
							<input type="number" name="cpf" placeholder="Numero de CPF" 
								size="30" class="form-control"/>
						</td>
						<td class="formulario"><br/>
							<input type="text" name="email" placeholder="e-mail" 
								size="30" class="form-control"/>
						</td>
					</tr>
					</thead>
					<tr>
						<td class="formulario">Contratado em:<br/>
							<input type="date" name="data" width="90px" class="form-control""/>
						</td>
						<td class="formulario"><br/>
							<select name="setor" id="setor" class="form-control">
								<option value = "0">Setor</option>
								<%	if(resposta != null && resposta.getEntidades() != null)
									{	for(EntidadeDominio ed:resposta.getEntidades())
										{	if(ed instanceof Setor)
											{	sbRegistro = new StringBuilder();
												sbRegistro.setLength(0);
												sbRegistro.append("<option value = '" + ed.getCodigo() + "'>" + 
												ed.getNome() +"</option>");
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
						<td class="formulario"><br/>
							<select name="regional" id="regional" class="form-control">
		                    	<option value="0">Regional</option>
								<%	if(resposta != null && resposta.getEntidades() != null)
									{	for(EntidadeDominio ed:resposta.getEntidades())
										{	if(ed instanceof Regional)
											{	sbRegistro = new StringBuilder();
												sbRegistro.setLength(0);
												sbRegistro.append("<option value = '" + ed.getCodigo() + "'>" + 
												ed.getNome() +"</option>");
												out.print(sbRegistro.toString());
											}
										}
									}
								%>
								<!-- <option value="1">Zona Sul</option>
		                    	<option value="2">Zona Leste</option>
		                    	<option value="3">Zona Norte</option>
		                    	<option value="4">Zona Oeste</option>
		                    	<option value="5">Mogi das Cruzes</option> -->
		                    </select>
						</td>
						<td class="formulario"><br/>
							<select name="cargo" id="cargo" class="form-control">
		                    	<option value="0">Cargo</option>
								<%	if(resposta != null && resposta.getEntidades() != null)
									{	for(EntidadeDominio ed:resposta.getEntidades())
										{	if(ed instanceof Cargo)
											{	sbRegistro = new StringBuilder();
												sbRegistro.setLength(0);
												sbRegistro.append("<option value = '" + ed.getCodigo() + "'>" + 
												ed.getNome() +"</option>");
												out.print(sbRegistro.toString());
											}
										}
									}
								%>
								<!-- <option value="1">Engenheiro</option>
		                    	<option value="2">Secretario</option>
		                    	<option value="3">Operador</option>
		                    	<option value="4">Desenvolvedor</option>
		                    	<option value="5">Analista</option> -->
		                    </select>
						</td>
					</tr>
				</table>
				<table align="center">
					<tr>
						<%	String user_email = "";
							int user_id = 0;
						 %>
						<td style="display:none;">
						<input type="hidden" id="cf" name="cf" 
														value="<%	if(resposta != null && resposta.getEntidades() != null)
																	{	if(!resposta.getEntidades().isEmpty())
																		{	for(EntidadeDominio ed:resposta.getEntidades())
																			{	if(ed instanceof Usuario)
																				{	user_email = ed.getNome();
																					user_id = ed.getCodigo();
																					break;
																				}
																			}
																		}
																	}
																	out.print(user_email);
																%>"/>
						<%out.print("<input type='hidden' id='cf1' name='cf1' value='"+user_id+"'/>"); %>
						</td>
						<td class="formulario"><br/>
							<button type="submit" name="funcionario" value="ConsultarFuncionario" 
								class="btn btn-primary form-control">Consultar</button>
						</td>
						<td class="formulario"><br/>
							<button type="submit" name="funcionario" value="FormularFuncionario" 
								class="btn btn-success form-control">Cadastrar</button>
						</td>
					</tr>
				</table>
			</div><br/>
			</form>
			<div>
				<table class="table table-striped table-bordered table-hover table-condensed">
					<tr align="center">
						<td class="tabela"><b>Matricula</b></td>
						<td class="tabela"><b>Nome</b></td>
						<td class="tabela"><b>CPF</b></td>
						<td class="tabela"><b>Admissão</b></td>
						<td class="tabela"><b>Setor</b></td>
						<td class="tabela"><b>Cargo</b></td>
						<td class="tabela"><b>Regional</b></td>
						<td class="tabela"><b>E-mail</b></td>
						<td class="tabela"><b>Status</b></td>
						<td class="tabela"><b>Data do cadastro</b></td>
						<td class="tabela"><b>Excluir</b></td>
						<td class="tabela"><b>Editar</b></td>
						<td class="tabela"><b>Ativar/<br/>Inativar</b></td>
					</tr>
					<%
						if(resposta != null && (resposta.getMsg().equals("Funcionário Cadastrado!") ||
							resposta.getMsg().equals("Dados da Consulta!") || 
							resposta.getMsg().equals("Funcionário excluído!") || 
							resposta.getMsg().equals("Situação do Funcionario") || 
							resposta.getMsg().equals("Seja bem vindo ao portal de Chamados!") ||
							resposta.getMsg().equals("Senha Alterada!") ||
							resposta.getMsg().equals("Dados Alterados!")))
						{	if(!resposta.getEntidades().isEmpty())//looping de funcionarios
							{	List<EntidadeDominio> entidades = resposta.getEntidades();
								//se houver funcionario(s)
								for(int i = 0; i < entidades.size(); i++)
								{	if(entidades.get(i) instanceof Funcionario)
									{	Funcionario funcionario = (Funcionario)entidades.get(i);
										sbRegistro = new StringBuilder();
										StringBuilder sbLink = new StringBuilder();
										sbRegistro.setLength(0);
										sbLink.setLength(0);
										//dados do funcionario cadastrado
										sbRegistro.append("<form action='MyServlet' id='myform' method='post'><tr>");
										sbRegistro.append("<input type='hidden' id='cf2' name='cf2' value='" + user_email + "'/>");
										sbRegistro.append("<input type='hidden' id='cf3' name='cf3' value='" + user_id + "'/>");
										sbRegistro.append("<td class='linha' align='center'>");
										sbRegistro.append("<input type='hidden' name='id' value='" + 
											funcionario.getCodigo() + "'/>");
										sbRegistro.append(" "+funcionario.getCodigo() + "</td>");
										sbRegistro.append("<td class='linha' align='center'>");
										sbRegistro.append("<input type='hidden' name='name' value='" + 
												funcionario.getNome() + "'/>");
										sbRegistro.append(funcionario.getNome());
										sbRegistro.append("</td>");
										sbRegistro.append("<td class='linha' align='center'>");
										sbRegistro.append("<input type='hidden' name='CPF' value='" + 
												funcionario.getCpf() + "'/>");
										sbRegistro.append(funcionario.getCpf());
										sbRegistro.append("</td>");
										sbRegistro.append("<td class='linha' align='center'>");
										DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
										sbRegistro.append("<input type='hidden' name='contrato' value='" + 
												df.format(funcionario.getDataRegistro()) + "'/>");
										sbRegistro.append(String.valueOf(funcionario.DateToString(funcionario.getDataRegistro())));
										sbRegistro.append("</td>");
										sbRegistro.append("<td class='linha' align='center'>");
										sbRegistro.append("<input type='hidden' name='setor2' value='" + 
												funcionario.getSetor().getCodigo() + "'/>");
										sbRegistro.append(funcionario.getSetor().getNome());
										sbRegistro.append("</td>");
										sbRegistro.append("<td class='linha' align='center'>");
										sbRegistro.append("<input type='hidden' name='cargo2' value='" + 
												funcionario.getCargo().getCodigo() + "'/>");
										sbRegistro.append(funcionario.getCargo().getNome());
										sbRegistro.append("</td>");
										sbRegistro.append("<td class='linha' align='center'>");
										sbRegistro.append("<input type='hidden' name='regional2' value='" + 
												funcionario.getRegional().getCodigo() + "'/>");
										sbRegistro.append(funcionario.getRegional().getNome());
										sbRegistro.append("</td>");
										sbRegistro.append("<td class='linha' align='center'>");
										sbRegistro.append("<input type='hidden' name='Email' value='" + 
												funcionario.getEmail() + "'/>");
										sbRegistro.append(funcionario.getEmail());
										sbRegistro.append("</td>");
										sbRegistro.append("<td class='linha' align='center'>");
										sbRegistro.append("<input type='hidden' name='status' value='" +
												funcionario.getStatus() + "'/>");
										if(funcionario.getStatus().equals("ativo"))	
										{sbRegistro.append("<font color='green'>");}
										else sbRegistro.append("<font color='red'>");
										sbRegistro.append(funcionario.getStatus().toUpperCase() + "</font>");
										sbRegistro.append("</td>");
										sbRegistro.append("<td class='linha' align='center'>");
										sbRegistro.append(funcionario.getDataCadastro());
										sbRegistro.append("</td>");
										sbRegistro.append("<td align='center'>");
										if(funcionario.getCodigo() == user_id)
										{	sbRegistro.append("<img id='lixo' src='imagens/seo-social-web-network-internet_262_icon-icons.com_61518.png'/>");
											sbRegistro.append("</td>");
										}
										else
										{	sbRegistro.append("<button type='submit' class='btn btn-link acao form-control btn-excluir' name='funcionario' id='funcionario' value='ExcluirFuncionario'>" +
												"<img src='imagens/seo-social-web-network-internet_262_icon-icons.com_61518.png'/></button>");
											sbRegistro.append("</td>");
										}
										sbRegistro.append("<td align='center'>");
										sbRegistro.append("<button type='submit' class='btn btn-link acao form-control' name='funcionario' id='funcionario' value='VisualizarFuncionario'>" +
															"<img src='imagens/edit_pencil_6320.png'/></button>");
										sbRegistro.append("</td>");
										sbRegistro.append("<td align='center'>");
										sbRegistro.append("<button type='submit' class='btn btn-link acao form-control' name='funcionario' id='funcionario' value='InativarFuncionario'>" +
															"<img src='imagens/disable_remove_delete_exit_close_11881.png'/></button>");
										sbRegistro.append("</td>");
										sbRegistro.append("</tr></form>");
										//escrever todos as tags e valores armazenadas no sbRegistro.
										out.print(sbRegistro.toString());
									}
									
								}
							
							}
						}
					%>					
				</table>
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
			    				<li class="item-rede-social"><a href="https://www.linkedin.com/school/fatec-mogi-das-cruzes/about/">
			    					<img src="imagens/linkedin.png" class="img-circle">
			    				</a></li>
			    				<li class="item-rede-social"><a href="https://www.linkedin.com/in/junior-cesar-57710a133/">
			    					<img src="imagens/Erinilson.png" class="img-circle">
			    					<br/>
			    					Erinilson<br/>
			    					Business<br/>
			    					Analisty
			    				</a></li><li class="item-rede-social"><a href="https://www.linkedin.com/in/emurakoshi/">
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
	</body>
</html>
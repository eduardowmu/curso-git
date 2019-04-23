<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--Importação de Lista e pacote onde estão as classes de dominio e controle-->
<%@ page import = "java.util.*,br.com.fatec2019.Dominio.*" %>
<%@ page import = "java.util.*,br.com.fatec2019.Controle.*" %>
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
						<h2 class="barra"><b id=titulo>Sistema de Atendimento</b></h2>
						<!-- barra do link abaixo a direita. -->
						<ul class="nav navbar-nav navbar-right">
							<li><a class="barra-direita" href="FuncForm.html">Funcionarios</a></li>
							<li><a class="barra-direita" href="#">Categorias</a></li>
							<li><a class="barra-direita" href="#">Chamados</a></li>
							<li><a class="barra-direita" href="login.html">Sair</a></li>
						</ul>
					</div>
				</div>
			</nav>
    	</div>
    	<div id="form" align="center">
    		<%	
    			Resposta resposta = (Resposta)session.getAttribute("resposta");
				//if(resposta != null && resposta.getMsg() != null)	out.print(resposta.getMsg()); 
			%>
			<fieldset>
    			<legend><i><b>Alteração de Funcionários</b></i></legend>
		        <form action="MyServlet" method="POST">
		            <table>
		                <tr>
		                    
		                    	<!-- se houver response de dados de algum request -->
		                    	<%	if(resposta != null && (!resposta.getMsg().equals("")))
		                    		{	List<EntidadeDominio> entidades = resposta.getEntidades();
										StringBuilder sbRegistro = new StringBuilder();
										StringBuilder sbLink = new StringBuilder();
										//se houver funcionario(s)
										if(entidades != null)//looping de funcionarios
										{	for(int i = 0; i < entidades.size(); i++)
											{	Funcionario funcionario = (Funcionario)entidades.get(i);
												sbRegistro.setLength(0);
												sbLink.setLength(0);
												//dados do funcionario cadastrado
												sbRegistro.append("<input type='hidden' name='matricula' value='" + 
																funcionario.getCodigo() + "'/>");
												sbRegistro.append("<td align='center'>Nome Completo:<br/><input type='text' name='nome' id='nome' class='form-control' " +
																	"placeholder='Nome completo' size='40' required value='" + 
																	funcionario.getNome() + "'/></td>");
												sbRegistro.append("<td align='center'>CPF:<br/><input type='number' name='cpf' id='cpf' class='form-control'" +
														"placeholder='CPF (só numeros)' size='40' required value='" + 
														funcionario.getCpf() + "'/></td>");
												sbRegistro.append("<td align='center'>E-mail:<br/><input type='email' name='email' id='email'" + 
														  " placeholder='e-mail' class='form-control' size='40' " + 
														  "required value='" + funcionario.getEmail() +"'/></td></tr>");
												sbRegistro.append("<tr><td align='center'>Setor:<br/><select name='setor' class='form-control'>");
												if(funcionario.getSetor().getCodigo() == 1)
												{sbRegistro.append("<option value='1' selected>Recursos Humanos</option>");}
												else sbRegistro.append("<option value='1'>Recursos Humanos</option>");
												if(funcionario.getSetor().getCodigo() == 2)
												{sbRegistro.append("<option value='2' selected>Tecnologia da Informacao</option>");}
												else sbRegistro.append("<option value='2'>Tecnologia da Informacao</option>");
												if(funcionario.getSetor().getCodigo() == 3)
												{sbRegistro.append("<option value='3' selected>Suprimentos</option>");}
												else sbRegistro.append("<option value='3'>Suprimentos</option>");
												if(funcionario.getSetor().getCodigo() == 4)
												{sbRegistro.append("<option value='4' selected>Marketing</option>");}
												else sbRegistro.append("<option value='4'>Marketing</option>");
												if(funcionario.getSetor().getCodigo() == 5)
												{sbRegistro.append("<option value='5' selected>Pos Venda</option>");}
												else sbRegistro.append("<option value='5'>Pos Venda</option>");
												if(funcionario.getSetor().getCodigo() == 6)
												{sbRegistro.append("<option value='6' selected>Engenharia</option></select></td>");}
												else sbRegistro.append("<option value='6'>Engenharia</option></select></td>");
												sbRegistro.append("<td align='center'>Cargo:<br/><select name='cargo' id='cargo' class='form-control'>");
												if(funcionario.getCargo().getCodigo() == 1)
												{sbRegistro.append("<option value='1' selected>Engenheiro</option>");}
												else sbRegistro.append("<option value='1'>Engenheiro</option>");
												if(funcionario.getCargo().getCodigo() == 2)
												{sbRegistro.append("<option value='2' selected>Secretario</option>");}
												else sbRegistro.append("<option value='2'>Secretario</option>");
												if(funcionario.getCargo().getCodigo() == 3)
												{sbRegistro.append("<option value='3' selected>Operador</option>");}
												else sbRegistro.append("<option value='3'>Operador</option>");
												if(funcionario.getCargo().getCodigo() == 4)
												{sbRegistro.append("<option value='4' selected>Desenvolvedor</option>");}
												else sbRegistro.append("<option value='4'>Desenvolvedor</option>");
												if(funcionario.getCargo().getCodigo() == 5)
												{sbRegistro.append("<option value='5' selected>Analista</option></select></td>");}
												else sbRegistro.append("<option value='5'>Analista</option></select></td>");
												sbRegistro.append("<td align='center'>Regional:<br/><select name='regional' id='regional' class='form-control'>");
												if(funcionario.getRegional().getCodigo() == 1)
												{sbRegistro.append("<option value='1' selected>Zona Sul</option>");}
												else sbRegistro.append("<option value='1'>Zona Sul</option>");
												if(funcionario.getRegional().getCodigo() == 2)
												{sbRegistro.append("<option value='2' selected>Zona Leste</option>");}
												else sbRegistro.append("<option value='2'>Zona Leste</option>");
												if(funcionario.getRegional().getCodigo() == 3)
												{sbRegistro.append("<option value='3' selected>Zona Norte</option>");}
												else sbRegistro.append("<option value='3'>Zona Norte</option>");
												if(funcionario.getRegional().getCodigo() == 4)
												{sbRegistro.append("<option value='4' selected>Zona Oeste</option>");}
												else sbRegistro.append("<option value='4'>Zona Oeste</option>");
												if(funcionario.getRegional().getCodigo() == 5)
												{sbRegistro.append("<option value='5' selected>Mogi das Cruzes</option></select></td></tr>");}
												else sbRegistro.append("<option value='5'>Mogi das Cruzes</option></select></td></tr>");
												sbRegistro.append("<tr><td align='center'>Senha:<br/><input type='password' name='senha1' id='senha1'" +
																  "placeholder='8 digitos, 1 Caracter especial, 1 letra maicuscula e minuscula'"
																  + "size='40' class='form-control' required value='" + funcionario.getSenha() + "'/>"
																  + "</td>");
												sbRegistro.append("<td align='center'>Repita a senha:<br/><input type='password' name='senha2' id='senha2'" +
																  "placeholder='Repita senha'" + "size='40' class='form-control' required value='" 
																  + funcionario.getSenha() + "'/></td>");
												sbRegistro.append("<td align='center'>Contratado em:<br/>" + 
														"<input type='date' name='data' id='data' class='form-control'" +
														"placeholder='Contratado em' size='35' required value='" + 
														String.valueOf(funcionario.getDataRegistro()) + "'/></td></tr>");
												sbRegistro.append("<tr><td><input type='hidden' class='form-control'/></td>");
												sbRegistro.append("<td><br/><button type='submit' class='btn btn-primary form-control'" +
																  "name='funcionario' value='AlterarFuncionario'>Alterar</button><td/></tr>");
												//escrever todos as tags e valores armazenadas no sbRegistro.
												out.print(sbRegistro.toString());
											}
										}
		                    		}
		                    	%>
							</td>
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
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<!-- Include all compiled plugins (below), or include individual files as needed -->
		<script src="bootstrap/js/bootstrap.min.js"></script>
	</body>
</html>
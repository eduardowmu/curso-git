package br.com.fatec2019.command;

import br.com.fatec2019.Controle.Resposta;
import br.com.fatec2019.Dominio.EntidadeDominio;
//Comando para alterar senha de um funcionario
public class AltSenhaCommand extends AbstractCommand
{	@Override public Resposta execute(EntidadeDominio entidade) 
	{return fachada.alterarSenha(entidade);}	
}

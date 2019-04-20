package br.com.fatec2019.command;
import br.com.fatec2019.Controle.Resposta;
import br.com.fatec2019.Dominio.EntidadeDominio;
//comando para alterar funcionario
public class AlterarCommand extends AbstractCommand
{	@Override public Resposta execute(EntidadeDominio entidade) 
	{return fachada.alterar(entidade);}	
}

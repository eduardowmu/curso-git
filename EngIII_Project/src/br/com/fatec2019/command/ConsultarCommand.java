package br.com.fatec2019.command;
import br.com.fatec2019.Controle.Resposta;
import br.com.fatec2019.Dominio.EntidadeDominio;
//método consultar
public class ConsultarCommand extends AbstractCommand
{	@Override public Resposta execute(EntidadeDominio entidade) 
	{return fachada.consultar(entidade);}
}

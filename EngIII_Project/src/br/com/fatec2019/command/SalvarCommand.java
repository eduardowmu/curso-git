package br.com.fatec2019.command;

import br.com.fatec2019.Controle.Resposta;
import br.com.fatec2019.Dominio.EntidadeDominio;
//método salvar
public class SalvarCommand extends AbstractCommand
{	@Override public Resposta execute(EntidadeDominio entidade) 
	{return fachada.salvar(entidade);}
}

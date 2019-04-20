package br.com.fatec2019.command;

import br.com.fatec2019.Controle.Resposta;
import br.com.fatec2019.Dominio.EntidadeDominio;
//comando de ativar, que chama o mesmo método de inativar (REAPROVAITAMENTO)
public class AtivarCommand extends AbstractCommand
{	@Override public Resposta execute(EntidadeDominio entidade) 
	{return fachada.Inativar(entidade);}
}

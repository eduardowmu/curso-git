package br.com.fatec2019.command;

import br.com.fatec2019.Controle.Resposta;
import br.com.fatec2019.Dominio.EntidadeDominio;

public class FormularCommand extends AbstractCommand
{	@Override public Resposta execute(EntidadeDominio entidade) 
	{return this.fachada.Formular(entidade);}
}

package br.com.fatec2019.command;

import br.com.fatec2019.Controle.Resposta;
import br.com.fatec2019.Dominio.EntidadeDominio;
//comando de inativar funcionario (ListaFuncionarios.jsp)
public class InativarCommand extends AbstractCommand
{	@Override public Resposta execute(EntidadeDominio entidade) 
	{return fachada.Inativar(entidade);}
}

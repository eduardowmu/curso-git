package br.com.fatec2019.Strategy;

import br.com.fatec2019.Dominio.EntidadeDominio;
import br.com.fatec2019.Dominio.Funcionario;

public class ComparadorSenhas extends AbstractStrategy
{	@Override public String Processar(EntidadeDominio entidade) 
	{	Funcionario funcionario = (Funcionario)entidade;
		if(funcionario.getSenha().equals(funcionario.getSenha2()))
			return null;
		
		return "Senhas não batem. ";
	}

}

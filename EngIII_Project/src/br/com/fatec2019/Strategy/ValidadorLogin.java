package br.com.fatec2019.Strategy;

import br.com.fatec2019.DAO.FuncionarioDAO;
import br.com.fatec2019.DAO.IDAO;
import br.com.fatec2019.Dominio.EntidadeDominio;
import br.com.fatec2019.Dominio.Funcionario;

public class ValidadorLogin implements IStrategy
{	@Override public String Processar(EntidadeDominio entidade) 
	{	IDAO dao = null;
		if(entidade instanceof Funcionario)
		{	Funcionario funcionario = (Funcionario)entidade;
			dao = new FuncionarioDAO();
			for(EntidadeDominio ed : dao.Consultar(funcionario))
			{	Funcionario f = (Funcionario)ed;
				if(f.getEmail().equals(funcionario.getEmail()) && f.getSenha().equals(funcionario.getSenha()))
					return null;
			}
		}
		return "Dados inconsistentes";
	}
}

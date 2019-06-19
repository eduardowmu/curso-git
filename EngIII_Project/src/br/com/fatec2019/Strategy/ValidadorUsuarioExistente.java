package br.com.fatec2019.Strategy;

import br.com.fatec2019.DAO.FuncionarioDAO;
import br.com.fatec2019.DAO.IDAO;
import br.com.fatec2019.Dominio.EntidadeDominio;
import br.com.fatec2019.Dominio.Funcionario;

public class ValidadorUsuarioExistente extends AbstractStrategy
{	@Override public String Processar(EntidadeDominio entidade) 
	{	IDAO dao = null;
		if(entidade instanceof Funcionario)
		{	dao = new FuncionarioDAO();
			Funcionario funcionario = (Funcionario)entidade;
			for(EntidadeDominio ed:dao.Consultar())
			{	Funcionario f = (Funcionario)ed;
				if(f.getEmail().equals(funcionario.getEmail()))
					return null;
			}
		}
		return "E-mail não cadastrado.";
	}	

}

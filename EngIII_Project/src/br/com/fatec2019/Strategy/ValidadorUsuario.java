package br.com.fatec2019.Strategy;

import br.com.fatec2019.DAO.FuncionarioDAO;
import br.com.fatec2019.DAO.IDAO;
import br.com.fatec2019.Dominio.EntidadeDominio;
import br.com.fatec2019.Dominio.Funcionario;

public class ValidadorUsuario extends AbstractStrategy
{	@Override public String Processar(EntidadeDominio entidade) 
	{	IDAO dao = null;
		if(entidade instanceof Funcionario)
		{	dao = new FuncionarioDAO();
			for(EntidadeDominio ed:dao.Consultar())
			{	Funcionario funcionario = (Funcionario)ed;
				if(funcionario.getEmail().equals(funcionario.getUsuario().getNome()))
				{return null;}
			}
		}
		return "Usuário não encontrado.";
	}

}

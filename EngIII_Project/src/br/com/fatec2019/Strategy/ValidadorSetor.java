package br.com.fatec2019.Strategy;
import java.sql.SQLException;

import br.com.fatec2019.DAO.SetorDAO;
import br.com.fatec2019.Dominio.EntidadeDominio;
import br.com.fatec2019.Dominio.Funcionario;
import br.com.fatec2019.Dominio.Setor;

//implementar metodo que valida o setor de cada funcionario
//seguir a mesma lógica para validar regional
public class ValidadorSetor extends AbstractStrategy
{	@Override public String Processar(EntidadeDominio entidade) 
	{	Funcionario funcionario = (Funcionario)entidade;
		if(funcionario.getSetor().getCodigo() > 0)	
		{	SetorDAO sDAO = new SetorDAO();
			try
			{	Setor setor = (Setor)sDAO.ConsultarEntidade(funcionario.getSetor());
				if(setor != null)
				{	funcionario.getSetor().setNome(setor.getNome());
					return null;
				}
			}
			catch(NullPointerException e) {return e.getMessage();}
		}
		return "Setor inválido. ";
	}	
}

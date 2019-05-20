package br.com.fatec2019.Strategy;
import br.com.fatec2019.DAO.RegionalDAO;
import br.com.fatec2019.DAO.SetorDAO;
import br.com.fatec2019.Dominio.EntidadeDominio;
import br.com.fatec2019.Dominio.Funcionario;
import br.com.fatec2019.Dominio.Regional;
//método que verifica se há um valor para o REgional do funcionario
public class ValidadorRegional extends AbstractStrategy
{	@Override public String Processar(EntidadeDominio entidade) 
	{	Funcionario funcionario = (Funcionario)entidade;
		if(funcionario.getRegional().getCodigo() > 0)	
		{	RegionalDAO rDAO = new RegionalDAO();
			try
			{	Regional regional = (Regional)rDAO.ConsultarEntidade(funcionario.getRegional());
				if(regional != null)
				{	funcionario.getRegional().setNome(regional.getNome());
					return null;
				}
			}
			catch(NullPointerException e) {return e.getMessage();}
		}
		return "Campo Regional não preenchido. ";
	}	
}

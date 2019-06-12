package br.com.fatec2019.Strategy;
import java.util.ArrayList;
import java.util.List;
import br.com.fatec2019.DAO.CargoDAO;
import br.com.fatec2019.Dominio.EntidadeDominio;
import br.com.fatec2019.Dominio.Funcionario;
import br.com.fatec2019.Dominio.Cargo;
public class ValidadorCargo extends AbstractStrategy
{	@Override public String Processar(EntidadeDominio entidade) 
	{	Funcionario funcionario = (Funcionario)entidade;
		//se usuarioID não tiver nenhum valor
		try
		{	if(funcionario.getCargo().getCodigo() > 0)
			{	CargoDAO cdao = new CargoDAO();
				for(EntidadeDominio ed : cdao.Consultar(funcionario.getCargo()))
				{	Cargo c = (Cargo)ed;
					if(c.getCodigo() == funcionario.getCargo().getCodigo())
					{	funcionario.getCargo().setNome(c.getNome());
						return null;
					}
				}
			}
		}
		catch(NullPointerException e) {return e.getMessage();}
		/*o próximo passo é fazer uma consulta no BD e verificar se já existe este nome 
		 de usuário, pois se existir, deverá retornar uma mensagem ao cliente*/
		return "Cargo inválido.\n";
	}	
}

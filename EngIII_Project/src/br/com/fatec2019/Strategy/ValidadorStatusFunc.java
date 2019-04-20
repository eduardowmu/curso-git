package br.com.fatec2019.Strategy;
import br.com.fatec2019.Dominio.EntidadeDominio;
import br.com.fatec2019.Dominio.Funcionario;
//strategy responsável pela definição ou alteração do status de um
//funcionario
public class ValidadorStatusFunc extends AbstractStrategy
{	@Override public String Processar(EntidadeDominio entidade) 
	{	Funcionario funcionario = (Funcionario)entidade;
		//se for um cadastro
		try
		{	if(funcionario.getStatus().equals("ativo"))
				funcionario.setStatus("inativo");
		
			//senão
			else funcionario.setStatus("ativo");
		}
		catch(NullPointerException e)
		{funcionario.setStatus("ativo");}
		return null;
	}
}
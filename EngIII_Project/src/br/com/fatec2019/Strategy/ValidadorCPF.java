package br.com.fatec2019.Strategy;

import br.com.fatec2019.DAO.FuncionarioDAO;
import br.com.fatec2019.DAO.IDAO;
import br.com.fatec2019.Dominio.EntidadeDominio;
import br.com.fatec2019.Dominio.Funcionario;

public class ValidadorCPF extends AbstractStrategy
{	@Override public String Processar(EntidadeDominio entidade) 
	{	Funcionario funcionario = (Funcionario)entidade;
		Integer[] dig = new Integer[11];
		int soma = 0;
		if(!funcionario.getCpf().equals("") && funcionario.getCpf().length() == 11)
		{	for(int i = 0, j = 10; i < funcionario.getCpf().length()-2; i++, j--)
			{	dig[i] = Integer.parseInt(String.valueOf(funcionario.getCpf().charAt(i)));
				soma += (dig[i]*j);
			}
		
			soma %= 11;
			//se soma maior que 1, será 11 - resto
			if(soma > 1)	dig[9] = 11 - soma;
			//senão, será zero
			else dig[9] = 0;
			
			//reinicializa a soma
			soma = 0;
			
			for(int i = 0, j = 11; i < funcionario.getCpf().length()-1; i++, j--)
			{soma += (dig[i]*j);}
			
			soma %= 11;
			if(soma > 1)	dig[10] = 11 - soma;
			else dig[10] = 0;
			if(dig[9] == Integer.parseInt(String.valueOf(funcionario.getCpf().charAt(9))) && 
				dig[10] == Integer.parseInt(String.valueOf(funcionario.getCpf().charAt(10))))
			{return null;}
		}
		return "CPF inválido!\n";
	}
}

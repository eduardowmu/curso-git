package br.com.fatec2019.Strategy;
import br.com.fatec2019.DAO.FuncionarioDAO;
import br.com.fatec2019.DAO.IDAO;
import br.com.fatec2019.Dominio.EntidadeDominio;
import br.com.fatec2019.Dominio.Funcionario;
//Strategy que dever� verificar se o usu�rio digitou uma senha
//de acordo com os requisitos n�o funcionais, para ambos os campos
//de senha e se as senhas s�o iguais.
public class ValidadorSenha extends AbstractStrategy
{	@Override public String Processar(EntidadeDominio entidade) 
	{	Funcionario funcionario = (Funcionario)entidade;
		String esp = "!?@#$%&*()_-=/*+,.^;{}�[]��~";//string de characteres especiais
		String low = "qwertyuiopasdfghjklzxcvbnm�";//string de alfabeto
		int e = 0;//quantidade de caracteres especiais
		int l = 0;//qtdade de letras minusculas
		int u = 0;//letras maiusculas
		if(funcionario.getSenha().length() >= 8)
		{	for(int i = 0; i < esp.length(); i++)
			{	if(funcionario.getSenha().contains(String.valueOf(esp.charAt(i))))
				{e++;}
			}
			
			for(int i = 0; i < low.length(); i++)
			{	if(funcionario.getSenha().contains(String.valueOf(low.charAt(i))))
					l++;
			}
			
			for(int i = 0; i < low.toUpperCase().length(); i++)
			{	if(funcionario.getSenha().contains(String.valueOf(low.toUpperCase().charAt(i))))
					u++;
			}
			
			if((e > 0 && l > 0 && u > 0))	
				return null;
		}
		return "Senha inv�lida! No m�nimo 8 caracteres, 1 letra min�scula e mai�scula e 1 caractere especial. Ex: !@#$%�&*.\n";
	}	
}

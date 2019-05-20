package br.com.fatec2019.Strategy;

import br.com.fatec2019.DAO.FuncionarioDAO;
import br.com.fatec2019.DAO.IDAO;
import br.com.fatec2019.Dominio.EntidadeDominio;
import br.com.fatec2019.Dominio.Funcionario;
//classe que deverá validar se o e-mail é valido. Na verdade
//esta validação não necessitaria ser implementado, pois a
//persistencia já ocorre na página HTML com a tag <input type="email"/>
public class ValidadorEmail extends AbstractStrategy
{	@Override public String Processar(EntidadeDominio entidade) 
	{	Funcionario funcionario = (Funcionario)entidade;
		if(funcionario.getEmail().contains("@gmail.com"))
			return null;
		
		return "E-mail incorreto. ";
	}	

}

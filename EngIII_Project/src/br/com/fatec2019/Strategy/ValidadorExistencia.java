package br.com.fatec2019.Strategy;

import br.com.fatec2019.DAO.FuncionarioDAO;
import br.com.fatec2019.DAO.IDAO;
import br.com.fatec2019.Dominio.EntidadeDominio;
import br.com.fatec2019.Dominio.Funcionario;
//Strategy que valida se uma Entidade já existe
//deverá invocar a DAO da entidade e fazer uma consulta
public class ValidadorExistencia extends AbstractStrategy
{	private IDAO dao;
	@Override public String Processar(EntidadeDominio entidade) 
	{	dao = new FuncionarioDAO();
		Funcionario funcionario = (Funcionario)entidade;
		//se a lista da busca não estiver vazia (= empty em Inglês)
		if(!dao.Consultar(funcionario).isEmpty())
		{	for(EntidadeDominio ed : dao.Consultar(funcionario))
			{	Funcionario f = (Funcionario)ed;
				if(f.getEmail().equals(funcionario.getEmail()) || f.getCpf().equals(funcionario.getCpf()))
					return "Usuário já cadastrado.\n";
			}
		}
		return null;
	}	
}
package br.com.fatec2019.Strategy;
import br.com.fatec2019.DAO.FuncionarioDAO;
import br.com.fatec2019.DAO.IDAO;
import br.com.fatec2019.Dominio.EntidadeDominio;
import br.com.fatec2019.Dominio.Funcionario;

//Strategy respons�vel pela valida��o do c�digo inserido
//para consulta, por exemplo, ou qualquer outra aplica��o
//Sugest�o: ler os requisitos n�o funcionais
public class ValidadorCodigo extends AbstractStrategy
{	@Override public String Processar(EntidadeDominio entidade) 
	{	if(entidade.getCodigo() > 0) 
		{	if(entidade instanceof Funcionario)
			{	IDAO fdao = new FuncionarioDAO();
				Funcionario funcionario = (Funcionario)entidade;
				entidade = fdao.ConsultarEntidade(funcionario);
			}
			return null;
		}
		else return "N�o foi poss�vel realizar a a��o! ";
	}
}

package br.com.fatec2019.Strategy;
import java.util.Date;
import br.com.fatec2019.Dominio.EntidadeDominio;
import br.com.fatec2019.Dominio.Funcionario;
//na verdade este método nem precisa ser feito, pois a persistencia dos dados
//já foram implementadas na criação da tabela (atributo tipo CURRENT_TIMESTAMP)
//referencias: minha apostila Desenvolvimento Web 2
public class RegistradorData extends AbstractStrategy
{	@Override public String Processar(EntidadeDominio entidade) 
	{	entidade.setDataCadastro(new Date(System.currentTimeMillis()));
		return null;
	}
}

package br.com.fatec2019.Strategy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import br.com.fatec2019.Dominio.EntidadeDominio;
import br.com.fatec2019.Dominio.Funcionario;
//Strategy de validação de dados cadastrais (Implementar!)
public class ValidadorDadosCadastrais extends AbstractStrategy
{	@Override public String Processar(EntidadeDominio entidade) 
	{	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try
		{	if(entidade.getNome().equals("") && entidade.getCodigo() == 0)
			{	if(entidade instanceof Funcionario)
				{	Funcionario funcionario = (Funcionario)entidade;
					if(funcionario.getCpf().equals("") && funcionario.getEmail().equals("") &&
						funcionario.getSetor().getCodigo() < 1 && funcionario.getRegional().getCodigo() < 1
						&& funcionario.getDataRegistro().equals(""))
					{return "Dados incorretos";}
				}
			}
			return null;
		}
		catch(NullPointerException | ParseException e)	{return "Dados inválidos";}
	}
}

package br.com.fatec2019.Strategy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import br.com.fatec2019.Dominio.EntidadeDominio;
import br.com.fatec2019.Dominio.Funcionario;
//Strategy de valida��o de dados cadastrais (Implementar!)
public class ValidadorDadosCadastrais extends AbstractStrategy
{	@Override public String Processar(EntidadeDominio entidade) 
	{	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try
		{	if(entidade.getNome().equals("") && entidade.getCodigo() == 0)
			{	if(entidade instanceof Funcionario)
				{	Funcionario funcionario = (Funcionario)entidade;
					if(funcionario.getCpf().equals("") && funcionario.getEmail().equals("") &&
						funcionario.getSetor().getCodigo() < 1 && funcionario.getRegional().getCodigo() < 1
						&& funcionario.getCargo().getCodigo() < 1 && 
						funcionario.DateToString(funcionario.getDataRegistro()).equals("01/01/1000"))
					{return "Preencha pelo menos um campo.\n";}
				}
			}
			return null;
		}
		catch(NullPointerException | ParseException e)	{return "Dados inv�lidos.\n";}
	}
}

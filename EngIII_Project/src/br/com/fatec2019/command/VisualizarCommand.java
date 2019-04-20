package br.com.fatec2019.command;
import br.com.fatec2019.Controle.Resposta;
import br.com.fatec2019.Dominio.EntidadeDominio;
//comando de visualizar, para inserir os dados de busca no BD e redireciona-los para
//a pagina FuncForm.jsp
public class VisualizarCommand extends AbstractCommand
{	@Override public Resposta execute(EntidadeDominio entidade) 
	{return fachada.visualizar(entidade);}
}

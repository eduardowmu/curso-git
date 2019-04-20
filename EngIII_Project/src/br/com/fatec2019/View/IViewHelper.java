package br.com.fatec2019.View;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.com.fatec2019.Dominio.*;
import br.com.fatec2019.Controle.*;
public interface IViewHelper 
{	public EntidadeDominio getEntidade(HttpServletRequest request);
	//método para pegar os dados do objeto tipo View
	public void setView(Resposta resposta, HttpServletRequest request, 
		HttpServletResponse response) throws IOException, ServletException;
}

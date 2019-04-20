package br.com.fatec2019.Controle;
import java.util.ArrayList;
import java.util.List;
import br.com.fatec2019.Dominio.*;
public class Resposta 
{	private String msg;//mensagem de retorno ao cliente, ap�s um request
	//lista de entidades
	private List<EntidadeDominio> entidades;
	
	//getters e setters
	public String getMsg() {return msg;}
	public void setMsg(String msg) {this.msg = msg;}
	
	//busca as entidades armazenadas previamente.
	public List<EntidadeDominio> getEntidades() {return entidades;}
	public void setEntidades(List<EntidadeDominio> entidades) 
	{this.entidades = entidades;}
	
	//este m�todo adiciona entidade numa lista, que poder� ser exibida,
	//alterada, etc. Qualquer Entidade que fa�a parte do dominio
	public void addEntidade(EntidadeDominio entidade) 
	{	if(entidades == null) 
		{entidades = new ArrayList<EntidadeDominio>();}
		//add entidade na lista de entidades
		entidades.add(entidade);
	}
}

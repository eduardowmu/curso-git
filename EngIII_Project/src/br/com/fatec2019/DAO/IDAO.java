package br.com.fatec2019.DAO;
import java.util.List;
import br.com.fatec2019.Dominio.*;

//interface de DAOs
public interface IDAO 
{	public abstract void Salvar(EntidadeDominio entidade);
	public abstract void Alterar(EntidadeDominio entidade);
	public abstract void Excluir(EntidadeDominio entidade);
	public abstract void Inativar(EntidadeDominio entidade);
	public abstract List<EntidadeDominio> Consultar(EntidadeDominio entidade);
	public abstract EntidadeDominio ConsultarEntidade(EntidadeDominio entidade);
	public abstract List<EntidadeDominio> Consultar();
}

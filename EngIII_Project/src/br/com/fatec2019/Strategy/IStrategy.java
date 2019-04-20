package br.com.fatec2019.Strategy;
import br.com.fatec2019.Dominio.EntidadeDominio;

//interface de Strategy, cujas classes que a implementam ir�o sempre implementar um m�todo com mesmo nome,
//seja de valida��o ou outros m�todos
public interface IStrategy 
{public abstract String Processar(EntidadeDominio entidade);}

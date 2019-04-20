package br.com.fatec2019.Strategy;
import br.com.fatec2019.Dominio.EntidadeDominio;

//interface de Strategy, cujas classes que a implementam irão sempre implementar um método com mesmo nome,
//seja de validação ou outros métodos
public interface IStrategy 
{public abstract String Processar(EntidadeDominio entidade);}

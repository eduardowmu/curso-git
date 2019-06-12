package br.com.fatec2019.Controle;

import br.com.fatec2019.Dominio.*;

//interface de fachada, que possui método(s) que deverão ser implementadas por classes que a implementa
//A classe resposta é uma classe que retornará respostas ao cliente, seja em forma de mensagens ou na
//forma de objetos do tipo Entidade de Dominio.
public interface IFachada 
{	public abstract Resposta salvar(EntidadeDominio entidade);
	public abstract Resposta alterar(EntidadeDominio entidade);
	public abstract Resposta alterarSenha(EntidadeDominio entidade);
	public abstract Resposta excluir(EntidadeDominio entidade);
	public abstract Resposta consultar(EntidadeDominio entidade);
	public abstract Resposta visualizar(EntidadeDominio entidade);
	public abstract Resposta Logar(EntidadeDominio entidade);
	public abstract Resposta Inativar(EntidadeDominio entidade);
	public abstract Resposta Formular(EntidadeDominio entidade);
}

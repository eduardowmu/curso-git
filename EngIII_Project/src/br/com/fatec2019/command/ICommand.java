package br.com.fatec2019.command;
import br.com.fatec2019.Controle.*;
import br.com.fatec2019.Dominio.*;
//interface de comandos
public interface ICommand 
{public Resposta execute(EntidadeDominio entidade);}

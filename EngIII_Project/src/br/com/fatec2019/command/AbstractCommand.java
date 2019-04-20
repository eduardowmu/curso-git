package br.com.fatec2019.command;

import br.com.fatec2019.Controle.Fachada;
import br.com.fatec2019.Controle.IFachada;
//Todas as classes herdeiras dessa deverá chamar a fachada
public abstract class AbstractCommand implements ICommand
{protected IFachada fachada = new Fachada();}

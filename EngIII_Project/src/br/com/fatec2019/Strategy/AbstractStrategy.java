package br.com.fatec2019.Strategy;
public abstract class AbstractStrategy implements IStrategy
{	protected StringBuilder sb = new StringBuilder(); 

	protected String VerificaMsg()
	{	if(sb.length()>0)//se houver alguma msg 
		{return sb.toString();}//mensagem enviado
		//senão retorna nulo
		else return null; 
	}
}

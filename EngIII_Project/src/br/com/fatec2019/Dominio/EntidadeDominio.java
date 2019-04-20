package br.com.fatec2019.Dominio;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public abstract class EntidadeDominio 
{	protected int codigo;			//toda entidade ou atributos do tipo protected, sómente quem é herdeiro e os 
	//que fazem parte de um mesmo pacote conseguem enxerga-los 
	protected String nome;
	//armazena data em que um evento aconteceu
	protected Date dataRegistro;
	//converte data para formato String
	protected String dataCadastro;
	
	//métodos GETTERs e SETTERs (métodos de acesso e modificadores)
	public int getCodigo() {return codigo;}
	public void setCodigo(Integer codigo) {this.codigo = codigo;}
	
	public String getNome() {return nome;}
	public void setNome(String nome) {this.nome = nome;}
	
	public Date getDataRegistro() {return dataRegistro;}
	public void setDataRegistro(Date dataRegistro) {this.dataRegistro = dataRegistro;}
	public String DateToString(Date date)
	{	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(date);
	}
	
	public String getDataCadastro() {return dataCadastro;}
	//referencia: https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
	public void setDataCadastro(Date dataCadastro) 
	{	DateFormat df = new SimpleDateFormat("EEE, dd/MM/yyyy HH:mm:ss");
		this.dataCadastro = df.format(dataCadastro);
	}
}

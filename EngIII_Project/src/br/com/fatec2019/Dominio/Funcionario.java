package br.com.fatec2019.Dominio;

import java.util.ArrayList;
import java.util.List;

public class Funcionario extends EntidadeDominio
{	private String cpf;
	private Setor setor; 
	private Regional regional;
	private String email;
	private Cargo cargo;
	private String senha;
	private String senha2;
	private String status;
	
	//GETTERs e SETTERs
	public Regional getRegional() {return regional;}
	public void setRegional(Regional regional) {this.regional = regional;}
	
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	
	public Setor getSetor() {return setor;}
	public void setSetor(Setor setor) {this.setor = setor;}
	
	public Cargo getCargo() {return cargo;}
	public void setCargo(Cargo cargo) {this.cargo = cargo;}
	
	public String getSenha() {return senha;}
	public void setSenha(String senha) {this.senha = senha;}
	
	public String getSenha2() {return senha2;}
	public void setSenha2(String senha2) {this.senha2 = senha2;}
	
	public String getStatus() {return status;}
	public void setStatus(String status) {this.status = status;}
	
	public String getCpf() {return cpf;}
	public void setCpf(String cpf) {this.cpf = cpf;}
}
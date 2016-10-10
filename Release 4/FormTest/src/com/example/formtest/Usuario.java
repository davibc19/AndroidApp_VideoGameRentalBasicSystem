package com.example.formtest;

public class Usuario{
	private String nome;
	private String password;
	private String tipoCadastro;
	private String telefone;
	private String passAdmSecurity = "123";
	
	public String getSecurityPassword()
	{
		return passAdmSecurity;
	}

	public void setPassword(String password)
	{this.password = password;}
	public String getPassword()
	{return this.password;}

	public void setNome(String nome)
	{this.nome = nome;}
	public String getNome()
	{return this.nome;}

	public void setTipoCadastro(String tipoCadastro)
	{this.tipoCadastro = tipoCadastro;}
	public String getTipoCadastro()
	{return this.tipoCadastro;}

	public void setTelefone(String telefone)
	{this.telefone = telefone;}
	public String getTelefone()
	{return this.telefone;}

	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}


}

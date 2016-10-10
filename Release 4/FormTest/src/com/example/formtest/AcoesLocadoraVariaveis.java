package com.example.formtest;

public class AcoesLocadoraVariaveis 
{
	
	private String tipoAcao;
	private String nomeJogo;
	private String precoUn;
	private String quantidade;
	private String data;
	private String subTotal;
	
	
	public String getNomeJogo()
	{
		return nomeJogo;
	}
	
	public void setNomeJogo(String nomeJogo)
	{
		this.nomeJogo = nomeJogo;
	}
	
	public String getPrecoUn()
	{
		return precoUn;
	}
	
	public void setPrecoUn(String precoUn)
	{
		this.precoUn = precoUn;
	}
	
	public String getQuantidade()
	{
		return quantidade;
	}
	
	public void setQuantidade(String quantidade)
	{
		this.quantidade = quantidade;
	}
	
	public String getData()
	{
		return data;
	}
	
	public void setData(String data)
	{
		this.data = data;
	}
	
	public String getSubTotal()
	{
		return subTotal;
	}
	
	public void setSubTotal(String subTotal)
	{
		this.subTotal = subTotal;
	}

	public String getTipoAcao() {
		return tipoAcao;
	}

	public void setTipoAcao(String tipoAcao) {
		this.tipoAcao = tipoAcao;
	}
}

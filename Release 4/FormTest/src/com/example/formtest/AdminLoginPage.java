package com.example.formtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminLoginPage extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// Liberando visualização para o usuário
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_login_layout);
		
		// Declaração dos Botões
		Button btJogos = (Button) findViewById(R.id.admJogos); 
		Button btAcoes = (Button) findViewById(R.id.admAcoes);
		Button btEstoques = (Button) findViewById(R.id.admEstoques);
		Button btRelatorio = (Button) findViewById(R.id.admRelatorio);
		Button btDeslogar = (Button) findViewById(R.id.admDeslogar);
		
		// Resposta ao botão deslogar
		btDeslogar.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
		
		// Abre a página de Jogos
		btJogos.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View v)
			{
				Intent itJogos = new Intent(AdminLoginPage.this, CrudJogo.class);
				startActivity(itJogos);
			}
		});
		
		// Abre a página de Atividades da Locadora
		btAcoes.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View v)
			{
				Intent itAcoes = new Intent(AdminLoginPage.this, AcoesLocadora.class);
				startActivity(itAcoes);
			}
		});
		
		btEstoques.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View v)
			{
				Intent itEstoques = new Intent(AdminLoginPage.this, EstoquesAdmUsr.class);
				startActivity(itEstoques);
			}
		});
		
		btRelatorio.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent itEstoques = new Intent(AdminLoginPage.this, Relatorios_vendaLocacao.class);
				startActivity(itEstoques);
			}
		});
		
		
	}
}

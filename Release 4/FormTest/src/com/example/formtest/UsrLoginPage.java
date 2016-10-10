package com.example.formtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UsrLoginPage extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// Liberando visualização para o usuário
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usr_login_layout);
		
		// Declaração dos Botões
		Button btEstoques = (Button) findViewById(R.id.usrEstoques);
		Button btDeslogar = (Button) findViewById(R.id.usrDeslogar);
		
		// Resposta ao botão deslogar
		btDeslogar.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
		
		btEstoques.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent itEstoques = new Intent(UsrLoginPage.this, EstoquesAdmUsr.class);
				startActivity(itEstoques);
			}
		});
		
	}

}

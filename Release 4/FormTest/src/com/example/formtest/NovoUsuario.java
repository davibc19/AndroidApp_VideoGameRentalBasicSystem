package com.example.formtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NovoUsuario extends Activity {

	UserDatabaseHelper userHelper = new UserDatabaseHelper(this);
	UserDatabaseHelper admHelper = new UserDatabaseHelper(this);
	
	private Spinner sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// Liberando visualização para o usuário
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_usuario);
	
		// Alimentando o botão Spinner!
		String[] opSpinnerUsuario = new String[] {"...", "Usuário", "Administrador"};
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opSpinnerUsuario);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		sp = (Spinner) findViewById(R.id.spinnerUsuario);
		sp.setAdapter(adapter);		
		
		// Botões, TextosEditáveis e o que foi digitado
		final Button btVoltar = (Button) findViewById(R.id.cancelar1);
		final Button btSalvar = (Button) findViewById(R.id.salvar1);
		
		// Encerra Cadastro
		btVoltar.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
		
		// Confirma Cadastro
		btSalvar.setOnClickListener(new View.OnClickListener()
		{				
			@Override
			public void onClick(View v)
			{
				
				EditText ETnome = (EditText) findViewById(R.id.TVnome);
				EditText ETsenha = (EditText) findViewById(R.id.TVsenha);
				EditText ETsenhaadm = (EditText) findViewById(R.id.senhaadm);
				EditText ETtelefone = (EditText) findViewById(R.id.TVTelefone);
				
				String nomestr = ETnome.getText().toString();
				String senhastr = ETsenha.getText().toString();
				String senhaadmstr = ETsenhaadm.getText().toString();
				String telefonestr = ETtelefone.getText().toString();
				if(ETnome.getText().length() == 0 || ETsenha.getText().length() == 0)
				{
					Toast campoObrigatorio = Toast.makeText(NovoUsuario.this, "Os campos nome e senha são obrigatórios!", Toast.LENGTH_SHORT);
					campoObrigatorio.show();
				}
				else
				{
					String opSpinner = (String) sp.getSelectedItem();
					if(opSpinner == "Usuário")
					{
						// CADASTRO DE USUÁRIO
						Usuario user = new Usuario();
						String passAdmSecurity = user.getSecurityPassword();
										
						if(!senhaadmstr.equals(passAdmSecurity))
						{
							// Senha Administrativa Incorreta!
							Toast mensagem = Toast.makeText(NovoUsuario.this, "SENHA ADMINISTRATIVA INCORRETA", Toast.LENGTH_SHORT);
							mensagem.show();
						}
						else
						{
							// Inserir os detalhes no Banco de Dados
							user.setTipoCadastro("Usuário");
							user.setNome(nomestr);
							user.setPassword(senhastr);
							user.setTelefone(telefonestr);
							
							userHelper.insertUsuario(user);
							Toast mensagem = Toast.makeText(NovoUsuario.this, "Usuário Cadastrado!", Toast.LENGTH_SHORT);
							mensagem.show();
							finish();
						}
					}else if(opSpinner == "Administrador")
					{
						// CADASTRO DE ADMINISTRADOR
						Usuario adm = new Usuario();
						String passAdmSecurity = adm.getSecurityPassword();
										
						if(!senhaadmstr.equals(passAdmSecurity))
						{
							// Senha Administrativa Incorreta!
							Toast mensagem = Toast.makeText(NovoUsuario.this, "SENHA ADMINISTRATIVA INCORRETA", Toast.LENGTH_SHORT);
							mensagem.show();
						}
						else
						{
							// Inserir os detalhes no Banco de Dados
							adm.setTipoCadastro("Administrador");
							adm.setNome(nomestr);
							adm.setPassword(senhastr);
							adm.setTelefone(telefonestr);
							
							admHelper.insertUsuario(adm);
							Toast mensagem = Toast.makeText(NovoUsuario.this, "Administrador Cadastrado!", Toast.LENGTH_SHORT);
							mensagem.show();
							finish();
						}
					}
					else
					{
						Toast mensagem = Toast.makeText(NovoUsuario.this, "Opção de Usuário Inválida", Toast.LENGTH_SHORT);
						mensagem.show();
					}
				}
			}
		});
	}
}
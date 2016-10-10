package com.example.formtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity{
	
	UserDatabaseHelper userHelper = new UserDatabaseHelper(this);
	UserDatabaseHelper admHelper = new UserDatabaseHelper(this);
	private Spinner sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		//"Chamando" os objetos
		final EditText login = (EditText)findViewById(R.id.login);
		final EditText senha = (EditText)findViewById(R.id.TVsenha);
		Button entrar = (Button) findViewById(R.id.entrar);
		Button registrar = (Button) findViewById(R.id.cadastrar);
		Button listar = (Button) findViewById(R.id.listagemUsr);

		// Alimentando o botão Spinner!
		String[] opSpinnerUsuario = new String[] {"...", "Usuário", "Administrador"};
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, opSpinnerUsuario);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		sp = (Spinner) findViewById(R.id.spinnerLogin);
		sp.setAdapter(adapter);
	
		
		// Resposta ao botão Cadastrar
		registrar.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View v)
			{
				Intent itCadastrar = new Intent(MainActivity.this, NovoUsuario.class);
				startActivity(itCadastrar);
			}
		});
		
		listar.setOnClickListener(new View.OnClickListener()
		{				
			@Override
			public void onClick(View v)
			{
				AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);                 
				alert.setTitle("Confirmação de Acesso");  
				alert.setMessage("Digite a Senha Administrativa:");                

				// Set an EditText view to get user input  
				final EditText input = new EditText(MainActivity.this);
				input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				alert.setView(input);
				
				
			    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener()
			    {  
			    	public void onClick(DialogInterface dialog, int whichButton)
			    	{  
				    	String valuestr = input.getText().toString();
				    	Usuario helper = new Usuario();
				    	if(valuestr.equals(helper.getSecurityPassword()))
				    	{
				    			Intent itListagem = new Intent(MainActivity.this, ListadeUsuarios.class);
				    			startActivity(itListagem);
				    	}
				    	else
				    	{
				    		Toast mensagem = Toast.makeText(MainActivity.this, "Senha Administrativa Incorreta", Toast.LENGTH_SHORT);
							mensagem.show();
				    	}
			    	}  
			    });  

			    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
			    {
			    	public void onClick(DialogInterface dialog, int which)
			    	{
			    		return;   
			    	}
			    });
			     
			     
			    alert.show();

				
				
			}
		});
		
		// Resposta ao Botão de Login
		entrar.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// Campos de Login e Senha
				String loginstr = login.getText().toString();
				String senhastr = senha.getText().toString();
				
				if(login.getText().length() == 0 || senha.getText().length() == 0)
				{
					Toast campoObrigatorio = Toast.makeText(MainActivity.this, "Os campos login e senha são obrigatórios!", Toast.LENGTH_SHORT);
					campoObrigatorio.show();
				}
				else{	
					String opSpinner = (String) sp.getSelectedItem();
					if(opSpinner == "Usuário")
					{
						// Iniciando Banco de dados do Usuário
						String DBpassword = userHelper.searchPass(loginstr, opSpinner);
						if(senhastr.equals(DBpassword))
						{
							Intent itLoginUsr = new Intent(MainActivity.this, UsrLoginPage.class);
							startActivity(itLoginUsr);
						}
						else
						{
							Toast temp = Toast.makeText(MainActivity.this, "Usuario e Senha inválidos", Toast.LENGTH_SHORT);
							temp.show();
						}
					}else if(opSpinner == "Administrador")
					{
						// Iniciando Banco de dados do Administrador
						String DBpassword = admHelper.searchPass(loginstr, opSpinner);
						if(senhastr.equals(DBpassword))
						{
							Intent itLoginAdmin = new Intent(MainActivity.this, AdminLoginPage.class);
							startActivity(itLoginAdmin);
						}
						else
						{
							Toast temp = Toast.makeText(MainActivity.this, "Usuario e Senha inválidos", Toast.LENGTH_SHORT);
							temp.show();
						}
					}
					else
					{
						Toast mensagem = Toast.makeText(MainActivity.this, "Opção de Usuário Inválida", Toast.LENGTH_SHORT);
						mensagem.show();
					}
				}
			}
		});
		
	}
}

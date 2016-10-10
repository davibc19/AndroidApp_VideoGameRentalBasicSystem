package com.example.formtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class ListadeUsuarios extends Activity {
	UserDatabaseHelper userHelper = new UserDatabaseHelper(this);
	private  Spinner sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// Liberando visualização para o usuário
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listagem_usuarios);
		
		final EditText ETnome = (EditText)findViewById(R.id.nomeBuscaCadastro);
		Button confirmar = (Button) findViewById(R.id.buscaCadastro);
		Button cancelar = (Button) findViewById(R.id.cancelarBuscaCastro);

		// Alimentando o botão Spinner!
		String[] opSpinnerUsuario = new String[] {"...", "Usuário", "Administrador"};
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListadeUsuarios.this, android.R.layout.simple_spinner_dropdown_item, opSpinnerUsuario);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		sp = (Spinner) findViewById(R.id.spinnerBuscaTipoCadastro);
		sp.setAdapter(adapter);
		
		cancelar.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
		
		// Resposta ao Botão de Pesquisar!
		confirmar.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// Campos
				String nomestr = ETnome.getText().toString();
				String opSpinner = (String) sp.getSelectedItem();
				// Cria um cursor e o faz receber o resultado das funções de Busca de Usuários
				Cursor cursor = null;
				
				if(ETnome.getText().length() == 0 && opSpinner == "...")
				{
					cursor = userHelper.buscarTodosUsuarios();
					
				}
				else
				{	
					if(opSpinner == "Usuário")
					{
						// PROCURA POR USUÁRIOS
						cursor = userHelper.buscarUsuario(nomestr, opSpinner);
						
					}else if(opSpinner == "Administrador")
					{
						cursor = userHelper.buscarUsuario(nomestr, opSpinner);
					}
				
					else
					{
						//OPÇÃO INVÁLIDA
						Toast mensagem = Toast.makeText(ListadeUsuarios.this, "Opção de Usuário Inválida", Toast.LENGTH_SHORT);
						mensagem.show();
					}
				}
				
				// Utiliza o Cursor obtido para mostrar o resultado
				
				// Setup mapping from cursor to view fields:
				String[] fromFieldNames = new String[] 
						{userHelper.KEY_NAME,  userHelper.KEY_TYPE, userHelper.KEY_TELEFONE, userHelper.KEY_PASSWORD};
				int[] toViewIDs = new int[]
						{R.id.TextViewNome, R.id.TextViewTipo, R.id.TextViewTelefone, R.id.TextViewSenha};
				
				// Create adapter to may columns of the DB onto elements in the UI.
				
				@SuppressWarnings("deprecation")
				SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(ListadeUsuarios.this, R.layout.usuario, cursor, fromFieldNames, toViewIDs);
				
				// Set the adapter for the list view
				final ListView myList = (ListView) findViewById(R.id.listViewFromDB);
				myList.setAdapter(myCursorAdapter);
				
				// Respostas ao Clique em um Item da Lista
				myList.setOnItemClickListener(new OnItemClickListener()
				{
				
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, final long id)
					{	
						final String[] lista = { "Editar", "Deletar", "Cancelar"};
						AlertDialog.Builder alert = new AlertDialog.Builder(ListadeUsuarios.this);                 
						alert.setTitle("Escolha um item");
						alert.setItems(lista, new DialogInterface.OnClickListener()
						{
						 
							@Override
							public void onClick(DialogInterface dialog, int item)
							{
							// TODO Auto-generated method stub
								switch (item)
								{
									case 0: // Editar
										setContentView(R.layout.editar_usuario);
										
										// Botões, TextosEditáveis e o que foi digitado
										final Button btVoltar = (Button) findViewById(R.id.cancelar2);
										final Button btSalvar = (Button) findViewById(R.id.salvar2);
										
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
												EditText ETnome = (EditText) findViewById(R.id.TVEditnome);
												EditText ETsenha = (EditText) findViewById(R.id.TVEditsenha);
												EditText ETtelefone = (EditText) findViewById(R.id.TVEditTelefone);
												
												String nomestr = ETnome.getText().toString();
												String senhastr = ETsenha.getText().toString();
												String telefonestr = ETtelefone.getText().toString();
												
												if(nomestr.length() == 0 && senhastr.length() == 0  && telefonestr.length() == 0)
												{
													showMessage("Nenhum campo está sendo alterado!", ListadeUsuarios.this);
												}
												else{
													Usuario user = new Usuario();
													user.setNome(nomestr);
													user.setPassword(senhastr);
													user.setTelefone(telefonestr);
													
													userHelper.atualizarUsuario(user, id);
													// GERANDO UMA INSTANCIA DA CLASSE DIALOG
													AlertDialog.Builder dialogo = new AlertDialog.Builder(ListadeUsuarios.this);
													 
													// GERANDO O BOX DE MENSAGEM
													dialogo.setMessage("Usuario Editado com Sucesso");
													dialogo.setNeutralButton("Ok", new DialogInterface.OnClickListener()
												    {  
												    	public void onClick(DialogInterface dialog, int whichButton)
												    	{  
												    		finish();
												    	}  
												    });  
													dialogo.show();
												}
											}
										});
										break;
										
									case 1: // Deletar
										
										// GERANDO UMA INSTANCIA DA CLASSE DIALOG
										AlertDialog.Builder dialogo = new AlertDialog.Builder(ListadeUsuarios.this);
										 
										// GERANDO O BOX DE MENSAGEM
										dialogo.setMessage("Deseja Realmente Deletar este Usuário?");
										dialogo.setPositiveButton("Sim", new DialogInterface.OnClickListener()
									    {  
									    	public void onClick(DialogInterface dialog, int whichButton)
									    	{  
									    		userHelper.deletarUsuario(id);
												
												showMessage("Usuário Deletado com Sucesso!",
														ListadeUsuarios.this);
									    	}  
									    });  
										
										dialogo.setNegativeButton("Não", new DialogInterface.OnClickListener()
									    {  
									    	public void onClick(DialogInterface dialog, int whichButton)
									    	{  
									    		showMessage("Cancelado",
														ListadeUsuarios.this);
									    	}  
									    });
										dialogo.setTitle("Atenção");
										dialogo.show();
										break;
										
							 
									case 2: // Cancelar
										break;
							 
								}
							}
						});
						alert.show();		
				
					}	
				});				
			}
		});
	}
	
	// METODO DE MENSAGEM NA TELA DO USUARIO
	public void showMessage(String Caption, Activity activity)
	{
		 
		// GERANDO UMA INSTANCIA DA CLASSE DIALOG
		AlertDialog.Builder dialogo = new AlertDialog.Builder(activity);
		 
		// GERANDO O BOX DE MENSAGEM
		dialogo.setTitle("Atenção");
		dialogo.setMessage(Caption);
		dialogo.setNeutralButton("Ok", null);
		dialogo.show();
	}
}
package com.example.formtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CrudJogo extends Activity
{
	
	JogosDatabaseHelper JogoHelper = new JogosDatabaseHelper(this);
	Spinner sp;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// Liberando visualização para o usuário
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_crud_jogos);
		
		// Alimentando o botão Spinner!
		String[] opTipoSpinnerJogo = new String[] {"Tipo de Produto", "Locação", "Venda"};
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opTipoSpinnerJogo);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		sp = (Spinner) findViewById(R.id.spinnerJogo);
		sp.setAdapter(adapter);		
		
		// Botões, TextosEditáveis e o que foi digitado
		final Button btVoltar = (Button) findViewById(R.id.cancelar3);
		final Button btCadastrar = (Button) findViewById(R.id.CadastrarNovoJogo);
		final Button btBuscar = (Button) findViewById(R.id.BuscarJogo);
		final EditText ETnome = (EditText)findViewById(R.id.ETNomeJogo);
		
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
		btCadastrar.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// Chamar a Atividade de Cadastro de novo Jogo
				Intent itCadastrar = new Intent(CrudJogo.this, CadastroJogos.class);
				startActivity(itCadastrar);
			}
		});
		
		btBuscar.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// Realiza a Busca e atualiza a listagem
				String nomestr = ETnome.getText().toString();
				String opSpinner = (String) sp.getSelectedItem();
				
				// Cria um cursor e o faz receber o resultado das funções de Busca de Usuários
				Cursor cursor = null;
				
				if(nomestr.length() == 0 && opSpinner == "Tipo de Produto")
				{
					cursor = JogoHelper.buscarTodosJogos();	
				}
				else
				{
					if(opSpinner == "Locação")
					{
						// PROCURA POR USUÁRIOS
						cursor = JogoHelper.buscarJogo(nomestr, opSpinner);
						
					}else if(opSpinner == "Venda")
					{
						cursor = JogoHelper.buscarJogo(nomestr, opSpinner);
					}
					else
					{
						//OPÇÃO INVÁLIDA
						Toast mensagem = Toast.makeText(CrudJogo.this, "Tipo de Produto Inválido", Toast.LENGTH_SHORT);
						mensagem.show();
					}
				}
				// Utiliza o Cursor obtido para mostrar o resultado
				
				// Setup mapping from cursor to view fields:
				String[] fromFieldNames = new String[] 
						{JogoHelper.KEY_NAME,  JogoHelper.KEY_TYPE, JogoHelper.KEY_FABRICANTE, JogoHelper.KEY_PRECOCOMPRA, JogoHelper.KEY_PRECOLOCADORA,
						JogoHelper.KEY_QUANTIDADE, JogoHelper.KEY_ESTILOJOGO, JogoHelper.KEY_FAIXAETARIA};
				int[] toViewIDs = new int[]
						{R.id.TVJOGOnome, R.id.TVJOGOtipo, R.id.TVJOGOfabricante, R.id.TVJOGOprecocompra,
						R.id.TVJOGOprecovenda, R.id.TVJOGOquantidade, R.id.TVJOGOestiloJogo, R.id.TVJOGOfaixaEtária};
				
				// Create adapter to may columns of the DB onto elements in the UI.
				
				@SuppressWarnings("deprecation")
				SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(CrudJogo.this, R.layout.jogos, cursor, fromFieldNames, toViewIDs);
				
				// Set the adapter for the list view
				final ListView myList = (ListView) findViewById(R.id.listView1);
				myList.setAdapter(myCursorAdapter);
				
				myList.setOnItemClickListener(new OnItemClickListener()
				{
				
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, final long id)
					{	
						final String[] lista = { "Editar", "Deletar", "Cancelar"};
						AlertDialog.Builder alert = new AlertDialog.Builder(CrudJogo.this);                 
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
										setContentView(R.layout.cadastro_jogo);
										// Spinners
										final Spinner sp1;
										final Spinner sp2;
										final Spinner sp3;
										
										String[] opSpinnerJogos1 = new String[] {"...", "Venda", "Locação"};
										String[] opSpinnerJogos2 = new String[] {"...", "Fantasia", "RPG", "Aventura", "Tiro", "Corrida", "MMO", "Educativo", "Esportes", "Ação", "Puzzle"};
										String[] opSpinnerJogos3 = new String[] {"...", "Livre", "10 anos", "12 anos", "14 anos", "16 anos", "18 anos"};
										
										ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(CrudJogo.this, android.R.layout.simple_spinner_dropdown_item, opSpinnerJogos1);
										adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
										ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(CrudJogo.this, android.R.layout.simple_spinner_dropdown_item, opSpinnerJogos2);
										adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
										ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(CrudJogo.this, android.R.layout.simple_spinner_dropdown_item, opSpinnerJogos3);
										adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
										
										sp1 = (Spinner) findViewById(R.id.spinnerJogo1);
										sp1.setAdapter(adapter1);
										
										sp2 = (Spinner) findViewById(R.id.spinnerJogo2);
										sp2.setAdapter(adapter2);
										
										sp3 = (Spinner) findViewById(R.id.spinnerJogo3);
										sp3.setAdapter(adapter3);
										
										// Botões, TextosEditáveis e o que foi digitado
										final Button btVoltar = (Button) findViewById(R.id.cancelar4);
										final Button btSalvar = (Button) findViewById(R.id.cadastrarJogo);
										
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
												EditText ETnome = (EditText) findViewById(R.id.editTextJogo2);
												EditText ETfabricante = (EditText) findViewById(R.id.editTextJogo3);
												EditText ETprecoCompra = (EditText) findViewById(R.id.editTextJogo4);
												EditText ETprecoLocadora = (EditText) findViewById(R.id.editTextJogo5);
												EditText ETquantidade = (EditText) findViewById(R.id.editTextJogo6);
												
												String nomestr = ETnome.getText().toString();
												String fabricantestr = ETfabricante.getText().toString();
												String precoComprastr = ETprecoCompra.getText().toString();
												String precoLocadorastr = ETprecoLocadora.getText().toString();
												String quantidadestr = ETquantidade.getText().toString();
												String tipoProduto= (String) sp1.getSelectedItem();
												String estiloJogo = (String) sp2.getSelectedItem();
												String faixaEtaria = (String) sp3.getSelectedItem();

												Jogos jogo = new Jogos();
												jogo.setNome(nomestr);
												jogo.setFabricante(fabricantestr);
												jogo.setPrecoCompra(precoComprastr);
												jogo.setPrecoLocadora(precoLocadorastr);
												jogo.setQuantidade(quantidadestr);
												jogo.setTipoProduto(tipoProduto);
												jogo.setEstiloJogo(estiloJogo);
												jogo.setFaixaEtaria(faixaEtaria);
												
												JogoHelper.atualizarJogo(jogo, id);
												// GERANDO UMA INSTANCIA DA CLASSE DIALOG
												AlertDialog.Builder dialogo = new AlertDialog.Builder(CrudJogo.this);
												 
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
										});
										break;
									case 1: // Deletar
										
										// GERANDO UMA INSTANCIA DA CLASSE DIALOG
										AlertDialog.Builder dialogo = new AlertDialog.Builder(CrudJogo.this);
										 
										// GERANDO O BOX DE MENSAGEM
										dialogo.setMessage("Deseja Realmente Deletar este Jogo?");
										dialogo.setPositiveButton("Sim", new DialogInterface.OnClickListener()
									    {  
									    	public void onClick(DialogInterface dialog, int whichButton)
									    	{  
									    		JogoHelper.deletarJogo(id);
												
												showMessage("Jogo Deletado com Sucesso!",
														CrudJogo.this);
									    	}  
									    });  
										
										dialogo.setNegativeButton("Não", new DialogInterface.OnClickListener()
									    {  
									    	public void onClick(DialogInterface dialog, int whichButton)
									    	{  
									    		showMessage("Cancelado",
									    				CrudJogo.this);
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

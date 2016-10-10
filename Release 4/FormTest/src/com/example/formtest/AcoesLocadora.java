package com.example.formtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AcoesLocadora extends Activity
{
	JogosDatabaseHelper JogoHelper = new JogosDatabaseHelper(this);
	Spinner sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acoes_locadora_adm);
		
		// Alimentando o botão Spinner!
		String[] opTipoSpinnerJogo = new String[] {"Tipo de Produto", "Locação", "Venda"};
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opTipoSpinnerJogo);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		sp = (Spinner) findViewById(R.id.spinnerAcoes1);
		sp.setAdapter(adapter);		

		
		Button btVoltar = (Button) findViewById(R.id.cancelar5);
		Button btPesquisar = (Button) findViewById(R.id.pesquisar1);
		final EditText ETnome = (EditText)findViewById(R.id.editTextNomeJogo);
		
		btVoltar.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
		
		btPesquisar.setOnClickListener(new View.OnClickListener()
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
						Toast mensagem = Toast.makeText(AcoesLocadora.this, "Tipo de Produto Inválido", Toast.LENGTH_SHORT);
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
				SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(AcoesLocadora.this, R.layout.jogos, cursor, fromFieldNames, toViewIDs);
				
				// Set the adapter for the list view
				final ListView myList = (ListView) findViewById(R.id.listView1);
				myList.setAdapter(myCursorAdapter);
				myList.setOnItemClickListener(new OnItemClickListener()
				{
				
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, final long id)
					{
						
						final String[] lista = {"Registrar Compra", "Registrar Venda", "Registrar Aluguel"};
						AlertDialog.Builder alert = new AlertDialog.Builder(AcoesLocadora.this);                 
						alert.setTitle("Escolha um item");
						alert.setItems(lista, new DialogInterface.OnClickListener()
						{
							
							@Override
							public void onClick(DialogInterface dialog, int item)
							{
							// TODO Auto-generated method stub
								switch (item)
								{
									case 0: // Compra
										// Chama Activity de Compra
										setContentView(R.layout.registrar_compra);							
										final Button btConfirmar = (Button) findViewById(R.id.confirmar1);
										final Button btCancelar = (Button) findViewById(R.id.cancelar6);
										final TextView TVnomeJogo = (TextView)findViewById(R.id.textViewAcoesLocadora1);
										final TextView TVprecoUn = (TextView)findViewById(R.id.textViewAcoesLocadora2);
										final TextView TVsubTotal = (TextView)findViewById(R.id.textViewAcoesLocadora3);
										final EditText ETquantidade = (EditText)findViewById(R.id.editTextAcoesLocadora1);
										final EditText ETdata = (EditText)findViewById(R.id.editTextAcoesLocadora2);
										
										
										// Transforma quantidade e preço em float, faz a conta e passa para String e, TVsubTotal
										Jogos auxTextCompra = new Jogos();
										auxTextCompra = JogoHelper.buscarDados(id);    
										
										TVnomeJogo.setText(auxTextCompra.getNome());
										TVprecoUn.setText(auxTextCompra.getPrecoCompra());
										TVsubTotal.setText("quantidade x "+TVprecoUn.getText().toString());
										
										// Botão Cancelar
										btCancelar.setOnClickListener(new View.OnClickListener()
										{	
											@Override
											public void onClick(View v)
											{
												finish();
											}
										});
										
										// Botão Confirmar Compra
										btConfirmar.setOnClickListener(new View.OnClickListener()
										{	
											@Override
											public void onClick(View v)
											{
												// Verifica se os campos estão vazios
												if((ETquantidade.getText().toString().equals("")) ||(ETdata.getText().toString().equals("")))
												{
													Toast mensagem = Toast.makeText(AcoesLocadora.this, "Todos os campos são de preenchimento obrigatórios!", Toast.LENGTH_SHORT);
													mensagem.show();
													return;
												}
												
												// Envia mensagem perguntando se tem certeza da escolha;
												// Se sim, insere no Banco de Dados;
												// Se não, finish;
												AlertDialog.Builder alert = new AlertDialog.Builder(AcoesLocadora.this);                 
												alert.setTitle("Confirmação de Compra");  
												alert.setMessage("Realmente deseja efetuar a compra deste jogo?"); 												
												
											    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener()
											    {  
											    	public void onClick(DialogInterface dialog, int whichButton)
											    	{  
											    		AcoesLocadoraVariaveis acoesLV = new AcoesLocadoraVariaveis();
														AcoesLocadoraDatabaseHelper acoesLHelper = new AcoesLocadoraDatabaseHelper(AcoesLocadora.this);
														
														String quantidadestr = ETquantidade.getText().toString();
														String datastr = ETdata.getText().toString();
														
														// Transforma Quantidade e Preço em Float para calculo do Subtotal
														int qtdCompra = Integer.parseInt(quantidadestr);
														float precoun = Float.parseFloat(TVprecoUn.getText().toString());
														float subtotal = qtdCompra*precoun;
														
														// Busca e Transforma a quantidade atual em Estoque em Float para atualizar esse estoque
														Jogos auxQtdEstoque = new Jogos();
														auxQtdEstoque = JogoHelper.buscarDados(id);
														int qtdEstoque = Integer.parseInt(auxQtdEstoque.getQuantidade().toString());
														qtdEstoque = (int) (qtdCompra + qtdEstoque);
														Jogos jAux = new Jogos();
														jAux.setQuantidade(""+qtdEstoque);
														JogoHelper.atualizarEstoque(jAux, id);
														
														acoesLV.setTipoAcao("Compra");
														acoesLV.setNomeJogo(TVnomeJogo.getText().toString());
														acoesLV.setData(datastr);
														acoesLV.setPrecoUn(TVprecoUn.getText().toString());
														acoesLV.setQuantidade(quantidadestr);
														acoesLV.setSubTotal(""+subtotal);
														
														acoesLHelper.insertAcao(acoesLV);
														finish();
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
										break;
										
									case 1: // Venda
										if(!JogoHelper.buscarTipoJogo(id).equals("Venda"))
										{
											AlertDialog.Builder dialogo = new AlertDialog.Builder(AcoesLocadora.this);
											// GERANDO O BOX DE MENSAGEM
											dialogo.setTitle("Atenção");
											dialogo.setMessage("Não é Possível realizar a VENDA deste tipo de Produto!");
											dialogo.setNeutralButton("Ok", null);
											dialogo.show();
										}
										else
										{
											// Chama Activity de Venda
											setContentView(R.layout.registrar_compra);
											final Button btConfirmar2 = (Button) findViewById(R.id.confirmar1);
											final Button btCancelar2 = (Button) findViewById(R.id.cancelar6);
											final TextView TVnomeJogo2 = (TextView)findViewById(R.id.textViewAcoesLocadora1);
											final TextView TVprecoUn2 = (TextView)findViewById(R.id.textViewAcoesLocadora2);
											final TextView TVsubTotal2 = (TextView)findViewById(R.id.textViewAcoesLocadora3);
											final EditText ETquantidade2 = (EditText)findViewById(R.id.editTextAcoesLocadora1);
											final EditText ETdata2 = (EditText)findViewById(R.id.editTextAcoesLocadora2);
											
											// Transforma quantidade e preço em float, faz a conta e passa para String e, TVsubTotal
											Jogos auxTextVenda = new Jogos();
											auxTextVenda = JogoHelper.buscarDados(id); 
											
											TVnomeJogo2.setText(auxTextVenda.getNome());
											TVprecoUn2.setText(auxTextVenda.getPrecoLocadora());
											TVsubTotal2.setText("quantidade x "+TVprecoUn2.getText().toString());
											
											if(Integer.parseInt(auxTextVenda.getQuantidade()) <= 0)
											{
												AlertDialog.Builder alert = new AlertDialog.Builder(AcoesLocadora.this);                 
												alert.setTitle("Alerta!");  
												alert.setMessage("Esse jogo não está disponível no estoque!");
												alert.setNeutralButton("Ok", new DialogInterface.OnClickListener()
											    {  
											    	public void onClick(DialogInterface dialog, int whichButton)
											    	{  
											    		finish();
											    	}  
											    });  
												alert.show();
											}
											
											// Botão Cancelar
											btCancelar2.setOnClickListener(new View.OnClickListener()
											{	
												@Override
												public void onClick(View v)
												{
													finish();
												}
											});
											
											// Botão Confirmar Venda
											btConfirmar2.setOnClickListener(new View.OnClickListener()
											{	
												@Override
												public void onClick(View v)
												{
													// Verifica se os campos estão vazios
													if((ETquantidade2.getText().toString().equals("")) ||(ETdata2.getText().toString().equals("")))
													{
														Toast mensagem = Toast.makeText(AcoesLocadora.this, "Todos os campos são de preenchimento obrigatórios!", Toast.LENGTH_SHORT);
														mensagem.show();
														return;
													}
													// Envia mensagem perguntando se tem certeza da escolha;
													// Se sim, insere no Banco de Dados;
													// Se não, finish;
													AlertDialog.Builder alert = new AlertDialog.Builder(AcoesLocadora.this);                 
													alert.setTitle("Confirmação de Venda");  
													alert.setMessage("Realmente deseja homologar a venda deste jogo?"); 												
													
												    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener()
												    {  
												    	public void onClick(DialogInterface dialog, int whichButton)
												    	{  
												    		AcoesLocadoraVariaveis acoesLV = new AcoesLocadoraVariaveis();
															AcoesLocadoraDatabaseHelper acoesLHelper = new AcoesLocadoraDatabaseHelper(AcoesLocadora.this);
															
															String quantidadestr = ETquantidade2.getText().toString();
															String datastr = ETdata2.getText().toString();
															
															Jogos auxQtdEstoque = new Jogos();
															auxQtdEstoque = JogoHelper.buscarDados(id);
															
															if(Integer.parseInt(auxQtdEstoque.getQuantidade()) < Integer.parseInt(quantidadestr))
															{
																AlertDialog.Builder alert = new AlertDialog.Builder(AcoesLocadora.this);                 
																alert.setTitle("Alerta!");  
																alert.setMessage("Não há esta quantidade de Jogos em estoque!");
																alert.setNeutralButton("Ok", new DialogInterface.OnClickListener()
															    {  
															    	public void onClick(DialogInterface dialog, int whichButton)
															    	{  
															    		finish();
															    	}  
															    });  
																alert.show();
															}
															else
															{
																int qtd = Integer.parseInt(quantidadestr);
																float precoun = Float.parseFloat(TVprecoUn2.getText().toString());
																float subtotal = qtd*precoun;
																
																int qtdEstoque = Integer.parseInt(auxQtdEstoque.getQuantidade().toString());
																qtdEstoque = (int) (qtdEstoque - qtd);
																
																Jogos jAux = new Jogos();
																jAux.setQuantidade(""+qtdEstoque);
																JogoHelper.atualizarEstoque(jAux, id);
																
																acoesLV.setTipoAcao("Venda");
																acoesLV.setNomeJogo(TVnomeJogo2.getText().toString());
																acoesLV.setData(datastr);
																acoesLV.setPrecoUn(TVprecoUn2.getText().toString());
																acoesLV.setQuantidade(quantidadestr);
																acoesLV.setSubTotal(""+subtotal);
																
																acoesLHelper.insertAcao(acoesLV);
																finish();
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
											break;
										}
										break;

									case 2: // Aluguel
										if(!JogoHelper.buscarTipoJogo(id).equals("Locação"))
										{
											AlertDialog.Builder dialogo = new AlertDialog.Builder(AcoesLocadora.this);
											// GERANDO O BOX DE MENSAGEM
											dialogo.setTitle("Atenção");
											dialogo.setMessage("Não é Possível realizar a LOCAÇÃO deste tipo de Produto!");
											dialogo.setNeutralButton("Ok", null);
											dialogo.show();
										}
										else
										{

											setContentView(R.layout.registrar_compra);
											final Button btConfirmar3 = (Button) findViewById(R.id.confirmar1);
											final Button btCancelar3 = (Button) findViewById(R.id.cancelar6);
											final TextView TVnomeJogo3 = (TextView)findViewById(R.id.textViewAcoesLocadora1);
											final TextView TVprecoUn3 = (TextView)findViewById(R.id.textViewAcoesLocadora2);
											final TextView TVsubTotal3 = (TextView)findViewById(R.id.textViewAcoesLocadora3);
											final EditText ETquantidade3 = (EditText)findViewById(R.id.editTextAcoesLocadora1);
											final EditText ETdata3 = (EditText)findViewById(R.id.editTextAcoesLocadora2);
											// Transforma quantidade e preço em float, faz a conta e passa para String e, TVsubTotal
											Jogos auxTextAluguel = new Jogos();
											auxTextAluguel = JogoHelper.buscarDados(id);
													
											if(Integer.parseInt(auxTextAluguel.getQuantidade()) <= 0)
											{
												AlertDialog.Builder alert = new AlertDialog.Builder(AcoesLocadora.this);                 
												alert.setTitle("Alerta!");  
												alert.setMessage("Esse jogo não está disponível no estoque!");
												alert.setNeutralButton("Ok", new DialogInterface.OnClickListener()
											    {  
											    	public void onClick(DialogInterface dialog, int whichButton)
											    	{  
											    		finish();
											    	}  
											    });  
												alert.show();
											}
											
											TVnomeJogo3.setText(auxTextAluguel.getNome());
											TVprecoUn3.setText(auxTextAluguel.getPrecoLocadora());
											TVsubTotal3.setText("quantidade x "+TVprecoUn3.getText().toString());
											
											// Botão Cancelar
											btCancelar3.setOnClickListener(new View.OnClickListener()
											{	
												@Override
												public void onClick(View v)
												{
													finish();
												}
											});
											
											// Botão Confirmar Venda
											btConfirmar3.setOnClickListener(new View.OnClickListener()
											{	
												@Override
												public void onClick(View v)
												{
													// Verifica se os campos estão vazios
													if((ETquantidade3.getText().toString().equals("")) ||(ETdata3.getText().toString().equals("")))
													{
														Toast mensagem = Toast.makeText(AcoesLocadora.this, "Todos os campos são de preenchimento obrigatórios!", Toast.LENGTH_SHORT);
														mensagem.show();
														return;
													}
													// Envia mensagem perguntando se tem certeza da escolha;
													// Se sim, insere no Banco de Dados;
													// Se não, finish;
													AlertDialog.Builder alert = new AlertDialog.Builder(AcoesLocadora.this);                 
													alert.setTitle("Confirmação de Locação");  
													alert.setMessage("Realmente deseja realizar a locação deste jogo?"); 												
													
												    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener()
												    {  
												    	public void onClick(DialogInterface dialog, int whichButton)
												    	{  
												    		AcoesLocadoraVariaveis acoesLV = new AcoesLocadoraVariaveis();
															AcoesLocadoraDatabaseHelper acoesLHelper = new AcoesLocadoraDatabaseHelper(AcoesLocadora.this);
															
															String quantidadestr = ETquantidade3.getText().toString();
															String datastr = ETdata3.getText().toString();
															
															Jogos auxQtdEstoque = new Jogos();
															auxQtdEstoque = JogoHelper.buscarDados(id);
															
															if(Integer.parseInt(auxQtdEstoque.getQuantidade()) < Integer.parseInt(quantidadestr))
															{
																AlertDialog.Builder alert = new AlertDialog.Builder(AcoesLocadora.this);                 
																alert.setTitle("Alerta!");  
																alert.setMessage("Não há esta quantidade de Jogos em estoque!");
																alert.setNeutralButton("Ok", new DialogInterface.OnClickListener()
															    {  
															    	public void onClick(DialogInterface dialog, int whichButton)
															    	{  
															    		finish();
															    	}  
															    });  
																alert.show();
															}
															else
															{
																int qtd = Integer.parseInt(quantidadestr);
																float precoun = Float.parseFloat(TVprecoUn3.getText().toString());
																float subtotal = qtd*precoun;
																
																int qtdEstoque = Integer.parseInt(auxQtdEstoque.getQuantidade().toString());
																qtdEstoque = (int) (qtdEstoque - qtd);
																
																Jogos jAux = new Jogos();
																jAux.setQuantidade(""+qtdEstoque);
																JogoHelper.atualizarEstoque(jAux, id);
																
																acoesLV.setTipoAcao("Locação");
																acoesLV.setNomeJogo(TVnomeJogo3.getText().toString());
																acoesLV.setData(datastr);
																acoesLV.setPrecoUn(TVprecoUn3.getText().toString());
																acoesLV.setQuantidade(quantidadestr);
																acoesLV.setSubTotal(""+subtotal);
																
																acoesLHelper.insertAcao(acoesLV);
																finish();
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
											break;
										}
										
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
}

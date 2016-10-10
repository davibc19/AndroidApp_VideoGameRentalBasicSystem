package com.example.formtest;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Relatorios_vendaLocacao extends Activity
{
	JogosDatabaseHelper JogoHelper = new JogosDatabaseHelper(this);
	AcoesLocadoraDatabaseHelper acoesLHelper = new AcoesLocadoraDatabaseHelper(Relatorios_vendaLocacao.this);
	Spinner sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acoes_locadora_adm);
		
		// Alimentando o botão Spinner!
		String[] opTipoSpinnerJogo = new String[] {"Tipo de Negócio", "Compra", "Venda", "Locação"};
		
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
				
				if(nomestr.length() == 0 && opSpinner == "Tipo de Negócio")
				{
					cursor = acoesLHelper.buscarTodosAcoes();	
				}
				else
				{
					if(opSpinner == "Compra")
					{
						// PROCURA POR USUÁRIOS
						cursor = acoesLHelper.buscarAcao(nomestr, opSpinner);
						
					}else if(opSpinner == "Venda")
					{
						cursor = acoesLHelper.buscarAcao(nomestr, opSpinner);
					}
					else if(opSpinner == "Locação")
					{
						cursor = acoesLHelper.buscarAcao(nomestr, opSpinner);
					}
					else
					{
						//OPÇÃO INVÁLIDA
						Toast mensagem = Toast.makeText(Relatorios_vendaLocacao.this, "Tipo de Produto Inválido", Toast.LENGTH_SHORT);
						mensagem.show();
					}
				}
				// Utiliza o Cursor obtido para mostrar o resultado
				
				// Setup mapping from cursor to view fields:
				String[] fromFieldNames = new String[] 
						{acoesLHelper.KEY_NAME,  acoesLHelper.KEY_TYPE, acoesLHelper.KEY_PRECOUN, acoesLHelper.KEY_QUANTIDADE,
						acoesLHelper.KEY_DATA, acoesLHelper.KEY_SUBTOTAL};
				int[] toViewIDs = new int[]
						{R.id.TVRELATORIOnome, R.id.TVRELATORIOtipo, R.id.TVRELATORIOprecoun, R.id.TVRELATORIOquantidade,
						R.id.TVRELATORIOdata, R.id.TVRELATORIOsubtotal};
				
				// Create adapter to may columns of the DB onto elements in the UI.
				
				@SuppressWarnings("deprecation")
				SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(Relatorios_vendaLocacao.this, R.layout.relatorio_base, cursor, fromFieldNames, toViewIDs);
				
				// Set the adapter for the list view
				final ListView myList = (ListView) findViewById(R.id.listView1);
				myList.setAdapter(myCursorAdapter);
			}
		});
	}
}
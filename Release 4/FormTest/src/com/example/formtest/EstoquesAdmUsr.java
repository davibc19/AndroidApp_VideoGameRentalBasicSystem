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

public class EstoquesAdmUsr extends Activity
{
	JogosDatabaseHelper JogoHelper = new JogosDatabaseHelper(this);
	Spinner sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// Liberando visualização para o usuário
		super.onCreate(savedInstanceState);
		setContentView(R.layout.estoques);
		
		// Alimentando o botão Spinner!
		String[] opTipoSpinnerJogo = new String[] {"Tipo de Produto", "Locação", "Venda"};
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opTipoSpinnerJogo);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		sp = (Spinner) findViewById(R.id.spinnerTipoEstoque1);
		sp.setAdapter(adapter);		
		
		// Botões, TextosEditáveis e o que foi digitado
		final Button btVoltar = (Button) findViewById(R.id.buttonCancelarEstoque);
		final Button btBuscar = (Button) findViewById(R.id.buttonPesquisarEstoque);
		final EditText ETnome = (EditText)findViewById(R.id.editTextNomeEstoque);
		
		// Encerra Cadastro
		btVoltar.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View v)
			{
				finish();
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
						Toast mensagem = Toast.makeText(EstoquesAdmUsr.this, "Tipo de Produto Inválido", Toast.LENGTH_SHORT);
						mensagem.show();
					}
				}
				// Utiliza o Cursor obtido para mostrar o resultado
				
				// Setup mapping from cursor to view fields:
				String[] fromFieldNames = new String[] 
						{JogoHelper.KEY_NAME,  JogoHelper.KEY_TYPE, JogoHelper.KEY_QUANTIDADE};
				int[] toViewIDs = new int[]
						{R.id.TVESTOQUEnome, R.id.TVESTOQUEtipo, R.id.TVESTOQUEquantidade};
				
				// Create adapter to may columns of the DB onto elements in the UI.
				
				@SuppressWarnings("deprecation")
				SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(EstoquesAdmUsr.this, R.layout.estoques_base, cursor, fromFieldNames, toViewIDs);
				
				// Set the adapter for the list view
				final ListView myList = (ListView) findViewById(R.id.listViewEstoques);
				myList.setAdapter(myCursorAdapter);
			}
			
		});
	}
}

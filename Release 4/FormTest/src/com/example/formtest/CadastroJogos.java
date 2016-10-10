package com.example.formtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CadastroJogos extends Activity {
	JogosDatabaseHelper jogoHelper = new JogosDatabaseHelper(this);
	private Spinner sp1;
	private Spinner sp2;
	private Spinner sp3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// Liberando visualização para o usuário
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_jogo);
		
		// Configurando os três Spinners utilizados
		String[] opSpinnerJogos1 = new String[] {"...", "Venda", "Locação"};
		String[] opSpinnerJogos2 = new String[] {"...", "Fantasia", "RPG", "Aventura", "Tiro", "Corrida", "MMO", "Educativo", "Esportes", "Ação", "Puzzle"};
		String[] opSpinnerJogos3 = new String[] {"...", "Livre", "10 anos", "12 anos", "14 anos", "16 anos", "18 anos"};
		
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opSpinnerJogos1);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opSpinnerJogos2);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opSpinnerJogos3);
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		sp1 = (Spinner) findViewById(R.id.spinnerJogo1);
		sp1.setAdapter(adapter1);
		
		sp2 = (Spinner) findViewById(R.id.spinnerJogo2);
		sp2.setAdapter(adapter2);
		
		sp3 = (Spinner) findViewById(R.id.spinnerJogo3);
		sp3.setAdapter(adapter3);
		
		// Botões, TextosEditáveis e o que foi digitado
		final Button btVoltar = (Button) findViewById(R.id.cancelar4);
		final Button btCadastrar = (Button) findViewById(R.id.cadastrarJogo);
		
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
				
				if(ETnome.getText().length() == 0 || ETfabricante.getText().length() == 0 || ETprecoCompra.getText().length() == 0 ||
				   ETprecoLocadora.getText().length() == 0 || ETquantidade.getText().length() == 0)
				{
					Toast campoObrigatorio = Toast.makeText(CadastroJogos.this, "Todos os campos são Obrigatórios!", Toast.LENGTH_SHORT);
					campoObrigatorio.show();
				}
				else
				{
					String tipoProduto= (String) sp1.getSelectedItem();
					String estiloJogo = (String) sp2.getSelectedItem();
					String faixaEtaria = (String) sp3.getSelectedItem();
					
					if(tipoProduto == "..." || estiloJogo == "..." || faixaEtaria == "...")
					{
						Toast campoObrigatorio = Toast.makeText(CadastroJogos.this, "Todos os campos são Obrigatórios!", Toast.LENGTH_SHORT);
						campoObrigatorio.show();
					}
					else
					{
						Jogos jogo = new Jogos();
						jogo.setNome(nomestr);
						jogo.setFabricante(fabricantestr);
						jogo.setPrecoCompra(precoComprastr);
						jogo.setPrecoLocadora(precoLocadorastr);
						jogo.setQuantidade(quantidadestr);
						jogo.setTipoProduto(tipoProduto);
						jogo.setEstiloJogo(estiloJogo);
						jogo.setFaixaEtaria(faixaEtaria);
						
						jogoHelper.insertJogos(jogo);
						Toast mensagem = Toast.makeText(CadastroJogos.this, "Jogo Cadastrado!", Toast.LENGTH_SHORT);
						mensagem.show();
						finish();
					}
				}
				
			}
		});
	}
}

package com.example.formtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class JogosDatabaseHelper extends SQLiteOpenHelper
{
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "jogos";
	private static final String TABLE_NAME = "jogos";
	
	static final String COLUMN_NAME = "nome";
	private static final String COLUMN_FABRICANTE = "fabricante";
	private static final String COLUMN_TYPE = "tipoProduto";
	private static final String COLUMN_PRECOCOMPRA = "precoCompra";
	private static final String COLUMN_PRECOLOCADORA = "precoLocadora";
	private static final String COLUMN_QUANTIDADE = "quantidade";
	private static final String COLUMN_ESTILOJOGO = "estiloJogo";
	private static final String COLUMN_FAIXAETARIA = "faixaEtaria";
	
	public String KEY_NAME = "nome";
	public String KEY_FABRICANTE = "fabricante";
	public String KEY_TYPE = "tipoProduto";
	public String KEY_PRECOCOMPRA = "precoCompra";
	public String KEY_PRECOLOCADORA = "precoLocadora";
	public String KEY_QUANTIDADE = "quantidade";
	public String KEY_ESTILOJOGO = "estiloJogo";
	public String KEY_FAIXAETARIA = "faixaEtaria";
	
	public String[] ALL_KEYS = new String[] {"_id, " +KEY_NAME, KEY_TYPE, KEY_FABRICANTE, KEY_PRECOCOMPRA, KEY_PRECOLOCADORA, KEY_QUANTIDADE, KEY_ESTILOJOGO, KEY_FAIXAETARIA};
	
	
	protected SQLiteDatabase db;
	
	private static final String TABLE_CREATE = "create table "+TABLE_NAME+" ( _id integer primary key autoincrement, "+COLUMN_NAME+" TEXT , "+
	COLUMN_TYPE+" TEXT , "+COLUMN_FABRICANTE+" TEXT , "+COLUMN_PRECOCOMPRA+" TEXT , "+COLUMN_PRECOLOCADORA+" TEXT , "+COLUMN_QUANTIDADE+" TEXT ," +
	" "+COLUMN_ESTILOJOGO+" TEXT , "+COLUMN_FAIXAETARIA+" TEXT);";
	
	public JogosDatabaseHelper(Context context)
	{
		super(context , DATABASE_NAME , null , DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(TABLE_CREATE);
		this.db = db;
	}
	
	public void insertJogos(Jogos c)
	{
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME , c.getNome());
		values.put(COLUMN_TYPE, c.getTipoProduto());
		values.put(COLUMN_FABRICANTE , c.getFabricante());
		values.put(COLUMN_PRECOCOMPRA, c.getPrecoCompra());
		values.put(COLUMN_PRECOLOCADORA , c.getPrecoLocadora());
		values.put(COLUMN_QUANTIDADE , c.getQuantidade());
		values.put(COLUMN_ESTILOJOGO , c.getEstiloJogo());
		values.put(COLUMN_FAIXAETARIA , c.getFaixaEtaria());
		
		db.insert(TABLE_NAME, null, values);
	}
	
	public void atualizarEstoque(Jogos jogo, long rowId)
	{
		db = this.getWritableDatabase();
		ContentValues valores = new ContentValues();
		if(jogo.getQuantidade().length() != 0) valores.put("quantidade", jogo.getQuantidade());

		db.update(TABLE_NAME, valores, " _id = ?", new String[]{""+rowId});
	}
	
	public void atualizarJogo(Jogos jogo, long rowId)
	{
		db = this.getWritableDatabase();
		ContentValues valores = new ContentValues();
		if(jogo.getNome().length() != 0) valores.put("nome", jogo.getNome());
		if(jogo.getTipoProduto() != "...") valores.put("tipoProduto", jogo.getTipoProduto());
		if(jogo.getFabricante().length() != 0) valores.put("fabricante", jogo.getFabricante());
		if(jogo.getPrecoCompra().length() != 0) valores.put("precoCompra", jogo.getPrecoCompra());
		if(jogo.getPrecoLocadora().length() != 0) valores.put("precoLocadora", jogo.getPrecoLocadora());
		if(jogo.getQuantidade().length() != 0) valores.put("quantidade", jogo.getQuantidade());
		if(jogo.getEstiloJogo() != "...") valores.put("estiloJogo", jogo.getEstiloJogo());
		if(jogo.getFaixaEtaria() != "...") valores.put("faixaEtaria", jogo.getFaixaEtaria());
		
		db.update(TABLE_NAME, valores, " _id = ?", new String[]{""+rowId});
	}
	
	public void deletarJogo(long rowId)
	{	    
		db.delete(TABLE_NAME, " _id = " + rowId, null);
	}
	
	
	
	public Cursor buscarJogo(String nomeBusca, String tipoBusca)
	{
		db = this.getReadableDatabase();
		Cursor c;
		String where;
		
		if(nomeBusca.length() != 0){
			where = COLUMN_NAME+ " = ?" + " and "+ COLUMN_TYPE+ " = ?";
			c = db.query(true, TABLE_NAME, ALL_KEYS, 
						where, new String[]{nomeBusca, tipoBusca}, null, null, "nome ASC", null);
		}
		else
		{	
			where = COLUMN_TYPE+ " = ?";
			c = db.query(true, TABLE_NAME, ALL_KEYS, 
						where, new String[]{tipoBusca}, null, null, "nome ASC", null);
		}
		
		
		if (c != null) {
			c.moveToFirst();
		}
				
		return c;
	}
	
	public String buscarTipoJogo(long rowId)
	{
		db = this.getReadableDatabase();
		String query = "select _id, tipoProduto from "+TABLE_NAME;
		Cursor cursor = db.rawQuery(query , null);
		
		String id, type;
		String rowIdstr = ""+rowId;
		
		type = "not found";
		
		if(cursor.moveToFirst())
		{
			do
			{
				id = cursor.getString(0);	
				if(id.equals(rowIdstr))
				{
					type = cursor.getString(1);
					break;
				}
				
			}while(cursor.moveToNext());
		}
		
		return type;
	}	
	
	public Cursor buscarTodosJogos()
	{
		String where = null;
		db = this.getReadableDatabase();
		Cursor c = 	db.query(true, TABLE_NAME, ALL_KEYS, 
							where, null, null, null, "tipoProduto ASC, nome ASC", null);
		if (c != null)
		{
			c.moveToFirst();
		}
		return c;
	}
	
	public Jogos buscarDados(long rowId)
	{
		db = this.getReadableDatabase();
		String query = "select _id, nome, tipoProduto, precoCompra, precoLocadora, quantidade from "+TABLE_NAME;
		Cursor cursor = db.rawQuery(query , null);
		
		String id;
		String rowIdstr = ""+rowId;
		
		Jogos objJogo = new Jogos();
		
		if(cursor.moveToFirst())
		{
			do
			{
				id = cursor.getString(0);	
				if(id.equals(rowIdstr))
				{
					objJogo.setNome(cursor.getString(1));
					objJogo.setTipoProduto(cursor.getString(2));
					objJogo.setPrecoCompra(cursor.getString(3));
					objJogo.setPrecoLocadora(cursor.getString(4));
					objJogo.setQuantidade(cursor.getString(5));
					break;
				}
				
			}while(cursor.moveToNext());
		}
		
		return objJogo;
	}	
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
		db.execSQL(query);
		this.onCreate(db);	
	}
	
}
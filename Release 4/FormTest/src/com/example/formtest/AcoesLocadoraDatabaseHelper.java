package com.example.formtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class AcoesLocadoraDatabaseHelper extends SQLiteOpenHelper
{
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "acoesLocadora";
	private static final String TABLE_NAME = "acoesLocadora";

	private static final String COLUMN_TYPE = "tipoAcao";
	private static final String COLUMN_NAME = "nome";
	private static final String COLUMN_PRECOUN = "precoUn";
	private static final String COLUMN_QUANTIDADE = "quantidade";
	private static final String COLUMN_DATA = "data";
	private static final String COLUMN_SUBTOTAL = "subtotal";
	
	public String KEY_TYPE = "tipoAcao";
	public String KEY_NAME = "nome";
	public String KEY_PRECOUN = "precoUn";
	public String KEY_QUANTIDADE = "quantidade";
	public String KEY_DATA = "data";
	public String KEY_SUBTOTAL = "subtotal";
	
	public String[] ALL_KEYS = new String[] {"_id, " +KEY_TYPE, KEY_NAME, KEY_PRECOUN, KEY_QUANTIDADE, KEY_DATA, KEY_SUBTOTAL};
	
	
	protected SQLiteDatabase db;
	
	private static final String TABLE_CREATE = "create table "+TABLE_NAME+" ( _id integer primary key autoincrement, "+COLUMN_NAME+" TEXT , "+
			COLUMN_TYPE+" TEXT, "+COLUMN_PRECOUN+" TEXT , "+COLUMN_QUANTIDADE+" TEXT , "+COLUMN_DATA+" TEXT , "+COLUMN_SUBTOTAL+" TEXT);";
	
	public AcoesLocadoraDatabaseHelper(Context context)
	{
		super(context , DATABASE_NAME , null , DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(TABLE_CREATE);
		this.db = db;
	}
	
	public void insertAcao(AcoesLocadoraVariaveis c)
	{
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_TYPE, c.getTipoAcao());
		values.put(COLUMN_NAME , c.getNomeJogo());
		values.put(COLUMN_PRECOUN, c.getPrecoUn());
		values.put(COLUMN_QUANTIDADE , c.getQuantidade());
		values.put(COLUMN_DATA, c.getData());
		values.put(COLUMN_SUBTOTAL, c.getSubTotal());
		
		db.insert(TABLE_NAME, null, values);
	}

	public Cursor buscarTodosAcoes()
	{
		String where = null;
		db = this.getReadableDatabase();
		Cursor c = 	db.query(true, TABLE_NAME, ALL_KEYS, 
							where, null, null, null, "tipoAcao ASC, nome ASC", null);
		if (c != null)
		{
			c.moveToFirst();
		}
		return c;
	}
	
	public Cursor buscarAcao(String nomeBusca, String tipoBusca)
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
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
		db.execSQL(query);
		this.onCreate(db);	
	}
	
	
	
}

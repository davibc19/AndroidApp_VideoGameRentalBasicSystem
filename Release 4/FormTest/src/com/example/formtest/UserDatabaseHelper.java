package com.example.formtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper
{
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "usuarios";
	private static final String TABLE_NAME = "usuarios";
	static final String COLUMN_NAME = "nome";
	private static final String COLUMN_TYPE = "tipoCadastro";
	private static final String COLUMN_TELEFONE = "telefone";
	private static final String COLUMN_PASSWORD = "password";
	public String KEY_NAME = "nome";
	public String KEY_TYPE = "tipoCadastro";
	public String KEY_TELEFONE = "telefone";
	public String KEY_PASSWORD = "password";
	public String[] ALL_KEYS = new String[] {"_id, " +KEY_NAME, KEY_TYPE, KEY_TELEFONE, KEY_PASSWORD};
	
	
	protected SQLiteDatabase db;
	
	private static final String TABLE_CREATE = "create table "+TABLE_NAME+" ( _id integer primary key autoincrement, "+COLUMN_NAME+" TEXT , "+
	COLUMN_PASSWORD+" TEXT , "+COLUMN_TYPE+" TEXT , "+COLUMN_TELEFONE+" TEXT);";
	
	public UserDatabaseHelper(Context context)
	{
		super(context , DATABASE_NAME , null , DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(TABLE_CREATE);
		this.db = db;
	}

	public void insertUsuario(Usuario c)
	{
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME , c.getNome());
		values.put(COLUMN_TYPE, c.getTipoCadastro());
		values.put(COLUMN_TELEFONE, c.getTelefone());
		values.put(COLUMN_PASSWORD , c.getPassword());
		
		db.insert(TABLE_NAME, null, values);
	}
	
	public String searchPass(String uname, String typeSelected)
	{
		db = this.getReadableDatabase();
		String query = "select nome, password, tipoCadastro from "+TABLE_NAME;
		Cursor cursor = db.rawQuery(query , null);
		
		String username, pass, typeDb;
		
		pass = "not found";
		
		if(cursor.moveToFirst())
		{
			do
			{
				username = cursor.getString(0);	
				typeDb = cursor.getString(2);
				if(username.equals(uname) && typeDb.equals(typeSelected))
				{
					pass = cursor.getString(1);
					break;
				}
				
			}while(cursor.moveToNext());
		}
		
		return pass;
	}
	
	public void atualizarUsuario(Usuario usuario, long rowId)
	{
		db = this.getWritableDatabase();
		ContentValues valores = new ContentValues();
		if(usuario.getNome().length() != 0) valores.put("nome", usuario.getNome());
		if(usuario.getPassword().length() != 0) valores.put("password", usuario.getPassword());
		if(usuario.getTelefone().length() != 0) valores.put("telefone", usuario.getTelefone());
		
		db.update(TABLE_NAME, valores, " _id = ?", new String[]{""+rowId});
	}
	
	public void deletarUsuario(long rowId)
	{	    
		db.delete(TABLE_NAME, " _id = " + rowId, null);
	}
	
	
	
	public Cursor buscarUsuario(String nomeBusca, String tipoBusca)
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
	
	public Cursor buscarTodosUsuarios()
	{
		String where = null;
		db = this.getReadableDatabase();
		Cursor c = 	db.query(true, TABLE_NAME, ALL_KEYS, 
							where, null, null, null, "tipoCadastro ASC, nome ASC", null);
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
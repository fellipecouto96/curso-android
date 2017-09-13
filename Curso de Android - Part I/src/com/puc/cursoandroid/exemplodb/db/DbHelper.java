package com.puc.cursoandroid.exemplodb.db;

import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "ExemploDb";
	private static final int DATABASE_VERSION = 1;
	
	public static final String STRING = "TEXT";
	public static final String INT = "INTEGER";
	
	public DbHelper(Context context){
		// Método para criar o banco de dados
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Forma padrão de criar tabela
		db.execSQL("CREATE TABLE usuarios (USERNAME TEXT, PASSWORD TEXT);");
		
		String script = sqlCriaTabela(DALContatos.getTableName(), DALContatos.getCamposTipo(), true);
		if (script != null) {
			db.execSQL(script);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Forma padrao
		db.execSQL("DROP TABLE IF EXISTS usuarios");
		//Forma Otimizada
		db.execSQL("DROP TABLE IF EXISTS " + DALContatos.getTableName());
	}
	
	
	public String sqlCriaTabela(String nomeTabela, Map<String,String> campoTipo, Boolean possuiChavePrimaria){
		String script = null;
		try {
			
			script = "CREATE TABLE " + nomeTabela + "(";
			int numeroDeCampos = 0;
			for (Map.Entry<String, String> entry : campoTipo.entrySet())
			{
				if (numeroDeCampos > 1) {
					script += ", ";
				}
			    if (numeroDeCampos == 1 && possuiChavePrimaria) {
					script += entry.getKey() + " " + entry.getValue() + " PRIMARY KEY";
				}
			    else{
			    	script += entry.getKey() + " " + entry.getValue();
			    }
			    numeroDeCampos += 1;
			}
			
			script += ");";
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
		return script;
	}
}
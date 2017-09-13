package com.puc.cursoandroid.exemplodb.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.puc.cursoandroid.exemplodb.db.model.Contato;

public class DALContatos {

	private static final String KEY_ID = "id";
	private static final String KEY_NOME = "nome";
	private static final String KEY_TELEFONE = "telefone";
	private static final String TABLE_CONTATOS = "contatos";

	public static Map<String, String> getCamposTipo() {
		// Forma otimizada
		Map<String, String> camposTipo = new HashMap<String, String>();
		camposTipo.put(KEY_ID, DbHelper.INT);
		camposTipo.put(KEY_NOME, DbHelper.STRING);
		camposTipo.put(KEY_TELEFONE, DbHelper.STRING);
		return camposTipo;
	}

	public static String getTableName(){
		return TABLE_CONTATOS;
	}
	
	private Context _contexto;

	public DALContatos(Context contexto) {
		// TODO Auto-generated constructor stub
		this._contexto = contexto;
	}

	/**
	 * CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adiciona novo contato
	void addContato(Contato Contato) {
		SQLiteDatabase db = new DbHelper(_contexto).getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NOME, Contato.getNome()); 
		values.put(KEY_TELEFONE, Contato.getPhoneNumber()); 

		// Insere no banco de dados
		db.insert(TABLE_CONTATOS, null, values);
		db.close(); // Fecha conexao
	}

	// Getting Contato
	Contato getContato(int id) {
		SQLiteDatabase db = new DbHelper(_contexto).getReadableDatabase();

		Cursor cursor = db.query(TABLE_CONTATOS, new String[] { KEY_ID,
				KEY_NOME, KEY_TELEFONE }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Contato Contato = new Contato(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2));
		// return Contato
		return Contato;
	}

	// Getting All Contatos
	public List<Contato> getAllContatos() {
		List<Contato> ContatoList = new ArrayList<Contato>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTATOS;

		SQLiteDatabase db = new DbHelper(_contexto).getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contato Contato = new Contato();
				Contato.setID(Integer.parseInt(cursor.getString(0)));
				Contato.setNome(cursor.getString(1));
				Contato.setPhoneNumber(cursor.getString(2));
				// Adding Contato to list
				ContatoList.add(Contato);
			} while (cursor.moveToNext());
		}

		// return Contato list
		return ContatoList;
	}

	// Updating Contato
	public int updateContato(Contato Contato) {
		SQLiteDatabase db = new DbHelper(_contexto).getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NOME, Contato.getNome());
		values.put(KEY_TELEFONE, Contato.getPhoneNumber());

		// updating row
		return db.update(TABLE_CONTATOS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(Contato.getID()) });
	}

	// Deleting single Contato
	public void deleteContato(Contato Contato) {
		SQLiteDatabase db = new DbHelper(_contexto).getWritableDatabase();
		db.delete(TABLE_CONTATOS, KEY_ID + " = ?",
				new String[] { String.valueOf(Contato.getID()) });
		db.close();
	}

	// Getting Contatos Count
	public int getContatosCount() {
		String countQuery = "SELECT  * FROM " + TABLE_CONTATOS;
		SQLiteDatabase db = new DbHelper(_contexto).getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}
}

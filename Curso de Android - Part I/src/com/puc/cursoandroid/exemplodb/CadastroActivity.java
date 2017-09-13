package com.puc.cursoandroid.exemplodb;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.puc.cursoandroid.exemplodb.db.DbHelper;

public class CadastroActivity extends Activity {

	private EditText edUsuario;
	private EditText edSenha;
	private EditText edConfirmacaoSenha;
	private Button btnCadastrar;
	private Button btnCancelar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro);
		
		edUsuario = (EditText)findViewById(R.id.edUsuario);
		edSenha = (EditText)findViewById(R.id.edSenha);
		edConfirmacaoSenha = (EditText)findViewById(R.id.edConfirmacaoSenha);
		btnCadastrar = (Button)findViewById(R.id.btnCadastrar);
		btnCancelar = (Button)findViewById(R.id.btnCancelar);
		btnCadastrar.setOnClickListener(clickCadastrar);
		btnCancelar.setOnClickListener(clickCancelar);
		
	}
	
	OnClickListener clickCadastrar = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (edUsuario.getText().toString().isEmpty()) {
				Toast.makeText(getApplicationContext(), "O Campo usuário não pode ser vazio!", Toast.LENGTH_LONG).show();
				edUsuario.requestFocus();
				return;
			}
			
			//Método tradicional de Select
			SQLiteDatabase dbReader = new DbHelper(getApplicationContext()).getReadableDatabase();
			Cursor cursor = null;
			
			//Clausula where
			String where = "USERNAME = ?";
			
			//Parametro da clausula where
			String argumentos[] = new String[] {edUsuario.getText().toString()};
			
			//Quais Colunas quero retornar
			String[] colunas = new String[] { "USERNAME", "PASSWORD"};
			
			cursor = dbReader.query("usuarios", colunas, where, argumentos, null,null,null);
			
			if (cursor != null && cursor.moveToFirst()) {
				Toast.makeText(getApplicationContext(), "Usuário já cadastrado!", Toast.LENGTH_LONG).show();
				edUsuario.requestFocus();
				cursor.close();
				return;
			}
			
			String senha = edSenha.getText().toString();
			String conf = edConfirmacaoSenha.getText().toString();
			//Verifica se as senhas digitadas são iguais
			if (senha.compareTo(conf) != 0) {
				Toast.makeText(getApplicationContext(), "As senhas não coincidem! Tente novamente!", Toast.LENGTH_LONG).show();
				edSenha.requestFocus();
				return;
			}
			
			//Método tradicional de insert
			SQLiteDatabase dbWriter = new DbHelper(getApplicationContext()).getWritableDatabase();
			ContentValues cadastroUsuario = new ContentValues();
			cadastroUsuario.put("USERNAME", edUsuario.getText().toString());
			cadastroUsuario.put("PASSWORD", edSenha.getText().toString());
			
			dbWriter.insert("USUARIOS", null, cadastroUsuario);
			Toast.makeText(getApplicationContext(), "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show();
			//Minha estrategia depois do cadastro finalizado
			finish();
			
		}
	};
	
    OnClickListener clickCancelar = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	};
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}
}

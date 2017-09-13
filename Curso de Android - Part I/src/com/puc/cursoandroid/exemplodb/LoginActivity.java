package com.puc.cursoandroid.exemplodb;

import com.puc.cursoandroid.exemplodb.db.DbHelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText edUsuario;
	private EditText edSenha;
	private Button btnEntrar;
	private Button btnCadastrar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		edUsuario = (EditText) findViewById(R.id.edUsuario);
		edSenha = (EditText) findViewById(R.id.edSenha);
		btnEntrar = (Button) findViewById(R.id.btnEntrar);
		btnCadastrar = (Button) findViewById(R.id.btnCadastrar);

		// OnClick
		btnEntrar.setOnClickListener(clickEntrar);
		btnCadastrar.setOnClickListener(clickCadastrar);
	}

	OnClickListener clickEntrar = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			// Método tradicional de Select
			SQLiteDatabase dbReader = new DbHelper(getApplicationContext())
					.getReadableDatabase();
			Cursor cursor = null;

			// Clausula where
			String where = "USERNAME = ? AND PASSWORD = ?";

			// Parametro da clausula where
			String argumentos[] = new String[] {
					edUsuario.getText().toString(),
					edSenha.getText().toString() };

			// Quais Colunas quero retornar
			String[] colunas = new String[] { "USERNAME", "PASSWORD" };

			cursor = dbReader.query("usuarios", colunas, where, argumentos,
					null, null, null);

			if (cursor != null && cursor.moveToFirst()) {
				cursor.close();
				Intent meusContatos = new Intent(getApplicationContext(),
						ContatosActivity.class);
				startActivity(meusContatos);
				finish();
			} else {
				Toast.makeText(getApplicationContext(),
						"Usuário e/ou senha inválidos!", Toast.LENGTH_LONG)
						.show();
				edUsuario.requestFocus();
				return;
			}

		}
	};

	OnClickListener clickCadastrar = new OnClickListener() {

		@Override
		public void onClick(View v) {

			Intent frmCadastro = new Intent(getApplicationContext(), // Contexto
					CadastroActivity.class); // Classe da Tela
			startActivity(frmCadastro); // Inicia tela

		}
	};
}

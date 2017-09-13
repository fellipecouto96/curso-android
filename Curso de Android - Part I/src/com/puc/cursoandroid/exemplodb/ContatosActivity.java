package com.puc.cursoandroid.exemplodb;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.puc.cursoandroid.exemplodb.db.DALContatos;
import com.puc.cursoandroid.exemplodb.db.model.Contato;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ContatosActivity extends Activity {

	private Button btnNovo;
	private Button btnInfo;
	private static DALContatos dalContatos;
	List<Contato> contatos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contatos);
		if (dalContatos == null) {
			dalContatos = new DALContatos(getApplicationContext());
		}

		btnNovo = (Button) findViewById(R.id.btnNovo);
		btnInfo = (Button) findViewById(R.id.btnInfo);

		btnNovo.setOnClickListener(clickNovo);
		btnInfo.setOnClickListener(clickInfo);

		final ListView listview = (ListView) findViewById(R.id.ListView1);

		final ArrayList<String> list = new ArrayList<String>();
		contatos = dalContatos.getAllContatos();
		for (Contato contato : contatos) {
			list.add(contato.getNome());
		}

		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				android.R.layout.simple_list_item_1, list);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				final String item = (String) parent.getItemAtPosition(position);
				Toast.makeText(parent.getContext(), item, Toast.LENGTH_SHORT)
						.show();

				/*
				 * list.remove(item); adapter.notifyDataSetChanged();
				 */

			}
		});
	}

	OnClickListener clickNovo = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};

	OnClickListener clickInfo = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
			builder.setTitle("Informação")
			       .setMessage("Clique no botão novo, para criar um novo contato!")
			       .setCancelable(false)
			       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                //do things
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
		}
	};

	/* Classe que implementa o adapter que preencherá a tela com itens */

	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}
}

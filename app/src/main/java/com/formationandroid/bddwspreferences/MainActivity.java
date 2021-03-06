package com.formationandroid.bddwspreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.formationandroid.bddwspreferences.json.RetourWS;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity
{
	
	// Constantes :
	private static final String TAG = MainActivity.class.getSimpleName();
	private static final String LIEN = "https://httpbin.org/post";
	private static final String CLE_MEMO = "memo";
	private static final String CLE_POSITION = "position";
	
	// Vues :
	private RecyclerView recyclerView = null;
	private EditText editTextMemo = null;
	
	// Adapter :
	private MemosAdapter memosAdapter = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// init :
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// accès à la base de données :
		List<MemoDTO> listeMemoDTO = AppDatabaseHelper.getDatabase(this).memosDAO().getListeMemos();
		
		// vues :
		recyclerView = findViewById(R.id.liste_memos);
		editTextMemo = findViewById(R.id.saisie_memo);
		
		// à ajouter pour de meilleures performances :
		recyclerView.setHasFixedSize(true);
		
		// layout manager, décrivant comment les items sont disposés :
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		
		// adapter :
		memosAdapter = new MemosAdapter(this, listeMemoDTO);
		recyclerView.setAdapter(memosAdapter);
		
		// affichage de la dernière position cliquée :
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		int dernierePosition = preferences.getInt(CLE_POSITION, -1);
		if (dernierePosition > -1)
		{
			Toast.makeText(this, getString(R.string.main_message_derniere_position, dernierePosition), Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * Appelé lors du clic sur un item de la liste, depuis l'adapter.
	 * @param position Position dans la liste d'objets métier (position à partir de zéro !)
	 */
	public void onClicItem(int position)
	{
		// sauvegarde de la position en shared preferences :
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(CLE_POSITION, position);
		editor.apply();
		
		// mémo :
		MemoDTO memoDTO = memosAdapter.getMemoDTOParPosition(position);
		
		// client HTTP :
		AsyncHttpClient client = new AsyncHttpClient();
		
		// paramètres :
		RequestParams requestParams = new RequestParams();
		requestParams.put(CLE_MEMO, memoDTO.intitule);
		
		// appel :
		client.post(LIEN, requestParams, new AsyncHttpResponseHandler()
		{
			@Override
			public void onSuccess(int statusCode, Header[] headers,
								  byte[] response)
			{
				String retour = new String(response);
				Gson gson = new Gson();
				RetourWS retourWS = gson.fromJson(retour, RetourWS.class);
				Toast.makeText(MainActivity.this, retourWS.form.memo, Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
								  byte[] errorResponse, Throwable e)
			{
				Log.e(TAG, e.toString());
			}
		});
	}
	
	/**
	 * Listener clic bouton valider.
	 * @param view Bouton valider
	 */
	public void onClickBoutonValider(View view)
	{
		// ajout du mémo en base :
		MemoDTO memoDTO = new MemoDTO(editTextMemo.getText().toString());
		AppDatabaseHelper.getDatabase(this).memosDAO().insert(memoDTO);
		
		// rechargement de la liste de mémos depuis la base de données (car le mémo n'est pas forcément ajouté en première position,
		// la liste étant triée par ordre alphabétique) :
		List<MemoDTO> listeMemoDTO = AppDatabaseHelper.getDatabase(this).memosDAO().getListeMemos();
		
		// mise à jour des mémos :
		memosAdapter.actualiserMemos(listeMemoDTO);
		
		// animation de repositionnement de la liste :
		recyclerView.smoothScrollToPosition(0);
		
		// on efface le contenu de la zone de saisie :
		editTextMemo.setText("");
	}
	
}

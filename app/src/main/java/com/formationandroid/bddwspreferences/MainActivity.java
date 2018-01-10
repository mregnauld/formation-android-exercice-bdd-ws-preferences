package com.formationandroid.bddwspreferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener
{
	
	// Vues :
	private RecyclerView recyclerView = null;
	private EditText editTextMemo = null;
	
	// Adapter :
	private MemosAdapter memosAdapter = null;
	
	// Gesture detector :
	private GestureDetector gestureDetector = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// init :
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// création de la base de données, si inexistante :
		DatabaseHelper databaseHelper = new DatabaseHelper(this);
		databaseHelper.getReadableDatabase();
		
		// accès à la base de données :
		MemosDAO coursesDAO = new MemosDAO();
		List<MemoDTO> listeMemoDTO = coursesDAO.getListeMemos(this);
		
		// vues :
		recyclerView = findViewById(R.id.liste_memos);
		editTextMemo = findViewById(R.id.saisie_memo);
		
		// à ajouter pour de meilleures performances :
		recyclerView.setHasFixedSize(true);
		
		// layout manager, décrivant comment les items sont disposés :
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		
		// adapter :
		memosAdapter = new MemosAdapter(listeMemoDTO);
		recyclerView.setAdapter(memosAdapter);
		
		// listener :
		recyclerView.addOnItemTouchListener(this);
		gestureDetector = new GestureDetector(this,
				new GestureDetector.SimpleOnGestureListener()
				{
					@Override
					public boolean onSingleTapUp(MotionEvent event)
					{
						return true;
					}
				});
	}
	
	@Override
	public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent motionEvent)
	{
		if (gestureDetector.onTouchEvent(motionEvent))
		{
			// récupération de l'item cliqué :
			View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
			
			// position dans la liste d'objets métier (position à partir de zéro !) :
			if (child != null)
			{
				int position = recyclerView.getChildAdapterPosition(child);
				Toast.makeText(this, getString(R.string.main_message_position, position), Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void onTouchEvent(RecyclerView rv, MotionEvent e) {}
	
	@Override
	public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
	
	/**
	 * Listener clic bouton valider.
	 * @param view Bouton valider
	 */
	public void onClickBoutonValider(View view)
	{
		// ajout du mémo :
		memosAdapter.ajouterMemo(new MemoDTO(editTextMemo.getText().toString()));
		
		// animation de repositionnement de la liste (sinon on ne voit pas l'item ajouté) :
		recyclerView.smoothScrollToPosition(0);
		
		// on efface le contenu de la zone de saisie :
		editTextMemo.setText("");
	}
	
}

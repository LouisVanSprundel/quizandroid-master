package com.example.quizz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.Menu;
import android.view.View.OnClickListener;

public class Connection_activity extends Activity {

	private Button b = null;
	private EditText e = null;
	private EditText p = null;
	public String adresse;
	public String pseudo;
	Thread t1;
	static Intent intent ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connection_activity_layout);

		e = (EditText) findViewById(R.id.adresse);
		p = (EditText) findViewById(R.id.pseudo_text);

		b = (Button) findViewById(R.id.boutonconnection);
		b.setOnClickListener(new OnClickListener(){


			@Override
			public void onClick(View v) {
				//on récupère l'adresse entrée par l'utilisateur
				adresse = e.getText().toString();
				pseudo = p.getText().toString();
				//on lance le thread qui permet Connection_thread
				t1 = new Thread(new Connection_thread(adresse,pseudo));
				t1.start();

				while(Connection_thread.ret.isEmpty());

				//On lance l'activité QCM qui affiche les questions/réponses du serveur
				intent = new Intent(Connection_activity.this, Qcm_activity.class);
				startActivity(intent);
				Connection_activity.this.finish();
			}
		});


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.connection_activity, menu);
		return true;
	}

}

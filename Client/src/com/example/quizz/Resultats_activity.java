package com.example.quizz;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class Resultats_activity extends Activity{


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resultat_activity_layout);

		TextView tv = (TextView) findViewById(R.id.SCORE);
		tv.setText(Connection_thread.score);

		Button bt = (Button) findViewById(R.id.quitter);
		bt.setOnClickListener(new OnClickListener(){


			@Override
			public void onClick(View v) {
				//				for(int i = 0 ; i<(connect.nb_questions)*7 ;i++){
				//					connect.ret.remove(i);
				//				}
				Resultats_activity.this.finish();
			}
		});

	}
}


package com.example.quizz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Qcm_activity extends Activity{

	static int i=0;
	static int x = 0;

	Intent intent2;
	final Context context = this;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qcm_activity_layout);


		//On remplit les view par les questions réponses
		TextView tv = (TextView) findViewById(R.id.question_numero);
		tv.setText(Connection_thread.ret.get(0+i));

		Button bt1 = (Button) findViewById(R.id.bt1);
		bt1.setText(Connection_thread.ret.get(1+i));

		Button bt2 = (Button) findViewById(R.id.bt2);
		bt2.setText(Connection_thread.ret.get(2+i));

		Button bt3 = (Button) findViewById(R.id.bt3);
		bt3.setText(Connection_thread.ret.get(3+i)); 

		Button bt4 = (Button) findViewById(R.id.bt4);
		bt4.setText(Connection_thread.ret.get(4+i));


		bt1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Connection_thread.out.println("1");
				Connection_thread.out.flush();
				Dialogue();
			}
		});

		bt2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Connection_thread.out.println("2");
				Connection_thread.out.flush();
				Dialogue();
			}
		});

		bt3.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Connection_thread.out.println("3");
				Connection_thread.out.flush();
				Dialogue();
			}
		});

		bt4.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Qcm_activity.this.onPause();
				Connection_thread.out.println("4");
				Connection_thread.out.flush();
				Dialogue();

			}
		});
	}

	public void Dialogue(){
		String cliquer = "Cliquez pour continuer";
		StringBuilder str = new StringBuilder() ;

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if(Connection_thread.index == Connection_thread.nb_questions*7){

			str.append("\n");
			str.append("Cliquez, puis veuillez attendre ");
			str.append("\n");
			str.append("que les autres joueurs aient terminé");
			cliquer = str.toString();
		}
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

		// set title
		alertDialogBuilder.setTitle("Resultats");

		// set dialog message
		alertDialogBuilder
		.setMessage(Connection_thread.ret.get(5+i)+"\n"+Connection_thread.ret.get(6+i))
		.setCancelable(false)
		.setPositiveButton(cliquer,new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				if(Connection_thread.index != Connection_thread.nb_questions*7){
					i=i+7;
					Qcm_activity.this.onResume();
				}
				else
				{
					
					while(!"ATTENDRE".equals(Connection_thread.fin_attente))
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					Qcm_activity.this.finish();
					intent2 = new Intent(Qcm_activity.this, Resultats_activity.class);
					startActivity(intent2);


				}
			}		
		});


		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

	}

	public void Dialogue_fin(){
		while(!"ATTENDRE".equals(Connection_thread.fin_attente)){
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

			// set title
			alertDialogBuilder.setTitle("Attente");

			// set dialog message
			alertDialogBuilder
			.setMessage("Veuillez attendre les autres joueurs")
			.setCancelable(false);

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		}
	}



	@Override
	protected void onResume() {

		super.onResume();
		this.onCreate(null);
	}
}




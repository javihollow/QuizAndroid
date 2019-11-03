package com.example.quiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class GameoverActivity extends AppCompatActivity {

    public ArrayList<Puntuacion> puntuaciones;
    public Puntuacion puntuacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gameover);
        Intent intent = getIntent();
        int puntos = Integer.parseInt(intent.getStringExtra("POINTS"));
        String jugador = intent.getStringExtra("PLAYER");

        TextView score = findViewById(R.id.score);
        score.setText("Su puntuación es de: \n          "+ puntos +" puntos");

        Button replay = findViewById(R.id.replay);
        replay.setText("Volver a Jugar");
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity();
            }
        });

        //Create parser
        ScoreParser parser = new ScoreParser();

        //Read previous stored scores
        puntuaciones = new ArrayList<Puntuacion>();
        puntuaciones = parser.parseXML(getApplicationContext());

        //Add new player score
        puntuacion = new Puntuacion();
        puntuacion.setJugador(jugador);
        puntuacion.setPuntos(puntos);
        puntuaciones.add(puntuacion);

        //Sort scores by highest
        Collections.sort(puntuaciones);

        //Setup 10 best scores
        if(puntuaciones.size() > 10){
            puntuaciones = new ArrayList<>(puntuaciones.subList(0, 10));
        }

        //Toast.makeText(getApplicationContext(), puntuacion.getPuntos(), Toast.LENGTH_LONG).show();
        //Store new scores
        parser.writeXML(getApplicationContext(), puntuaciones);
    }


    protected void changeActivity(){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}

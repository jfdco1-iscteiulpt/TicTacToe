package com.example.johny.galo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    char[][] quadro;
    Basedados bd;

    static final int SIZE = 3;
    private int matrix_size;
    private int current_size;

    android.widget.GridLayout gamegrid;

    private char CurrentTurn;

    private char StartTurn;

    private String player1;
    private String player2;

    private static final String TAG = "MainActivity";

    ImageView[][] images;

    ImageView start;

    boolean acabou;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quadro = new char[SIZE][SIZE];

        matrix_size=SIZE*SIZE;

        current_size=0;

        gamegrid = (android.widget.GridLayout) findViewById(R.id.grelha);


        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        String current_c =  sharedPref.getString("startsym",null);

        if(current_c!=null) {

            StartTurn = current_c.charAt(0);

        }else{

            StartTurn='X';

        }


        CurrentTurn = StartTurn;

        acabou=false;

        int rowsize = gamegrid.getRowCount();

        Log.d(TAG, "Numero de colunas: " + rowsize);


        startMatrix();
        /*
        gamegrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                play(v);

            }
        });

        */


        start = (ImageView) findViewById(R.id.comecar);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                Intent current = getIntent();
                finish();
                startActivity(current);
                */
                reset();
            }
        });


        images = new ImageView[3][3];
        images[0][0] = (ImageView) findViewById(R.id.imageView0);
        images[1][0] = (ImageView) findViewById(R.id.imageView1);
        images[2][0] = (ImageView) findViewById(R.id.imageView2);
        images[0][1] = (ImageView) findViewById(R.id.imageView3);
        images[1][1] = (ImageView) findViewById(R.id.imageView4);
        images[2][1] = (ImageView) findViewById(R.id.imageView5);
        images[0][2] = (ImageView) findViewById(R.id.imageView6);
        images[1][2] = (ImageView) findViewById(R.id.imageView7);
        images[2][2] = (ImageView) findViewById(R.id.imageView8);


        for (int x = 0; x != images.length; x++) {
            for (int y = 0; y != images[x].length; y++) {
                final int i = x;
                final int j = y;

                images[i][j].setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {

                        play2(images[i][j], i, j);

                    }
                });

            }
        }

        bd = new Basedados(this);


    }


    public void play(View view) {

        ImageView tappedView = (ImageView) view;

        if (CurrentTurn == 'X') {
            tappedView.setTranslationY(-3000f);
            tappedView.setImageResource(R.drawable.bola);
            tappedView.animate().translationYBy(3000f).setDuration(500);
            CurrentTurn = 'O';

        } else if (CurrentTurn == 'O') {

            tappedView.setTranslationY(-3000f);
            tappedView.setImageResource(R.drawable.cruz);
            tappedView.animate().translationYBy(3000f).setDuration(500);
            CurrentTurn = 'X';
        }


    }


    public void play2(ImageView tappedView, int x, int y) {


        //ImageView vazio = new ImageView(this);
        //vazio.setImageResource(R.drawable.vazio);

        //if(tappedView.equals(vazio)) {

        if(!acabou) {

            Log.d(TAG, "As coordenadas do botão são Linha: " + x + " Coluna:" + y);

            Log.d(TAG, "Valor da matrix é " + quadro[x][y]);

            boolean vazio = verificar_vazio(x, y);


            if (vazio) {

                if (CurrentTurn == 'X') {
                    tappedView.setTranslationY(-3000f);
                    tappedView.setImageResource(R.drawable.cruz);
                    tappedView.animate().translationYBy(3000f).setDuration(500);
                    insertIntoMatrix(CurrentTurn, x, y);
                    verificar_vitoria();
                    CurrentTurn = 'O';

                } else if (CurrentTurn == 'O') {

                    tappedView.setTranslationY(-3000f);
                    tappedView.setImageResource(R.drawable.bola);
                    tappedView.animate().translationYBy(3000f).setDuration(500);
                    insertIntoMatrix(CurrentTurn, x, y);
                    verificar_vitoria();
                    CurrentTurn = 'X';
                }



            }
            //}

        }
    }


    public void reset() {

        for (int x = 0; x != images.length; x++) {
            for (int y = 0; y != images[x].length; y++) {

                quadro[x][y] = ' ';
                ImageView img = images[x][y];
                img.setImageResource(R.drawable.vazio);

            }
        }

        acabou=false;
        CurrentTurn=StartTurn;
        current_size=0;

    }


    public void insertIntoMatrix(char turn, int x, int y) {

        quadro[x][y] = turn;
        current_size++;
        Log.d(TAG,"De momento estão introduzidos " + current_size + " caracteres");
        Log.d(TAG,"O tamanho total da matriz é " + matrix_size);

    }


    public boolean verificar_vazio(int x, int y) {

        if (quadro[x][y] == ' ') {

            return true;

        } else {

            return false;

        }

    }


    public void startMatrix() {

        for (int x = 0; x != quadro.length; x++) {
            for (int y = 0; y != quadro[x].length; y++) {

                quadro[x][y] = ' ';

            }
        }

    }


    public void verificar_vitoria() {

        boolean vencedor_linhas = false;

        //verificar linhas
        for (int i = 0; i < 3; i++) {

            vencedor_linhas = verificar_linha(i, CurrentTurn);

            if (vencedor_linhas)
                break;
        }


        //verficar colunas

        boolean vencedor_colunas = false;

        for (int j = 0; j < 3; j++) {

            vencedor_colunas = verificar_coluna(j, CurrentTurn);

            if (vencedor_colunas)
                break;
        }

        //verificar diagonais

        boolean vencedor_diagonal = false;

        vencedor_diagonal=verificar_diagonal(CurrentTurn);

        if(vencedor_linhas || vencedor_colunas || vencedor_diagonal){

            if(CurrentTurn==StartTurn) {

                Toast toast = Toast.makeText(getApplicationContext(),
                        player1 + " ganhou ao " + player2, Toast.LENGTH_SHORT);

                toast.show();
                insertIntoDB(CurrentTurn);



            }else{

                Toast toast = Toast.makeText(getApplicationContext(),
                        player2 + " ganhou ao " + player1, Toast.LENGTH_SHORT);

                toast.show();
                insertIntoDB(CurrentTurn);

            }

            acabou=true;


        }else if((!vencedor_linhas || !vencedor_colunas || !vencedor_diagonal) && current_size==matrix_size){


            Toast toast = Toast.makeText(getApplicationContext(),
                    "A partida acabou em empate", Toast.LENGTH_SHORT);
            toast.show();
            insertIntoDB(' ');

        }


    }


    public boolean verificar_linha(int i, char turn) {


        if (quadro[i][0] == turn && quadro[i][1] == turn && quadro[i][2] == turn)
            return true;
        else
            return false;

        /*
        for(int k = 0 ; k < 3 ; k++)
        {
            quadro[i][k] ==

        }
        */
    }


    public void insertIntoDB(char c){


        if(c=='X')
            bd.consultaEscrita("insert into jogos (tresultado,nome_imagem,tempo) values " +
                    "('Vitoria de', 'cruz', " + (new Date().getTime() + ")"));
        else if (c=='O')
            bd.consultaEscrita("insert into jogos (tresultado,nome_imagem,tempo) values " +
                    "('Vitoria de', 'bola', " + (new Date().getTime() + ")"));
        else
            bd.consultaEscrita("insert into jogos (tresultado,nome_imagem,tempo) values " +
                    "('Empate', 'vazio', " + (new Date().getTime() + ")"));

    }


    public boolean verificar_coluna(int j, char turn) {

        if (quadro[0][j] == turn && quadro[1][j] == turn && quadro[2][j] == turn)
            return true;
        else
            return false;
    }


    public boolean verificar_diagonal(char turn) {

        if (quadro[0][0] == turn && quadro[1][1] == turn && quadro[2][2] == turn) {

            return true;
        }else if (quadro[2][0] == turn && quadro[1][1] == turn && quadro[0][2] == turn){

            return true;

        }else {

            return false;
        }

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        getMenuInflater().inflate(R.menu.game_settings,menu);


        return true;

    }



    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){

            case R.id.menu_regras:
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,RegrasActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_settings:
                Intent settings_itent = new Intent();
                settings_itent.setClass(MainActivity.this,SettingsActivity.class);
                startActivity(settings_itent);
                return true;

            case R.id.menu_ultimos_jogos:
                Intent intent2 = new Intent();
                intent2.setClass(MainActivity.this,UltimosJogosActivity.class);
                startActivity(intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }


    public void onStart(){

        super.onStart();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        String current_c =  sharedPref.getString("startsym",null);

        if(current_c!=null) {

            StartTurn = current_c.charAt(0);

        }else{

            StartTurn='X';

        }

        player1 = sharedPref.getString("p1",null);
        player2 = sharedPref.getString("p2",null);




    }


}

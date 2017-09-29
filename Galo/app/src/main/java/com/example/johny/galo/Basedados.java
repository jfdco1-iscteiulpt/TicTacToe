package com.example.johny.galo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by FormaPC10 on 20/07/2017.
 */

public class Basedados {
    private Conexao conexaobd;
    private SQLiteDatabase bd;

    private class Conexao extends SQLiteOpenHelper{

        public Conexao(Context context){
            super(context,"bdgalo",null,1);

        }

        public void onCreate(SQLiteDatabase db){

            db.execSQL("create table jogos (_id integer primary KEY, tresultado varchar(50), nome_imagem varchar(50), tempo integer )");

        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){


        }

    }

    public Basedados(Context contexto_inicial){

        conexaobd = new Conexao(contexto_inicial);
        bd = conexaobd.getWritableDatabase();

    }

    public void fechar(){
        conexaobd.close();

    }

    public void consultaEscrita(String consulta){
        bd.execSQL(consulta);
    }

    public Cursor consultaLeitura(String consulta){
        return bd.rawQuery(consulta,null);
    }

}

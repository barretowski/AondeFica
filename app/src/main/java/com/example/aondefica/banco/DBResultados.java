package com.example.aondefica.banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBResultados extends SQLiteOpenHelper {

    private static final int VERSAO = 1;

    public DBResultados(Context context)
    {
        super(context, "resultados.db", null, VERSAO);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {   db.execSQL("CREATE TABLE resultados(res_id INTEGER PRIMARY KEY AUTOINCREMENT,res_cep VARCHAR (15), res_logradouro VARCHAR (30), res_complemento VARCHAR(30), res_bairro VARCHAR(30), res_localidade VARCHAR(30), res_uf VARCHAR(2), res_ibge VARCHAR(20), res_gia VARCHAR (10), res_ddd INTEGER, res_siafi INTEGER);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL ("DROP TABLE IF EXISTS resultados");
        onCreate(db);
    }
}

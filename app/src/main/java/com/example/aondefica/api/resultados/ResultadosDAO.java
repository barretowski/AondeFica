package com.example.aondefica.api.resultados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.aondefica.banco.Conexao;

import java.util.ArrayList;

public class ResultadosDAO {
    private Conexao con;
    private final String TABLE="resultados";

    public ResultadosDAO (Context context)
    {   con = new Conexao(context);
        con.conectar();
    }
    public boolean salvar(Resultados r)
    {   ContentValues dados=new ContentValues();
        dados.put("res_cep", r.getCep());
        dados.put("res_logradouro", r.getLogradouro());
        dados.put("res_complemento", r.getComplemento());
        dados.put("res_bairro", r.getBairro());
        dados.put("res_localidade", r.getLocalidade());
        dados.put("res_uf", r.getUf());
        dados.put("res_ibge", r.getIbge());
        dados.put("res_gia", r.getGia());
        dados.put("res_ddd", r.getDdd());
        dados.put("res_siafi", r.getSiafi());
        return con.inserir(TABLE,dados)>0;
    }
    public boolean alterar(Resultados r)
    {   ContentValues dados=new ContentValues();
        dados.put("res_id",r.getId());
        dados.put("res_cep", r.getCep());
        dados.put("res_logradouro", r.getLogradouro());
        dados.put("res_complemento", r.getComplemento());
        dados.put("res_bairro", r.getBairro());
        dados.put("res_localidade", r.getLocalidade());
        dados.put("res_uf", r.getUf());
        dados.put("res_ibge", r.getIbge());
        dados.put("res_gia", r.getGia());
        dados.put("res_ddd", r.getDdd());
        dados.put("res_siafi", r.getSiafi());
        return con.alterar(TABLE,dados,"res_id="+r.getId())>0;
    }
    public boolean apagar(long chave)
    {  return con.apagar(TABLE,"res_id="+chave)>0;
    }
    public boolean apagarTodos()
    {  return con.apagarTodos(TABLE)>0;
    }
    public Resultados get(int id)
    {   Resultados o = null;
        Cursor cursor=con.consultar("select * from "+TABLE);
        if(cursor.moveToFirst())
            o=new Resultados(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor.getString(3));
        cursor.close();;
        return o;
    }
    public ArrayList<Resultados> get(String filtro)
    {   ArrayList <Resultados> objs = new ArrayList();
        String sql="select * from "+TABLE;
        if (!filtro.equals(""))
            sql+=" where "+filtro;
        Cursor cursor=con.consultar(sql);
        if(cursor.moveToFirst())
            while (!cursor.isAfterLast()) {
                objs.add(new Resultados(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor.getString(3)));
                cursor.moveToNext();}
        cursor.close();;
        return objs;
    }


}

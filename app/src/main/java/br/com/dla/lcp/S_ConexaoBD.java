package br.com.dla.lcp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class S_ConexaoBD extends SQLiteOpenHelper {


    private static final String DATABASE_NAME           = S_Dados.getDatabaseName();
    private static final int    DATABASE_VERSION        = S_Dados.getDatabaseVersao();
    private static final String DATABASE_TABLE_LIST     = S_Dados.getDatebaseTableList();
    private static final String DATABASE_TABLE_PRODUTOS = S_Dados.getDatebaseTableProduct();
    private static final String DATABASE_TL_DROP        = S_Dados.getDatabaseTlDrop();
    private static final String DATABASE_TP_DROP        = S_Dados.getDatabaseTpDrop();


    public S_ConexaoBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase conexBD) {
        //criando tables
        try {
            conexBD.execSQL(DATABASE_TABLE_LIST);
            conexBD.execSQL(DATABASE_TABLE_PRODUTOS);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase conexBD, int oldVersion, int newVersion) {
        conexBD.execSQL(DATABASE_TL_DROP );
        conexBD.execSQL(DATABASE_TP_DROP );
        onCreate(conexBD);
    }

}

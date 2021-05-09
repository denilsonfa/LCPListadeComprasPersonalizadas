package br.com.dla.lcp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

public class S_ConexaoDAO {
    S_Dados dados = new S_Dados();

    //	---------------------		Atributos - BD		---------------------	//
    private final S_ConexaoBD conexao;

    //	---------------------		Atributos - dados		---------------------	//
    //DATABASE NAME
    protected static final String       DATABASE_NAME = S_Dados.DATABASE_NAME;

    //TABELA LISTA
    protected String    nomeList    = dados.getNomeList();
    protected static final String       TL_NAME   = S_Dados.TL_NAME;
    protected static final String       TL_ITEM01 = S_Dados.TL_ITEM01;
    protected static final String       TL_ITEM02 = S_Dados.TL_ITEM02;
    protected static final String       TL_ITEM03 = S_Dados.TL_ITEM03;
    protected static final String       TL_ITEM04 = S_Dados.TL_ITEM04;
    protected static final String       TL_ITEM05 = S_Dados.TL_ITEM05;
    protected static final String       TL_ITEM06 = S_Dados.TL_ITEM06;
    protected static final String       TL_DATE   = S_Dados.TL_DATE;

    //TABELA PRODUTOS
    protected static final String       TP_NAME =   S_Dados.TP_NAME;
    protected static final String       TP_ITEM01 = S_Dados.TP_ITEM01;
    protected static final String       TP_ITEM02 = S_Dados.TP_ITEM02;
    protected static final String       TP_ITEM03 = S_Dados.TP_ITEM03;
    protected static final String       TP_ITEM04 = S_Dados.TP_ITEM04;
    protected static final String       TP_ITEM05 = S_Dados.TP_ITEM05;
    protected static final String       TP_ITEM06 = S_Dados.TP_ITEM06;
    protected static final String       TP_ITEM07 = S_Dados.TP_ITEM07;
    protected static final String       TP_ITEM08 = S_Dados.TP_ITEM08;
    protected static final String       TP_FK     = S_Dados.TP_FK;

    protected static final String       PONT_VIRG       = S_Dados.PONT_VIRG;
    protected static final String       WHERE_IDPRODUCT = S_Dados.getWhereIdProduct();

    //COMANDOS SQLITE
    private static final String DATABASE_SELECT_JOIN                = S_Dados.getDatebaseSelectJoin();
    private static final String DATABASE_SELECT_JOIN01              = S_Dados.getDatebaseSelectJoin01();
    private static final String DATABASE_SELECT_JOIN02              = S_Dados.getDatebaseSelectJoin02();
    private static final String DATABASE_SELECT_JOIN03              = S_Dados.getDatebaseSelectJoin03();

    private static final String DATABASE_SELECT_LIST_MAX            = S_Dados.getDatabaseSelectListMax();
    private static final String DATABASE_SELECT_LIST_MAX_CHECK      = S_Dados.getDatabaseSelectListMaxCheck();
    private static final String DATABASE_SELECT_LIST_NOME           = S_Dados.getDatabaseSelectListNome();
    private static final String DATABASE_SELECT_LIST_DATA           = S_Dados.getDatabaseSelectListData();
    private static final String DATABASE_SELECT_LIST_CHK            = S_Dados.getDatabaseSelectListChk();
    private static final String DATABASE_SELECT_LIST_TL             = S_Dados.getDatabaseSelectListTL();
    private static final String DATABASE_SELECT_LIST_TOTAL_MAX      = S_Dados.getDatabaseSelectListTotalMax();
    private static final String DATABASE_COUNT_LISTA_CHECK          = S_Dados.getDatabaseSelectListIscheck();

    private static final String DATABASE_SELECT_PRODUCT             = S_Dados.getDatabaseSelectProduct();
    private static final String DATABASE_SELECT_PRODUCT_TOTAL       = S_Dados.getDatabaseSelectProductTotal();
    private static final String DATABASE_SELECT_LIST                = S_Dados.getDatabaseSelectList();
    private static final String DATABASE_SELECT_LIST_CHECK          = S_Dados.getDatabaseSelectListCheck();
    private static final String DATABASE_SELECT_LIST_ISCHECK        = S_Dados.getDatabaseSelectListIsCheck();
    private static final String DATABASE_SELECT_IDPRODUCT           = S_Dados.getDatabaseSelectIdproduct();

    private static final String DATABASE_SELECT_IDPRODUCT01         = S_Dados.getDatabaseSelectIdproduct01();
    private static final String DATABASE_SELECT_IDPRODUCT02         = S_Dados.getDatabaseSelectIdproduct02();

    private static final String DATABASE_COUNT_PRODUCT_LIST         = S_Dados.getDatabaseCountProductList();
    private static final String DATABASE_COUNT_PRODUCT_CHECK01      = S_Dados.getDatabaseCountProductCheck01();
    private static final String DATABASE_COUNT_PRODUCT_CHECK02      = S_Dados.getDatabaseCountProductCheck02();

    private static final String DATABASE_COUNT_TOTAL_PRODUCT        = S_Dados.getDatabaseCountTotalProduct();
    private static final String DATABASE_COUNT_TOTAL_LIST           = S_Dados.getDatabaseCountTotalList();

    //	---------------------	Metodo Construtor	---------------------	//
    public S_ConexaoDAO(Context context){
        conexao = new S_ConexaoBD(context);
    }

    //	---------------------		Metodos - CRUD (table: lista / produtos)		---------------------	//

    //	---------------------		CREATE		---------------------	//

    //	-----***-----	Create - table lista	-----***-----	//
    public void createList(long numListDia, long numListMes) {
        ContentValues valores;
        long resultado;
        @SuppressLint("DefaultLocale")
        final String numListFormat = String.format("%02d", numListDia) +"/"+ String.format("%02d", numListMes);

        SQLiteDatabase DATABASE = conexao.getWritableDatabase();
        valores = new ContentValues();
        valores.put( TL_ITEM02, nomeList + numListFormat );
        valores.put( TL_ITEM03, TL_DATE);
        valores.put( TL_ITEM04, "0");
        valores.put( TL_ITEM05, "0");

        resultado = DATABASE.insert(TL_NAME, null, valores);
        DATABASE.close();
    }

    //	-------------------------------------------------------------------	//

    //	-----***-----	Create - table produtos	-----***-----	//
    public String createProduct(int idListP, String nomeProduct, double quantProduct, String medidaProduct, String tipoProduct, double valorProduct, boolean checkProduct) {
        ContentValues valores;
        long resultado;
        SQLiteDatabase DATABASE = conexao.getWritableDatabase();
        valores = new ContentValues();
        valores.put( TP_ITEM02, idListP );
        valores.put( TP_ITEM03, nomeProduct );
        valores.put( TP_ITEM04, quantProduct );
        valores.put( TP_ITEM05, medidaProduct );
        valores.put( TP_ITEM06, tipoProduct );
        valores.put( TP_ITEM07, valorProduct );
        valores.put( TP_ITEM08, checkProduct );

        resultado = DATABASE.insert(TP_NAME, null, valores);
        DATABASE.close();

        if (resultado ==-1)
            return "Erro ao adicionar Produto!";
        else
            return "Produto Adicionado!";
    }
    //	-------------------------------------------------------------------	//

    //	---------------------		READ		---------------------	//

    //	-----***-----	Read - table lista join produtos	-----***-----	//
    public Cursor readProduct(String idListL) {
        SQLiteDatabase DATABASE = conexao.getReadableDatabase();

        String DATABASE_SELECT_JOIN = DATABASE_SELECT_JOIN01+idListL+DATABASE_SELECT_JOIN02+idListL+DATABASE_SELECT_JOIN03;

        Cursor cursor = null;
        if(DATABASE!=null){
            cursor = DATABASE.rawQuery(DATABASE_SELECT_JOIN, null);
            //cursor = DATABASE.rawQuery(DATABASE_SELECT_PRODUCT, null);
        }
        return cursor;
    }
    //	-------------------------------------------------------------------	//

    //	-----***-----	Read - table lista	-----***-----	//
    public Cursor readList() {
        SQLiteDatabase DATABASE = conexao.getReadableDatabase();

        Cursor cursor = null;
        if(DATABASE!=null){
            cursor = DATABASE.rawQuery(DATABASE_SELECT_LIST, null);
        }
        return cursor;
    }
    //	-------------------------------------------------------------------	//

    //	-----***-----	Read - table lista	-----***-----	//
    public Cursor readListCheck() {
        SQLiteDatabase DATABASE = conexao.getReadableDatabase();

        Cursor cursor = null;
        if(DATABASE!=null){
            cursor = DATABASE.rawQuery(DATABASE_SELECT_LIST_CHECK, null);
        }
        return cursor;
    }
    //	-------------------------------------------------------------------	//

    //	-----***-----	Read - table lista	-----***-----	//
    public Cursor readListIsCheck() {
        SQLiteDatabase DATABASE_LIST_IsCheck = conexao.getReadableDatabase();

        Cursor cursor = null;
        if(DATABASE_LIST_IsCheck !=null){
            cursor = DATABASE_LIST_IsCheck.rawQuery(DATABASE_SELECT_LIST_ISCHECK, null);
        }
        return cursor;
    }
    //	-------------------------------------------------------------------	//

    //	-----***-----	Read Count() - table lista	-----***-----	//
    public long numList() {
        SQLiteDatabase DATABASE = conexao.getWritableDatabase();
        return DatabaseUtils.queryNumEntries(DATABASE, TL_NAME)+1;
    }

    //	-----***-----	Read Count() - table lista	-----***-----	//
    public int numItensListS(int idListL) {
        String query = DATABASE_COUNT_PRODUCT_LIST+idListL+PONT_VIRG;
        SQLiteDatabase DATABASEList = conexao.getReadableDatabase();
        Cursor cursor = DATABASEList.rawQuery(query, null);
        return cursor.getCount();

    }
    //	-------------------------------------------------------------------	//

    //	-----***-----	Read Count() - table lista	-----***-----	//
    public double numReadProductTotal(int idListL) {

        double valorTotal = 0;
        SQLiteDatabase DATABASEProductTotal = conexao.getReadableDatabase();
        String query = DATABASE_SELECT_PRODUCT_TOTAL+idListL+PONT_VIRG;

        Cursor cursor = DATABASEProductTotal.rawQuery(query, null);
        if(cursor.moveToFirst()) { valorTotal = cursor.getDouble(0); }

        return valorTotal;

    }
    //	-------------------------------------------------------------------	//

    //	-----***-----	Read Count() - table lista	-----***-----	//
    public int numListaCheck() {

        String query = DATABASE_COUNT_LISTA_CHECK;
        SQLiteDatabase DATABASEList = conexao.getReadableDatabase();
        Cursor cursor = DATABASEList.rawQuery(query, null);
        return cursor.getCount();

    }
    //	-------------------------------------------------------------------	//


    //	-----***-----	Read Count() - table lista	-----***-----	//
    public int numProductCheck(int idListL) {

        String query = DATABASE_COUNT_PRODUCT_CHECK01+idListL+DATABASE_COUNT_PRODUCT_CHECK02;
        SQLiteDatabase DATABASEList = conexao.getReadableDatabase();
        Cursor cursor = DATABASEList.rawQuery(query, null);
        return cursor.getCount();

    }
    //	-------------------------------------------------------------------	//

    //	-----***-----	Read DATABASE - table lista	-----***-----	//
    public static boolean doesDatabaseExist(ContextWrapper context) {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        return dbFile.exists();
    }
    //	-------------------------------------------------------------------	//

    //	-----***-----	Read DATABASE - table lista idUltimaList	-----***-----	//
    public long idUltimaList() {

        long valorTotal = 0;
        SQLiteDatabase DATABASEProductTotal = conexao.getReadableDatabase();
        String query = DATABASE_SELECT_LIST_MAX;

        Cursor cursor = DATABASEProductTotal.rawQuery(query, null);
        if(cursor.moveToFirst()) { valorTotal = cursor.getLong(0); }

        return valorTotal;
    }
    //	-------------------------------------------------------------------	//

    //	-----***-----	Read DATABASE - table lista idUltimaListCHECK	-----***-----	//
    public long idUltimaListCheck() {

        long valorTotal = 0;
        SQLiteDatabase DATABASEProductTotal = conexao.getReadableDatabase();
        String query = DATABASE_SELECT_LIST_MAX_CHECK;

        Cursor cursor = DATABASEProductTotal.rawQuery(query, null);
        if(cursor.moveToFirst()) { valorTotal = cursor.getLong(0); }

        return valorTotal;
    }
    //	-------------------------------------------------------------------	//

    //	-----***-----	Read DATABASE - table lista idUltimaList	-----***-----	//
    public float maxTotalList() {

        float valorTotal = 0;
        SQLiteDatabase DATABASEProductTotal = conexao.getReadableDatabase();
        String query = DATABASE_SELECT_LIST_TOTAL_MAX;

        Cursor cursor = DATABASEProductTotal.rawQuery(query, null);
        if(cursor.moveToFirst()) { valorTotal = cursor.getFloat(0); }

        return valorTotal;
    }
    //	-------------------------------------------------------------------	//

    //	-----***-----	Read DATABASE - table lista idUltimaList	-----***-----	//
    public String readListName(long idListL) {

        String valorTotal = "";
        SQLiteDatabase DATABASEProductTotal = conexao.getReadableDatabase();
        String query = DATABASE_SELECT_LIST_NOME+idListL+PONT_VIRG;

        Cursor cursor = DATABASEProductTotal.rawQuery(query, null);
        if(cursor.moveToFirst()) { valorTotal = cursor.getString(0); }

        return valorTotal;
    }
    //	-------------------------------------------------------------------	//

    //	-----***-----	Read DATABASE - table lista idUltimaList	-----***-----	//
    public String readListData(long idListL) {

        String valorTotal = "";
        SQLiteDatabase DATABASEProductTotal = conexao.getReadableDatabase();
        String query = DATABASE_SELECT_LIST_DATA+idListL+PONT_VIRG;

        Cursor cursor = DATABASEProductTotal.rawQuery(query, null);
        if(cursor.moveToFirst()) { valorTotal = cursor.getString(0); }

        return valorTotal;
    }
    //	-------------------------------------------------------------------	//

    //	-----***-----	Read DATABASE - table lista idUltimaList	-----***-----	//
    public String readListChk(long idListL) {

        String valorTotal = "";
        SQLiteDatabase DATABASEProductTotal = conexao.getReadableDatabase();
        String query = DATABASE_SELECT_LIST_CHK+idListL+PONT_VIRG;

        Cursor cursor = DATABASEProductTotal.rawQuery(query, null);
        if(cursor.moveToFirst()) { valorTotal = cursor.getString(0); }

        return valorTotal;
    }
    //	-------------------------------------------------------------------	//

    //	-----***-----	Read DATABASE - table lista idUltimaList	-----***-----	//
    public String readListTL(long idListL) {

        String valorTotal = "";
        SQLiteDatabase DATABASEProductTotal = conexao.getReadableDatabase();
        String query = DATABASE_SELECT_LIST_TL+idListL+PONT_VIRG;

        Cursor cursor = DATABASEProductTotal.rawQuery(query, null);
        if(cursor.moveToFirst()) { valorTotal = cursor.getString(0); }

        return valorTotal;
    }
    //	-------------------------------------------------------------------	//

    //	-----***-----	Read - idprodutos max 	-----***-----	//
    public long readIdProductMAX(int idListL) {
//        SQLiteDatabase DATABASE = conexao.getReadableDatabase();
//
//        String DATABASE_SELECT_JOIN = DATABASE_SELECT_IDPRODUCT+idListL+PONT_VIRG;
//
//        Cursor cursor = null;
//        if(DATABASE!=null){
//            cursor = DATABASE.rawQuery(DATABASE_SELECT_JOIN, null);
//            //cursor = DATABASE.rawQuery(DATABASE_SELECT_PRODUCT, null);
//        }
//        DATABASE.close();
//        return cursor;

        long valorTotal = 0;
        SQLiteDatabase DATABASEProductTotal = conexao.getReadableDatabase();
        String DATABASE_SELECT_JOIN = DATABASE_SELECT_IDPRODUCT+idListL+PONT_VIRG;

        Cursor cursor = DATABASEProductTotal.rawQuery(DATABASE_SELECT_JOIN, null);
        if(cursor.moveToFirst()) { valorTotal = cursor.getLong(0); }

        return valorTotal;
    }
    //	-------------------------------------------------------------------	//

    //	-----***-----	Read - idprodutos max 	-----***-----	//
    public long readIdProduct(int idListL, int idProduct) {
//        SQLiteDatabase DATABASE = conexao.getReadableDatabase();
//
//        String DATABASE_SELECT_JOIN = DATABASE_SELECT_IDPRODUCT+idListL+PONT_VIRG;
//
//        Cursor cursor = null;
//        if(DATABASE!=null){
//            cursor = DATABASE.rawQuery(DATABASE_SELECT_JOIN, null);
//            //cursor = DATABASE.rawQuery(DATABASE_SELECT_PRODUCT, null);
//        }
//        DATABASE.close();
//        return cursor;

        long valorTotal = 0;
        SQLiteDatabase DATABASEProductTotal = conexao.getReadableDatabase();
        String DATABASE_SELECT_JOIN = DATABASE_SELECT_IDPRODUCT01+idListL+DATABASE_SELECT_IDPRODUCT02+idListL+PONT_VIRG;

        Cursor cursor = DATABASEProductTotal.rawQuery(DATABASE_SELECT_JOIN, null);
        if(cursor.moveToFirst()) { valorTotal = cursor.getLong(0); }

        return valorTotal;
    }
    //	-------------------------------------------------------------------	//

    //	---------------------		UPDATE		---------------------	//

    //	-----***-----	UPDATE DATABASE - table list	-----***-----	//
    public boolean updateListCheck(String idListL, boolean checkList, double totalList) {
        SQLiteDatabase DATABASE = conexao.getWritableDatabase();

        String TL_ITEM01ADD = TL_ITEM01+"=?";

        ContentValues contentValues = new ContentValues();
        contentValues.put("idListL", idListL);
        contentValues.put("checkList", checkList);
        contentValues.put("totalList", totalList);

        long result = DATABASE.update(TL_NAME, contentValues, TL_ITEM01ADD, new String[]{idListL});
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }
    //	-------------------------------------------------------------------	//

    //	-----***-----	UPDATE DATABASE - table product	-----***-----	//
    public boolean updateProduct(String idProduct, String nomeProduct, double quantProduct, String medidaProduct, String tipoProduct) {
        SQLiteDatabase DATABASE = conexao.getWritableDatabase();

        String TP_ITEM01ADD = TP_ITEM01+"=?";

        ContentValues contentValues = new ContentValues();
        contentValues.put("nomeProduct", nomeProduct);
        contentValues.put("quantProduct", quantProduct);
        contentValues.put("medidaProduct", medidaProduct);
        contentValues.put("tipoProduct", tipoProduct);

        long result = DATABASE.update(TP_NAME, contentValues, TP_ITEM01ADD, new String[]{idProduct});
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }
    //	-------------------------------------------------------------------	//

    //	-----***-----	UPDATE DATABASE - table product	-----***-----	//
    public boolean updateProductValor(String idProduct, double valorProduct, boolean checkProduct) {
        SQLiteDatabase DATABASE = conexao.getWritableDatabase();

        String TP_ITEM01ADD = TP_ITEM01+"=?";

        ContentValues contentValues = new ContentValues();
        contentValues.put("valorProduct", valorProduct);
        contentValues.put("checkProduct", checkProduct);

        long result = DATABASE.update(TP_NAME, contentValues, TP_ITEM01ADD, new String[]{idProduct});
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }
    //	-------------------------------------------------------------------	//

    //	---------------------		DELETE		---------------------	//

    //	-----***-----	DELETE DATABASE - table lista	-----***-----	//
    public Boolean deleteProduct(String idProduct){
        SQLiteDatabase DATABASE = conexao.getWritableDatabase();

        String TP_ITEM01ADD = TP_ITEM01+"=?";

        long result = DATABASE.delete(TP_NAME, TP_ITEM01ADD, new String[]{idProduct});
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }
    //	-------------------------------------------------------------------	//

    //	-----***-----	DELETE DATABASE - table lista	-----***-----	//
    public Boolean deleteList(String idList){
        SQLiteDatabase DATABASE = conexao.getWritableDatabase();

        String TP_ITEM01ADD = TL_ITEM01+"=?";

        long result = DATABASE.delete(TL_NAME, TP_ITEM01ADD, new String[]{idList});
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }
    //	-------------------------------------------------------------------	//

    //	-----***-----	Read Count() - Total Product	-----***-----	//
    public int countTotalProduct() {
        String query = DATABASE_COUNT_TOTAL_PRODUCT;
        SQLiteDatabase DATABASEList = conexao.getReadableDatabase();
        Cursor cursor = DATABASEList.rawQuery(query, null);
        return cursor.getCount();

    }
    //	-------------------------------------------------------------------	//

    //	-----***-----	Read Count() - Total List	-----***-----	//
    public int countTotalList() {
        String query = DATABASE_COUNT_TOTAL_LIST;
        SQLiteDatabase DATABASEList = conexao.getReadableDatabase();
        Cursor cursor = DATABASEList.rawQuery(query, null);
        return cursor.getCount();

    }
    //	-------------------------------------------------------------------	//

}

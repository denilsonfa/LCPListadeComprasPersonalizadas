package br.com.dla.lcp;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.util.Calendar;

//	---------------------		Criado por: Denilson Araujo		---------------------	//
//	---------------------		       @Denilson_fa       		---------------------	//

public class S_Dados implements Serializable {

    //	---------------------		Atributos - Date		---------------------	//
    protected static final Calendar date = Calendar.getInstance(); 	//Instanciando data do sistema;

    protected static final int 		    dia = date.get(Calendar.DATE);					//coleta o dia atual do sistema
    protected static final int 		    mes = date.get(Calendar.MONTH)+1;				//soma +1 ao mes coletado pelo sistema, pois ele conta de 0 ao 11
    protected static final int 		    ano = date.get(Calendar.YEAR);					//coleta o ano atual do sistema
    protected static final int 		    hora = date.get(Calendar.HOUR_OF_DAY);			//coleta a hora atual do sistema
    protected static final int 		    min = date.get(Calendar.MINUTE);				//coleta o minuto atual do sistema

    protected static final String 	dateSystem = dia+"/"+mes+"/"+ano+" - "+hora+":"+min;	//log da data da criação da lista de compras
    //exemplo: 01/01/2021 - 12:00


    //	---------------------		Atributos - BD Product		---------------------	//
    protected int 		idProduct;								//ID do Produto
    protected int		idListP;								//ID da lista onde o produto está registrado
    protected boolean	checkProduct;							//Boolean de checar item na lista de consulta
    protected String 	nomeProduct;							//Nome do Produto
    protected double 	quantProduct;							//Quantidade para o produto
    protected String 	medidaProduct;							//Forma de medida de cada produto
    protected String 	tipoProduct;							//Categoria em que o produto se enquadra
    protected double    valorProduct;							//Valor atribuido a unidade/peso do produto
    protected double	totalProduct;							//Valor total dos produtos (valor*quantidade)

    //	---------------------		Atributos - BD List		---------------------	//
    protected int		idListL;								//ID da lista referente ao produto
    protected String	nomeList = "Lista de Compras ";		    //Nome da Lista == (Lista de Compras 01)
    protected String	dataList;								//data da criação da lista
    protected boolean   checkList;                              //Boolean de checar se a lista está completa

    //	---------------------		Atributos - BD Consult		---------------------	//
    protected int		idListC;								//ID para consulta da lista

    //	---------------------		Atributos - SQLite.S_ConexaoBD		---------------------	//
    protected static final String       DATABASE_NAME       = "databaseLCP";  //Nome Database
    protected static final int          DATABASE_VERSION    = 1;              //Versão Database

    //	---------------------		Atributos - SQLite.onCreate		---------------------	//
    //TL == Table Lista
    //TP == Table Produtos
    //TABELA LISTA
    protected static final String       TL_NAME = "lista";

    protected static final String       TL_ITEM01 = "idListL";
    protected static final String       TL_ITEM02 = "nomeList";
    protected static final String       TL_ITEM03 = "dataList";
    protected static final String       TL_ITEM04 = "checkList";
    protected static final String       TL_ITEM05 = "ultimoIdList";

    //TABELA PRODUTOS
    protected static final String       TP_NAME   = "produtos";

    protected static final String       TP_ITEM01 = "idProduct";
    protected static final String       TP_ITEM02 = "idListP";
    protected static final String       TP_ITEM03 = "nomeProduct";
    protected static final String       TP_ITEM04 = "quantProduct";
    protected static final String       TP_ITEM05 = "medidaProduct";
    protected static final String       TP_ITEM06 = "tipoProduct";
    protected static final String       TP_ITEM07 = "valorProduct";
    protected static final String       TP_ITEM08 = "checkProduct";
    protected static final String       TP_ITEM09 = "totalProduct";

    protected static final String       TP_FK     = "fk_Product";
    protected static final String       PONT_VIRG = ";";

    //ALTER TABLE
    protected static final String DATABASE_FK_PRODUCT = " constraint "
            +TP_FK+ " foreign key ("
            +TP_ITEM02+ ") references "
            +TL_NAME+ " ("
            +TL_ITEM01+ ") ";

    //ESTRUTURA DAS TABELAS (sql statement)
    protected static final String DATABASE_TABLE_LIST = "create table " +TL_NAME+ "( "
            +TL_ITEM01+ " integer primary key autoincrement, "
            +TL_ITEM02+ " varchar(20) not null, "
            +TL_ITEM03+ " varchar(20) not null, "
            +TL_ITEM04+ " boolean "
            +");";

    protected static final String DATABASE_TABLE_PRODUTOS = "create table " +TP_NAME+ "( "
            +TP_ITEM01+ " integer primary key autoincrement, "
            +TP_ITEM02+ " integer not null, "
            +TP_ITEM03+ " varchar(50) not null, "
            +TP_ITEM04+ " mediumint not null, "
            +TP_ITEM05+ " varchar(15) not null, "
            +TP_ITEM06+ " varchar(30) not null, "
            +TP_ITEM07+ " double, "
            +TP_ITEM08+ " boolean, "
            +DATABASE_FK_PRODUCT
            +");";

    //DROP TABELAS
    protected static final String DATABASE_TL_DROP = "DROP TABLE IF EXISTS " + TL_NAME;
    protected static final String DATABASE_TP_DROP = "DROP TABLE IF EXISTS " + TP_NAME;

    protected static final String WHERE_IDPRODUCT = "where " + TP_ITEM01 + " = ";

    //Consultas Listas

    protected static final String DATABASE_SELECT_JOIN = " select * from "
            +TL_NAME+" join "
            +TP_NAME+" on "
            +TL_NAME+"."+TL_ITEM01+" = 1 where "
            +TP_NAME+"."+TP_ITEM02+" = 1 order by "
            +TP_ITEM01+" desc;";

    protected static final String DATABASE_SELECT_JOIN01 = " select * from "
            +TL_NAME+" join "
            +TP_NAME+" on "
            +TL_NAME+"."+TL_ITEM01+" = ";

    protected static final String DATABASE_SELECT_JOIN02 = " where "
            +TP_NAME+"."+TP_ITEM02+" = ";

    protected static final String DATABASE_SELECT_JOIN03 = "  order by "
            +TP_ITEM01+" desc;";

    protected static final String DATABASE_SELECT_LIST_MAX = " select MAX("
            +TL_ITEM01+") as "
            +TL_ITEM05+" from "
            +TL_NAME+";";

    protected static final String DATABASE_SELECT_LIST_NOME = " select "
            +TL_ITEM02+" from "
            +TL_NAME+" where "
            +TL_ITEM01+" = ";

    protected static final String DATABASE_SELECT_LIST_DATA = " select "
            +TL_ITEM03+" from "
            +TL_NAME+" where "
            +TL_ITEM01+" = ";

    protected static final String DATABASE_SELECT_LIST_CHK = " select "
            +TL_ITEM04+" from "
            +TL_NAME+" where "
            +TL_ITEM01+" = ";

    //Consultas Productos

    protected static final String DATABASE_SELECT_PRODUCT = " select * from "
            +TP_NAME+" order by "
            +TP_ITEM01+" desc;";

    protected static final String DATABASE_SELECT_PRODUCT_TOTAL = " select SUM("
            +TP_ITEM04+"*"
            +TP_ITEM07+") as "
            +TP_ITEM09+" from "
            +TP_NAME+"  where "
            +TP_ITEM02+" = ";

    protected static final String DATABASE_SELECT_LIST_CHECK = " select * from "
            +TL_NAME+" where "
            +TL_ITEM04+" = 0 order by "
            +TL_ITEM01+" desc;";

    protected static final String DATABASE_SELECT_LIST_ISCHECK = " select * from "
            +TL_NAME+" where "
            +TL_ITEM04+" = 1 order by "
            +TL_ITEM01+" desc;";

    protected static final String DATABASE_SELECT_LIST = " select * from "
            +TL_NAME+" order by "
            +TL_ITEM01+" desc;";

    protected static final String DATABASE_SELECT_IDPRODUCT = " select MAX("
            +TP_ITEM01+") from "
            +TP_NAME+" where "
            +TP_ITEM02+" = ";

    protected static final String DATABASE_SELECT_IDPRODUCT01 = " select "
            +TP_ITEM01+" from "
            +TP_NAME+" where "
            +TP_ITEM02+" = ";

    protected static final String DATABASE_SELECT_IDPRODUCT02 = " and "
            +TP_ITEM01+" = ";

    protected static final String DATABASE_COUNT_PRODUCT_LIST = "SELECT * FROM "
            +TP_NAME+" where "
            +TP_ITEM02+" = ";

    protected static final String DATABASE_COUNT_PRODUCT_CHECK01 = "select * from "
            +TP_NAME+" where "
            +TP_ITEM02+" = ";

    protected static final String DATABASE_COUNT_PRODUCT_CHECK02 = " and "
            +TP_ITEM08+" = 1;";

    //TL_DATE
    @SuppressLint("DefaultLocale")
    protected static final String TL_DATE = String.format("%02d",dia)+"/"
            +String.format("%02d",mes)+"/"
            +String.format("%02d",ano)+" - "
            +String.format("%02d",hora)+":"
            +String.format("%02d",min);

    //	---------------------	Metodo Construtor	---------------------	//
    public S_Dados() {
    }

    //	---------------------	Getters e Setters - BD Product	---------------------	//
    public int getIdProduct() {
        return idProduct;
    }
    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getIdListP() {
        return idListP;
    }
    public void setIdListP(int idListP) {
        this.idListP = idListP;
    }

    public boolean getCheckProduct() {
        return checkProduct;
    }
    public void setCheckProduct(boolean checkProduct) {
        this.checkProduct = checkProduct;
    }

    public String getNomeProduct() {
        return nomeProduct;
    }
    public void setNomeProduct(String nomeProduct) {
        this.nomeProduct = nomeProduct;
    }

    public double getQuantProduct() {
        return quantProduct;
    }
    public void setQuantProduct(double quantProduct) {
        this.quantProduct = quantProduct;
    }

    public String getMedidaProduct() {
        return medidaProduct;
    }
    public void setMedidaProduct(String medidaProduct) {
        this.medidaProduct = medidaProduct;
    }

    public String getTipoProduct() {
        return tipoProduct;
    }
    public void setTipoProduct(String tipoProduct) {
        this.tipoProduct = tipoProduct;
    }

    public double getValorProduct() {
        valorProduct = (Math.rint (valorProduct * 100.0) / 100.0);
        return valorProduct;
    }
    public void setValorProduct(double valorProduct) {
        valorProduct = (Math.rint (valorProduct * 100.0) / 100.0);
        this.valorProduct = valorProduct;
    }

    public double getTotalProduct() {
        totalProduct = (Math.rint (totalProduct * 100.0) / 100.0);
        return totalProduct;
    }
    public void setTotalProduct(double totalProduct) {
        totalProduct = (Math.rint (totalProduct * 100.0) / 100.0);
        this.totalProduct = totalProduct;
    }


    //	---------------------	Getters e Setters - BD List	---------------------	//
    public int getIdListL() {
        return idListL;
    }
    public void setIdListL(int idListL) {
        this.idListL = idListL;
    }

    public String getNomeList() {
        return nomeList;
    }
    public void setNomeList(String nomeList) {
        this.nomeList = nomeList;
    }

    public String getDataList() {
        return dataList;
    }
    public void setDataList(String dataList) {
        this.dataList = dataList;
    }

    public boolean getCheckList() {
        return checkList;
    }
    public void setCheckList(boolean checkList) {
        this.checkList = checkList;
    }

    //	---------------------	Getters e Setters - BD Consult ---------------------	//
    public int getIdListC() {
        return idListC;
    }
    public void setIdListC(int idListC) {
        this.idListC = idListC;
    }

    //	---------------------	Getters - Date ---------------------	//
    public static int getDia(){
        return dia;
    }
    public static int getMes(){
        return mes;
    }
    public static int getAno(){
        return ano;
    }
    public static int getHora(){
        return hora;
    }
    public static int getMin(){
        return min;
    }
    public static String getDatesystem() {
        return dateSystem;
    }

    public static String getPontVirg() {return PONT_VIRG;}

    //	---------------------	Getters - SQLite	---------------------	//
    public static String getDatabaseName() {
        return DATABASE_NAME;
    }
    public static int getDatabaseVersao() {
        return DATABASE_VERSION;
    }

    public static String getDatebaseTableList() {return DATABASE_TABLE_LIST;}
    public static String getDatebaseTableProduct() {return DATABASE_TABLE_PRODUTOS;}

    public static String getDatebaseSelectJoin() {return DATABASE_SELECT_JOIN;}

    public static String getDatebaseSelectJoin01() {return DATABASE_SELECT_JOIN01;}
    public static String getDatebaseSelectJoin02() {return DATABASE_SELECT_JOIN02;}
    public static String getDatebaseSelectJoin03() {return DATABASE_SELECT_JOIN03;}

    public static String getDatabaseSelectListMax() {return DATABASE_SELECT_LIST_MAX;}
    public static String getDatabaseSelectListNome() {return DATABASE_SELECT_LIST_NOME;}
    public static String getDatabaseSelectListData() {return DATABASE_SELECT_LIST_DATA;}
    public static String getDatabaseSelectListChk() {return DATABASE_SELECT_LIST_CHK;}

    public static String getDatabaseSelectProduct() {return DATABASE_SELECT_PRODUCT;}
    public static String getDatabaseSelectProductTotal() {return DATABASE_SELECT_PRODUCT_TOTAL;}
    public static String getDatabaseSelectList() {return DATABASE_SELECT_LIST;}
    public static String getDatabaseSelectListCheck() {return DATABASE_SELECT_LIST_CHECK;}
    public static String getDatabaseSelectListIsCheck() {return DATABASE_SELECT_LIST_ISCHECK;}
    public static String getDatabaseSelectIdproduct() {return DATABASE_SELECT_IDPRODUCT;}
    public static String getDatabaseSelectIdproduct01() {return DATABASE_SELECT_IDPRODUCT01;}
    public static String getDatabaseSelectIdproduct02() {return DATABASE_SELECT_IDPRODUCT02;}

    public static String getDatabaseCountProductList(){return DATABASE_COUNT_PRODUCT_LIST;}
    public static String getDatabaseCountProductCheck01(){return DATABASE_COUNT_PRODUCT_CHECK01;}
    public static String getDatabaseCountProductCheck02(){return DATABASE_COUNT_PRODUCT_CHECK02;}

    public static String getDatabaseTlDrop() {return DATABASE_TL_DROP;}
    public static String getDatabaseTpDrop() {return DATABASE_TP_DROP;}

    public static String getWhereIdProduct() {return WHERE_IDPRODUCT;}


}
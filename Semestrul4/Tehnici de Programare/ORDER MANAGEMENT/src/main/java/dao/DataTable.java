package dao;

public class DataTable {
    Object[] antet ;
    Object[][] table;
    public DataTable( Object[] antet,Object[][] table){
        this.antet=antet;
        this.table=table;
    }

    public Object[] getAntet() {
        return antet;
    }

    public Object[][] getTable() {
        return table;
    }
}

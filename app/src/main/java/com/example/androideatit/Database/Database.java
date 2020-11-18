package com.example.androideatit.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.androideatit.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {

    private static final String Db_Name="EatIt.db";
    private static final int DB_Ver=1;

    public Database(Context context)
    {
        super(context, Db_Name, null, DB_Ver);
    }

    public List<Order> getCarts(){
        SQLiteDatabase db=getReadableDatabase();
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();

        String[] sqlSelect={"ProductName","ProductID","Quantity","Price","Discount"};
        String sqlTable="OrderDetail";

        qb.setTables(sqlTable);

        Cursor c=qb.query(db,sqlSelect,null,null,null,null,null);

        final List<Order> result=new ArrayList<>();
        if (c.moveToFirst()){
            do {
                result.add(new Order(c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Discount")))
                );
            }while (c.moveToNext());

        }
        return result;
    }

    public void addToCarts(Order order){
        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("INSERT INTO OrderDetail(ProductId,ProductName,Quantity,Price,Discount) VALUES('%s','%s','%s','%s','%s');",
                order.getProductId(),order.getProductName(),order.getQuantity(),order.getPrice(),order.getDiscount());
        db.execSQL(query);
    }

    public void cleanCarts(){
        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }
}

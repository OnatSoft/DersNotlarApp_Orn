package per.example.dersnotlar_apporn;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBConnection extends SQLiteOpenHelper {

    public DBConnection(@Nullable Context context) {
        super(context, "dersnotlar.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE \"notlar\" (\n" +
                "\t\"Not_id\"\tINTEGER,\n" +
                "\t\"Ders_adi\"\tTEXT,\n" +
                "\t\"Not1\"\tINTEGER,\n" +
                "\t\"Not2\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"Not_id\" AUTOINCREMENT)\n" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS notlar");
        onCreate(db);
    }
}

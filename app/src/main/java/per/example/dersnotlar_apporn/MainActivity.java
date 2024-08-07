package per.example.dersnotlar_apporn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rv;
    FloatingActionButton fab;
    ArrayList<Notlar> notlarArrayList = new ArrayList<>();
    DBConnection dbConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        rv = findViewById(R.id.rv);
        fab = findViewById(R.id.fab);
        setSupportActionBar(toolbar);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        dbConnection = new DBConnection(this);

        notlarArrayList = new NotlarDao().tumNotlar(dbConnection);

        //* Not ortalaması hesaplama.
        double toplam = 0.0;
        for (Notlar n : notlarArrayList) {
            toplam = toplam + (n.getNot1() + n.getNot2()) / 2;
        }
        toolbar.setSubtitle("Ortalama= " + (toplam / notlarArrayList.size()));
        toolbar.setSubtitleTextColor(Color.WHITE);

        NotlarCardAdapter adapter = new NotlarCardAdapter(this, notlarArrayList);
        rv.setAdapter(adapter);

        fab.setOnClickListener(view -> {

            startActivity(new Intent(MainActivity.this, NotKayitActivity.class));
        });
    }


    @Override
    public void onBackPressed() {
        //* Bu back pressed methoduyla Main Activity'den geri tuşuna tıklandığında her türlü uygulamadan çıkmasını sağlıyor.
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
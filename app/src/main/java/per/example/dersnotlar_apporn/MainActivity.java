package per.example.dersnotlar_apporn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
        dbConnection = new DBConnection(this);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        notlarArrayList = new NotlarDao().tumNotlar(dbConnection);
        NotlarCardAdapter adapter = new NotlarCardAdapter(this, notlarArrayList);
        rv.setAdapter(adapter);

        //* Not ortalaması hesaplama.
        double toplam = 0.0;
        for (Notlar n : notlarArrayList) {
            toplam = toplam + (n.getNot1() + n.getNot2()) / 2;
        }
        toolbar.setSubtitle("Ortalama= " + (toplam / notlarArrayList.size()));
        toolbar.setSubtitleTextColor(Color.WHITE);

        fab.setOnClickListener(view -> {
            //* Bottom Sheet layout tasarımı activity'de inflate ediyoruz.
            BottomSheetDialog bottomSheet = new BottomSheetDialog(this);
            bottomSheet.requestWindowFeature(Window.FEATURE_NO_TITLE);
            View v = LayoutInflater.from(this).inflate(R.layout.bottom_sheet, null);
            bottomSheet.setContentView(v);

            //* Bottom Sheet sayfasına ekstra görünüm ayarları ekliyoruz.
            bottomSheet.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            bottomSheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            bottomSheet.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            bottomSheet.getWindow().setGravity(Gravity.BOTTOM);

            //* Bottom Sheet tasarımında bulunan kullanılacak widget'lara view nesnesiyle erişiyoruz.
            TextInputLayout txtLayout = v.findViewById(R.id.txtLayoutDersAd);
            TextInputLayout txtLayout2 = v.findViewById(R.id.txtLayoutNot1);
            TextInputLayout txtLayout3 = v.findViewById(R.id.txtLayoutNot2);
            TextInputEditText editTxtDersAd = v.findViewById(R.id.editTxtDersAd);
            TextInputEditText editTxtNot1 = v.findViewById(R.id.editTxtNot1);
            TextInputEditText editTxtNot2 = v.findViewById(R.id.editTxtNot2);
            Button btnNotKayit = v.findViewById(R.id.btnNotKaydet);

            btnNotKayit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String dersAdi = editTxtDersAd.getText().toString().trim();
                    String not1 = editTxtNot1.getText().toString().trim();
                    String not2 = editTxtNot2.getText().toString().trim();

                    if (TextUtils.isEmpty(dersAdi)) {

                        txtLayout.setError("Bu alan boş bırakılamaz!");
                    }
                    if (TextUtils.isEmpty(not1)) {

                        txtLayout2.setError("Bu alan boş bırakılamaz!");
                    }
                    if (TextUtils.isEmpty(not2)) {

                        txtLayout3.setError("Bu alan boş bırakılamaz!");
                    } else {
                        new NotlarDao().notEkle(dbConnection, dersAdi, Integer.parseInt(not1), Integer.parseInt(not2));
                        Toast.makeText(getApplicationContext(), dersAdi + " Dersi başarıyla kaydedildi.", Toast.LENGTH_LONG).show();
                        bottomSheet.dismiss();
                    }
                }
            });//- button kapanış
            bottomSheet.show();

        });//- FAB kapanış

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
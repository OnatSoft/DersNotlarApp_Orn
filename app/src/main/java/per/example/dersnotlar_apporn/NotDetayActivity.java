package per.example.dersnotlar_apporn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class NotDetayActivity extends AppCompatActivity {
    TextInputEditText editTxtDersAd2, editTxtGuncelleNot1, editTxtGuncelleNot2;
    TextInputLayout inputLayoutDersAdi, inputLayoutNot1, inputLayoutNot2;
    Toolbar detayToolbar;
    DBConnection dbConnection;
    Notlar not;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_detay);
        editTxtDersAd2 = findViewById(R.id.edittxtDersAdi);
        editTxtGuncelleNot1 = findViewById(R.id.edittxtNot1);
        editTxtGuncelleNot2 = findViewById(R.id.edittxtNot2);
        detayToolbar = findViewById(R.id.DetayToolbar);
        inputLayoutDersAdi = findViewById(R.id.inputLayoutDersAdi);
        inputLayoutNot1 = findViewById(R.id.inputLayoutNot1);
        inputLayoutNot2 = findViewById(R.id.inputLayoutNot2);


        setSupportActionBar(detayToolbar);
        detayToolbar.setTitleTextColor(Color.WHITE);

        dbConnection = new DBConnection(this);

        //* Main Activity'den sayfa geçişiyle gönderilen verileri "nesne" key ismiyle karşılama.
        not = (Notlar) getIntent().getSerializableExtra("nesne");
        editTxtDersAd2.setText(not.getDersAdi());
        editTxtGuncelleNot1.setText(String.valueOf(not.getNot1()));
        editTxtGuncelleNot2.setText(String.valueOf(not.getNot2()));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_Delete) {
            Snackbar.make(detayToolbar, "Mevcut olan ders silinsin mi?", Snackbar.LENGTH_LONG)
                    .setAction("Evet", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            new NotlarDao().notSil(dbConnection, not.getNotID());
                            startActivity(new Intent(NotDetayActivity.this, MainActivity.class));
                            finish();
                        }
                    }).show();
        } else if (item.getItemId() == R.id.action_Edit) {

            String dersAdi = editTxtDersAd2.getText().toString().trim();
            String not1 = editTxtGuncelleNot1.getText().toString().trim();
            String not2 = editTxtGuncelleNot2.getText().toString().trim();

            if (TextUtils.isEmpty(dersAdi)) {
                inputLayoutDersAdi.setError("Bu alan boş bırakılamaz!");
                return false;
            } else if (TextUtils.isEmpty(not1)) {
                inputLayoutNot1.setError("Bu alan boş bırakılamaz!");
                return false;
            } else if (TextUtils.isEmpty(not2)) {
                inputLayoutNot2.setError("Bu alan boş bırakılamaz!");
                return false;
            } else {
                new NotlarDao().notGuncelle(dbConnection, not.getNotID(), dersAdi, Integer.parseInt(not1), Integer.parseInt(not2));
                startActivity(new Intent(NotDetayActivity.this, MainActivity.class));
                finish();
            }
        }
        return true;
    }
}
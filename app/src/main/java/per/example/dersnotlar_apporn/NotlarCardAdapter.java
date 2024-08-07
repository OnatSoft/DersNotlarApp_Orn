package per.example.dersnotlar_apporn;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotlarCardAdapter extends RecyclerView.Adapter<NotlarCardAdapter.cardViewNesneTutucu> {
    private Context ctx;
    private List<Notlar> notlarList;

    //* Recycler View'de notlar listesini düzenli tutmak için Adapter oluşturuluyor.
    public NotlarCardAdapter(Context ctx, List<Notlar> notlarList) {
        this.ctx = ctx;
        this.notlarList = notlarList;
    }

    public class cardViewNesneTutucu extends RecyclerView.ViewHolder {
        private CardView cardDers;
        private TextView txtDersAd, txtNot1, txtNot2;

        public cardViewNesneTutucu(@NonNull View itemView) {
            super(itemView);
            cardDers = itemView.findViewById(R.id.cardDers);
            txtDersAd = itemView.findViewById(R.id.txtDersAd);
            txtNot1 = itemView.findViewById(R.id.txtNot1);
            txtNot2 = itemView.findViewById(R.id.txtNot2);
        }
    }

    @NonNull
    @Override
    public cardViewNesneTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim, parent, false);
        return new cardViewNesneTutucu(v);
    }

    @Override
    public void onBindViewHolder(@NonNull cardViewNesneTutucu holder, int position) {

        final Notlar notlar = notlarList.get(position);

        holder.txtDersAd.setText(notlar.getDersAdi());
        holder.txtNot1.setText(String.valueOf(notlar.getNot1()));
        holder.txtNot2.setText(String.valueOf(notlar.getNot2()));

        holder.cardDers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ctx, NotDetayActivity.class);
                intent.putExtra("nesne", notlar);
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notlarList.size();
    }
}

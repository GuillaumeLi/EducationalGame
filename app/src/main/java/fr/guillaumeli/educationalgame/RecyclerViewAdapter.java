package fr.guillaumeli.educationalgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private ArrayList wordIDList = new ArrayList();
    private ArrayList wordList = new ArrayList();
    private ArrayList rightLetterList = new ArrayList();
    private ArrayList falseLetterList = new ArrayList();
    private Activity activity;

    public RecyclerViewAdapter (Context context, DataBaseHelper dataBaseHelper, Activity activity) {
        this.context = context;
        for(Data data : dataBaseHelper.getData()) {
            wordIDList.add(data.getId());
            wordList.add(data.getWord());
            rightLetterList.add(data.getRightLetter());
            falseLetterList.add(data.getFalseLetter());
        }
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.card_txt_id.setText(String.valueOf(wordIDList.get(position)));
        holder.card_txt_word.setText(String.valueOf(wordList.get(position)));
        holder.card_txt_rightLetter.setText("Right : " + String.valueOf(rightLetterList.get(position)));
        holder.card_txt_falseLetter.setText("False : "+String.valueOf(falseLetterList.get(position)));
        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateDialog.class);
                intent.putExtra("ID", String.valueOf(wordIDList.get(position)));
                intent.putExtra("Word", String.valueOf(wordList.get(position)));
                intent.putExtra("Right letter", String.valueOf(rightLetterList.get(position)));
                intent.putExtra("False letter", String.valueOf(falseLetterList.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wordIDList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView card_txt_id, card_txt_word, card_txt_rightLetter, card_txt_falseLetter;
        ImageButton updateBtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card_txt_id = itemView.findViewById(R.id.card_txt_id);
            card_txt_word = itemView.findViewById(R.id.card_txt_word);
            card_txt_rightLetter = itemView.findViewById(R.id.card_txt_rightLetter);
            card_txt_falseLetter = itemView.findViewById(R.id.card_txt_falseLetter);
            updateBtn = itemView.findViewById(R.id.imgBtn_update);
        }
    }
}

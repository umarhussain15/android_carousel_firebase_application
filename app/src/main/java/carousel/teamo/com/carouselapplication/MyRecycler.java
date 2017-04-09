package carousel.teamo.com.carouselapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Umar on 08-Apr-17.
 */

public class MyRecycler extends RecyclerView.Adapter<MyRecycler.ViewHolder> {
    private final List<RecyclerHelperPOJO> mValues;
    private final Context c;

    public MyRecycler(List<RecyclerHelperPOJO> mValues, Context c) {
        this.mValues = mValues;
        this.c = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);

        holder.mNameView.setText(mValues.get(position).getScorePOJO().getName());

        holder.mScoreView.setText(mValues.get(position).getScorePOJO().getScore()+" ");
        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = FirebaseAuth.getInstance().getCurrentUser().getUid();
                RecyclerHelperPOJO pojo = mValues.get(position);
                if (pojo.getScorePOJO().getScore() == 9) {
                    Toast.makeText(c,"You won game "+pojo.getScorePOJO().getName()+" !",Toast.LENGTH_LONG).show();
                    FirebaseDatabase.getInstance().getReference().child(s).child(mValues.get(position).getId()).child("score").setValue(0);
                } else {
                    FirebaseDatabase.getInstance().getReference().child(s).child(mValues.get(position).getId()).child("score").setValue(mValues.get(position).getScorePOJO().getScore() + 1);
                }
            }
        });
        holder.subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = FirebaseAuth.getInstance().getCurrentUser().getUid();
                RecyclerHelperPOJO pojo = mValues.get(position);
                if (pojo.getScorePOJO().getScore() !=0) {

                    FirebaseDatabase.getInstance().getReference().child(s).child(mValues.get(position).getId()).child("score").setValue(mValues.get(position).getScorePOJO().getScore() - 1);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView, mScoreView;
        public final ImageButton addButton, subtractButton;
        public RecyclerHelperPOJO mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.gameName);
            mScoreView = (TextView) view.findViewById(R.id.score);
            addButton = (ImageButton) view.findViewById(R.id.addButton);
            subtractButton = (ImageButton) view.findViewById(R.id.minusButton);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}

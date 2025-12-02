package com.example.dialectgame.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dialectgame.R;
import com.example.dialectgame.model.CollectedCard;
import java.util.List;

public class CardAlbumAdapter extends RecyclerView.Adapter<CardAlbumAdapter.CardViewHolder> {
    private List<CollectedCard> cardList;

    public CardAlbumAdapter(List<CollectedCard> cardList) {
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_album, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CollectedCard card = cardList.get(position);

        // 设置卡片图片
        int cardResId = holder.itemView.getContext().getResources().getIdentifier(
                card.getCardImageUrl().replace("drawable/", ""),
                "drawable",
                holder.itemView.getContext().getPackageName()
        );
        if (cardResId != 0) {
            holder.ivCard.setImageResource(cardResId);
        } else {
            holder.ivCard.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        // 设置卡片信息
        holder.tvRegion.setText(card.getRegion());
        holder.tvDialect.setText(card.getDialectType());
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    // 卡片ViewHolder
    static class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCard;
        TextView tvRegion, tvDialect;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCard = itemView.findViewById(R.id.iv_card_item);
            tvRegion = itemView.findViewById(R.id.tv_card_region);
            tvDialect = itemView.findViewById(R.id.tv_card_dialect);
        }
    }
}
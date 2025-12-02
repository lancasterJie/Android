package com.example.dialectgame.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.dialectgame.model.CollectedCard;
import java.util.List;

@Dao // 必须添加@Dao注解，标识这是Room的数据访问接口
public interface CardDao {

    // 查询所有收集的卡片（对应CardAlbumActivity中的loadCollectedCards()调用）
    @Query("SELECT * FROM collected_cards ORDER BY collectTime DESC")
    List<CollectedCard> getAllCards();

    // 检查卡片是否已收集（对应RewardActivity中的collectCard()调用）
    @Query("SELECT EXISTS(SELECT * FROM collected_cards WHERE cardId = :cardId)")
    boolean isCardCollected(String cardId);

    // 插入新卡片（对应RewardActivity中的collectCard()调用）
    @Insert
    void insertCard(CollectedCard card);
}
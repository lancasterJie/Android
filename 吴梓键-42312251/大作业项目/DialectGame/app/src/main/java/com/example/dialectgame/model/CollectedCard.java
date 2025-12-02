package com.example.dialectgame.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull; // 导入NonNull注解

// 收集的卡片实体类（Room数据库表）
@Entity(tableName = "collected_cards")
public class CollectedCard {
    @PrimaryKey
    @NonNull // 添加NonNull注解，确保主键非空
    private String cardId; // 与DialectPuzzle的id一致
    private String region; // 地区
    private String dialectType; // 方言类型
    private String cardImageUrl; // 卡片图片
    private String culturalKnowledge; // 文化知识
    private long collectTime; // 收集时间（时间戳）

    // getter/setter
    public String getCardId() { return cardId; }
    public void setCardId(String cardId) { this.cardId = cardId; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getDialectType() { return dialectType; }
    public void setDialectType(String dialectType) { this.dialectType = dialectType; }
    public String getCardImageUrl() { return cardImageUrl; }
    public void setCardImageUrl(String cardImageUrl) { this.cardImageUrl = cardImageUrl; }
    public String getCulturalKnowledge() { return culturalKnowledge; }
    public void setCulturalKnowledge(String culturalKnowledge) { this.culturalKnowledge = culturalKnowledge; }
    public long getCollectTime() { return collectTime; }
    public void setCollectTime(long collectTime) { this.collectTime = collectTime; }
}
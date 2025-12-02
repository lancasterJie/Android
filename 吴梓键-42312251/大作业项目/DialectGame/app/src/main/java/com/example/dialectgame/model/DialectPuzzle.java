package com.example.dialectgame.model;

import java.io.Serializable;
import java.util.List;

public class DialectPuzzle implements Serializable {
    private String id;
    private String region;  //地区
    private String dialectType; //谜题类型
    private String dialectText; //谜题文本
    private List<String> standardTranslations; // 答案集
    private List<String> hints;
    private String cardImageUrl;
    private String culturalKnowledge;
    private String xunfeiAccent;
    private int level; // 新增关卡号

    // getter/setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getDialectType() { return dialectType; }
    public void setDialectType(String dialectType) { this.dialectType = dialectType; }
    public String getDialectText() { return dialectText; }
    public void setDialectText(String dialectText) { this.dialectText = dialectText; }
    public List<String> getStandardTranslations() { return standardTranslations; }
    public void setStandardTranslations(List<String> standardTranslations) { this.standardTranslations = standardTranslations; }
    public List<String> getHints() { return hints; }
    public void setHints(List<String> hints) { this.hints = hints; }
    public String getCardImageUrl() { return cardImageUrl; }
    public void setCardImageUrl(String cardImageUrl) { this.cardImageUrl = cardImageUrl; }
    public String getCulturalKnowledge() { return culturalKnowledge; }
    public void setCulturalKnowledge(String culturalKnowledge) { this.culturalKnowledge = culturalKnowledge; }
    public String getXunfeiAccent() { return xunfeiAccent; }
    public void setXunfeiAccent(String xunfeiAccent) { this.xunfeiAccent = xunfeiAccent; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
}
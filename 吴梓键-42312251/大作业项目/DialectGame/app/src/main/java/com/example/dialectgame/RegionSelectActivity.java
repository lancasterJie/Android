package com.example.dialectgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dialectgame.model.DialectPuzzle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegionSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_select);

        // 地区选择按钮点击事件
        findViewById(R.id.btn_sichuan).setOnClickListener(v -> goToLevelSelect("四川"));
        findViewById(R.id.btn_guangdong).setOnClickListener(v -> goToLevelSelect("广东"));
        findViewById(R.id.btn_shanghai).setOnClickListener(v -> goToLevelSelect("上海"));
        findViewById(R.id.btn_zhejiang).setOnClickListener(v -> goToLevelSelect("浙江"));
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    // 跳转到关卡选择界面
    private void goToLevelSelect(String region) {
        Intent intent = new Intent(this, LevelSelectActivity.class);
        intent.putExtra("REGION", region);
        intent.putExtra("PUZZLES", new ArrayList<>(getPuzzlesByRegion(region)));
        startActivity(intent);
        finish();
    }

    // 获取对应地区的所有谜题
    private List<DialectPuzzle> getPuzzlesByRegion(String region) {
        List<DialectPuzzle> puzzles = new ArrayList<>();
        switch (region) {
            case "四川":
                puzzles.add(createSichuanPuzzle(1));
                puzzles.add(createSichuanPuzzle(2));
                puzzles.add(createSichuanPuzzle(3));
                break;
            case "广东":
                puzzles.add(createGuangdongPuzzle(1));
                puzzles.add(createGuangdongPuzzle(2));
                puzzles.add(createGuangdongPuzzle(3));
                break;
            case "上海":
                puzzles.add(createShanghaiPuzzle(1));
                puzzles.add(createShanghaiPuzzle(2));
                puzzles.add(createShanghaiPuzzle(3));
                break;
            case "浙江":
                puzzles.add(createZhejiangPuzzle(1));
                puzzles.add(createZhejiangPuzzle(2));
                puzzles.add(createZhejiangPuzzle(3));
                break;
        }
        return puzzles;
    }

    // 四川话谜题（多关卡）
    private DialectPuzzle createSichuanPuzzle(int level) {
        DialectPuzzle puzzle = new DialectPuzzle();
        puzzle.setLevel(level);
        puzzle.setRegion("四川");
        puzzle.setDialectType("四川话");
        puzzle.setXunfeiAccent("sichuan");

        switch (level) {
            case 1:
                puzzle.setId("sichuan_001");
                puzzle.setDialectText("巴适得板");
                puzzle.setStandardTranslations(Arrays.asList("非常舒服", "很棒", "让人满意", "安逸"));
                puzzle.setHints(Arrays.asList("形容感受很好的状态", "常用来夸赞食物、环境或心情", "近义词是'安逸'、'舒服'"));
                puzzle.setCardImageUrl("drawable/sichuan_card1");
                puzzle.setCulturalKnowledge("四川话中的'巴适'是核心方言词汇，源于盆地气候带来的安逸生活态度。");
                break;
            case 2:
                puzzle.setId("sichuan_002");
                puzzle.setDialectText("摆龙门阵");
                puzzle.setStandardTranslations(Arrays.asList("聊天", "闲谈", "讲故事", "吹牛"));
                puzzle.setHints(Arrays.asList("一种社交活动", "与语言有关", "常发生在茶馆等地"));
                puzzle.setCardImageUrl("drawable/sichuan_card2");
                puzzle.setCulturalKnowledge("'摆龙门阵'是四川人特有的社交方式，体现了川人乐观开朗的性格。");
                break;
            case 3:
                puzzle.setId("sichuan_003");
                puzzle.setDialectText("莫来头");
                puzzle.setStandardTranslations(Arrays.asList("没关系", "不要紧", "没问题", "不碍事"));
                puzzle.setHints(Arrays.asList("表示宽容的用语", "常用于回应道歉", "带有安慰的意味"));
                puzzle.setCardImageUrl("drawable/sichuan_card3");
                puzzle.setCulturalKnowledge("四川方言中表达宽容和理解的常用语，体现了川人豁达的心态。");
                break;
        }
        return puzzle;
    }

    // 粤语谜题（多关卡）
    private DialectPuzzle createGuangdongPuzzle(int level) {
        DialectPuzzle puzzle = new DialectPuzzle();
        puzzle.setLevel(level);
        puzzle.setRegion("广东");
        puzzle.setDialectType("粤语");
        puzzle.setXunfeiAccent("cantonese");

        switch (level) {
            case 1:
                puzzle.setId("guangdong_001");
                puzzle.setDialectText("食咗饭未");
                puzzle.setStandardTranslations(Arrays.asList("吃了饭没有", "吃饭了吗", "吃过饭了吗"));
                puzzle.setHints(Arrays.asList("粤语中最常用的问候语之一", "核心词是'食'（吃）和'饭'", "相当于普通话的'吃了吗'"));
                puzzle.setCardImageUrl("drawable/guangdong_card1");
                puzzle.setCulturalKnowledge("粤语'食咗饭未'体现了广东人'以食为天'的文化。");
                break;
            case 2:
                puzzle.setId("guangdong_002");
                puzzle.setDialectText("后生仔");
                puzzle.setStandardTranslations(Arrays.asList("年轻人", "小伙子", "年轻小伙子"));
                puzzle.setHints(Arrays.asList("形容人的词语", "与年龄有关", "指年轻的男性"));
                puzzle.setCardImageUrl("drawable/guangdong_card2");
                puzzle.setCulturalKnowledge("粤语中对年轻人的称呼，体现了广东话的生动形象。");
                break;
            case 3:
                puzzle.setId("guangdong_003");
                puzzle.setDialectText("唔该");
                puzzle.setStandardTranslations(Arrays.asList("谢谢", "麻烦了", "劳驾", "多谢"));
                puzzle.setHints(Arrays.asList("礼貌用语", "使用频率很高", "有多重含义"));
                puzzle.setCardImageUrl("drawable/guangdong_card3");
                puzzle.setCulturalKnowledge("'唔该'是粤语中最常用的礼貌用语之一，用途广泛。");
                break;
        }
        return puzzle;
    }

    // 上海话谜题（多关卡）
    private DialectPuzzle createShanghaiPuzzle(int level) {
        DialectPuzzle puzzle = new DialectPuzzle();
        puzzle.setLevel(level);
        puzzle.setRegion("上海");
        puzzle.setDialectType("上海话");
        puzzle.setXunfeiAccent("shanghai");

        switch (level) {
            case 1:
                puzzle.setId("shanghai_001");
                puzzle.setDialectText("阿拉欢喜侬");
                puzzle.setStandardTranslations(Arrays.asList("我喜欢你", "我们喜欢你", "俺喜欢你"));
                puzzle.setHints(Arrays.asList("表达情感的常用语", "阿拉=我/我们", "欢喜=喜欢"));
                puzzle.setCardImageUrl("drawable/shanghai_card1");
                puzzle.setCulturalKnowledge("上海话中的'阿拉'源于宁波话，后成为上海方言的标志性代词。");
                break;
            case 2:
                puzzle.setId("shanghai_002");
                puzzle.setDialectText("侬好");
                puzzle.setStandardTranslations(Arrays.asList("你好", "您好", "你好呀"));
                puzzle.setHints(Arrays.asList("问候语", "日常交际常用", "侬=你"));
                puzzle.setCardImageUrl("drawable/shanghai_card2");
                puzzle.setCulturalKnowledge("上海话中最基本的问候语，体现了上海话的礼貌特征。");
                break;
            case 3:
                puzzle.setId("shanghai_003");
                puzzle.setDialectText("交关");
                puzzle.setStandardTranslations(Arrays.asList("很多", "非常", "相当", "十分"));
                puzzle.setHints(Arrays.asList("形容词", "表示数量或程度", "使用范围广泛"));
                puzzle.setCardImageUrl("drawable/shanghai_card3");
                puzzle.setCulturalKnowledge("上海话中表示程度的常用词，体现了吴语的特色。");
                break;
        }
        return puzzle;
    }

    // 浙江话谜题（多关卡）
    private DialectPuzzle createZhejiangPuzzle(int level) {
        DialectPuzzle puzzle = new DialectPuzzle();
        puzzle.setLevel(level);
        puzzle.setRegion("浙江");
        puzzle.setDialectType("吴语（杭州话）");
        puzzle.setXunfeiAccent("shanghai");

        switch (level) {
            case 1:
                puzzle.setId("zhejiang_001");
                puzzle.setDialectText("你个老小，介个弄弄啦");
                puzzle.setStandardTranslations(Arrays.asList("你这个小孩子，怎么搞的呀", "你这孩子，怎么弄的呀"));
                puzzle.setHints(Arrays.asList("常用的日常用语", "老小=小孩子", "介个=怎么"));
                puzzle.setCardImageUrl("drawable/zhejiang_card1");
                puzzle.setCulturalKnowledge("杭州话属于吴语太湖片，保留了较多古汉语特征。");
                break;
            case 2:
                puzzle.setId("zhejiang_002");
                puzzle.setDialectText("饭吃过冇");
                puzzle.setStandardTranslations(Arrays.asList("吃过饭了吗", "吃饭了没有", "饭吃了吗"));
                puzzle.setHints(Arrays.asList("询问用语", "与饮食有关", "冇=没有"));
                puzzle.setCardImageUrl("drawable/zhejiang_card2");
                puzzle.setCulturalKnowledge("杭州话中关于饮食的常用询问语，体现了当地的饮食文化。");
                break;
            case 3:
                puzzle.setId("zhejiang_003");
                puzzle.setDialectText("真当好吃");
                puzzle.setStandardTranslations(Arrays.asList("真的好吃", "确实好吃", "非常好吃"));
                puzzle.setHints(Arrays.asList("形容词", "用于评价食物", "真当=真的"));
                puzzle.setCardImageUrl("drawable/zhejiang_card3");
                puzzle.setCulturalKnowledge("杭州话中表达赞美食物的常用语，反映了浙江丰富的饮食文化。");
                break;
        }
        return puzzle;
    }
}
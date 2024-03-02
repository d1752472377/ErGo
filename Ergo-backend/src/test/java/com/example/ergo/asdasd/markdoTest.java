package com.example.ergo.asdasd;

import com.example.ergo.util.MarkdownToHtmlUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author autor
 * @date 2024/3/1
 * 描述：
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class markdoTest {

    @Test
    public void shishi(){
        String markdown = "| 组装机主板| 品牌电脑 |||\n |-|-|-|-|\n | 主板品牌| 启动按键 | 电脑品牌| 启动按键|\n | 华硕主板| F8| 联想电脑| F12|\n | 技嘉主板| F12| 宏基电脑| F12|\n | 微星主板| F11| 华硕电脑| ESC或F8|\n | 映泰主板| F9| 惠普电脑 | F9或F12|\n | 梅捷主板 | ESC或F12 | 联想Thinkpad| F12|\n | 七彩虹主板| ESC或F11 | 戴尔电脑| F12或ESC|\n | 华擎主板| F11| 神舟电脑| F12|\n | 斯巴达克主板| ESC| 东芝电脑| F12|\n | 昂达主板| F11| 三星电脑| F12或F2|\n | 双敏主板| ESC| IBM电脑| F12 |\n | 翔升主板| F10| 富士通电脑| F12|\n | 精英主板| ESC或F11| 海尔电脑| F12|\n | 冠盟主板| F11或F12| 方正电脑 | F12|\n | 富士康主板| ESC或F12| 清华同方电脑 | F12|\n | 顶星主板| F11或F12| 微星电脑| F11 |\n | 铭瑄主板| ESC或F11| 明基电脑 | F9或F8|\n | 盈通主板| F8| 技嘉电脑 | F12|\n | 捷波主板| ESC| Gateway电脑   | F12|\n | Intel主板 | F12| eMachines电脑 | F12 |\n | 杰微主板| ESC或F8| 索尼电脑 | ESC|\n | 致铭主板| F12| 苹果电脑 | 长按“option”键 |\n | 磐英主板| ESC|||\n | 磐正主板| ESC|||\n | 冠铭主板| F9||  |\n | 注意：其它机型请尝试或参考以上品牌常用启动热键 ||||\n";
        String html = MarkdownToHtmlUtils.MarkdownConverter.markdownToHtml(markdown);
        System.out.println(html);
    }

}

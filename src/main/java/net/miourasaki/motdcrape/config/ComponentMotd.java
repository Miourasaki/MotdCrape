package net.miourasaki.motdcrape.config;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComponentMotd {
    public static List<CrapeComponent> parse(List<String> listString) {
        List<CrapeComponent> components = new ArrayList<>();

        for (int i = 0; i < listString.size(); i++) {
            parseOne(listString.get(i), components::add);
            if (i < listString.size() - 1) {
                components.add(new CrapeComponent("\n", null, false, false, false, false, false));
            }
        }


        return components;
    }

    static Pattern pattern = Pattern.compile("<([\\w+#+:]*)>(.*?)</\\1>");

    private static void patternOne(String line, Consumer<CrapeComponent> consumer,
                                   Color initCurrentColor,
                                   boolean initBold,
                                   boolean initItalic,
                                   boolean initUnderlined,
                                   boolean initStrikethrough,
                                   boolean initObfuscated) {

        Color currentColor = initCurrentColor;
        boolean bold = initBold;
        boolean italic = initItalic;
        boolean underlined = initUnderlined;
        boolean strikethrough = initStrikethrough;
        boolean obfuscated = initObfuscated;


        Matcher matcher = pattern.matcher(line);

        int lastIndex = 0;

        while (matcher.find()) {
            if (matcher.start() > lastIndex) {
                // 将头部无标签值打成对象
                String plainText = line.substring(lastIndex, matcher.start());
                consumer.accept(new CrapeComponent(plainText, currentColor, bold, italic, underlined, strikethrough, obfuscated));
            }

            // 获取标签和内容
            String tag = matcher.group(1);
            String content = matcher.group(2);

            switch (tag.toLowerCase()) {
                case "b":
                case "bold":
                    bold = true;
                    break;
                case "i":
                case "italic":
                    italic = true;
                    break;
                case "u":
                case "underlined":
                    underlined = true;
                    break;
                case "s":
                case "strikethrough":
                    strikethrough = true;
                    break;
                case "o":
                case "obfuscated":
                    obfuscated = true;
                    break;
            }
            String[] colorTagList = tag.toLowerCase().split(":");
            if (colorTagList.length == 2) {
                if (colorTagList[0].equals("color")) {
                    switch (colorTagList[1]) {
                        case "blue":
                            currentColor = Color.BLUE;
                            break;
                        case "green":
                            currentColor = Color.GREEN;
                            break;
                        case "red":
                            currentColor = Color.RED;
                            break;
                        case "yellow":
                            currentColor = Color.YELLOW;
                            break;
                        case "cyan":
                            currentColor = Color.CYAN;
                            break;
                        case "magenta":
                            currentColor = Color.MAGENTA;
                            break;
                        case "black":
                            currentColor = Color.BLACK;
                            break;
                        case "dark_gray":
                            currentColor = Color.DARK_GRAY;
                            break;
                        case "gray":
                            currentColor = Color.GRAY;
                            break;
                        case "light_gray":
                            currentColor = Color.LIGHT_GRAY;
                            break;
                        case "orange":
                            currentColor = Color.ORANGE;
                            break;
                        case "pink":
                            currentColor = Color.PINK;
                            break;
                        case "white":
                            currentColor = Color.WHITE;
                            break;
                    }
                }
                if (colorTagList[0].equals("hex")) {
                    currentColor = Color.decode(colorTagList[1]);
                }
            }



            if (colorTagList[0].equals("gradient") && colorTagList.length >= 3) {

                Color startColor = Color.decode(colorTagList[1]);
                Color endColor = Color.decode(colorTagList[2]);

                List<Color> gradientColors = generateGradient(startColor, endColor, content.length());
                for (int i = 0; i < content.length(); i++) {
                    currentColor = gradientColors.get(i);
                    char c = content.charAt(i);
                    consumer.accept(new CrapeComponent(String.valueOf(c), currentColor, bold, italic, underlined, strikethrough, obfuscated));
                }



            }else {
                // 判断迭代还是完成
                Matcher matcherContext = pattern.matcher(content);
                if (matcherContext.find()) {
                    patternOne(content, consumer, currentColor, bold, italic, underlined, strikethrough, obfuscated);
                } else {
                    consumer.accept(new CrapeComponent(content, currentColor, bold, italic, underlined, strikethrough, obfuscated));
                }
            }

            bold = initBold;
            italic = initItalic;
            underlined = initUnderlined;
            strikethrough = initStrikethrough;
            obfuscated = initObfuscated;

            // 更新标签为的索引
            lastIndex = matcher.end();
        }

        // 将尾部无标签值打成对象
        if (lastIndex < line.length()) {
            String plainText = line.substring(lastIndex);
            consumer.accept(new CrapeComponent(plainText, currentColor, bold, italic, underlined, strikethrough, obfuscated));
        }

    }

    private static void parseOne(String line, Consumer<CrapeComponent> consumer) {
        patternOne(line, consumer, null, false, false, false, false, false);

    }


    public static List<Color> generateGradient(Color color1, Color color2, int steps) {
        List<Color> gradientColors = new ArrayList<>();

        // 计算步长
        float stepSize = 1f / (steps - 1);

        for (int i = 0; i < steps; i++) {
            // 计算当前位置的插值比例
            float ratio = i * stepSize;

            // 使用线性插值计算颜色
            int red = (int) (color1.getRed() * (1 - ratio) + color2.getRed() * ratio);
            int green = (int) (color1.getGreen() * (1 - ratio) + color2.getGreen() * ratio);
            int blue = (int) (color1.getBlue() * (1 - ratio) + color2.getBlue() * ratio);

            // 创建新的颜色对象并添加到列表中
            Color interpolatedColor = new Color(red, green, blue);
            gradientColors.add(interpolatedColor);
        }

        return gradientColors;
    }
}


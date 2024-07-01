package net.miourasaki.crapelib.text.component;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class CrapeParser {
    
    private String context;
    private List<CrapeComponent> components;
    public void setContext(String context) {
        this.context = context;
    }
    public void setContext(List<String> context) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < context.size(); i++) {
            result.append(context.get(i));
            if (i != context.size() - 1) {
                result.append("\n");
            }
        }
        this.context = result.toString();
    }
    public CrapeParser(String context) {
        this.context = context;
    }
    public CrapeParser(List<String> context) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < context.size(); i++) {
            result.append(context.get(i));
            if (i != context.size() - 1) {
                result.append("\n");
            }
        }
        this.context = result.toString();
    }


    static Pattern xmlPattern = Pattern.compile("<([\\w+#+:]*)>(.*?)</\\1>");


    public void parse() {
        List<CrapeComponent> components = new ArrayList<>();

        pattern(context, components::add, null, false,false,false,false,false, null,null);
        this.components = components;
    }
    public BaseComponent getBaseComponent() {
        this.parse();
        BaseComponent baseComponent = new TextComponent();
        for (CrapeComponent component : this.components) {

            BaseComponent componentObject = new TextComponent();
            componentObject.addExtra(component.getContext());
            if (component.getColor() != null) componentObject.setColor(ChatColor.of(component.getColor()));
            if (component.isBold()) componentObject.setBold(true);
            if (component.isItalic()) componentObject.setItalic(true);
            if (component.isObfuscated()) componentObject.setObfuscated(true);
            if (component.isStrikethrough()) componentObject.setStrikethrough(true);
            if (component.isUnderlined()) componentObject.setUnderlined(true);

            baseComponent.addExtra(componentObject);
        }

        return baseComponent;
    }



    private static void pattern(String str, Consumer<CrapeComponent> consumer,
                                    Color initCurrentColor,
                                    boolean initBold,
                                    boolean initItalic,
                                    boolean initUnderlined,
                                    boolean initStrikethrough,
                                    boolean initObfuscated,
                                    List<Color> initGradientColors,
                                    String initGradientTest) {

        Color currentColor = initCurrentColor;
        boolean bold = initBold;
        boolean italic = initItalic;
        boolean underlined = initUnderlined;
        boolean strikethrough = initStrikethrough;
        boolean obfuscated = initObfuscated;
        List<Color> gradientColors = initGradientColors;
        String gradientTest = initGradientTest;


        Matcher matcher = xmlPattern.matcher(str);

        int lastIndex = 0;

        while (matcher.find()) {
            // 将头部无标签值打成对象
            if (matcher.start() > lastIndex) {
                String plainText = str.substring(lastIndex, matcher.start());

                addANewComponent(consumer, currentColor, bold, italic, underlined, strikethrough, obfuscated, gradientColors, plainText);
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

                List<Color> colorList = new ArrayList<>();
                for (String colorTag : colorTagList) {
                    if (isHexColor(colorTag)) {
                        colorList.add(Color.decode(colorTag));
                    }
                }


                StringBuilder result = new StringBuilder();
                removeXmlTag(content, result::append);


                List<Color> currentGradientColors = generateListGradient(colorList, result.length());

                if (gradientTest != null && gradientColors!= null) {
                  for (int i = 0; i < currentGradientColors.size(); i++) {
                      gradientColors.set(lastIndex+i, currentGradientColors.get(i));
                  }
                } else {
                    gradientColors = currentGradientColors;

                }
                gradientTest = String.valueOf(result);
                currentColor = null;
            }

                // 判断迭代还是完成
            Matcher matcherContext = xmlPattern.matcher(content);
            if (matcherContext.find()) {
                pattern(content, consumer, currentColor, bold, italic, underlined, strikethrough, obfuscated, gradientColors, gradientTest);
            } else {
                addANewComponent(consumer, currentColor, bold, italic, underlined, strikethrough, obfuscated, gradientColors, content);
            }

            currentColor = initCurrentColor;
            bold = initBold;
            italic = initItalic;
            underlined = initUnderlined;
            strikethrough = initStrikethrough;
            obfuscated = initObfuscated;

            // 更新标签为的索引
            lastIndex = matcher.end();
        }

        // 将尾部无标签值打成对象
        if (lastIndex < str.length()) {
            String plainText = str.substring(lastIndex);
            addANewComponent(consumer, currentColor, bold, italic, underlined, strikethrough, obfuscated, gradientColors, plainText);
        }

    }

    private static void addANewComponent(Consumer<CrapeComponent> consumer, Color currentColor, boolean bold, boolean italic, boolean underlined, boolean strikethrough, boolean obfuscated, List<Color> gradientColors, String plainText) {
        if (gradientColors == null || gradientColors.isEmpty()) {
            consumer.accept(new CrapeComponent(plainText, currentColor, bold, italic, underlined, strikethrough, obfuscated));
        }else {
            for (int i = 0; i < plainText.length(); i++) {
                Color gradientColorElement = gradientColors.get(0);  // 获取第一个元素
                gradientColors.remove(0);  // 删除第一个元素

                Color nowColor = currentColor;

                if (nowColor == null)  nowColor = gradientColorElement;

                char nowChar = plainText.charAt(i);
                consumer.accept(new CrapeComponent(String.valueOf(nowChar), nowColor, bold, italic, underlined, strikethrough, obfuscated));
            }
        }
    }

    private static boolean isHexColor(String color) {
        // 正则表达式匹配#RRGGBB或#RRGGBBAA
        String hexColorPattern = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{8})$";
        return color.matches(hexColorPattern);
    }
    private static void removeXmlTag(String content,Consumer<String> consumer) {
        Matcher matcherObject = xmlPattern.matcher(content);
        int lastEnd = 0;
        while (matcherObject.find()) {
            if (matcherObject.start() > lastEnd) {
                consumer.accept(content.substring(lastEnd, matcherObject.start()));
            }

            String main = matcherObject.group(2);

            Matcher matcherContext = xmlPattern.matcher(main);
            if (matcherContext.find()) {
                removeXmlTag(main,consumer);
            } else {
                consumer.accept(main);
            }

            lastEnd = matcherObject.end();
        }
        if (lastEnd < content.length()) {
            consumer.accept(content.substring(lastEnd));
        }
    }



    public static List<Color> generateListGradient(List<Color> colors, int totalSteps) {
        if (colors == null || colors.isEmpty() || totalSteps <= 0) {
            throw new IllegalArgumentException("Invalid input parameters.");
        }

        List<Color> gradientColors = new ArrayList<>();

        gradientColors.add(colors.get(0));


        int check = colors.size() - 1;
        int checkSteps = (totalSteps - 2) / check;


        for (int i = 0; i < check; i++) {
            System.out.println(colors.get(i).toString());
            List<Color> objectColorList = generateTwoGradient(colors.get(i), colors.get(i+1), checkSteps);
            System.out.println(objectColorList.toString());
            gradientColors.addAll(objectColorList);
        }



        // 如果剩余步数大于0，则将最后一个颜色加入多次以补齐
        while (gradientColors.size() < totalSteps) {
            gradientColors.add(colors.get(colors.size() - 1));
        }

        return gradientColors;
    }

    public static List<Color> generateTwoGradient(Color color1, Color color2, int steps) {
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


    @Deprecated
    public static List<Color> generateGradient(List<Color> colors, int totalSteps) {
        if (colors == null || colors.isEmpty() || totalSteps <= 0) {
            throw new IllegalArgumentException("Invalid input parameters.");
        }

        List<Color> gradientColors = new ArrayList<>();

        // 如果只有一个颜色，则直接返回包含该颜色的列表
        if (colors.size() == 1) {
            for (int i = 0; i < totalSteps; i++) {
                gradientColors.add(colors.get(0));
            }
            return gradientColors;
        }

        // 计算每个颜色段应有的步数，最后一个颜色段可能会少几步
        int[] stepsInEachSection = new int[colors.size() - 1];
        int remainingSteps = totalSteps;
        for (int i = 0; i < colors.size() - 1; i++) {
            stepsInEachSection[i] = remainingSteps / (colors.size() - i);
            remainingSteps -= stepsInEachSection[i];
        }

        // 第一个颜色直接加入列表
        gradientColors.add(colors.get(0));

        // 遍历颜色列表中的每对相邻颜色
        for (int i = 0; i < colors.size() - 1; i++) {
            Color startColor = colors.get(i);
            Color endColor = colors.get(i + 1);

            // 生成当前颜色段的所有颜色
            for (int j = 0; j < stepsInEachSection[i]; j++) {
                float ratio = (float) j / (stepsInEachSection[i] - 1);

                int red = (int) (startColor.getRed() * (1 - ratio) + endColor.getRed() * ratio);
                int green = (int) (startColor.getGreen() * (1 - ratio) + endColor.getGreen() * ratio);
                int blue = (int) (startColor.getBlue() * (1 - ratio) + endColor.getBlue() * ratio);

                Color interpolatedColor = new Color(red, green, blue);
                gradientColors.add(interpolatedColor);
            }
        }

        // 如果剩余步数大于0，则将最后一个颜色加入多次以补齐
        while (gradientColors.size() < totalSteps) {
            gradientColors.add(colors.get(colors.size() - 1));
        }

        return gradientColors;
    }
}


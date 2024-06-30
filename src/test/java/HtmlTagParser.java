import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlTagParser {

    // 正则表达式匹配HTML标签及其内容
    private static final Pattern htmlPattern = Pattern.compile("<([a-zA-Z]+)([^>]*)>(.*?)</\\1>");

    // 递归解析HTML字符串
    public static List<Component> parse(String input) {
        List<Component> components = new ArrayList<>();
        Matcher matcher = htmlPattern.matcher(input);

        int lastIndex = 0;
        while (matcher.find()) {
            // 添加非标签文本内容
            if (matcher.start() > lastIndex) {
                components.add(new Component(matcher.group(3), false, null));
            }

            // 解析标签及其属性
            String tag = matcher.group(1);
            String attributes = matcher.group(2);
            String innerContent = matcher.group(3);

            boolean isBold = tag.equals("bold");
            String color = null;
            if (attributes.contains("color:red")) {
                color = "red";
            }

            // 递归解析标签内部内容
            List<Component> innerComponents = parse(innerContent);

            // 添加当前解析的组件
            components.add(new Component(innerContent, isBold, color));

            lastIndex = matcher.end();
        }

        // 添加最后的非标签文本内容
        if (lastIndex < input.length()) {
            components.add(new Component(input.substring(lastIndex), false, null));
        }

        return components;
    }

    // 测试
    public static void main(String[] args) {
        String input = "a<bold>ab<color:red>cde</color:red>f</bold>";

        List<Component> components = parse(input);

        // 打印解析结果
        for (Component component : components) {
            System.out.println(component);
        }
    }

    // 组件类，用于表示解析后的文本片段
    public static class Component {
        private String content;
        private boolean isBold;
        private String color;

        public Component(String content, boolean isBold, String color) {
            this.content = content;
            this.isBold = isBold;
            this.color = color;
        }

        @Override
        public String toString() {
            return "Component{" +
                    "content='" + content + '\'' +
                    ", isBold=" + isBold +
                    ", color='" + color + '\'' +
                    '}';
        }
    }
}

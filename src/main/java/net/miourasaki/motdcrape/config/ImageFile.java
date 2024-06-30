package net.miourasaki.motdcrape.config;

import net.miourasaki.motdcrape.console.Out;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageFile {

    public static BufferedImage get(File fileDir,String configName) {
        File iconFile = new File(fileDir,"icons/" + configName + ".png");
        if (iconFile.isFile()) {
            try {
                // 读取图片文件
                BufferedImage image = ImageIO.read(iconFile);

                if (image != null) {
                    // 获取图片宽度和高度
                    int width = image.getWidth();
                    int height = image.getHeight();

                    // 判断宽高是否为64x64像素
                    if (width == 64 && height == 64) {

                        return image;

                    } else {
                        Out.warning("Image is not 64x64 > " + iconFile.toPath());
                    }
                } else {
                    Out.warning("Can't read " + iconFile.toPath());
                }
            } catch (IOException exception) {
                Out.warning(exception.toString());
            }
        }else {
            Out.warning("\"MotdCrape/icons\" The specified file does not exist under");
        }
        return null;
    }

}

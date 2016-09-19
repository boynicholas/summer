/*
 * Copyright 2016 Cnlyml
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.lyml.summer.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

/**
 * 根据真实姓名生成默认头像
 * @ClassName: FontImageUtils
 * @author: cnlyml
 * @date: 2016年7月27日 下午3:47:41
 */
public class FontImageUtils {
	public static void createImage(String str, File outFile) throws IOException {
		createImage(str, new Font("微软雅黑", Font.BOLD, 50), outFile);
	}
	
	/**
	 * 生成头像图片
	 * @Title: createImage
	 * @Description: TODO
	 * @param str
	 * @param font
	 * @param outFile
	 * @throws IOException
	 * @return: void
	 */
	public static void createImage(String str, Font font, File outFile) throws IOException {
		int[] ranRGB = getRanRGB();
		boolean isQian=isQianRGB(ranRGB);
		
		int width=120;
        int height=120;
        
      //创建图片
        BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
        Graphics2D g=image.createGraphics();
        g.setColor(new Color(ranRGB[0],ranRGB[1],ranRGB[2]));//　一个未知颜色
        g.fillRect(0, 0, width, height);//先用颜色填充背景
        //判断是浅颜色还是深颜色
        if(isQian){
            g.setColor(Color.black);//黑色
        }else{
            g.setColor(Color.white);//白色
        }
        
        //设置画笔字体
        g.setFont(font);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //判断字体的绘制位置,居中显示
        FontMetrics fm=g.getFontMetrics(font);
        int textWidth=fm.stringWidth(str);
        int x=(width-textWidth)/2;
        int ascent=fm.getAscent();
        int descent=fm.getDescent();
        int y=(height-ascent-descent)/2+ascent;
        
        //画出字符串
        g.drawString(str,x,y);
        g.dispose();
        //输出png图片
        ImageIO.write(image, "png", outFile);
	}
	
	/**
	 * 获取随机颜色
	 * @Title: getRanRGB
	 * @return: int[]
	 */
	private static int[] getRanRGB() {
		int[] colors = new int[3];
		
		for(int i = 0; i < colors.length; i++) {
			colors[i] = (int) (Math.random() * 256);
		}
		
		return colors;
	}
	
	/**
	 * 判断是否浅颜色
	 * @Title: isQianRGB
	 * @param colors
	 * @return: boolean
	 */
	private static boolean isQianRGB(int[] colors) {
		int grayLevel = (int) (colors[0] * 0.299 + colors[1] * 0.587 + colors[2] * 0.114);
		
		return grayLevel >= 192 ? true : false;
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(UUID.randomUUID().toString().replace("-", ""));
	}
}

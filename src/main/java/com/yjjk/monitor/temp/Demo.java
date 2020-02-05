package com.yjjk.monitor.temp;

import com.alibaba.fastjson.JSON;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * @program: monitor2.0
 * @description: 图片生成
 * @author: CentreS
 * @create: 2020-01-08 11:14:55
 **/
public class Demo {

    private static final String str = "{\"indexArray\": [\n" +
            "                            {\n" +
            "                                \"indexAnomaly\": null,\n" +
            "                                \"basReportId\": null,\n" +
            "                                \"unitList\": \"[]\",\n" +
            "                                \"sort\": \"22\",\n" +
            "                                \"type\": \"血常规\",\n" +
            "                                \"error_tip\": \"0\",\n" +
            "                                \"sequence\": \"1\",\n" +
            "                                \"score\": \"136\",\n" +
            "                                \"unit\": \"10^9个/L\",\n" +
            "                                \"name\": \"白细胞计数\",\n" +
            "                                \"text\": \"白细胞计数\",\n" +
            "                                \"index_id\": \"2501\",\n" +
            "                                \"value\": \"6.4\",\n" +
            "                                \"originName\": \"白细胞计数\",\n" +
            "                                \"referenceValue\": \"[阴性]\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"indexAnomaly\": null,\n" +
            "                                \"basReportId\": null,\n" +
            "                                \"unitList\": \"[]\",\n" +
            "                                \"sort\": \"31\",\n" +
            "                                \"type\": \"血常规\",\n" +
            "                                \"error_tip\": \"0\",\n" +
            "                                \"sequence\": \"120\",\n" +
            "                                \"score\": \"136\",\n" +
            "                                \"unit\": \"%\",\n" +
            "                                \"name\": \"中性粒细胞百分比\",\n" +
            "                                \"text\": \"中性粒细胞(%)\",\n" +
            "                                \"index_id\": \"2507\",\n" +
            "                                \"value\": \"55.7\",\n" +
            "                                \"originName\": \"中性粒细胞\",\n" +
            "                                \"referenceValue\": \"(50.0~70.0] %\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"indexAnomaly\": null,\n" +
            "                                \"basReportId\": null,\n" +
            "                                \"unitList\": \"[]\",\n" +
            "                                \"sort\": \"38\",\n" +
            "                                \"type\": \"血常规\",\n" +
            "                                \"error_tip\": \"0\",\n" +
            "                                \"sequence\": \"30\",\n" +
            "                                \"score\": \"136\",\n" +
            "                                \"unit\": \"%\",\n" +
            "                                \"name\": \"淋巴细胞百分比\",\n" +
            "                                \"text\": \"淋巴细胞(%)\",\n" +
            "                                \"index_id\": \"2498\",\n" +
            "                                \"value\": \"33.6\",\n" +
            "                                \"originName\": \"淋巴细胞%\",\n" +
            "                                \"referenceValue\": \"(20.0~40.0] %\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"indexAnomaly\": null,\n" +
            "                                \"basReportId\": null,\n" +
            "                                \"unitList\": \"[]\",\n" +
            "                                \"sort\": \"45\",\n" +
            "                                \"type\": \"血常规\",\n" +
            "                                \"error_tip\": \"0\",\n" +
            "                                \"sequence\": \"50\",\n" +
            "                                \"score\": \"136\",\n" +
            "                                \"unit\": \"%\",\n" +
            "                                \"name\": \"单核细胞百分比\",\n" +
            "                                \"text\": \"单核细胞(%)\",\n" +
            "                                \"index_id\": \"2500\",\n" +
            "                                \"value\": \"4.7\",\n" +
            "                                \"originName\": \"单核细胞%\",\n" +
            "                                \"referenceValue\": \"(3.0~8.0] %\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"indexAnomaly\": null,\n" +
            "                                \"basReportId\": null,\n" +
            "                                \"unitList\": \"[]\",\n" +
            "                                \"sort\": \"53\",\n" +
            "                                \"type\": \"血常规\",\n" +
            "                                \"error_tip\": \"0\",\n" +
            "                                \"sequence\": \"160\",\n" +
            "                                \"score\": \"136\",\n" +
            "                                \"unit\": \"%\",\n" +
            "                                \"name\": \"嗜酸性粒细胞百分比\",\n" +
            "                                \"text\": \"嗜酸性粒细胞(%)\",\n" +
            "                                \"index_id\": \"2511\",\n" +
            "                                \"value\": \"5.2\",\n" +
            "                                \"originName\": \"嗜酸性粒细胞%\",\n" +
            "                                \"referenceValue\": \"(0.5~5.0] %\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"indexAnomaly\": null,\n" +
            "                                \"basReportId\": null,\n" +
            "                                \"unitList\": \"[]\",\n" +
            "                                \"sort\": \"60\",\n" +
            "                                \"type\": \"血常规\",\n" +
            "                                \"error_tip\": \"0\",\n" +
            "                                \"sequence\": \"170\",\n" +
            "                                \"score\": \"136\",\n" +
            "                                \"unit\": \"%\",\n" +
            "                                \"name\": \"嗜碱性粒细胞百分比\",\n" +
            "                                \"text\": \"嗜碱性粒细胞(%)\",\n" +
            "                                \"index_id\": \"2512\",\n" +
            "                                \"value\": \"0.8\",\n" +
            "                                \"originName\": \"嗜碱性粒细胞%\",\n" +
            "                                \"referenceValue\": \"(0.0~1.0] %\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"indexAnomaly\": null,\n" +
            "                                \"basReportId\": null,\n" +
            "                                \"unitList\": \"[]\",\n" +
            "                                \"sort\": \"68\",\n" +
            "                                \"type\": \"血常规\",\n" +
            "                                \"error_tip\": \"-1\",\n" +
            "                                \"sequence\": \"110\",\n" +
            "                                \"score\": \"119\",\n" +
            "                                \"unit\": \"10^9个/L\",\n" +
            "                                \"name\": \"中性粒细胞计数\",\n" +
            "                                \"text\": \"中性粒细胞\",\n" +
            "                                \"index_id\": \"2506\",\n" +
            "                                \"value\": \"3.6\",\n" +
            "                                \"originName\": \"中性粒细胞数\",\n" +
            "                                \"referenceValue\": \"(2.0~7.5] 10^9个/L\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"indexAnomaly\": null,\n" +
            "                                \"basReportId\": null,\n" +
            "                                \"unitList\": \"[]\",\n" +
            "                                \"sort\": \"75\",\n" +
            "                                \"type\": \"血常规\",\n" +
            "                                \"error_tip\": \"0\",\n" +
            "                                \"sequence\": \"10\",\n" +
            "                                \"score\": \"116\",\n" +
            "                                \"unit\": \"10^9个/L\",\n" +
            "                                \"name\": \"淋巴细胞计数\",\n" +
            "                                \"text\": \"淋巴细胞\",\n" +
            "                                \"index_id\": \"164\",\n" +
            "                                \"value\": \"2.2\",\n" +
            "                                \"originName\": \"淋巴细胞数\",\n" +
            "                                \"referenceValue\": \"(0.8~4.0] 10^9个/L\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"indexAnomaly\": null,\n" +
            "                                \"basReportId\": null,\n" +
            "                                \"unitList\": \"[]\",\n" +
            "                                \"sort\": \"83\",\n" +
            "                                \"type\": \"血常规\",\n" +
            "                                \"error_tip\": \"0\",\n" +
            "                                \"sequence\": \"6\",\n" +
            "                                \"score\": \"116\",\n" +
            "                                \"unit\": \"10^9/L?\",\n" +
            "                                \"name\": \"单核细胞计数\",\n" +
            "                                \"text\": \"单核细胞\",\n" +
            "                                \"index_id\": \"2499\",\n" +
            "                                \"value\": \"0.30\",\n" +
            "                                \"originName\": \"单核细胞数\",\n" +
            "                                \"referenceValue\": \"(0.12~0.8] 10^9/L?\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"indexAnomaly\": null,\n" +
            "                                \"basReportId\": null,\n" +
            "                                \"unitList\": \"[]\",\n" +
            "                                \"sort\": \"90\",\n" +
            "                                \"type\": \"血常规\",\n" +
            "                                \"error_tip\": \"0\",\n" +
            "                                \"sequence\": \"180\",\n" +
            "                                \"score\": \"121\",\n" +
            "                                \"unit\": \"10^9个/L\",\n" +
            "                                \"name\": \"嗜酸性粒细胞计数\",\n" +
            "                                \"text\": \"嗜酸性粒细胞\",\n" +
            "                                \"index_id\": \"2513\",\n" +
            "                                \"value\": \"0.33\",\n" +
            "                                \"originName\": \"嗜酸性粒细胞数\",\n" +
            "                                \"referenceValue\": \"(0.05~0.3] 10^9个/L\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"indexAnomaly\": null,\n" +
            "                                \"basReportId\": null,\n" +
            "                                \"unitList\": \"[]\",\n" +
            "                                \"sort\": \"94\",\n" +
            "                                \"type\": \"血常规\",\n" +
            "                                \"error_tip\": \"0\",\n" +
            "                                \"sequence\": \"190\",\n" +
            "                                \"score\": \"121\",\n" +
            "                                \"unit\": \"10^9个/L\",\n" +
            "                                \"name\": \"嗜碱性粒细胞计数\",\n" +
            "                                \"text\": \"嗜碱性粒细胞.\",\n" +
            "                                \"index_id\": \"2514\",\n" +
            "                                \"value\": \"0.05\",\n" +
            "                                \"originName\": \"嗜碱性粒细胞数\",\n" +
            "                                \"referenceValue\": \"(0.0~0.1] 10^9个/L\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"indexAnomaly\": null,\n" +
            "                                \"basReportId\": null,\n" +
            "                                \"unitList\": \"[]\",\n" +
            "                                \"sort\": \"98\",\n" +
            "                                \"type\": \"血常规\",\n" +
            "                                \"error_tip\": \"0\",\n" +
            "                                \"sequence\": \"4\",\n" +
            "                                \"score\": \"136\",\n" +
            "                                \"unit\": \"10^12个/L\",\n" +
            "                                \"name\": \"红细胞计数\",\n" +
            "                                \"text\": \"红细胞计数\",\n" +
            "                                \"index_id\": \"2502\",\n" +
            "                                \"value\": \"5.70\",\n" +
            "                                \"originName\": \"红细胞计数\",\n" +
            "                                \"referenceValue\": \"[阴性]\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"indexAnomaly\": null,\n" +
            "                                \"basReportId\": null,\n" +
            "                                \"unitList\": \"[\\\"mg/L\\\",\\\"g/L\\\"]\",\n" +
            "                                \"sort\": \"1026\",\n" +
            "                                \"type\": \"血常规\",\n" +
            "                                \"error_tip\": \"0\",\n" +
            "                                \"sequence\": \"2\",\n" +
            "                                \"score\": \"136\",\n" +
            "                                \"unit\": \"g/L\",\n" +
            "                                \"name\": \"血红蛋白测定\",\n" +
            "                                \"text\": \"血红蛋白\",\n" +
            "                                \"index_id\": \"2504\",\n" +
            "                                \"value\": \"166\",\n" +
            "                                \"originName\": \"血红蛋白\",\n" +
            "                                \"referenceValue\": \"成年男性120~160，成年女性110~150，新生儿170~200，婴儿100~140，儿童120~140\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"indexAnomaly\": null,\n" +
            "                                \"basReportId\": null,\n" +
            "                                \"unitList\": \"[]\",\n" +
            "                                \"sort\": \"1035\",\n" +
            "                                \"type\": \"血常规\",\n" +
            "                                \"error_tip\": \"0\",\n" +
            "                                \"sequence\": \"3\",\n" +
            "                                \"score\": \"136\",\n" +
            "                                \"unit\": \"%\",\n" +
            "                                \"name\": \"红细胞压积\",\n" +
            "                                \"text\": \"红细胞压积\",\n" +
            "                                \"index_id\": \"2503\",\n" +
            "                                \"value\": \"48.8\",\n" +
            "                                \"originName\": \"红细胞压积\",\n" +
            "                                \"referenceValue\": \"(40.0~50.0] %\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"indexAnomaly\": null,\n" +
            "                                \"basReportId\": null,\n" +
            "                                \"unitList\": \"[]\",\n" +
            "                                \"sort\": \"1042\",\n" +
            "                                \"type\": \"血常规\",\n" +
            "                                \"error_tip\": \"0\",\n" +
            "                                \"sequence\": \"230\",\n" +
            "                                \"score\": \"141\",\n" +
            "                                \"unit\": \"fl\",\n" +
            "                                \"name\": \"平均红细胞体积\",\n" +
            "                                \"text\": \"平均红细胞体积\",\n" +
            "                                \"index_id\": \"3284\",\n" +
            "                                \"value\": \"85.6\",\n" +
            "                                \"originName\": \"平均红细胞体积\",\n" +
            "                                \"referenceValue\": \"(82.0~95.0] fl\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"indexAnomaly\": null,\n" +
            "                                \"basReportId\": null,\n" +
            "                                \"unitList\": \"[]\",\n" +
            "                                \"sort\": \"1049\",\n" +
            "                                \"type\": \"血常规\",\n" +
            "                                \"error_tip\": \"0\",\n" +
            "                                \"sequence\": \"150\",\n" +
            "                                \"score\": \"123\",\n" +
            "                                \"unit\": \"pg\",\n" +
            "                                \"name\": \"平均红细胞血红蛋白定量\",\n" +
            "                                \"text\": \"平均血红蛋白含量\",\n" +
            "                                \"index_id\": \"2510\",\n" +
            "                                \"value\": \"29.1\",\n" +
            "                                \"originName\": \"平均血红蛋白定量\",\n" +
            "                                \"referenceValue\": \"(27.0~32.0] pg\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"indexAnomaly\": null,\n" +
            "                                \"basReportId\": null,\n" +
            "                                \"unitList\": \"[\\\"mg/L\\\",\\\"g/L\\\"]\",\n" +
            "                                \"sort\": \"1057\",\n" +
            "                                \"type\": \"血常规\",\n" +
            "                                \"error_tip\": \"0\",\n" +
            "                                \"sequence\": \"100\",\n" +
            "                                \"score\": \"136\",\n" +
            "                                \"unit\": \"g/L\",\n" +
            "                                \"name\": \"平均红细胞血红蛋白浓度\",\n" +
            "                                \"text\": \"平均血红蛋白浓度\",\n" +
            "                                \"index_id\": \"2505\",\n" +
            "                                \"value\": \"340\",\n" +
            "                                \"originName\": \"平均血红蛋白浓度\",\n" +
            "                                \"referenceValue\": \"(320.0~360.0] g/L;(320000.0~360000.0] mg/L\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"indexAnomaly\": null,\n" +
            "                                \"basReportId\": null,\n" +
            "                                \"unitList\": \"[]\",\n" +
            "                                \"sort\": \"1064\",\n" +
            "                                \"type\": \"血常规\",\n" +
            "                                \"error_tip\": \"0\",\n" +
            "                                \"sequence\": \"250\",\n" +
            "                                \"score\": \"136\",\n" +
            "                                \"unit\": \"%\",\n" +
            "                                \"name\": \"红细胞体积分布宽度\",\n" +
            "                                \"text\": \"红细胞分布宽度\",\n" +
            "                                \"index_id\": \"3289\",\n" +
            "                                \"value\": \"11.9\",\n" +
            "                                \"originName\": \"红细胞分布宽度\",\n" +
            "                                \"referenceValue\": \"(10.9~15.7] %\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"indexAnomaly\": null,\n" +
            "                                \"basReportId\": null,\n" +
            "                                \"unitList\": \"[]\",\n" +
            "                                \"sort\": \"1071\",\n" +
            "                                \"type\": \"血常规\",\n" +
            "                                \"error_tip\": \"0\",\n" +
            "                                \"sequence\": \"5\",\n" +
            "                                \"score\": \"136\",\n" +
            "                                \"unit\": \"10^9/L?\",\n" +
            "                                \"name\": \"血小板计数\",\n" +
            "                                \"text\": \"血小板计数\",\n" +
            "                                \"index_id\": \"2508\",\n" +
            "                                \"value\": \"180\",\n" +
            "                                \"originName\": \"血小板计数\",\n" +
            "                                \"referenceValue\": \"(100.0~300.0] 10^9/L?\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"indexAnomaly\": null,\n" +
            "                                \"basReportId\": null,\n" +
            "                                \"unitList\": \"[]\",\n" +
            "                                \"sort\": \"1079\",\n" +
            "                                \"type\": \"血常规\",\n" +
            "                                \"error_tip\": \"0\",\n" +
            "                                \"sequence\": \"200\",\n" +
            "                                \"score\": \"136\",\n" +
            "                                \"unit\": \"%\",\n" +
            "                                \"name\": \"血小板压积\",\n" +
            "                                \"text\": \"血小板压积\",\n" +
            "                                \"index_id\": \"2515\",\n" +
            "                                \"value\": \"0.190\",\n" +
            "                                \"originName\": \"血小板压积\",\n" +
            "                                \"referenceValue\": \"(0.18~0.35] %\"\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"indexAnomaly\": null,\n" +
            "                                \"basReportId\": null,\n" +
            "                                \"unitList\": \"[]\",\n" +
            "                                \"sort\": \"1087\",\n" +
            "                                \"type\": \"血常规\",\n" +
            "                                \"error_tip\": \"0\",\n" +
            "                                \"sequence\": \"260\",\n" +
            "                                \"score\": \"136\",\n" +
            "                                \"unit\": \"fl\",\n" +
            "                                \"name\": \"平均血小板体积\",\n" +
            "                                \"text\": \"平均血小板体积\",\n" +
            "                                \"index_id\": \"3290\",\n" +
            "                                \"value\": \"10.5\",\n" +
            "                                \"originName\": \"平均血小板体积\",\n" +
            "                                \"referenceValue\": \"(7.0~13.0] fl\"\n" +
            "                            }\n" +
            "                        ]}";

    public static void main(String[] args) throws Exception {
        JsonRootBean bean = JSON.parseObject(str, JsonRootBean.class);
        List<IndexArray> indexArray = bean.getIndexArray();
//        IndexDetailArray i = new IndexDetailArray();
//        i.setOriginName("急诊尿酸");
//        i.setValue("659.4");
//        indexDetailArray.clear();
//        indexDetailArray.add(i);
        createImage(indexArray, new Font("微软雅黑", Font.BOLD, 40), new File(
                "D:/temp/a.png"), 2048, indexArray.size() * 67);

    }

    /**
     * 根据str,font的样式以及输出文件目录
     *
     * @param font    字体
     * @param outFile 输出文件位置
     * @param width   宽度
     * @param height  高度
     * @throws Exception
     */
    public static void createImage(List<IndexArray> list, Font font, File outFile,
                                   Integer width, Integer height) throws Exception {
        // 创建图片
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.setClip(0, 0, width, height);
        g.setColor(Color.white);
        // 先用黑色填充整张图片,也就是背景
        g.fillRect(0, 0, width, height);
        // 在换成红色
        g.setColor(Color.black);
        // 设置画笔字体
        g.setFont(font);
        /** 用于获得垂直居中y */
        Rectangle clip = g.getClipBounds();
        FontMetrics fm = g.getFontMetrics(font);
        int ascent = fm.getAscent();
        int descent = fm.getDescent();

        for (int i = 0; i < list.size(); i++) {
            g.drawString(list.get(i).getOriginName() + ("     ") + list.get(i).getValue(), 100, 10 * (i + 1) + ascent * (i + 1) + descent * i);
        }


//        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
//        int count = 0;
//        while (iterator.hasNext()) {
//            Map.Entry<String, String> entry = iterator.next();
//            g.drawString(entry.getKey().toString() + ("  ") + (entry.getValue().toString()), 100, 100 * (count + 1) + ascent * (count + 1) + descent * count);
//            count++;
//        }
        g.dispose();
        // 输出png图片
        ImageIO.write(image, "png", outFile);
    }
}

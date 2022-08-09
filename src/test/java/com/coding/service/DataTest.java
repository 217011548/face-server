package com.coding.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;

public class DataTest {


    @Test
    void demo() {
        String data = "?>3d%LG4I";
        System.out.println(processText(data));
//        System.out.println(Integer.toHexString(180));
//        System.out.println((int) 'L');
        String str = null;
        str = "2352455350CB421C47204C4734494F4B";
        str = "2352455350DA47000000000000004F4B";
        str = "2352455350ED3900003F00005F4B4F4B";
        str = "2352455350ED421C47204C4734494F4B";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i += 2) {
            int a = Integer.valueOf(String.valueOf(str.charAt(i)), 16);
            int b = Integer.valueOf(String.valueOf(str.charAt(i + 1)), 16);
//            int b = str.charAt(i + 1) - '0';
            int itemData = a * 16 + b;
            System.out.println(itemData + "->" + Integer.toHexString(itemData));
            System.out.println((char) str.charAt(i) + "" + (char) str.charAt(i + 1) + "--》" + itemData + ":" + (char) itemData);
            sb.append((char) itemData);
        }
        System.out.println(sb.toString());
        System.out.println(processText("#RESPËB\u001CG LG4IOK"));

        //

        getData();

    }

    public static String getData() {
        //32
        String str = null;
        str = "2352455350CB421C47204C4734494F4B";

        System.out.println(str.length());
        str = str.substring(10,str.length()-4);

        System.out.println(str);
        return str;
    }

    public static String binaryString(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            sb.append(n >>> i & 1);
        }
        return sb.toString();
    }

    public static String processText(String data) {
        if (StringUtils.isBlank(data)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            int item = data.charAt(i);
            if (item < 16) {
                sb.append("0").append(Integer.toHexString(item));
            } else {
                sb.append(Integer.toHexString(item));
            }
        }
        return sb.toString();
    }

}

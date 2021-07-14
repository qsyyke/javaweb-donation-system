package com.yq.web.servlet.alipayservlet.Operation;



import com.yq.alipay.trade.utils.ZxingUtils;

import java.io.File;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/01 18:04
 **/

public class QRTest {
    public static void main (String[] args) {
        String content = "this is a test content";
        File qrCodeImge = ZxingUtils.getQRCodeImge(content, 200, "D:\\Code\\yq\\out\\artifacts\\dmf_war_exploded\\images\\2.png");

    }
}

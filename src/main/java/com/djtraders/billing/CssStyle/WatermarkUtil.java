package com.djtraders.billing.CssStyle;

import com.lowagie.text.pdf.*;
import com.lowagie.text.*;

import java.awt.Color;

public class WatermarkUtil {

    public static void addWatermark(PdfWriter writer, String text) {

        PdfContentByte canvas = writer.getDirectContentUnder();
        Font font = new Font(Font.HELVETICA, 50, Font.BOLD, new Color(200, 200, 200));

        Phrase watermark = new Phrase(text, font);

        ColumnText.showTextAligned(
                canvas,
                Element.ALIGN_CENTER,
                watermark,
                297, 420, 45 // x, y, angle
        );
    }
}
package com.djtraders.billing.CssStyle;

import com.djtraders.billing.UI.ItemUI;
import com.djtraders.billing.model.InvoiceEntity;
import com.djtraders.billing.model.ItemEntity;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;

import java.awt.*;
import java.io.FileOutputStream;

public class InvoicePDF {

    public  void generate(InvoiceEntity invoice) {

        try {
            Document doc = new Document(PageSize.A4, 10, 10, 10, 10);
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("invoice.pdf"));
            doc.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            Font normal = FontFactory.getFont(FontFactory.HELVETICA, 9);
            Font solid = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9);

            /* ================= MAIN TABLE ================= */
            PdfPTable main = new PdfPTable(8);
            main.setWidthPercentage(100);

            /* ================= TITLE ================= */
            PdfPCell title = new PdfPCell(new Phrase("TAX INVOICE", titleFont));
            title.setColspan(8);
            title.setHorizontalAlignment(Element.ALIGN_CENTER);
            title.setPadding(10);
            main.addCell(title);

            /* ================= HEADER (SELLER / BUYER) ================= */
            PdfPCell seller = new PdfPCell(new Phrase(invoice.getSellerAddress(), normal));
            seller.setColspan(4);
            seller.setPadding(8);

            PdfPCell buyer = new PdfPCell(new Phrase(invoice.getBuyerAddress(), normal));
            buyer.setColspan(4);
            buyer.setPadding(8);

            main.addCell(seller);
            main.addCell(buyer);

            /* ================= INFO ROW ================= */
            main.addCell(makeCell("Mob: "));
            main.addCell(makeCell("9161490408 "));
             main.addCell(makeCell(" Inv No:"));
            PdfPCell invoiceCell=new PdfPCell(new Phrase(invoice.getInvoiceNo()));
            invoiceCell.setColspan(2);
            main.addCell(invoiceCell);
            main.addCell(makeCell("Date:"));
            PdfPCell dateCell=new PdfPCell(new Phrase(invoice.getInvoiceDate().toString()));
            dateCell.setColspan(2);
            main.addCell(dateCell);
            /* ====================Black Row create============================*/
            PdfPCell balnkRow = new PdfPCell(new Phrase("" , bold));
            balnkRow.setColspan(8);
            main.addCell(balnkRow);

            /* ================= TABLE HEADER ================= */
            String[] headers = {"S.N", "Particulars", "HSN", "MRP", "Qty", "Rate", "Dis%", "Amount"};

            for (String h : headers) {
                PdfPCell c = new PdfPCell(new Phrase(h, bold));
                c.setBackgroundColor(Color.GRAY);
                c.setHorizontalAlignment(Element.ALIGN_CENTER);
                c.setPadding(5);
                main.addCell(c);
            }
             main.setWidths(new float[]{2f, 6f, 3f, 2f, 1.5f, 2f, 1.5f, 2f});
            /* ================= ITEMS ================= */
            double totalQty = 0;
            double totalAmt = 0;
            int i = 1;

            for (ItemEntity p : invoice.getListOfItems()) {

                main.addCell(makeCell1(String.valueOf(i++)));
                main.addCell(makeCellLong(p.getName()));
                main.addCell(makeCell1(p.getHsn()));
                main.addCell(makeCell1(String.valueOf(p.getMrp())));
                main.addCell(makeCell1(String.valueOf(p.getQty())));
                main.addCell(makeCell1(String.valueOf(p.getRate())));
                main.addCell(makeCell1(String.valueOf(p.getDiscount())));
                main.addCell(makeCell1(String.valueOf(p.getAmount())));

                totalQty += p.getQty();
                totalAmt += p.getAmount();
            }

            /* ================= TOTAL ROW ================= */
            PdfPCell blank = new PdfPCell(new Phrase("TOTAL QTY"));
            blank.setColspan(2);

            PdfPCell tq = new PdfPCell(new Phrase(String.valueOf(totalQty)));
            tq.setColspan(2);

            PdfPCell blank2 = new PdfPCell(new Phrase("AMOUNT"));
            blank2.setColspan(2);

            PdfPCell tamt = new PdfPCell(new Phrase(String.format("%.2f%%",totalAmt)));
            tamt.setColspan(2);

            main.addCell(blank);
            main.addCell(tq);
            main.addCell(blank2);
            main.addCell(tamt);

            /* ================= GRAND TOTAL ================= */
            PdfPCell grand = new PdfPCell(new Phrase("GRAND TOTAL: " + String.format("%.2f%%",totalAmt), bold));
            grand.setColspan(8);
            grand.setBackgroundColor(Color.lightGray);
            grand.setHorizontalAlignment(Element.ALIGN_RIGHT);
            grand.setPadding(8);
            main.addCell(grand);

            /* ====================In words============================*/

            PdfPCell Inwords = new PdfPCell(new Phrase("In Words: " + NumberToWordsINR.convert(totalAmt), solid));
            Inwords.setColspan(8);
            Inwords.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            Inwords.setPadding(8);
            main.addCell(Inwords);
            /* ================= BANK + SIGN ================= */
            PdfPCell bank = new PdfPCell(new Phrase(
                    "STATE BANK OF INDIA\nA/C: 44378868527\nIFSC: SBIN0011251", normal));
            bank.setColspan(5);
            bank.setPadding(8);

            PdfPCell sign = new PdfPCell(new Phrase(
                    "For RJ Traders\n\nSignature", normal));
            sign.setColspan(3);
            sign.setPadding(8);

            main.addCell(bank);
            main.addCell(sign);

            // ⭐ watermark add karo
            WatermarkUtil.addWatermark(writer, "R.J. TRADERS D.L.N.");
            doc.add(main);
            doc.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* helper method */
    private static PdfPCell makeCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }
    private static PdfPCell makeCell1(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setNoWrap(false); // ⭐ IMPORTANT for Particulars
        return cell;
    }
    private static PdfPCell makeCellLong(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));

        cell.setPadding(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        cell.setNoWrap(false); // ⭐ wrap allow
        cell.setLeading(12f, 0f); // better line spacing

        return cell;
    }
}
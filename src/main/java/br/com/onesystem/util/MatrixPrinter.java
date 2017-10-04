package br.com.onesystem.util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import br.com.onesystem.valueobjects.FormatPage;

/**
 *
 * @author Rafael
 */
public class MatrixPrinter {

// The number of CMs per Inch
    public static final double CM_PER_INCH = 0.393700787d;
// The number of Inches per CMs
    public static final double INCH_PER_CM = 2.545d;
// The number of Inches per mm's
    public static final double INCH_PER_MM = 25.45d;

    public MatrixPrinter(FormatPage format) {
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();
        double width = cmsToPixel(10, 72);
        double height = format.getHeight();
        paper.setSize(width, height);
        // 10 mm border...
        paper.setImageableArea(
                cmsToPixel(0.1, 72),
                cmsToPixel(0.1, 72),
                width - cmsToPixel(0.1, 72),
                height - cmsToPixel(0.1, 72));
        // Orientation
        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(paper);
        PageFormat validatePage = pj.validatePage(pf);
        pj.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int page) throws PrinterException {
                if (page != 0) {
                    return NO_SUCH_PAGE;
                }
                return PAGE_EXISTS;
            }

        }, validatePage);
        try {
            pj.print();
        } catch (PrinterException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Converts the given pixels to cm's based on the supplied DPI
     *
     * @param pixels
     * @param dpi
     * @return
     */
    public static double pixelsToCms(double pixels, double dpi) {
        return inchesToCms(pixels / dpi);
    }

    /**
     * Converts the given cm's to pixels based on the supplied DPI
     *
     * @param cms
     * @param dpi
     * @return
     */
    public static double cmsToPixel(double cms, double dpi) {
        return cmToInches(cms) * dpi;
    }

    /**
     * Converts the given cm's to inches
     *
     * @param cms
     * @return
     */
    public static double cmToInches(double cms) {
        return cms * CM_PER_INCH;
    }

    /**
     * Converts the given inches to cm's
     *
     * @param inch
     * @return
     */
    public static double inchesToCms(double inch) {
        return inch * INCH_PER_CM;
    }
}

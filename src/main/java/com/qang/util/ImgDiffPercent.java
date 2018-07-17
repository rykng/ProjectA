package com.qang.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.io.File;
import java.io.IOException;


/**
 * This util tool is to compare 2 File pdf objects (File based, File current) and it will place 
 * all the diff*.png into the output  directory of where you want all the diff images (page by page) 
 * 
 *  This tool won't bother to compare if both files has different width X length or more/less pages.
 * 
 * To use, it's simple: just called ImgDiffPercent.fullPdfCompare(File, File, String img_directory)
 * it will return the average percentage diff rate from each page. 
 * 
 * it's best use for comparing signed + autofill and fields with different textsize, highlighting, different 
 * shape like strikethrough, italics.  You should have a based file that's pre-autofill and signed. store that 
 * file as a based in the src/test/resources/pdf/image_based directory. and then next time, when you finish signing 
 * a doc. downloaded. and then run this ImgDiffPercent.fullPdfCompare vs the current and the base. 
 *
 * You should note down the percent difference. you kind of have to play around with this tool to understand 
 * what the number means. compare it with autofilled doc vs non-filled or 0 signing docs.and then you compare 
 * with another autofilled + signed doc. you can click on the diff files to see the difference in pink.
 * 
 * for example: if you are missing 1 initial, the diff percentage may increase about 0.08%. 
 * if you are only missing a textfield. it jump only 0.02 to 0.03%. if you are missing a signature, 
 * it might jump to 0.16% (bigger the area of difference, bigger the numbers wiill jump).
 * 
 * In theory, it should even catch regression bugs like missing highlighting capability or missing italics 
 * font checking checkboxes, center vs left align if the image is different from the base doc. 
 *  
 * 
 * Example:
 * 
 * @Test (groups = { "utility" })
 *	public void imgDiffPercentTest() {
 *		String basePath = s_PDF_PATH + File.separator + "Grammar_Practice_base.pdf";
 *		String currentPath = s_PDF_PATH + File.separator + "Grammar_Practice_curr.pdf";
 *		File pdfBase = new File(basePath);
 *		File currentPDF = new File(currentPath);
 *	    	
 *		try {
 *		   double avg = ImgDiffPercent.fullPdfCompare(pdfBase, currentPDF, s_IMAGE_PATH);
 *		   System.out.println("final avg = " +avg + " %");
 *
 *           //assert the value you think it's acceptable as the tolerance rate of changes
 *		   Assert.assertTrue(avg <= 0.025);
 *		}
 *		catch(IOException ioe) {
 *			ioe.printStackTrace();
 *		}
 *		
 *		
 *	}
 * 
 * 
 * 
 * @author rng
 *
 */
public class ImgDiffPercent  {

	
	/**
	 * 
	 * @param pdf
	 * @param imgOutputDir
	 * @throws IOException
	 */
    public static void turnPDFIntoImages(File pdf, String imgOutputDir) throws IOException {
		
    	
    	File imgDir = new File(imgOutputDir);
    	if (!imgDir.exists()) {
	    	imgDir.mkdirs();
	    }
    	
    	PDDocument document = PDDocument.load(pdf);
		
    	PDFRenderer pdfRenderer = new PDFRenderer(document);
    	for (int page = 0; page < document.getNumberOfPages(); ++page)  { 
    	    BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);

    	    // suffix in filename will be used as the file format
    	    ImageIO.write(bim , "png", new File( imgOutputDir + File.separator + pdf.getName() +"_"+page+".png" ));

    	}
    	document.close();
	}
    
    
    /**
     * main method to call to compare the base file and the current file.
     * base file should store in src/test/resources/pdf/compare_based folder
     * @param base
     * @param current
     * @param outputPath
     * @return double[] , each page 
     * @throws IOException
     */
    public static double[] fullPdfCompare(File base, File current, String outputPath) throws IOException {
    	turnPDFIntoImages(base, outputPath);
    	turnPDFIntoImages(current, outputPath);
    	
    	PDDocument doc1 = PDDocument.load(base);
    	PDDocument doc2 = PDDocument.load(current);
    	int numOfPages_doc1 = doc1.getNumberOfPages();
    	int numOfPages_doc2 = doc2.getNumberOfPages();
    	doc1.close();
    	doc2.close();
    	
    	double[] diffImgPercent = new double[numOfPages_doc2];
    	
    	if (numOfPages_doc1 == numOfPages_doc2) {
    		for (int page=0; page< numOfPages_doc2; ++page) {
    			File f1 = new File(outputPath  + File.separator + base.getName() +"_"+page+".png" );
    			File f2 = new File(outputPath  + File.separator + current.getName() +"_"+page+".png" );
    			
    			//System.out.println(f1.getAbsolutePath());
    			//System.out.println(f2.getAbsolutePath());

    			boolean result = getDifferenceImage(f1, f2, outputPath + File.separator +"diff_"+page+".png"); 
    			diffImgPercent[page] = imageDiffPercentage(f1, f2);
    			System.out.println("Page " + page + " diff % ==> " + diffImgPercent[page] + "    result: " + result);
    			
    		}
    		
    		return diffImgPercent;
    	}
    	
    	return null;
    	
    	
    }
    
    
    
    
    /**
     * main method to call to compare the base file and the current file.
     * base file should store in src/test/resources/pdf/compare_based folder
     * @param base
     * @param current
     * @param outputPath
     * @return
     * @throws IOException
     */
    public static double fullPdfCompareAndGetAverageImgDiffPercent(File base, File current, String outputPath) throws IOException {
    	double[] diffImgPercent = fullPdfCompare(base, current, outputPath);
    	double total = 0.0;
    	
    	if (diffImgPercent == null) {
    		return -999.99d;
    	}
    	
    	
    	for (int i=0; i< diffImgPercent.length; i++) {
    		total = total + diffImgPercent[i];
    	}
    	
    	return total / diffImgPercent.length;
    	
    	
    }
	

	public static BufferedImage getDifferenceImage(BufferedImage img1, BufferedImage img2) {

			// convert images to pixel arrays...
			final int w = img1.getWidth(),
			h = img1.getHeight(), 
            highlight = Color.MAGENTA.getRGB();

			final int[] p1 = img1.getRGB(0, 0, w, h, null, 0, w);
			final int[] p2 = img2.getRGB(0, 0, w, h, null, 0, w);

			// compare img1 to img2, pixel by pixel. If different, highlight img1's pixel...
			for (int i = 0; i < p1.length; i++) {
				if (p1[i] != p2[i]) {
                    p1[i] = highlight;
			    }
            }

			// save img1's pixels to a new BufferedImage, and return it...
			// (May require TYPE_INT_ARGB)
			final BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			out.setRGB(0, 0, w, h, p1, 0, w);
			return out;

	}


	/**
	 * 
	 * @param imgFile1
	 * @param imgFile2
	 * @param output
	 * @return
	 */
	public static boolean getDifferenceImage(File imgFile1, File imgFile2, String output) {

			String formatName = "png";
		
			File outputFile = null;
			boolean success = false;

			try {

				
				outputFile = new File(output);
				outputFile.createNewFile();

				BufferedImage img1 = ImageIO.read(imgFile1);
				BufferedImage img2 = ImageIO.read(imgFile2);

				success = ImageIO.write(getDifferenceImage(img1, img2), formatName, outputFile);

			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}

			return success;

	}



	public static double imageDiffPercentage(File file1, File file2)  {

			BufferedImage img1 = null;
			BufferedImage img2 = null;
		

			try {
				

				img1 = ImageIO.read(file1);
				img2 = ImageIO.read(file2);

			} 
			catch (IOException e) {
				e.printStackTrace();
			}

			int width1 = img1.getWidth(null);
			int width2 = img2.getWidth(null);
			int height1 = img1.getHeight(null);
			int height2 = img2.getHeight(null);

			if ((width1 != width2) || (height1 != height2)) {
				System.err.println("Error: Images dimensions mismatch");
				return -99999999.99;
			}

			long diff = 0;

			for (int y = 0; y < height1; y++) {
				for (int x = 0; x < width1; x++) {
					int rgb1 = img1.getRGB(x, y);
					int rgb2 = img2.getRGB(x, y);
					int r1 = (rgb1 >> 16) & 0xff;
					int g1 = (rgb1 >>  8) & 0xff;
					int b1 = (rgb1      ) & 0xff;
					int r2 = (rgb2 >> 16) & 0xff;
					int g2 = (rgb2 >>  8) & 0xff;
					int b2 = (rgb2      ) & 0xff;

			        diff += Math.abs(r1 - r2);
			        diff += Math.abs(g1 - g2);
			        diff += Math.abs(b1 - b2);
				}
			}

		    double n = width1 * height1 * 3;
		    double p = diff / n / 255.0;
		    //System.out.println("diff percent: " + (p * 100.0));
		    
		    return (p * 100.0);

		}

}


package controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.time.LocalDate;
import java.lang.Object;
import java.net.URL;

import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.util.ArrayList;
import java.util.Set;
import enums.Commands;
import logic.ReportDetail;
import ocsf.server.ConnectionToClient;
import server.ServerUI;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import logic.Message;
/**
 * Controller class responsible for handling PDF generation and manipulation.
 */
public class PdfController {

	private ConnectionToClient client;
	  /**
     * Constructs a new PdfController with the given client connection.
     * @param client The connection to the client
     */
	public PdfController(ConnectionToClient client) {
		this.client = client;
	}


    /**
     * Creates a PDF document with the given title, subtitle, image, and output file path.
     * @param title The title of the PDF document
     * @param subtitle The subtitle of the PDF document
     * @param imagePath The path to the image to be included in the PDF
     * @param outputFilePath The path where the generated PDF will be saved
     * @param reportID The ID of the report associated with the PDF
     * @param parkName The name of the park associated with the PDF
     * @return The full path to the generated PDF document
     */
	public String createPDF(String title, String subtitle, String imagePath, String outputFilePath, int reportID,String parkName) {

	    try {
	    	
	    	PDDocument document = new PDDocument();
	        // Create a new page
	        PDPage page = new PDPage(PDRectangle.A4);
	        document.addPage(page);

	        // Create a content stream for drawing on the page
	        PDPageContentStream contentStream = new PDPageContentStream(document, page);

	        // Set the font and draw the title and subtitle
	        contentStream.beginText();
	        contentStream.setFont(PDType1Font.TIMES_ROMAN, 18);
	        contentStream.newLineAtOffset(25, 600);
	        contentStream.showText(title);
	        contentStream.setFont(PDType1Font.TIMES_BOLD, 14);
	        contentStream.newLineAtOffset(0, -20);
	        contentStream.showText(subtitle);
	        contentStream.endText();
	        
	        

	     // Add main image in the top center of the page
//	        PDImageXObject mainImage = PDImageXObject.createFromFile(getClass().getResource("/images/GoNatureLogo.png").getFile(), document);
//	        float mainImageWidth = mainImage.getWidth();
//	        float mainImageHeight = mainImage.getHeight();
//	        float mainImageX = (page.getMediaBox().getWidth() - mainImageWidth) / 2;
//	        float mainImageY = page.getMediaBox().getHeight() - mainImageHeight - 40 ; // Adjust position if needed
//	        float scaleFactor1 = 0.3f;
//	        contentStream.drawImage(mainImage, mainImageX, mainImageY, mainImageWidth* scaleFactor1, mainImageHeight* scaleFactor1);

	        PDImageXObject image = PDImageXObject.createFromFile(imagePath, document);
	        float imageWidth = image.getWidth();
	        float imageHeight = image.getHeight();
	        float scaleFactor2 = 0.7f; // Adjust this value to resize the image
	        contentStream.drawImage(image, 25, 150, imageWidth * scaleFactor2, imageHeight * scaleFactor2);

	        // Close the content stream
	        contentStream.close();

	        // Save the document
	        Path outputPath = Paths.get(outputFilePath);
	        String fileName = "/Report "+LocalDate.now().toString()+".pdf";
	        String outputPathFull = outputPath.toString()+fileName;
	        document.save(outputPathFull);
	        System.out.println("PDF created successfully at: " + outputPath.toAbsolutePath());
	        
	        
	        File imageFile = new File(imagePath);
	        if (imageFile.exists()) {
	            imageFile.delete();
	            System.out.println("Image file deleted: " + imagePath);
	        }
	        
	        return outputPathFull;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

    /**
     * Converts a PDF file to a byte array.
     * @param path The path to the PDF file
     * @return The byte array representing the PDF file
     * @throws FileNotFoundException If the specified file is not found
     * @throws IOException If an I/O error occurs
     */
	public byte[] pdfToByte(String path) throws FileNotFoundException, IOException {
        // Read the PDF file into a byte array
        File pdfFile = new File(path);
        byte[] pdfData = new byte[(int) pdfFile.length()];
        try (FileInputStream fis = new FileInputStream(pdfFile)) {
            fis.read(pdfData);
        }
        return pdfData;
	}

    /**
     * Generates a new report based on the provided report details.
     * @param report The details of the report to be generated
     */
	public void newReport(ReportDetail report) {

		String subtitle = report.getTitle() +" Of "+ report.getParkName() +" Betwwen "+report.getDateFrom().toString() +"& "+report.getDateTo().toString()+".";

		String documentPath = createPDF(report.getTitle(),subtitle,report.getImg(),report.getPathTo(),report.getReportId(),report.getParkName());
		try {
			byte[] document = pdfToByte(documentPath);

		if(ServerUI.sv.dbController.insertReportToDb(report.getTitle(), report.getDateFrom(), report.getDateTo(), document ,report.getParkName())) {
			Message msg = new Message(true,Commands.AddReportCheck);
			try {
				client.sendToClient(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
		Message msg = new Message(false,Commands.AddReportCheck);
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		} catch (FileNotFoundException e) {e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();
		}
		
	}


    /**
     * Retrieves a list of reports from the database and sends it to the client.
     * @param reportType The type of report to retrieve
     */
	public void getReportTable(String reportType) {
		
		
		ArrayList<ReportDetail> reportDetailList = ServerUI.sv.dbController.getReportTableInDb(reportType);
		Message msg = new Message(reportDetailList,Commands.SetReportList);
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}



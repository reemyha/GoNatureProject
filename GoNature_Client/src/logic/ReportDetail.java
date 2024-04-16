package logic;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;
import java.time.LocalDate;

import javafx.scene.image.WritableImage;
/**
 * Represents details of a report including park name, date range, title, image, and file.
 */
public class ReportDetail implements Serializable {
	
	private String parkName;
	private LocalDate dateTo;
	private LocalDate dateFrom;
	private String title;
	private String img;
	private String pathTo;
	private int reportId;
	private String reportTitle;
	private byte[] file;
	
	public ReportDetail(String parkName,LocalDate dateFrom,LocalDate dateTo, String title, String image, String pathTo) {
		this.dateTo = dateTo;
		this.dateFrom = dateFrom;
		this.title = title;
		this.img = image;
		this.pathTo = pathTo;
		this.parkName = parkName;
	}


	

	public ReportDetail(int reportID, String reportTitle, LocalDate date_from, LocalDate date_to, String parkName, byte[] file) {
		this.reportId = reportID;
		this.reportTitle = reportTitle;
		this.dateTo = date_to;
		this.dateFrom = date_from;
		this.parkName = parkName;
		this.file = file;

		
	}

	public String getParkName() {
		return parkName;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public String getTitle() {
		return title;
	}

	public String getImg() {
		return img;
	}

	public String getPathTo() {
		return pathTo;
	}




	public int getReportId() {
		return reportId;
	}




	public String getReportTitle() {
		return reportTitle;
	}




	public byte[] getFile() {
		return file;
	}

}

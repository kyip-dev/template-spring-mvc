package com.kyip.utils;

import java.io.FileOutputStream;
import java.net.URL;

import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Ref: http://howtodoinjava.com/apache-commons/create-pdf-files-in-java-itext-tutorial/
 * @author kyip
 *
 */
@Component
public class PdfFileBuilder {

	public void build(String filePath) {
		Document document = new Document();
		try
		{
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
			document.open();
			document.add(new Paragraph("Demo build PDF using itextpdf"));

			// set attributes
			document.addAuthor("kyip");
			document.addCreationDate();
			document.addCreator("");
			document.addTitle("Title");
			document.addSubject("Subject attr");

			// set image
			String imageUrl = "https://avatars1.githubusercontent.com/u/15243708?v=3&s=460";
			Image image = Image.getInstance(new URL(imageUrl));
			image.scalePercent(10);
			document.add(image);

			// build table
			PdfPTable table = new PdfPTable(3); // 3 columns.
			table.setWidthPercentage(100); //Width 100%
			table.setSpacingBefore(10f); //Space before table
			table.setSpacingAfter(10f); //Space after table
			float[] columnWidths = {1f, 1f, 1f};
			table.setWidths(columnWidths);

			// build table cells
			PdfPCell cell1 = createTableCell("row1 Cell 1", BaseColor.BLUE);
			PdfPCell cell2 = createTableCell("row1 Cell 2", BaseColor.BLACK);
			PdfPCell cell3 = createTableCell("row1 Cell 3", BaseColor.MAGENTA);
			PdfPCell cell4 = createTableCell("row2 Cell 1", BaseColor.MAGENTA);
			PdfPCell cell5 = createTableCell("row2 Cell 2", BaseColor.GREEN);
			PdfPCell cell6 = createTableCell("row2 Cell 3", BaseColor.DARK_GRAY);

			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell(cell5);
			table.addCell(cell6);

			document.add(table);

			document.close();
			writer.close();
		} catch (Exception e)
		{

		}
	}

	private PdfPCell createTableCell(String paragraph, BaseColor borderColor) {
		PdfPCell cell = new PdfPCell(new Paragraph(paragraph));
		cell.setBorderColor(borderColor);
		cell.setPaddingLeft(10);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		return cell;
	}
}

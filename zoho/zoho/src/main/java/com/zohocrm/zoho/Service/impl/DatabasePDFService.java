package com.zohocrm.zoho.Service.impl;


import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.zohocrm.zoho.Payload.LeadDto;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Stream;

public class DatabasePDFService {

    public static ByteArrayInputStream employeePDFReport(List<LeadDto> leads) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfWriter.getInstance(document, out);
            document.open();

            // Add Content to PDF file ->
            Font fontHeader = FontFactory.getFont(FontFactory.TIMES_BOLD, 22);
            Paragraph para = new Paragraph("Leads Structure", fontHeader);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(10);
            // Add PDF Table Header ->
            Stream.of("ID", "First Name", "Last Name","Email", "Mobile","Lead Type","Address" , "Designation","Company","Note").forEach(headerTitle -> {
                PdfPCell header = new PdfPCell();
                Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD);
                header.setBackgroundColor(Color.CYAN);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setBorderWidth(2);
                header.setPhrase(new Phrase(headerTitle, headFont));
                table.addCell(header);
            });

            for (LeadDto lead : leads) {
                PdfPCell idCell = new PdfPCell(new Phrase(lead.getLid().toString()));
                idCell.setPaddingLeft(4);
                idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(idCell);

                PdfPCell firstNameCell = new PdfPCell(new Phrase(lead.getFirstName()));
                firstNameCell.setPaddingLeft(4);
                firstNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                firstNameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(firstNameCell);

                PdfPCell lastNameCell = new PdfPCell(new Phrase(String.valueOf(lead.getLastName())));
                lastNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lastNameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                lastNameCell.setPaddingRight(4);
                table.addCell(lastNameCell);

                PdfPCell deptCell = new PdfPCell(new Phrase(String.valueOf(lead.getEmail())));
                deptCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                deptCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                deptCell.setPaddingRight(4);
                table.addCell(deptCell);

                PdfPCell phoneNumCell = new PdfPCell(new Phrase(String.valueOf(lead.getMobile())));
                phoneNumCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                phoneNumCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                phoneNumCell.setPaddingRight(4);
                table.addCell(phoneNumCell);

                PdfPCell leadType = new PdfPCell(new Phrase(String.valueOf(lead.getLeadType())));
                leadType.setVerticalAlignment(Element.ALIGN_MIDDLE);
                leadType.setHorizontalAlignment(Element.ALIGN_CENTER);
                leadType.setPaddingRight(4);
                table.addCell(leadType);
                PdfPCell address = new PdfPCell(new Phrase(String.valueOf(lead.getAddress())));
                address.setVerticalAlignment(Element.ALIGN_MIDDLE);
                address.setHorizontalAlignment(Element.ALIGN_CENTER);
                address.setPaddingRight(4);
                table.addCell(address);
                PdfPCell designation = new PdfPCell(new Phrase(String.valueOf(lead.getDesignation())));
                designation.setVerticalAlignment(Element.ALIGN_MIDDLE);
                designation.setHorizontalAlignment(Element.ALIGN_CENTER);
                designation.setPaddingRight(4);
                table.addCell(designation);
                PdfPCell Company = new PdfPCell(new Phrase(String.valueOf(lead.getCompany())));
                Company.setVerticalAlignment(Element.ALIGN_MIDDLE);
                Company.setHorizontalAlignment(Element.ALIGN_CENTER);
                Company.setPaddingRight(4);
                table.addCell(Company);
                PdfPCell Note = new PdfPCell(new Phrase(String.valueOf(lead.getNote())));
                Note.setVerticalAlignment(Element.ALIGN_MIDDLE);
                Note.setHorizontalAlignment(Element.ALIGN_CENTER);
                Note.setPaddingRight(4);
                table.addCell(Note);
            }
            document.add(table);

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}

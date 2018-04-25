package com.project.manager.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.project.manager.models.Report;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.util.Optional;

/**
 * Class responsible for generating reports in pdf file
 */
public class PDFReportGenerator {
    private Logger logger;

    public PDFReportGenerator() {
        this.logger = Logger.getLogger(PDFReportGenerator.class);
    }

    /**
     * @param report this is object which will provide all date necessary to generate report, that data was fetch from
     *               original project object
     * @return pdf file which is converting to byte array
     * @throws DocumentException exception in case of generate pdf problems
     */
    public byte[] createReportPDF(Report report) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        setTitle(report, document);

        setDates(report, document);

        setTeamTable(report, document);

        setTasksTable(report, document);

        document.close();
        writer.close();
        logger.info("The report was created!");

        return baos.toByteArray();
    }

    /**
     * That method set tasks in table in order of status in pdf document
     * @param report report object which store tasks
     * @param document original document where method print table
     * @throws DocumentException in case of generating pdf problems
     */
    private void setTasksTable(Report report, Document document) throws DocumentException {
        Paragraph tasksParagraph = new Paragraph("Tasks", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
        tasksParagraph.setAlignment(Element.ALIGN_CENTER);
        tasksParagraph.setSpacingAfter(10);
        document.add(tasksParagraph);

        PdfPTable tasks = new PdfPTable(new float[]{3, 3, 3});
        PdfPCell newTasks = new PdfPCell(new Phrase("New tasks", FontFactory.getFont(FontFactory.HELVETICA, 14)));
        PdfPCell currentTasks = new PdfPCell(new Phrase("Actual tasks", FontFactory.getFont(FontFactory.HELVETICA, 14)));
        PdfPCell doneTasks = new PdfPCell(new Phrase("Done tasks", FontFactory.getFont(FontFactory.HELVETICA, 14)));
        newTasks.setHorizontalAlignment(Element.ALIGN_CENTER);
        currentTasks.setHorizontalAlignment(Element.ALIGN_CENTER);
        doneTasks.setHorizontalAlignment(Element.ALIGN_CENTER);
        tasks.addCell(newTasks);
        tasks.addCell(currentTasks);
        tasks.addCell(doneTasks);

        PdfPCell first = new PdfPCell();
        first.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPTable firstTable = new PdfPTable(1);
        first.addElement(firstTable);
        firstTable.setWidthPercentage(100);

        PdfPCell second = new PdfPCell();
        first.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPTable secondTable = new PdfPTable(1);
        second.addElement(secondTable);
        secondTable.setWidthPercentage(100);

        PdfPCell third = new PdfPCell();
        first.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPTable thirdTable = new PdfPTable(1);
        third.addElement(thirdTable);
        thirdTable.setWidthPercentage(100);

        prepareNewTasks(report, firstTable);
        prepareCurrentTasks(report, secondTable);
        prepareDoneTasks(report, thirdTable);

        tasks.addCell(first);
        tasks.addCell(second);
        tasks.addCell(third);
        document.add(tasks);
    }

    private void prepareDoneTasks(Report report, PdfPTable thirdTable) {
        report.getDoneTasks().forEach(o -> {
            PdfPCell c = new PdfPCell(
                    new Phrase(o.getTaskName() + "\n" + o.getTag() + "\n" + o.getTaskPriority().name()));
            c.setPaddingBottom(50);
            c.setHorizontalAlignment(Element.ALIGN_CENTER);
            thirdTable.addCell(c);
        });
    }

    private void prepareCurrentTasks(Report report, PdfPTable secondTable) {
        report.getCurrentTasks().forEach(o -> {
            PdfPCell c = new PdfPCell(
                    new Phrase(o.getTaskName() + "\n" + o.getTag() + "\n" + o.getTaskPriority().name()));
            c.setPaddingBottom(50);
            c.setHorizontalAlignment(Element.ALIGN_CENTER);
            secondTable.addCell(c);
        });
    }

    private void prepareNewTasks(Report report, PdfPTable firstTable) {
        report.getNewTasks().forEach(o -> {
            PdfPCell c = new PdfPCell(
                    new Phrase(o.getTaskName() + "\n" + o.getTag() + "\n" + o.getTaskPriority().name()));
            c.setPaddingBottom(50);
            c.setHorizontalAlignment(Element.ALIGN_CENTER);
            firstTable.addCell(c);
        });
    }

    /**
     * That method print table filled data about members of project
     * @param report object which store all necessary data to generate pdf report document
     * @param document original document which will be generated, it's needed to print table inside this object
     * @throws DocumentException in case of generating pdf problems
     */
    private void setTeamTable(Report report, Document document) throws DocumentException {
        PdfPTable table = new PdfPTable(new float[]{2, 2});
        PdfPCell cell = new PdfPCell(new Paragraph("Team"));
        cell.setColspan(4);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5f);
        table.addCell(cell);

        PdfPCell managerColumn = new PdfPCell(new Paragraph("Manager"));
        managerColumn.setHorizontalAlignment(Element.ALIGN_CENTER);
        managerColumn.setPadding(5f);
        table.addCell(managerColumn);

        PdfPCell membersColumn = new PdfPCell(new Paragraph("Team"));
        membersColumn.setHorizontalAlignment(Element.ALIGN_CENTER);
        membersColumn.setPadding(5f);
        table.addCell(membersColumn);

        java.util.List<String> team = report.getTeam();
        PdfPCell manager = new PdfPCell(new Paragraph(team.get(0)));
        manager.setRowspan(team.size() - 1);
        manager.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(manager);

        for (int i = 1; i < team.size(); i++) {
            PdfPCell members = new PdfPCell(new Paragraph(team.get(i)));
            members.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(members);
        }
        table.setSpacingAfter(25);
        document.add(table);
    }

    /**
     * Method which print date with period between last and actual report
     * @param report object with store all data about report which will be used to generate document
     * @param document original document reference which is use to print elements inside
     * @throws DocumentException in case of generating pdf problems
     */
    private void setDates(Report report, Document document) throws DocumentException {
        Paragraph lastDate = new Paragraph("Last report date: This is first report");
        Optional.ofNullable(report.getLastReportDate())
                .ifPresent(l -> lastDate.set(0, new Paragraph("Last report date: " + l.toString())));
        lastDate.setAlignment(Element.ALIGN_LEFT);
        Paragraph actualDate = new Paragraph("Actual report date: " + report.getActualDate());
        actualDate.setAlignment(Element.ALIGN_LEFT);
        actualDate.setSpacingAfter(20);
        document.add(lastDate);
        document.add(actualDate);
    }

    /**
     * Method which print title of report inside pdf document
     * @param report which store all needed data about report
     * @param document original object where method print elements
     * @throws DocumentException in case of generating pdf problems
     */
    private void setTitle(Report report, Document document) throws DocumentException {
        Paragraph titleParagraph = new Paragraph(report.getTitle(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22));
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        titleParagraph.setSpacingBefore(20f);
        document.add(titleParagraph);
    }
}

package work;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.table.DefaultTableModel;
import java.io.FileOutputStream;
import java.util.Vector;
import java.util.stream.Stream;


public class ExportPdf
{
    public static void createPdf(String filename, String[] columns, DefaultTableModel table_model) throws Exception
    {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();

        PdfPTable table = new PdfPTable(columns.length);
        addTableHeader(table,columns);
        addRows(table, table_model);

        document.add(table);
        document.close();
    }

    private static void addRows(PdfPTable table, DefaultTableModel table_model)
    {
        Vector entity = table_model.getDataVector();
        for (int i = 0; i < table_model.getRowCount(); i++)
        {
            for (int j = 0; j < table_model.getColumnCount(); j++)
            {
                table.addCell(((Vector)entity.elementAt(i)).elementAt(j).toString());
            }
        }
    }

    private static void addTableHeader(PdfPTable table, String[] columns)
    {
        Stream.of(columns).forEach(columnTitle ->
        {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.PINK);
            header.setBorderWidth(1);
            header.setPhrase(new Phrase(columnTitle));
            table.addCell(header);
        });
    }
}

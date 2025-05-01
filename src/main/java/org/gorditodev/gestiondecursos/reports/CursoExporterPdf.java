package org.gorditodev.gestiondecursos.reports;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import org.gorditodev.gestiondecursos.entity.Curso;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class CursoExporterPdf {

    private List<Curso> listaCursos;

    public CursoExporterPdf(List<Curso> listaCursos) {
        this.listaCursos = listaCursos;
    }

    private void writeTableHeader(PdfPTable table) {// escribe la cabezera de la tabla
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.blue);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);//esto adjunta la celda
        cell.setPhrase(new Phrase("Descripcion", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Publicado", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Nivel", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Titulo", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {// para escribir datos en la tabla
        for (Curso curso : listaCursos) {
            table.addCell(String.valueOf(curso.getId()));
            table.addCell(curso.getDescripcion());
            table.addCell(curso.getIsPublicado());
            table.addCell(String.valueOf(curso.getNivel()));
            table.addCell(curso.getTitulo());
        }
    }

    public void export(HttpServletResponse response) throws IOException {//metodo para exportar
        Document document = new Document(PageSize.A4);// para elegir el tamaño de la hoja  impreimir
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();// abre el documento

        //Elegimos las fuentes
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Lista de Cursos", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);//Numero de columnas
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.3f, 3.5f, 3.5f, 2.0f, 1.5f});// calcula el espacio entre las celdas
        table.setSpacingBefore(10);//espaciado entre columnas

        writeTableHeader(table);//escribe la cabecera
        writeTableData(table);//escribe los datos

        document.add(table);//pasos importantes
        document.close();//pasos importantes
    }
}

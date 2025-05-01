package org.gorditodev.gestiondecursos.reports;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;// en caso de no aparecer llamaar manualmente
import org.gorditodev.gestiondecursos.entity.Curso;

import java.io.IOException;
import java.util.List;

public class CursoExportExcel {

    private XSSFWorkbook workbook;//para trabajar en archivos excel (libros)

    private XSSFSheet sheet;// crea la hoja de calculo

    private List<Curso> cursos;

    public CursoExportExcel(List<Curso> cursos) {
        this.cursos = cursos;
        workbook = new XSSFWorkbook();// Inicializamos el libro

    }

    private void writeHeader() {//inicializamos la cabecera
        sheet = workbook.createSheet("Cursos");

        Row row = sheet.createRow(0);//Numero de filas
        CellStyle style = workbook.createCellStyle();// se crea un estilo celda
        XSSFFont font = workbook.createFont();
        font.setBold(true);// para q    ue este en negrita
        font.setFontHeight(16);
        style.setFont(font);
//Organizamos las filas osea las de arriba
        createCell(row, 0, "ID", style);
        createCell(row, 1, "Descripcion", style);
        createCell(row, 2, "Publicado", style);
        createCell(row, 3, "Nivel", style);
        createCell(row, 4, "Titulo", style);

    }

    //Metodo para crear celdas a partir de la que querramos
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);

        if(value instanceof Integer) {
            cell.setCellValue((Integer)value);
        }
        else if(value instanceof Boolean) {
            cell.setCellValue((Boolean)value);//Valor boolean casteado
        }
        else if(value instanceof String) {
            cell.setCellValue((String)value);
        }
        cell.setCellStyle(style);// pasamos el estilo
    }

    private void writeDataLines(){
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();

        font.setFontHeight(14);
        style.setFont(font);

        for(Curso curso:cursos){
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row,columnCount++, curso.getId(), style);
            createCell(row, columnCount++, curso.getDescripcion(), style);
            createCell(row, columnCount++, curso.getIsPublicado(), style);
            createCell(row, columnCount++, curso.getNivel(), style);
            createCell(row, columnCount++, curso.getTitulo(), style);

        }
    }

    //Metodo para exportar
    public void export(HttpServletResponse response) throws IOException {
        writeHeader();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}

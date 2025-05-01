package org.gorditodev.gestiondecursos.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.gorditodev.gestiondecursos.entity.Curso;
import org.gorditodev.gestiondecursos.reports.CursoExportExcel;
import org.gorditodev.gestiondecursos.reports.CursoExporterPdf;
import org.gorditodev.gestiondecursos.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    public CursoController(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }


    @GetMapping
    public String home() {

        return "redirect:/cursos"; //pendiente el redirect sino sale eliminarlo
    }

    @GetMapping("/cursos")
    public String listarCursos(Model model) {
        List<Curso> cursos = cursoRepository.findAll();
        cursos = cursoRepository.findAll();
        model.addAttribute("cursos", cursos);
        return "cursos";
    }

    @GetMapping("/cursos/nuevo")
    public String agregarCurso(Model model) {
        Curso curso = new Curso();
        //curso.setPublicado(true);

        model.addAttribute("curso", curso);
        model.addAttribute("pageTitle", "Nuevo Curso");
        return "curso_form";
    }

    @PostMapping("/cursos/save")
    public String guardarCurso(Curso curso, RedirectAttributes redirectAttributes) {
        try {
            cursoRepository.save(curso);
            redirectAttributes.addFlashAttribute("message", "Curso guardado correctamente.... OK");
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/cursos";
    }
//Actualizar curso
    @GetMapping("/cursos/{id}")
    public String editarCurso(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Curso curso = cursoRepository.findById(id).get();

            model.addAttribute("pageTitle", "Editar curso:" + id);
            model.addAttribute("curso", curso);
            //redirectAttributes.addFlashAttribute("message", "Curso actualizado con exito");
            return "curso_form";
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/cursos";
    }

//Eliminar curso
    @GetMapping("/cursos/eliminar/{id}")
    public String eliminarCurso(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            cursoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Curso eliminado correctamente, mi brother");
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/cursos";
    }

    @GetMapping("/export/pdf")
    public void generarReportePDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");// por el formato del reporte ne este caso PDf
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");//formateador de fecha con su patron, en años, meses, dias, horas, minutos y segundos
        String currentDateTime = dateFormat.format(new Date());// para poner la fecha actual o del servidor

        String headerKey = "Content-Disposition";// Cabecera
        String headerValue = "attachment; filename=cursos" + currentDateTime + ".pdf";//oara descargar el archivo
        response.setHeader(headerKey, headerValue);// genera la descarga del reporte

        List<Curso> cursos = cursoRepository.findAll();//pasa los valores a imprimir

        CursoExporterPdf exporterPDF = new CursoExporterPdf(cursos);
        exporterPDF.export(response);
    }


    @GetMapping("/export/excel")
    public void generarReporteExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");// por el formato del reporte ne este caso PDf
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");//formateador de fecha con su patron, en años, meses, dias, horas, minutos y segundos
        String currentDateTime = dateFormat.format(new Date());// para poner la fecha actual o del servidor

        String headerKey = "Content-Disposition";// Cabecera
        String headerValue = "attachment; filename=cursos" + currentDateTime + ".xlsx";//oara descargar el archivo
        response.setHeader(headerKey, headerValue);// genera la descarga del reporte

        List<Curso> cursos = cursoRepository.findAll();//pasa los valores a imprimir

        CursoExportExcel exporterExcel = new CursoExportExcel(cursos);
        exporterExcel.export(response);
    }

}

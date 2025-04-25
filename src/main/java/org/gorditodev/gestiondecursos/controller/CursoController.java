package org.gorditodev.gestiondecursos.controller;

import org.gorditodev.gestiondecursos.entity.Curso;
import org.gorditodev.gestiondecursos.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
}

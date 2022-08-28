/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eventoapp.eventoapp.controller;

import com.eventoapp.eventoapp.dao.ConvidadoDao;
import com.eventoapp.eventoapp.dao.EventoDao;
import com.eventoapp.eventoapp.model.Convidado;
import com.eventoapp.eventoapp.model.Evento;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Gustavo
 */

@Controller
public class EventoController {
    
    @Autowired
    private EventoDao eventoDao;
    
    @Autowired
    private ConvidadoDao convidadoDao;
    
    @RequestMapping(value="/cadastrarEvento", method=RequestMethod.GET)
    public String form() {
        return "evento/formEvento";
    }
    
    @RequestMapping(value="/cadastrarEvento", method=RequestMethod.POST)
    public String form(@Valid Evento evento, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("mensagem", "Verifique os campos!");
            return "redirect:/cadastrarEvento";
        }
        
        eventoDao.save(evento);
        
        redirectAttributes.addFlashAttribute("mensagem", "Evento salvo com sucesso!");
        return "redirect:/cadastrarEvento";
    }
    
    @RequestMapping("/eventos")
    public ModelAndView listaEventos() {
        ModelAndView modelAndView = new ModelAndView("index");
        
        Iterable<Evento> eventos = eventoDao.findAll();
        modelAndView.addObject("eventos", eventos);
        
        return modelAndView;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView detalhesEvento(@PathVariable("id") long idEvento) {
        Evento evento = eventoDao.findById(idEvento);

        ModelAndView modelAndView = new ModelAndView("evento/detalhesEvento");
        modelAndView.addObject("evento", evento);
        
        Iterable<Convidado> convidados = convidadoDao.findByEvento(evento);
        modelAndView.addObject("convidados", convidados);

        return modelAndView;
    }
    
    @RequestMapping("/deletarEvento")
    public String deletarEvento(long idEvento) {
        Evento evento = eventoDao.findById(idEvento);
        eventoDao.delete(evento);
        
        return "redirect:/eventos";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String salvarConvidado(@PathVariable("id") long idEvento, @Valid Convidado convidado,
            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("mensagem", "Verifique os campos!");
            return "redirect:/{id}";
        }
        
        Evento evento = eventoDao.findById(idEvento);
        convidado.setEvento(evento);
        
        convidadoDao.save(convidado);
        
        redirectAttributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");
        return "redirect:/{id}";
    }
    
    @RequestMapping("/deletarConvidado")
    public String deletarConvidado(String rg) {
        Convidado convidado = convidadoDao.findByRg(rg);
        
        Evento evento = convidado.getEvento();
        long codigoEvento = evento.getId();
        String codigo = "" + codigoEvento;
        
        convidadoDao.delete(convidado);
        
        return "redirect:/" + codigoEvento;
    }
    
}

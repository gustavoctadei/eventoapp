/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eventoapp.eventoapp.controller;

import com.eventoapp.eventoapp.dao.EventoDao;
import com.eventoapp.eventoapp.model.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Gustavo
 */

@Controller
public class EventoController {
    
    @Autowired
    private EventoDao eventoDao;
    
    @RequestMapping(value="/cadastrarEvento", method=RequestMethod.GET)
    public String form() {
        return "evento/formEvento";
    }
    
    @RequestMapping(value="/cadastrarEvento", method=RequestMethod.POST)
    public String form(Evento evento) {
        eventoDao.save(evento);
        
        return "redirect:/cadastrarEvento";
    }
    
}

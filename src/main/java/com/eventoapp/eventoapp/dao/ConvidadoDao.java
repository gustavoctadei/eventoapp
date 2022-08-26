/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.eventoapp.eventoapp.dao;

import com.eventoapp.eventoapp.model.Convidado;
import com.eventoapp.eventoapp.model.Evento;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Gustavo
 */
public interface ConvidadoDao extends CrudRepository<Convidado, String> {
    
    Iterable<Convidado> findByEvento(Evento evento);
    
}

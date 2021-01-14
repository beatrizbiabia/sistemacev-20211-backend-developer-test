package com.survivorapi.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.survivorapi.dto.ExchangeDTO;
import com.survivorapi.entity.Item;
import com.survivorapi.entity.Survivor;
import com.survivorapi.exception.ResultException;
import com.survivorapi.repository.SurvivorRepository;



/**
 * Class Service for exchange item
 *  Created by Beatriz on 11/01/21.
 */
@Service
public class ExchangeService {


    @Autowired
    private SurvivorRepository peopleRepository;

    /**
     * Make an exchange between survivors
     * @param dto
     */
    @Transactional
    public void makeExchange(ExchangeDTO dto)  throws Exception {

        Survivor people = peopleRepository.findById(dto.getUserId()).get();

        Survivor recipient = peopleRepository.findByName(dto.getRecipientName());

        if (people.isContamined() || recipient.isContamined()) {
            throw new ResultException("Survivor/Recipient is infected.");
        }

        Integer pointsWanted = 0;

        Integer pointsPayment = 0;

        for (Item wanted : dto.getItemsWanted()) {

            pointsWanted +=  wanted.getPoint() * wanted.getQuantity();
        }

        for (Item payment : dto.getItemsForPayment()) {

            pointsPayment += payment.getPoint() * payment.getQuantity();
        }

        if ( !pointsWanted.equals(pointsPayment) ) {
            throw new ResultException("Items exchanged must have the same amount of points.");
        }
        
        Set<Item> itensIWanted = new HashSet();

        //For people
        for (Item item : people.getItens()) {
        	
        	

            //add item inventory
            for (Item itemWanted : dto.getItemsWanted()) {

                if (item.getName().equals(itemWanted.getName())) {

                    item.setQuantity(item.getQuantity() + itemWanted.getQuantity());
                }else {
                	itensIWanted.add(itemWanted);
                }
            }

            //Subtract item inventory
            for (Item itemPayment : dto.getItemsForPayment()) {

                if (item.getName().equals(itemPayment.getName())) {

                    item.setQuantity(item.getQuantity() - itemPayment.getQuantity());
                    if(item.getQuantity()<0) {
                    	item.setQuantity(0);
                    }
                }
            }
        }
        
          people.getItens().addAll(itensIWanted);

        //For recipient
        for (Item item : recipient.getItens()) {

            //subtract item inventory
            for (Item itemWanted : dto.getItemsWanted()) {

                if (item.getName().equals(itemWanted.getName())) {

                    item.setQuantity(item.getQuantity() - itemWanted.getQuantity());
                    if(item.getQuantity()<0) {
                    	item.setQuantity(0);
                    }
                }
            }

            //add item inventory
            for (Item itemPayment : dto.getItemsForPayment()) {

                if (item.getName().equals(itemPayment.getName())) {

                    item.setQuantity(item.getQuantity() + itemPayment.getQuantity());
                }
            }
        }

        peopleRepository.save(people);

        peopleRepository.save(recipient);

    }

    private List<Item> fillListEntityItems(List<Item> lstItensDTO) {

        List<Item> lstItems = new ArrayList<>();

        lstItensDTO.stream().forEach(itemDTO -> {

            Item item = new Item();

            item.setName(itemDTO.getName());
            item.setPoint(itemDTO.getPoint());

            item.setQuantity(itemDTO.getQuantity());

            lstItems.add(item);
        });

        return lstItems;
    }


}

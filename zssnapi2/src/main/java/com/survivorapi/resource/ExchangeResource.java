package com.survivorapi.resource;




import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.survivorapi.dto.ExchangeDTO;
import com.survivorapi.exception.ResultException;
import com.survivorapi.resource.util.HeaderUtil;
import com.survivorapi.service.ExchangeService;

/**
 * Created by Beatriz on 11/01/21.
 */
@RestController
@RequestMapping(value = "/exchange")
public class ExchangeResource {

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private HttpServletResponse response;
    
 
    /**
     * Make exchange between Survivors
     * @param exchange
     * @throws Exception 
     */
    @RequestMapping(value = "/make-exchange", method = RequestMethod.POST)
    public ResponseEntity<ExchangeDTO> makeExchange(@RequestBody ExchangeDTO exchange) throws Exception {
    	
    	try {
    		 exchangeService.makeExchange(exchange);
    	}catch (IOException e) {
    			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "500 INTERNAL_SERVER_ERROR");
    		  throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR ,"Survivor/Recipient is infected.");
		}
       
        
             
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert("exchange", exchange.getUserId().toString()))
                .body(exchange);
    }
}

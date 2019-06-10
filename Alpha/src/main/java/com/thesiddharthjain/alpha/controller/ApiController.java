/**
 * 
 */
package com.thesiddharthjain.alpha.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thesiddharthjain.alpha.dto.BaseRequest;
import com.thesiddharthjain.alpha.dto.BaseResponse;
import com.thesiddharthjain.alpha.service.BankService;


/**
 * @author sjxpl
 *
 */


@RestController
@RequestMapping("/api")
public class ApiController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);
	
	@Autowired
	private BankService bankService;
	
	
	@GetMapping("/status")
	public String statusCheck()
	{
		LOGGER.debug("********* In status Check Method*******");
		return bankService.getApiStatus();
	}
	
	@PostMapping("/bankdetails")
	public String getBankDetails(@RequestBody BaseRequest req) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException
	{
		LOGGER.debug("********* In bank Details Method*******");
		return bankService.getBankDetails(req);
	}
	

}

package com.thesiddharthjain.alpha.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import com.thesiddharthjain.alpha.dto.BaseRequest;
import com.thesiddharthjain.alpha.dto.BaseResponse;

public interface BankService {
	
	public String getApiStatus();
	public String getBankDetails(BaseRequest request) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException;

}

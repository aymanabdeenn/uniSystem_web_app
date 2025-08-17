package com.example.uniSystem_web_app.services;

import com.example.uniSystem_web_app.dto.RecaptchaResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RecaptchaService {

    private static final String SECRET_KEY = "6Lc3n6grAAAAAIMRnw1L3vPYCxDsQaKge76umZHu";
    private static final String VERIFY_URL =
            "https://www.google.com/recaptcha/api/siteverify";

    public boolean verifyToken(String token) {
        RestTemplate restTemplate = new RestTemplate();

        String url = VERIFY_URL + "?secret=" + SECRET_KEY + "&response=" + token;

        RecaptchaResponse recaptchaResponse =
                restTemplate.postForObject(url, null, RecaptchaResponse.class);

        return recaptchaResponse != null && recaptchaResponse.isSuccess();
    }

}

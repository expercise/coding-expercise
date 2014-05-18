package com.ufukuzun.kodility.utils;

import com.ufukuzun.kodility.exception.KodilityGenericException;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class ShaDigester {

    public String sha256(String value) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(value.getBytes("UTF-8"));
            return new String(Hex.encode(messageDigest.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new KodilityGenericException("Message digester algorithm exception has occurred.", e);
        } catch (UnsupportedEncodingException e) {
            throw new KodilityGenericException("Byte encoding error has occurred.", e);
        }
    }

}

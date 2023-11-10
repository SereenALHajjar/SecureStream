/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;

/**
 *
 * @author Seree
 */
public class Sig 
{
    PrivateKey privateKey ;
    PublicKey publicKey , other ;
    Signature signature ;
    Sig(PublicKey pu , PrivateKey pr , PublicKey o) throws NoSuchAlgorithmException, InvalidKeyException
    {
        this.privateKey = pr ;
        this.publicKey = pu ;
        this.other = o ;
        this.signature = Signature.getInstance("SHA256withRSA");
    }
    String getSig(String msg) throws SignatureException, InvalidKeyException
    {
        this.signature.initSign(this.privateKey);
        this.signature.update(msg.getBytes()) ;
        byte[] sign = this.signature.sign() ;
        String s = Base64.getEncoder().encodeToString(sign) ;
        return s ;
    }
    boolean veri(String msg , String rs) throws InvalidKeyException, SignatureException
    {
        this.signature.initVerify(this.other);
        this.signature.update(msg.getBytes());
        byte[] sign = Base64.getDecoder().decode(rs) ;
        return this.signature.verify(sign) ;
    }
        
}

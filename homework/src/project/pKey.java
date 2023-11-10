/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework;

import java.io.Serializable;
import java.security.PublicKey;

/**
 *
 * @author Seree
 */
public class pKey implements Serializable{
    PublicKey key ; 
    pKey(PublicKey Key)
    {
         this.key=Key ;
    }
    PublicKey getkey()
    {
        return this.key ; 
    }
}

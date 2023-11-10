/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework;

import java.io.Serializable;

/**
 *
 * @author Seree
 */
public class Message implements Serializable{
    String msg ;
    String msgSig ; 
    Message(String a , String b)
    {
        this.msg=a ; 
        this.msgSig=b ;
    }
}

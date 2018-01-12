package Testit;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import tiralabra.reitinhakuohjelma.rakenne.Pino;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author User
 */
public class PinoTesti {
    
    public PinoTesti() {
        
    }
    
    @Test
    public void testaaLisays() {
        Pino<Integer> pino = new Pino<>();
        pino.push(1);
        assertTrue(pino.peek() == 1);
    }
    
    @Test
    public void testaaKoko() {
        Pino<Integer> pino = new Pino<>();
        pino.push(1);
        assertTrue(pino.koko()==1);
    }
    
    @Test
    public void testaaPoisto() {
        Pino<Integer> pino = new Pino<>();
        pino.push(1);
        assertTrue(pino.pop() == 1);
        assertTrue(pino.onTyhja());
    }
    
    @Test
    public void testaaTyhjenna() {
        Pino<Integer> pino = new Pino<>();
        pino.push(1);
        pino.push(2);
        pino.push(3);
        pino.tyhjenna();
        assertTrue(pino.onTyhja());
    }
    
    @Test
    public void testaaArraynKasvatus() {
        Pino<Integer> pino = new Pino<>();
        for (int i = 0; i < 11; i++) {
            pino.push(i);
        }
        assertTrue(pino.arraynKoko() == 20);
        for (int i = 0; i < 11; i++) {
            pino.push(10 + i);
        }
        assertTrue(pino.arraynKoko() == 40);
    }
}

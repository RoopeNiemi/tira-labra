package Testit;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import tiralabra.reitinhakuohjelma.rakenne.Lista;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author User
 */
public class ListaTesti {

    public ListaTesti() {

    }

    @Test
    public void testaaLisays() {
        Lista<Integer> l = new Lista<>();
        l.lisaa(10);
        assertTrue(l.get(0) == 10);
    }

    @Test
    public void testaaKoko() {
        Lista<Integer> lista = new Lista<>();
        lista.lisaa(1);
        assertTrue(lista.koko() == 1);
        lista.lisaa(1);
        lista.lisaa(2);
        assertTrue(lista.koko() == 3);
    }

    @Test
    public void testaaArraynKasvatus() {
        Lista<Integer> l = new Lista<>();
        for (int i = 0; i < 11; i++) {
            l.lisaa(i);
        }
        assertTrue(l.arraynKoko() == 20);
        for (int i = 0; i < 11; i++) {
            l.lisaa(10 + i);
        }
        assertTrue(l.arraynKoko() == 40);
    }

    @Test
    public void testaaGet() {
        Lista<Integer> l = new Lista<>();
        for (int i = 0; i < 10; i++) {
            l.lisaa(i);
        }
        assertTrue(l.get(6) == 6);

    }
}

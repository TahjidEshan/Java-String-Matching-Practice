/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab3compiler;

/**
 *
 * @author eshan
 */
public class Transition {

    Node node;
    char string;
    String str;

    public Transition(Node n, char s) {
        this.node = n;
        this.string = s;
    }

    public Transition(Node n, String s) {
        this.node = n;
        this.str = s;
    }
}

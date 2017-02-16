/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab3compiler;

import java.util.ArrayList;

/**
 *
 * @author eshan
 */
public class Node {

    public ArrayList<Transition> list = new ArrayList();
    public ArrayList<Node> onEmpty = new ArrayList();
    public boolean isAccept = false;
    public boolean isReject = false;

    public Node getNextMove(char str) {
        for (Transition t : list) {
            if (t.string == str) {
                return t.node;
            }
        }
        return null;
    }

    public Node getNextMove(String str) {
        for (Transition t : list) {
            if (t.str.equals(str)) {
                return t.node;
            }
        }
        return null;
    }

    public boolean hasEmptyMove() {
        return !onEmpty.isEmpty();
    }

    public ArrayList emptyList() {
        return onEmpty;
    }

    public void addEmptyMove(Node n) {
        this.onEmpty.add(n);
    }

    public void addTransition(Transition t) {
        this.list.add(t);
    }
}

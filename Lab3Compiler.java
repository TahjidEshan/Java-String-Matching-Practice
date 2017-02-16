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
import java.util.*;

public class Lab3Compiler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int regCount = scanner.nextInt();
        scanner.nextLine();
        Node[] regEx = new Node[regCount];
        for (int i = 0; i < regEx.length; ++i) {
            String s = scanner.nextLine();
            Node start = new Node();
            Node end = new Node();
            end.isAccept = true;
            Node reject = new Node();
            reject.isReject = true;
            for (int val = 48; val <= 90; ++val) {
                Transition t = new Transition(reject, (char) val);
                end.addTransition(t);
            }
            Node current = start;
            regEx[i] = start;
            for (int j = 0; j < s.length(); ++j) {
                if (s.charAt(j) == '(') {
                    // String temp = "";
                    char val = '-';
                    String temp = "";
                    char c = s.charAt(j + 1);
                    Node temp1 = current;
                    for (int m = j + 1; m < s.length(); ++m) {
                        if (s.charAt(m) == ')') {
                            if (m + 1 < s.length()) {
                                val = s.charAt(m + 1);
                            }
                            j = m + 1;
                            break;
                        } else {
                            //  temp = temp + s.charAt(m);
                            Node n = new Node();
                            Transition t = new Transition(n, s.charAt(m));
                            current.addTransition(t);
                            current = n;
                            temp = temp + s.charAt(m);
                        }
                    }
                    //System.out.println(temp);
                    if (val == '*') {
//                        Transition t = new Transition(current, temp);
//                        current.addTransition(t);
                        temp1.addEmptyMove(current);
                        Transition t = new Transition(temp1, c);
                        current.addTransition(t);
                    } else if (val == '+') {
                        Node k = current;
                        for (int v = 0; v < temp.length(); ++v) {
                            Node n = new Node();
                            Transition t = new Transition(n, temp.charAt(v));
                            current.addTransition(t);
                            current = n;
                        }
                        k.addEmptyMove(current);
                        Transition t = new Transition(k, c);
                        current.addTransition(t);
                    }
                } else if (s.charAt(j) == '[') {
                    char val = '/';
                    String temp = "";
                    Node n = new Node();
                    Node temp1 = current;
                    for (int m = j + 1; m < s.length(); ++m) {
                        if (s.charAt(m) == ']') {
                            if (m + 1 < s.length() && (s.charAt(m + 1) == '*' || s.charAt(m + 1) == '+' || s.charAt(m + 1) == '?')) {
                                val = s.charAt(m + 1);
                                j = m + 1;
                            } else {
                                j = m;
                            }
                            current = n;
                            break;
                        } else if (s.charAt(m) == '-') {
                            int a = (int) s.charAt(m - 1);
                            int b = (int) s.charAt(m + 1);
                            for (int value = a; value <= b; ++value) {
                                Transition t = new Transition(n, (char) value);
                                current.addTransition(t);
                                temp += (char) value;
                            }
                            current = n;
                        } else if ((m < s.length() - 1 && s.charAt(m + 1) != '-') || (m == s.length() - 1)) {
                            Transition t = new Transition(n, s.charAt(m));
                            current.addTransition(t);
                            temp += s.charAt(m);
                        }
                    }
                    if (val == '*') {
                        temp1.addEmptyMove(current);
                        for (int k = 0; k < temp.length(); ++k) {
                            Transition t = new Transition(current, temp.charAt(k));
                            current.addTransition(t);
                        }
                    } else if (val == '+') {
                        for (int k = 0; k < temp.length(); ++k) {
                            Transition t = new Transition(current, temp.charAt(k));
                            current.addTransition(t);
                        }
                    } else if (val == '?') {
                        temp1.addEmptyMove(current);
                        for (int k = 0; k < temp.length(); ++k) {
                            Transition t = new Transition(reject, temp.charAt(k));
                            current.addTransition(t);
                        }
                    }
                } else {
                    if (j < s.length() - 1 && s.charAt(j + 1) == '*') {
                        Transition t = new Transition(current, s.charAt(j));
                        current.addTransition(t);
                        ++j;
                    } else if (j < s.length() - 1 && s.charAt(j + 1) == '+') {
                        Node point = new Node();
                        Transition t = new Transition(point, s.charAt(j));
                        current.addTransition(t);
                        current = point;
                        Transition tr = new Transition(current, s.charAt(j));
                        current.addTransition(tr);
                        ++j;
                    } else if (j < s.length() - 1 && s.charAt(j + 1) == '?') {
                        Node point = new Node();
                        Transition t = new Transition(point, s.charAt(j));
                        current.addTransition(t);
                        current.addEmptyMove(point);
                        current = point;
                        Transition tr = new Transition(reject, s.charAt(j));
                        current.addTransition(tr);
                        ++j;
                    } else if (s.charAt(j) == '^' && j < s.length() - 1) {
                        Transition tr = new Transition(reject, s.charAt(j + 1));
                        current.addTransition(tr);
                        ++j;
                    } else {
                        Node point = new Node();
                        Transition t = new Transition(point, s.charAt(j));
                        current.addTransition(t);
                        current = point;
                    }
                }
            }
            current.addEmptyMove(end);
        }
        int sCount = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < sCount; ++i) {
            String s = scanner.nextLine();
            boolean reject = true;
            for (int j = 0; j < regEx.length; ++j) {
                Node current = regEx[j];
                for (int m = 0; m < s.length(); ++m) {
                    try {
                        Node temp = current.getNextMove(s.charAt(m));
                        if (temp != null && temp.hasEmptyMove()) {
                            if (m < s.length() - 1 && temp.getNextMove(s.charAt(m + 1)) != null) {

                            } else {
                                ArrayList<Node> list = temp.emptyList();
                                temp = list.get(0);
                                if (temp.isAccept && m == s.length() - 1) {
                                    System.out.println("Yes," + (j + 1));
                                    reject = false;
                                    break;
                                } else if (temp.isReject) {
                                    break;
                                }
                            }
                        }
                        if (!temp.isAccept) {
                            current = temp;
                        }
                    } catch (NullPointerException e) {
                    }
                }

            }

            if (reject) {
                System.out.println("No,0");
            }
        }
    }

}

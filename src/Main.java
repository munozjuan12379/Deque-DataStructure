// This program is meant to create a simulation of a post office in which customers are in a
// line where they are either being serviced, become disgruntled and leave, or move to the side
// and then get put back in front of line when ready.
// This program uses a modified Node and Deque class that takes in a class customer as their data portions

import java.util.Random;
import java.util.Scanner;

class Node{
    private Customer cust;
    private Node next;
    private Node prev;
    public Node() {
        cust = null;
        next = null;
        prev = null;
    }
    public Node(Node n, Node p, Customer c) {
        cust = c;
        next = n;
        prev = p;
    }
    public void setCustomer(Customer c) {
        cust = c;
    }
    public void setNext(Node n) {
        next = n;
    }
    public void setPrev(Node p){
        prev = p;
    }
    public Customer getCustomer() {
        return cust;
    }
    public Node getNext() {
        return next;
    }
    public Node getPrev(){
        return prev;
    }
}
class Deque {
    private int size;
    private Node front;
    private Node rear;

    public Deque() {
        size = 0;
        front = null;
        rear = null;
    }
    public void addFront(Customer x) {
        Node t = new Node(null, null, x);
        if (front == null) {
            front = t;
            rear = t;
        } else {
            t.setNext(front);
            front.setPrev(t);
            front = t;
        }
        size++;
    }
    public void addRear(Customer x) {
        Node t = new Node(null, null, x);
        if (front == null) {
            front = t;
            rear = t;
        } else {
            t.setPrev(rear);
            rear.setNext(t);
            rear = t;
        }
        size++;
    }
    public Customer removeFront() {
        if (front == null) {
            return null;
        }
        Customer x = front.getCustomer();
        front = front.getNext();
        if (front == null) {
            rear = null;
        }
        else{
            front.setPrev(null);
        }

        size --;
        return x;
    }
    public Customer removeRear(){
        if (front == null) {
            return null;
        }
        Customer x = rear.getCustomer();
        rear = rear.getPrev();
        if (rear == null) {
            front = null;
        }
        else{
            rear.setNext(null);
        }

        size --;
        return x;
    }
    public Customer getFront() {
        if (front == null)
            return null;
        else {
            return front.getCustomer();
        }
    }
    public Customer getRear() {
        if (rear == null)
            return null;
        else {
            return rear.getCustomer();
        }
    }
    public int size() {
        return size;
    }
    public boolean isEmpty() {
        if (front == null)
            return true;
        else return false;
    }
    public void clear() {
        front = null;
        rear = null;
        size = 0;
    }
    public void print(){
        Node t = front;
        while(t != null){
            System.out.println(t.getCustomer());
            t = t.getNext();
        }
    }
}
class Customer{
    private Integer customerNmbr;
    private Integer processTime;
    private Integer disgruntFlag;
    public Customer(){
        customerNmbr = 0;
        processTime = 0;
        disgruntFlag = 0;
    }
    public Customer(Integer a, Integer b, Integer c){
        customerNmbr = a;
        processTime = b;
        disgruntFlag = c;
    }
    public void setCustomerNmbr(Integer a){
        customerNmbr = a;
    }
    public void setProcessTime(Integer b){
        processTime = b;
    }
    public void setDisgruntFlag(Integer c){
        disgruntFlag = c;
    }
    public int getCustomerNmbr(){
        return customerNmbr;
    }
    public int getProcessTime(){
        return processTime;
    }
    public int getDisgruntFlag(){
        return disgruntFlag;
    }
}

public class Main {
    public static void dataCheck(int x){
        Scanner scan = new Scanner(System.in);
        while (x < 0 || x> 100){
            System.out.println("Number not valid enter a new number between 0-100");
            x = scan.nextInt();
        }
    }
    public static void main(String[] args) {
        Random rand = new Random();
        Scanner scan = new Scanner(System.in);
        // get simulation parameters
        System.out.println("Enter the probability a new customer will arrive between 0-100: ");
        int arrivalProb = scan.nextInt();
        dataCheck(arrivalProb);
        System.out.println("Enter the probability a customer will become disgruntled between 0-100: ");
        int disgruntProb = scan.nextInt();
        dataCheck(disgruntProb);
        System.out.println("Enter the probability a customer will need to step aside between 0-100: ");
        int stepasideProb = scan.nextInt();
        dataCheck(stepasideProb);
        System.out.println("Enter the total simulation time as any positive integer: ");
        int simTime = scan.nextInt();
        while (simTime < 0){
            System.out.println("Number not valid enter a positive Integer");
            simTime = scan.nextInt();
        }
        System.out.println("Enter the total time it will take a customer to be service between 3-10: ");
        int serviceTime = scan.nextInt();
        while (serviceTime < 3 || serviceTime > 10){
            System.out.println("Number not valid enter a integer between 3-10");
            serviceTime = scan.nextInt();
        }
        // set different data structures
        Deque officeLine = new Deque();
        Deque sideLine = new Deque();
        Deque finishLine = new Deque();
        Deque leavers = new Deque();
        officeLine.addRear(new Customer(-3, 0, 0));
        officeLine.addRear(new Customer(-2, 0, 0));
        officeLine.addRear(new Customer(-1, 0, 0));
        // begin simulation
        for (int i = 0; i < simTime; i++){
            if (officeLine.getFront().getProcessTime() < serviceTime) {
                // new customer arrives
                if (rand.nextInt(100)+1 <= arrivalProb) {
                    officeLine.addRear(new Customer(simTime, 0, 0));
                }
                // customer in the end of line gets disgruntled
                if (rand.nextInt(100)+1 <= disgruntProb){
                    officeLine.getRear().setDisgruntFlag(1);
                    leavers.addRear(officeLine.getRear());
                    officeLine.removeRear();
                }
                // customer in front of line needs to step aside
                if (rand.nextInt(100)+1 <= stepasideProb && sideLine.getFront() == null){
                    sideLine.addFront(officeLine.getFront());
                    officeLine.removeFront();
                }
            }
            // customer in front has been fully processed
            // move them to the processed customer deque
            else {
                finishLine.addRear(officeLine.getFront());
                officeLine.removeFront();
            }
            simTime++;
            officeLine.getFront().setProcessTime(simTime);
        }
        System.out.println("Number of customers serviced: ");
        while (finishLine.getFront().)
        finishLine.print();
        System.out.println("Number of customers still in line");
        officeLine.print();
        sideLine.print();
        System.out.println("Number of disgruntled customers");
        leavers.print();
    }
}
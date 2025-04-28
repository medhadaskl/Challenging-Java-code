package org.example;
public class DetectLoop_FloydsCycle {
        // Node class represents a node in the linked list
        static class Node {
            int data;
            Node next;

            Node(int data) {
                this.data = data;
                this.next = null;
            }
        }

        private Node head;

        // Add a new node to the end of the list
        public void add(int data) {
            Node newNode = new Node(data);
            if (head == null) {
                head = newNode;
            } else {
                Node temp = head;
                while (temp.next != null) {
                    temp = temp.next;
                }
                temp.next = newNode;
            }
        }

        // Detect if there is a cycle in the linked list using Floyd's Cycle detection algorithm
        public boolean hasCycle() {
            if (head == null) return false;

            Node slow = head;
            Node fast = head;

            while (fast != null && fast.next != null) {
                slow = slow.next; //slow pointer
                fast = fast.next.next; //fast pointer

                if (slow == fast) {
                    // If slow and fast pointers meet, there is a cycle
                    return true;
                }
            }
            return false; // If fast pointer reaches the end, there is no cycle
        }

        // Print the list (for debugging purposes)
        public void printList() {
            Node temp = head;
            while (temp != null) {
                System.out.print(temp.data + " -> ");
                temp = temp.next;
            }
            System.out.println("null");
        }

        // Method to create a cycle for testing purposes
        public void createCycle(int pos) {
            if (head == null) return;

            Node temp = head;
            Node cycleStartNode = null;
            int index = 0;

            while (temp.next != null) {
                if (index == pos) {
                    cycleStartNode = temp;
                }
                temp = temp.next;
                index++;
            }

            if (cycleStartNode != null) {
                temp.next = cycleStartNode; // Create the cycle
            }
        }

        public static void main(String[] args) {
            DetectLoop_FloydsCycle list = new DetectLoop_FloydsCycle();
            list.add(1);
            list.add(2);
            list.add(3);
            list.add(4);
            list.add(5);

            // Test without a cycle
            System.out.println("Has cycle (should be false): " + list.hasCycle());

            // Create a cycle for testing
            list.createCycle(2); // Creating a cycle starting at position 2 (Node 3)
            System.out.println("Has cycle (should be true): " + list.hasCycle());
        }
}

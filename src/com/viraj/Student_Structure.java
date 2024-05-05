package com.viraj;
    public class Student_Structure {

        static class Student {
            int id;
            String name;
            Student next;

            public Student(int id, String name) {
                this.id = id;
                this.name = name;
                this.next = null;
            }
        }

        static class StudentLinkedList {
            Student head;

            public StudentLinkedList() {
                this.head = null;
            }

            public void insert(Student student) {
                if (head == null) {
                    head = student;
                } else {
                    Student temp = head;
                    while (temp.next != null) {
                        temp = temp.next;
                    }
                    temp.next = student;
                }
            }

            public void traverse() {
                Student temp = head;
                while (temp != null) {
                    System.out.println("ID: " + temp.id + ", Name: " + temp.name);
                    temp = temp.next;
                }
            }

            public void delete(int id) {
                if (head == null) {
                    return;
                }
                if (head.id == id) {
                    head = head.next;
                    return;
                }
                Student temp = head;
                while (temp.next != null && temp.next.id != id) {
                    temp = temp.next;
                }
                if (temp.next != null) {
                    temp.next = temp.next.next;
                }
            }

            public Student concatenate(Student head2) {
                if (head == null) {
                    return head2;
                }
                Student temp = head;
                while (temp.next != null) {
                    temp = temp.next;
                }
                temp.next = head2;
                return head;
            }

            public void insertSorted(Student element) {
                if (head == null || element.id < head.id) {
                    element.next = head;
                    head = element;
                    return;
                }
                Student temp = head;
                while (temp.next != null && element.id > temp.next.id) {
                    temp = temp.next;
                }
                element.next = temp.next;
                temp.next = element;
            }

            public void insertQueue(Student element) {
                if (head == null) {
                    head = element;
                } else {
                    Student temp = head;
                    while (temp.next != null) {
                        temp = temp.next;
                    }
                    temp.next = element;
                }
            }

            public Student deleteQueue() {
                if (head == null) {
                    return null;
                }
                Student deletedNode = head;
                head = head.next;
                deletedNode.next = null;
                return deletedNode;
            }
        }

        public static void main(String[] args) {
            StudentLinkedList list = new StudentLinkedList();

            // Test inserting students
            list.insert(new Student(1, "John"));
            list.insert(new Student(2, "Alice"));
            list.insert(new Student(3, "Bob"));

            // Test traversing the list
            System.out.println("Students in the list:");
            list.traverse();

            // Test deleting a student
            list.delete(2);
            System.out.println("After deleting student with ID 2:");
            list.traverse();

            // Test concatenating two lists
            StudentLinkedList list2 = new StudentLinkedList();
            list2.insert(new Student(4, "Eve"));
            list2.insert(new Student(5, "Mike"));

            System.out.println("Students in the second list:");
            list2.traverse();

            list.concatenate(list2.head);
            System.out.println("Concatenated list:");
            list.traverse();

            // Test inserting an element in sorted order
            Student newStudent = new Student(6, "Sarah");
            list.insertSorted(newStudent);
            System.out.println("After inserting new student in sorted order:");
            list.traverse();

            // Test insertQueue and deleteQueue
            StudentLinkedList queueList = new StudentLinkedList();
            queueList.insertQueue(new Student(10, "Amy"));
            queueList.insertQueue(new Student(11, "Mark"));

            System.out.println("Students in the queue list:");
            queueList.traverse();

            Student deletedNode = queueList.deleteQueue();
            System.out.println("Deleted node from the queue: ID: " + deletedNode.id + ", Name: " + deletedNode.name);
            System.out.println("Updated queue list:");
            queueList.traverse();
        }
    }

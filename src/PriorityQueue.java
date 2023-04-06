public class PriorityQueue<E extends Comparable<E>> {

    private Node head;

    public void enqueue (E value) {
        Node newNode = new Node(value);
        head = merge(newNode,head);
    }
    public E dequeue() {
        E returnValue = head.value;
        if (head.left == null && head.right== null){
            head = null;
        }
        else{
            head = merge(head.left,head.right);
        }
        return returnValue;
    }

    public boolean isEmpty(){
        return head == null;
    }

    private Node merge(Node firstNode, Node secondNode) {
        Node small;
        if (firstNode == null) return secondNode;
        if (secondNode == null) return firstNode;
        if (firstNode.value.compareTo(secondNode.value) < 0) {
            firstNode.right = merge(firstNode.right, secondNode);
            small = firstNode;
        } else {
            secondNode.right = merge(secondNode.right, firstNode);
            small = secondNode;
        }
        if (getNpl(small.left) < getNpl(small.right)) {
            Node temporary = small.left;
            small.left = small.right;
            small.right = temporary;
        }
        small.nullPathLength = setNullPathLength(small);
        return small;
    }

    private int setNullPathLength(Node node){
        int left = 0;
        int right = 0;
        if (node == null){
            return 0;
        }
        left += setNullPathLength(node.left);
        right += setNullPathLength(node.right);

        if (left < right){
            left++;
            return left;
        }
        else {
            right++;
            return right;
        }
    }

    private int getNpl(Node node) {
        if (node == null) return -1;
        return node.nullPathLength;
    }

    class Node {
        public Node left;
        public Node right;
        public int nullPathLength;
        public E value;

        public Node(E value) {
            this.value = value;
            this.right = null;
            this.left = null;
        }
    }
}




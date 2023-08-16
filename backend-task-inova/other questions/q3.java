
public class BackendTaskInovaApplication {


    /*
    Node here is a simulation class that should hold a value and a pointer to the next node.
     */
    public static void main(final String[] args) {
        public Node middleNode(Node start) {
            Node slow, fast, new_start;
            slow  = start;
            fast = start;
            while(fast != null  && fast.next != null)
            {
                slow = slow.next;
                fast = fast.next.next;
            }
            new_start = slow;
            return new_start;

        }
    }

}
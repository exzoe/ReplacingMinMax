import java.util.NoSuchElementException;

public class SimpleLinkedList<T extends Comparable <T>> {

    private SimpleLinkedListItem<T> head = null;
    private SimpleLinkedListItem<T> tail = null;
    private int count = 0;

    private static class SimpleLinkedListItem<T> {
        public T value;
        public SimpleLinkedListItem<T> next;

        public SimpleLinkedListItem(T value, SimpleLinkedListItem<T> next) {
            this.value = value;
            this.next = next;
        }

        public SimpleLinkedListItem(T value) {
            this(value, null);
        }

        public SimpleLinkedListItem() {
            this(null, null);
        }
    }

    public void addFirst(T value) {
        head = new SimpleLinkedListItem<>(value, head);
        if (tail == null) {
            tail = head;
        }
        count++;
    }

    public void addLast(T value) {
        if (tail == null) {
            head = tail = new SimpleLinkedListItem<>(value);
        } else {
            tail.next = new SimpleLinkedListItem<>(value);
            tail = tail.next;
        }
        count++;
    }

    private SimpleLinkedListItem<T> getItem(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + count);
        }
        SimpleLinkedListItem<T> curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr;
    }

    public T get(int index) {
        return getItem(index).value;
    }

    public int size() {
        return count;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        SimpleLinkedListItem<T> curr = head;
        while (curr != null) {
            sb.append(curr.value);
            if (curr.next != null) {
                sb.append(", ");
            }
            curr = curr.next;
        }
        return sb.toString();
    }

    public T findLastMax() {
        if (head == null) {
            throw new NoSuchElementException("The list is empty.");
        }

        SimpleLinkedListItem<T> curr = head;
        T result = curr.value;
        while (curr != null) {
            if (curr.value.compareTo(result) > 0) {
                result = curr.value;
            }
            curr = curr.next;
        }
        return result;
    }

    public void swapMinMax() {
        if (head == null || head.next == null) {
            return;
        }

        SimpleLinkedListItem<T> minNode = head;
        SimpleLinkedListItem<T> maxNode = head;
        SimpleLinkedListItem<T> prevMinNode = null;
        SimpleLinkedListItem<T> prevMaxNode = null;

        SimpleLinkedListItem<T> curr = head;
        SimpleLinkedListItem<T> prev = null;

        while (curr != null) {
            if (curr.value.compareTo(minNode.value) < 0) {
                minNode = curr;
                prevMinNode = prev;
            }
            if (curr.value.compareTo(maxNode.value) >= 0) {
                maxNode = curr;
                prevMaxNode = prev;
            }
            prev = curr;
            curr = curr.next;
        }

        if (minNode == maxNode) {
            return;
        }


        if (prevMinNode != null) {
            prevMinNode.next = maxNode;
        } else {
            head = maxNode;
        }

        if (prevMaxNode != null) {
            prevMaxNode.next = minNode;
        } else {
            head = minNode;
        }

        SimpleLinkedListItem<T> temp = minNode.next;
        minNode.next = maxNode.next;
        maxNode.next = temp;

        if (minNode.next == null) {
            tail = minNode;
        }
        if (maxNode.next == null) {
            tail = maxNode;
        }
    }
}
package forneymon.arena;

import java.util.Objects;
import forneymon.species.*;


/**
 * Collections of Forneymon ready to fight in the arena!
 */
public class LinkedForneymonagerie implements Forneymonagerie {

    // Fields
    // -----------------------------------------------------------
    private Node sentinel;
    private int size, modCount;


    // Constructor
    // -----------------------------------------------------------
    public LinkedForneymonagerie () {
        this.size = this.modCount = 0;
        this.sentinel = new Node(null);
        this.sentinel.next = this.sentinel;
        this.sentinel.prev = this.sentinel;
    }


    // Methods
    // -----------------------------------------------------------
    public boolean empty () {
        return size == 0;
    }

    public int size () {
        return size;
    }

    public boolean collect (Forneymon toAdd) {
        return insertForneymon(toAdd, 1);
    }

    public boolean releaseSpecies (String fmType) {
        Node found = find(fmType);
        boolean exists = found != null;
        if (exists) {
            removeForneymon(fmType, 1);
        }
        return exists;
    }

    public Forneymon get (int index) {
        if (index < 0 || index >= this.size) {
            throw new IllegalArgumentException();
        }
        return find(index).fm;
    }

    public Forneymon getMVP () {
        Forneymon best = this.sentinel.next.fm;
        for (Node curr = this.sentinel.next; curr != this.sentinel; curr = curr.next) {
            Forneymon current = curr.fm;
            int currentLevel = current.getLevel(),
                currentHealth = current.getHealth();
            if ((best.getLevel() > currentLevel) || (best.getLevel() == currentLevel && best.getHealth() >= currentHealth)) {
                continue;
            }
            best = current;
        }
        return best;
    }

    public Forneymon remove (int index) {
        if (index < 0 || index >= this.size) {
            throw new IllegalArgumentException();
        }
        Node found = find(index);
        removeForneymon(found.fm.getSpecies(), 1);
        return found.fm;
    }

    public int getSpeciesIndex (String fmType) {
        int i = 0;
        for (Node curr = this.sentinel.next; curr != this.sentinel; curr = curr.next) {
            if (fmType.equals(curr.fm.getSpecies())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public boolean containsSpecies (String toCheck) {
        return find(toCheck) != null;
    }

    public void trade (Forneymonagerie other) {
        LinkedForneymonagerie otherLFM = (LinkedForneymonagerie) other;
        Node tempHead = sentinel;
        int tempSize = size;

        sentinel = otherLFM.sentinel;
        size = otherLFM.size;

        otherLFM.sentinel = tempHead;
        otherLFM.size = tempSize;
        modCount++;
        otherLFM.modCount++;
    }

    public void rearrange (String fmType, int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException();
        }
        Node found = find(fmType);
        Node newPos = this.sentinel;
        int currInd = -1;
        int i = 0;
        for (Node current = this.sentinel.next; i < this.size; current = current.next, i++) {
            if (current == found) { currInd = i; }
            if (i == index-1) {
                newPos = current;
            }
        }

        if (found == null || currInd == index) { return; }
        if (currInd < index) { newPos = newPos.next; }
        deleteNode(found);
        addAfter(newPos, found);
        this.modCount++;
    }

    public LinkedForneymonagerie.Iterator getIterator () {
        if (empty()) {
            throw new IllegalStateException();
        }
        return new LinkedForneymonagerie.Iterator(this);
    }

    @Override
    public LinkedForneymonagerie clone () {
        LinkedForneymonagerie dolly = new LinkedForneymonagerie();
        for (Node curr = this.sentinel.next; curr != this.sentinel; curr = curr.next) {
            dolly.appendNode(new Node(curr.fm.clone()));
        }
        dolly.size = size;
        return dolly;
    }

    @Override
    public String toString () {
        String[] result = new String[size];
        int i = 0;
        for (Node curr = this.sentinel.next; curr != this.sentinel; curr = curr.next, i++) {
            result[i] = curr.fm.toString();
        }
        return "[ " + String.join(", ", result) + " ]";
    }

    @Override
    public boolean equals (Object other) {
        if (other.getClass() != this.getClass()) { return false; }
        LinkedForneymonagerie otherFM = (LinkedForneymonagerie) other;
        if (this.size != otherFM.size) { return false; }
        for (Node curr1 = this.sentinel.next, curr2 = otherFM.sentinel.next; curr1 != this.sentinel && curr2 != otherFM.sentinel; curr1 = curr1.next, curr2 = curr2.next) {
            if (!curr1.fm.equals(curr2.fm)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode () {
        return Objects.hash(this.sentinel, this.size, this.modCount);
    }


    // Private helper methods
    // -----------------------------------------------------------

    private Node find (int index) {
        Node curr = this.sentinel.next;
        for (; curr != this.sentinel; curr = curr.next, index--) {
            if (index == 0) {
                break;
            }
        }
        return (curr == this.sentinel) ? null : curr;
    }

    private Node find (String toFind) {
        Node curr = this.sentinel.next;
        for (; curr != this.sentinel; curr = curr.next) {
            if (curr.fm.getSpecies().equals(toFind)) {
                break;
            }
        }
        return (curr == this.sentinel) ? null : curr;
    }

    private void appendNode (Node n) {
        Node oldTail = this.sentinel.prev;
        n.prev = oldTail;
        n.next = this.sentinel;
        oldTail.next = n;
        this.sentinel.prev = n;
    }

    private void addAfter (Node toAddAfter, Node toAdd) {
        toAdd.next = toAddAfter.next;
        toAdd.prev = toAddAfter;
        toAddAfter.next.prev = toAdd;
        toAddAfter.next = toAdd;
    }

    private void deleteNode (Node n) {
        n.prev.next = n.next;
        n.next.prev = n.prev;
    }

    private boolean insertForneymon (Forneymon toAdd, int count) {
        Node found = find(toAdd.getSpecies());
        boolean newType = false;

        // Case: new fm, so add new Node
        if (found == null) {
            appendNode(new Node(toAdd));
            newType = true;
            this.size++;

        // Case: existing fm, so update level
        } else if (found.fm != toAdd) {
            found.fm = (found.fm.getLevel() < toAdd.getLevel()) ? toAdd : found.fm;
        } else {
            return newType;
        }

        modCount++;
        return newType;
    }

    private boolean removeForneymon (String fmType, int count) {
        Node found = find(fmType);

        // Case: no such fm toRemove
        if (found == null) {
            return false;
        }

        this.modCount++;
        this.deleteNode(found);
        this.size -= count;
        return true;
    }


    // Inner Classes
    // -----------------------------------------------------------

    public class Iterator {
        private LinkedForneymonagerie host;
        private Node current;
        private int itModCount;

        Iterator (LinkedForneymonagerie host) {
            this.host = host;
            this.itModCount = host.modCount;
            this.current = host.sentinel.next;
        }

        public boolean atEnd () {
            return isValid() && current.next == host.sentinel;
        }

        public boolean atStart () {
            return isValid() && current.prev == host.sentinel;
        }

        public boolean isValid () {
            return host.modCount == itModCount && host.size > 0;
        }

        public Forneymon getCurrent () {
            verifyIntegrity();
            return current.fm;
        }

        public void next () {
            verifyIntegrity();
            this.current = this.current.next;
            if (current == host.sentinel) {
                this.current = this.current.next;
            }
        }

        public void prev () {
            verifyIntegrity();
            this.current = this.current.prev;
            if (current == host.sentinel) {
                this.current = this.current.prev;
            }
        }

        public Forneymon removeCurrent () {
            Forneymon fm = this.current.fm;
            Node old = this.current;
            this.prev();
            host.deleteNode(old);
            host.size--;
            this.itModCount++;
            host.modCount++;
            return fm;
        }

        private void verifyIntegrity () {
            if (!isValid()) {
                throw new IllegalStateException();
            }
        }
    }

    private class Node {
        Node next, prev;
        Forneymon fm;

        Node (Forneymon fm) {
            this.fm = fm;
        }
    }

}

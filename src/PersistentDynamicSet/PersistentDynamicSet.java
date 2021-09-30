/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistentDynamicSet;
import BinarySearchTree.BinarySearchTree;
import BinarySearchTree.Node;
import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
/**
 *
 * @author jcvsa
 */
public class PersistentDynamicSet<E> extends BinarySearchTree{
    
    public PersistentDynamicSet() {
        super();
    }

    public PersistentDynamicSet(Collection<? extends E> c) {
        super(c);
    }

    public PersistentDynamicSet(Comparator<? super E> comparator) {
        super(comparator);
    }

    public PersistentDynamicSet(SortedSet<E> s) {
        super(s);
    }
    
    
    
}

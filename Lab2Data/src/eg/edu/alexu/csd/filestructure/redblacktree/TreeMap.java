package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;

import javax.management.RuntimeErrorException;

import java.util.Set;

public class TreeMap<V, T> implements ITreeMap {
    private IRedBlackTree RB = new RedBlackTree();
    private ArrayList<T> keys = new ArrayList<T>();
    private ArrayList<T> sortedKeys = new ArrayList<T>();
    private ArrayList<V> values = new ArrayList<V>();
    private ArrayList<V> sortedValues = new ArrayList<V>();
    private Map.Entry<Comparable, V> ElementEqualKey=null;
    private Set <Map.Entry<T, V> > mySet = new LinkedHashSet<>();
    private ArrayList<Entry<T, V>> arrayOfEntries = new ArrayList<>();
    private int size=0;
    TreeMap(){

    }
    @Override
    public Entry ceilingEntry(Comparable key) {
        if(key==null){
            throw new RuntimeErrorException(null);
        }
        Map.Entry<Comparable, V> entry = null;
        Iterator<Map.Entry<Comparable, V>> itr = entrySet().iterator();
        while(itr.hasNext())
        {
            Map.Entry<Comparable, V> temp =itr.next();
            if (key.compareTo(temp.getKey())<=0) {
                entry=temp;
                break;
            }
        }

        return entry;
    }

    @Override
    public Comparable ceilingKey(Comparable key) {
        return (Comparable) ceilingEntry(key).getKey();
    }

    @Override
    public void clear() {
        size=0;
        RB = new RedBlackTree();
    }

    @Override
    public boolean containsKey(Comparable key) {
        return RB.contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        if (value==null) {
            throw new RuntimeErrorException (null);
        }
        System.out.print(RB.getRoot().getKey());
        return searchValue (RB.getRoot(),value);
    }

    public boolean searchValue (INode root,Object value) {
        INode temp = root;
        if (temp==null||temp.getValue()==null) return false;
        if (temp.getValue().equals(value)) {
            return true;
        }
        return searchValue (temp.getLeftChild(),value)||searchValue(temp.getRightChild(),value);
    }
    @Override
    public Set entrySet() {
//      mySort();
        mySet.clear();
        Comparator<? super Entry<T, V>> c = new Comparator<Entry<T, V>>() {

            @Override
            public int compare(Entry<T, V> o1, Entry<T, V> o2) {
                if (((Comparable) o1.getKey()).compareTo(o2.getKey()) < 0) {
                    return -1;
                }
                if (((Comparable) o1.getKey()).compareTo(o2.getKey()) > 0) {
                    return 1;
                }
                if (((Comparable) o1.getKey()).compareTo(o2.getKey()) == 0) {
                    return 0;
                }
                return 0;
            }
        };
        arrayOfEntries.sort(c);
        mySet.addAll(arrayOfEntries);
        return mySet;
    }
    private void mySort () {
        sortedKeys=(ArrayList<T>) keys.clone();
        sortedKeys.sort(null);
        for (int i =0;i<sortedKeys.size();i++) {
            int x =keys.indexOf(sortedKeys.get(i));
            sortedValues.add (values.get(x));
        }
    }
    private Map.Entry<T, V> setEntry (T key,V value) {
        return new AbstractMap.SimpleEntry<T, V> (key,value);
    }
    @Override
    public Entry firstEntry() {
        if (size==0) return null;
        return (Entry) this.entrySet().iterator().next();
    }
    @Override
    public Comparable firstKey() {
        if (size==0) return null;
        return (Comparable) firstEntry().getKey() ;
    }

    @Override
    public Entry floorEntry(Comparable key) {
        // TODO Auto-generated method stub
        if(key==null){
            throw new RuntimeErrorException(null);
        }
        Map.Entry<Comparable, V> entry = null;
        Iterator<Map.Entry<Comparable, V>> itr = entrySet().iterator();
        while(itr.hasNext())
        {
            Map.Entry<Comparable, V> temp =itr.next();
            if (key.compareTo(temp.getKey())>=0) {
                entry=temp;
            }
            else {
                break;
            }
        }
        return entry;
    }

    @Override
    public Comparable floorKey(Comparable key) {
        // TODO Auto-generated method stub
        if(key==null){
            throw new RuntimeErrorException(null);
        }
        else {
            return (Comparable) floorEntry(key).getKey();
        }
    }

    @Override
    public Object get(Comparable key) {
        // TODO Auto-generated method stub
        if(key==null){
            throw new RuntimeErrorException(null);
        }
        else {
            Map.Entry<Comparable, V> entry = null;
            Iterator<Map.Entry<Comparable, V>> itr = entrySet().iterator();
            while(itr.hasNext())
            {
                Map.Entry<Comparable, V> temp =itr.next();
                if (key.compareTo(temp.getKey())==0) {
                    entry=temp;
                    ElementEqualKey=temp;
                    break;
                }
            }
            if(entry==null){
                return null;
            }
            else {
                return entry.getValue();
            }
        }
    }

    @Override
    public ArrayList headMap(Comparable toKey) {
        // TODO Auto-generated method stub
        if(toKey==null){
            throw new RuntimeErrorException(null);
        }
        else {
            ArrayList<Map.Entry<Comparable, V>> Output=new ArrayList<Map.Entry<Comparable, V>>();
            Iterator<Map.Entry<Comparable, V>> itr = entrySet().iterator();
            while(itr.hasNext())
            {
                Map.Entry<Comparable, V> temp =itr.next();
                if (toKey.compareTo(temp.getKey())>0) {
                    Output.add(temp);
                }
            }
            return Output;
        }

    }

    @Override
    public ArrayList headMap(Comparable toKey, boolean inclusive) {
        // TODO Auto-generated method stub
        if(!inclusive){
            return headMap(toKey);
        }
        else {
            ArrayList<Map.Entry<Comparable, V>> Output;
            Output=headMap(toKey);
            get(toKey);
            Output.add(ElementEqualKey);
            return Output;
        }
    }

    @Override
    public Set keySet() {
        // TODO Auto-generated method stub
        Set <Comparable> mySet = new LinkedHashSet<>();
        Iterator<Map.Entry<Comparable, V>> itr = entrySet().iterator();
        while(itr.hasNext())
        {
            Map.Entry<Comparable, V> temp =itr.next();
            mySet.add(temp.getKey());
        }
        return mySet;
    }

    @Override
    public Entry lastEntry() {
        // TODO Auto-generated method stub
        Set <Comparable> mySet = new LinkedHashSet<>();
        Iterator<Map.Entry<Comparable, V>> itr = entrySet().iterator();
        if(itr==null){
            return null;
        }
        else {
            Map.Entry<Comparable, V> temp=null;
            while(itr.hasNext())
            {
                temp =itr.next();

            }
            return temp;
        }

    }

    @Override
    public Comparable lastKey() {
        // TODO Auto-generated method stub
        Map.Entry<Comparable, V> Output=lastEntry();
        if(Output==null){
            return null;
        }
        else {
            return Output.getKey();
        }
    }

    @Override
    public Entry pollFirstEntry() {
        // TODO Auto-generated method stub
        Map.Entry<Comparable, V> Output=null;
        Iterator<Map.Entry<Comparable, V>> itr = entrySet().iterator();
        if(itr.hasNext()){
            Output=itr.next();
            remove(Output.getKey());
            return Output;
        }
        else {
            return null;
        }

    }

    @Override
    public Entry pollLastEntry() {
        // TODO Auto-generated method stub
        Map.Entry<Comparable, V> Output=null;
        Iterator<Map.Entry<Comparable, V>> itr = entrySet().iterator();
        if(mySet.size()==0){
            return null;
        }
        else{
            while (itr.hasNext()){
                Output=itr.next();
            }
            remove(Output.getKey());
            return Output;
        }
    }

    @Override
    public void put(Comparable key, Object value) {
        if (key==null||value == null) {
            throw new RuntimeErrorException (null);
        }
        if (!keys.contains(key)) {
            RB.insert(key, value);
            size++;
            keys.add((T) key);
            values.add((V) value);
            arrayOfEntries.add(setEntry((T)key,(V)value));
        }else {
            values.set(keys.indexOf(key), (V) value);
            arrayOfEntries.set(keys.indexOf(key),setEntry((T)key,(V)value));
        }


//          mySet.add(setEntry(sortedKeys.get(i),sortedValues.get(i)));
    }

    @Override
    public void putAll(Map map) {
        if (map==null) {
            throw new RuntimeErrorException (null);
        }
        Iterator<Map.Entry<Comparable, V>> itr = map.entrySet().iterator();
        while(itr.hasNext())
        {
            Map.Entry<Comparable, V> entry = itr.next();
            put(entry.getKey(),entry.getValue());
        }
    }

    @Override
    public boolean remove(Comparable key) {
        boolean remove =RB.delete(key);
        mySort();
        if (remove==true) {
            size--;
            Iterator<Map.Entry<Comparable, V>> itr = entrySet().iterator();

            Map.Entry<Comparable, V> entry = (Entry<Comparable, V>) entrySet().iterator().next();
            int index = sortedKeys.indexOf(key);
            mySet.remove(entry);
            values.remove(keys.indexOf(key));
            sortedValues.remove(keys.indexOf(key));
            arrayOfEntries.remove(index);
            keys.remove(key);
            sortedKeys.remove(key);
            return true;
        }
        return false;
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public Collection values() {
        return values;
    }

}
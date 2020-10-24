package com.plf.common.treemap;

import java.util.Comparator;

@SuppressWarnings("all")
public class TreeMap<K,V> {
    //定义比较器变量
    private final Comparator<? super K> comparator;


    public TreeMap(){
        comparator = null;
    }

    public TreeMap(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }

    //定义根结点
    private Entry<K,V> root;

    //定义集合长度
    private int size;


    //定义内部类表示j键值对
    private class Entry<K,V> {
        K k;
        V v;
        Entry<K,V> left;
        Entry<K,V> right;
        Entry<K,V> parent;

        public Entry(K k,V v,Entry<K,V> left,Entry<K,V> right,Entry<K,V> parent){
            this.k=k;
            this.v=v;
            this.left=left;
            this.right=right;
            this.parent=parent;
        }
    }

    public int size(){
        return size;
    }


    //get()方法
    public V get(K key){
        Entry<K,V> entry = getEntry(key);
        return entry == null ? null : entry.v;
    }

    //根据键获取Entry对象方法
    public Entry<K,V> getEntry(Object key){
        //非空校验
        if(key == null){
            throw new NullPointerException();
        }

        //给根结点起名
        Entry<K,V> t = root;

        //判断有没有传入比较器
        if(comparator != null){
            K k = (K)key;
            while(t != null){
                int cmp = comparator.compare(k,t.k);
                if(cmp<0){
                    t = t.left;
                }else if(cmp>0){
                    t = t.right;
                }else{
                    return t;
                }
            }
        }else{
            Comparable<K> k = (Comparable<K>) key;

            while( t != null){
                int cmp = k.compareTo(t.k);
                if(cmp<0){
                    t = t.left;
                }else if(cmp>0){
                    t = t.right;
                }else{
                    return t;
                }
            }
        }

        //如果找不到
        return null;
    }



    public V put(K key,V value){
        Entry<K,V> t = root;

        if(key == null){
            throw new NullPointerException();
        }

        if(t == null){
            Entry<K,V> entry = new Entry<>(key,value,null,null,null);
            root = entry;
            //集合长度+1
            size++;
            return null;
        }

        //键值对表示新增结点的父结点
        Entry<K,V> parent = t;
        int cmp = 0;
        //键值对表示新增结点的父结点
        if(comparator != null){
            while(t != null){
                cmp = comparator.compare(key,t.k);
                parent = t;
                if(cmp < 0){
                    t = t.left;
                }else if(cmp > 0){
                    t = t.right;
                }else{
                    V v = t.v;
                    t.v = value;
                    return v;
                }
            }
        }else{
			Comparable<? super K> k = (Comparable<? super K>) key;

            while( t != null){
                parent = t;
                cmp = k.compareTo(t.k);
                if(cmp<0){
                    t = t.left;
                }else if(cmp>0){
                    t = t.right;
                }else{
                    V v = t.v;
                    t.v = value;
                    return v;
                }
            }
        }

        Entry<K,V> entry = new Entry<>(key,value,null,null,parent);

        if(cmp<0){
            parent.left = entry;
        }else{
            parent.right = entry;
        }

        size++;

        return null;
    }


    public V remove(K key){
        Entry<K,V> entry = getEntry(key);
        if(entry == null){
            return null;
        }

        if(entry.left == null && entry.right != null){
            if(entry.parent.right == entry){
                entry.parent.right = entry.right;
            }else if(entry.parent.left == entry){
                entry.parent.left = entry.right;
            }else{
                root = entry.right;
            }

            entry.right.parent = entry.parent;
        }else if(entry.left != null && entry.right == null){
            if(entry.parent.right == entry){
                entry.parent.right = entry.left;
            }else if(entry.parent.left == entry){
                entry.parent.left = entry.left;
            }else{
                root = entry.left;
            }

            entry.left.parent = entry.parent;
        }else if(entry.left != null && entry.right != null){

            Entry<K,V> target = entry.right;

            while(target.left != null){
                target  = target.left;
            }

            if(entry.right == target){
                target.parent = entry.parent;
                if(entry.parent.right == entry){
                    entry.parent.right = target;
                }else if(entry.parent.left == entry){
                    entry.parent.left = target;
                }else{
                    root = target;
                }

                entry.left.parent = target;
                target.left = entry.left;

            }else{
                if(target.right == null){
                    target.parent.left = null;
                }else{
                    target.parent.left = target.right;
                    target.right = target.parent;
                }

                if(entry == root){
                    root = target;
                }else if(entry.parent.right == entry){
                    entry.parent.right = target;
                }else if(entry.parent.left == entry){
                    entry.parent.left = target;
                }

                entry.left.parent = target;
                entry.right.parent = target;
                target.left = entry.left;
                target.right = entry.right;
            }
        }else{
            if(entry.parent.right == entry){
                entry.parent.right = null;
            }else if(entry.parent.left == entry){
                entry.parent.left = null;
            }else{
                root = null;
            }
        }

        size--;
        return entry.v;
    }

    public String toString(){
        if(root == null){
            return "{}";
        }
        String s = "{";
        String s1 = method(root);

        s =s + s1.substring(0,s1.length()-1)+"}";
        return s;
    }


    private  String method(Entry<K,V> entry){
        String s="";

        if(entry.left != null){
            s += method(entry.left);
        }

        s += entry.k+"="+entry.v+",";

        if(entry.right != null){
            s += method(entry.right);
        }

        return s;
    }
}

package com.example.country.helpClasses;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class helptest {

    public static void main(String[] args) {
        Set<String> strings = new HashSet<>();

        strings.add("hello");
        strings.add("test");
        strings.add("book");

        Iterator iterator = strings.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        strings.remove("test");

        System.out.println("-------------");

        Iterator iterator2 = strings.iterator();
        while (iterator2.hasNext()) {
            System.out.println(iterator2.next());
        }
    }
}

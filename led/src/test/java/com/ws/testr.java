package com.ws;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class testr {
    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("q", "1");
        map.put("a", "2");
        map.put("b", "3");
        map.put("qv", "4");
        map.put("qe", "5");
        Set set = map.keySet();

        for(Iterator iter = set.iterator(); iter.hasNext();)
        {
            String key = (String)iter.next();
            String value = (String)map.get(key);
            System.out.println(iter.next());
            System.out.println(""+key+":"+value);

        }
//        Iterator iterator = map.entrySet().iterator();
//        boolean b = iterator.hasNext();
//        while (b) {
//            System.out.println(iterator.next());
//        }
    }
}
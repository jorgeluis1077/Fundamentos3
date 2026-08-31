package pruebaarraylist;
// author: Guarista Tanori Jorge Luis
// Date: 2026-08-31

import java.util.ArrayList;

public class PruebaArrayList {

    public static void main(String[] args) {
        ArrayList<String> personalities =
        new ArrayList<String>();
        personalities.add("Ada Lovelace");
        personalities.add("Alan Turing");
        personalities.add("Grace Hopper");
        String name = personalities.get(1);
        System.out.println(name);
        System.out.println("The size of the ArrayList is: "+
        personalities.size());
        personalities.remove(0);
        personalities.set(1, "Margaret Hamilton");
        System.out.println(personalities);
        
        
        
    }
    
}

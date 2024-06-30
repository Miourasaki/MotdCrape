package net.miourasaki.motdcrape.console;

import net.miourasaki.motdcrape.BungeePlugin;

import java.util.logging.Logger;

public class Out {


     static String prefix = "§1MotdCrape> §r";

     public static void send(String o) {
          System.out.println(prefix + o);
     }

     public static void warning(String o) {
          Logger.getLogger("MotdCrape").warning(o);
     }

}

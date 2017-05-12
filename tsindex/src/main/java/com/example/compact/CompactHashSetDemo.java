package com.example.compact;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by xingbowu on 17/5/5.
 */
public class CompactHashSetDemo {
    public static void main(String[] args) {
        // Handle arguments
        if (args.length != 1) {
            System.out.println("Usage: java CompactHashSetDemo regular|compact");
            System.exit(1);
        }
        Set<String> set;
        if (args[0].equals("regular"))
            set = new HashSet<String>();
        else if (args[0].equals("compact"))
            set = new CompactHashSet<String>(TRANSLATOR);
        else
            throw new IllegalArgumentException();

        // Keep adding entries to the set until death by OutOfMemoryError
        Runtime rt = Runtime.getRuntime();
        long lastPrint = 0;
        while (true) {
            String val = Integer.toString(set.size(), 36);
            set.add(val);
            if (System.currentTimeMillis() - lastPrint > 100) {
                System.out.printf("\rCount = %d, Memory = %.2f MiB", set.size(), (rt.totalMemory() - rt.freeMemory()) / 1048576.0);
                lastPrint = System.currentTimeMillis();
            }
        }
    }


    // Serialization format: String s -> [s as bytes in UTF-8].
    private static final CompactSetTranslator<String> TRANSLATOR = new CompactSetTranslator<String>() {

        public byte[] serialize(String s) {
            try {
                return s.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError(e);
            }
        }


        public boolean isInstance(Object obj) {
            return obj instanceof String;
        }


        public int getHash(String s) {
            int state = 0;
            for (int i = 0; i < s.length(); i++) {
                state += s.charAt(i);
                for (int j = 0; j < 4; j++) {
                    state *= 0x7C824F73;
                    state ^= 0x5C12FE83;
                    state = Integer.rotateLeft(state, 5);
                }
            }
            return state;
        }


        public String deserialize(byte[] packed) {
            try {
                return new String(packed, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError(e);
            }
        }

    };
}

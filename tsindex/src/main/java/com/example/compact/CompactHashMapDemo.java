package com.example.compact;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by xingbowu on 17/5/5.
 */
public class CompactHashMapDemo {
    public static void main(String[] args) {
        // Handle arguments
        if (args.length != 1) {
            System.out.println("Usage: java CompactHashMapDemo regular|compact");
            System.exit(1);
        }
        Map<String,Integer> map;
        if (args[0].equals("regular"))
            map = new HashMap<String,Integer>();
        else if (args[0].equals("compact"))
            map = new CompactHashMap<String,Integer>(TRANSLATOR);
        else
            throw new IllegalArgumentException();

        // Keep adding entries to map until death by OutOfMemoryError
        Runtime rt = Runtime.getRuntime();
        Random r = new Random();
        long lastPrint = 0;
        while (true) {
            char[] keyChars = new char[10];
            for (int i = 0; i < keyChars.length; i++)
                keyChars[i] = (char)(r.nextInt(94) + 33);  // Printable ASCII
            String key = new String(keyChars);
            Integer value = r.nextInt();
            map.put(key, value);
            if (System.currentTimeMillis() - lastPrint > 100) {
                System.out.printf("\rCount = %d, Memory = %.2f MiB", map.size(), (rt.totalMemory() - rt.freeMemory()) / 1048576.0);
                lastPrint = System.currentTimeMillis();
            }
        }
    }


    // Serialization format: (String s, int n) -> [s as bytes in UTF-8] + [n as 4 bytes in big endian].
    private static final CompactMapTranslator<String,Integer> TRANSLATOR = new CompactMapTranslator<String,Integer>() {

        public boolean isKeyInstance(Object obj) {
            return obj instanceof String;
        }


        public int getHash(String key) {
            int state = 0;
            for (int i = 0; i < key.length(); i++) {
                state += key.charAt(i);
                for (int j = 0; j < 4; j++) {
                    state *= 0x7C824F73;
                    state ^= 0x5C12FE83;
                    state = Integer.rotateLeft(state, 5);
                }
            }
            return state;
        }


        public byte[] serialize(String key, Integer value) {
            try {
                byte[] packed = key.getBytes("UTF-8");
                int off = packed.length;
                packed = Arrays.copyOf(packed, off + 4);
                int val = value;
                packed[off + 0] = (byte)(val >>> 24);
                packed[off + 1] = (byte)(val >>> 16);
                packed[off + 2] = (byte)(val >>>  8);
                packed[off + 3] = (byte)(val >>>  0);
                return packed;
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError(e);
            }
        }


        public String deserializeKey(byte[] packed) {
            try {
                return new String(packed, 0, packed.length - 4, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError(e);
            }
        }


        public Integer deserializeValue(byte[] packed) {
            int n = packed.length;
            return (packed[n - 1] & 0xFF) | (packed[n - 2] & 0xFF) << 8 | (packed[n - 3] & 0xFF) << 16 | packed[n - 4] << 24;
        }
    };

}

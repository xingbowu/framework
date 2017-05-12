package com.example.compact;

/**
 * Created by xingbowu on 17/5/5.
 */
public interface CompactMapTranslator<K, V> {
    /**
     * Tests whether the specified object is an instance of the key class {@code K}. This method exists
     * because {@code contains()}, {@code get()}, and {@code remove()} take an {@code Object} argument.
     * This must return {@code false} on {@code null}, not throw an exception.
     * @return whether the object is an instance of the key class {@code K}
     */
    public boolean isKeyInstance(Object obj);


    /**
     * Returns the hash code of the specified object. The method exists to make it easy to
     * sidestep poorly distributed hash functions, such as the one in {@code String.hashCode()}.
     * <p>This method must be consistent with the behavior of {@code equals()} - in other words,
     * {@code x.equals(y)} implies {@code translator.getHash(x) == translator.getHash(y)}.</p>
     * <p></p>
     * @param key the object to hash
     * @return the hash code of the object
     * @throws NullPointerException if the key is {@code null}
     */
    public int getHash(K key);


    /**
     * Returns a serialized version of the specified key-value pair as a byte array.
     * The key must be non-{@code null}, but the value may be {@code null} depending on
     * the specific translator's policy.
     * @param key the key object to serialize
     * @param value the value object to serialize
     * @return the key-value pair packed as a byte array
     * @throws NullPointerException if the key is {@code null}
     */
    public byte[] serialize(K key, V value);


    /**
     * Returns a copy of the original key object by deserializing the specified packed array.
     * <p>The result is non-{@code null}. Note that the returned object must be {@code equal()} to
     * the key that was stored, but it does not necessarily refer to the same object.</p>
     * @param packed the packed key-value pair byte array
     * @return a copy of the original key object, which is not {@code null}
     */
    public K deserializeKey(byte[] packed);


    /**
     * Returns a copy of the original value object by deserializing the specified packed array.
     * <p>The result may be {@code null} depending on the specific translator's policy. Note that the
     * returned object must be {@code equal()} to the value that was stored, but it does not necessarily
     * refer to the same object.</p>
     * @param packed the packed key-value pair byte array
     * @return a copy of the original value object
     */
    public V deserializeValue(byte[] packed);

}

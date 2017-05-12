package com.example.compact;

/**
 * Created by xingbowu on 17/5/5.
 */
public interface CompactSetTranslator<E> {
    /**
     * Tests whether the specified object is an instance of the element class {@code E}.
     * This method exists because {@code contains()} and {@code remove()} take an {@code Object} argument.
     * This must return {@code false} on {@code null}, not throw an exception.
     * @return whether the object is an instance of the element class {@code E}
     */
    public boolean isInstance(Object obj);


    /**
     * Returns the hash code of the specified object. The method exists to make it easy to
     * sidestep poorly distributed hash functions, such as the one in {@code String.hashCode()}.
     * <p>This method must be consistent with the behavior of {@code equals()} - in other words,
     * {@code x.equals(y)} implies {@code translator.getHash(x) == translator.getHash(y)}.</p>
     * <p></p>
     * @param obj the object to hash
     * @return the hash code of the object
     * @throws NullPointerException if the object is {@code null}
     */
    public int getHash(E obj);


    /**
     * Returns a serialized version of the specified object as a byte array.
     * The object must be non-{@code null}.
     * @param obj the object to serialize
     * @return the object packed as a byte array
     * @throws NullPointerException if the object is {@code null}
     */
    public byte[] serialize(E obj);


    /**
     * Returns a copy of the original object by deserializing the specified packed array.
     * <p>The result is non-{@code null}. Note that the returned object must be {@code equal()}
     * to the object that was stored, but it does not necessarily refer to the same object.</p>
     * @param packed the packed object byte array
     * @return a copy of the original object, which is not {@code null}
     */
    public E deserialize(byte[] packed);

}

package com.anyoptional.util;

import com.anyoptional.lang.Nullable;

import java.util.Collection;
import java.util.Map;

public abstract class Assert {

    /**
     * Assert a boolean expression, throwing an {@code IllegalStateException}
     * if the expression evaluates to {@code false}.
     * <p>Call {@link #isTrue} if you wish to throw an {@code IllegalArgumentException}
     * on an assertion failure.
     * <pre class="code">Assert.state(id == null, "The id property must not already be initialized");</pre>
     * @param expression a boolean expression
     * @param message the exception message to use if the assertion fails
     * @throws IllegalStateException if {@code expression} is {@code false}
     */
    public static void state(boolean expression, String message, Object... args) {
        if (!expression) {
            throw new IllegalStateException(MessageFormatter.arrayFormat(message, args).first);
        }
    }

    /**
     * Assert a boolean expression, throwing an {@code IllegalArgumentException}
     * if the expression evaluates to {@code false}.
     * <pre class="code">Assert.isTrue(i &gt; 0, "The value must be greater than zero");</pre>
     * @param expression a boolean expression
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if {@code expression} is {@code false}
     */
    public static void isTrue(boolean expression, String message, Object... args) {
        if (!expression) {
            throw new IllegalArgumentException(MessageFormatter.arrayFormat(message, args).first);
        }
    }

    /**
     * Assert that an object is {@code null}.
     * <pre class="code">Assert.isNull(value, "The value must be null");</pre>
     * @param object the object to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object is not {@code null}
     */
    public static void isNull(@Nullable Object object, String message, Object... args) {
        if (object != null) {
            throw new IllegalArgumentException(MessageFormatter.arrayFormat(message, args).first);
        }
    }

    /**
     * Assert that an object is not {@code null}.
     * <pre class="code">Assert.notNull(clazz, "The class must not be null");</pre>
     * @param object the object to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object is {@code null}
     */
    public static void notNull(@Nullable Object object, String message, Object... args) {
        if (object == null) {
            throw new IllegalArgumentException(MessageFormatter.arrayFormat(message, args).first);
        }
    }

    /**
     * Assert that an array contains elements; that is, it must not be
     * {@code null} and must contain at least one element.
     * <pre class="code">Assert.notEmpty(array, "The array must contain elements");</pre>
     * @param array the array to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object array is {@code null} or contains no elements
     */
    public static void notEmpty(@Nullable Object[] array, String message, Object... args) {
        if (ObjectUtils.isEmpty(array)) {
            throw new IllegalArgumentException(MessageFormatter.arrayFormat(message, args).first);
        }
    }

    /**
     * Assert that an array contains no {@code null} elements.
     * <p>Note: Does not complain if the array is empty!
     * <pre class="code">Assert.noNullElements(array, "The array must contain non-null elements");</pre>
     * @param array the array to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object array contains a {@code null} element
     */
    public static void noNullElements(@Nullable Object[] array, String message, Object... args) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new IllegalArgumentException(MessageFormatter.arrayFormat(message, args).first);
                }
            }
        }
    }

    /**
     * Assert that a collection contains elements; that is, it must not be
     * {@code null} and must contain at least one element.
     * <pre class="code">Assert.notEmpty(collection, "Collection must contain elements");</pre>
     * @param collection the collection to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the collection is {@code null} or
     * contains no elements
     */
    public static void notEmpty(@Nullable Collection<?> collection, String message, Object... args) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new IllegalArgumentException(MessageFormatter.arrayFormat(message, args).first);
        }
    }

    /**
     * Assert that a Map contains entries; that is, it must not be {@code null}
     * and must contain at least one entry.
     * <pre class="code">Assert.notEmpty(map, "Map must contain entries");</pre>
     * @param map the map to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the map is {@code null} or contains no entries
     */
    public static void notEmpty(@Nullable Map<?, ?> map, String message, Object... args) {
        if (CollectionUtils.isEmpty(map)) {
            throw new IllegalArgumentException(MessageFormatter.arrayFormat(message, args).first);
        }
    }

}

package mail.api.serial;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface DataStructure {

    boolean isPresent();

    void ifPresent(Consumer<DataStructure> consumer);

    DataStructure getChild(String name);

    List<DataStructure> getChildren(String name);

    DataStructureStream streamChildren(String name);

    DataElement get(String name);

    List<DataElement> getAll(String name);

    default Stream<DataElement> streamAll(String name) {
        return getAll(name).stream();
    }

    <T> T get(String name, Class<T> type) throws NullPointerException;

    <T> T orElse(String name, Class<T> type, T other);

    <T> T orElseGet(String name, Class<T> type, Supplier<T> supplier);

    <T, E extends Throwable> T orElseThrow(String name, Class<T> type, Supplier<E> supplier) throws E;

    <T> List<T> getAll(String name, Class<T> type);

    default <T> Stream<T> streamAll(String name, Class<T> type) {
        return getAll(name, type).stream();
    }

    interface DataElement {

        boolean isPresent();

        void ifPresent(Consumer<DataElement> consumer);

        <T> Optional<T> map(Function<DataElement, T> mapper);

        <T> Optional<T> flatMap(Function<DataElement, Optional<T>> mapper);

        <T> Optional<T> map(Class<T> type);

        <T> T as(Class<T> type) throws NullPointerException;

        <T> T orElse(Class<T> type, T other);

        <T> T orElseGet(Class<T> type, Supplier<T> supplier);

        <T, E extends Throwable> T orElseThrow(Class<T> type, Supplier<E> supplier) throws E;

    }

    interface DataStructureStream extends Stream<DataStructure> {

        DataStructureStream getChild(String name);

        Stream<List<DataStructure>> getChildren(String name);

        DataStructureStream streamChildren(String name);

        Stream<DataElement> get(String name);

        Stream<List<DataElement>> getAll(String name);

        default Stream<DataElement> streamAll(String name) {
            return getAll(name).flatMap(List::stream);
        }

        <T> Stream<T> get(String name, Class<T> type);

        <T> Stream<List<T>> getAll(String name, Class<T> type);

        default <T> Stream<T> streamAll(String name, Class<T> type) {
            return getAll(name, type).flatMap(List::stream);
        }

    }

}

package com.example.webfluxconvertfluxtocollectionormap;

import com.example.webfluxconvertfluxtocollectionormap.model.Person;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@SpringBootApplication
public class WebfluxConvertFluxToCollectionOrMapApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxConvertFluxToCollectionOrMapApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var peopleF = Flux.just(
                new Person("ADAM", 20),
                new Person("EWA", 30),
                new Person("IZA", 40),
                new Person("ZOSIA", 50),
                new Person("ADAM", 50)
        );

        // -------------------------------------------------------------------------------------------------
        // ---------------------------------------------- 1 ------------------------------------------------
        // -------------------------------------------------------------------------------------------------
        // collectList()
        // Flux<T> -> Mono<List<T>>
        /*peopleF
                .collectList()
                .map(people -> people.stream().map(Person::name).toList())
                .subscribe(System.out::println);*/

        // -------------------------------------------------------------------------------------------------
        // ---------------------------------------------- 2 ------------------------------------------------
        // -------------------------------------------------------------------------------------------------
        // collectSortedList()
        // Flux<T> -> sorted Mono<List<T>>
        /*peopleF
                .collectSortedList(Comparator.comparing(Person::age, Comparator.reverseOrder()))
                .subscribe(System.out::println);*/

        // -------------------------------------------------------------------------------------------------
        // ---------------------------------------------- 3 ------------------------------------------------
        // -------------------------------------------------------------------------------------------------
        // collectMap()
        // Flux<T> -> Mono<Map<K, V>>
        // Kiedy klucz się powtórzy, wartość w mapie wynikowej będzie aktualizowana / nadpisana.
        // Dodanie trzeciego argumentu pozwoli określić rodzaj mapy, która ma powstać
        /*peopleF
                .collectMap(Person::name, Person::age, LinkedHashMap::new)
                .subscribe(System.out::println);*/

        // -------------------------------------------------------------------------------------------------
        // ---------------------------------------------- 4 ------------------------------------------------
        // -------------------------------------------------------------------------------------------------
        // collectMultiMap()
        // Flux<T> -> Mono<Map<K, Collection<V>>>
        // Kiedy klucz się powtórzy wartość zostanie dodana do powiązanej z nim listy
        /*peopleF
                .collectMultimap(Person::name, Person::age)
                .subscribe(System.out::println);*/

        // -------------------------------------------------------------------------------------------------
        // ---------------------------------------------- 5 ------------------------------------------------
        // -------------------------------------------------------------------------------------------------
        // collect()
        // Flux<T> -> Mono<E>
        peopleF
                .collect(Collectors.collectingAndThen(
                        Collectors.mapping(Person::name, Collectors.toList()),
                        names -> String.join(", ", names)))
                .subscribe(System.out::println);
    }
}

package com.example.demo;


import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class DojoStreamTest {

    @Test
    void converterData(){
        List<Player> list = CsvUtilFile.getPlayers();
        assert list.size() == 18207;
    }

    @Test
    void jugadoresMayoresA35(){
        List<Player> list = CsvUtilFile.getPlayers();
        Set<Player> result = list.stream()
                .filter(jugador -> jugador.getAge() > 35)
                .collect(Collectors.toSet());
        result.forEach(System.out::println);
    }

    @Test
    void jugadoresMayoresA35SegunClub(){
        List<Player> list = CsvUtilFile.getPlayers();
        Map<String, List<Player>> result = list.stream().filter(player -> player.getAge() > 35)
                .distinct()
                .collect(Collectors.groupingBy(Player::getClub));
        System.out.println(result);
    }

    @Test
    void mejorJugadorConNacionalidadFrancia(){
        List<Player> list = CsvUtilFile.getPlayers();
        list.stream()
                .filter(jugador -> jugador.getNational().equals("France"))
                .reduce((p1, p2) -> p1.getWinners() > p2.getWinners()?p1:p2).ifPresent(System.out::println);
    }


    @Test
    void clubsAgrupadosPorNacionalidad(){
        List<Player> list = CsvUtilFile.getPlayers();
        Map<String, Set<String>> result = list.stream().collect(Collectors.groupingBy(
                Player::getNational,
                HashMap::new,
                Collectors.mapping(
                        Player::getClub,
                        Collectors.toSet()
                )
        ));
        result.forEach((key, value) -> {
            System.out.println(key + ": \n");
            System.out.println(value);
            System.out.println("\n");
        });
    }

    @Test
    void clubConElMejorJugador(){
        List<Player> list = CsvUtilFile.getPlayers();
        list.stream().reduce((p1, p2) -> p1.getWinners() > p2.getWinners()?p1:p2)
                .ifPresent(player -> System.out.println("Club con mejor jugador: " + player.getClub()));
    }

    @Test
    void ElMejorJugador(){
        List<Player> list = CsvUtilFile.getPlayers();
        list.stream().reduce((p1, p2) -> p1.getWinners() > p2.getWinners()?p1:p2)
                .ifPresent(System.out::println);
    }

    @Test
    void mejorJugadorSegunNacionalidad(){
        List<Player> list = CsvUtilFile.getPlayers();
        Map<String, Player> result = list.stream().collect(Collectors.toMap(
                Player::getNational,
                player -> player,
                (current, newValue)->((current.getWinners()/current.getGames()) >
                        (newValue.getWinners()/ newValue.getGames())
                        ?current
                        :newValue)));

        result.forEach((key, value) -> {
            System.out.println(key + "\n");
            System.out.println(value + "\n");
        });
    }


}

package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {

    List<String> ids;

    final int number_pokemons = 5;

    Pokemon[] pokemons;

    public Player() {
        ids = new ArrayList<>();
        for (int id = 1; id <= 20; id++) {
            ids.add(String.valueOf(id));
        }
        pokemons = new Pokemon[number_pokemons];
    }

    public void instantiatePokemons() {
        for (int i = 0; i < number_pokemons; i++) {
            instantiatePokemon(i);
        }
    }

    void instantiatePokemon(int pos) {
        Random rand = new Random();
        int n;
        do {
            n = rand.nextInt(20) + 1;
        } while (!ids.contains(String.valueOf(n)));
        ids.remove(String.valueOf(n));
        pokemons[pos] = new Pokemon(n);
    }
}

package org.fiit.votefilm.util;

import org.fiit.votefilm.dto.MovieResultTMDB;

import java.util.AbstractList;

public class FilmResultList extends AbstractList<MovieResultTMDB> {
    private Object[] elements;
    private int size = 0;

    public FilmResultList() {
        this(10);
    }

    public FilmResultList(int initialCapacity) {
        elements = new Object[initialCapacity];
    }

    @Override
    public MovieResultTMDB get(int index) {
        return (MovieResultTMDB) elements[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public MovieResultTMDB set(int index, MovieResultTMDB element) {
        MovieResultTMDB oldValue = get(index);
        elements[index] = element;
        return oldValue;
    }

    @Override
    public boolean add(MovieResultTMDB e) {
        if (size == elements.length) {
            Object[] bigger = new Object[elements.length * 2];
            System.arraycopy(elements, 0, bigger, 0, size);
            elements = bigger;
        }
        elements[size] = e;
        size++;
        return true;
    }

    public MovieResultTMDB getBestFilm() {
        MovieResultTMDB bestFilm = get(0);
        for (int i = 1; i < size(); i++) {
            if (get(i).getVoteAverage() > bestFilm.getVoteAverage()) {
                bestFilm = get(i);
            }
        }
        return bestFilm;
    }
}

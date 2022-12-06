package me.samcefalo.sqlcache.cache.options;

import me.samcefalo.sqlcache.dao.DAO;

public class MapLoader<I, T> implements org.redisson.api.map.MapLoader<I, T> {

    private final DAO<T, I> dao;

    public MapLoader(DAO dao) {
        this.dao = dao;
    }

    @Override
    public T load(I s) {
        return dao.getById(s);
    }

    @Override
    public Iterable<I> loadAllKeys() {
        return null;
    }

}

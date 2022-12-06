package me.samcefalo.sqlcache.cache.options;

import me.samcefalo.sqlcache.dao.DAO;

import java.util.Collection;
import java.util.Map;

public class MapWriter<I, T> implements org.redisson.api.map.MapWriter<I, T> {

    private final DAO<T, I> dao;

    public MapWriter(DAO dao) {
        this.dao = dao;
    }

    @Override
    public void write(Map<I, T> map) {
        for (Map.Entry<I, T> entry : map.entrySet()) {
            dao.insertOrUpdate(entry.getValue());
        }
    }

    @Override
    public void delete(Collection<I> collection) {
        for (I key : collection) {
            dao.delete(key);
        }
    }

}

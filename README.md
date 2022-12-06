# Redis Cache
Database interface using redis

![Alt text](/image.png?raw=true "Diagram")

## Install & Connect redis with WSL2
curl -fsSL https://packages.redis.io/gpg | sudo gpg --dearmor -o /usr/share/keyrings/redis-archive-keyring.gpg

echo "deb [signed-by=/usr/share/keyrings/redis-archive-keyring.gpg] https://packages.redis.io/deb $(lsb_release -cs) main" | sudo tee /etc/apt/sources.list.d/redis.list

sudo apt-get update

sudo apt-get install redis

### Connect
sudo service redis-server start

redis-cli 

### API

Create Entity
```java
@Data
@Builder
public class Car implements Serializable {

    private UUID id;
    private String name;
    private String age;

}
```

Create a Car DAO implementing DAO<Entity Class, Pk Class>
```java
public class CarDAO implements DAO<Car, UUID> {

    public CarDAO(DataSource dataSource) {
    }

    @Override
    public Car getById(UUID id) {
      
    }

    @Override
    public void insert(Car car) {
        
    }

    @Override
    public void insertOrUpdate(Car car) {
        
    }

    @Override
    public void update(Car car) {

    }

    @Override
    public void delete(UUID id) {

    }
}
```

Create a Cache implementing Cache<Entity Class, Pk Class>
In my case i'm using Redisson integrated with my DAO class
```java
public abstract class RedissonCache<T, I> implements Cache<T, I> {

    @Getter
    private RMapCache<I, T> map;
    @Getter
    private final String name;
    private final RedissonClient redisson;
    private final DAO dao;

    public RedissonCache(RedissonProvider redissonProvider, DAO dao, String name) {
        this.name = name;
        this.redisson = redissonProvider.getRedisson();
        this.dao = dao;
        this.configureMap(new WriterOptions());
    }

    public void configureMap(WriterOptions writerOptions) {
        this.map = redisson.getMapCache(this.name, MapOptions.<I, T>defaults()
                .loader(new MapLoader(dao))
                .writer(new MapWriter(dao))
                .writeMode(writerOptions.getWriteMode())
                .writeBehindDelay(writerOptions.getWriteBehindDelay())
                .writeBehindBatchSize(writerOptions.getWriteBehindBatchSize()));
    }

    @Override
    public void setOrUpdate(I key, T value) {
        this.setOrUpdate(key, value, 600, TimeUnit.SECONDS);
    }

    public void setOrUpdate(I key, T value, int time, TimeUnit timeUnit) {
        map.put(key, value, time, timeUnit);
    }

    @Override
    public Future<T> get(I key) {
        return map.getAsync(key);
    }

    @Override
    public void delete(I key) {
        map.removeAsync(key);
    }

    @Override
    public void clear() {
        map.clear();
    }

}
```

MapLoader Class
```java
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
```

MapWriter Class
```java
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
```

Car Cache using RedissonCache
```java
public class CarCache extends RedissonCache<Car, UUID> {

    public CarCache(RedissonProvider redissonProvider, DAO<Car, UUID> dao) {
        super(redissonProvider, dao, "carMap");
    }

}
```

RedissonProvider Class
```java
public class RedissonProvider implements RedisProvider {

    @Getter
    private final RedissonClient redisson;

    public RedissonProvider(String url, int port) {
        Config config = new Config();
        config.useSingleServer()
                // use "rediss://" for SSL connection
                .setAddress("redis://" + url + ":" + port);

        this.redisson = Redisson.create(config);
    }

    public RedissonProvider(Config config) {
        this.redisson = Redisson.create(config);
    }

    @Override
    public void close() {

    }

}
```


finally you can create a service and use the Cache by implementing Service<Entity Class, Pk Class>
```java
public class CarService implements Service<Car, UUID> {

    private final Cache<Car, UUID> cache;

    public CarService(DataSource dataSource) {
        this.cache = new CarCache(RedisFactory.RedissonProviderInstance(), new CarDAO(dataSource));
    }

    @Override
    public Future<Car> getById(UUID id) {
        return this.cache.get(id);
    }

    @Override
    public void insert(Car car) {
        this.cache.setOrUpdate(car.getId(), car);
    }

    @Override
    public void update(Car car) {
        this.cache.setOrUpdate(car.getId(), car);
    }

    @Override
    public void delete(UUID id) {
        this.cache.delete(id);
    }

}
```



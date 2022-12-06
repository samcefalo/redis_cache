package me.samcefalo.sqlcache.cache.options;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.redisson.api.MapOptions;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WriterOptions {

    private MapOptions.WriteMode writeMode;
    private int writeBehindDelay;
    private int writeBehindBatchSize;

}

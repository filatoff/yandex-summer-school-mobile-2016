package ru.yandex.summerschool2016.filatovaa.data.cache;

import android.content.Context;

import ru.yandex.summerschool2016.filatovaa.data.cache.serializer.JsonSerializer;
import ru.yandex.summerschool2016.filatovaa.data.entity.ArtistEntity;
import ru.yandex.summerschool2016.filatovaa.data.exception.ArtistsNotFoundException;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.yandex.summerschool2016.filatovaa.data.utils.FileManager;
import ru.yandex.summerschool2016.filatovaa.data.utils.SharedPreferences;
import ru.yandex.summerschool2016.filatovaa.domain.executor.ThreadExecutor;
import rx.Observable;

/**
 * {@link ArtistCache} implementation.
 */
@Singleton
public class ArtistCacheImpl implements ArtistCache {

    private static final String CACHE_FILE_NAME = "artists";
    private static final long CACHE_EXPIRATION_TIME_MILLIS = 1000 * 60 * 60;

    private final Context context;
    private final File cacheDir;
    private final JsonSerializer serializer;
    private final FileManager fileManager;
    private final SharedPreferences sharedPreferences;
    private final ThreadExecutor threadExecutor;

    /**
     * Constructor of the class {@link ArtistCacheImpl}.
     *
     * @param context A
     * @param artistCacheSerializer {@link JsonSerializer} for object serialization.
     * @param fileManager {@link FileManager} for saving serialized objects to the file system.
     */
    @Inject
    public ArtistCacheImpl(Context context, JsonSerializer artistCacheSerializer,
                           FileManager fileManager, SharedPreferences sharedPreferences,
                           ThreadExecutor executor) {
        if (context == null || artistCacheSerializer == null || fileManager == null || executor == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.context = context.getApplicationContext();
        this.cacheDir = this.context.getCacheDir();
        this.serializer = artistCacheSerializer;
        this.fileManager = fileManager;
        this.sharedPreferences = sharedPreferences;
        this.threadExecutor = executor;
    }

    /**
     * Gets an {@link rx.Observable} which will emit a list of {@link ArtistEntity}.
     */
    @Override
    public Observable<List<ArtistEntity>> get() {
        return Observable.create(subscriber -> {
            File artistEntitiesFile = ArtistCacheImpl.this.buildFile();
            String fileContent = ArtistCacheImpl.this.fileManager.readFileContent(artistEntitiesFile);
            List<ArtistEntity> artistEntities = ArtistCacheImpl.this.serializer.deserialize(fileContent);

            if (artistEntities != null) {
                subscriber.onNext(artistEntities);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new ArtistsNotFoundException());
            }
        });
    }

    /**
     * Puts and element into the cache.
     *
     * @param artistEntities Collection to insert in the cache.
     */
    @Override
    public void put(List<ArtistEntity> artistEntities) {
        if (artistEntities != null) {
            File artistEntitiesFile = this.buildFile();
            if (!isCached()) {
                String jsonString = this.serializer.serialize(artistEntities);
                this.executeAsynchronously(new CacheWriter(this.fileManager, artistEntitiesFile, jsonString));
                setLastCacheUpdateTimeMillis();
            }
        }
    }

    /**
     * Checks if a collection (Artist) exists in the cache.
     *
     * @return true if the collection is cached, otherwise false.
     */
    @Override
    public boolean isCached() {
        File userEntitiesFile = this.buildFile();
        return this.fileManager.exists(userEntitiesFile);
    }

    /**
     * Checks if the cache is expired.
     *
     * @return true, the cache is expired, otherwise false.
     */
    @Override
    public boolean isExpired() {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

        boolean expired = ((currentTime - lastUpdateTime) > CACHE_EXPIRATION_TIME_MILLIS);

        if (expired) {
            this.evictAll();
        }

        return expired;
    }

    /**
     * Evict all elements of the cache.
     */
    @Override
    public void evictAll() {
        this.executeAsynchronously(new CacheEvictor(this.fileManager, this.cacheDir));
    }

    /**
     * Build a file, used to be inserted in the disk cache.
     *
     * @return A valid file.
     */
    private File buildFile() {
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(this.cacheDir.getPath());
        fileNameBuilder.append(File.separator);
        fileNameBuilder.append(CACHE_FILE_NAME);

        return new File(fileNameBuilder.toString());
    }

    /**
     * Set in millis, the last time the cache was accessed.
     */
    private void setLastCacheUpdateTimeMillis() {
        this.sharedPreferences.putLong(SharedPreferences.SP_KEY_CACHE_LAST_UPDATE,
                System.currentTimeMillis());
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private long getLastCacheUpdateTimeMillis() {
        return this.sharedPreferences.getLong(SharedPreferences.SP_KEY_CACHE_LAST_UPDATE);
    }

    /**
     * Executes a {@link Runnable} in another Thread.
     *
     * @param runnable {@link Runnable} to execute
     */
    private void executeAsynchronously(Runnable runnable) {
        this.threadExecutor.execute(runnable);
    }

    /**
     * {@link Runnable} class for writing to disk.
     */
    private static class CacheWriter implements Runnable {
        private final FileManager fileManager;
        private final File fileToWrite;
        private final String fileContent;

        CacheWriter(FileManager fileManager, File fileToWrite, String fileContent) {
            this.fileManager = fileManager;
            this.fileToWrite = fileToWrite;
            this.fileContent = fileContent;
        }

        @Override
        public void run() {
            this.fileManager.writeToFile(fileToWrite, fileContent);
        }
    }

    /**
     * {@link Runnable} class for evicting all the cached files
     */
    private static class CacheEvictor implements Runnable {
        private final FileManager fileManager;
        private final File cacheDir;

        CacheEvictor(FileManager fileManager, File cacheDir) {
            this.fileManager = fileManager;
            this.cacheDir = cacheDir;
        }

        @Override
        public void run() {
            this.fileManager.clearDirectory(this.cacheDir);
        }
    }
}

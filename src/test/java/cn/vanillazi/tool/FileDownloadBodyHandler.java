package cn.vanillazi.tool;

import jdk.internal.net.http.common.Utils;

import java.io.FilePermission;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

// Similar to Path body handler, but for file download.
public class FileDownloadBodyHandler implements HttpResponse.BodyHandler<Path> {
    private final Path directory;
    private final List<OpenOption> openOptions;
    // may be null

    /**
     * Factory for creating FileDownloadBodyHandler.
     *
     * Permission checks are performed here before construction of the
     * FileDownloadBodyHandler. Permission checking and construction are
     * deliberately and tightly co-located.
     */
    public static FileDownloadBodyHandler create(Path directory,List<OpenOption> openOptions) {
        // existence, etc, checks must be after permission checks
        if (Files.notExists(directory))
            throw new IllegalArgumentException("non-existent directory: " + directory);
        if (!Files.isDirectory(directory))
            throw new IllegalArgumentException("not a directory: " + directory);
        if (!Files.isWritable(directory))
            throw new IllegalArgumentException("non-writable directory: " + directory);
        return new FileDownloadBodyHandler(directory, openOptions);
    }

    private FileDownloadBodyHandler(Path directory,
                                    List<OpenOption> openOptions) {
        this.directory = directory;
        this.openOptions = openOptions;
    }

    /** The "attachment" disposition-type and separator. */
    static final String DISPOSITION_TYPE = "attachment;";

    /** The "filename" parameter. */
    static final Pattern FILENAME = Pattern.compile("filename\\s*=", CASE_INSENSITIVE);

    static final List<String> PROHIBITED = List.of(".", "..", "", "~" , "|");

    static final UncheckedIOException unchecked(HttpResponse.ResponseInfo rinfo,
                                                String msg) {
        String s = String.format("%s in response [%d, %s]", msg, rinfo.statusCode(), rinfo.headers());
        return new UncheckedIOException(new IOException(s));
    }

    @Override
    public HttpResponse.BodySubscriber<Path> apply(HttpResponse.ResponseInfo responseInfo) {

        Path file = Paths.get(directory.toString());

        return  null;
    }

    static class  PathBodySubscriber implements HttpResponse.BodySubscriber<Path> {
        private static final FilePermission[] EMPTY_FILE_PERMISSIONS = new FilePermission[0];
        private final Path file;
        private final OpenOption[] options;

        private final AtomicBoolean subscribed = new AtomicBoolean();
        private volatile Flow.Subscription subscription;
        private volatile FileChannel out;

        PathBodySubscriber(Path file, OpenOption[] options) {
            this.file = file;
            this.options = options;
        }


        @Override
        public CompletionStage<Path> getBody() {
            return null;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            Objects.requireNonNull(subscription);
            if (!subscribed.compareAndSet(false, true)) {
                subscription.cancel();
                return;
            }

            try {
                out = FileChannel.open(file, options);
            } catch (IOException ioe) {
                result.completeExceptionally(ioe);
                subscription.cancel();
                return;
            }

            subscription.request(1);

        }
            @Override
            public void onNext (List < ByteBuffer > items) {
                try {
                    out.write(items.toArray(new ByteBuffer[0]));
                } catch (IOException ex) {
                    close();
                    subscription.cancel();
                    result.completeExceptionally(ex);
                }
                subscription.request(1);
            }

            @Override
            public void onError (Throwable throwable){

            }

            @Override
            public void onComplete() {

            }

        private void close() {
            try {
                out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

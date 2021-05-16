package zti.reactor.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Mono;

@Slf4j
public class MonoTest {

    @Test
    public void firstFunction(){
        log.info("Hello world!");
    }

    @Test
    public void monoSubscriber(){
        String name = "Jan Kowalski";
        Mono<String> mono = Mono.just(name)
                .log();

        mono.subscribe();
        log.info("Mono {}", mono);
        log.info("Everything works fine.");
    }

    @Test
    public void subscriberConsumer(){
        String name = "Jan Kowalski";
        Mono<String> mono = Mono.just(name)
                .log();

        mono.subscribe(str -> log.info("Wartość {}", str));
        log.info("Mono {}", mono);
    }

    @Test
    public void subscriberConsumerWithError(){
        String name = "Jan Kowalski";
        Mono<String> mono = Mono.just(name)
                .map(s -> {throw new RuntimeException("Błąd");});

        mono.subscribe(str -> log.info("Imie {}", str), str -> log.error("Coś poszło nie tak"));
    }

    @Test
    public void subscriberConsumerComplete(){
        String name = "Jan Kowalski";
        Mono<String> mono = Mono.just(name)
                .log()
                .map(String::toUpperCase);

        mono.subscribe(str -> log.info("Wartość {}", str),
                Throwable::printStackTrace,
                () -> log.info("KONIEC"));
        log.info("Mono {}", mono);
    }

    @Test
    public void subscriberConsumerSubscription(){
        String name = "Jan Kowalski";
        Mono<String> mono = Mono.just(name)
                .log()
                .map(String::toUpperCase);

        mono.subscribe(str -> log.info("Wartość {}", str),
                Throwable::printStackTrace,
                () -> log.info("KONIEC"),
                Subscription::cancel);
    }

    @Test
    public void monoMethods(){
        String name = "Jan Kowalski";
        Mono<String> mono = Mono.just(name)
                .log()
                .map(String::toUpperCase)
                .doOnSubscribe(subscription -> log.info("Subscribed"))
                .doOnRequest(number -> log.info("Otrzymano request"))
                .doOnNext(str -> log.info("Wartość jest {}", str))
                .doOnSuccess(s -> log.info("onSuccess"));

        mono.subscribe(str -> log.info("Wartość {}", str),
                Throwable::printStackTrace,
                () -> log.info("KONIEC"));
    }
}

package zti.reactor.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import java.time.Duration;

@Slf4j
public class FluxTest {

    @Test
    public void fluxSubsriber(){
        Flux<String> fluxNames = Flux.just("Jan", "Anna", "Klaudia", "Janusz", "Wiktor")
                .log();

        fluxNames.subscribe();
    }

    @Test
    public void fluxSubsriberInt(){
        Flux<Integer> fluxNumbers = Flux.range(5, 11)
                .log();

        fluxNumbers.subscribe(n -> log.info("Numer: {}", n));
    }


    @Test
    public void fluxSubsriberError(){
        Flux<Integer> fluxNumbersList = Flux.range(5, 11)
                .log()
                .map(i -> {
                    if(i == 10)
                        throw new IndexOutOfBoundsException("Zły indeks");
                    return i;
                });

        fluxNumbersList.subscribe(n -> log.info("Numer - {}", n),
                Throwable::printStackTrace,
                () -> log.info("KONIEC"),
                subscription -> subscription.request(5));
    }

    @Test
    public void fluxSubsriberBackpressure(){
        Flux<Integer> fluxNumbersList = Flux.range(5, 11)
                .log();

        fluxNumbersList.subscribe(new Subscriber<Integer>() {

            private int count = 0;
            private Subscription subscription;
            private final int requestCount = 2;

            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                subscription.request(2);
            }

            @Override
            public void onNext(Integer integer) {
                count++;
                if(count >= requestCount){
                    count = 0;
                    subscription.request(requestCount);
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Test
    public void fluxSubsriberBackpressure2(){
        Flux<Integer> fluxNumbersList = Flux.range(5, 11)
                .log();

        fluxNumbersList.subscribe(new BaseSubscriber<Integer>() {

            private int count = 0;
            private Subscription subscription;
            private final int requestCount = 2;

            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(requestCount);
            }

            @Override
            protected void hookOnNext(Integer value) {
                count++;
                if(count >= requestCount){
                    count = 0;
                    request(requestCount);
                }
            }
        });
    }

    @Test
    public void intervalFlux() throws Exception{
        Flux<Long> interval = Flux.interval(Duration.ofMillis(100))
                .take(10)
                .log();

        interval.subscribe(i -> log.info("Number {}", i));

        Thread.sleep(3000);
    }

    @Test
    public void concatWithValue() {
        Flux<String> str1 = Flux.just("ab", "cd");
        Flux<String> str2 = Flux.just("ef", "gh");

        Flux<String> concatString = str1.concatWith(str2).log();
        concatString.subscribe(System.out::println);
    }
}

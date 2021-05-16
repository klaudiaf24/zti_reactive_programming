package examples;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class Launcher1 {
	public static void main(String[] args) {
		System.out.println("Meotda tworzenia - just");
		Observable<String> myStringsJust = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
		myStringsJust.subscribe(s -> System.out.println(s));

		System.out.println();
		System.out.println("Meotda tworzenia - intervale");
		Observable<Long> secondIntervals = Observable.interval(1, TimeUnit.SECONDS);
		secondIntervals.subscribe(s -> System.out.println(s + " Mississippi"));
		sleep(5000);

		System.out.println();
		System.out.println("Meotda tworzenia - create");
		Observable<String> source = Observable.create(emitter -> {
			try {
				emitter.onNext("Alpha");
				emitter.onNext("Beta");
				emitter.onNext("Gamma");
				emitter.onNext("Delta");
				emitter.onNext("Epsilon");
				emitter.onComplete();
			} catch (Throwable e) {
				emitter.onError(e);
			}
		});
		source.subscribe(s -> System.out.println(s), Throwable::printStackTrace);

		System.out.println();
		System.out.println("Meotda tworzenia - range");
		Observable.range(5, 11).subscribe(s -> System.out.println(s));

		System.out.println();
		System.out.println("Meotda tworzenia - empty");
		Observable<String> empty = Observable.empty();
		empty.subscribe(System.out::println, Throwable::printStackTrace, () -> System.out.println("Empty - Done!"));
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

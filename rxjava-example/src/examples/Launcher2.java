package examples;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class Launcher2 {
	public static void main(String[] args) {
		System.out.println("Podstawowe operatory -  filter");
		Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon").filter(s -> s.length() != 5)
				.subscribe(s -> System.out.println(s));

		System.out.println();
		System.out.println("Podstawowe operatory -  take");
		Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon").take(3).subscribe(s -> System.out.println(s));

		System.out.println();
		System.out.println("Podstawowe operatory -  take z intervalem");
		Observable.interval(300, TimeUnit.MILLISECONDS).take(2, TimeUnit.SECONDS).subscribe(i -> System.out.println(i));
		sleep(5000);

		System.out.println();
		System.out.println("Podstawowe operatory -  skip");
		Observable.range(1, 100).skip(90).subscribe(i -> System.out.println(i));

		System.out.println();
		System.out.println("Podstawowe operatory -  elementAt");
		Observable.just("Alpha", "Beta", "Zeta", "Eta", "Gamma", "Delta").elementAt(3)
				.subscribe(i -> System.out.println(i));
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
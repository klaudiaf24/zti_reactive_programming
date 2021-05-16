package examples;

import io.reactivex.Observable;

public class Launcher4 {

	public static void main(String[] args) {
		System.out.println("Operatory redukcyjne -  count");
		Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon").count().subscribe(s -> System.out.println(s));

		System.out.println();
		System.out.println("Operatory redukcyjne -  reduce");
		Observable.just(5, 3, 7, 10, 2, 14).reduce((total, next) -> total + next).subscribe(s -> System.out.println(s));

		System.out.println();
		System.out.println("Operatory redukcyjne -  contains");
		Observable.range(1, 10000).contains(9563).subscribe(s -> System.out.println(s));

		System.out.println();
		System.out.println("Operatory redukcyjne -  all");
		Observable.just(5, 3, 7, 11, 2, 14).all(i -> i < 10).subscribe(s -> System.out.println(s));
	}
}

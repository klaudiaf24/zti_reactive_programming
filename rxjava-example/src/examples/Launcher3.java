package examples;

import io.reactivex.Observable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Launcher3 {

	public static void main(String[] args) {
		System.out.println("Operatory zmiany -  map");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy");
		Observable.just("1/3/2016", "5/9/2016", "10/12/2016").map(s -> LocalDate.parse(s, dtf))
				.subscribe(i -> System.out.println(i));

		System.out.println();
		System.out.println("Operatory zmiany -  startWith");
		Observable<String> menu = Observable.just("Coffee", "Tea", "Espresso", "Latte");
		menu.startWith("COFFEE SHOP MENU").subscribe(System.out::println);

		System.out.println();
		System.out.println("Operatory zmiany -  defaultIfEmpty");
		Observable<String> items = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
		items.filter(s -> s.startsWith("Z")).defaultIfEmpty("None").subscribe(System.out::println);

		System.out.println();
		System.out.println("Operatory zmiany -  sorted");
		Observable.just(6, 2, 5, 7, 1, 4, 9, 8, 3).sorted().subscribe(System.out::println);

	}
}

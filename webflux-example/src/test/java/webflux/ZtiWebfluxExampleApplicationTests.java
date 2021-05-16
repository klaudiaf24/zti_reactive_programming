package webflux;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
class ZtiWebfluxExampleApplicationTests {

	@Test
	void contextLoads() {
		Flux.just("Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień",
				"Październik", "Listopad", "Grudzień").filter(month -> month.startsWith("M"))
				.subscribe(System.out::println);

		Mono.just("Miesiąc").filter(month -> month.startsWith("M")).subscribe(System.out::println);

		Mono.just("Miesiąc").filter(month -> month.startsWith("P")).subscribe(System.out::println);
		;

	}

}

# Programowanie reaktywne

Repozytorium z przykładami i prezentacją na seminarium z przedmiotu _Zaawanoswane techonologie internetowe_ w semestrze letnim 2020/21 na Akademii Górniczo-Hutniczej w Krakowie, wydział Fizyki i Informatyki Stosowanej.

## Plan seminarium
* [Seminarium - prezentacja](#seminarium-prezentacja)
* [Reactor Project](#reactor-project)
* [RxJava](#rxjava)
* [Spring Webflux](#spring-webflux)

## Seminarium - prezentacja
.
	
## Reactor Project
. 

## RxJava

Poniżej zaprezenowane jest uruchomienie środowiska programistycznego i jego konfiguracja, w celu zaimplementowania przykładowego projektu z użyciem **RjJava** krok po kroku:

1. Przed przystąpieniem to setup'u środowiska wymagane jest przygotowanie sobie potrzebnych bibliotek, które zostaną dołączone do projektu przy pomocy **Maven**: 
    * [RxJava](https://mvnrepository.com/artifact/io.reactivex.rxjava2/rxjava) - w poniższym przykładzie wykorzystanko najnowsza wersję [2.2.21](https://mvnrepository.com/artifact/io.reactivex.rxjava2/rxjava/2.2.21)

        ![myImage](images/rxJava-maven.jpg)
    
    * [Reactive-Stream](https://mvnrepository.com/artifact/org.reactivestreams/reactive-streams) - w dependencjach do **RxJavy 2.2.21** podana jest wersja [1.0.3](https://mvnrepository.com/artifact/org.reactivestreams/reactive-streams/1.0.3), którą też należy dołączyć do projektu

        ![myImage](images/reactiveStreams-maven.jpg)


2. Uruchamiamy środowisko programistyczn dostępne na serwerach uczelnianych - **Eclipse**.
3. Tworzymy nowy projekt: **File -> New -> Java Project**, pozostajemy przy domyślnych ustawieniach.
4. Konwertujemy projekt do **Maven Project**: **: prawy przycisk myszy -> Configure -> Convert to Maven Project**, pozostawiając wszystkie ustawienia jako domyślne.
5. Do wygenerowanego pliku **pom.xml** dodajemy znalezione wcześniej dependencje RxJavy i ReactiveStream:
    ```xml
    <dependencies>
        <dependency>
            <groupId>io.reactivex.rxjava2</groupId>
            <artifactId>rxjava</artifactId>
            <version>2.2.21</version>
        </dependency>
        <dependency>
            <groupId>org.reactivestreams</groupId>
            <artifactId>reactive-streams</artifactId>
            <version>1.0.3</version>
        </dependency>
    </dependencies>

    ```
6. Implementujemy plik testowy **test.java**, który nie znajduje się w żadnym pakiecie:
    ```java
    import io.reactivex.Observable;
    import io.reactivex.functions.Consumer;

    public class test {

        public static void main(String[] args) {
            Observable.just("Witajcie", "na", "seminarium", "z", "Programowania reaktywnego!")
                    .subscribe(new Consumer<String>() {

                        @Override
                        public void accept(String t) throws Exception {
                            System.out.println(t);
                        }

                    });
        }
    }
    ```

7. Uruchamiamy i otrzymujemy:
    ```
    Witajcie
    na
    seminarium
    z
    Programowania reaktywnego!
    ```
    Co oznacza dobrze dodane biblioteki i pozwala przejść do głównego przykładu.


8. Aplikacja będzie przykładem programowania reaktywnego, polegająca na pobieraniu zanych stron internetowych z wykorzystaniem bibloteki **RxJava**.

9. Tworzymy nowy pakiet **rxjava**, którym umieścimy klasę **Worker.java**:
    ```java
    package rxjava;

    public class Worker {

        private static String[] url = {
                "https://github.com/ReactiveX/RxJava", 
                "http://reactivex.io/",
                "https://www.baeldung.com/rx-java"
                };
        
        public static void main(String[] args) {
            
        }
    }
    ```

10. Dodajemy do klasy **Worker.java** prostą funkcję odpowiedzialną za pobranie strony internetowej dzięki URL'owi, którą wykorzystamy przy implementacji _Observera_:
    ```java
    public static void readWebsite(String link, String filename) throws IOException {
		URL website = new URL(link);
		BufferedReader in = new BufferedReader(new InputStreamReader(website.openStream()));
		
		String inputLine;
		StringBuilder stringBuilder = new StringBuilder();	
	
		while((inputLine = in.readLine()) != null) {
			stringBuilder.append(inputLine);
			stringBuilder.append(System.lineSeparator());
		}
		
		in.close();
		
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename, false));
		bufferedWriter.write(stringBuilder.toString());
		bufferedWriter.close();
	}
    ```

11. Tworzymy w funkcji _main_ _Observera_, który jest interfacem, więc wymagana jest ]implementacja 4 metod: _onComplete()_, _onError(Throwable e)_, _onNext(String t)_ oraz dla standardu **RxJava2** _onSubscribe(Disposable d)_, które w wersji **RxJava1** nie było wymagane:
    ```java
    Observer<String> observer = new Observer<String>() {
			@Override
			public void onComplete() {
				// TODO Auto-generated method stub
			}

			@Override
			public void onError(Throwable e) { 
				// TODO Auto-generated method stub
			}

			@Override
			public void onNext(String t) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSubscribe(Disposable d) {
				// TODO Auto-generated method stub
			}
		};
    ```

12. Uzupełniamy podstawowe metody _Observera_, które będą nam potrzebne (nie przewidziałam działań dla _onError_ oraz _onSubscribe_), wprowadzamy działanie na wątkach:
    ```java
			@Override
			public void onComplete() {
				System.out.println("Wszytskie elementy zostały przetworzone!!");
			}

			@Override
			public void onNext(String t) {
				Thread thread = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							readWebsite(t, new Random().nextInt() + ".html");
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				});
				thread.start();

			}
    ```

13. Kiedy cała logika _Observera_ jest zaimplementowana pozostaje połączyć go z _Observable_ poprzez dodanie:
    ```java
    Observable.fromArray(url).subscribe(observer);
    ```
    do funkcji _main_.

14. Cała klasa **Worker.java** wygląda finalnie:
    ```java
    package rxjava;

    import java.io.BufferedReader;
    import java.io.BufferedWriter;
    import java.io.FileWriter;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.net.URL;
    import java.util.Random;

    import io.reactivex.Observable;
    import io.reactivex.Observer;
    import io.reactivex.disposables.Disposable;

    public class Worker {

        private static String[] url = { 
                "https://github.com/ReactiveX/RxJava", 
                "http://reactivex.io/",
                "https://www.baeldung.com/rx-java" };

        public static void main(String[] args) {
            Observer<String> observer = new Observer<String>() {
                @Override
                public void onComplete() {
                    System.out.println("Wszytskie elementy zostały przetworzone!!");

                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(String t) {
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                readWebsite(t, new Random().nextInt() + ".html");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    thread.start();
                    System.out.println("Pobrano adres: " + t);
                }

                @Override
                public void onSubscribe(Disposable d) {

                }
            };

            Observable.fromArray(url).subscribe(observer);

        }

        public static void readWebsite(String link, String filename) throws IOException {
            URL website = new URL(link);
            BufferedReader in = new BufferedReader(new InputStreamReader(website.openStream()));

            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
                stringBuilder.append(System.lineSeparator());
            }

            in.close();

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename, false));
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.close();
        }

    }
    ```
    Program działa prawidłowo, wyniki wyświetlone na konsoli:
    ```java
    Pobrano adres: https: //github.com/ReactiveX/RxJava
    Pobrano adres: http: //reactivex.io/
    Pobrano adres: https: //www.baeldung.com/rx-java
    Wszytskie elementy zostały przetworzone!!
    ```
    oraz poprawnie pobrane i wygenerowane pliki w strukturze katalogu:

    ![myImage](images/struct.jpg)

15. Inne przykłady operatorów znajdują się w katalogu _rxjava-example/src/examples_ zaczerpnięte zostały z ksiażki [Learning RxJava](http://www.blancodesigns.com.br/revistas/digital_art/imagens/Learning%20RxJava.pdf).

## Spring Webflux
.

### Twórcy
* [Klaudia Fil](https://github.com/klaudiaf24)
* [Joanna Marcinkowska](https://github.com/jmarcinkowska)
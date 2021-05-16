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

	private static String[] url = { "https://github.com/ReactiveX/RxJava", "http://reactivex.io/",
			"https://www.baeldung.com/rx-java" };

	public static void main(String[] args) {
		Observer<String> observer = new Observer<String>() {
			@Override
			public void onComplete() {
				// TODO Auto-generated method stub
				System.out.println("Wszytskie elementy zostały przetworzone!!");

			}

			@Override
			public void onError(Throwable e) {
				// TODO Auto-generated method stub
				e.printStackTrace();
			}

			@Override
			public void onNext(String t) {
				// TODO Auto-generated method stub
				Thread thread = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub

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

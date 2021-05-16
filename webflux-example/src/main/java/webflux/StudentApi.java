package webflux;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class StudentApi {

	private StudentRepo studentRepo;

	public StudentApi(StudentRepo studentRepo) {
		this.setStudentRepo(studentRepo);
	}

	@GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Student> get() {
		return studentRepo.findAll().delayElements(Duration.ofSeconds(1));
	}

	public StudentRepo getStudentRepo() {
		return studentRepo;
	}

	public void setStudentRepo(StudentRepo studentRepo) {
		this.studentRepo = studentRepo;
	}

}

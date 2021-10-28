package DogApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/dogs")
public class DogController {

    private DogRespository dogRespository;

    @Autowired
    public DogController(DogRespository dogRespository){ this.dogRespository = dogRespository; }

    @GetMapping
    public List<Dog> getDog (){ return dogRespository.findAll(); }

    @PostMapping
    public Dog addDog (@RequestBody Dog dog){ return dogRespository.save(dog); }

    @GetMapping("/{id}")
    public ResponseEntity<Dog> getDog (@PathVariable Long id){
        return dogRespository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDog (@PathVariable Long id){
        return dogRespository.findById(id)
                .map(dog -> {
                    dogRespository.delete(dog);
                    return ResponseEntity.ok().build();
                }).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Dog> patchDog (@PathVariable Long id, @RequestBody Dog dog){
        return dogRespository.findById(id)
                .map(dog1 -> {
                    dog.setName(dog1.getName());
                    dog.setAge(dog1.getAge());
                    dog.setEmail(dog1.getEmail());
                    return ResponseEntity.ok().body(dog1);
                }).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dog> putDog (@PathVariable Long id, @RequestBody Dog dog){
        return dogRespository.findById(id)
                .map(dog1 -> {
                    if (dog.getName() != null){
                        dog.setName(dog1.getName());
                    }
                    if (dog.getAge() != 0){
                        dog.setAge(dog1.getAge());
                    }
                    if(dog.getEmail() != null){
                        dog.setEmail(dog1.getEmail());
                    }
                    return ResponseEntity.ok().body(dog1);
                }).orElseGet(()-> ResponseEntity.notFound().build());
    }
}

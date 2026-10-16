package com.esi.msordonnance.Repository;
import com.esi.msordonnance.Entities.Medicament;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface MedicamentRepository extends MongoRepository<Medicament, String> {
    @Query("{ 'ordonnance.idPatient': ?0 }")
    List<Medicament> findMedicamentsByIdPatient(Long idPatient);
}

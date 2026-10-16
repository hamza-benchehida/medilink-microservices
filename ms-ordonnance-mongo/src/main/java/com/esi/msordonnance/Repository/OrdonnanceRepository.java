package com.esi.msordonnance.Repository;
import com.esi.msordonnance.Entities.Ordonnance;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface OrdonnanceRepository extends MongoRepository<Ordonnance, String> {
    List<Ordonnance> findOrdonnancesByIdPatient(long idPatient);
}

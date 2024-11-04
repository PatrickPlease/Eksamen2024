package dat.daos;

import dat.dtos.TripDTO;
import java.util.List;
import java.util.Set;

public interface IDAO<T> {
    T getByID(Integer id);
    Set<T> getAll();
    T create(T t);
    void delete(T t);
    T update(T t);
}

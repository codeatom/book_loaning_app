package book_loaning_app.application.data;

import book_loaning_app.application.model.Details;

import java.util.List;

public interface DetailsDAO {
    Details findById(int id);
    List<Details> findAll();
    Details create(Details details);
    Details update(Details details);
    void delete(int id);
}

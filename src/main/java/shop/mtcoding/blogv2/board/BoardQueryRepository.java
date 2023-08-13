package shop.mtcoding.blogv2.board;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardQueryRepository {

    @Autowired
    private EntityManager em;

}

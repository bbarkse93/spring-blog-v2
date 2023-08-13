package shop.mtcoding.blogv2.reply;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReplyQueryRepository {

    @Autowired
    private EntityManager em;

}

package com.crypto.cryptoApp.repository;

import com.crypto.cryptoApp.dto.CoinTransationDTO;
import com.crypto.cryptoApp.entity.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@EnableAutoConfiguration
public class CoinRepository {
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Coin insert(Coin coin){
        entityManager.persist(coin);
        return  coin;
    }

    @Transactional
    public Coin update(Coin coin){
        entityManager.merge(coin);
        return coin;
    }
    public List<CoinTransationDTO> listAll(){
       String jpql = "select new com.crypto.cryptoApp.dto.CoinTransationDTO (c.name, sum(c.quantity)) from Coin c group by c.name";
       TypedQuery<CoinTransationDTO> query = entityManager.createQuery(jpql,CoinTransationDTO.class);
       return query.getResultList();
    }

    public List<Coin> getByName(String name){
        String jpql = "select c from Coin c where c.name like :name";
        TypedQuery<Coin> query = entityManager.createQuery(jpql, Coin.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    @Transactional
    public boolean remove(int id){
       Coin coin = entityManager.find(Coin.class, id);
       if (coin == null)
           new  RuntimeException();
      entityManager.remove(coin);
      return  true;

    }
}

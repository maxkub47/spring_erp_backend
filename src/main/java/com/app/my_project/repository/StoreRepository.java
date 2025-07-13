package com.app.my_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.my_project.entity.StoreEntity;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
    String SQL = """
                    SELECT
                        id,
                        name,
                        (
                            SELECT SUM(PRODLOG.qty)
                            FROM PRODLOG
                            WHERE PRODLOG.production_id = PRODUCT.id
                        ) AS total_production_log,
                        (
                            SELECT SUM(PRODLOSS.qty)
                            FROM PRODLOSS
                            WHERE PRODLOSS.production_id = PRODUCT.id
                        ) AS total_production_loss
                        FROM PRODUCT
                        WHERE PRODUCT.id = :id
            """;

    @Query(value = SQL, nativeQuery = true)
    List<Object[]> findProductionSummary(@Param("id") Long id);
}

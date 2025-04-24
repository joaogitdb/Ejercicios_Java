package com.ejercicio.ejercicio3.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ejercicio.ejercicio3.Employee;


@Repository
@Transactional
public class EmployeeDao {

	@PersistenceContext
    private EntityManager entityManager;

	
	  /**
     * Retorna los empleados cuya cantidad de transacción es mayor que el monto mínimo.
     */
    public List<Employee> findMinAmount(int minAmount) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> employee = cq.from(Employee.class);
        cq.select(employee)
          .where(cb.greaterThan(employee.get("transactionAmount"), minAmount));
        return entityManager.createQuery(cq).getResultList();
    }
    
    /**
     * Elimina el empleado dado.
     */
    public void delete(Employee emp) {
        // Se asegura de que la entidad esté administrada
        Employee managed = entityManager.merge(emp);
        entityManager.remove(managed);
    }
}

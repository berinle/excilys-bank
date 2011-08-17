package com.excilys.ebi.bank.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.ebi.bank.model.entity.ref.OperationStatus;
import com.excilys.ebi.bank.model.entity.ref.OperationStatusRef;

public interface OperationStatusRefDao extends JpaRepository<OperationStatusRef, OperationStatus> {

}

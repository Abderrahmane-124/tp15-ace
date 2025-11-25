package com.example.banque_service.DTO;

import com.example.banque_service.Entities.TypeTransaction;
import lombok.Data;

import java.util.Date;

@Data
public class TransactionRequest {
    private Long compteId;
    private double montant;
    private Date date;
    private TypeTransaction type;
}


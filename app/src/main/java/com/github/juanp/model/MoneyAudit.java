package com.github.juanp.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record MoneyAudit (
    UUID transactionID,
    BankService targetService,
    String description,
    OffsetDateTime createdAt)
{

}

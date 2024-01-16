package pl.edu.prz.baw.houston.fed.money

import java.time.ZonedDateTime

class MoneyTransferDetails {
    String amount
    String fromAccountNumber
    String toAccountNumber
    ZonedDateTime requestedAt
    MoneyTransferStatus status

    MoneyTransferDetails(String amount, String fromAccountNumber, String toAccountNumber, ZonedDateTime requestedAt, MoneyTransferStatus status) {
        this.amount = amount
        this.fromAccountNumber = fromAccountNumber
        this.toAccountNumber = toAccountNumber
        this.requestedAt = requestedAt
        this.status = status
    }
}

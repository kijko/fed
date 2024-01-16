package pl.edu.prz.baw.houston.fed.money

import java.time.ZonedDateTime

class TransferRequestInfo {

    String id
    String sender
    String recipient
    String amount
    ZonedDateTime dateTime
    MoneyTransferStatus status


    TransferRequestInfo() {
    }

    TransferRequestInfo(String id, String sender, String recipient, String amount, ZonedDateTime dateTime, MoneyTransferStatus status) {
        this.id = id
        this.sender = sender
        this.recipient = recipient
        this.amount = amount
        this.dateTime = dateTime
        this.status = status
    }
}

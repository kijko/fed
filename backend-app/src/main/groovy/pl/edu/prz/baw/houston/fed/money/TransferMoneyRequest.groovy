package pl.edu.prz.baw.houston.fed.money

class TransferMoneyRequest {
    String toAccountNumber
    String amount

    TransferMoneyRequest() {
    }

    TransferMoneyRequest(String toAccountNumber, String amount) {
        this.toAccountNumber = toAccountNumber
        this.amount = amount
    }
}

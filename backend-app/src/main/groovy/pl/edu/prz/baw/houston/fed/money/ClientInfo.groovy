package pl.edu.prz.baw.houston.fed.money

class ClientInfo {
    String accountNumber
    String login
    String firstName
    String lastName
    String balance
    List<MoneyTransferDetails> outboundTransfers
    List<MoneyTransferDetails> inboundTransfer

    ClientInfo(String accountNumber, String login, String firstName, String lastName, String balance, List<MoneyTransferDetails> outboundTransfers, List<MoneyTransferDetails> inboundTransfer) {
        this.accountNumber = accountNumber
        this.login = login
        this.firstName = firstName
        this.lastName = lastName
        this.balance = balance
        this.outboundTransfers = outboundTransfers
        this.inboundTransfer = inboundTransfer
    }
}

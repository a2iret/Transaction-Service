package kg.megacom.transactionwithlogging.dto;

public record TransferDto(Integer sender_id, Integer receiver_id, double amount) {}

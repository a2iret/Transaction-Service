package kg.megacom.transactionwithlogging.controller;

import kg.megacom.transactionwithlogging.dto.TransferDto;
import kg.megacom.transactionwithlogging.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> createTransfer(@RequestBody TransferDto transferDto){
        return ResponseEntity.ok(service.createTransfer(transferDto));
    }
}

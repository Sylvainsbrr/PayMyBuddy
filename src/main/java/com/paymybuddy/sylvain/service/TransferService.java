package com.paymybuddy.sylvain.service;

import com.paymybuddy.sylvain.dto.ExternalTransferDto;
import com.paymybuddy.sylvain.dto.InternalTransferDto;

import java.util.List;

public interface TransferService {
    InternalTransferDto doInternalTransfer(InternalTransferDto internalTransferDto);
    List<InternalTransferDto> findInternalTransferByUser(String emailOwner);
    ExternalTransferDto doExternalTransfer(ExternalTransferDto externalTransferDto);
    List<ExternalTransferDto> findExternalTransferByUser(String username);
}

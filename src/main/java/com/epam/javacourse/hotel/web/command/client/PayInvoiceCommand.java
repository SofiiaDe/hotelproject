package com.epam.javacourse.hotel.web.command.client;

import com.epam.javacourse.hotel.AppContext;
import com.epam.javacourse.hotel.Exception.DBException;
import com.epam.javacourse.hotel.model.service.IInvoiceService;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;
import com.epam.javacourse.hotel.web.command.RedirectCommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PayInvoiceCommand implements ICommand {
    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        IInvoiceService invoiceService = AppContext.getInstance().getInvoiceService();
        int invoiceId = Integer.parseInt(request.getParameter("pay_invoice"));
        invoiceService.payInvoice(invoiceId);

        return new RedirectCommandResult(Path.COMMAND_CLIENT_ACCOUNT);

    }
}
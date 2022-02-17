package com.epam.javacourse.hotel.web.command.client;

import com.epam.javacourse.hotel.exception.DBException;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PaymentPageCommand implements ICommand {
    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DBException {

        request.setAttribute("invoiceId", request.getParameter("invoiceId"));
        return new AddressCommandResult(Path.PAGE_PAY_INVOICE);

    }
}

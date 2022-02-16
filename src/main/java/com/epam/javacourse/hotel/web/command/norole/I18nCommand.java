package com.epam.javacourse.hotel.web.command.norole;

import com.epam.javacourse.hotel.Exception.AppException;
import com.epam.javacourse.hotel.web.Path;
import com.epam.javacourse.hotel.web.command.AddressCommandResult;
import com.epam.javacourse.hotel.web.command.ICommand;
import com.epam.javacourse.hotel.web.command.ICommandResult;
import com.epam.javacourse.hotel.web.command.RedirectCommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Objects;

/**
 * Internationalization command.
 */
public class I18nCommand implements ICommand {

    @Override
    public ICommandResult execute(HttpServletRequest request, HttpServletResponse response) throws AppException {

        HttpSession session = request.getSession();

        String fmtLocale = "javax.servlet.jsp.jstl.fmt.locale";

        if (Objects.equals(request.getParameter("langField"), Path.LOCALE_NAME_EN)) {
            Config.set(session, fmtLocale, Path.LOCALE_NAME_EN);
            session.setAttribute("locale", Path.LOCALE_NAME_EN);
        } else {
            Config.set(session, fmtLocale, Path.LOCALE_NAME_UK);
            session.setAttribute("locale", Path.LOCALE_NAME_UK);
        }


        String url = request.getHeader("referer");
        if (!url.contains("controller")) {
            return new RedirectCommandResult(Path.COMMAND_HOME_PAGE);
        }
        String originalPath = url.substring(url.indexOf("controller"));

        return new RedirectCommandResult(originalPath);
    }
}

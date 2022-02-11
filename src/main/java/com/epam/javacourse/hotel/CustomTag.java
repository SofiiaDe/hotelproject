package com.epam.javacourse.hotel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.GregorianCalendar;


public class CustomTag extends TagSupport {

    private static final Logger logger = LogManager.getLogger(CustomTag.class);

    String color;

    public CustomTag() {
        super();
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int doAfterBody() throws JspException
    {
        try
        {
            /* Creating an instance of the BodyContent class */
            // Get the bodycontent as string
//            BodyContent bc = getBodyContent();
//
//            // GetJspWriter to output content
//            String body = bc.getString();
//
//            /* Creating an instance of the JspWriter class */
//            JspWriter out = bc.getEnclosingWriter();

            JspWriter out = pageContext.getOut();


            /* Setting the color as selected by the user */
            out.print("<body bgcolor="+color+">");

        }
        catch(IOException ioe)
        {
            throw new JspException("Error:"+ioe.getMessage());
        }
        return SKIP_BODY;
    }



    //    public static String notNull(Object obj) {
//        String result;
//        if (obj == null || obj.toString().isEmpty()) {
//            result = "Attribute or Parameter is null or empty";
//        } else {
//            result = obj.toString();
//        }
//        return result;
//    }

//    public static boolean contains(String[] list, String string) {
//        if (list != null && string != null) {
//            for (String element: list) {
//                if (string.equals(element)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

}

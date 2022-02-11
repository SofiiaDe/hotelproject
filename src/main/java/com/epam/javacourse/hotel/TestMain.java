package com.epam.javacourse.hotel;

import com.epam.javacourse.hotel.model.service.IApplicationService;

public class TestMain {

    public static void main(String[] args) {
        IApplicationService applicationService = AppContext.getInstance().getApplicationService();
        System.out.println(applicationService.parseToLocalDateTime("2022-02-18T00:00"));
    }

}

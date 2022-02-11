package com.epam.javacourse.hotel;

public class AppConfigurationManager implements IAppConfigurationManager{
    @Override
    public int getDefaultPageSize() {
        // theoretically it should read from configuration file, but now it's ok to have it hardcoded
        return 3;
    }
}

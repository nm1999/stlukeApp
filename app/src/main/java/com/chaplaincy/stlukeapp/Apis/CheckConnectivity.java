package com.chaplaincy.stlukeapp.Apis;

import java.io.IOException;

public class CheckConnectivity {

    public boolean isInternetAvailable() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("ping -c 1 google.com");
        int exitCode = 0;

        try {
            exitCode = process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return exitCode == 0;
    }
}

package com.example.uniSystem_web_app.llmLauncher;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LlmLauncher {

    @Value("${llm.python.script}")
    private String pythonScript;

    @Value("${llm.python.port}")
    private int port;

    private Process pythonProcess;

    @PostConstruct
    public void startPythonServer() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("py",  pythonScript , String.valueOf(port));
        pb.inheritIO();
        pythonProcess = pb.start();
        System.out.println("Python LLM server started on port " + port);
    }

    @PreDestroy
    public void terminatePythonServer(){
        if(pythonProcess != null && pythonProcess.isAlive()){
            pythonProcess.destroy();
            System.out.println("Python LLM server terminated.");
        }
    }

}

package com.demo.producer.configuration;

import java.io.File;
import java.time.Duration;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.demo.producer.listener.MyFileChangeListener;


@Configuration
public class FileWatcherConfig {

	@Value("${data.listener.log.path}")
	private String logPath;
	
	@Value("${data.listener.scan.duration.millis}")
	private int scanDuration;
	
	@Value("${data.listener.wait.duration.millis}")
	private int waitDuration;
	
	@Bean
	public MyFileChangeListener fileChangeListener() {
		return new MyFileChangeListener();
	}
	
	@Bean
    public FileSystemWatcher fileSystemWatcher() {
        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(true, Duration.ofMillis(scanDuration), Duration.ofMillis(waitDuration));
        fileSystemWatcher.addSourceDirectory(new File(logPath));
        
        fileSystemWatcher.addListener(fileChangeListener());
        fileSystemWatcher.start();
        
        System.out.println("Started FileSystemWatcher");
        return fileSystemWatcher;
    }
	
    @PreDestroy
    public void onDestroy() throws Exception {
        fileSystemWatcher().stop();
    }

}

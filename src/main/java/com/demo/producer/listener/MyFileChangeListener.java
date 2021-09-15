package com.demo.producer.listener;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFile.Type;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Component;

import com.demo.producer.service.ProducerService;
import com.demo.producer.utility.FileUtility;

@Component
public class MyFileChangeListener implements FileChangeListener {
	
	@Autowired
	private ProducerService producerService;
	
	@Override
	public void onChange(Set<ChangedFiles> changeSet) {
		for(ChangedFiles cFiles : changeSet) {
            for(ChangedFile cFile: cFiles.getFiles()) {
                if(!isLocked(cFile.getFile().toPath()) && cFile.getType().equals(Type.MODIFY)) {
                	// Get last line from file
                	String message = FileUtility.readLastLineFromFile(cFile.getFile());
                	
                	// Publish the message
                    producerService.publishToTopic(FileUtility.getFileTopic(cFile.getFile().getName()), message);
                }
            }
        }
	}
	
	private boolean isLocked(Path path) {
        try (FileChannel ch = FileChannel.open(path, StandardOpenOption.WRITE); FileLock lock = ch.tryLock()) {
            return lock == null;
        } catch (IOException e) {
            return true;
        }
    }


}

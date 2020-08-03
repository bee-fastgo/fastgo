package com.bee.team.fastgo.tools.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * store trigger log in each log-file
 *
 * @author luke 2016-3-12 19:25:12
 */
public class DeployJobFileAppender {
    private static Logger logger = LoggerFactory.getLogger(DeployJobFileAppender.class);

    /**
     * log filename, like "logPath/yyyy-MM-dd/9999.log"
     *
     * @param logPath
     * @param logId
     * @return
     */
    public static String makeLogFileName(String logPath, String logId) {

		// avoid concurrent problem, can not be static
        File logFilePath = new File(logPath);
        if (!logFilePath.exists()) {
            logFilePath.mkdir();
        }

        // filePath/yyyy-MM-dd/9999.log
        String logFileName = logFilePath.getPath()
                .concat(File.separator)
                .concat(logId)
                .concat(".log");
        return logFileName;
    }

    /**
     * append log
     *
     * @param logFileName
     * @param appendLog
     */
    public static void appendLog(String logFileName, String appendLog) {

        // log file
        if (logFileName == null || logFileName.trim().length() == 0) {
            return;
        }
        File logFile = new File(logFileName);

        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                return;
            }
        }

        // log
        if (appendLog == null) {
            appendLog = "";
        }
        appendLog += "\r\n";

        // append file content
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(logFile, true);
            fos.write(appendLog.getBytes("utf-8"));
            fos.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

    }

    /**
     * support read log-file
     *
     * @param logFileName
     * @return log content
     */
    public static DeployLogResult readLog(String logFileName, int fromLineNum) {

        // valid log file
        if (logFileName == null || logFileName.trim().length() == 0) {
            return new DeployLogResult(fromLineNum, 0, "readLog fail, logFile not found", true);
        }
        File logFile = new File(logFileName);

        if (!logFile.exists()) {
            return new DeployLogResult(fromLineNum, 0, "readLog fail, logFile not exists", true);
        }

        // read file
        Boolean isEnd = false;
        StringBuffer logContentBuffer = new StringBuffer();
        int toLineNum = 0;
        LineNumberReader reader = null;
        try {
            //reader = new LineNumberReader(new FileReader(logFile));
            reader = new LineNumberReader(new InputStreamReader(new FileInputStream(logFile), "utf-8"));
            String line = null;

            while ((line = reader.readLine()) != null) {
				// [from, to], start as 1
                toLineNum = reader.getLineNumber();
                if (toLineNum >= fromLineNum) {
                    logContentBuffer.append(line).append("\n");
                }
                if(line.contains("部署完成")){
                    isEnd = true;
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

        // result
        DeployLogResult deployLogResult = new DeployLogResult(fromLineNum, toLineNum, logContentBuffer.toString(), isEnd);
        return deployLogResult;

		/*
        // it will return the number of characters actually skipped
        reader.skip(Long.MAX_VALUE);
        int maxLineNum = reader.getLineNumber();
        maxLineNum++;	// 最大行号
        */
    }

    /**
     * read log data
     *
     * @param logFile
     * @return log line content
     */
    public static String readLines(File logFile) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(logFile), "utf-8"));
            if (reader != null) {
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                return sb.toString();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }

}

package toppan.example.toppan.models;

import lombok.Data;

@Data
public class ReportData {
    /**
     * Обращений за прошлый период
     */
    private String requestOld;
    /**
     * Выдано за прошлый период
     */
    private String issuedOld;
    /**
     * Обращений
     */
    private String request;
    /**
     * Выдано
     */
    private String issued;
    private String pidrozdilNumber;

    public ReportData(String reportData) {
        String[] strData = reportData.split(",");
        requestOld = strData[0];
        issuedOld = strData[1];
        request = strData[2];
        issued = strData[3];
        pidrozdilNumber = strData[4];
    }
}

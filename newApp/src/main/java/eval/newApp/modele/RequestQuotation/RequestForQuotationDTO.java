package eval.newApp.modele.RequestQuotation;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class RequestForQuotationDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("transaction_date")
    private Date transactionDate;

    @JsonProperty("status")
    private String status;

    @JsonProperty("schedule_date")
    private Date scheduleDate;

    // Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }
}

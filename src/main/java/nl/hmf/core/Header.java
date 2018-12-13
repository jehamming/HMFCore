package nl.hmf.core;

public class Header {

    private String version;
    private String sender;
    private String timestamp;
    private String correlatiodId;
    private String producerId;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCorrelatiodId() {
        return correlatiodId;
    }

    public void setCorrelatiodId(String correlatiodId) {
        this.correlatiodId = correlatiodId;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }
}

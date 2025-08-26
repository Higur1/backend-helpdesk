package io.github.higur.helpdesk.domain.enums;

public enum Status {
    OPEN(0, "OPEN"), PROCEEDING(1, "PROCEEDING"), CLOSED(2, "CLOSED");

    private Integer code;
    private String description;

    Status(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static Status toEnum(Integer code){
        if(code == null){
            return null;
        }
        for(Status x: Status.values()){
            if(code.equals(x.getCode())){
                return x;
            }
        }
        throw new IllegalArgumentException("Invalid status");
    }
}
